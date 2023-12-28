package cn.seu.cs.eshop.service.service.order.impl;

import cn.seu.cs.eshop.account.sdk.entity.req.GetAccountInfoResponse;
import cn.seu.cs.eshop.account.sdk.rpc.EshopAccountService;
import cn.seu.cs.eshop.common.component.EshopConfService;
import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.service.cache.order.OrderAreaAddressCache;
import cn.seu.cs.eshop.service.cache.product.ProdHashCache;
import cn.seu.cs.eshop.service.convert.EshopOrderConvert;
import cn.seu.cs.eshop.service.dao.EshopOrderDao;
import cn.seu.cs.eshop.service.dao.EshopOrderItemDao;
import cn.seu.cs.eshop.service.dao.EshopOrderReviewDao;
import cn.seu.cs.eshop.service.manager.order.EshopOrderProdManager;
import cn.seu.cs.eshop.service.pojo.bo.OrderStatusChangeRemarkConfBO;
import cn.seu.cs.eshop.service.pojo.db.*;
import cn.seu.cs.eshop.service.redisson.EshopRedissonLockService;
import cn.seu.cs.eshop.service.redisson.OrderNumberGenerateService;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopOrderAddressDTO;
import cn.seu.cs.eshop.service.sdk.order.order.dto.*;
import cn.seu.cs.eshop.service.sdk.order.order.req.*;
import cn.seu.cs.eshop.service.service.AbstractCrudService;
import cn.seu.cs.eshop.service.service.order.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import cs.seu.cs.eshop.common.sdk.exception.EshopException;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static cn.seu.cs.eshop.service.enums.order.EshopOrderCloseTypeEnum.TIMEOUT_CANCEL;
import static cn.seu.cs.eshop.service.enums.order.EshopOrderCloseTypeEnum.getByCloseType;
import static cn.seu.cs.eshop.service.enums.order.EshopOrderPayTypeEnum.getByPayType;
import static cn.seu.cs.eshop.service.enums.order.EshopOrderStatusEnum.*;
import static cn.seu.cs.eshop.service.nacos.ServiceNacosConfEnum.orderStatusChangeRemarkConf;
import static cn.seu.cs.eshop.service.nacos.ServiceNacosConfEnum.unpaidOrderTimeout;
import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.orderIdRedissonLock;
import static cs.seu.cs.eshop.common.sdk.enums.EshopStatusEnum.INVALID;
import static cs.seu.cs.eshop.common.sdk.enums.EshopStatusEnum.VALID;
import static cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils.buildSuccessResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Service
public class OrderServiceImpl extends AbstractCrudService<EshopOrderDTO> implements OrderService {
    @Resource
    private ProdHashCache prodHashCache;
    @Resource
    private EshopOrderDao eshopOrderDao;
    @Resource
    private EshopOrderItemDao eshopOrderItemDao;
    @Resource
    private EshopOrderReviewDao eshopOrderReviewDao;
    @Resource
    private EshopRedissonLockService eshopRedissonLockService;
    @Resource
    private EshopConfService eshopConfService;
    @Resource
    private OrderAreaAddressCache orderAreaAddressCache;
    @Resource
    private OrderNumberGenerateService orderNumberGenerateService;
    @Resource
    private EshopOrderProdManager eshopOrderProdManager;
    @DubboReference(timeout = 4000, retries = 0)
    private EshopAccountService eshopAccountService;


    @Override
    public InitOrderListResponse initOrderList(InitOrderListRequest request) {
        List<OrderInitIdsDTO> orderIds = request.getOrderIds();
        List<EshopOrderItemDTO> data = new ArrayList<>();
        Map<Long, String> shopMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(orderIds)) {
            data = orderIds.stream()
                    .map(id -> {
                        EshopProdDO prod = prodHashCache.getProdData(id.getProdId());
                        EshopProdSkuDO sku = prodHashCache.getProdSkuData(id.getProdId(), id.getSkuId());
                        EshopOrderItemDTO item = EshopOrderConvert.convertToEshopOrderItemDTO(prod, sku);
                        if (item != null) {
                            String shopName = shopMap.getOrDefault(item.getShopId(), null);
                            if (shopName == null) {
                                GetAccountInfoResponse response =
                                        eshopAccountService.getAccountInfo(item.getShopId());
                                shopName = response.getData().getNickname();
                                shopMap.put(item.getShopId(), shopName);
                            }
                            item.setProdCount(id.getProdCount());
                            item.setExt(shopName);
                            item.setDeliveryCost(prod.getDeliveryPrice());
                        }
                        return item;
                    })
                    .filter(Objects::nonNull)
                    .toList();
        }
        return buildSuccessResponse(InitOrderListResponse.class, data);
    }

    @Override
    @Transactional
    public BaseResponse changeOrderStatus(ChangeOrderStatusRequest request) {
        RLock lock = eshopRedissonLockService.getLock(orderIdRedissonLock, request.getOrderId());
        try {
            lock.lock(orderIdRedissonLock.expirationTime(), orderIdRedissonLock.timeUnit());
            EshopOrderDO origin = eshopOrderDao.selectById(request.getOrderId());
            if (origin == null) {
                throw new EshopException("订单不存在请检查");
            }
            Integer originStatus = origin.getStatus(), newStatus = request.getStatus();
            OrderStatusChangeRemarkConfBO config =
                    eshopConfService.getConfigObject(orderStatusChangeRemarkConf, OrderStatusChangeRemarkConfBO.class);
            String remark = config.getRemarkByStatus(newStatus);
            EshopOrderDO update = new EshopOrderDO();
            update.setId(request.getOrderId());
            if (originStatus == UNPAID.getStatus()) {
                if (newStatus == UNDELIVERED.getStatus()) { // 付款
                    int payType = Integer.parseInt(request.getParam1());
                    long total = Long.parseLong(request.getParam2());
                    String price = BigDecimal.valueOf(total)
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
                            .toString();
                    remark = remark.formatted(getByPayType(payType).getPayTypeDesc(), price);
                    update.setPayType(payType);
                    update.setPayTime(TimeUtils.getCurrentTime());
                } else if (newStatus == CANCELED.getStatus()) { // 取消
                    int closeType = Integer.parseInt(request.getParam1());
                    remark = remark.formatted(getByCloseType(closeType).getCloseTypeDesc());
                    update.setCloseType(closeType);
                    update.setCancelTime(TimeUtils.getCurrentTime());
                    // 退还库存
                    List<EshopOrderItemDO> items =
                            eshopOrderItemDao.selectByOrderId(origin.getOrderNumber());
                    for (EshopOrderItemDO item : items) {
                        eshopOrderProdManager.updateProdSkuStocks(item.getProdId(),
                                item.getSkuId(), -item.getProdCount());
                    }
                } else {
                    throw new EshopException("订单未付款");
                }
            } else if (originStatus == UNDELIVERED.getStatus()) {
                if (newStatus == UNRECEIVED.getStatus()) { // 发货
                    String deliveryId = request.getParam1();
                    remark = remark.formatted(deliveryId);
                    update.setDeliveryId(deliveryId);
                    update.setDeliveryTime(TimeUtils.getCurrentTime());
                } else {
                    throw new EshopException("订单未发货");
                }
            } else if (originStatus == UNRECEIVED.getStatus()) {
                if (newStatus == FINISHED.getStatus()) { // 收货
                    update.setCompleteTime(TimeUtils.getCurrentTime());
                } else {
                    throw new EshopException("订单未收货");
                }
            }  else {
                throw new EshopException("订单状态不正确");
            }
            // 更新订单状态
            update.setStatus(newStatus);
            update.setUpdateTime(TimeUtils.getCurrentTime());
            eshopOrderDao.updateById(update);
            // 添加订单review表
            EshopOrderReviewDO review = new EshopOrderReviewDO();
            review.setOrderId(request.getOrderId());
            review.setModifier(request.getModifier());
            review.setOldStatus(originStatus);
            review.setStatus(newStatus);
            review.setRemark(remark);
            MysqlUtils.buildEffectEntity(review);
            eshopOrderReviewDao.insert(review);
            return buildSuccessResponse(BaseResponse.class, update.getId().toString());
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public BaseResponse updateUserOrder(UpdateUserOrderRequest request) {
        long id = crudOperation(request);
        return buildSuccessResponse(BaseResponse.class, String.valueOf(id));
    }

    @Override
    public ListPageOrderResponse listPageOrder(ListPageOrderRequest request) {
        IPage<EshopOrderDO> pages = eshopOrderDao.selectByCondition(
                request.getOrderNumber(), request.getUserId(), request.getShopId(), request.getRoleType(),
                request.getStatus(), request.getCloseType(), request.getPayType(), request.getPage());
        EshopOrderPageDTO data = MysqlUtils.buildPageData(EshopOrderPageDTO.class, pages,
                res -> {
                    EshopOrderAddressDTO address = EshopOrderAddressDTO.builder()
                            .id(res.getAddressId())
                            .build();
                    return EshopOrderConvert.convertToEshopOrderDTO(res, address, null);
                });
        return buildSuccessResponse(ListPageOrderResponse.class, data);
    }

    @Override
    public ListOrderReviewResponse listOrderReview(Long orderId) {
        List<EshopOrderReviewDO> reviews = eshopOrderReviewDao.selectByOrderId(orderId);
        List<OrderStatusChangeDTO> data = reviews.stream()
                .map(EshopOrderConvert::convertToOrderStatusChangeDTO)
                .toList();
        return buildSuccessResponse(ListOrderReviewResponse.class, data);
    }

    @Override
    @Transactional
    public GetOrderInfoResponse getOrderInfo(Long orderId) {
        EshopOrderDO order = eshopOrderDao.selectById(orderId);
        EshopOrderAddressDTO address = orderAreaAddressCache.getOrderAddress(order.getAddressId());
        Long timeout = eshopConfService.getConfigObject(unpaidOrderTimeout, Long.class);
        // 检查订单是否超时
        if (order.getStatus() == UNPAID.getStatus() && timeout + order.getCreateTime() < TimeUtils.getCurrentTime()) {
            ChangeOrderStatusRequest request = ChangeOrderStatusRequest.builder()
                    .orderId(order.getId())
                    .modifier(StringUtils.EMPTY)
                    .status(CANCELED.getStatus())
                    .param1(String.valueOf(TIMEOUT_CANCEL.getCloseType()))
                    .build();
            changeOrderStatus(request);
            order = eshopOrderDao.selectById(orderId);
        }
        List<EshopOrderItemDO> itemsDO = eshopOrderItemDao.selectByOrderId(order.getOrderNumber());
        List<EshopOrderItemDTO> items = itemsDO.stream()
                .map(EshopOrderConvert::convertToEshopProdOrderDTO)
                .toList();
        EshopOrderDTO data = EshopOrderConvert.convertToEshopOrderDTO(order, address, items);
        return buildSuccessResponse(GetOrderInfoResponse.class, data);
    }

    @Override
    public long insert(EshopOrderDTO data) {
        List<EshopOrderItemDTO> items = data.getOrderItems();
        if (CollectionUtils.isNotEmpty(items)) {
            EshopOrderDO order = EshopOrderConvert.convertToEshopOrderDO(data);
            MysqlUtils.buildEffectEntity(order);
            String orderNumber = orderNumberGenerateService.generateOrderNumber();
            order.setOrderNumber(orderNumber);
            for (EshopOrderItemDTO item : items) {
                Long prodId = item.getProdId(), skuId = item.getSkuId();
                Integer count = item.getProdCount();
                // 更新SKU库存
                eshopOrderProdManager.updateProdSkuStocks(prodId, skuId, count);
                // 添加订单项
                EshopOrderItemDO orderItem = EshopOrderConvert.convertToEshopOrderItemDO(item, orderNumber);
                MysqlUtils.buildEffectEntity(orderItem);
                eshopOrderItemDao.insert(orderItem);
            }
            order.setUpdateTime(TimeUtils.getCurrentTime());
            order.setStatus(UNPAID.getStatus());
            eshopOrderDao.insert(order);
            OrderStatusChangeRemarkConfBO config =
                    eshopConfService.getConfigObject(orderStatusChangeRemarkConf, OrderStatusChangeRemarkConfBO.class);
            String remark = config.getRemarkByStatus(UNPAID.getStatus());
            // 添加订单review表
            EshopOrderReviewDO review = new EshopOrderReviewDO();
            review.setOrderId(order.getId());
            review.setModifier(StringUtils.EMPTY);
            review.setOldStatus(INVALID.getStatus());
            review.setStatus(UNPAID.getStatus());
            review.setRemark(remark);
            MysqlUtils.buildEffectEntity(review);
            eshopOrderReviewDao.insert(review);
            return order.getId();
        }
        return 0;
    }

    @Override
    public long delete(EshopOrderDTO data) {
        EshopOrderDO order = new EshopOrderDO();
        order.setId(data.getId());
        order.setUserDeleted(VALID.getStatus());
        eshopOrderDao.updateById(order);
        return data.getId();
    }

    @Override
    public long update(EshopOrderDTO data) {
        throw new EshopException("暂不支持修改订单~");
    }
}

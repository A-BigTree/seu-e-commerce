package cn.seu.cs.eshop.service.service.order.impl;

import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.service.convert.EshopOrderConvert;
import cn.seu.cs.eshop.service.convert.EshopProdCommConvert;
import cn.seu.cs.eshop.service.dao.EshopOrderDao;
import cn.seu.cs.eshop.service.dao.EshopOrderItemDao;
import cn.seu.cs.eshop.service.dao.EshopProdCommDao;
import cn.seu.cs.eshop.service.pojo.db.EshopOrderDO;
import cn.seu.cs.eshop.service.pojo.db.EshopOrderItemDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdCommDO;
import cn.seu.cs.eshop.service.sdk.order.comm.dto.EshopProdCommCountDTO;
import cn.seu.cs.eshop.service.sdk.order.comm.dto.EshopProdCommPageDTO;
import cn.seu.cs.eshop.service.sdk.order.comm.req.*;
import cn.seu.cs.eshop.service.sdk.order.order.dto.EshopOrderItemDTO;
import cn.seu.cs.eshop.service.service.order.ProdCommService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import cs.seu.cs.eshop.common.sdk.exception.EshopException;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static cn.seu.cs.eshop.service.enums.order.EshopOrderStatusEnum.FINISHED;
import static cn.seu.cs.eshop.service.enums.order.EshopProdCommEnum.*;
import static cs.seu.cs.eshop.common.sdk.enums.EshopStatusEnum.VALID;
import static cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils.buildSuccessResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/28
 */
@Service
public class ProdCommServiceImpl implements ProdCommService {
    @Resource
    private EshopProdCommDao prodCommDao;
    @Resource
    private EshopOrderDao eshopOrderDao;
    @Resource
    private EshopOrderItemDao eshopOrderItemDao;

    @Override
    public ListPageProdCommResponse listPageProdComm(ListPageProdCommRequest request) {
        IPage<EshopProdCommDO> result =
                prodCommDao.selectByConditions(request.getProdId(), request.getEvaluate(), request.getPage());
        EshopProdCommPageDTO data = MysqlUtils.buildPageData(EshopProdCommPageDTO.class,
                result, EshopProdCommConvert::convertToEshopProdCommDTO);
        return buildSuccessResponse(ListPageProdCommResponse.class, data);
    }

    @Override
    public ListUserProdCommResponse listUserProdComm(Long userId) {
        List<EshopOrderDO> orders = eshopOrderDao.selectFinishedOrderByUserId(userId);
        List<String> orderNumbers = orders.stream().map(EshopOrderDO::getOrderNumber).toList();
        List<EshopOrderItemDO> orderItems = eshopOrderItemDao.selectUnCommentOrder(orderNumbers);
        List<EshopOrderItemDTO> data = orderItems.stream()
                .map(EshopOrderConvert::convertToEshopProdOrderDTO)
                .toList();
        return buildSuccessResponse(ListUserProdCommResponse.class, data);
    }

    @Override
    @Transactional
    public BaseResponse addProdComm(AddProdCommRequest request) {
        EshopOrderItemDO item = eshopOrderItemDao.selectById(request.getData().getOrderItemId());
        if (item == null) {
            throw new EshopException("订单项不存在");
        }
        if (item.getCommStatus() == VALID.getStatus()) {
            throw new EshopException("订单项已评论");
        }
        EshopOrderDO order = eshopOrderDao.selectByOrderNumber(item.getOrderNumber());
        if (order == null) {
            throw new EshopException("订单不存在");
        }
        if (order.getStatus() != FINISHED.getStatus()) {
            throw new EshopException("订单未完成");
        }
        EshopProdCommDO entity = EshopProdCommConvert.convertToEshopProdCommDO(request.getData());
        MysqlUtils.buildEffectEntity(entity);
        long data = prodCommDao.insert(entity);
        if (data > 0) {
            EshopOrderItemDO update = new EshopOrderItemDO();
            update.setId(item.getId());
            update.setCommStatus(VALID.getStatus());
            eshopOrderItemDao.updateById(update);
        }
        return buildSuccessResponse(BaseResponse.class, String.valueOf(data));
    }

    @Override
    public GetProdCommCountResponse getProdCommCount(Long prodId) {
        List<Map<String, Object>> result = prodCommDao.selectCountGroupByEvaluation(prodId);
        EshopProdCommCountDTO.EshopProdCommCountDTOBuilder builder = EshopProdCommCountDTO.builder();
        if (CollectionUtils.isNotEmpty(result)) {
            result.forEach(item -> {
                int evaluate = (int) item.get("evaluate");
                if (GOOD.getType() == evaluate) {
                    builder.goodCount((long) item.get("count"));
                } else if (MEDIUM.getType() == evaluate) {
                    builder.mediumCount((long) item.get("count"));
                } else if (BAD.getType() == evaluate){
                    builder.badCount((long) item.get("count"));
                }
            });
        }
        EshopProdCommCountDTO data = builder.build();
        return buildSuccessResponse(GetProdCommCountResponse.class, data);
    }
}

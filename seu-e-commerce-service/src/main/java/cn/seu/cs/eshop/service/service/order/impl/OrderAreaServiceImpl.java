package cn.seu.cs.eshop.service.service.order.impl;

import cn.seu.cs.eshop.common.util.MysqlUtils;
import cn.seu.cs.eshop.service.cache.product.OrderAreaAddressCache;
import cn.seu.cs.eshop.service.convert.EshopOrderConvert;
import cn.seu.cs.eshop.service.dao.EshopUserAddressDao;
import cn.seu.cs.eshop.service.pojo.db.EshopAreaDO;
import cn.seu.cs.eshop.service.pojo.db.EshopUserAddressDO;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopAreaDTO;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopOrderAddressDTO;
import cn.seu.cs.eshop.service.sdk.order.address.req.GetOrderLevelAreaResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.GetUserAddressInfoResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.ListUserAddressResponse;
import cn.seu.cs.eshop.service.sdk.order.address.req.UpdateUserAddressRequest;
import cn.seu.cs.eshop.service.service.AbstractCrudService;
import cn.seu.cs.eshop.service.service.order.OrderAreaService;
import cs.seu.cs.eshop.common.sdk.entity.req.BaseResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static cs.seu.cs.eshop.common.sdk.util.ResponseBuilderUtils.buildSuccessResponse;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Service
public class OrderAreaServiceImpl extends AbstractCrudService<EshopOrderAddressDTO> implements OrderAreaService {
    @Resource
    OrderAreaAddressCache areaAddressCache;
    @Resource
    EshopUserAddressDao eshopUserAddressDao;

    @Override
    public long insert(EshopOrderAddressDTO data) {
        EshopUserAddressDO entity = EshopOrderConvert.convertToEshopUserAddressDO(data);
        MysqlUtils.buildEffectEntity(entity);
        eshopUserAddressDao.insert(entity);
        return entity.getId();
    }

    @Override
    public long delete(EshopOrderAddressDTO data) {
        eshopUserAddressDao.deleteById(data.getId());
        return data.getId();
    }

    @Override
    public long update(EshopOrderAddressDTO data) {
        EshopUserAddressDO entity = EshopOrderConvert.convertToEshopUserAddressDO(data);
        eshopUserAddressDao.updateById(entity);
        return entity.getId();
    }

    @Override
    public GetOrderLevelAreaResponse getOrderLevelArea(Long parentId) {
        List<EshopAreaDO> areas = areaAddressCache.getAreaLevelList(parentId);
        List<EshopAreaDTO> data = areas.stream()
                .map(EshopOrderConvert::convertToEshopAreaDTO)
                .toList();
        return buildSuccessResponse(GetOrderLevelAreaResponse.class, data);
    }

    @Override
    public GetUserAddressInfoResponse getUserAddressInfo(Long addressId) {
        EshopOrderAddressDTO data = areaAddressCache.getOrderAddress(addressId);
        return buildSuccessResponse(GetUserAddressInfoResponse.class, data);
    }

    @Override
    public ListUserAddressResponse listUserAddress(Long userId) {
        List<EshopOrderAddressDTO> data = areaAddressCache.getOrderAddressList(userId);
        return buildSuccessResponse(ListUserAddressResponse.class, data);
    }

    @Override
    public BaseResponse updateAddress(UpdateUserAddressRequest request) {
        areaAddressCache.deleteOrderAddressCache(request.getData().getUserId());
        long id = crudOperation(request);
        return buildSuccessResponse(BaseResponse.class, Long.toString(id));
    }
}

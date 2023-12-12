package cn.seu.cs.eshop.service.cache.order;

import cn.seu.cs.eshop.common.component.EshopRedisService;
import cn.seu.cs.eshop.service.convert.EshopOrderConvert;
import cn.seu.cs.eshop.service.dao.EshopAreaDao;
import cn.seu.cs.eshop.service.dao.EshopUserAddressDao;
import cn.seu.cs.eshop.service.pojo.db.EshopAreaDO;
import cn.seu.cs.eshop.service.pojo.db.EshopUserAddressDO;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopAreaDTO;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopOrderAddressDTO;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopOrderAreaDTO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Component
public class OrderAreaAddressCache {
    @Resource
    EshopRedisService eshopRedisService;
    @Resource
    EshopAreaDao eshopAreaDao;
    @Resource
    EshopUserAddressDao eshopUserAddressDao;

    public List<EshopAreaDO> getAreaLevelList(Long parentId) {
        List<EshopAreaDO> value = eshopRedisService.getListValue(parentId.toString(), orderAreaLevelCache, EshopAreaDO.class);
        if (CollectionUtils.isEmpty(value)) {
            List<EshopAreaDO> res = eshopAreaDao.selectByParentId(parentId);
            if (CollectionUtils.isNotEmpty(res)) {
                eshopRedisService.setObjectValue(parentId.toString(), res, orderAreaLevelCache);
            }
            return res;
        }
        return value;
    }

    public EshopOrderAreaDTO getOrderArea(Long areaId) {
        EshopOrderAreaDTO value = eshopRedisService.getObjectValue(areaId.toString(), orderAreaIdCache, EshopOrderAreaDTO.class);
        if (value == null) {
            EshopAreaDO area = eshopAreaDao.selectById(areaId);
            if (area == null) {
                return null;
            }
            EshopAreaDO city = eshopAreaDao.selectById(area.getParentId());
            if (city == null) {
                return null;
            }
            EshopAreaDO province = eshopAreaDao.selectById(city.getParentId());
            if (province == null) {
                return null;
            }
            value = EshopOrderAreaDTO.builder()
                    .areaId(areaId)
                    .area(EshopAreaDTO.builder()
                            .id(area.getId())
                            .areaName(area.getAreaName())
                            .build())
                    .city(EshopAreaDTO.builder()
                            .id(city.getId())
                            .areaName(city.getAreaName())
                            .build())
                    .province(EshopAreaDTO.builder()
                            .id(province.getId())
                            .areaName(province.getAreaName())
                            .build())
                    .build();
            eshopRedisService.setObjectValue(areaId.toString(), value, orderAreaIdCache);
        }
        return value;
    }

    public List<EshopOrderAddressDTO> getOrderAddressList(Long userId) {
        List<EshopOrderAddressDTO> value = eshopRedisService.getListValue(userId.toString(), orderUserAddressCache, EshopOrderAddressDTO.class);
        if (CollectionUtils.isEmpty(value)) {
            List<EshopUserAddressDO> res = eshopUserAddressDao.selectByUserId(userId);
            if (CollectionUtils.isNotEmpty(res)) {
                value = res.stream().
                        map(item -> {
                            EshopOrderAreaDTO area = getOrderArea(item.getAreaId());
                            return EshopOrderConvert.convertToEshopOrderAddressDTO(item, area);
                        })
                        .toList();
                eshopRedisService.setObjectValue(userId.toString(), value, orderUserAddressCache);
            }
        }
        return value;
    }

    public void deleteOrderAddressCache(Long userId) {
        eshopRedisService.removeValue(userId.toString(), orderUserAddressCache);
    }

    public EshopOrderAddressDTO getOrderAddress(Long addressId) {
        EshopOrderAddressDTO value = eshopRedisService.getObjectValue(addressId.toString(), orderUserAddressIdCache, EshopOrderAddressDTO.class);
        if (value == null) {
            EshopUserAddressDO address = eshopUserAddressDao.selectById(addressId);
            if (address == null) {
                return null;
            }
            EshopOrderAreaDTO area = getOrderArea(address.getAreaId());
            value = EshopOrderConvert.convertToEshopOrderAddressDTO(address, area);
            eshopRedisService.setObjectValue(addressId.toString(), value, orderUserAddressIdCache);
        }
        return value;
    }

    public void deleteOrderAddressIdCache(Long addressId) {
        eshopRedisService.removeValue(addressId.toString(), orderUserAddressIdCache);
    }
}

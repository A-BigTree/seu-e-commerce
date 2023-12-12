package cn.seu.cs.eshop.service.convert;

import cn.seu.cs.eshop.service.pojo.db.EshopAreaDO;
import cn.seu.cs.eshop.service.pojo.db.EshopUserAddressDO;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopAreaDTO;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopOrderAddressDTO;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopOrderAreaDTO;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
public class EshopOrderConvert {
    public static EshopOrderAddressDTO convertToEshopOrderAddressDTO(EshopUserAddressDO item, EshopOrderAreaDTO area) {
        if (item == null) {
            return null;
        }
        return EshopOrderAddressDTO.builder()
                .id(item.getId())
                .userId(item.getUserId())
                .receiver(item.getReceiver())
                .mobile(item.getMobile())
                .address(item.getAddress())
                .defaultAddress(item.getDefaultAddress())
                .area(area)
                .createTime(TimeUtils.convertString(item.getCreateTime(), TimeUtils.DATE_TIME_FORMAT))
                .build();
    }

    public static EshopUserAddressDO convertToEshopUserAddressDO(EshopOrderAddressDTO item) {
        if (item == null) {
            return null;
        }
        EshopUserAddressDO result = new EshopUserAddressDO();
        result.setUserId(item.getUserId());
        result.setReceiver(item.getReceiver());
        result.setMobile(item.getMobile());
        result.setAreaId(item.getArea().getAreaId());
        result.setAddress(item.getAddress());
        result.setDefaultAddress(item.getDefaultAddress());
        result.setId(item.getId() > 0 ? item.getId() : null);
        return result;
    }

    public static EshopAreaDTO convertToEshopAreaDTO(EshopAreaDO item) {
        if (item == null) {
            return null;
        }
        return EshopAreaDTO.builder()
                .id(item.getId())
                .areaName(item.getAreaName())
                .build();
    }
}

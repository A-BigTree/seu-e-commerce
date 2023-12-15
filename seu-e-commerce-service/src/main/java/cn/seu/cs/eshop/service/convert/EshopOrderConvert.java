package cn.seu.cs.eshop.service.convert;

import cn.seu.cs.eshop.service.pojo.db.*;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopAreaDTO;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopOrderAddressDTO;
import cn.seu.cs.eshop.service.sdk.order.address.dto.EshopOrderAreaDTO;
import cn.seu.cs.eshop.service.sdk.order.order.dto.EshopOrderDTO;
import cn.seu.cs.eshop.service.sdk.order.order.dto.EshopProdOrderDTO;
import cn.seu.cs.eshop.service.sdk.order.order.dto.OrderStatusChangeDTO;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;

import java.util.List;

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

    public static EshopOrderDTO convertToEshopOrderDTO(EshopOrderDO item,
                                                       EshopOrderAddressDTO address,
                                                       List<EshopProdOrderDTO> orderItems) {
        if (item == null) {
            return null;
        }
        return EshopOrderDTO.builder()
                .id(item.getId())
                .userId(item.getUserId())
                .shopId(item.getShopId())
                .address(address)
                .prodName(item.getProdName())
                .orderNumber(item.getOrderNumber())
                .remarks(item.getRemarks())
                .prodCount(item.getProdCount())
                .status(item.getStatus())
                .deliveryId(item.getDeliveryId())
                .deliveryCost(item.getDeliveryCost())
                .orderType(item.getOrderType())
                .closeType(item.getCloseType())
                .total(item.getTotal())
                .payType(item.getPayType())
                .payTime(item.getPayTime() != null ? TimeUtils.convertString(item.getPayTime(), TimeUtils.DATE_TIME_FORMAT) : null)
                .deliveryTime(item.getDeliveryTime() != null ? TimeUtils.convertString(item.getDeliveryTime(), TimeUtils.DATE_TIME_FORMAT) : null)
                .completeTime(item.getCompleteTime() != null ? TimeUtils.convertString(item.getCompleteTime(), TimeUtils.DATE_TIME_FORMAT) : null)
                .cancelTime(item.getCancelTime() != null ? TimeUtils.convertString(item.getCancelTime(), TimeUtils.DATE_TIME_FORMAT) : null)
                .updateTime(item.getUpdateTime() != null ? TimeUtils.convertString(item.getUpdateTime(), TimeUtils.DATE_TIME_FORMAT) : null)
                .createTime(item.getCreateTime() != null ? TimeUtils.convertString(item.getCreateTime(), TimeUtils.DATE_TIME_FORMAT) : null)
                .orderItems(orderItems)
                .build();
    }

    public static EshopOrderDO convertToEshopOrderDO(EshopOrderDTO item) {
        if (item == null) {
            return null;
        }
        EshopOrderDO result = new EshopOrderDO();
        result.setUserId(item.getUserId());
        result.setShopId(item.getShopId());
        result.setAddressId(item.getAddress().getId());
        result.setProdName(item.getProdName());
        result.setOrderNumber(item.getOrderNumber());
        result.setRemarks(item.getRemarks());
        result.setProdCount(item.getProdCount());
        result.setStatus(item.getStatus());
        result.setDeliveryId(item.getDeliveryId());
        result.setDeliveryCost(item.getDeliveryCost());
        result.setOrderType(item.getOrderType());
        result.setCloseType(item.getCloseType());
        result.setTotal(item.getTotal());
        result.setPayType(item.getPayType());
        result.setId(item.getId() > 0 ? item.getId() : null);
        return result;
    }

    public static EshopProdOrderDTO convertToEshopProdOrderDTO(EshopOrderItemDO item) {
        if (item == null) {
            return null;
        }
        return EshopProdOrderDTO.builder()
                .id(item.getId())
                .userId(item.getUserId())
                .prodId(item.getProdId())
                .prodName(item.getProdName())
                .pic(item.getPic())
                .skuId(item.getSkuId())
                .skuName(item.getSkuName())
                .price(item.getPrice())
                .shopId(item.getShopId())
                .prodCount(item.getProdCount())
                .status(item.getCommStatus())
                .createTime(TimeUtils.convertString(item.getCreateTime(), TimeUtils.DATE_TIME_FORMAT))
                .build();
    }

    public static EshopOrderItemDO convertToEshopOrderItemDO(EshopProdOrderDTO item, Long orderId, String orderNumber) {
        if (item == null) {
            return null;
        }
        EshopOrderItemDO result = new EshopOrderItemDO();
        result.setOrderId(orderId);
        result.setOrderNumber(orderNumber);
        result.setProdId(item.getProdId());
        result.setSkuId(item.getSkuId());
        result.setShopId(item.getShopId());
        result.setProdCount(item.getProdCount());
        result.setPrice(item.getPrice());
        result.setProdName(item.getProdName());
        result.setSkuName(item.getSkuName());
        result.setPic(item.getPic());
        result.setUserId(item.getUserId());
        result.setCommStatus(item.getStatus());
        result.setId(item.getId() > 0 ? item.getId() : null);
        return result;
    }

    public static OrderStatusChangeDTO convertToOrderStatusChangeDTO(EshopOrderReviewDO item) {
        if (item == null) {
            return null;
        }
        return OrderStatusChangeDTO.builder()
                .id(item.getId())
                .orderId(item.getOrderId())
                .modifier(item.getModifier())
                .remark(item.getRemark())
                .status(item.getStatus())
                .oldStatus(item.getOldStatus())
                .createTime(TimeUtils.convertString(item.getCreateTime(), TimeUtils.DATE_TIME_FORMAT))
                .build();
    }
}

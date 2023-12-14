package cn.seu.cs.eshop.service.convert;

import cn.seu.cs.eshop.service.pojo.db.EshopBasketDO;
import cn.seu.cs.eshop.service.sdk.product.basket.dto.EshopProdBasketDTO;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/14
 */
public class ProdBasketConvert {
    public static EshopProdBasketDTO convertToEshopProdBasketDTO(EshopBasketDO item) {
        if (item == null) {
            return null;
        }
        return EshopProdBasketDTO.builder()
                .id(item.getId())
                .userId(item.getUserId())
                .prodId(item.getProdId())
                .pic(item.getPic())
                .prodName(item.getProdName())
                .skuId(item.getSkuId())
                .skuName(item.getSkuName())
                .price(item.getPrice())
                .shopId(item.getShopId())
                .prodCount(item.getProdCount())
                .createTime(TimeUtils.convertString(item.getCreateTime(),TimeUtils.DATE_TIME_FORMAT))
                .build();
    }

    public static EshopBasketDO convertToEshopBasketDO(EshopProdBasketDTO item) {
        if (item == null) {
            return null;
        }
        EshopBasketDO result = new EshopBasketDO();
        result.setUserId(item.getUserId());
        result.setPic(item.getPic());
        result.setProdId(item.getProdId());
        result.setProdName(item.getProdName());
        result.setSkuId(item.getSkuId());
        result.setSkuName(item.getSkuName());
        result.setPrice(item.getPrice());
        result.setShopId(item.getShopId());
        result.setProdCount(item.getProdCount());
        result.setId(item.getId() > 0 ? item.getId() : null);
        return result;
    }
}

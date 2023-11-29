package cn.seu.cs.eshop.service.convert;

import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdReviewDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProdReviewDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProdSkuDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProdSkuPropDTO;
import cn.seu.cs.eshop.service.sdk.product.prod.dto.EshopProductDTO;
import cs.seu.cs.eshop.common.sdk.util.TimeUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
public class EshopProductConvert {
    public static EshopProdDO covertDO(EshopProductDTO prod) {
        String images = null;
        if (!CollectionUtils.isEmpty(prod.getImages())) {
            images = String.join(";", prod.getImages());
        }
        String parameters = "";
        if (!CollectionUtils.isEmpty(prod.getParameters())) {
            parameters = prod.getParameters().stream().
                    map(prop -> prop.getProp() + ":" + prop.getValue())
                    .collect(Collectors.joining(";"));
        }
        EshopProdDO entity = new EshopProdDO();
        entity.setId(prod.getId() > 0 ? prod.getId() : null);
        entity.setProdName(prod.getProdName());
        entity.setShopId(prod.getShopId());
        entity.setStatus(prod.getStatus());
        entity.setOriginPrice(prod.getOriginPrice());
        entity.setPrice(prod.getPrice());
        entity.setCategoryId(prod.getCategoryId());
        entity.setTotalStocks(prod.getTotalStocks());
        entity.setSoldNum(prod.getSoldNum());
        entity.setBrief(prod.getBrief());
        entity.setContent(prod.getContent());
        entity.setPic(prod.getPic());
        entity.setImages(images);
        entity.setDeliveryMode(prod.getDeliveryMode());
        entity.setDeliveryPrice(prod.getDeliveryPrice());
        entity.setUpdateTime(TimeUtils.getCurrentTime());
        entity.setParameters(parameters);
        return entity;
    }

    public static EshopProdSkuDO covertDO(EshopProdSkuDTO dto, Long prodId) {
        String properties = "";
        if (!CollectionUtils.isEmpty(dto.getProperties())) {
            properties = dto.getProperties().stream()
                    .map(prop -> prop.getProp() + ":" + prop.getValue())
                    .collect(Collectors.joining(";"));
        }
        EshopProdSkuDO sku = new EshopProdSkuDO();
        sku.setId(dto.getId() > 0 ? dto.getId() : null);
        sku.setProdId(dto.getProdId() > 0 ? dto.getProdId() : prodId);
        sku.setProperties(properties);
        sku.setSkuName(dto.getSkuName());
        sku.setSkuCode(dto.getSkuCode());
        sku.setPic(dto.getPic());
        sku.setOriginPrice(dto.getOriginPrice());
        sku.setPrice(dto.getPrice());
        sku.setStocks(dto.getStocks());
        return sku;
    }

    public static EshopProdSkuDTO covertDTO(EshopProdSkuDO entity) {
        List<EshopProdSkuPropDTO> props = Arrays.stream(entity.getProperties().split(";"))
                .map(pv -> {
                    String[] prop = pv.split(":");
                    return new EshopProdSkuPropDTO(prop[0], prop[1]);
                }).toList();
        return EshopProdSkuDTO.builder()
                .id(entity.getId())
                .prodId(entity.getProdId())
                .properties(props)
                .skuName(entity.getSkuName())
                .skuCode(entity.getSkuCode())
                .pic(entity.getPic())
                .originPrice(entity.getOriginPrice())
                .price(entity.getPrice())
                .stocks(entity.getStocks())
                .createTime(TimeUtils.convertString(entity.getCreateTime(), TimeUtils.DATE_TIME_FORMAT))
                .build();
    }

    public static EshopProductDTO covertDTO(EshopProdDO prod, List<EshopProdSkuDO> skus) {
        List<EshopProdSkuPropDTO> parameters = Arrays.stream(prod.getParameters().split(";"))
                .map(pv -> {
                    String[] prop = pv.split(":");
                    return new EshopProdSkuPropDTO(prop[0], prop[1]);
                }).toList();
        return EshopProductDTO.builder()
                .id(prod.getId())
                .prodName(prod.getProdName())
                .shopId(prod.getShopId())
                .status(prod.getStatus())
                .originPrice(prod.getOriginPrice())
                .price(prod.getPrice())
                .categoryId(prod.getCategoryId())
                .totalStocks(prod.getTotalStocks())
                .soldNum(prod.getSoldNum())
                .brief(prod.getBrief())
                .content(prod.getContent())
                .parameters(parameters)
                .pic(prod.getPic())
                .images(prod.getImages() != null ? Arrays.asList(prod.getImages().split(";")) : null)
                .deliveryMode(prod.getDeliveryMode())
                .deliveryPrice(prod.getDeliveryPrice())
                .updateTime(TimeUtils.convertString(prod.getUpdateTime(), TimeUtils.DATE_TIME_FORMAT))
                .createTime(TimeUtils.convertString(prod.getCreateTime(), TimeUtils.DATE_TIME_FORMAT))
                .skus(CollectionUtils.isEmpty(skus) ? null : skus.stream().map(EshopProductConvert::covertDTO).toList())
                .build();
    }

    public static EshopProdReviewDTO convertToEshopProdReviewDTO(EshopProdReviewDO item) {
        EshopProdReviewDTO result = new EshopProdReviewDTO();
        result.setModifier(item.getModifier());
        result.setRemark(item.getRemark());
        result.setStatus(item.getStatus());
        result.setCreateTime(TimeUtils.convertString(item.getCreateTime(), TimeUtils.DATE_TIME_FORMAT));
        return result;
    }

}

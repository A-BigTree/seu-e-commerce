package cn.seu.cs.eshop.service.convert;

import cn.seu.cs.eshop.common.util.TimeUtils;
import cn.seu.cs.eshop.service.pojo.db.ProductCategoryDO;
import cn.seu.cs.eshop.service.pojo.db.ProductCategoryPropDO;
import cn.seu.cs.eshop.service.pojo.db.ProductPropDO;
import cn.seu.cs.eshop.service.pojo.db.ProductPropValueDO;
import cn.seu.cs.eshop.service.sdk.product.category.dto.ProdCategoryDTO;
import cn.seu.cs.eshop.service.sdk.product.category.dto.ProdCategoryPropDTO;
import cn.seu.cs.eshop.service.sdk.product.category.dto.ProdPropDTO;
import cn.seu.cs.eshop.service.sdk.product.category.dto.ProdPropValueDTO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static cn.seu.cs.eshop.common.util.TimeUtils.DATE_TIME_FORMAT;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
public class ProductCategoryConvert {
    public static ProductCategoryDO convertDO(ProdCategoryDTO dto) {
        ProductCategoryDO entity = new ProductCategoryDO();
        entity.setShopId(dto.getShopId());
        entity.setCategoryName(dto.getCategoryName());
        entity.setCategoryLevel(dto.getLevel());
        entity.setStatus(dto.getStatus());
        entity.setCategoryLevel(dto.getLevel());
        entity.setParentId(dto.getParentId());
        return entity;
    }

    public static ProdCategoryDTO convertDTO(ProductCategoryDO category) {
        return ProdCategoryDTO.builder()
                .id(category.getId())
                .parentId(category.getParentId())
                .categoryName(category.getCategoryName())
                .shopId(category.getShopId())
                .level(category.getCategoryLevel())
                .status(category.getStatus())
                .createTime(TimeUtils.convertString(category.getCreateTime(), DATE_TIME_FORMAT))
                .build();
    }

    public static ProductPropDO convertDO(ProdPropDTO dto) {
        ProductPropDO entity = new ProductPropDO();
        entity.setId(dto.getId() > 0 ? dto.getId() : null);
        entity.setPropName(dto.getPropName());
        entity.setPropType(dto.getPropType());
        entity.setSelfAdd(dto.getSelfAdd());
        entity.setShopId(dto.getShopId());
        return entity;
    }

    public static ProductPropValueDO convertDO(ProdPropValueDTO dto, Long propId) {
        ProductPropValueDO entity = new ProductPropValueDO();
        entity.setId(dto.getId() > 0 ? dto.getId() : null);
        entity.setShopId(dto.getShopId());
        entity.setPropId(dto.getPropId() > 0 ? dto.getPropId() : propId);
        entity.setValueName(dto.getValueName());
        return entity;
    }

    public static ProdPropDTO convertDTO(ProductPropDO prop, List<ProductPropValueDO> values) {
        List<ProdPropValueDTO> propValue = new ArrayList<>();
        if (!CollectionUtils.isEmpty(values)) {
            propValue = values.stream()
                    .map(ProductCategoryConvert::convertDTO)
                    .toList();
        }
        return ProdPropDTO.builder()
                .id(prop.getId())
                .shopId(prop.getShopId())
                .propName(prop.getPropName())
                .propType(prop.getPropType())
                .selfAdd(prop.getSelfAdd())
                .createTime(TimeUtils.convertString(prop.getCreateTime(), DATE_TIME_FORMAT))
                .value(propValue)
                .build();
    }

    public static ProdPropValueDTO convertDTO(ProductPropValueDO value) {
        return ProdPropValueDTO.builder()
                .id(value.getId())
                .propId(value.getPropId())
                .shopId(value.getShopId())
                .valueName(value.getValueName())
                .createTime(TimeUtils.convertString(value.getCreateTime(), DATE_TIME_FORMAT))
                .build();
    }

    public static ProductCategoryPropDO convertDO(ProdCategoryPropDTO dto, Long shopId) {
        ProductCategoryPropDO entity = new ProductCategoryPropDO();
        entity.setId(dto.getId() > 0 ? dto.getId() : null);
        entity.setCategoryId(dto.getCategoryId());
        entity.setPropId(dto.getPropId());
        entity.setShopId(shopId);
        entity.setPropType(dto.getPropType());
        return entity;
    }

    public static ProdCategoryPropDTO convertDTO(ProductCategoryPropDO value, ProdPropDTO prop) {
        return ProdCategoryPropDTO.builder()
                .id(value.getId())
                .categoryId(value.getCategoryId())
                .propType(value.getPropType())
                .propId(value.getPropId())
                .prop(prop)
                .build();
    }
}

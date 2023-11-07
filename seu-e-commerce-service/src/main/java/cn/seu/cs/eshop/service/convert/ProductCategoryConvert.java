package cn.seu.cs.eshop.service.convert;

import cn.seu.cs.eshop.common.util.TimeUtils;
import cn.seu.cs.eshop.service.pojo.db.ProductCategoryDO;
import cn.seu.cs.eshop.service.sdk.product.dto.ProdCategoryDTO;

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
                .createTime(TimeUtils.convertString(category.getCreateTime(), TimeUtils.DATE_TIME_FORMAT))
                .build();
    }
}

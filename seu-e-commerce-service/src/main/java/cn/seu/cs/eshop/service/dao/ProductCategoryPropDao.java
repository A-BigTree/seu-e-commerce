package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.enums.product.ProdPropTypeEnum;
import cn.seu.cs.eshop.service.pojo.db.ProductCategoryPropDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import static cn.seu.cs.eshop.service.enums.product.ProdPropTypeEnum.DEFAULT;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/18
 */
@Mapper
public interface ProductCategoryPropDao extends MysqlBaseDao<ProductCategoryPropDO> {
    default List<ProductCategoryPropDO> selectByCategoryId(Long categoryId, Long shopId, Integer propType) {
        ProductCategoryPropDO entity = new ProductCategoryPropDO();
        entity.setCategoryId(categoryId);
        if (propType != DEFAULT.getType()) {
            entity.setPropType(propType);
        }
        return selectList(buildShopIds(shopId, new QueryWrapper<>(entity)));
    }
}

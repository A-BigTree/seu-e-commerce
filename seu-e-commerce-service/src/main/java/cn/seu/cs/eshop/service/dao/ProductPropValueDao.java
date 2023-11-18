package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.ProductPropValueDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
public interface ProductPropValueDao extends MysqlBaseDao<ProductPropValueDO> {
    default List<ProductPropValueDO> selectByPropId(Long propId, Long shopId) {
        ProductPropValueDO entity = new ProductPropValueDO();
        entity.setPropId(propId);
        QueryWrapper<ProductPropValueDO> wrapper = new QueryWrapper<>(entity);
        return selectList(buildShopIds(shopId, wrapper));
    }

    default void deleteByPropId(Long propId) {
        ProductPropValueDO entity = new ProductPropValueDO();
        entity.setPropId(propId);
        QueryWrapper<ProductPropValueDO> wrapper = new QueryWrapper<>(entity);
        delete(wrapper);
    }

    default List<ProductPropValueDO> selectByBatchPropIds(List<Long> propIds, Long shopId) {
        ProductPropValueDO entity = new ProductPropValueDO();
        QueryWrapper<ProductPropValueDO> wrapper = new QueryWrapper<>(entity);
        wrapper.in("prop_id", propIds);
        return selectList(buildShopIds(shopId, wrapper));
    }
}

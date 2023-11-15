package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.ProductCategoryDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.seu.cs.eshop.common.constants.CommonConstants.OFFICIAL_ID;
import static cn.seu.cs.eshop.common.enums.ProdStatusEnum.VALID;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/6
 */
@Mapper
public interface ProductCategoryDao extends MysqlBaseDao<ProductCategoryDO> {

    default List<ProductCategoryDO> selectByParentId(Long id) {
        ProductCategoryDO entity = new ProductCategoryDO();
        entity.setParentId(id);
        return selectList(new QueryWrapper<>(entity));
    }

    default IPage<ProductCategoryDO> selectPageByParentId(Long id, Long shopId, PageDTO page) {
        ProductCategoryDO entity = new ProductCategoryDO();
        entity.setParentId(id);
        QueryWrapper<ProductCategoryDO> wrapper = new QueryWrapper<>(entity);
        List<Long> ids = new ArrayList<>(Collections.singletonList(shopId));
        if (shopId != OFFICIAL_ID) {
            ids.add(OFFICIAL_ID);
        }
        wrapper.in("shop_id", ids);
        return selectPage(page, wrapper);
    }

    default List<ProductCategoryDO> selectPageByShopId(Long shopId) {
        ProductCategoryDO entity = new ProductCategoryDO();
        entity.setStatus(VALID.getStatus());
        QueryWrapper<ProductCategoryDO> wrapper = new QueryWrapper<>(entity);
        List<Long> ids = new ArrayList<>(Collections.singletonList(shopId));
        if (shopId != OFFICIAL_ID) {
            ids.add(OFFICIAL_ID);
        }
        wrapper.in("shop_id", ids);
        return selectList(wrapper);
    }
}
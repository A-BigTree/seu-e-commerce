package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.ProductPropDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
@Mapper
public interface ProductPropDao extends MysqlBaseDao<ProductPropDO> {
    default IPage<ProductPropDO> selectPageByShopId(Long shopId, PageDTO page) {
        return selectPage(page, buildShopIds(shopId, new QueryWrapper<>(new ProductPropDO())));
    }
}

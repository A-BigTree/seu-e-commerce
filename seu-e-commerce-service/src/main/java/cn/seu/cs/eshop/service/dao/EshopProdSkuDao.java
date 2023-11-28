package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Mapper
public interface EshopProdSkuDao extends MysqlBaseDao<EshopProdSkuDO> {

    default List<EshopProdSkuDO> selectByProdId(Long prodId) {
        EshopProdSkuDO entity = new EshopProdSkuDO();
        entity.setProdId(prodId);
        return selectList(new QueryWrapper<>(entity));
    }
}

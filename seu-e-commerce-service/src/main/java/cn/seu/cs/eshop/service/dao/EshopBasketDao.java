package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.EshopBasketDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Mapper
public interface EshopBasketDao extends MysqlBaseDao<EshopBasketDO> {
    default List<EshopBasketDO> selectByUserId(Long userId) {
        EshopBasketDO entity = new EshopBasketDO();
        entity.setUserId(userId);
        return selectList(new QueryWrapper<>(entity));
    }

    default EshopBasketDO getBasketByUserIdAndProdId(Long userId, Long prodId) {
        EshopBasketDO entity = new EshopBasketDO();
        entity.setUserId(userId);
        entity.setProdId(prodId);
        return selectOne(new QueryWrapper<>(entity));
    }
}

package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.EshopProdReviewDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Mapper
public interface EshopProdReviewDao extends MysqlBaseDao<EshopProdReviewDO> {
    default List<EshopProdReviewDO> selectByProdId(Long prodId) {
        EshopProdReviewDO entity = new EshopProdReviewDO();
        entity.setProdId(prodId);
        QueryWrapper<EshopProdReviewDO> wrapper = new QueryWrapper<>(entity);
        wrapper.orderByAsc("createTime");
        return selectList(wrapper);
    }
}

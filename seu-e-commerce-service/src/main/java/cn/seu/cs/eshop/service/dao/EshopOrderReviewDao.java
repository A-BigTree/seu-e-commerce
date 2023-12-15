package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.EshopOrderDO;
import cn.seu.cs.eshop.service.pojo.db.EshopOrderReviewDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@Mapper
public interface EshopOrderReviewDao extends MysqlBaseDao<EshopOrderReviewDO> {
    default List<EshopOrderReviewDO> selectByOrderId(Long orderId) {
        EshopOrderReviewDO entity = new EshopOrderReviewDO();
        entity.setOrderId(orderId);
        QueryWrapper<EshopOrderReviewDO> wrapper = new QueryWrapper<>(entity);
        wrapper.orderByDesc("create_time");
        return selectList(wrapper);
    }
}

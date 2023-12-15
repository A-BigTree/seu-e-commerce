package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.EshopOrderItemDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Mapper
public interface EshopOrderItemDao extends MysqlBaseDao<EshopOrderItemDO> {
    default List<EshopOrderItemDO> selectByOrderId(String orderNumber, Integer commStatus) {
        EshopOrderItemDO entity = new EshopOrderItemDO();
        entity.setOrderNumber(orderNumber);
        if (commStatus != null) {
            entity.setCommStatus(commStatus);
        }
        return selectList(new QueryWrapper<>(entity));
    }
}

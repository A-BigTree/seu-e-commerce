package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.EshopOrderItemDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

import static cs.seu.cs.eshop.common.sdk.enums.EshopStatusEnum.INVALID;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Mapper
public interface EshopOrderItemDao extends MysqlBaseDao<EshopOrderItemDO> {
    default List<EshopOrderItemDO> selectByOrderId(String orderNumber) {
        EshopOrderItemDO entity = new EshopOrderItemDO();
        entity.setOrderNumber(orderNumber);
        return selectList(new QueryWrapper<>(entity));
    }

    default List<EshopOrderItemDO> selectUnCommentOrder(List<String> orderNumbers) {
        if (CollectionUtils.isEmpty(orderNumbers)) {
            return new ArrayList<>();
        }
        EshopOrderItemDO entity = new EshopOrderItemDO();
        entity.setCommStatus(INVALID.getStatus());
        QueryWrapper<EshopOrderItemDO> wrapper = new QueryWrapper<>(entity);
        wrapper.in("order_number", orderNumbers);
        wrapper.orderByDesc("create_time");
        return selectList(wrapper);
    }
}

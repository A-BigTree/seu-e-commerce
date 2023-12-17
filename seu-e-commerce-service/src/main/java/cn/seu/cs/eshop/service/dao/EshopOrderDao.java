package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.enums.order.EshopOrderStatusEnum;
import cn.seu.cs.eshop.service.pojo.db.EshopOrderDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cs.seu.cs.eshop.common.sdk.enums.EshopStatusEnum.INVALID;
import static cs.seu.cs.eshop.common.sdk.enums.UserRoleEnum.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Mapper
public interface EshopOrderDao extends MysqlBaseDao<EshopOrderDO> {
    default IPage<EshopOrderDO> selectByCondition(String orderNumber,
                                                  Long userId,
                                                  Long shopId,
                                                  Integer roleType,
                                                  Integer status,
                                                  Integer closeType,
                                                  Integer payType,
                                                  PageDTO page) {
        EshopOrderDO entity = new EshopOrderDO();
        // 用户区分
        if (roleType == CUSTOMER.getValue()) {
            entity.setUserId(userId);
            entity.setUserDeleted(INVALID.getStatus());
        } else if (roleType == BUSINESS.getValue()) {
            entity.setShopId(shopId);
            if (userId != null && userId > 0) {
                entity.setUserId(userId);
            }
        } else {
            if (userId != null && userId > 0) {
                entity.setUserId(userId);
            }
            if (shopId != null && shopId > 0) {
                entity.setShopId(shopId);
            }
        }
        // 订单状态
        if (status != null && status != DEFAULT.getValue()) {
            entity.setStatus(status);
        }
        // 订单关闭类型
        if (closeType != null && closeType != DEFAULT.getValue()) {
            entity.setCloseType(closeType);
        }
        // 订单支付类型
        if (payType != null && payType != DEFAULT.getValue()) {
            entity.setPayType(payType);
        }
        // 订单号
        if (!StringUtils.isEmpty(orderNumber)) {
            entity.setOrderNumber(orderNumber);
        }
        QueryWrapper<EshopOrderDO> wrapper = new QueryWrapper<>(entity);
        wrapper.orderByDesc("create_time");
        return selectPage(page, wrapper);
    }

    default Map<Integer, Long> selectCountByStatus(Long userId) {
        EshopOrderDO entity = new EshopOrderDO();
        entity.setUserId(userId);
        QueryWrapper<EshopOrderDO> wrapper = new QueryWrapper<>(entity);
        wrapper.select("status as 'status', IFNULL(count(*), 0) as 'count'")
                .groupBy("status");
        List<Map<String, Object>> status = selectMaps(wrapper);
        Map<Integer, Long> result = new HashMap<>();
        for (EshopOrderStatusEnum value : EshopOrderStatusEnum.values()) {
            result.put(value.getStatus(), 0L);
        }
        for (Map<String, Object> map : status) {
            result.put((Integer) map.get("status"), ((Long) map.get("count")));
        }
        return result;
    }
}

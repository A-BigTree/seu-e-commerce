package cn.seu.cs.eshop.im.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.im.db.EshopImMessageDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import static cs.seu.cs.eshop.common.sdk.enums.EshopStatusEnum.VALID;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
@Mapper
public interface EshopImMessageDao extends MysqlBaseDao<EshopImMessageDO> {
    default List<EshopImMessageDO> selectAllMessageByUserId(Long userId) {
        QueryWrapper<EshopImMessageDO> query = new QueryWrapper<>();
        query.eq("from_user_id", userId).or().eq("to_user_id", userId);
        query.orderByDesc("create_time");
        return selectList(query);
    }

    default IPage<EshopImMessageDO> selectMessagePageByUserId(Long fromId, Long toId, PageDTO page) {
        QueryWrapper<EshopImMessageDO> query = new QueryWrapper<>();
        query.eq("from_user_id", fromId).eq("to_user_id", toId).or().eq("from_user_id", toId).eq("to_user_id", fromId);
        query.orderByDesc("create_time");
        return selectPage(page, query);
    }

    default void updateMessagesStatus(List<Long> ids) {
        QueryWrapper<EshopImMessageDO> query = new QueryWrapper<>();
        query.in("id", ids);
        EshopImMessageDO update = new EshopImMessageDO();
        update.setStatus(VALID.getStatus());
        update(update, query);
    }

    /**
     * 统计用户未读消息
     */
    default long countUnreadMessage(Long userId) {
        QueryWrapper<EshopImMessageDO> query = new QueryWrapper<>();
        query.eq("to_user_id", userId).eq("status", 0);
        return selectCount(query);
    }
}

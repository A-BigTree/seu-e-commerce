package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.EshopProdUserHistoryDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Mapper
public interface EshopProdUserHistoryDao extends MysqlBaseDao<EshopProdUserHistoryDO> {
    default long countByUserId(Long userId) {
        EshopProdUserHistoryDO entity = new EshopProdUserHistoryDO();
        entity.setUserId(userId);
        return selectCount(new QueryWrapper<>(entity));
    }

    default IPage<EshopProdUserHistoryDO> selectByUserId(Long userId, PageDTO page) {
        EshopProdUserHistoryDO entity = new EshopProdUserHistoryDO();
        entity.setUserId(userId);
        QueryWrapper<EshopProdUserHistoryDO> wrapper = new QueryWrapper<>(entity);
        wrapper.orderByDesc("create_time");
        return selectPage(page, wrapper);
    }

    default EshopProdUserHistoryDO selectByUserIdAndProdId(Long userId, Long prodId) {
        EshopProdUserHistoryDO entity = new EshopProdUserHistoryDO();
        entity.setUserId(userId);
        entity.setProdId(prodId);
        List<EshopProdUserHistoryDO> res = selectList(new QueryWrapper<>(entity));
        if (CollectionUtils.isEmpty(res)) {
            return null;
        } else {
            return res.get(0);
        }
    }
}

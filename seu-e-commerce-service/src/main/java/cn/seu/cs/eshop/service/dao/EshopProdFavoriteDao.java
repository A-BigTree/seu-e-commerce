package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.EshopProdFavoriteDO;
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
public interface EshopProdFavoriteDao extends MysqlBaseDao<EshopProdFavoriteDO> {
    default long countByUserId(Long userId) {
        EshopProdFavoriteDO entity = new EshopProdFavoriteDO();
        entity.setUserId(userId);
        return selectCount(new QueryWrapper<>(entity));
    }

    default IPage<EshopProdFavoriteDO> selectPageByUserId(Long userId, PageDTO page) {
        EshopProdFavoriteDO entity = new EshopProdFavoriteDO();
        entity.setUserId(userId);
        return selectPage(page, new QueryWrapper<>(entity));
    }

    default Long getFavoriteId(Long userId, Long prodId) {
        EshopProdFavoriteDO entity = new EshopProdFavoriteDO();
        entity.setUserId(userId);
        entity.setProdId(prodId);
        List<EshopProdFavoriteDO> res = selectList(new QueryWrapper<>(entity));
        if (CollectionUtils.isEmpty(res)) {
            return 0L;
        } else {
            return res.stream().map(EshopProdFavoriteDO::getId).findFirst().orElse(0L);
        }
    }
}

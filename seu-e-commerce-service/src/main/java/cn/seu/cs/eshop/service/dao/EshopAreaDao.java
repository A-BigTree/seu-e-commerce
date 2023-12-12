package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.EshopAreaDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Mapper
public interface EshopAreaDao extends MysqlBaseDao<EshopAreaDO> {
    default List<EshopAreaDO> selectByParentId(Long parentId) {
        EshopAreaDO entity = new EshopAreaDO();
        entity.setParentId(parentId);
        return selectList(new QueryWrapper<>(entity));
    }
}

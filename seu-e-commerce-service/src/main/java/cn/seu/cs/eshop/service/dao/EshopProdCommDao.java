package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.EshopProdCommDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Mapper
public interface EshopProdCommDao extends MysqlBaseDao<EshopProdCommDO> {
    default List<Map<String, Object>> selectCountGroupByEvaluation(Long prodId) {
        EshopProdCommDO entity = new EshopProdCommDO();
        entity.setProdId(prodId);
        QueryWrapper<EshopProdCommDO> wrapper = new QueryWrapper<>(entity);
        wrapper.select("evaluate", "count(*) as count").groupBy("evaluate");
        return selectMaps(wrapper);
    }
}

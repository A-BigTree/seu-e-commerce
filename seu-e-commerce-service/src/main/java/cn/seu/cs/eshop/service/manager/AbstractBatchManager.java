package cn.seu.cs.eshop.service.manager;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.common.util.MysqlUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static cn.seu.cs.eshop.common.enums.CrudOperationTypeEnum.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
public abstract class AbstractBatchManager<DO extends MysqlBaseDO, Dao extends MysqlBaseDao<DO>>
        extends ServiceImpl<Dao, DO> implements IService<DO> {

    public abstract void deleteBatchByIds(List<DO> entities);

    @Transactional
    public void updateDiffEntities(Map<Integer, List<DO>> entityMap) {
        // 批量插入
        if (!CollectionUtils.isEmpty(entityMap.get(INSERT.getType()))) {
            this.saveBatch(entityMap.get(INSERT.getType()));
        }
        // 批量更新
        if (!CollectionUtils.isEmpty(entityMap.get(UPDATE.getType()))) {
            this.updateBatchById(entityMap.get(UPDATE.getType()));
        }
        // 批量删除
        if (!CollectionUtils.isEmpty(entityMap.get(DELETE.getType()))) {
            this.deleteBatchByIds(entityMap.get(DELETE.getType()));
        }
    }

    public Map<Integer, List<DO>> listDiffEntities(List<DO> newDO, List<DO> originDO) {
        List<DO> insert = new ArrayList<>();
        Set<Long> exist = new HashSet<>();
        List<DO> update = newDO.stream()
                .filter(value -> {
                    if (value.getId() > 0) {
                        exist.add(value.getId());
                        return true;
                    } else {
                        insert.add(MysqlUtils.buildEffectEntity(value));
                        return false;
                    }
                }).toList();
        List<DO> delete = new ArrayList<>();
        if (!CollectionUtils.isEmpty(originDO)) {
            delete = originDO.stream()
                    .filter(value -> !exist.contains(value.getId()))
                    .toList();
        }
        Map<Integer, List<DO>> result = new HashMap<>();
        result.put(INSERT.getType(), insert);
        result.put(UPDATE.getType(), update);
        result.put(DELETE.getType(), delete);
        return result;
    }

    @Transactional
    public void updateDiffEntities(List<DO> newDO, List<DO> originDO) {
        this.updateDiffEntities(listDiffEntities(newDO, originDO));
    }

}

package cn.seu.cs.eshop.common.entity.db;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import static cn.seu.cs.eshop.common.enums.DeletedStateEnum.DELETED;
import static cn.seu.cs.eshop.common.enums.DeletedStateEnum.UNDELETED;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
public interface MysqlBaseDao<T extends MysqlBaseDO> extends BaseMapper<T> {
    default T selectById(Long id) {
        QueryWrapper<T> entity = new QueryWrapper<>();
        entity.eq("id", id);
        entity.eq("deleted", UNDELETED.getState());
        return selectOne(entity);
    }

    default List<T> selectByIds(Collection<Long> idList) {
        QueryWrapper<T> entity = new QueryWrapper<>();
        entity.in("id", idList);
        entity.eq("deleted", UNDELETED.getState());
        return selectList(entity);
    }

    /**
     * 软删除
     */
    @SuppressWarnings("unchecked")
    default int softDeleteById(Long id) throws Exception {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T entity = clazz.getDeclaredConstructor().newInstance();
        entity.setId(id);
        entity.setDeleted(DELETED.getState());
        return updateById(entity);
    }
}

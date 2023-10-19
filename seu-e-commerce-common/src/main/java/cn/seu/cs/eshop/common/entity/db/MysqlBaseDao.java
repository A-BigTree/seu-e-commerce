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
    default int deletePhysicallyById(Long id) {
        return deleteById(id);
    }
}

package cn.seu.cs.eshop.common.util;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import lombok.extern.slf4j.Slf4j;

import static cn.seu.cs.eshop.common.enums.DeletedStateEnum.UNDELETED;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
@Slf4j
public class MysqlUtils {
    public static <T extends MysqlBaseDO> T buildEffectEntity(T entity) {
        entity.setCreateTime(System.currentTimeMillis());
        entity.setDeleted(UNDELETED.getState());
        return entity;
    }
}

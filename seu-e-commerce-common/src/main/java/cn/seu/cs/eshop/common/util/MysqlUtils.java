package cn.seu.cs.eshop.common.util;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageInterface;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static cs.seu.cs.eshop.common.sdk.enums.DeletedStateEnum.UNDELETED;

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

    public static PageDTO transToPage(IPage<? extends MysqlBaseDO> page) {
        return PageDTO.builder()
                .pageNum(page.getCurrent())
                .pageSize(page.getSize())
                .total(page.getTotal())
                .pageSum(page.getPages())
                .build();
    }

    public static <T extends MysqlBaseDO>IPage<T> transToPage(PageDTO pageDTO) {
        return new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
    }

    public static <INFO extends Serializable,
            DTO extends PageInterface<INFO>,
            DO extends MysqlBaseDO>
    DTO buildPageData(Class<DTO> clazz,  IPage<DO> page, Function<DO, INFO> map) {
        DTO result = null;
        try {
            result= clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("build fail. e:", e);
            return null;
        }
        result.setPage(transToPage(page));
        List<INFO> records = new ArrayList<>();
        for (DO mysqlDO : page.getRecords()) {
            records.add(map.apply(mysqlDO));
        }
        result.setRecords(records);
        return result;
    }
}

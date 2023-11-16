package cn.seu.cs.eshop.common.entity.db;

import cn.seu.cs.eshop.common.util.MysqlUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.seu.cs.eshop.common.constants.CommonConstants.OFFICIAL_ID;


/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
public interface MysqlBaseDao<T extends MysqlBaseDO> extends BaseMapper<T> {
    default int deletePhysicallyById(Long id) {
        return deleteById(id);
    }

    default IPage<T> selectPage(PageDTO pageDTO, Wrapper<T> wrapper) {
        IPage<T> page = MysqlUtils.transToPage(pageDTO);
        return selectPage(page, wrapper);
    }

    default QueryWrapper<T> buildShopIds(Long shopId, QueryWrapper<T> wrapper) {
        List<Long> ids = new ArrayList<>(Collections.singletonList(shopId));
        if (shopId != OFFICIAL_ID) {
            ids.add(OFFICIAL_ID);
        }
        wrapper.in("shop_id", ids);
        return wrapper;
    }
}

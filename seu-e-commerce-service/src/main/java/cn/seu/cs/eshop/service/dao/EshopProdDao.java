package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.enums.product.ProdStatusEnum;
import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import static cn.seu.cs.eshop.common.constants.CommonConstants.OFFICIAL_ID;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Mapper
public interface EshopProdDao extends MysqlBaseDao<EshopProdDO> {

    default IPage<EshopProdDO> selectByConditions(Long id, Long shopId, String prodName, Integer status, Long categoryId, PageDTO page) {
        EshopProdDO entity = new EshopProdDO();
        if (id > 0) {
            entity.setId(id);
        }
        if (shopId != OFFICIAL_ID) {
            entity.setShopId(shopId);
        }
        if (status != ProdStatusEnum.DEFAULT.getStatus()) {
            entity.setStatus(status);
        }
        if (categoryId != -1) {
            entity.setCategoryId(categoryId);
        }
        QueryWrapper<EshopProdDO> wrapper = new QueryWrapper<>(entity);
        if (!StringUtils.isEmpty(prodName)) {
            wrapper.like("prod_name", prodName);
        }
        return selectPage(page, wrapper);
    }
}

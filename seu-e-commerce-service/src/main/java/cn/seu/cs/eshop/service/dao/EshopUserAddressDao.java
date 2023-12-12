package cn.seu.cs.eshop.service.dao;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDao;
import cn.seu.cs.eshop.service.pojo.db.EshopUserAddressDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import static cs.seu.cs.eshop.common.sdk.enums.EshopStatusEnum.VALID;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Mapper
public interface EshopUserAddressDao extends MysqlBaseDao<EshopUserAddressDO> {
    default List<EshopUserAddressDO> selectByUserId(Long userId) {
        EshopUserAddressDO entity = new EshopUserAddressDO();
        entity.setUserId(userId);
        return selectList(new QueryWrapper<>(entity));
    }

    default List<EshopUserAddressDO> selectByUserIdAndDefault(Long userId) {
        EshopUserAddressDO entity = new EshopUserAddressDO();
        entity.setUserId(userId);
        entity.setDefaultAddress(VALID.getStatus());
        return selectList(new QueryWrapper<>(entity));
    }
}

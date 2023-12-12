package cn.seu.cs.eshop.service.cache.product;

import cn.seu.cs.eshop.common.component.EshopRedisService;
import cn.seu.cs.eshop.service.dao.EshopAreaDao;
import cn.seu.cs.eshop.service.pojo.db.EshopAreaDO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static cn.seu.cs.eshop.service.redis.ServiceRedisConfEnum.orderAreaLevelCache;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@Component
public class OrderAreaCache {
    @Resource
    EshopRedisService eshopRedisService;
    @Resource
    EshopAreaDao eshopAreaDao;

    public List<EshopAreaDO> getAreaLevelList(Long parentId) {
        List<EshopAreaDO> value = eshopRedisService.getListValue(parentId.toString(), orderAreaLevelCache, EshopAreaDO.class);
        if (CollectionUtils.isEmpty(value)) {
            List<EshopAreaDO> res = eshopAreaDao.selectByParentId(parentId);
            if (CollectionUtils.isNotEmpty(res)) {
                eshopRedisService.setObjectValue(parentId.toString(), res, orderAreaLevelCache);
            }
            return res;
        }
        return value;
    }
}

package cn.seu.cs.eshop.service.manager.product;

import cn.seu.cs.eshop.service.dao.EshopProdSkuDao;
import cn.seu.cs.eshop.service.manager.AbstractBatchManager;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import cn.seu.cs.eshop.service.redisson.ProdSkuCodeGenerateService;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Component
public class EshopProdSkuManager extends AbstractBatchManager<EshopProdSkuDO, EshopProdSkuDao> {
    @Resource
    EshopProdSkuDao eshopProdSkuDao;
    @Resource
    ProdSkuCodeGenerateService prodSkuCodeGenerateService;

    @Override
    public void deleteBatchByIds(List<EshopProdSkuDO> entities) {
        List<Long> ids = entities.stream().map(EshopProdSkuDO::getId).toList();
        if (!CollectionUtils.isEmpty(ids)) {
            eshopProdSkuDao.deleteBatchIds(ids);
        }
    }
    
    @Override
    @Transactional
    public void saveEntities(List<EshopProdSkuDO> entities) {
        entities.forEach(value -> value.setSkuCode(prodSkuCodeGenerateService.generateSkuCode()));
        this.saveBatch(entities);
    }
}

package cn.seu.cs.eshop.service.manager.product;

import cn.seu.cs.eshop.service.dao.ProductCategoryPropDao;
import cn.seu.cs.eshop.service.manager.AbstractBatchManager;
import cn.seu.cs.eshop.service.pojo.db.ProductCategoryPropDO;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/18
 */
@Component
public class ProdCategoryPropManager extends AbstractBatchManager<ProductCategoryPropDO, ProductCategoryPropDao> {
    @Resource
    ProductCategoryPropDao productCategoryPropDao;

    @Override
    public void deleteBatchByIds(List<ProductCategoryPropDO> entities) {
        List<Long> ids = entities.stream()
                .map(ProductCategoryPropDO::getId)
                .toList();
        if (!CollectionUtils.isEmpty(ids)) {
            productCategoryPropDao.deleteBatchIds(ids);
        }
    }
}

package cn.seu.cs.eshop.service.manager.product;

import cn.seu.cs.eshop.service.dao.ProductPropValueDao;
import cn.seu.cs.eshop.service.manager.AbstractBatchManager;
import cn.seu.cs.eshop.service.pojo.db.ProductPropValueDO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/15
 */
@Component
public class ProdPropValueManager
        extends AbstractBatchManager<ProductPropValueDO, ProductPropValueDao> {

    @Resource
    ProductPropValueDao productPropValueDao;

    @Override
    public void deleteBatchByIds(List<ProductPropValueDO> entities) {
        List<Long> ids = entities.stream()
                .map(ProductPropValueDO::getId)
                .toList();
        productPropValueDao.deleteBatchIds(ids);
    }
}

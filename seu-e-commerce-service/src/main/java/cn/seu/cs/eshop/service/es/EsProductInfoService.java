package cn.seu.cs.eshop.service.es;

import cn.seu.cs.eshop.common.entity.elastic.AbstractElasticService;
import cn.seu.cs.eshop.service.pojo.es.EshopProductInfoIndex;
import org.springframework.stereotype.Service;

import static cn.seu.cs.eshop.service.constants.ElasticConstants.ES_PRODUCT_INFO_INDEX;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/4
 */
@Service
public class EsProductInfoService extends AbstractElasticService<EshopProductInfoIndex> {
    
    @Override
    public String getIndex() {
        return ES_PRODUCT_INFO_INDEX;
    }
}

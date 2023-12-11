package cn.seu.cs.eshop.service.es;

import cn.seu.cs.eshop.common.entity.elastic.AbstractElasticService;
import cn.seu.cs.eshop.service.pojo.es.EshopProductInfoIndex;
import co.elastic.clients.elasticsearch._types.aggregations.AggregateBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.seu.cs.eshop.service.constants.ElasticConstants.ES_PRODUCT_INFO_INDEX;
import static cn.seu.cs.eshop.service.constants.FieldConstants.*;
import static cn.seu.cs.eshop.service.enums.product.ProdStatusEnum.PUBLISHED;

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

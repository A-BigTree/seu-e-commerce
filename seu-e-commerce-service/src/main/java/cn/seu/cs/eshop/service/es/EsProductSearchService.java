package cn.seu.cs.eshop.service.es;

import cn.seu.cs.eshop.common.entity.elastic.AbstractElasticService;
import cn.seu.cs.eshop.service.pojo.es.ProductEsIndex;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;
import cs.seu.cs.eshop.common.sdk.entity.dto.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.seu.cs.eshop.service.constants.ElasticConstants.ES_PRODUCT_SEARCH_INDEX;
import static cn.seu.cs.eshop.service.constants.FieldConstants.*;
import static cn.seu.cs.eshop.service.enums.product.ProdStatusEnum.PUBLISHED;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@Service
public class EsProductSearchService extends AbstractElasticService<ProductEsIndex> {

    public List<ProductEsIndex> searchByConditions(String prodName, String orderBy, String order, PageDTO page) {
        BoolQuery.Builder queryBuilder = QueryBuilders.bool();
        // 模糊匹配
        if (!StringUtils.isEmpty(prodName)) {
            queryBuilder.must(fn -> fn.multiMatch(match ->
                    match.fields(PROD_NAME, PROD_BRIEF, PROD_CONTENT, PROD_PARAMETERS, PROD_SKU_PROPERTIES)
                            .query(prodName)));
        }
        // 已上架商品
        queryBuilder.must(fn -> fn.term(term -> term.field(PROD_STATUS).value(PUBLISHED.getStatus())));
        // 总库存大于0
        RangeQuery rangeQuery = RangeQuery.of(fn -> fn.field(PROD_STOCK).gt(JsonData.of(0)));
        queryBuilder.must(rangeQuery._toQuery());
        // 构建查询语句
        NativeQueryBuilder query = NativeQuery.builder()
                .withQuery(queryBuilder.build()._toQuery())
                // 分页方案
                .withPageable(PageRequest.of((int) (page.getPageNum() - 1), (int) page.getPageSize()));
        // 排序方案
        if (!StringUtils.isEmpty(orderBy)) {
            query.withSort(fn -> fn.field(ord -> ord.field(orderBy).order(order.equals("asc") ? SortOrder.Asc : SortOrder.Desc)));
        }
        return search(query.build());
    }

    @Override
    public String getIndex() {
        return ES_PRODUCT_SEARCH_INDEX;
    }
}

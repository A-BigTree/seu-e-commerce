package cn.seu.cs.eshop.common.entity.elastic;

import jakarta.annotation.Resource;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Component
public abstract class AbstractElasticService<INDEX extends EsBaseIndex> {
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    public String save(INDEX index) {
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withIndex(getIndex())
                .withId(index.getDocId())
                .withOpType(IndexQuery.OpType.INDEX)
                .withObject(index)
                .build();
        return elasticsearchTemplate.index(indexQuery, IndexCoordinates.of(getIndex()));
    }

    public abstract String getIndex();
}

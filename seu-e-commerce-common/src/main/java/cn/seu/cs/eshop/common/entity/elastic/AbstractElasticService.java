package cn.seu.cs.eshop.common.entity.elastic;

import jakarta.annotation.Resource;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.List;

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

    @SuppressWarnings("unchecked")
    public List<INDEX> search(Query query) {
        SearchHits<INDEX> result = elasticsearchTemplate.search(query,
                (Class<INDEX>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0],
                IndexCoordinates.of(getIndex()));
        return result.get()
                .map(SearchHit::getContent)
                .toList();
    }

    public abstract String getIndex();
}

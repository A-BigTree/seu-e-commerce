package cn.seu.cs.eshop.common.entity.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
public interface EsBaseRepository<INDEX extends EsBaseIndex> extends ElasticsearchRepository<INDEX, Long> {
}

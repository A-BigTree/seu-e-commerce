package cn.seu.cs.eshop.common.entity.elastic;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Data
public class EsBaseIndex implements Serializable {
    @Id
    private String docId;

    @Field(type = FieldType.Long)
    private Long createTime;

}

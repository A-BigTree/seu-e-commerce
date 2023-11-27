package cn.seu.cs.eshop.common.entity.elastic;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Data
public abstract class EsBaseIndex implements Serializable {
    private String id;
    private Long createTime;

    public abstract String getDocId();

}

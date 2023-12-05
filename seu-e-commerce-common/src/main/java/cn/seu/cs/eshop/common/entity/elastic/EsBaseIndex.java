package cn.seu.cs.eshop.common.entity.elastic;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */

public interface EsBaseIndex extends Serializable {

    String getDocId();

}

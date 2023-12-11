package cn.seu.cs.eshop.service.pojo.es;

import cn.seu.cs.eshop.common.entity.elastic.EsBaseIndex;
import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@Data
public class ProductEsIndex implements EsBaseIndex {
    private Long id;
    private String prodName; // 分词
    private Long shopId;
    private Long originPrice;
    private Long price;
    private Long categoryId;
    private Integer totalStocks;
    private Integer soldNum;
    private String brief;   // 分词
    private String content;  // 分词
    private String parameters; // 分词
    private String pic;
    private String images;
    private Integer deliveryMode;
    private Integer deliveryPrice;
    private Long updateTime;
    private Long createTime;
    private Integer status;
    // SKU信息
    private String skuProperties;

    @Override
    public String getDocId() {
        return shopId + "_" + id;
    }
}

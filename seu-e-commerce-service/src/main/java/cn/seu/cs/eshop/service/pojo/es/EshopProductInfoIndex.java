package cn.seu.cs.eshop.service.pojo.es;

import cn.seu.cs.eshop.common.entity.elastic.EsBaseIndex;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/4
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EshopProductInfoIndex extends EsBaseIndex {
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
    // SKU信息
    private Long skuId;
    private String properties;  // 分词
    private String skuName;    // 分词
    private String skuCode;
    private String skuPic;
    private Long skuOriginPrice;
    private Long skuPrice;
    private Integer skuStocks;

    @Override
    public String getDocId() {
        return id + "_" + skuId;
    }
}

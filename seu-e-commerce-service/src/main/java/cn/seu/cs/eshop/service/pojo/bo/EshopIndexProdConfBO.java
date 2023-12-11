package cn.seu.cs.eshop.service.pojo.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/11
 */
@Data
public class EshopIndexProdConfBO implements Serializable {
    private Integer style;
    private Long id;
    private String title;
    private Integer limit;
    private String orderBy;
    private String order;
}

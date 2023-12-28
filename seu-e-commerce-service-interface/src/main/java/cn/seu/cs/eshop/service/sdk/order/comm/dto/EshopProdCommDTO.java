package cn.seu.cs.eshop.service.sdk.order.comm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopProdCommDTO implements Serializable {
    private Long id;
    private Long userId;
    private String userName;
    private String headPic;
    private Long prodId;
    private Long orderItemId;
    private String content;
    private Integer score;
    private Integer status;
    private List<String> images;
    private Integer commType;
    private Integer evaluate;
    private String createTime;
}

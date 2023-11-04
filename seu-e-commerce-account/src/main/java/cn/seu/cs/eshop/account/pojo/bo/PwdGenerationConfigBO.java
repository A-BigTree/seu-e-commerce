package cn.seu.cs.eshop.account.pojo.bo;

import lombok.Data;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/4
 */
@Data
public class PwdGenerationConfigBO {
    private String symbols;
    private Integer length;
}

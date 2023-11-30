package cn.seu.cs.eshop.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/30
 */
@Data
public class ProdReviewEmailBO implements Serializable {
    private String subject;
    private String prefix;
    private Map<Integer, String> text;
    private String suffix;
}

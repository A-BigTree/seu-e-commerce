package cn.seu.cs.eshop.account.pojo.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/4
 */
@Data
public class ReviewEmailContextBO implements Serializable {
    private String subject;
    private String prefix;
    private Map<Integer, String> text;
    private String suffix;
}

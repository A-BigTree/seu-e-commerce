package cs.seu.cs.eshop.common.sdk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EshopImMessageDTO implements Serializable {
    private Long id;
    private Integer msgType;
    private Long createTime;
    /**
     * 1 文本消息
     * {
     *     "text": ""
     * }
     * <p>
     *
     * 2 卡片消息
     * {
     *     "title": "",
     *     "desc": "",
     *     "pic": "",
     *     "url": ""
     * }
     */
    private String content;
}

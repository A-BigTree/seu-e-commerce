package cn.seu.cs.eshop.im.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("eshop_im_message")
public class EshopImMessageDO extends MysqlBaseDO {
    private Integer msgType;
    private Long fromUserId;
    private Long toUserId;
    private Integer status;
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

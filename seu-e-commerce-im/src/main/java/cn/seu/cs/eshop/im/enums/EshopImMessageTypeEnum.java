package cn.seu.cs.eshop.im.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/29
 */
@AllArgsConstructor
@Getter
public enum EshopImMessageTypeEnum {
    /**
     * 心跳
     */
    HEARTBEAT(1),
    /**
     * 登录信息
     */
    LOGIN(2),
    /**
     * Session状态信息
     */
    SESSION(3),
    /**
     * 文本消息
     */
    TEXT(4),
    /**
     * 卡片消息
     */
    CARD(5),

    ;
    final int type;
}

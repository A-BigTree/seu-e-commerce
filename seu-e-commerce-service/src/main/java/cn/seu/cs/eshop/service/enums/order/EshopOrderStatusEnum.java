package cn.seu.cs.eshop.service.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/15
 */
@AllArgsConstructor
@Getter
public enum EshopOrderStatusEnum {
    /**
     * 未支付
     */
    UNPAID(1),
    /**
     * 代发货
     */
    UNDELIVERED(2),
    /**
     * 待收货
     */
    UNRECEIVED(3),
    /**
     * 待评价
     */
    UNCOMMENTED(4),
    /**
     * 已完成
     */
    FINISHED(5),
    /**
     * 已取消
     */
    CANCELED(6),
    ;
    final int status;
}

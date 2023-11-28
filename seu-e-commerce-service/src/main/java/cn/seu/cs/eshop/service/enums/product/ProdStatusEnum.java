package cn.seu.cs.eshop.service.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/28
 */
@Getter
@AllArgsConstructor
public enum ProdStatusEnum {
    DEFAULT(-1, ""),

    TO_BE_REVIEWED(0, "待审核"),

    REVIEW_FAILED(1, "审核失败"),

    REVIEW_SUCCESS(2, "审核成功"),

    PUBLISHED(3, "已上架"),

    UN_PUBLISH(4, "已下架")
    ;
    final int status;
    final String text;
}

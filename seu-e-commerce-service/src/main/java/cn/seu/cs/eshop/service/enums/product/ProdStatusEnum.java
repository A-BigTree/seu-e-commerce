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
    DEFAULT(-1),

    TO_BE_REVIEWED(0),

    REVIEW_FAILED(1),

    REVIEW_SUCCESS(2),

    PUBLISHED(3),

    UN_PUBLISH(4)
    ;
    final int status;
}

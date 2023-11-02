package cs.seu.cs.eshop.common.sdk.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDTO implements Serializable {
    private long pageNum;
    private long pageSize;
    private long pageSum;
    private long total;
}

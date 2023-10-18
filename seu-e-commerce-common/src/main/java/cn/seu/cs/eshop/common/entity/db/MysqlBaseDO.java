package cn.seu.cs.eshop.common.entity.db;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@Data
public class MysqlBaseDO implements Serializable {
    @TableId
    private Long id;

    private Long createTime;

    private Integer deleted;
}

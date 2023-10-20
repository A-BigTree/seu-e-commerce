package cn.seu.cs.eshop.common.entity.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
@Data
public class MysqlBaseDO implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long createTime;

    @TableLogic
    private Integer deleted;
}

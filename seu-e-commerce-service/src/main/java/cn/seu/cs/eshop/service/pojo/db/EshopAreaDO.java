package cn.seu.cs.eshop.service.pojo.db;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/12/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("eshop_area")
public class EshopAreaDO extends MysqlBaseDO {
    private String areaName;
    private Long parentId;
    private Integer level;
}

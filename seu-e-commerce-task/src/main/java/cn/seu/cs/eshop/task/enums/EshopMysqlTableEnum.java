package cn.seu.cs.eshop.task.enums;

import cn.seu.cs.eshop.common.entity.db.MysqlBaseDO;
import cn.seu.cs.eshop.common.util.JsonUtils;
import cn.seu.cs.eshop.service.pojo.db.EshopOrderDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdDO;
import cn.seu.cs.eshop.service.pojo.db.EshopProdSkuDO;
import cn.seu.cs.eshop.service.pojo.db.ProductPropValueDO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static cn.seu.cs.eshop.task.constants.EshopDatabaseConstants.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/27
 */
@Getter
@AllArgsConstructor
public enum EshopMysqlTableEnum {
    DEFAULT("", "", MysqlBaseDO.class),
    PROD_PROP_VALUE(SERVICE_DATABASE, "prod_prop_value", ProductPropValueDO.class),
    ESHOP_PROD(SERVICE_DATABASE, "eshop_prod", EshopProdDO.class),
    ESHOP_PROD_SKU(SERVICE_DATABASE, "eshop_prod_sku", EshopProdSkuDO.class),
    ESHOP_ORDER(SERVICE_DATABASE, "eshop_order", EshopOrderDO.class),
    ;
    final String database;
    final String table;
    final Class<? extends MysqlBaseDO> objectDO;

    public static final Map<String, EshopMysqlTableEnum> TABLE_MAP = new HashMap<>();

    static {
        for (EshopMysqlTableEnum table : EshopMysqlTableEnum.values()) {
            TABLE_MAP.put(table.getDatabase() + SEPARATOR + table.getTable(), table);
        }
    }

    public static EshopMysqlTableEnum getDatabaseTable(String database, String table) {
        return TABLE_MAP.getOrDefault(database + SEPARATOR + table, DEFAULT);
    }

    @SuppressWarnings("unchecked")
    public <T extends MysqlBaseDO> T getData(Map<String, String> map) {
        return JsonUtils.snakeCaseMapToObject(map, (Class<T>) objectDO);
    }
}

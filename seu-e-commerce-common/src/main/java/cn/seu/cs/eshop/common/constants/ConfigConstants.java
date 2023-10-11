package cn.seu.cs.eshop.common.constants;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/11
 */
public class ConfigConstants {
    public static String CONFIG_GROUP = "ESHOP";
    public static String CONFIG_PREFIX = "eshop.%s.";
    public static String DATASOURCE_PREFIX = CONFIG_PREFIX + "datasource.";
    public static String MYSQL_CONFIG = DATASOURCE_PREFIX + "mysql";
    public static String REDIS_CONFIG = DATASOURCE_PREFIX + "redis";
    public static String ES_CONFIG = DATASOURCE_PREFIX + "es";
    public static String COMMON_CONFIG = CONFIG_PREFIX + "config.";

    public static String getDataId(String format, String application) {
        return String.format(format, application);
    }

    public static String getDataId(String format, String application, String configName) {
        return getDataId(format, application) + configName;
    }
}

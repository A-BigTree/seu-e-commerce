package cn.seu.cs.eshop.common.constants;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/11
 */
public class ConfigConstants {
    public static String CONFIG_GROUP = "ESHOP";
    public static String CONFIG_PREFIX = "eshop.%s.";
    public static String COMMON_CONFIG = CONFIG_PREFIX + "config.";
    public static String ACCOUNT_CONFIG = "account";
    public static String API_CONFIG = "api";
    public static String IM_CONFIG = "im";
    public static String SERVICE_CONFIG = "service";
    public static String TASK_CONFIG = "task";

    public static long TIMEOUT_CONFIG = 3000L;

    public static String SERVER_ADDR = "server-addr";

    public static String USERNAME = "username";

    public static String PASSWORD = "password";

    public static String getDataId(String format, String application) {
        return String.format(format, application);
    }

    public static String getDataId(String format, String application, String configName) {
        return getDataId(format, application) + configName;
    }
}

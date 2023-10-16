package cn.seu.cs.eshop.common.constants;

import java.util.concurrent.TimeUnit;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/16
 */
public class RedisConstants {
    public static String ESHOP_PREFIX = "eshop:";
    public static long DEFAULT_EXPIRATION_TIME = 60L;
    public static TimeUnit DEFAULT_EXPIRATION_TIME_UNIT = TimeUnit.SECONDS;
}

package cn.seu.cs.eshop.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
public class TimeUtils {
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 计算time偏移量
     * @param time 初始时间
     * @param offset 偏移量
     * @param timeUnit 偏移量单位
     * @return 结果毫秒数
     */
    public static Long offsetDateTime(Long time, Long offset, TimeUnit timeUnit) {
        return time + timeUnit.toMillis(offset);
    }

    public static Long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static String convertString(Long time, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(time));
    }

    public static Long convertMills(String time, SimpleDateFormat dateFormat) throws ParseException {
        return dateFormat.parse(time).getTime();
    }
}

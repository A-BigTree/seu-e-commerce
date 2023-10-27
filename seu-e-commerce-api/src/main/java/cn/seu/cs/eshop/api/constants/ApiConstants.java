package cn.seu.cs.eshop.api.constants;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/23
 */
public class ApiConstants {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String IMAGE_HEADER_PREFIX = "/data/eshop/static/app/image/head%s/";

    public static final String DEFAULT_SUFFIX = ".jpg";

    public static String buildString(String format, String key) {
        return String.format(format, key);
    }

}

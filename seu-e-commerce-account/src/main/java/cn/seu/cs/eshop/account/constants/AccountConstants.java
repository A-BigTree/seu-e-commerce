package cn.seu.cs.eshop.account.constants;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/22
 */
public class AccountConstants {
    public static String VERIFY_CODE_RETRY_ERROR = "验证码已发送请稍后重试";
    public static String VERIFY_CODE_SEND_SUCCESS = "验证码发送成功";
    public static String VERIFY_CODE_CHECK_ERROR = "验证码错误";
    public static String ACCOUNT_DUPLICATION_ERROR = "注册邮箱账户已存在";
    public static String ACCOUNT_NOT_EXIST_ERROR = "用户不存在";
    public static String REGISTER_STATE_CHANGE_ERROR = "注册状态修改错误";
    public static String PASSWORD_SLAT_MD5 = "eshop-java";
    public static String PASSWORD_ADMIN_SLAT_MD5 = "eshop-admin";
    public static String NICKNAME_PREFIX = "CS-SEUer";
    public static String NICKNAME_SYMBOLS = "0123456789";
    public static int NICKNAME_CODE_LENGTH = 6;
    public static String SHOP_HEAD_IMAGE_PREFIX = "/head/head%s/";
}

package cn.seu.cs.eshop.common.util;


import java.security.SecureRandom;
import java.util.Random;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/19
 */
public class RandomGenerateUtils {
    private static final Random RANDOM = new SecureRandom();


    /**
     * 随机生成固定长度验证码
     * @param symbols 可选字符集合
     * @param length 验证码长度
     * @return 返回结果
     */
    public static String generateVerCode(String symbols, int length) {
        char[] numbers = new char[length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = symbols.charAt(RANDOM.nextInt(symbols.length()));
        }
        return new String(numbers);
    }

    /**
     * 生成昵称
     */
    public static String generateNickname(String prefix, String symbols, int length) {
        return prefix + generateVerCode(symbols, length);
    }
}

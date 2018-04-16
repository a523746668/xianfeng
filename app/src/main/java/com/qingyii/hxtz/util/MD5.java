package com.qingyii.hxtz.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 */
public class MD5 {

    private MD5() {
    }

    /**
     * MD5加密32位
     *
     * @param param 需要加密参数
     * @return 加密结果
     */
    public static String encrypt32(String param) {
        return encrypt(false, param);
    }

    /**
     * MD5加密16位
     *
     * @param param 需要加密参数
     * @return 加密结果
     */
    public static String encrypt16(String param) {
        return encrypt(true, param);
    }

    /**
     * MD5加密
     *
     * @param flag  true:返回16位，否则32位
     * @param param 需要加密参数
     * @return 加密结果
     */
    private static String encrypt(boolean flag, String param) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String result = byteArrayToHexString(md.digest(param.getBytes()));
            return flag ? result.substring(8, 24) : result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    // 测试主函数
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String s = new String("中国");
        System.out.println("原始：" + s);// 14e1b600b1fd579f47433b88e8d85291
        System.out.println("MD5后：" + encrypt32(s));
        System.out.println("MD5后：" + encrypt16(s));
    }

}

package com.yuan.sha;

import org.apache.commons.codec.binary.Hex;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要算法：安全散列算法
 */
public class MySHA {

    private static String src = "wang yuan SHA";

    public static void jdkSHA() {
        try {
            //获取SHA对象
            MessageDigest sha = MessageDigest.getInstance("SHA");
            sha.update(src.getBytes());
            System.out.println("JDK SHA 密文：" + Hex.encodeHexString(sha.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkSHA();
    }

}

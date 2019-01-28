package com.yuan.md;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要算法：MD
 * <p>
 * MD类算法有MD2，MD4，MD5。这里只拿最常用的MD5举例
 */
public class MyMD {

    private static String src = "wang yuan MD";

    public static void jdkMD5() {
        try {
            //获取MD5对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(src.getBytes());
            //使用第3方库转为16进制输出，否则乱码没法看
            System.out.println("JDK MD5 密文：" + Hex.encodeHexString(md5Bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkMD5();
    }

}

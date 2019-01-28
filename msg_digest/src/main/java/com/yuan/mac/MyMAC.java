package com.yuan.mac;

import org.apache.commons.codec.binary.Hex;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 消息摘要算法：MAC算法
 */
public class MyMAC {

    private static String src = "wang yuan MAC";

    private static void jdkHmacMD5() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");//初始化KeyGenerator
            SecretKey secretKey = keyGenerator.generateKey();//产生密钥
            byte[] key = secretKey.getEncoded();//获得密钥
//            byte[] key =Hex.decodeHex(new char[]{'a','a','a','a','a','a','a','a','a','a'});//也可以自定义密钥
            SecretKey restore = new SecretKeySpec(key, "HmacMD5");//还原密钥
            //实例化MAC
            Mac mac = Mac.getInstance(restore.getAlgorithm());
            mac.init(restore);
            byte[] hmacMD5Bytes = mac.doFinal(src.getBytes());//执行摘要
            System.out.println("JDK HmacMD5 密文：" + Hex.encodeHexString(hmacMD5Bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkHmacMD5();
    }

}

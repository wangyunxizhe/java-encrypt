package com.yuan.aes;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * 对称加密算法：AES
 * 对称加密算法的缺点：当密钥被泄露时，那么会很容易被破解，因为加密/解密持有的是同一密钥
 * <p>
 * 目前使用最为广泛的加密方式
 */
public class MyAES {

    private static String src = "wang yuan AES";

    private static void jdkAES() {
        try {
            //生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");//初始化KeyGenerator
            keyGenerator.init(192);//需要128/192/256位的key
//            keyGenerator.init(new SecureRandom());//也可以这样，生成一个默认长度的key，根据不同算法，生成不同长度的key
            SecretKey secretKey = keyGenerator.generateKey();//产生密钥
            byte[] keyBytes = secretKey.getEncoded();//获得密钥
            //进行key的转换
            Key key = new SecretKeySpec(keyBytes, "AES");
            //加密
            //Cipher：加/解密操作类，参数为：加密算法/工作方式/填充方式
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("JDK AES 密文：" + Hex.encodeHexString(result));
            //解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);
            System.out.println("JDK 3DES 解密后明文：" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkAES();
    }

}

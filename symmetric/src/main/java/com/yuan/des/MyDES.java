package com.yuan.des;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 对称加密算法：DES
 * 对称加密算法的缺点：当密钥被泄露时，那么会很容易被破解，因为加密/解密持有的是同一密钥
 */
public class MyDES {

    private static String src = "wang yuan DES";

    public static void jdkDES() {
        try {
            //生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");//初始化KeyGenerator
            keyGenerator.init(56);//需要56位的key
            SecretKey secretKey = keyGenerator.generateKey();//产生密钥
            byte[] key = secretKey.getEncoded();//获得密钥
            //进行key的转换
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");//参数为：指定的加密方式
            SecretKey doAfferKey = factory.generateSecret(desKeySpec);//转化之后生成的DES密钥
            //加密
            //加/解密操作类，参数为：加密算法/工作方式/填充方式
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, doAfferKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("JDK DES 密文：" + Hex.encodeHexString(result));
            //解密
            cipher.init(Cipher.DECRYPT_MODE, doAfferKey);
            result = cipher.doFinal(result);
            System.out.println("JDK DES 解密后明文：" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkDES();
    }

}

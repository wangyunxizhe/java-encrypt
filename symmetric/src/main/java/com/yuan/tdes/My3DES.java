package com.yuan.tdes;

import org.apache.commons.codec.binary.Hex;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * 对称加密算法：3DES
 * 对称加密算法的缺点：当密钥被泄露时，那么会很容易被破解，因为加密/解密持有的是同一密钥
 */
public class My3DES {

    private static String src = "wang yuan 3DES";

    private static void jdk3DES() {
        try {
            //生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");//初始化KeyGenerator
            keyGenerator.init(168);//需要168位的key
//            keyGenerator.init(new SecureRandom());//也可以这样，生成一个默认长度的key，根据不同算法，生成不同长度的key
            SecretKey secretKey = keyGenerator.generateKey();//产生密钥
            byte[] key = secretKey.getEncoded();//获得密钥
            //进行key的转换
            DESedeKeySpec desKeySpec = new DESedeKeySpec(key);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");//参数为：指定的加密方式
            SecretKey doAfferKey = factory.generateSecret(desKeySpec);//转化之后生成的DES密钥
            //加密
            //Cipher：加/解密操作类，参数为：加密算法/工作方式/填充方式
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, doAfferKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("JDK 3DES 密文：" + Hex.encodeHexString(result));
            //解密
            cipher.init(Cipher.DECRYPT_MODE, doAfferKey);
            result = cipher.doFinal(result);
            System.out.println("JDK 3DES 解密后明文：" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdk3DES();
    }

}

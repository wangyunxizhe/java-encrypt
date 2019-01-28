package com.yuan.pbe;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * 对称加密算法：PBE
 * 对称加密算法的缺点：当密钥被泄露时，那么会很容易被破解，因为加密/解密持有的是同一密钥
 * <p>
 * PBE算法结合了消息摘要算法和对称加密算法的优点
 */
public class MyPBE {

    private static String src = "wang yuan PBE";

    private static void jdkPBE() {
        try {
            //初始化盐
            SecureRandom random = new SecureRandom();
            byte[] salt = random.generateSeed(8);//通过随机数产出盐，8位
            //口令与密钥
            String pwd = "yuan.w";//口令
            PBEKeySpec pbeKeySpec = new PBEKeySpec(pwd.toCharArray());
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
            Key key = factory.generateSecret(pbeKeySpec);//密钥
            //加密
            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, 100);//100为迭代次数
            Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("JDK PBE 密文：" + Hex.encodeHexString(result));
            //解密
            cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
            result = cipher.doFinal(result);
            System.out.println("JDK PBE 解密后明文：" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkPBE();
    }

}

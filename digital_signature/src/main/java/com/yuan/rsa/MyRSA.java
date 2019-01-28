package com.yuan.rsa;

import org.apache.commons.codec.binary.Hex;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 数字签名算法：RSA（也是一种非对称加密算法）
 */
public class MyRSA {

    private static String src = "wang yuan RSA";

    private static void jdkRSA() {
        try {
            //1，初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            //2，执行签名（加密），用私钥签名，公钥验证
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);//私钥
            //创建签名对象
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);//用私钥初始化签名
            signature.update(src.getBytes());
            byte[] result = signature.sign();
            System.out.println("JDK RSA 签名：" + Hex.encodeHexString(result));
            //3，验证签名（解密）
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);//公钥
            //创建签名对象
            signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);//用公钥初始化验证签名
            signature.update(src.getBytes());
            boolean is = signature.verify(result);
            System.out.println("JDK RSA 签名 验证结果是否统一：" + is);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        jdkRSA();
    }
}

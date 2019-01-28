package com.yuan.dsa;

import org.apache.commons.codec.binary.Hex;
import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 数字签名算法：DSA
 */
public class MyDSA {

    private static String src = "wang yuan DSA";

    private static void jdkDSA() {
        try {
            //1，初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            DSAPublicKey dsaPublicKey = (DSAPublicKey) keyPair.getPublic();
            DSAPrivateKey dsaPrivateKey = (DSAPrivateKey) keyPair.getPrivate();
            //2，执行签名（加密），用私钥签名，公钥验证
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(dsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);//私钥
            //创建签名对象
            Signature signature = Signature.getInstance("SHA1withDSA");
            signature.initSign(privateKey);//用私钥初始化签名
            signature.update(src.getBytes());
            byte[] result = signature.sign();
            System.out.println("JDK DSA 签名：" + Hex.encodeHexString(result));
            //3，验证签名（解密）
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(dsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("DSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);//公钥
            //创建签名对象
            signature = Signature.getInstance("SHA1withDSA");
            signature.initVerify(publicKey);//用公钥初始化验证签名
            signature.update(src.getBytes());
            boolean is = signature.verify(result);
            System.out.println("JDK DSA 签名 验证结果是否统一：" + is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkDSA();
    }

}

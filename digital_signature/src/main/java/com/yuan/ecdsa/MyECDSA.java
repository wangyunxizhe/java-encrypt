package com.yuan.ecdsa;

import org.apache.commons.codec.binary.Hex;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 数字签名算法：ECDSA
 */
public class MyECDSA {

    private static String src = "wang yuan ECDSA";

    private static void jdkECDSA() {
        try {
            //1，初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
            //2，执行签名（加密），用私钥签名，公钥验证
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(ecPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);//私钥
            //创建签名对象
            Signature signature = Signature.getInstance("SHA1withECDSA");
            signature.initSign(privateKey);//用私钥初始化签名
            signature.update(src.getBytes());
            byte[] result = signature.sign();
            System.out.println("JDK ECDSA 签名：" + Hex.encodeHexString(result));
            //3，验证签名（解密）
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(ecPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);//公钥
            //创建签名对象
            signature = Signature.getInstance("SHA1withECDSA");
            signature.initVerify(publicKey);//用公钥初始化验证签名
            signature.update(src.getBytes());
            boolean is = signature.verify(result);
            System.out.println("JDK ECDSA 签名 验证结果是否统一：" + is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkECDSA();
    }

}

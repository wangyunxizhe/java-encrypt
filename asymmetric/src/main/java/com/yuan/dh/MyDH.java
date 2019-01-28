package com.yuan.dh;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * 非对称加密算法：DH
 * <p>
 * 由于jdk版本问题，该类在运行之前，在IDEA该类的启动参数中加了-Djdk.crypto.KeyAgreement.legacyKDF=true
 */
public class MyDH {

    private static String src = "wang yuan DH";

    //由于是演示所以代码写在一个方法中，真实情况中，1，2两个步骤是不可能在一起的，肯定是1在发送方，2在接受方
    private static void jdkDH() {
        try {
            //1，初始化发送方密钥
            KeyPairGenerator senderKey = KeyPairGenerator.getInstance("DH");
            senderKey.initialize(512);
            KeyPair senderKeyPair = senderKey.generateKeyPair();
            //发送方公钥，需要给接收方（通过网络，文件，U盘都可以）
            byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();

            //2，初始化接收方密钥
            KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
            PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
            DHParameterSpec dhParameterSpec = ((DHPublicKey) receiverPublicKey).getParams();
            KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            receiverKeyPairGenerator.initialize(dhParameterSpec);
            KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
            PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
            byte[] receiverPublicKeyEnc = receiverKeyPair.getPublic().getEncoded();

            //3，密钥构建
            KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
            receiverKeyAgreement.init(receiverPrivateKey);
            receiverKeyAgreement.doPhase(receiverPublicKey, true);
            //生成接收方本地密钥
            SecretKey receiverDESKey = receiverKeyAgreement.generateSecret("DES");

            KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
            x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
            PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
            KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
            senderKeyAgreement.init(senderKeyPair.getPrivate());
            senderKeyAgreement.doPhase(senderPublicKey, true);
            //生成发送方本地密钥
            SecretKey senderDESKey = senderKeyAgreement.generateSecret("DES");
            if (Objects.equals(receiverDESKey, senderDESKey)) {
                System.out.println("双方密钥相同");
            }
            //4，加密
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, senderDESKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("JDK DH 密文：" + Hex.encodeHexString(result));
            //5，解密
            cipher.init(Cipher.DECRYPT_MODE, receiverDESKey);
            result = cipher.doFinal(result);
            System.out.println("JDK DH 解密后明文：" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkDH();
    }

}

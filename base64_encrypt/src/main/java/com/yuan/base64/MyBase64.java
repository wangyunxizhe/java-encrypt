package com.yuan.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.io.IOException;

/**
 * java算法：Base64
 */
public class MyBase64 {

    private static String src = "wang yuan base64";

    //该方法使用jdk自带的base64算法类
    public static void jdkBase64() {
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            String encode = encoder.encode(src.getBytes());
            System.out.println("密文：" + encode);

            BASE64Decoder decoder = new BASE64Decoder();
            System.out.println("解密后的明文：" + new String(decoder.decodeBuffer(encode)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        jdkBase64();
    }

}

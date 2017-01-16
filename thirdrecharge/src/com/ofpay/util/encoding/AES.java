package com.ofpay.util.encoding;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AES {

    private static String aes = "AES";

    private static String charsetName = "UTF-8";

    /**
     * key长度
     */
    private static final int KEYSIZE = 16;

    /**
     * 加密
     * 
     * @param sSrc
     *            加密原串
     * @param sKey
     *            加密密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static String encrypt(String sSrc, String sKey, String sIv) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {
        // Key为空null
        if (sKey == null) {
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != KEYSIZE) {
            return null;
        }
        byte[] raw = sKey.getBytes(charsetName);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, aes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(sIv.getBytes(charsetName));// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(charsetName));

        return new String(Base64.encode(encrypted), charsetName);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
     * 加密
     * 
     * @param sSrc
     *            加密原串
     * @param sKey
     *            加密密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static byte[] encryptbyte(String sSrc, String sKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer(sSrc);
        // Key为空null
        if (sKey == null) {
            return new byte[0];
        }
        // 判断Key是否为16位
        if (sKey.length() != KEYSIZE) {
            return new byte[0];
        }

        while (sb.length() % KEYSIZE != 0) {
            char c = 0;
            sb.append(c);
        }
        byte[] raw = sb.toString().getBytes(charsetName);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, aes);
        Cipher cipher = Cipher.getInstance(aes); // 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(sb.toString().getBytes(charsetName));

    }

    /**
     * 解密
     * 
     * @param sSrc
     *            解密串
     * @param sKey
     *            解密密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static String decrypt(String sSrc, String sKey, String sIv) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
            IOException {
        // 判断Key是否正确
        if (sKey == null) {
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != KEYSIZE) {
            return null;
        }
        byte[] raw = sKey.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, aes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(sIv.getBytes(charsetName));
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = Base64.decode(sSrc.getBytes());// 先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original, charsetName);
    }

    /**
     * hex2byte
     * 
     * @param strhex
     * @return
     */
    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return new byte[0];
        }
        int l = strhex.length();
        if ((l & 1) != 0) {
            return new byte[0];
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), KEYSIZE);
        }
        return b;
    }

    /**
     * byte2hex
     * 
     * @param b
     *            byte Array
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuffer sbhs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                sbhs.append("0").append(stmp);
            } else {
                sbhs.append(stmp);
            }
        }
        return sbhs.toString();
    }

    /**
     * 解密
     * 
     * @param sSrc
     *            解密串
     * @param sKey
     *            解密密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static byte[] decryptbyte(byte[] sSrc, String sKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException {
        // 判断Key是否正确
        if (sKey == null) {
            return new byte[0];
        }
        // 判断Key是否为16位
        if (sKey.length() != KEYSIZE) {
            return new byte[0];
        }
        byte[] raw = sKey.getBytes(charsetName);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, aes);
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding"); // 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(sSrc);
    }

}

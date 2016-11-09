package com.thridrecharge.service.utils;

import java.security.MessageDigest;

public class MD5Utils {

	/** ʮ�����������ֵ��ַ���ӳ������ */
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
 
    /**
     * ��inputString���ܡ�
     *
     * @param inputString
     *            �����ܵ��ַ���
     * @return
     */
    public static String createPassword(String inputString) {
        return encodeByMD5(inputString);
    }
 
    /**
     * ��֤����������Ƿ���ȷ
     *
     * @param password
     *            ���������루���ܺ�������룩
     * @param inputString
     *            ������ַ���
     * @return
     */
    public static boolean authenticatePassword(String password,
            String inputString) {
        if (password.equals(encodeByMD5(inputString))) {
            return true;
        } else {
            return false;
        }
    }
 
    /**
     * ���ַ�������MD5����
     *
     * @param originString
     * @return
     */
    public static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] results = md.digest(originString.getBytes());
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
 
    /**
     * ת���ֽ�����Ϊ16�����ִ�
     *
     * @param b
     *            �ֽ�����
     * @return ʮ�������ִ�
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }
 
    /**
     * ��һ���ֽ�ת����16������ʽ���ַ���
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    
    public static void main(String[] args) {
//    	                                        userid=oufei001&orderid=536&sporderid=1&merchantsubmittime=20140514102201&result=1&key=Abcd1234
//    	                                        userid=oufei001&orderid=536&sporderid=1&merchantsubmittime=20140514102201&result=1&key=Abcd1234
        String password = MD5Utils.encodeByMD5("userid=oufei001&orderid=536&sporderid=1&merchantsubmittime=20140514102201&result=1&key=Abcd1234");
        System.out.println(password);
    }
}

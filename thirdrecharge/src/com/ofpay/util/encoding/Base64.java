/**
 * Base64.java
 */
package com.ofpay.util.encoding;

public final class Base64 {
    private Base64() {

    }

    /**
     * Encode String object;
     * 
     * @param src
     *            String object to be encoded.
     * @return encoded String;
     */
    public static String encode(String src) {
        String target = null;
        if (src != null) {
            byte[] bts1 = src.getBytes();
            byte[] bts2 = encode(bts1);
            if (bts2 != null) {
                target = new String(bts2);
            }
        }
        return target;
    }

    /**
     * Encodes hex octects into Base64.
     * 
     * @param binaryData
     *            Array containing binary data to encode.
     * @return Base64-encoded data.
     */
    public static byte[] encode(byte[] binaryData) {
        return org.apache.commons.codec.binary.Base64.encodeBase64(binaryData);
    }

    /**
     * Base64解码字符串
     * @param src 待解码的字符串
     * @return 解码后的字符串
     */
    public static String decode(String src) {
        String target = null;
        if (src != null) {
            byte[] bts1 = src.getBytes();
            byte[] bts2 = decode(bts1);
            if (bts2 != null) {
                target = new String(bts2);
            }
        }
        return target;
    }

    /**
     * Decodes Base64 data into octects
     * 
     * @param base64Data
     *            Byte array containing Base64 data
     * @return Array containing decoded data.
     */
    public static byte[] decode(byte[] base64Data) {
        return org.apache.commons.codec.binary.Base64.decodeBase64(base64Data);
    }
}

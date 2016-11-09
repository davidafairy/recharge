package com.redstoneinfo.platform.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author zhangxiaorong
 *
 * 2014-2-11
 */
public class MD5Util {
	
	public static String encode(String string ){
		String lisence = null;
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			string = string + new SimpleDateFormat("yyyy-MM-dd").parse("2013-10-12").getTime();
		    string = changeValue(string);
			byte[] md5=md.digest(string.getBytes());
			lisence = changeValue(new BASE64Encoder().encode(md5));			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lisence;
	}
	
	private static String changeValue(String s) {
	    byte[] b = s.getBytes();
	    char[] ch = new char[s.length()];
   
	    for ( int i = 0, k = b.length; i < k; i++) {
	    	int m = b[i];
		    if (m >= 48 && m <= 57)
		        m = ((m - 48) + 5) % 10 + 48;
		    else if (m >= 65 && m <= 90)
		        m = ((m - 65) + 13) % 26 + 65;
		    else if (m >= 97 && m <= 122)
		        m = ((m - 97) + 13) % 26 + 97;
		    ch[i] = (char) m;
	    }
	    String str = String.valueOf(ch);
	    return aa(str.getBytes());
	}
	
	private static String aa(byte[] bs){
		char hexDigits[] = { 
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		int j = bs.length; 
		char[] str = new char[j * 2]; 
		int k = 0; 
		for (int i = 0; i < j; i++) { 
			byte b = bs[i]; 
			str[k++] = hexDigits[b >>> 4 & 0xf]; 
			str[k++] = hexDigits[b & 0xf]; 
		} 
		return new String(str);
	}
	
	public static void main(String[] s){
		System.out.println(encode("Abc123"));
	}

}

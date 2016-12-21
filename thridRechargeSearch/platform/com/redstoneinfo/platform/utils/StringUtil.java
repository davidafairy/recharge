package com.redstoneinfo.platform.utils;


/**
 * @author zhangxiaorong
 * 2014-1-26
 */
public class StringUtil {
	
	private static char[][] filenameReplaceArray = new char[][]{
			{'/', '／'}, 
			{'|', '｜'}, 
			{'<', '〈'}, 
			{'>', '〉'}, 
			{':', '：'}, 
			{',', '，'}, 
			{';', '；'}, 
			{'\'', '‘'}, 
			{'?', '？'},
			{'*', '＊'},
			{' ', '\u0000'}
	};

	
	public static boolean isUpperCase(String string){
		char[] cs = string.toCharArray();
		for (char c :cs){
			if (c < 65 || c > 90)
				return false;
		}
		return true;
	}	
	
	public static boolean isLowerCase(String string){
		char[] cs = string.toCharArray();
		for (char c :cs){
			if (c < 97 || c > 122)
				return false;
		}
		return true;
	}
	
	public static String correctFileName(String name){
		if (name == null)
			return null;
		
		if (name.trim().equals(""))
			return "";
		
		for (char[] cs :filenameReplaceArray){
			name = name.replace(cs[0], cs[1]);
		}
		return name.trim();
	}
	
	
	public static void main(String[] s){
		System.out.println(correctFileName("=:\\/"));
	}

}

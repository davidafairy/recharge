package com.redstoneinfo.platform.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public enum YESNO {

	YES(1,"是"),NO(2,"否");
	
	private int key;
	private String value;
	
	private YESNO(int key,String value) {
		this.key = key;
		this.value = value;
	}
	
	public int getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
	public static String getValueByKey(int key) {
		switch(key) {
			case 1 : return YES.value;
			case 2 : return NO.value;
			default: return "";
		}
	}
	
	public static JSONArray toJSON() {
		JSONArray jsonArray = new JSONArray();
		JSONObject yesObj = new JSONObject();
		yesObj.put("key", YES.getKey());
		yesObj.put("value", YES.getValue());
		jsonArray.add(yesObj);
		
		JSONObject noObj = new JSONObject();
		noObj.put("key", NO.getKey());
		noObj.put("value", NO.getValue());
		jsonArray.add(noObj);
		return jsonArray;
	}
}

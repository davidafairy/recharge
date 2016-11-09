package com.redstoneinfo.platform.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public enum Status {
	
	NORMAL(1,"正常"),CANCEL(2,"注销");
	
	private int key;
	private String value;
	
	private Status(int key,String value) {
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
			case 1 : return NORMAL.value;
			case 2 : return CANCEL.value;
			default: return "";
		}
	}
	
	public static JSONArray toJSON() {
		JSONArray jsonArray = new JSONArray();
		JSONObject strUnAduit = new JSONObject();
		strUnAduit.put("key", NORMAL.getKey());
		strUnAduit.put("value", NORMAL.getValue());
		jsonArray.add(strUnAduit);
		
		JSONObject strAudit = new JSONObject();
		strAudit.put("key", CANCEL.getKey());
		strAudit.put("value", CANCEL.getValue());
		jsonArray.add(strAudit);
		
		
		return jsonArray;
	}


}

package com.redstoneinfo.platform.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public enum VideoType {
	
	HIGH(1,"高清"),STANDARD(2,"标准"), GENERAL(3,"普通");
	
	private int key;
	private String value;
	
	private VideoType(int key,String value) {
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
			case 1 : return HIGH.value;
			case 2 : return STANDARD.value;
			case 3 : return GENERAL.value;
			default: return "";
		}
	}
	
	public static JSONArray toJSON() {
		JSONArray jsonArray = new JSONArray();
		JSONObject strUnAduit = new JSONObject();
		strUnAduit.put("key", HIGH.getKey());
		strUnAduit.put("value", HIGH.getValue());
		jsonArray.add(strUnAduit);
		
		JSONObject strAudit = new JSONObject();
		strAudit.put("key", STANDARD.getKey());
		strAudit.put("value", STANDARD.getValue());
		jsonArray.add(strAudit);
		
		JSONObject innigsAduit = new JSONObject();
		innigsAduit.put("key", GENERAL.getKey());
		innigsAduit.put("value", GENERAL.getValue());
		jsonArray.add(innigsAduit);
		
		return jsonArray;
	}


}

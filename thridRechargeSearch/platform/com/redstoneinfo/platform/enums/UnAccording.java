package com.redstoneinfo.platform.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author liyun
 *
 * 2014-2-17
 */
public enum UnAccording {

	according(1,"通过"),unAccording(2,"未通过") ;
	private int key;
	private String value;
	
	
	private UnAccording(int key,String value) {
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

			case 1 : return according.value;
			case 2 : return unAccording.value;
			default: return "";
		}
	}
	
	public static JSONArray toJson(){
		
		JSONArray jsonArray = new JSONArray();
		
		JSONObject defaultObj = new JSONObject();
		defaultObj.put("key", "");
		defaultObj.put("value", "请选择");
		jsonArray.add(defaultObj);
		
		JSONObject accordingObj = new JSONObject();
		accordingObj.put("key", according.getKey());
		accordingObj.put("value", according.getValue());
		jsonArray.add(accordingObj);
		
		JSONObject unAccordingObj = new JSONObject();
		unAccordingObj.put("key", unAccording.getKey());
		unAccordingObj.put("value", unAccording.getValue());
		jsonArray.add(unAccordingObj);
		return jsonArray;
	}
}

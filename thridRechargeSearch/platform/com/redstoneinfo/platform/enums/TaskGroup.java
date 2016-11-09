package com.redstoneinfo.platform.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * 计划任务组
 * 
 * @author zhangxiaorong
 *
 * 2014-2-15
 */
public enum TaskGroup {
	
	GROUP1(1, "GROUP1", ""),
	GROUP2(2, "GROUP2 ", "");
    
	private int key;
	private String value;
	private String desc;
	
	
	private TaskGroup(int key,String value, String desc) {
		this.key = key;
		this.value = value;
		this.desc = desc;
	}
	
	public int getKey() {
		return key;
	}
	
	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}

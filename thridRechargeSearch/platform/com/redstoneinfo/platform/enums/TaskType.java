package com.redstoneinfo.platform.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * 计划任务类型
 * 
 * @author zhangxiaorong
 *
 * 2014-2-15
 */
public enum TaskType {
	
	AUTO_RUN(1, "自动", ""),
	MANUAL_RUN(2, "手动 ", "");
    
	private int key;
	private String value;
	private String desc;
	
	
	private TaskType(int key,String value, String desc) {
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

package com.redstoneinfo.platform.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * 计划任务状态
 * 
 * @author zhangxiaorong
 *
 * 2014-2-15
 */
public enum TaskStatus {
	
	EXECUTING(1, "运行中", ""),
	PAUSE(2, "暂停 ", ""),
	STOP(3, "终止 ", ""),
	DISABLE(4, "禁用 ", "");
	
	private int key;
	private String value;
	private String desc;
	
	
	private TaskStatus(int key,String value, String desc) {
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

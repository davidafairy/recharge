package com.redstoneinfo.platform.enums;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public enum AuditStatus {
	
	UNAUDITED(1,"未审核"),AUDITED(2,"已审核"), ARRANGE(3,"已编排"), FILE(4,"已归档"), SEND(5,"已发布"), OFFINE(6,"已下线"), AUDITFAIL(9,"审核失败");
	
	private int key;
	private String value;
	
	private AuditStatus(int key,String value) {
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
			case 1 : return UNAUDITED.value;
			case 2 : return AUDITED.value;
			case 3 : return ARRANGE.value;
			case 4 : return FILE.value;
			case 5 : return SEND.value;
			case 6 : return OFFINE.value;
			case 9 : return AUDITFAIL.value;
			default: return "";
		}
	}
	
	public static JSONArray toJSON() {
		JSONArray jsonArray = new JSONArray();
		JSONObject strUnAduit = new JSONObject();
		strUnAduit.put("key", UNAUDITED.getKey());
		strUnAduit.put("value", UNAUDITED.getValue());
		jsonArray.add(strUnAduit);
		
		JSONObject strAudit = new JSONObject();
		strAudit.put("key", AUDITED.getKey());
		strAudit.put("value", AUDITED.getValue());
		jsonArray.add(strAudit);
		
		JSONObject innigsAduit = new JSONObject();
		innigsAduit.put("key", ARRANGE.getKey());
		innigsAduit.put("value", ARRANGE.getValue());
		jsonArray.add(innigsAduit);
		
		JSONObject safetyChecked = new JSONObject();
		safetyChecked.put("key", FILE.getKey());
		safetyChecked.put("value", FILE.getValue());
		jsonArray.add(safetyChecked);
		
		JSONObject send = new JSONObject();
		send.put("key", SEND.getKey());
		send.put("value", SEND.getValue());
		jsonArray.add(send);
		
		JSONObject offine = new JSONObject();
		offine.put("key", OFFINE.getKey());
		offine.put("value", OFFINE.getValue());
		jsonArray.add(offine);
		
		JSONObject auditFail = new JSONObject();
		auditFail.put("key", AUDITFAIL.getKey());
		auditFail.put("value", AUDITFAIL.getValue());
		jsonArray.add(auditFail);
		
		return jsonArray;
	}


}

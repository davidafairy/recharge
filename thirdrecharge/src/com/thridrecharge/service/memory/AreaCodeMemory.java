package com.thridrecharge.service.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.thridrecharge.service.entity.AreaCode;

public class AreaCodeMemory {

	private static AreaCodeMemory areaCodeMemory = new AreaCodeMemory();

	private Map phoneMap = new HashMap();

	private AreaCodeMemory() {

	}

	public static AreaCodeMemory getAreaCodeMemeory() {
		return areaCodeMemory;
	}

	public void initPhoneMap(List phoneList) {
		for (Iterator iter = phoneList.iterator(); iter.hasNext();) {
			AreaCode phoneNo = (AreaCode) iter.next();
			List ruList = ruleToList(phoneNo.getPhoneNo());
			for (Iterator iterator = ruList.iterator(); iterator.hasNext();) {
				String ruString = (String) iterator.next();
				phoneMap.put(ruString, phoneNo.getDescription());
			}
		}
	}

	private List ruleToList(String rule) {
		List ruleList = new ArrayList();
		rule = StringUtils.replace(rule, " ", "");
		String[] shortRule = StringUtils.split(rule, ",");
		for (int i = 0; i < shortRule.length; i++) {
			String ruleNo = shortRule[i];
			String fristNo = StringUtils.split(ruleNo, "#")[0];
			String suffNo = StringUtils.split(ruleNo, "#")[1];
			if (suffNo.contains("-")) {// 450-459
				String startNo = fristNo + StringUtils.split(suffNo, "-")[0];
				String endNo = fristNo + StringUtils.split(suffNo, "-")[1];
				Long startNoInt = Long.valueOf(startNo);
				Long endNoInt = Long.valueOf(endNo);
				for (long j = startNoInt; j <= endNoInt; j++) {
					ruleList.add(String.valueOf(j));
				}
			} else {
				ruleList.add(fristNo + suffNo);
			}
		}
		return ruleList;
	}

	/**
	 * 根据电话号码，获取agentcode 南京是 "34b06q8";
	 */
	public String getAgentCode(String phone) {
		String phoneNo = StringUtils.substring(phone, 0, 7);
		if (phoneMap.containsKey(phoneNo)) {
			return (String) phoneMap.get(phoneNo);
		} else {
			return "34b06q8";// 没有就返回南京
		}

		// return "34b06q8";//没有就返回南京

	}
}

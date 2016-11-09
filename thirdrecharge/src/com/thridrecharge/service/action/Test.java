package com.thridrecharge.service.action;

import com.thridrecharge.service.utils.MD5Utils;

public class Test {

	public static void main(String[] args) {
		System.out.println(MD5Utils.encodeByMD5("agentName=app001&flowNo=111&phoneNo=222&money=3000&key=abc113"));
	}

}

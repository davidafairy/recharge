package com.thridrecharge.service.enums;

public enum RechargeCardStatus {

	//1：未使用；
	UNUSE(1),
	//2：已使用；
	USED(2),
	//3：预占；
	OCCUPY(3),
	//4：不可用
	ERROR(4);
	private int status;
	
	private RechargeCardStatus(int status) {
		this.status = status;
	}
	
	public int intValue() {
		return status;
	}
}

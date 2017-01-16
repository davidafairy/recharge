package com.thridrecharge.service.enums;

public enum DealResult {

	//³É¹¦
	SUCCESS(1),
	//Ê§°Ü
	FAULT(2);
	private int result;
	
	private DealResult(int result) {
		this.result = result;
	}
	
	public int intValue() {
		return result;
	}
}

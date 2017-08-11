package com.thridrecharge.service.enums;

public enum Switch {

	//��
	OPEN(1),
	
	//��
	CLOSE(2);
	
	private int s;
	
	private Switch(int s){
		this.s = s;
	}
	
	public int intValue(){
		return s;
	}
}

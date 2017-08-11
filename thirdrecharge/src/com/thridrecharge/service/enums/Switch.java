package com.thridrecharge.service.enums;

public enum Switch {

	//¿ª
	OPEN(1),
	
	//¹Ø
	CLOSE(2);
	
	private int s;
	
	private Switch(int s){
		this.s = s;
	}
	
	public int intValue(){
		return s;
	}
}

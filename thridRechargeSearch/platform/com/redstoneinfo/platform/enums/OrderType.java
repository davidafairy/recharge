package com.redstoneinfo.platform.enums;

public enum OrderType {

	ASC(1),DESC(2);
	
	private int key;
	
	private OrderType(int key) {
		this.key = key;
	}
	
	public int getKey() {
		return key;
	}
}

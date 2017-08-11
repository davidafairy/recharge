package com.thridrecharge.service.enums;

public enum OrderStatus {

	//订单接收
	RECEIVE(1),
	
	//充值中
	RECHARGEING(2),
	
	//充值成功
	SUCCESS(3),
	
	//回调中
	CALLBACK(4),

	//可疑
	SUSPICIOUS(5);
	
	private int status;
	
	private OrderStatus(int status) {
		this.status = status;
	}
	
	public int intValue() {
		return status;
	}
}

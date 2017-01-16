package com.thridrecharge.service.enums;

public enum OrderChannel {

	//线下充值
	OFFLINE(1),
	
	//线上充值
	ONLINE(2),
	
	//充值卡充值
	RECHARGECARD(3);
	private int channel;
	
	private OrderChannel(int channel) {
		this.channel = channel;
	}
	
	public int intValue() {
		return channel;
	}
}

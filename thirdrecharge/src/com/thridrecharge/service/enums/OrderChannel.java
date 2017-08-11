package com.thridrecharge.service.enums;

public enum OrderChannel {

	//���³�ֵ
	OFFLINE(1),
	
	//���ϳ�ֵ
	ONLINE(2),
	
	//��ֵ����ֵ
	RECHARGECARD(3),
	
	//ʲô���������ߣ���ֵʧ��
	NIL(4);
	
	private int channel;
	
	private OrderChannel(int channel) {
		this.channel = channel;
	}
	
	public int intValue() {
		return channel;
	}
}

package com.thridrecharge.service.enums;

public enum OrderStatus {

	//��������
	RECEIVE(1),
	
	//��ֵ��
	RECHARGEING(2),
	
	//��ֵ�ɹ�
	SUCCESS(3);
	
	private int status;
	
	private OrderStatus(int status) {
		this.status = status;
	}
	
	public int intValue() {
		return status;
	}
}

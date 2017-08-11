package com.thridrecharge.service.enums;

public enum OrderStatus {

	//��������
	RECEIVE(1),
	
	//��ֵ��
	RECHARGEING(2),
	
	//��ֵ�ɹ�
	SUCCESS(3),
	
	//�ص���
	CALLBACK(4),

	//����
	SUSPICIOUS(5);
	
	private int status;
	
	private OrderStatus(int status) {
		this.status = status;
	}
	
	public int intValue() {
		return status;
	}
}

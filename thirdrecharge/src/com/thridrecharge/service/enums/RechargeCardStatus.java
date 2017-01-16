package com.thridrecharge.service.enums;

public enum RechargeCardStatus {

	//1��δʹ�ã�
	UNUSE(1),
	//2����ʹ�ã�
	USED(2),
	//3��Ԥռ��
	OCCUPY(3),
	//4��������
	ERROR(4);
	private int status;
	
	private RechargeCardStatus(int status) {
		this.status = status;
	}
	
	public int intValue() {
		return status;
	}
}

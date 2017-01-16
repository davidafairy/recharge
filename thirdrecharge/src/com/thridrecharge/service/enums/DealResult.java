package com.thridrecharge.service.enums;

public enum DealResult {

	//�ɹ�
	SUCCESS(1),
	//ʧ��
	FAULT(2);
	private int result;
	
	private DealResult(int result) {
		this.result = result;
	}
	
	public int intValue() {
		return result;
	}
}

package com.thridrecharge.service;

import com.thridrecharge.service.enums.ErrorCode;

public class RechargeException extends RuntimeException {

	private static final long serialVersionUID = 3537178729649626192L;
	
	private int errorCode;
	
	public RechargeException(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public RechargeException(ErrorCode errorCode) {
		this.errorCode = errorCode.getErrorCode();
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}
}

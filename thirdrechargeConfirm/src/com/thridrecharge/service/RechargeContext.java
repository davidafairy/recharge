package com.thridrecharge.service;

import org.springframework.context.ApplicationContext;

public class RechargeContext {

private static RechargeContext context = new RechargeContext();
	
	private ApplicationContext appContext = null;
	
	private RechargeContext() {
		
	}
	
	public static RechargeContext getRechargeContext() {
		return context;
	}

	public ApplicationContext getAppContext() {
		return appContext;
	}

	public void setAppContext(ApplicationContext appContext) {
		if (null == this.appContext)
			this.appContext = appContext;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(String springBeanName) {
		if (null != appContext) {
			return (T)appContext.getBean(springBeanName);
		}
		return null;
	}

}

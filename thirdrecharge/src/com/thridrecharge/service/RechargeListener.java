package com.thridrecharge.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thridrecharge.service.ordermanager.callback.CallbackManager;
import com.thridrecharge.service.ordermanager.recharge.RechargeManager;

public class RechargeListener implements ServletContextListener {

	private Log callbacklog = LogFactory.getLog("callback");
	
	private Log rechargelog = LogFactory.getLog("recharge");
	
	@Override
	public void contextDestroyed(ServletContextEvent context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());
		if(springContext != null){
			RechargeContext.getRechargeContext().setAppContext(springContext);
			final CallbackManager callbackManager = (CallbackManager)springContext.getBean("callbackManager");
			
			//�����ص�����
			new Thread(new Runnable(){

				@Override
				public void run() {
					callbacklog.info("************************��ֵ�ص���������*******************");
					callbackManager.startRecharge();
					callbacklog.info("************************��ֵ�ص����������ɹ�*******************");
				}}).start();
			
			final RechargeManager rechargeManager = (RechargeManager)springContext.getBean("rechargeManager");

			//������ֵ����
			new Thread(new Runnable(){

				@Override
				public void run() {
					rechargelog.info("************************��ֵ��������*******************");
					rechargeManager.startRecharge();
					rechargelog.info("************************��ֵ���������ɹ�*******************");
				}}).start();
			
			
		}
		
	}

}

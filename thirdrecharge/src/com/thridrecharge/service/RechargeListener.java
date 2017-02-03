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
			
			//启动回调任务
			new Thread(new Runnable(){

				@Override
				public void run() {
					callbacklog.info("************************充值回调任务启动*******************");
					callbackManager.startRecharge();
					callbacklog.info("************************充值回调任务启动成功*******************");
				}}).start();
			
			final RechargeManager rechargeManager = (RechargeManager)springContext.getBean("rechargeManager");

			//启动充值任务
			new Thread(new Runnable(){

				@Override
				public void run() {
					rechargelog.info("************************充值任务启动*******************");
					rechargeManager.startRecharge();
					rechargelog.info("************************充值任务启动成功*******************");
				}}).start();
			
			
		}
		
	}

}

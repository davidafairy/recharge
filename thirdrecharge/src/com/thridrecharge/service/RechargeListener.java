package com.thridrecharge.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thridrecharge.service.ordermanager.RechargeManager;

public class RechargeListener implements ServletContextListener {

	private Log log = LogFactory.getLog("recharge");
	
	@Override
	public void contextDestroyed(ServletContextEvent context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(context.getServletContext());
		if(springContext != null){
			RechargeContext.getRechargeContext().setAppContext(springContext);
			final RechargeManager rechargeManager = (RechargeManager)springContext.getBean("rechargeManager");
			
			//启动充值任务
			new Thread(new Runnable(){

				@Override
				public void run() {
					log.info("************************充值任务启动*******************");
					rechargeManager.startRecharge();
					log.info("************************充值任务启动成功*******************");
				}}).start();
			
			
		}
		
	}

}

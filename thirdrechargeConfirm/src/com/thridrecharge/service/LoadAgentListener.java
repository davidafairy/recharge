package com.thridrecharge.service;

import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.thridrecharge.service.entity.Agent;
import com.thridrecharge.service.entity.AreaCode;
import com.thridrecharge.service.memory.AgentMemory;
import com.thridrecharge.service.memory.AreaCodeMemory;
import com.thridrecharge.service.socketrecharge.AgentInterfaceDao;

public class LoadAgentListener implements ServletContextListener {

	private Log log = LogFactory.getLog(this.getClass());
	
	private int lastUpdateDate;
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		if(springContext != null){
			final AgentInterfaceDao agentInterfaceDao = (AgentInterfaceDao)springContext.getBean("agentInterfaceDao");
			List<Agent> agents = agentInterfaceDao.listAgents();
			AgentMemory agentMemory = AgentMemory.getAgentMemory();
			for (Agent agent : agents) {
				agentMemory.addAgent(agent);
			}
			
			List<AreaCode> areaCodes = agentInterfaceDao.listAreaCode();
			AreaCodeMemory.getAreaCodeMemeory().initPhoneMap(areaCodes);
			
			//每天重新加载agent list
			lastUpdateDate = Calendar.getInstance().getTime().getDate();
			new Thread(new Runnable(){

				@Override
				public void run() {
					while(true) {
						int currentDate = Calendar.getInstance().getTime().getDate();
						if (lastUpdateDate!=currentDate) {
							log.info("正在更新代理商Map");
							List<Agent> agents = agentInterfaceDao.listAgents();
							AgentMemory agentMemory = AgentMemory.getAgentMemory();
							for (Agent agent : agents) {
								agentMemory.addAgent(agent);
							}
							lastUpdateDate = currentDate;
							log.info("更新代理商Map成功");
						} else {
							try {
								Thread.currentThread().sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					
					
				}}).start();
		}
	}

}

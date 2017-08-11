package com.thridrecharge.service.ordermanager.callback;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.entity.OrderHis;
import com.thridrecharge.service.ordermanager.OrderDao;

@Controller
public class CallbackManager {

	private Log log = LogFactory.getLog("callback");
	
	@Autowired
	private OrderDao orderDao;
	
	public void startRecharge() {
		while(true) {
			try {
				//处理内存中已经完成的订单
				log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				List<OrderHis> orderHisList = CallbackPool.getInstance().getOrderHisList();
				log.info("@@@@@@@@@@@@@处理内存中已经回调完成的订单："+orderHisList.size());
				for (OrderHis orderHis : orderHisList) {
					
					//回调完成以后，只要清除order表即可
					orderDao.cleanOrderById(orderHis.getOrderId());
					log.info("@@@@@@@@@@@@@处理内存中已经完成的订单（手机号："+orderHis.getMobile()+";订单号:"+orderHis.getFlowNo());
					
				}
				log.info("@@@@@@@@@@@@@内存中已经完成的订单保存成功");
				
				//加载新的订单
				int idlesse = CallbackPool.getInstance().getIdlesseQueue();
				
				log.info("@@@@@@@@@@@@@总队列数："+CallbackPool.THREAD_QUEUE+";空闲队列数："+idlesse);
				log.info("@@@@@@@@@@@@@线程池是否空闲："+CallbackPool.getInstance().isStoped());
				if(idlesse > 0) {
					log.info("@@@@@@@@@@@@@加载新的待处理订单");
					List<Integer> agentIds = orderDao.getAgentIdList();
					if (agentIds.size() > 0) {
						String ids = "";
						for (Integer agentId : agentIds) {
							ids += String.valueOf(agentId)+",";
						}
						log.info("@@@@@@@@@@@@@符合条件代理商ID："+ids);
						List<Order> orders = orderDao.getCallbackOrders(idlesse,agentIds);
						log.info("@@@@@@@@@@@@@加载"+orders.size()+"条新的待处理订单");
						if (orders.size() > 0) {
							CallbackPool.getInstance().processCallback(orders);
							
						} else {
							log.info("@@@@@@@@@@@@@没有查询到可回调订单，等待2秒");
							try {
								Thread.currentThread().sleep(2000);
							} catch (InterruptedException e) {
								//Do nothing
							}
						}
					} else {
						log.info("@@@@@@@@@@@@@没有符合条件的代理商");
						log.info("@@@@@@@@@@@@@内存中没有空闲队列，等待2秒");
						try {
							Thread.currentThread().sleep(2000);
						} catch (InterruptedException e) {
							//Do nothing
						}
					}
					
				}else {
					log.info("@@@@@@@@@@@@@内存中没有空闲队列，等待2秒");
					try {
						Thread.currentThread().sleep(2000);
					} catch (InterruptedException e) {
						//Do nothing
					}
				}
			} catch (Exception e) {
				log.error("error",e);
				e.printStackTrace();
			}
			
			
			
		}
	}
	

}

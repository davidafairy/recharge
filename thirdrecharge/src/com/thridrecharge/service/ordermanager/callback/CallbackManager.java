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
				//�����ڴ����Ѿ���ɵĶ���
				log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				List<OrderHis> orderHisList = CallbackPool.getInstance().getOrderHisList();
				log.info("@@@@@@@@@@@@@�����ڴ����Ѿ��ص���ɵĶ�����"+orderHisList.size());
				for (OrderHis orderHis : orderHisList) {
					
					//�ص�����Ժ�ֻҪ���order����
					orderDao.cleanOrderById(orderHis.getOrderId());
					log.info("@@@@@@@@@@@@@�����ڴ����Ѿ���ɵĶ������ֻ��ţ�"+orderHis.getMobile()+";������:"+orderHis.getFlowNo());
					
				}
				log.info("@@@@@@@@@@@@@�ڴ����Ѿ���ɵĶ�������ɹ�");
				
				//�����µĶ���
				int idlesse = CallbackPool.getInstance().getIdlesseQueue();
				
				log.info("@@@@@@@@@@@@@�ܶ�������"+CallbackPool.THREAD_QUEUE+";���ж�������"+idlesse);
				log.info("@@@@@@@@@@@@@�̳߳��Ƿ���У�"+CallbackPool.getInstance().isStoped());
				if(idlesse > 0) {
					log.info("@@@@@@@@@@@@@�����µĴ�������");
					List<Integer> agentIds = orderDao.getAgentIdList();
					if (agentIds.size() > 0) {
						String ids = "";
						for (Integer agentId : agentIds) {
							ids += String.valueOf(agentId)+",";
						}
						log.info("@@@@@@@@@@@@@��������������ID��"+ids);
						List<Order> orders = orderDao.getCallbackOrders(idlesse,agentIds);
						log.info("@@@@@@@@@@@@@����"+orders.size()+"���µĴ�������");
						if (orders.size() > 0) {
							CallbackPool.getInstance().processCallback(orders);
							
						} else {
							log.info("@@@@@@@@@@@@@û�в�ѯ���ɻص��������ȴ�2��");
							try {
								Thread.currentThread().sleep(2000);
							} catch (InterruptedException e) {
								//Do nothing
							}
						}
					} else {
						log.info("@@@@@@@@@@@@@û�з��������Ĵ�����");
						log.info("@@@@@@@@@@@@@�ڴ���û�п��ж��У��ȴ�2��");
						try {
							Thread.currentThread().sleep(2000);
						} catch (InterruptedException e) {
							//Do nothing
						}
					}
					
				}else {
					log.info("@@@@@@@@@@@@@�ڴ���û�п��ж��У��ȴ�2��");
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

package com.thridrecharge.service.action;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.entity.RechargeCard;
import com.thridrecharge.service.enums.DealResult;
import com.thridrecharge.service.enums.RechargeCardStatus;
import com.thridrecharge.service.ordermanager.OrderDao;
import com.thridrecharge.service.ordermanager.OrderManager;
import com.thridrecharge.service.ordermanager.recharge.RechargeDao;
import com.thridrecharge.service.socketrecharge.AgentInterfaceManager;
import com.thridrecharge.service.utils.MD5Utils;

@Controller
@Scope("prototype")
public class CardCallbackAction extends ActionSupport {

	private Log log = LogFactory.getLog("recharge");
	
	@Autowired
	private AgentInterfaceManager agentInterfaceManager;
	
	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private RechargeDao rechargeDao;
	
	@Autowired
	private OrderDao orderDao;
	
	//�̻����
	public String userid;
	//����״̬ 0:��ֵ��;1:�������ɹ����Ʒѣ�ִ�г�ֵ���̳ɹ������������ճ�ֵ����ɹ���;9:�������ʧ�ܲ��Ʒ�
	public String ret_code;
	//�̻�������
	public String sporder_id;
	//����ʱ��
	public String ordersuccesstime;
	//ʵ����ֵ(Ĭ�ϲ����أ������÷���)
	public String parvalue;
	//��ֵ����룬�ж϶�����ֵ���������,���ӿ��ĵ�
	public String rechargeinfo;
	//�ص����ܴ� ��userid+ret_code+ sporder_id+key��������md5��32λ��д
	public String sign;
	
	/**
	 * �յ��ӿ�
	 */
	@Action(value = "cardRechargeCallback")
	public void recharge() {
		
		try {
			log.info("==============================���ճ�ֵ���ص�����============================");
			log.info("========userid��"+userid+"��");
			log.info("========ret_code��"+ret_code+"��");
			log.info("========sporder_id��"+sporder_id+"��");
			log.info("========ordersuccesstime��"+ordersuccesstime+"��");
			log.info("========parvalue��"+parvalue+"��");
			log.info("========rechargeinfo��"+rechargeinfo+"��");
			log.info("========sign��"+sign+"��");
			
			String sKey = MD5Utils.encodeByMD5(userid+ret_code+sporder_id+"Abcd1234");
			log.info("========sKey��"+sKey+"��");
			if (sKey.equalsIgnoreCase(sign)) {
				
				Order order = orderDao.findOrderByFlowNo(sporder_id);
				RechargeCard rechargeCard = rechargeDao.findOccupyCardByFlowNo(order.getFlowNo());
				
				order.setRechargeTime(Calendar.getInstance().getTime());
				
				//��ֵ�ɹ�
				if ("2000".equals(rechargeinfo) ||
						"2001".equals(rechargeinfo)||
						"2003".equals(rechargeinfo)) {
					
					order.setDealResult(DealResult.SUCCESS.intValue()); //��ֵ�ɹ�
					rechargeCard.setUseState(RechargeCardStatus.USED.intValue()); //��ʹ��
				} else {
					order.setDealResult(DealResult.FAULT.intValue()); //��ֵʧ��
					
					//�����쳣
					if ("3001".equals(rechargeinfo) ||
							"3002".equals(rechargeinfo)||
							"3003".equals(rechargeinfo)) {
						rechargeCard.setUseState(RechargeCardStatus.ERROR.intValue()); //���ܲ�����
					} else {
						rechargeCard.setUseState(RechargeCardStatus.UNUSE.intValue()); //δʹ��
					}
					
				}
				
				
				//�޸ĳ�ֵ��״̬
				rechargeDao.updateRechargeCard(rechargeCard);
				
				//�޸Ķ���״̬
				orderDao.saveOrder(order);
				
				log.info("========�����ص�����ɹ���FlowNo��"+sporder_id+"��");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("callback error:", e);
		}
		
			
			
			
	}

	
	
	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public AgentInterfaceManager getAgentInterfaceManager() {
		return agentInterfaceManager;
	}

	public void setAgentInterfaceManager(AgentInterfaceManager agentInterfaceManager) {
		this.agentInterfaceManager = agentInterfaceManager;
	}

	public OrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(OrderManager orderManager) {
		this.orderManager = orderManager;
	}



	public RechargeDao getRechargeDao() {
		return rechargeDao;
	}



	public void setRechargeDao(RechargeDao rechargeDao) {
		this.rechargeDao = rechargeDao;
	}



	public String getUserid() {
		return userid;
	}



	public void setUserid(String userid) {
		this.userid = userid;
	}



	public String getRet_code() {
		return ret_code;
	}



	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}



	public String getSporder_id() {
		return sporder_id;
	}



	public void setSporder_id(String sporder_id) {
		this.sporder_id = sporder_id;
	}



	public String getOrdersuccesstime() {
		return ordersuccesstime;
	}



	public void setOrdersuccesstime(String ordersuccesstime) {
		this.ordersuccesstime = ordersuccesstime;
	}



	public String getParvalue() {
		return parvalue;
	}



	public void setParvalue(String parvalue) {
		this.parvalue = parvalue;
	}



	public String getRechargeinfo() {
		return rechargeinfo;
	}



	public void setRechargeinfo(String rechargeinfo) {
		this.rechargeinfo = rechargeinfo;
	}



	public String getSign() {
		return sign;
	}



	public void setSign(String sign) {
		this.sign = sign;
	}

	public static void main(String[] args) {
		String sKey = MD5Utils.encodeByMD5("A14042301CHs012013218888000Abcd1234");
		System.out.println(sKey.equalsIgnoreCase("44E1BE566D9C37B6E4940BB433138559"));
	}
	
}

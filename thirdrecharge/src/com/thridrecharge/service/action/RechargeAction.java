package com.thridrecharge.service.action;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.thridrecharge.service.RechargeException;
import com.thridrecharge.service.entity.Agent;
import com.thridrecharge.service.entity.AreaCode;
import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.enums.ErrorCode;
import com.thridrecharge.service.enums.OrderChannel;
import com.thridrecharge.service.memory.AgentMemory;
import com.thridrecharge.service.memory.AreaCodeMemory;
import com.thridrecharge.service.ordermanager.OrderManager;
import com.thridrecharge.service.ordermanager.recharge.RechargeDao;
import com.thridrecharge.service.socketrecharge.AgentInterfaceManager;
import com.thridrecharge.service.utils.MD5Utils;

@Controller
@Scope("prototype")
public class RechargeAction extends ActionSupport {

	private Log log = LogFactory.getLog("order");
	
	@Autowired
	private AgentInterfaceManager agentInterfaceManager;
	
	@Autowired
	private OrderManager orderManager;
	
	@Autowired
	private RechargeDao rechargeDao;
	
	public String agentName;
	public String flowNo;
	public String phoneNo;
	public long money;
	public String sign;
	
	/**
	 * �յ��ӿ�
	 */
	@Action(value = "recharge")
	public void recharge() {
		log.info("==============================��������============================");
		log.info("========agentName��"+agentName+"��");
		log.info("========flowNo��"+flowNo+"��");
		log.info("========phoneNo��"+phoneNo+"��");
		log.info("========money��"+money+"��");
		log.info("========sign��"+sign+"��");
		try {
			
			
			//2.����ֵ�����������1000Ԫ��С��10Ԫ,��ֱ�ӱ���,�º��˺Ų������
			if (!"ch001".equals(agentName)&&(money > 100000 || money < 1000)) {
				log.info("========��ֵʧ�ܣ�ErrorCode:��"+ErrorCode.MONEYFAIL.getErrorCode()+"����Desc������������С��");
				jsonWrite(ErrorCode.MONEYFAIL);
				return;
			}
			
			Agent agent = agentInterfaceManager.findAgentByName(agentName);
			//3.���IP��ַ
			HttpServletRequest request = ServletActionContext.getRequest();
	        String ipAddress="";
	        if (null != request) {
	            ipAddress = getUserIpAddr(request); // ȡ�ͻ���IP��ַ
	            log.info("����ͻ��˵�IP��ַ:" + ipAddress);
	            if (!StringUtils.contains(agent.getIp(), ipAddress)) {
	            	log.info("========��ֵʧ�ܣ��Ƿ�IP��ַ=>"+ipAddress);
					jsonWrite(ErrorCode.SERVERUNAVAILABLE);
					return;
                }
	        }
	        
	        //4.���key�Ƿ���ȷ
			log.info("========��ʼ���ǩ��ԭsign����"+sign+"��========");
			String source = "agentName="+agentName+"&flowNo="+flowNo+"&phoneNo="+phoneNo+"&money="+money+"&key="+agent.getPassword();
			log.info("========��ʼ���ǩ��source����"+source+"��========");
			String realSign = MD5Utils.encodeByMD5(source);
			if (!realSign.equals(sign)) {
				log.info("========ǩ�����ʧ��");
				jsonWrite(ErrorCode.SERVERUNAVAILABLE);
				return;
			}
	        
			//5.��������״̬�Ƿ����
			log.info("========��ʼ��������״̬��agentName��"+agentName+"��========");
			boolean agentState = orderManager.checkAgentState(agent.getId());
			if (!agentState) {
				log.info("========��ֵʧ�ܣ��ô����̡�"+agentName+"����ʱ������");
				jsonWrite(ErrorCode.SERVERUNAVAILABLE);
				return;
			}
			
			//6.�����������
			log.info("========��ʼ����������agentName��"+agentName+"��========");
			if (agent.getBalance() <= 15000000) {
				log.info("========��ֵʧ�ܣ��ô����̡�"+agentName+"������");
				jsonWrite(ErrorCode.BALANCE);
				return;
			}
			
			//7.��ʼ������
			Order order = new Order();
			order.setAgentId(agent.getId());
			order.setCreateTime(Calendar.getInstance().getTime());
			order.setFlowNo(flowNo);
			order.setGroupNo(agent.getGroupno());
			order.setMobile(phoneNo);
			order.setMoney(money);
			order.setDealResult(0);
			order.setChannel(OrderChannel.ONLINE.intValue()); //����Ĭ������
			
			//8.��������
			if(!(phoneNo.startsWith("156510") || phoneNo.startsWith("156511")
					 || phoneNo.startsWith("156512") || phoneNo.startsWith("156513")
					 || phoneNo.startsWith("156514") || phoneNo.startsWith("156515")
					 || phoneNo.startsWith("156516") || phoneNo.startsWith("1709")) && agent.getRate() == 1) { //����18651���ε��û�
				
				//����ֵ���벻���������κŶΣ����Ҹô�����֧�����³�ֵ
				//����������м������³�ֵ���ʣ�ֻ���������ϣ���������
				boolean isOffline = agentInterfaceManager.checkRechargeRate(phoneNo);
				if (isOffline) {
					order.setChannel(OrderChannel.OFFLINE.intValue()); //���³�ֵ
				} 
			}
			//���ܳ�ֵ����
			String areaCodeDesc = AreaCodeMemory.getAreaCodeMemeory().getAgentCode(order.getMobile());
			AreaCode areaCode = rechargeDao.getAreaCode(areaCodeDesc);
			if (areaCode.getRechargeStrategy() == 3) {
				order.setChannel(OrderChannel.RECHARGECARD.intValue()); //���ܳ�ֵ
			}
			
			//9.��¼����
			log.info("========��ʼ��¼������phoneNo��"+phoneNo+"����money��"+money+"��========");
			orderManager.saveOrder(order);
			log.info("========������¼��ɣ�"+order);
			
			jsonWrite(ErrorCode.SUCCESS); //�ɹ�
			return;
			
				
		} catch(Exception e) {
			e.printStackTrace();
			log.info("========��������ʧ�ܣ�ErrorCode:��"+ErrorCode.PORTALFAIL.getErrorCode()+"����Desc����ǰ��ϵͳ����ʧ�ܡ�");
			jsonWrite(ErrorCode.PORTALFAIL);
			return;
		}
	}

	public void jsonWrite(ErrorCode error) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Pragma","no-cache"); 
		response.setDateHeader ("Expires", 0); 
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		try {
			PrintWriter out = response.getWriter();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("code", error.getErrorCode());
			jsonObj.put("msg", ErrorCode.getDesc(error.getErrorCode()));
			out.print(jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
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

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}
	
	/**
     * ��ȡIP��ַ�ķ���
     * @param request
     * @return
     */
    private String getUserIpAddr(HttpServletRequest request) {
        //��ȡ��������Ŀͻ��˵�IP��ַ; �ų���request.getRemoteAddr() ���� ��ͨ����Apache,Squid�ȷ����������Ͳ��ܻ�ȡ���ͻ��˵���ʵIP��ַ��
    	if (request.getHeader("x-forwarded-for") == null) {
    		return request.getRemoteAddr();
    	}
    	return request.getHeader("x-forwarded-for");

    }
}

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
import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.enums.ErrorCode;
import com.thridrecharge.service.memory.AgentMemory;
import com.thridrecharge.service.ordermanager.OrderManager;
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
	
	public String agentName;
	public String flowNo;
	public String phoneNo;
	public long money;
	public String sign;
	
	@Action(value = "recharge")
	public void recharge() {
		log.info("==============================接收请求：============================");
		log.info("========agentName【"+agentName+"】");
		log.info("========flowNo【"+flowNo+"】");
		log.info("========phoneNo【"+phoneNo+"】");
		log.info("========money【"+money+"】");
		log.info("========sign【"+sign+"】");
		try {
			
			//检查IP地址
			HttpServletRequest request = ServletActionContext.getRequest();
	        String ipAddress="";
	        boolean flag = false;
	        if (null != request) {
	        	List<String> ipList = AgentMemory.getAgentMemory().getAllIp();
	            ipAddress = getUserIpAddr(request); // 取客户端IP地址
	            log.info("请求客户端的IP地址:" + ipAddress);
	            for (String s : ipList) {
	                if (StringUtils.contains(s, ipAddress)) {
	                    flag = true;
	                    break;
	                }
	            }
	        }
	        if(!flag) {
	        	log.info("========充值失败：非法IP地址=>"+ipAddress);
				jsonWrite(ErrorCode.SERVERUNAVAILABLE);
				return;
	        }
	        
	        //检查屏蔽号段
			if(!"ch001".equals(agentName)&&(phoneNo.startsWith("156510") || phoneNo.startsWith("156511")
					 || phoneNo.startsWith("156512") || phoneNo.startsWith("156513")
					 || phoneNo.startsWith("156514") || phoneNo.startsWith("156515")
					 || phoneNo.startsWith("156516") || phoneNo.startsWith("1709"))) { //屏蔽18651网段的用户
				log.info("========充值失败：暂时不支持15651、1709网段的用户");
				jsonWrite(ErrorCode.SERVERUNAVAILABLE);
				return;
			}
			
			//如果金额大于1000元或小于10元,则直接报错
			if (!"ch001".equals(agentName)&&(money > 100000 || money < 1000)) {
				log.info("========充值失败：ErrorCode:【"+ErrorCode.MONEYFAIL.getErrorCode()+"】，Desc：【金额过大或过小】");
				jsonWrite(ErrorCode.MONEYFAIL);
				return;
			}
			
			AgentMemory agentMemory = AgentMemory.getAgentMemory();
			Agent agent = agentMemory.getAgent(agentName);
			
			try {
				
				//检查代理商最小可充值金额
//				log.info("========开始检查代理商最小可充值金额：agentName【"+agentName+"】========");
//				boolean rechargeAmountState = orderManager.checkRechargeAmount(agent.getId(),money);
//				if(!rechargeAmountState) {
//					log.info("========充值失败：ErrorCode:【"+ErrorCode.MONEYFAIL.getErrorCode()+"】，Desc：【金额低于代理商最小可充值金额】");
//					return ErrorCode.MONEYFAIL.getErrorCode();
//				}
				
				//非陈宏账号,必须大于10元
				if(agent.getId() != 731 && money == 1000){
					log.info("========充值失败：ErrorCode:【"+ErrorCode.MONEYFAIL.getErrorCode()+"】，Desc：【金额过大或过小】");
					jsonWrite(ErrorCode.MONEYFAIL);
					return;
				}
				
				//检查key是否正确
				log.info("========开始检查签名原sign：【"+sign+"】========");
				String source = "agentName="+agentName+"&flowNo="+flowNo+"&phoneNo="+phoneNo+"&money="+money+"&key="+agent.getPassword();
				log.info("========开始检查签名source：【"+source+"】========");
				String realSign = MD5Utils.encodeByMD5(source);
				if (!realSign.equals(sign)) {
					log.info("========签名检查失败");
					jsonWrite(ErrorCode.SERVERUNAVAILABLE);
					return;
				}
				
				//检查代理商状态是否可用
				log.info("========开始检查代理商状态：agentName【"+agentName+"】========");
				boolean agentState = orderManager.checkAgentState(agent.getId());
				if (!agentState) {
					log.info("========充值失败：该代理商【"+agentName+"】暂时不可用");
					jsonWrite(ErrorCode.SERVERUNAVAILABLE);
					return;
				}
				
				//检查代理商余额
				log.info("========开始检查代理商余额：agentName【"+agentName+"】========");
				boolean agentBalance = orderManager.checkAgentBalance(agent.getId());
				if (!agentBalance) {
					log.info("========充值失败：该代理商【"+agentName+"】余额不足");
					jsonWrite(ErrorCode.BALANCE);
					return;
				}
				
				//开始充值
				log.info("========开始记录订单：phoneNo【"+phoneNo+"】，money【"+money+"】========");
				Order order = new Order();
				order.setAgentId(agent.getId());
				order.setCreateTime(Calendar.getInstance().getTime());
				order.setFlowNo(flowNo);
				order.setGroupNo(agent.getGroupno());
				order.setMobile(phoneNo);
				order.setMoney(money);
				orderManager.saveOrder(order);
//				agentInterfaceManager.deducting(agent.getId(),flowNo,agent.getGroupno(),phoneNo, money);
				log.info("========订单记录完成：phoneNo【"+phoneNo+"】，money【"+money+"】========");
				
				jsonWrite(ErrorCode.SUCCESS); //成功
				return;
			} catch (RechargeException e) {
				log.info("========订单接收失败：ErrorCode:【"+e.getErrorCode()+"】，Desc：【"+ErrorCode.getDesc(e.getErrorCode())+"】");
				jsonWrite(ErrorCode.SERVERUNAVAILABLE);
				return;
			}
		} catch(Exception e) {
			e.printStackTrace();
			log.info("========订单接收失败：ErrorCode:【"+ErrorCode.PORTALFAIL.getErrorCode()+"】，Desc：【前置系统处理失败】");
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
     * 获取IP地址的方法
     * @param request
     * @return
     */
    private String getUserIpAddr(HttpServletRequest request) {
        //获取经过代理的客户端的IP地址; 排除了request.getRemoteAddr() 方法 在通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址了
    	if (request.getHeader("x-forwarded-for") == null) {
    		return request.getRemoteAddr();
    	}
    	return request.getHeader("x-forwarded-for");

    }
}

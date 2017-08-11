package com.thridrecharge.service.action;


import java.io.PrintWriter;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.entity.OrderHis;
import com.thridrecharge.service.enums.ErrorCode;
import com.thridrecharge.service.ordermanager.OrderManager;
import com.thridrecharge.service.socketrecharge.AgentInterfaceManager;

@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport {

private Log log = LogFactory.getLog("order");
	
	@Autowired
	private AgentInterfaceManager agentInterfaceManager;
	
	@Autowired
	private OrderManager orderManager;
	
	public String flowNo;
	/**
	 * 
	 * @param flowNo
	 * @return
	 * 0:没有该订单
	 * 1:排队中
	 * 2:充值中
	 * 3:充值成功
	 * 4:充值失败
	 * 5:其他异常
	 * 6:可疑
	 */
	@Action(value = "orderConfirm")
	public void searchRechargeResult() {
		log.info("========订单查询:"+flowNo);
		if (StringUtils.isBlank(flowNo)) {
			log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【订单号为空】");
			jsonWrite(0,"没有该订单");
			return;
		}
		Order order = orderManager.searchOrder(flowNo);
		if (null != order) {
			if (order.getStatus() == 1) {
				log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【排队中】");
				jsonWrite(1,"排队中");
			}
			if (order.getStatus() == 2) {
				log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【充值中】");
				jsonWrite(2,"充值中");
			} 
			if (order.getStatus() == 3) {
				log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【正在回调】");
				jsonWrite(2,"充值中");
			} 
			if (order.getStatus() == 4) {
				log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【正在回调】");
				jsonWrite(2,"充值中");
			}
			log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【其他异常】");
			jsonWrite(5,"其他异常");
		}else {
			OrderHis orderHis = orderManager.searchOrderHis(flowNo);
			if (null != orderHis) {
				if(orderHis.getResult() == 1) {
					log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【充值成功】");
					jsonWrite(3,"充值成功");
				}
				if(orderHis.getResult() == 2) {
					if (orderHis.getErrorCode() == ErrorCode.TIMEOUT.getErrorCode()) {
						log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【可疑】");
						jsonWrite(6,"可疑");//可疑
					}
					log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【充值失败】");
					jsonWrite(4,"充值失败");
				}
				log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【其他异常】");
				jsonWrite(5,"其他异常");
			} else {
				log.info("========订单查询结果:flowNo:【"+flowNo+"】,result:【没有该订单】");
				jsonWrite(0,"没有该订单");
			}
		}
	}

	public void jsonWrite(int errorCode,String msg) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Pragma","no-cache"); 
		response.setDateHeader ("Expires", 0); 
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		try {
			PrintWriter out = response.getWriter();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("code", errorCode);
			jsonObj.put("msg", msg);
			out.print(jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

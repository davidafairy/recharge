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
	 * 0:û�иö���
	 * 1:�Ŷ���
	 * 2:��ֵ��
	 * 3:��ֵ�ɹ�
	 * 4:��ֵʧ��
	 * 5:�����쳣
	 * 6:����
	 */
	@Action(value = "orderConfirm")
	public void searchRechargeResult() {
		log.info("========������ѯ:"+flowNo);
		if (StringUtils.isBlank(flowNo)) {
			log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:��������Ϊ�ա�");
			jsonWrite(0,"û�иö���");
			return;
		}
		Order order = orderManager.searchOrder(flowNo);
		if (null != order) {
			if (order.getStatus() == 1) {
				log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:���Ŷ��С�");
				jsonWrite(1,"�Ŷ���");
			}
			if (order.getStatus() == 2) {
				log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:����ֵ�С�");
				jsonWrite(2,"��ֵ��");
			} 
			if (order.getStatus() == 3) {
				log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:�����ڻص���");
				jsonWrite(2,"��ֵ��");
			} 
			if (order.getStatus() == 4) {
				log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:�����ڻص���");
				jsonWrite(2,"��ֵ��");
			}
			log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:�������쳣��");
			jsonWrite(5,"�����쳣");
		}else {
			OrderHis orderHis = orderManager.searchOrderHis(flowNo);
			if (null != orderHis) {
				if(orderHis.getResult() == 1) {
					log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:����ֵ�ɹ���");
					jsonWrite(3,"��ֵ�ɹ�");
				}
				if(orderHis.getResult() == 2) {
					if (orderHis.getErrorCode() == ErrorCode.TIMEOUT.getErrorCode()) {
						log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:�����ɡ�");
						jsonWrite(6,"����");//����
					}
					log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:����ֵʧ�ܡ�");
					jsonWrite(4,"��ֵʧ��");
				}
				log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:�������쳣��");
				jsonWrite(5,"�����쳣");
			} else {
				log.info("========������ѯ���:flowNo:��"+flowNo+"��,result:��û�иö�����");
				jsonWrite(0,"û�иö���");
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

package com.redstoneinfo.platform.biz.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.redstoneinfo.platform.annotation.Pagination;
import com.redstoneinfo.platform.bean.PageBean;
import com.redstoneinfo.platform.biz.dao.OrderDao;
import com.redstoneinfo.platform.entity.Agent;
import com.redstoneinfo.platform.entity.OrderHis;
import com.redstoneinfo.platform.ssh.RSBaseAction;
import com.redstoneinfo.platform.utils.DateUtil;

@Controller
@Scope("prototype")
@ParentPackage("orderQuery")
@Results({ @Result(name = "orderList", location = "biz/orderList.jsp"),
			@Result(name = "orderHisList", location = "biz/orderHisList.jsp"),
})
public class OrderAction extends RSBaseAction {

	@Autowired
	private OrderDao orderDao;
	
	@Action("orderList")
	public String orderList() {
		return "orderList";
	}
	
	@Action(value = "getOrderList")
	@Pagination
	public String getOrderList(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Agent agent = (Agent)request.getSession().getAttribute("currentAgent");
		
		PageBean pageBean = getPageBean();
		if (!"admin".equals(agent.getLoginName())) {
			Map<String,String> condition = pageBean.getCondition();
			condition.put("eq.agentId", String.valueOf(agent.getId()));
		}
		
		//只需要调用查询，框架自动实现了分页
		orderDao.listOrders(pageBean);
		return null;
	}
	
	@Action("orderHisList")
	public String orderHisList() {
		return "orderHisList";
	}
	
	@Action(value = "getOrderHisList")
	@Pagination
	public String getOrderHisList(){
		//只需要调用查询，框架自动实现了分页
		HttpServletRequest request = ServletActionContext.getRequest();
		Agent agent = (Agent)request.getSession().getAttribute("currentAgent");
		
		PageBean pageBean = getPageBean();
		if (!"admin".equals(agent.getLoginName())) {
			Map<String,String> condition = pageBean.getCondition();
			condition.put("eq.agentId", String.valueOf(agent.getId()));
		}
		orderDao.listOrderHisList(pageBean);
		return null;
	}
	
	
}

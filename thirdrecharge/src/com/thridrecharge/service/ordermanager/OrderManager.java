package com.thridrecharge.service.ordermanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.entity.OrderHis;

@Controller
@Transactional
public class OrderManager {

	@Autowired
	private OrderDao orderDao;
	
	public void saveOrder(Order order) {
		orderDao.saveOrder(order);
	}
	
	public boolean checkAgentState(int agentId) {
		return orderDao.checkAgentState(agentId);
	}
	
	public boolean checkAgentBalance(int agentId) {
		return orderDao.checkAgentBalance(agentId);
	}
	
	public boolean checkRechargeAmount(int agentId,long amount) {
		return orderDao.checkRechargeAmount(agentId,amount);
	}
	
	public Order searchOrder(String flowNo) {
		return orderDao.findOrderByFlowNo(flowNo);
	}
	
	public OrderHis searchOrderHis(String flowNo) {
		return orderDao.findOrderHisByFlowNo(flowNo);
	}
}

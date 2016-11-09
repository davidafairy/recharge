package com.redstoneinfo.platform.biz.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.redstoneinfo.platform.bean.PageBean;
import com.redstoneinfo.platform.entity.Order;
import com.redstoneinfo.platform.entity.OrderHis;
import com.redstoneinfo.platform.enums.OrderType;
import com.redstoneinfo.platform.ssh.RSBaseDao;

@Repository
public class OrderDao extends RSBaseDao {

	public void listOrders(PageBean pageBean) {
		this.listWithPageBean(Order.class, pageBean, "createTime",OrderType.DESC);
	}
	
	public void listOrderHisList(PageBean pageBean) {
		
		this.listWithPageBean(OrderHis.class, pageBean, "createTime",OrderType.DESC);
	}
	
	public List listOrderHisListWithCondition(Map<String,String> condition){
		return this.listWithCondition(OrderHis.class, condition, "rechargeTime",OrderType.DESC);
	}
}

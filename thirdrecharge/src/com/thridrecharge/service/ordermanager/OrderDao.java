package com.thridrecharge.service.ordermanager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thridrecharge.service.entity.Agent;
import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.entity.OrderHis;
import com.thridrecharge.service.enums.OrderChannel;
import com.thridrecharge.service.enums.OrderStatus;

@Repository
@Transactional
public class OrderDao extends HibernateDaoSupport {
	
	@Autowired
	public void setSf(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public void saveOrder(Order order) {
		this.getHibernateTemplate().saveOrUpdate(order);
	}
	
	public void updateOrder(Order order) {
		this.getHibernateTemplate().update(order);
	}
	
	public List<Order> getTop10Order() {
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria crit = session.createCriteria(Order.class);
				
				//排序方法
				crit.addOrder(org.hibernate.criterion.Order.asc("id"));
				
				//分页
				crit.setMaxResults(10);
				List list = crit.list();
				return list;
			}
		});
		return list;
	}
	
	//获取可以充值代理商ID
	//余额必须大于15000，状态是可用(1)
	public List<Integer> getAgentIdList() {
		List<Integer> agentIds = new ArrayList<Integer>();
		List<Agent> agents = this.getHibernateTemplate().loadAll(Agent.class);
		for (Agent agent : agents) {
			//余额中加入预充值金额
			if (agent.getPreCharge()>0) {
				agent.setBalance(agent.getBalance() + agent.getPreCharge());
				agent.setPreCharge(0);
				this.getHibernateTemplate().update(agent);
			}
			
			if (agent.getBalance() >15000000 && agent.getState() == 1) {
				agentIds.add(agent.getId());
			}
		}
		return agentIds;
	}
	
	//获取待回调订单
	public List<Order> getCallbackOrders(final int num,final List<Integer> agentIds) {
		
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria crit = session.createCriteria(Order.class);
				
				//排序方法
				crit.addOrder(org.hibernate.criterion.Order.asc("id"));
				crit.add(Property.forName("agentId").in(agentIds));
				crit.add(Restrictions.eq("status",OrderStatus.SUCCESS.intValue()));   //查询充值已完成的
				//分页
				crit.setMaxResults(num);
				List list = crit.list();
				for (int i=0;i<list.size();i++) {
					Order order = (Order)list.get(i);
					order.setStatus(OrderStatus.CALLBACK.intValue());  //回调中
				}
				return list;
			}
		});
//		this.getHibernateTemplate().saveOrUpdateAll(list);
		return list;
	}
	
	//获取用充值卡充值的订单
	public List<Order> getCardRechargeOrders(final int num,final List<Integer> agentIds) {
		
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria crit = session.createCriteria(Order.class);
				
				//排序方法
				crit.addOrder(org.hibernate.criterion.Order.asc("id"));
				crit.add(Property.forName("agentId").in(agentIds));
				crit.add(Restrictions.eq("status",OrderStatus.RECEIVE.intValue()));   //查询待充值
				crit.add(Restrictions.eq("channel",OrderChannel.RECHARGECARD.intValue()));   //渠道3表示充值卡充值
				//分页
				crit.setMaxResults(num);
				List list = crit.list();
				for (int i=0;i<list.size();i++) {
					Order order = (Order)list.get(i);
					order.setStatus(OrderStatus.RECHARGEING.intValue());  //充值中
				}
				return list;
			}
		});
		this.getHibernateTemplate().saveOrUpdateAll(list);
		return list;
	}

	public void cleanOrder(Order order) {
		this.getHibernateTemplate().delete(order);
	}
	
	public void cleanOrderById(final int orderId) {
		final String sql = "delete Order where id = ?";
		this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.createQuery(sql).setInteger(0, orderId).executeUpdate();
				return null;
			}
			
		});
	}
	
	public void saveOrderHis(OrderHis orderHis) {
		this.getHibernateTemplate().saveOrUpdate(orderHis);
	}
	
	public boolean checkAgentState(int agentId) {
		Agent agent = this.getHibernateTemplate().load(Agent.class, agentId);
		if (agent.getState() == 2) { //准备启动
			changeAgentState(agentId,1);   //启动成功
		}
		if(agent.getState() == 3) { //准备停止
			changeAgentState(agentId,4);  //已停止
			return false;
		}
		if (agent.getState() == 4) {//以停止
			return false;
		}
		return true;
	}
	
	public boolean checkAgentBalance(int agentId) {
		Agent agent = this.getHibernateTemplate().load(Agent.class, agentId);
		if(agent.getBalance() > 15000000)
			return true;
		return false;
	}
	
	public boolean checkRechargeAmount(int agentId,long amount) {
		Agent agent = this.getHibernateTemplate().load(Agent.class, agentId);
		if(agent.getMinAmount() < amount)
			return true;
		return false;
		
	}
	
	public OrderHis findOrderHisByFlowNo(String flowNo) {
		List<OrderHis> orderHisList = (List<OrderHis>)this.getHibernateTemplate().find("from OrderHis where flowNo = ? ", flowNo);
		if (orderHisList.size() > 0) {
			return orderHisList.get(0);
		}
		return null;
	}
	
	public Order findOrderByFlowNo(String flowNo) {
		List<Order> orderList = (List<Order>)this.getHibernateTemplate().find("from Order where flowNo = ? ", flowNo);
		if (orderList.size() > 0) {
			return orderList.get(0);
		}
		return null;
	}
	
	public void changeAgentState(int agentId,long state) {
		final String sql = "update Agent set state = "+state+" where id = "+agentId;
		this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.createQuery(sql).executeUpdate();
				return null;
			}
			
		});
	}
}

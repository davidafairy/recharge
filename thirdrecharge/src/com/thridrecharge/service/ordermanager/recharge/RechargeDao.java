package com.thridrecharge.service.ordermanager.recharge;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.thridrecharge.service.RechargeException;
import com.thridrecharge.service.entity.Agent;
import com.thridrecharge.service.entity.AreaCode;
import com.thridrecharge.service.entity.DealRecord;
import com.thridrecharge.service.entity.Order;
import com.thridrecharge.service.entity.OrderHis;
import com.thridrecharge.service.entity.RechargeCard;
import com.thridrecharge.service.enums.ErrorCode;
import com.thridrecharge.service.enums.OrderChannel;
import com.thridrecharge.service.enums.OrderStatus;
import com.thridrecharge.service.enums.RechargeCardStatus;
import com.thridrecharge.service.memory.AgentMemory;

@Repository
@Transactional
public class RechargeDao extends HibernateDaoSupport {

	private Log log = LogFactory.getLog("recharge");
	
	@Autowired
	public void setSf(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public AreaCode getAreaCode(String description) {
		String hql = "from AreaCode where description = ?";
		List<AreaCode> areaCodes = (List<AreaCode>)this.getHibernateTemplate().find(hql, description);
		if (areaCodes.size() > 0) {
			return areaCodes.get(0);
		}
		return null;
	}
	
	public int findUnUsedCardNum(long amount) {
		String hql = "from RechargeCard where amount = ? and usestate = 1";
		List<RechargeCard> cardList = (List<RechargeCard>)this.getHibernateTemplate().find(hql, amount);
		return cardList.size();
	}
	
	public  RechargeCard getRechargeCard(final long amount) {
		
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria crit = session.createCriteria(RechargeCard.class);
				
				//排序方法
				crit.add(Restrictions.eq("amount",amount));   //查询待充值
				crit.add(Restrictions.eq("useState",RechargeCardStatus.UNUSE.intValue()));   //渠道3表示充值卡充值
				//分页
				crit.setMaxResults(1);
				List list = crit.list();
				
				return list;
			}
		});
		
		if (list.size() > 0) {
			RechargeCard rechargeCard = (RechargeCard)list.get(0);
			
			rechargeCard.setUseState(RechargeCardStatus.OCCUPY.intValue());  //预占
			
			this.getHibernateTemplate().saveOrUpdate(rechargeCard);
			
			return rechargeCard;
		}
		
		return null;
		
	}
	
	//根据号码查询被占用的充值卡
	public RechargeCard findOccupyCardByFlowNo(String flowNo) {
		String hql = "from RechargeCard where useState = 3 and flowno = ?";
		List<RechargeCard> areaCodes = (List<RechargeCard>)this.getHibernateTemplate().find(hql, flowNo);
		if (areaCodes.size() == 1) {
			return areaCodes.get(0);
		}
		if (areaCodes.size() > 1) {
			log.error("同一个订单占用了多张充值卡:flowNo=="+flowNo);
		}
		return null;
	}
	
	public void updateRechargeCard(RechargeCard rechargeCard) {
		this.getHibernateTemplate().update(rechargeCard);
	}
	
	public void saveDealRecord(DealRecord dealRecord) {
		this.getHibernateTemplate().save(dealRecord);
	}
	
	/**
	 * 从余额中扣款
	 * @param agentId 代理商ID
	 * @param money 扣款金额
	 * @return int
	 */
	public void deducting(int agentId,long money,OrderHis orderHis) throws RechargeException {
		
		try {
			Agent agent = this.getHibernateTemplate().load(Agent.class, agentId);
			
			//先做续费
			long newBalance = agent.getBalance();
			if (agent.getPreCharge()!=0) {  //如果预充值有钱，则存到余额里面去，并且把预充值金额清空
				newBalance = agent.getBalance() + agent.getPreCharge();
			}
			
			//再做扣款
			Agent memAgent = AgentMemory.getAgentMemory().getAgent(agent.getLoginName());
			Double dMoney = (Double)(money*10*memAgent.getDiscount());
			long lMoney = dMoney.longValue();
			newBalance = newBalance - lMoney;
			
			if (newBalance >= 0) {
				saveNewBalance(agentId,newBalance);
				orderHis.setAmount(lMoney);
				orderHis.setBalance(newBalance);
				log.info("********扣款：代理商：【"+agentId+"】，折扣：【"+memAgent.getDiscount()+"】，扣款：【"+lMoney+"】，余额【"+newBalance+"】********");
				
			} else {
				throw new RechargeException(ErrorCode.BALANCE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof RechargeException) {
				throw (RechargeException)e;
			} else {
				throw new RechargeException(ErrorCode.SERVERUNAVAILABLE);
			}
			
		}
		
	}
	
	public void saveNewBalance(int agentId,long newBalance) {
		final String sql = "update Agent set balance = "+newBalance+",PRE_CHARGE=0  where id = "+agentId;
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

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
				
				//���򷽷�
				crit.add(Restrictions.eq("amount",amount));   //��ѯ����ֵ
				crit.add(Restrictions.eq("useState",RechargeCardStatus.UNUSE.intValue()));   //����3��ʾ��ֵ����ֵ
				//��ҳ
				crit.setMaxResults(1);
				List list = crit.list();
				
				return list;
			}
		});
		
		if (list.size() > 0) {
			RechargeCard rechargeCard = (RechargeCard)list.get(0);
			
			rechargeCard.setUseState(RechargeCardStatus.OCCUPY.intValue());  //Ԥռ
			
			this.getHibernateTemplate().saveOrUpdate(rechargeCard);
			
			return rechargeCard;
		}
		
		return null;
		
	}
	
	//���ݺ����ѯ��ռ�õĳ�ֵ��
	public RechargeCard findOccupyCardByFlowNo(String flowNo) {
		String hql = "from RechargeCard where useState = 3 and flowno = ?";
		List<RechargeCard> areaCodes = (List<RechargeCard>)this.getHibernateTemplate().find(hql, flowNo);
		if (areaCodes.size() == 1) {
			return areaCodes.get(0);
		}
		if (areaCodes.size() > 1) {
			log.error("ͬһ������ռ���˶��ų�ֵ��:flowNo=="+flowNo);
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
	 * ������пۿ�
	 * @param agentId ������ID
	 * @param money �ۿ���
	 * @return int
	 */
	public void deducting(int agentId,long money,OrderHis orderHis) throws RechargeException {
		
		try {
			Agent agent = this.getHibernateTemplate().load(Agent.class, agentId);
			
			//��������
			long newBalance = agent.getBalance();
			if (agent.getPreCharge()!=0) {  //���Ԥ��ֵ��Ǯ����浽�������ȥ�����Ұ�Ԥ��ֵ������
				newBalance = agent.getBalance() + agent.getPreCharge();
			}
			
			//�����ۿ�
			Agent memAgent = AgentMemory.getAgentMemory().getAgent(agent.getLoginName());
			Double dMoney = (Double)(money*10*memAgent.getDiscount());
			long lMoney = dMoney.longValue();
			newBalance = newBalance - lMoney;
			
			if (newBalance >= 0) {
				saveNewBalance(agentId,newBalance);
				orderHis.setAmount(lMoney);
				orderHis.setBalance(newBalance);
				log.info("********�ۿ�����̣���"+agentId+"�����ۿۣ���"+memAgent.getDiscount()+"�����ۿ��"+lMoney+"������"+newBalance+"��********");
				
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

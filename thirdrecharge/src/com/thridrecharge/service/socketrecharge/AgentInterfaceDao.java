package com.thridrecharge.service.socketrecharge;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.thridrecharge.service.entity.Agent;
import com.thridrecharge.service.entity.AreaCode;

@Repository
public class AgentInterfaceDao extends HibernateDaoSupport {

	@Autowired
	public void setSf(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public List<Agent> listAgents() {
		return this.getHibernateTemplate().loadAll(Agent.class);
	}
	
	public List<AreaCode> listAreaCode() {
		return this.getHibernateTemplate().loadAll(AreaCode.class);
	}
	
	public Agent findAgentById(int id) {
		return this.getHibernateTemplate().load(Agent.class, id);
	}
	
	public Agent findAgentByName(String agentName) {
		String hql = "from Agent where loginName = ?";
		List<Agent> agents = this.getHibernateTemplate().find(hql, agentName);
		if (agents.size() > 0) {
			return agents.get(0);
		}
		return null;
	}
	
	
	public int getAreaSuccessRate(String description) {
		String hql = "from AreaCode where description = ?";
		List<AreaCode> areaCodes = (List<AreaCode>)this.getHibernateTemplate().find(hql, description);
		if (areaCodes.size() > 0) {
			return areaCodes.get(0).getRate();
		}
		return 0;
	}
	
	public AreaCode getAreaCode(String description) {
		String hql = "from AreaCode where description = ?";
		List<AreaCode> areaCodes = (List<AreaCode>)this.getHibernateTemplate().find(hql, description);
		if (areaCodes.size() > 0) {
			return areaCodes.get(0);
		}
		return null;
	}
}

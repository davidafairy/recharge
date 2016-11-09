package com.redstoneinfo.platform.biz.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.redstoneinfo.platform.entity.Agent;
import com.redstoneinfo.platform.ssh.RSBaseDao;
import com.redstoneinfo.platform.utils.MD5Util;

@Repository
public class AgentDao extends RSBaseDao {

	public Agent validate(Agent longAgent) {
		String hql = "from Agent where loginName = ? and searchPwd = ?";
		List agents = this.getHibernateTemplate().find(hql, longAgent.getLoginName(),MD5Util.encode(longAgent.getPassword()));
		if (agents.size() > 0) {
			return (Agent)agents.get(0);
		} else {
			return null;
		}
	}
	
	public boolean updateAgentPassword(long agentId,String newPassword) {
		final String updateHql = "update biz_agent set searchPwd = '" + newPassword+"' where id = " + agentId;
		return this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.createSQLQuery(updateHql).executeUpdate();
				return true;
			}
			
		});
	}
	
	public List<Agent> listAgents() {
		return this.getHibernateTemplate().loadAll(Agent.class);
	}
}

package com.redstoneinfo.platform.biz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.redstoneinfo.platform.bean.PageBean;
import com.redstoneinfo.platform.entity.CheckingFile;
import com.redstoneinfo.platform.entity.Order;
import com.redstoneinfo.platform.enums.OrderType;
import com.redstoneinfo.platform.ssh.RSBaseDao;

@Repository
public class CheckingDao extends RSBaseDao {

	public CheckingFile findLastCheckingFile(String agentName) {
		String hql = "from CheckingFile where agentName like ? order by id desc";
		List list = this.getHibernateTemplate().find(hql, agentName);
		if (list.size() > 0) {
			return (CheckingFile)list.get(0);
		} 
		return null;
	}
	
	public void saveCheckingFile(CheckingFile cf) {
		this.getHibernateTemplate().saveOrUpdate(cf);
	}
	
	public void listChecks(PageBean pageBean) {
		this.listWithPageBean(CheckingFile.class, pageBean, "genTime",OrderType.DESC);
	}
	
	public CheckingFile getCheckById(int checkId) {
		return this.getHibernateTemplate().load(CheckingFile.class, checkId);
	}
}

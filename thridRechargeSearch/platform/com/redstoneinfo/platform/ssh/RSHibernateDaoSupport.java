package com.redstoneinfo.platform.ssh;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * HibernateDao基类，所有HibernateDao都要继承该基类
 * @author davidafairy
 *
 */
public class RSHibernateDaoSupport extends HibernateDaoSupport {

	@Autowired
	public void setSf(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}

package com.redstoneinfo.platform.ssh;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.redstoneinfo.platform.bean.PageBean;
import com.redstoneinfo.platform.enums.OrderType;

public abstract class RSBaseDao extends HibernateDaoSupport {

	private List<String> aliasList = new ArrayList<String>();
	
	@Autowired
	public void setSf(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public void list(Class clazz, PageBean pageBean) {
		
		listAndOrder(clazz, pageBean, null, 1);
	}

	/*
	 * orderField 这个是Class的属性名，大小写敏感
	 */
	public void listAndDescOrder(Class clazz, PageBean pageBean, String orderField){
		listAndOrder(clazz, pageBean, orderField, 1);
	}

	/*
	 * orderField 这个是Class的属性名，大小写敏感
	 */
	public void listAndAscOrder(Class clazz, PageBean pageBean, String orderField){
		listAndOrder(clazz, pageBean, orderField, 2);
	}
	
	/*
	 * orderField 这个是Class的属性名，大小写敏感
	 * orderType 如果值为1表示是降序，否则是升序
	 */
	private void listAndOrder(Class clazz, PageBean pageBean, String orderField, int orderType) {
		
		int size = this.getHibernateTemplate().loadAll(clazz).size();
		
		final int first = (pageBean.getPage() -1) * pageBean.getRows();
		final int maxResults = pageBean.getRows();
		String str = "from " + clazz.getName();
		if (orderField != null && !orderField.trim().equals("")){
			str = str + " order by " + orderField;
			if (orderType == 1)
				str = str + " desc ";
			else 
				str = str + " asc ";
		}
		final String hql = str;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(first);
				query.setMaxResults(maxResults);
				List list = query.list();
				return list;
			}
		});
		
		pageBean.setTotal(size);
		pageBean.setResult(list);
	}
	
	public void listAndOrder(String  hqlStr, PageBean pageBean, String orderField, int orderType) {
		
		int size = this.getHibernateTemplate().find(hqlStr).size();
		
		final int first = (pageBean.getPage() -1) * pageBean.getRows();
		final int maxResults = pageBean.getRows();
		
		if (orderField != null && !orderField.trim().equals("")){
			hqlStr =hqlStr + " order by " + orderField;
			if (orderType == 1)
				hqlStr = hqlStr + " desc ";
			else 
				hqlStr = hqlStr + " asc ";
		}
		final String hql = hqlStr;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(first);
				query.setMaxResults(maxResults);
				List list = query.list();
				return list;
			}
		});
		
		pageBean.setTotal(size);
		pageBean.setResult(list);
	}
	
	/*
	 * orderField 这个是Class的属性名，大小写敏感
	 * orderType 如果值为1表示是降序，否则是升序
	 */
	public void listWithPageBean(final Class clazz, PageBean pageBean, final String orderField, final OrderType orderType) {
		
		int size = findAllSizeWithCondition(clazz,pageBean);
		
		final int first = (pageBean.getPage() -1) * pageBean.getRows();
		final int maxResults = pageBean.getRows();
		final Map<String,String> condition = pageBean.getCondition();
		
		Session session = this.getSession();
		
		Criteria crit = session.createCriteria(clazz);
		
		genCriteria(crit,clazz,condition);
		
		//排序方法
		if (orderType == OrderType.ASC) {
			crit.addOrder(Order.asc(orderField));
		}
		if (orderType == OrderType.DESC) {
			crit.addOrder(Order.desc(orderField));
		}
		
		//分页
		crit.setFirstResult(first);
		crit.setMaxResults(maxResults);
		List list = crit.list();
		
		pageBean.setTotal(size);
		pageBean.setResult(list);
	}
	
	public List listWithCondition(final Class clazz, Map<String,String> condition, final String orderField, final OrderType orderType) {
		Session session = this.getSession();
		Criteria crit = session.createCriteria(clazz);
		genCriteria(crit,clazz,condition);
		
		//排序方法
		if (orderType == OrderType.ASC) {
			crit.addOrder(Order.asc(orderField));
		}
		if (orderType == OrderType.DESC) {
			crit.addOrder(Order.desc(orderField));
		}
		List list = crit.list();
		return list;
	}
	
	//根据条件查询所有记录总数，用户分页
	public int findAllSizeWithCondition(final Class clazz, PageBean pageBean) {
		
		final Map<String,String> condition = pageBean.getCondition();
		Session session = this.getSession();
		Criteria crit = session.createCriteria(clazz);
		
		genCriteria(crit,clazz,condition);
		
		//分页
		return (Integer)crit.setProjection(Projections.rowCount()).uniqueResult();
	}
	
	//组装查询条件
	private Criteria genCriteria(Criteria crit,Class clazz,Map<String,String> condition) {
		for (Map.Entry<String, String> entry : condition.entrySet()) {
			try {
				String conditionKey = entry.getKey();
				conditionKey = conditionKey.trim();
				String searchType = conditionKey.substring(0,conditionKey.indexOf("."));
				String fieldName = conditionKey.substring(conditionKey.indexOf(".")+1);
				
				//转换数据类型
				Object obj = null;
				
				//如果包含. 说明是子对象属性，通过递归，获取最终的属性类型
				Class type = getFieldClass(crit,clazz,null,fieldName);
				
				if (type == String.class) {
					obj = entry.getValue();
				}
				if (type == Integer.class || type == int.class) {
					obj = Integer.valueOf(entry.getValue());
				}
				if (type == Long.class || type == long.class) {
					obj = Long.valueOf(entry.getValue());
				}
				if (type == Date.class) {
					if (entry.getValue().contains(":")) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						obj = df.parse(entry.getValue());
					} else {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						obj = df.parse(entry.getValue());
						if ("le".equals(searchType)){
							Date date = (Date)obj;
							date.setHours(23);
							date.setMinutes(59);
							date.setSeconds(59);
						}
					}
					
				}
				
				//判断条件查询方式
				if ("eq".equals(searchType)) {
					crit.add(Restrictions.eq(fieldName,obj));
				}
				if ("lk".equals(searchType)) {
					crit.add(Restrictions.like(fieldName,String.valueOf(obj),MatchMode.ANYWHERE));
				}
				if ("gt".equals(searchType)) {
					crit.add(Restrictions.gt(fieldName, obj));
				}
				if ("lt".equals(searchType)) {
					crit.add(Restrictions.lt(fieldName, obj));
				}
				if ("ge".equals(searchType)) {
					crit.add(Restrictions.ge(fieldName, obj));
				}
				if ("le".equals(searchType)) {
					crit.add(Restrictions.le(fieldName, obj));
				}
				if ("not".equals(searchType)) {
					crit.add(Restrictions.not(Restrictions.eq(fieldName, obj)));
				}
				if ("notlk".equals(searchType)) {
					crit.add(Restrictions.not(Restrictions.like(fieldName,String.valueOf(obj),MatchMode.ANYWHERE)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		aliasList.clear(); //getFieldClass方法会设置别名，aliasList是个零时列表，需要清空
		return crit;
	}
	
	private Class getFieldClass(Criteria crit,Class parentClass,String parentBeanName,String fieldName) throws SecurityException, NoSuchFieldException {
		
		if (fieldName.contains(".")) {
			String beanName = fieldName.substring(0,fieldName.indexOf("."));
			String subFieldName = fieldName.substring(fieldName.indexOf(".")+1);
			
			//设置别名，否则子对象属性做为条件进行查询时会报could not resolve property异常
			String alias = beanName;
			if (StringUtils.isNotBlank(parentBeanName)) {
				alias = parentBeanName + "." + beanName;
			}
			if (!aliasList.contains(alias)) {
				crit.createAlias(alias, alias);
				aliasList.add(alias);
			}
			
			Field subObj = parentClass.getDeclaredField(beanName);
			return getFieldClass(crit,subObj.getType(),alias,subFieldName);
			
		} else {
			Field field = null;
			try {
				field = parentClass.getField(fieldName);
			} catch (NoSuchFieldException e) {
				field = parentClass.getDeclaredField(fieldName);
			}
			return field.getType();
		}
	}
	
	public void saveOrUpdateAll(List ls){
		this.getHibernateTemplate().saveOrUpdateAll(ls);
	}
	public void save(Object obj){
		this.getHibernateTemplate().save(obj);
	}
	
	public void delete(Object obj){
		this.getHibernateTemplate().delete(obj);
	}
	public void update(Object obj){
		this.getHibernateTemplate().update(obj);
	}
	public void saveOrUpdate(Object obj){
		this.getHibernateTemplate().saveOrUpdate(obj);
	}
}

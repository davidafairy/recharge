package com.redstoneinfo.platform.utils;

import java.lang.reflect.Field;

/**
 * 
 * @author zhangxiaorong
 *
 * 2014-2-11
 */
public class HQLUtil {

	public static String getQueryHQLWithConditionByBean(Object obj) {
		StringBuffer exthql = new StringBuffer("select ");
		StringBuffer name = new StringBuffer(" " + obj.getClass().getSimpleName().toLowerCase() + "0.");
		StringBuffer hql = new StringBuffer(" " + " from " + obj.getClass().getSimpleName() + " " + obj.getClass().getSimpleName().toLowerCase() +"0" );
		Field[] fields = obj.getClass().getDeclaredFields();
		
		if (fields.length == 0)
			return hql.toString();
		
		hql.append(" where 1=1");
		try {
			for (Field field : fields){
				field.setAccessible(true);
				String str = field.getName();
				exthql.append(name).append(str).append(",");
				if (field.get(obj) == null)
					continue;
				if (field.get(obj) instanceof String  ){
					if (!((String)field.get(obj)).trim().equals(""))
						if (((String)field.get(obj)).length() >= 3)
							hql.append(" and ").append(name).append(str).append(" like '%").append( (String)field.get(obj) ).append("%'");
						else
							hql.append(" and ").append(name).append(str).append(" = '").append( (String)field.get(obj) ).append("'");
				}  else {
					hql.append(" and ").append(str).append(" = ").append( field.get(obj) );
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
		
		return hql.toString();
	}
}

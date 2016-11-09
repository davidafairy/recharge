package com.redstoneinfo.platform.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.annotations.GenericGenerator;

/**
 * 所有实体对象的基类
 * @author davidafairy
 *
 */
@MappedSuperclass
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4881640802347260452L;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Transient
	private Map<Integer, Object> hashCodeMap = new HashMap<Integer, Object>();
	
	public static JSONArray toJSONArray(List<? extends BaseEntity> entityList){
		if (entityList == null || entityList.size() == 0)
			return null; 
		
		JSONArray jsonAarry = new JSONArray();
		for (BaseEntity entity : entityList)
			if (entity != null)
				jsonAarry.add(entity.toJSON());
		return jsonAarry;
	}
	
	
	public JSONObject toJSON() {
		
		if (existObject(this))
			return (JSONObject) hashCodeMap.get(this.hashCode());
		
		JSONObject node = new JSONObject();
		hashCodeMap.put(this.hashCode(), node);
		
		try {
			
			if (this.getClass() != BaseEntity.class)
				node.put("id", this.getClass().getSuperclass().getDeclaredField("id").get(this));
			
			Field[] fields = this.getClass().getDeclaredFields();
			sortFields(fields);
			
			for (int i = 0; i < fields.length; i++){
				
				String fieldName = fields[i].getName();
				fields[i].setAccessible(true);
				Object value = fields[i].get(this);
				
				if ("hashCodeMap".equals(fieldName.trim()) || "dateFormat".equals(fieldName.trim()) 
						|| "serialVersionUID".equals(fieldName.trim()))
					continue;					
				
				if (value == null)
					continue;
				
				if (isBaseType(fields[i].getType()))
					node.put(fieldName, value);
				
				if (isDateType(fields[i].getType()))
					node.put(fieldName, dateFormat.format(value));
				
				if (isTimestampType(fields[i].getType()))
					node.put(fieldName, dateFormat.format(((Timestamp)value).getTime()));
				
				if (isRightOrSubClass(fields[i].getType(), BaseEntity.class))
					if (!existObject(value)){
						JSONObject obj = ((BaseEntity) value).toJSON();
						hashCodeMap.put(value.hashCode(), obj);
						node.put(fieldName, obj);
					} else{
						node.put(fieldName, hashCodeMap.get(value.hashCode()));
					}
				
				collection: 
				if (isRightOrSubClass(fields[i].getType(), Collection.class)){
					
					if (existObject(value)){
						node.put(fieldName, hashCodeMap.get(value.hashCode()));
						break collection;
					}
						
					Collection col = (Collection) value;
					
					if (col.size() > 0){
						
						JSONArray jsonAarry = new JSONArray();
						
//						//如果集合没有配泛型则应该删去这个判断
//						Class claz = getParameterizedType(col.getClass());
//						boolean isRightOrSubClass = isRightOrSubClass(claz, BaseEntity.class);
//						if (!isRightOrSubClass)
//							break collection;
						
						Iterator it = col.iterator();
						element:
						while (it.hasNext()){
							
							BaseEntity entity = (BaseEntity) it.next();
							if (existObject(entity)){
								jsonAarry.add(hashCodeMap.get(entity.hashCodeMap));
								continue element;
							}
							
							JSONObject obj = entity.toJSON();
							jsonAarry.add(obj);
							hashCodeMap.put(entity.hashCode(), obj);
						}
						
						node.put(fieldName, jsonAarry);
						hashCodeMap.put(col.hashCode(), jsonAarry);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return node;
	}
	
	/**
	 * 把集合放在后面，把非集合属性放前面
	 * hashcodelist 是用来判断，不让整个Bean中的属性和子属性中，有重复的对象存入。
	 * 这样各层的属性值如果是同样的对象，就可以先执行放到上层属性中，而不会放到下层的集合中
	 */
	private void sortFields(Field[] fields) {
		if (fields.length == 0)
			return;
		
		for (int i = 0; i < fields.length - 1; i++){
			if (isRightOrSubClass(fields[i].getType(), Collection.class)){
				Field temp = fields[i];
				for (int j = i + 1; j < fields.length; j++)
					fields[j-1] = fields[j];
				fields[fields.length-1] = temp;
			}
		}
	}

	private boolean existObject(Object obj) {
		
		if (obj == null)
			return true;
		for (Integer val :hashCodeMap.keySet())
			if (val.hashCode() == obj.hashCode())
				return true;
		return false;
	}

	private Class<?> getParameterizedType(Class<?> cls){
		Type type = cls.getGenericSuperclass();
		ParameterizedType type1 = (ParameterizedType) type;  
        Type[] types = type1.getActualTypeArguments();  
        Class<?> claz =  (Class<?>) types[0];
        return claz;
	}
	
	private boolean isRightOrSubClass(Class<?> claz, Class<?> rightClass) {
		if (claz == null)
			return false;
			
		if (claz.getSuperclass() == null && claz.getInterfaces().length == 0)
			return false;
		
		if (claz.getSuperclass() != null && claz.getSuperclass() == rightClass)
			return true;
		
		if (claz.getInterfaces().length > 0 && containInterface(claz.getInterfaces(), rightClass))
			return true;

		else {
			 boolean result = isRightOrSubClass(claz.getSuperclass(), rightClass) ;
			 
			 if (result)
				 return true;
			 
			 if (claz.getInterfaces().length > 0)
				 for (Class<?> c : claz.getInterfaces()){
					 result = isRightOrSubClass(c, rightClass) ;
					 if (result)
						 return result;
				 }
			 
			 return false;
		}
	}
	
	private boolean containInterface(Class<?>[] interfaces, Class<?> rightClass) {
		for (int i = 0; i < interfaces.length; i++)
			if (interfaces[i] == rightClass)
				return true;
		return false;
	}

	private static boolean isDateType(Class<?> claz) {
		
		if(claz == null)
			return false;

		if (claz.getName().equals(java.sql.Date.class.getName()))
			return true;

		if (claz.getName().equals(java.util.Date.class.getName()))
			return true;
		
		return false;
	}
	
	private static boolean isTimestampType(Class<?> claz) {
		if(claz == null)
			return false;

		if (claz.getName().equals(java.sql.Timestamp.class.getName()))
			return true;
		
		return false;
	}


	private static boolean isBaseType(Class<?> o) {
		
		if(o == null)
			return true;
		
		String className = o.getName();
		if (className == null)
			return false;
		if (o.isPrimitive())
			return true;
		if (o == Byte.class)
			return true;
		if (o == Character.class)
			return true;
		if (o == Short.class)
			return true;
		if (o == Integer.class)
			return true;
		if (o == Long.class)
			return true;
		if (o == Float.class)
			return true;
		if (o == Double.class)
			return true;
		if (o == Boolean.class)
			return true;
		if (o == String.class)
			return true;
		if (o == StringBuffer.class)
			return true;
		if (o == StringBuilder.class)
			return true;
		if (o == BigInteger.class)
			return true;
		if (o == BigDecimal.class)
			return true;
		return false;
	}
	
	public static void main(String[] s ) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		 String str = dateFormat.format(new java.sql.Date(new Timestamp(System.currentTimeMillis()).getTime()));
		 String str2 = dateFormat.format(new Timestamp(System.currentTimeMillis()).getTime());
		 System.out.println(str);
		 System.out.println(str2);
		 System.out.println(Boolean.class.getConstructor(String.class).newInstance("true"));
		 System.out.println(String.valueOf(true));
		 
		 System.out.println(ArrayList.class.getInterfaces()[0].getSimpleName());
//		 List.class.getSuperclass();
//		 List.class.getSuperclass();
	}

}

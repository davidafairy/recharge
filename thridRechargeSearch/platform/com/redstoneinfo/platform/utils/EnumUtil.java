package com.redstoneinfo.platform.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * @author zhangxiaorong
 * 2014-1-26
 */
public class EnumUtil {
	
	public static Object getValueByEnumAttribue(Class<?> claz, Object oldValue, String newKey){
		Map map = enumToMap(claz);
        return getValueByOldValueAndNewKey(map, oldValue, newKey);
	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, HashMap<String, Object>> enumToMap(Class<?> claz){
		HashMap<String, HashMap<String, Object>> map = new HashMap<String, HashMap<String, Object>>(); 
		
		if (!claz.isEnum())
			return null;
				
		try {
			
			Method mm = claz.getDeclaredMethod("values", null);
			Enum[] objs = (Enum[]) mm.invoke(null, null);
			if (objs == null || objs.length == 0)
				return null;
			
			Method[] ms = claz.getDeclaredMethods();
			List<MethodFilterResult> list = filterMethod(ms);
			
			for (Enum e : objs){
				HashMap<String, Object> m = new HashMap<String, Object>(); 
				for (MethodFilterResult result : list){
					Object o = result.getMethod().invoke(e, new Object[]{});
					m.put(result.getName(), o);
				}
				map.put(e.name(), m);
			}
			
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
        return map;
	}

	public static JSONArray enumToJson(Class<?> claz){
		
		if (!claz.isEnum())
			return null;
		
		Object[] objs = claz.getEnumConstants();
		if (objs == null || objs.length == 0)
			return null;
		
		JSONArray array = new JSONArray();
		
		try {
//			Method mm = claz.getDeclaredMethod("values", null);
//			Enum[] rs = (Enum[]) mm.invoke(null, null);
			Method[] ms = claz.getDeclaredMethods();
			List<MethodFilterResult> list = filterMethod(ms);
			
			for (Object obj : objs){
		        JSONObject jsonObj = new JSONObject();
				
				for (MethodFilterResult result : list){
					Object o = result.getMethod().invoke(obj, new Object[]{});
					jsonObj.put(result.getName(), o);
				}
				array.add(jsonObj);
			}
			
			
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return array;
	}
	
	/*
	 * 结构
	 * Map(EnumName, EnumValueMap())
	 * EnumValueMap(attrName, attrValue)
	 * 
	 * 要求enum值不要重复，不然可能会查错值
	 */
	public static Object getValueByOldValueAndNewKey(Map map, Object value, String key){
		Set<Map.Entry<String, Map<String, Object>>> set = map.entrySet();
		Iterator<Map.Entry<String, Map<String, Object>>> it = set.iterator();
		while (it.hasNext()){
			Map.Entry<String, Map<String, Object>> entry = it.next();
			Map<String, Object> m = entry.getValue();
			
			Iterator<Map.Entry<String, Object>> i = m.entrySet().iterator();
			while (i.hasNext()){
				Map.Entry<String, Object> en = i.next();
				if (String.valueOf(en.getValue()).equals(String.valueOf(value)))
					return m.get(key);
			}
		}
		
		return null;
	}


	private static List<MethodFilterResult> filterMethod(Method[] ms) {
		List<MethodFilterResult> list = new ArrayList<MethodFilterResult>();
		for (Method m : ms){
			if (m == null)
				continue;
			
			isGetMethod(m, list);
		}
		return list;
	}
	
	public static void isGetMethod(Method m, List<MethodFilterResult> list) {
		String methodName = m.getName();
		if (methodName.startsWith("get") 
				&& StringUtil.isLowerCase(methodName.substring(4)) 
				&& StringUtil.isUpperCase(String.valueOf(methodName.charAt(3))))
			list.add(new MethodFilterResult(m.getName().substring(3).toLowerCase(), m));
		
		if (methodName.startsWith("is") 
				&& StringUtil.isLowerCase(methodName.substring(3))
				&& StringUtil.isUpperCase(String.valueOf(methodName.charAt(2)))
				&& m.getReturnType() == boolean.class)
			list.add(new MethodFilterResult(m.getName().substring(2).toLowerCase(), m));
	}
	
	
	private static class MethodFilterResult{
		private String name;
		private Method method;
		
		MethodFilterResult(String methodName, Method method) {
			this.name = methodName;
			this.method = method;
		}

		public String getName() {
			return name;
		}
		
		public Method getMethod() {
			return method;
		}
		
	}
	
}
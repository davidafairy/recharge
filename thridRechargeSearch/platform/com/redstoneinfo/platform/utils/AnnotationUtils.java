/**
 * 
 */
package com.redstoneinfo.platform.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author zhangxiaorong
 * 2014-3-26
 */
public class AnnotationUtils {
	
	static String[] origMethodArray = new String[]{
		"equals", "toString", "hashCode", "annotationType"	
	};
	
	public static Map<String, Object> parseAnnotationToMap(Class clazz, Class annotationClass) throws 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Annotation annotation = clazz.getAnnotation(annotationClass);
		if (null == annotation)
			return null;
		
		Method[] methods = annotation.getClass().getDeclaredMethods();
		Map<String, Object> map = new HashMap<String, Object>();  
		
		out:
		for (Method method : methods){
			String methodName = method.getName();
			for (String str : origMethodArray){
				if (methodName.equals(str))
					continue out;
			}
			
			Object obj = method.invoke(annotation, new Object[]{});
			map.put(methodName, obj);
		}
		
		return map;
	}

	
	public static JSONObject parseAnnotationToJson(Class clazz, Class annotationClass) throws 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException{
	
		Annotation annotation = clazz.getAnnotation(annotationClass);
		if (null == annotation)
			return null;
		
		Method[] methods = annotation.getClass().getDeclaredMethods();
        JSONObject jsonObj = new JSONObject();
		
		out:
		for (Method method : methods){
			String methodName = method.getName();
			for (String str : origMethodArray){
				if (methodName.equals(str))
					continue out;
			}
			
			Object obj = method.invoke(annotation, new Object[]{});
			jsonObj.put(methodName, obj);
		}
		
		return jsonObj;
	}
	
	
	public static void main (String[] s) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
//		Map map = parseAnnotationToMap(QuartzSchedule.class, TaskScheduler.class);
//		System.out.println(map.entrySet().toArray()[map.size()-1]);
//		Package p = Package.getPackage("quartzjob");
//		System.out.println();
	}
	
}

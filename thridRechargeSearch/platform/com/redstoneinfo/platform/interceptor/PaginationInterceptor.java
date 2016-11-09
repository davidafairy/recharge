package com.redstoneinfo.platform.interceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.redstoneinfo.platform.annotation.Pagination;
import com.redstoneinfo.platform.bean.PageBean;
import com.redstoneinfo.platform.entity.BaseEntity;
import com.redstoneinfo.platform.ssh.RSBaseAction;

/**
 * 如果请求的方法加了Pagination Annotation，则该Interceptor会自动封装PageBean对象
 * @author davidafairy
 *
 * 2014年2月12日
 */
public class PaginationInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = -7154866416038872691L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		String result = "";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String servletPath = request.getServletPath();
		Class actionClazz = invocation.getAction().getClass();
		String methodName = invocation.getProxy().getMethod();
		Method actionMethod = actionClazz.getMethod(methodName);
				
		Pagination paginationAnnotation = actionMethod.getAnnotation(Pagination.class);
		
		if (null != paginationAnnotation) {
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			
			/**
			 * 如果request里面有page和rows值，则把这两个值放到pageBean里面去
			 */
			if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
				RSBaseAction rsAction = (RSBaseAction)invocation.getAction();
				PageBean pageBean = rsAction.getPageBean();
				try {
					
					//分页查询前准备pageBean
					pageBean.setPage(Integer.valueOf(page));
					pageBean.setRows(Integer.valueOf(rows));
					pageBean.setCondition(genCondition(request));
					
					//查询
					result = invocation.invoke();
					
					//输出查询结果
					JSONObject resultJSON = packageJSON(pageBean);
					outputResult(resultJSON);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			result = invocation.invoke();
		}
		
		return result;
	}
	
	private Map<String,String> genCondition(HttpServletRequest request) {
		Map<String,String> condition = new HashMap<String,String>();
		Map<String,Object> parameterMap = request.getParameterMap();
		for (Map.Entry<String,Object> entry : parameterMap.entrySet()) {
			String sourceKey = entry.getKey();
			if (sourceKey.startsWith("condition.")) {
				String targetKey = sourceKey.substring(sourceKey.indexOf(".")+1);
				String[] value = (String[])entry.getValue();
				if (value.length > 0 && StringUtils.isNotBlank(value[0])) {
					condition.put(targetKey, StringUtils.trim(value[0]));
				}
				
			}
		}
		
		return condition;
	}
	
	/**
	 * 根据PageBean生成用于页面分页显示的JSON对象
	 * @param pageBean
	 * @return
	 */
	private JSONObject packageJSON(PageBean pageBean) {
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		List resultList = pageBean.getResult();
		for (int i=0;i<resultList.size();i++) {
			BaseEntity entity = (BaseEntity)resultList.get(i);
			JSONObject entityJSON = entity.toJSON();
			jsonArray.add(entityJSON);
		}
		
		jsonObj.put("rows", jsonArray);
		jsonObj.put("total", pageBean.getTotal());
		
		return jsonObj;
	}
	
	private void outputResult(JSONObject json) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		try {
			PrintWriter out = response.getWriter();
			out.print(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.redstoneinfo.platform.interceptor;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class EncodingInterceptor extends AbstractInterceptor {

	/**
	 * @author zhangxiaorong
	 * 
	 * Struts2编码拦截器
	 */
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
			ActionContext actionContext = arg0.getInvocationContext();
		
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		
		try {
			if (request.getMethod().compareToIgnoreCase("post") >= 0) 
				request.setCharacterEncoding("utf-8");
			
			else {
				Iterator iter = request.getParameterMap().values().iterator();
				while (iter.hasNext()) {
					String[] parames = (String[]) iter.next();
					for (int i = 0; i < parames.length; i++) 
						parames[i] = new String(parames[i].getBytes("iso8859-1"), "utf-8");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return arg0.invoke();
	}

}
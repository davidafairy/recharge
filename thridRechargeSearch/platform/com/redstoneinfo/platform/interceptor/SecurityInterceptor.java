package com.redstoneinfo.platform.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.redstoneinfo.platform.entity.Agent;

public class SecurityInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String servletPath = request.getServletPath();
		
		if (servletPath.contains("index.action") 
				|| servletPath.contains("login.action") 
				|| servletPath.contains("toLogin.action")) {
			String result = invocation.invoke();
			return result;
			
		} else {
			Agent currentUser = (Agent) session.getAttribute("currentAgent");
			if (currentUser != null) {
				
				if (servletPath.contains("index.action") || servletPath.contains("login.action") 
						|| servletPath.contains("toLogin.action")  
						|| servletPath.contains("left.action") || servletPath.contains("top.action")
						|| servletPath.contains("work.action") || servletPath.contains("foot.action")
						|| servletPath.contains("foot.action") || servletPath.contains("menu.action")){
					String result = invocation.invoke();
					return result;
				}
				
				
				String result = invocation.invoke();
				return result;
				
			} else {
				return "relogin";
			}
		}
	}

}

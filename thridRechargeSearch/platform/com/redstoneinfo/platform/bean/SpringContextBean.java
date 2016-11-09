/**
 * 
 */
package com.redstoneinfo.platform.bean;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * @author zhangxiaorong
 *
 * 2014-3-26
 */
public class SpringContextBean {

	private static ApplicationContext context;
	
	private static void init() {
		ServletContext ServletContext = ServletActionContext.getServletContext();
		
		//获取web环境下的ApplicationContext
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletContext);
	}
	
    public static ApplicationContext getContext() {
    	if (context == null)
    		synchronized(SpringContextBean.class){
    			if (context == null)
    				init();
    		}
    	
        return context;
    }
    
    public static Object getBean(String id) {
    	return getContext().getBean(id);
	}
    
}

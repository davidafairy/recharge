package com.redstoneinfo.platform.ssh;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Value;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.redstoneinfo.platform.bean.PageBean;

/**
 * Action基类
 * @author davidafairy
 *
 */
public class RSBaseAction extends ActionSupport implements Preparable{

	private String projectName;
	
	private PageBean pageBean = new PageBean();
	
	@Override
	public void prepare() throws Exception {
		
	}
	

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public void setRequestAttribute(String key, Object value) {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute(key, value);
	}
	
	public void setError(String error) {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("error", error);
	}

	public String getProjectName() {
		return projectName;
	}

	@Value("#{propertiesReader[projectName]}")
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public void jsonWrite(Object obj) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Cache-Control","no-cache"); 
		response.setHeader("Pragma","no-cache"); 
		response.setDateHeader ("Expires", 0); 
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		try {
			PrintWriter out = response.getWriter();
			out.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void convertMsgToJSONObject(boolean success,String str) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success", success);
		if(success){
			jsonObj.put("msg",str+"成功");
		}else{
			jsonObj.put("msg",str+"失败");
		}
		jsonWrite(jsonObj);
		
	}
}

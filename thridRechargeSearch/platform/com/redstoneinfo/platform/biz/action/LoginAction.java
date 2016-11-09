package com.redstoneinfo.platform.biz.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.redstoneinfo.platform.biz.dao.AgentDao;
import com.redstoneinfo.platform.entity.Agent;
import com.redstoneinfo.platform.ssh.RSBaseAction;


@Controller
@ParentPackage("orderQuery")
@Scope("prototype")
@Results({ @Result(name = "toLogin", location = "login.jsp"),
	       @Result(name = "toMain", location = "main.action",type="redirect"),
		   @Result(name = "mainPage", location = "main.jsp")})
public class LoginAction extends RSBaseAction {
	

	private static final long serialVersionUID = 2904565008457330670L;

	private Agent agent;
	
	String errorMess = null;
	
	@Autowired
	private AgentDao agentDao;
	
	@Action(value = "validateLogin")
	public String validateLogin(){

		return "toMain";
		
	}
	
	@Action(value = "toLogin")
	public String toLogin(){
		
		return "toLogin";
		
	}
	
	@Action(value = "login")
	public String login() {
		
		Agent currentAgent = agentDao.validate(agent);
		
		if (currentAgent != null) {
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("currentAgent", currentAgent);
			
			return "toMain";
		}else{
			errorMess ="用户名密码不正确，请重新登录";
			return "toLogin";
		}
	}
	
	@Action(value = "logout")
	public String logout(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("currentAgent");
		return "toLogin";
	}

	@Action(value = "main")
	public String main() {
		return "mainPage";
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getErrorMess() {
		return errorMess;
	}

	public void setErrorMess(String errorMess) {
		this.errorMess = errorMess;
	}

	
}

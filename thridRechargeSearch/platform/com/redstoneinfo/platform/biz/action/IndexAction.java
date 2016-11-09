package com.redstoneinfo.platform.biz.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.redstoneinfo.platform.ssh.RSBaseAction;

@Controller
@ParentPackage("orderQuery")
@Scope("prototype")
@Results({ @Result(name = "toLogin", location = "login.jsp")})
public class IndexAction extends RSBaseAction {

	@Action(value = "index")
	public String index() {
		
		return "toLogin";
	}
	
	
}

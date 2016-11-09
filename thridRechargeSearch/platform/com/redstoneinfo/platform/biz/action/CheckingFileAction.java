package com.redstoneinfo.platform.biz.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.redstoneinfo.platform.annotation.Pagination;
import com.redstoneinfo.platform.bean.PageBean;
import com.redstoneinfo.platform.biz.dao.CheckingDao;
import com.redstoneinfo.platform.entity.Agent;
import com.redstoneinfo.platform.entity.CheckingFile;
import com.redstoneinfo.platform.ssh.RSBaseAction;

@Controller
@Scope("prototype")
@ParentPackage("orderQuery")
@Results({ @Result(name = "checkList", location = "biz/checkList.jsp"),
	@Result(name="download",type="stream",params={"contentType","application/octet-stream;charset=GBK","inputName","inputStream","contentDisposition","attachment;filename=${fileName}","bufferSize","4096"})})
public class CheckingFileAction extends RSBaseAction {

	private String checkId;
	
	private CheckingFile cf;
	
	private String fileName;
	
	@Autowired
	private CheckingDao checkingDao;
	
	@Action("checkList")
	public String checkList() {
		return "checkList";
	}
	
	@Action(value = "getCheckList")
	@Pagination
	public String getCheckList(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Agent agent = (Agent)request.getSession().getAttribute("currentAgent");
		
		PageBean pageBean = getPageBean();
		if (!"admin".equals(agent.getLoginName())) {
			Map<String,String> condition = pageBean.getCondition();
			condition.put("eq.agentId", String.valueOf(agent.getId()));
		}
		
		//只需要调用查询，框架自动实现了分页
		checkingDao.listChecks(pageBean);
		return null;
	}
	
	@Action(value = "downloadCheckingFile")
	public String downloadCheckingFile() {
		cf = checkingDao.getCheckById(Integer.valueOf(checkId));
		fileName = cf.getFileName();
		return "download";
	}

	 public InputStream getInputStream() {
         return ServletActionContext.getServletContext().getResourceAsStream("/tmpFiles/" + fileName);
	 }

	 
	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public CheckingFile getCf() {
		return cf;
	}

	public void setCf(CheckingFile cf) {
		this.cf = cf;
	}

	public String getFileName() {
		try {
			return this.fileName = new String(fileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "1.xls";
		}
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}

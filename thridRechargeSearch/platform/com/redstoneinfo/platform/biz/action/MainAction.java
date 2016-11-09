package com.redstoneinfo.platform.biz.action;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.redstoneinfo.platform.utils.MD5Util;

@Controller
@ParentPackage("orderQuery")
@Scope("prototype")
@Results({@Result(name = "top", location = "top.jsp"),
		@Result(name = "left", location = "left.jsp"),
		@Result(name = "work", location = "work.jsp"),
		@Result(name = "foot", location = "foot.jsp"),
		@Result(name = "showMap", location = "map.jsp")
		})
public class MainAction extends RSBaseAction {
	
	private boolean success;
	
	@Autowired
	private AgentDao agentDao;
	
	@Action("top")
	public String top() {
		return "top";
	}

	@Action("left")
	public String left() {
		return "left";
	}

	@Action("work")
	public String main() {
		
		return "showMap";
	}
	
	

	@Action("foot")
	public String foot() {
		return "foot";
	}
	
	@Action("mainMenu")
	public void mainMenu(){
		// dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
		
			jsonWrite(getResource());
	}
	
	public JSONArray getResource() {
		JSONArray jsonAarry = new JSONArray();
		
		JSONObject orderObj = new JSONObject();
		orderObj.put("id", 2);
		orderObj.put("text", "待充值订单查询");
		JSONObject orderAttribute = new JSONObject();
		orderAttribute.put("url", "orderList.action");
		orderObj.put("attributes",orderAttribute);
		jsonAarry.add(orderObj);
		
		JSONObject orderHisObj = new JSONObject();
		orderHisObj.put("id", 3);
		orderHisObj.put("text", "已充值订单查询");
		JSONObject orderHisAttribute = new JSONObject();
		orderHisAttribute.put("url", "orderHisList.action");
		orderHisObj.put("attributes",orderHisAttribute);
		jsonAarry.add(orderHisObj);
		
		JSONObject checkObj = new JSONObject();
		checkObj.put("id", 4);
		checkObj.put("text", "对账文件");
		JSONObject checkAttribute = new JSONObject();
		checkAttribute.put("url", "checkList.action");
		checkObj.put("attributes",checkAttribute);
		jsonAarry.add(checkObj);
		return jsonAarry;
	}
	
	@Action(value = "validateEnableEditUserSelfPassword")
	public void validateEnableEditUserSelfPassword(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String oldPassword= request.getParameter("oldPassword").trim();
		Agent agent = (Agent)request.getSession().getAttribute("currentAgent");
		String password = agent.getSearchPwd();
		String msg = "";
		if(password!=null&&!(password.equals(""))){
			if(MD5Util.encode(oldPassword).equals(password)){
				success = true;
			}else{
				success = false;
				msg = "旧密码不正确，请重新输入";
			}
		}else{
			success = false;
			msg = "原密码为空或空串，不能修改，请联系管理员";
		}
        JSONObject  jsonObj = new JSONObject();
		jsonObj.put("success", success);
		jsonObj.put("msg", msg);
		jsonWrite(jsonObj);
	}
	
	@Action(value = "updateUserSelfPassword")
	public String updateUserSelfPassword(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Agent agent = (Agent)request.getSession().getAttribute("currentAgent");
		
		String newPassword= request.getParameter("newPassword").trim();
		success = agentDao.updateAgentPassword(agent.getId(),MD5Util.encode(newPassword));
		if (success) {
			
			agent.setSearchPwd(MD5Util.encode(newPassword));
		}
		jsonWrite(convertBooleanToJSONObject(success,"更新密码"));
		return null;
	}
	
	private JSONObject convertBooleanToJSONObject(boolean success,String str) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success", success);
		if(success){
			jsonObj.put("msg",str+"成功");
		}else{
			jsonObj.put("msg",str+"失败");
		}
		return jsonObj;
		
	}
}


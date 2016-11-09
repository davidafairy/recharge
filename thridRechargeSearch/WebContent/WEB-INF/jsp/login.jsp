<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${projectName}</title>
<%
	request.setAttribute("base", request.getContextPath());
%>
<LINK href="${base}/css/login.css" type=text/css rel=stylesheet>
<STYLE type=text/css>
    #errorMess {color:red; font-size:16px;} #Layer1 { position:absolute; width:36px;
    height:20px; z-index:1; left: 590px; top: 340px; background-color: #CCCCCC;
    } .STYLE1 { color: #FFFFFF; font-family: "宋体"; } #Layer2 { position:absolute;
    width:24px; height:20px; z-index:1; left: 596px; top: 367px; background-color:
    #CCCCCC; } .STYLE2 {font-size: 12px}
</STYLE>
<script type="text/javascript">
    function doLogin() {
        document.getElementById('loginForm').submit();
    }

    function updateLoginName() {
        var t = document.getElementById("txtUserName");
        t.value = "";
        t.style.color = "black";
        t.name = "agent.loginName";
        t.focus();
    }
    function updatePassword() {
        var t = document.getElementById("txtUserPassword1");
        t.style.display = "none";
        t.blur();
        var t1 = document.getElementById("txtUserPassword");
        t1.style.display = "";
        t1.focus();
    }
    function input() {
        var t1 = document.getElementById("txtUserPassword");
        var r = t1.createTextRange();
        r.collapse(false);
        r.select();
    }

   	function onEnter(event) {
   		if(event.keyCode==13){  
   			doLogin();
   		}
   	}
    
    
</script>
</HEAD>

<div class="div">
		<form id="loginForm" action="login.action" method="post">
		  <table width="900" border="0" align="center" cellpadding="0" cellspacing="0">
		    <tr>
		      <td height="83" align="center" valign="top"><font size="9"><B>订单查询系统</B></font></td>
		    </tr>
		    <tr>
		      <td><table width="900" border="0" align="left" cellpadding="0" cellspacing="0">
		        <tr>
		          <td height="47"><div class="bg1">${roleName}登录</div></td>
		          <td>&nbsp;</td>
		        </tr>
		      </table></td>
		    </tr>
		    <tr>
		      <td  class="bg_blue" align="center"><div class=" bg_center">
		        <table width="100%" height="149" border="0" cellpadding="0" cellspacing="0">
		          <tr>
		            <td width="54%" valign="top"><table width="130" border="0" align="right" cellpadding="0" cellspacing="0">
		              <tr>
		                <td width="143" height="37">&nbsp;<input type="hidden" name="loginRole" value="${loginRole }"></td>
		              </tr>
		              <tr>
		                <td height="28" align="right">
	                  <INPUT  class="textbox" id="txtUserName" tabindex="1" type="text" value="登录名" style="color:#E0E0E0; width: 140px; height: 22px; "name="loginName" onClick="updateLoginName();">		              </tr>
		              <tr>
		                   <td height="28" align="right">
		               <INPUT  class="textbox" id="txtUserPassword1" tabindex="2" type="text" name="password1" value="密码" style="color:#E0E0E0; width: 140px; height: 22px;" onFocus="updatePassword();">
		               <INPUT  class="textbox" id="txtUserPassword" type="password" name="agent.password" value="" style="color:black; width: 140px; height: 22px; display:none;" onFocus="input();" onKeyDown="onEnter(event);">
		               </TD>
		             
		              </tr>
		              <tr>
		                <td height="51" valign="bottom"><SPAN id="errorMess"><s:property value="errorMess"/></SPAN></td>
		              </tr>
		            </table></td>
		            <th width="46%"><a href="#" onClick="doLogin()"><img src="images/login/dl_13.png" width="156" height="149" /></a></th>
		          </tr>
		        </table>
		     </div></td>
		    </tr>
		  </table>
		</form>
		<div class="txt1"></div>
</div>

<div class="STYLE1 STYLE2" id="Layer1" style="display:none;" >用户名</div>
<div class="STYLE1 STYLE2" id="Layer2" style="display:none;" >密码</div>

</BODY>
</HTML>

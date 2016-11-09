<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
    
<HTML>
<HEAD>

<%
	request.setAttribute("base", request.getContextPath());
%>



<link rel="stylesheet" type="text/css" href="${base}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${base}/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${base}/css/pf.css">
<link rel="stylesheet" type="text/css" href="${base}/easyui/demo.css">
<LINK href="${base}/css/login.css" type=text/css rel=stylesheet>

<script type="text/javascript" src="${base}/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${base}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/easyui/easyui-lang-zh_CN.js"></script>

<META http-equiv=Content-Type content="text/html; charset=utf-8">
<style type="text/css">  
        #fmPassword{  
            margin:0;  
            width:330px;
            heigth:90px;
            padding:5px 5px;  
        }  
        .ftitle{  
            font-size:14px;  
            font-weight:bold;  
            color:#666;  
            padding:5px 0;  
            margin-bottom:10px;  
            border-bottom:1px solid #ccc;  
        }  
        .fitem{  
            margin-bottom:10px;  
        }  
        .fitem label{  
            display:inline-block;  
            width:80px;  
            text-align:right;
            padding:5px;
        }  
    </style>  
</HEAD>
<body>
	<div class="div_top" style="height:51px">
	  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" background="images/login_new/top_03.png">
	    <tr>
	      <td width="50%" align="left" valign="middle" style="font-size:18px"><font size="6" color="white"><B>联通订单查询系统</B></font></td>
	      <td width="43%" align="right" valign="middle"><a href="#"  id="btnEditPassword"   style="color:white; "  onclick="editUserPassword()">修改密码</a></td>
	     <td width="7%" align="center" valign="middle"><a href="#" id="btnExit" style="color:white;"  onclick="doLogout()" >安全退出</a></td>
	    </tr>
	  </table>
	</div>
	
	   <div id="dlgPassword" class="easyui-dialog" style="width:380px;height:300px;" closed="true" buttons="#dlg-buttons-password">
			<form id="fmPassword" style=" width:300px;heigth:90px; padding:10px 10px; "  method="post">
				<div class="fitem">
				  <label style="width:70px; display:inline-block;">原密码: </label>
					<input id="oldPassword" type="password" class="easyui-validatebox" required="true" missingMessage="原密码不能为空">
		 		</div>
								<div class="fitem" style="height:5px;" ></div>
				<div class="fitem">
				   <label style="width:70px; display:inline-block;">新密码: </label>
				   <input id="newPassword" type="password" class="easyui-validatebox"	 required="true" missingMessage="新密码不能为空">
				</div>
								<div class="fitem" style="height:5px;" ></div>
				<div class="fitem">
				   <label style="width:70px; display:inline-block;">确认密码:</label>
				   <input id="confirmPassWord" type="password" class="easyui-validatebox"	validType="equalTo['#newPassword']" required="true" missingMessage="确认新密码不能为空">
			   </div>
			</form>
	   </div>
	
		<div id="dlg-buttons-password">
		  <a href="#" id="btnPasswordSave" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUserPassword()">保存</a> 
		  <a href="#" id="btnPasswordCanel" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgPassword').dialog('close')">取 消</a>
		</div>
		
		<script type="text/javascript">

	function editUserPassword(){  //修改用戶密码窗口  
	    	$("#dlgPassword").dialog('open').dialog('setTitle','修改自己的密码');
	    	$("#fmPassword").form('clear');
	   
	}  

	  function saveUserPassword(){ //保存用户增加或修改的信息
		  progress();
      	 $('#btnPasswordSave').linkbutton({disabled:true});
     	 $('#btnPasswordCancel').linkbutton({disabled:true});
      	  var v1 =  $("#fmPassword").form('validate');
            if(v1){
		           	   $.ajax({  
		      	            url:'validateEnableEditUserSelfPassword.action?oldPassword='+$('#oldPassword').val(), 
		      	             cache:false,  
		      	             dataType:'text',  
		      	             success:function(data){ 
			      	        	   eval('data='+data);
			      	        	   var str = data.success;
			      	        	   if(str){
			      	        		  $("#fmPassword").form('submit', {  
					          	                 url: 'user/updateUserSelfPassword.action?newPassword='+$('#newPassword').val(), 
					          	                 success:function(data){ 
					          	                 eval('data='+data); 
					          	                 $.messager.progress('close');

					          	                 $('#btnPasswordSave').linkbutton({disabled:false});
					             	             $('#btnPasswordCancel').linkbutton({disabled:false}); 
							          	               if (data.success){  
							                            	$.messager.alert('操作提示',data.msg,'ok'); 
							                            	$('#dlgPassword').window('close'); 
							                            } else {  
							                                $.messager.alert('操作提示',data.msg,'error');
							                                $('#dlgPassword').window('close');
							                               
							                            }   
					      	                   }
					
					      	        		});
			                           }else{
			                        	        $.messager.progress('close');
					                          	$.messager.alert('操作提示',"您输入的旧密码错误，请重新输入",'error'); 
					                          	$('#oldPassword').val("");
					                          	 $('#btnPasswordSave').linkbutton({disabled:false});
					               	            $('#btnPasswordCancel').linkbutton({disabled:false});
			                               }
		                           }
		                   });
                 }else{
              	            $.messager.progress('close');
		               	   $.messager.alert('操作提示',"表单字段验证没通过，不能提交",'error'); 
		               	   $('#btnPasswordSave').linkbutton({disabled:false});
	          	           $('#btnPasswordCancel').linkbutton({disabled:false});
                    }
      	}

	  /*登出绑定*/
		//$("#logout").bind("click",doLogout);

	  
		function doLogout(){
			url = 'logout.action';
			location.href = url;
		}

	  function progress(){//进度条
  	      var win = $.messager.progress({
  	          title:'请稍等',
  	          msg:'正在提交数据...',
  	          text:""
  	      });
  	  }

	  $.extend($.fn.validatebox.defaults.rules, {
		
		    safepass: {
		        validator: function (value, param) {
		            return safePassword(value);
		        },
		        message: '密码由字母、数字或字符组成，至少6位'
		    },
		    equalTo: {
		        validator: function (value, param) {
		            return value == $(param[0]).val();
		        },
		        message: '两次输入的字符不一至'
		    }
		});
		
	  var safePassword = function (value) {
		    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
		};
   </script>
  </body>
</HTML>

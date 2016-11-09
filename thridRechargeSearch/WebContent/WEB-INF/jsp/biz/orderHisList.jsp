<%@page import="com.redstoneinfo.platform.entity.Agent"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<HTML>

<HEAD>
<title>${projectName}</title>
<%
	request.setAttribute("base", request.getContextPath());
%>
<link rel="stylesheet" type="text/css" href="${base}/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${base}/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${base}/css/pf.css">
<link rel="stylesheet" type="text/css" href="${base}/easyui/demo.css">
<script type="text/javascript" src="${base}/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${base}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/easyui/easyui-lang-zh_CN.js"></script>

<META http-equiv=Content-Type content="text/html; charset=utf-8">
<style type="text/css">  
        #fm{  
            margin:0;  
            padding:10px 30px;  
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
            margin-bottom:5px;  
        }  
        .fitem label{  
            display:inline-block;  
            width:80px;  
        }  
         .fitem2{  
            margin-bottom:25px;  
            padding:25px 0;
        }  
        .fitem2 label{  
            display:inline-block;  
            width:50px; 
            padding:5px; 
        } 
    </style>  
    
    <script type="text/javascript">

    var form =$('#ff');
    var win = $('#dlg');

    var formPassword = $('#fmPassword'); 

        function changeP(){//翻页按钮
            var dg = $('#dg');
            dg.datagrid('loadData',[]);
            dg.datagrid({pagePosition:$('#p-pos').val()});
            dg.datagrid('getPager').pagination({
                layout:['list','sep','first','prev','sep',$('#p-style').val(),'sep','next','last','sep','refresh']
            });
        }

       
        
        
        function closeWindow(){  
            win.window('close');  
        }  

        function progress(){//进度条
  	      var win = $.messager.progress({
  	          title:'请稍等',
  	          msg:'正在提交数据...',
  	          text:""
  	      });
  	  }
      
      
        function refresh(){//刷新列表
        	$('#dg').datagrid('reload');  
        }

        function doSearch(){  //查询按钮
              $('#dg').datagrid('load',{  
            	  "condition.lk.flowNo": $('#flowNo').val(),
            	  "condition.lk.mobile": $('#mobile').val(),
            	  "condition.eq.agentId":($('#agentId').combobox('getValue')),
            	  "condition.ge.createTime":$("input[name='startCreateTime']").val(),
            	  "condition.le.createTime":$("input[name='endCreateTime']").val(),
            	  "condition.eq.errorCode":($('#errorCode').combobox('getValue'))
            	  
              });  
        } 

        function doClear(){//清空查询条件
        	  $('#flowNo').val("");
        	  $('#mobile').val("");
        	   $('#startCreateTime').datebox('setValue',"");
		    $('#endCreateTime').datebox('setValue',"");
		    
            }
        
        function initPage(){//分页的初始化
        	
        	var p = $('#dg').datagrid().datagrid('getPager'); 
 	         p.pagination({        
 	         pageSize: 20,//每页显示的记录条数 
 	         pageList: [10,20,30,40],//可以设置每页记录条数的列表 
 	         beforePageText: '第',//页数文本框前显示的汉字 
 	         afterPageText: '页    共 {pages} 页', 
 	         displayMsg: '当前显示{from}条到{to}条记录   共{total}条记录' 
 	        }); 
             
            }
        
        $(function(){
            initPage();
            <%
				Agent currentAgent = (Agent)request.getSession().getAttribute("currentAgent");
				if (!"admin".equals(currentAgent.getLoginName())) {
					%>
						$("#agentcondition").hide(); 
					<%
				}
			%>
        });

    </script>
</HEAD>
<body style="margin:0" class="easyui-layout">
<div region="center" style="padding:5px;" border="false">
<table id="dg" title="订单历史列表(只能查询两个月以内订单，超过两个月请跟客服人员联系)" class="easyui-datagrid" 
        url="getOrderHisList.action" 
        fit="true"
        toolbar="#searchtool"
        pagination="true"
        striped="true"
         loadMsg="正在加载数据，请稍等……"
        rownumbers="true" fitColumns="true" singleSelect="true" pageSize=20>  
    <thead>  
        <tr>  
            <th field="flowNo" width="100">订单号</th>  
            <th field="mobile"  width="100">手机号</th>  
            <th field="money" width="100">充值金额</th>
            <th field="createTime" width="100">订单时间</th>  
            <th field="rechargeTime" width="100">充值时间</th>
            <th field="result" width="100">充值结果</th> 
            <th field="balance" width="100">余额</th>
        </tr>  
    </thead>  
</table>  
</div>
<div id="searchtool">  
	<span id="agentcondition">
		 &nbsp;&nbsp;&nbsp; <span>代理商:</span>
		 <select id ="agentId" class ="easyui-combobox" name ="agentId" data-options ="panelHeight:'auto'" style =" padding:2px;width:141px;">
	     	<option value ="" selected ="selected">所有代理商</option>
	     	<option value ="721">社会渠道1</option>
	     	<option value ="723">社会渠道2</option>
	     	<option value ="724">社会渠道3</option>
	     	<option value ="732">社会渠道4</option>
	     	<option value ="731">社会渠道CH</option>
	     </select>
     </span>
       &nbsp;&nbsp;&nbsp; <span>订单号:</span><input type="text" id="flowNo" class="easyui-validatebox" value="" size=20 />&nbsp;&nbsp;&nbsp; 
      <span>手机号:</span><input type="text" id="mobile" class="easyui-validatebox" value="" size=20 />&nbsp;&nbsp;&nbsp; 
      &nbsp;<span>订单时间:</span><input id="startCreateTime" name="startCreateTime" class="easyui-datebox" size=12 editable ="false"  readonly="readonly" />--<input id="endCreateTime" name="endCreateTime" class="easyui-datebox"  size=12 editable ="false"  readonly="readonly"/>
      <span id="statuscondition">
		 &nbsp;&nbsp;&nbsp; <span>订单状态:</span>
		 <select id ="errorCode" class ="easyui-combobox" name ="errorCode" data-options ="panelHeight:'auto'" style =" padding:2px;width:141px;">
	     	<option value ="" selected ="selected">所有订单</option>
	     	<option value ="0">成功</option>
	     	<option value ="9">失败</option>
	     	<option value ="13">可疑</option>
	     </select>
     </span>
      <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="javascript:doSearch();">查询</a>&nbsp;&nbsp;
      <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="javascript:doClear();">清空</a>&nbsp;&nbsp;
      <a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refresh()">刷新</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    
</div>  



</body>
</html>
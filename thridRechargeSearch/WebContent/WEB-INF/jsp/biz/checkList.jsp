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
</HEAD>
<body style="margin:0" class="easyui-layout">
<div region="center" style="padding:5px;" border="false">
<table id="dg" title='对账文件列表' class="easyui-datagrid" 
        url="getCheckList.action" 
        fit="true"
        toolbar="#searchtool"
        pagination="true"
        striped="true"
         loadMsg="正在加载数据，请稍等……"
        rownumbers="true" fitColumns="true" singleSelect="true" pageSize=20>  
    <thead>  
        <tr>  
        	<th field="id" width="100" hidden="false"  align="center">id</th> 
            <th field="agentName" width="100">代理商名称</th>  
            <th field="checkingFileCycle"  width="100">对账文件周期</th>  
            <th field="fileName" width="100">对账文件</th>
        </tr>  
    </thead>  
</table>  
</div>
<div id="searchtool">  
      <a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refresh()">刷新</a>&nbsp;&nbsp;&nbsp;&nbsp;
      <a href="#" class="easyui-linkbutton" size="10" iconCls="icon-redo" plain="true" onclick="downloadCheckingFile()">下载对账文件</a>&nbsp;
</div>  


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
        });

		function downloadCheckingFile() {
			var row = $('#dg').datagrid('getSelected');  
			downloadFile(row.id);
		}
		
		function downloadFile(checkId){
		      var url = "downloadCheckingFile.action?checkId=" + checkId
		      location.href=url;
		 }
    </script>
</body>
</html>
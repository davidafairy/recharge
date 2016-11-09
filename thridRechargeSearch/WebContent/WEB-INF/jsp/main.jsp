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

</HEAD>

<body class="easyui-layout">
	<!--头部begin-->
	<div region="north" border="true" split="false"
		style="height: 51px; overflow: hidden;" href="top.action"></div>
	<!--头部end-->

	<div region="west" title="功能导航" split="true" style="width: 230px;"
		href="left.action"></div>


	<!--中间内容begin-->
	<div region="center">
		<div id="main-center" class="easyui-tabs" fit="true" border="false">
			
		</div>
	</div>
	<!--中间内容end-->


	<!-- 底部begin-->
	<div region="south" border="false" split="false"
		style="height: 0px; overflow: hidden;"
		href="foot.action"></div>


	<!-- 底部end-->
	<div id="FatherWin"></div>
	<div id="parentDialog"></div>
	<div id="Hsktabclosemm2010" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
	</div>
</body>

</HTML>



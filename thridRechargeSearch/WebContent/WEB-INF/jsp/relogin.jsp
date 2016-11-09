<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%
	request.setAttribute("base", request.getContextPath());
%>
<html>
	<head>
		<script type="text/javascript">
			try {
				parent.location.href = "${base}/index.action";
			}catch(e) {
				this.location.href = "${base}/index.action";
			}
		</script>
	</head>
</html>
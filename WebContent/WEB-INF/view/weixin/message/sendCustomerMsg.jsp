<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>${commonMapper.title}</title>
<script type="text/javascript">
	$(function() {
		//日期
	});
</script>
</head>
<body>
	<form:form id="sendCustomMsgForm"
		action="${pageContext.request.contextPath}/weixin/msg/sendCustomMsg.do" method="post">
		<table>
			<tr>
				<th><font color="red">*</font>普通用户openid</th>
				<td><input type="text" name="openid" size="40"></td>
			</tr>
			<tr>
				<th><font color="red">*</font>文本消息内容</th>
				<td><input type="text" name="content" size="40"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>

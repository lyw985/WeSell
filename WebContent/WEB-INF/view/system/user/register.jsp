<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<title>${commonMapper.title}</title>
	<style>.errorClass{color:red}</style>
	</head>
	<body>
		<form:form id="registerForm" modelAttribute="user" action="${pageContext.request.contextPath}/user/register.do" method="post">
		    <table>
			  <tr>
			     <td>用户名：</td>
			     <td>
			        <form:input path="loginId" maxlength="12"/>
			     </td>
			  </tr>
			  <tr>
			     <td>密码：</td>
			     <td>
			        <form:password path="password" maxlength="15"/>
			     </td>
			  </tr>
			  <tr>
			     <td>显示名：</td>
			     <td>
			        <form:input path="name" maxlength="12"/>
			     </td>
			  </tr>
			  <tr>
			     <td colspan="2" align="center"><input type="submit" value="提交"/></td>
			  </tr>
		    </table>
		</form:form>
		<a href="${pageContext.request.contextPath}/index.jsp">&#9666;返回首页</a>
	</body>
</html>

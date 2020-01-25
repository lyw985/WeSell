<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>人员详细</title>
</head>
<body>
<form:form action="${pageContext.request.contextPath}/user/save.do" commandName="user">
	<table class="detail">
		<tr>
			<td>登录id：</td>
			<td>
				<form:hidden path="id"/>
				<c:out value="${user.loginId}"></c:out>
			</td>
		</tr>
		<tr>
			<td>登录名称：</td>
			<td>
				<form:hidden path="name"/>
				<c:out value="${user.name}"></c:out>
			</td>
		</tr>
		<tr>
			<td>工号：</td>
			<td>
				<c:out value="${user.jobNumber}"></c:out>
			</td>
		</tr>
		<tr>
			<td>部门：</td>
			<td>
				<input type="hidden" name="departmentId" id="departmentId" value="${user.department.id }"/>
				<c:out value="${user.department.name}"></c:out>
			</td>
		</tr>
		<tr>
			<td>固定电话：</td>
			<td>
				<form:input path="phone"/>
			</td>
		</tr>
		<tr>
			<td>移动电话：</td>
			<td>
				<form:input path="mobile"/>
			</td>
		</tr>
		<tr>
			<td>电子邮件：</td>
			<td>
				<form:input path="email"/>
			</td>
		</tr>
		<tr>
			<td>联系地址：</td>
			<td>
				<form:input path="address"/>
			</td>
		</tr>
		<tr>
			<td>旧密码：</td>
			<td>
				<input type="hidden" name="password" id="password" value="">
				<input type="password" id="showPassword" value="">
			</td>
		</tr>
		<tr>
			<td>新密码：</td>
			<td>
				<input type="password" id="newPassword" value="">
			</td>
		</tr>
		<tr>
			<td>重复密码：</td>
			<td>
				<input type="password" id="rePassword" value="">
			</td>
		</tr>
	</table>
</form:form>
</body>
</html>
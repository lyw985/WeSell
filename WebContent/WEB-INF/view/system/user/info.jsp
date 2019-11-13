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
<form:form action="${commonMapper.rootPath}/user/save.do" commandName="user">
	<table class="detail">
		<tr>
			<td><span class="required">*</span>登录id：</td>
			<td>
				<form:input path="loginId"/>
				<form:hidden path="id"/>
			</td>
		</tr>
		<tr>
			<td><span class="required">*</span>登录名：</td>
			<td>
				<form:input path="name"/>
			</td>
		</tr>
		<tr>
			<td>工号：</td>
			<td>
				<c:out value="${user.jobNumber}"></c:out>
			</td>
		</tr>
		<tr>
			<td><span class="required">*</span>部门：</td>
			<td>
				<input type="hidden" id="departmentId" name="departmentId" readonly="readonly" value="${department.id }">
				<input type="text" id="departmentName" name="departmentName" readonly="readonly" value="${department.name }">
				<img alt="选择" title="选择部门" src="${commonMapper.rootPath}/images/choose.gif" style="margin: 0px;padding: 0px;">
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
			<td><span class="required">*</span>密码：</td>
			<td>
				<form:password path="password"/>
			</td>
		</tr>
	</table>
</form:form>
</body>
</html>
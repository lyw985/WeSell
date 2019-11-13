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
<title>角色详细</title>
</head>
<body>
	<form:form action="${commonMapper.rootPath}/role/save.do" commandName="role">
		<table>
		  <tr>
		     <td>角色名称：</td>
		     <td>
		        <form:input path="name" maxlength="12" class="validate[required]" />
		        <form:hidden path="id"/>
		     </td>
		  </tr>
		  <tr>
		     <td>所属系统：</td>
		     <td>
		     	<form:select path="module.id" items="${moduleList}" itemValue="id" itemLabel="name"></form:select>
		     </td>
		  </tr>
		  <tr>
		     <td>角色描述：</td>
		     <td>
		        <form:input path="description" maxlength="200" class="validate[required]" />
		     </td>
		  </tr>
	   </table>
	</form:form>
</body>
</html>
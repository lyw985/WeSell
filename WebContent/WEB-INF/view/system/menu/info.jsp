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
</head>
	<body>
		<form:form id="menuForm" action="${commonMapper.rootPath}/menu/save.do" method="post" commandName="menu">
		   <table>
			  <tr>
			     <td>菜单名称：</td>
			     <td>
			        <form:input path="name" maxlength="12" class="validate[required]" />
			        <form:hidden path="id"/>
			     </td>
			  </tr>
			  <tr>
			     <td>菜单地址：</td>
			     <td>
			        <form:input path="address" maxlength="200" class="validate[required]" />
			     </td>
			  </tr>
			  <tr>
			     <td>菜单描述：</td>
			     <td>
			        <form:input path="description" maxlength="200" class="validate[required]" />
			     </td>
			  </tr>
		   </table>
		</form:form>
	</body>
</html>

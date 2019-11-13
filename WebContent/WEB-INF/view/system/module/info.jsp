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
		<form:form id="moduleForm" action="${commonMapper.rootPath}/module/save.do" method="post" commandName="module">
		   <table>
			  <tr>
			     <td>子系统名称：</td>
			     <td>
			        <form:input path="name" maxlength="12" class="validate[required]" />
			        <form:hidden path="id"/>
			     </td>
			  </tr>
			  <tr>
			     <td>子系统编码：</td>
			     <td>
			        <form:input path="code" maxlength="200" class="validate[required]" />
			     </td>
			  </tr>
			  <tr>
			     <td>子系统描述：</td>
			     <td>
			        <form:input path="description" maxlength="200" class="validate[required]" />
			     </td>
			  </tr>
		   </table>
		</form:form>
	</body>
</html>

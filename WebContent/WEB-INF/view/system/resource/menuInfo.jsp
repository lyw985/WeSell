<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url value="/" var="root"></c:url>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>Insert title here</title>
</head>
<body>
	<form:form action="${commonMapper.rootPath}/resource/menu/add.do" commandName="menu">
		<table>
			<tr>
				<td>资源类型：</td>
				<td>
					<select class="type">
						<option value="module">子系统</option>
						<option value="menu" selected="selected">菜单</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>所属系统：</td>
				<td>
					<form:select cssClass="module" path="module" items="${modules}" itemValue="id" itemLabel="name"></form:select>
				</td>
			</tr>
			<tr>
				<td>父菜单：</td>
				<td>
					<form:hidden path="parentId" name="parentId" value="${parent.id }"/>
					<input name="parentName" id="parentName" value="${parent.name }" readonly="readonly">
					<img alt="选择" title="选择父菜单" src="${root }images/choose.gif" style="margin: 0px;padding: 0px;">
				</td>
			</tr>
			<tr>
				<td>菜单名称：</td>
				<td>
					<form:input path="name" id="name"/>
					<form:hidden path="id" id="id"/>
				</td>
			</tr>
			<tr>
				<td>菜单地址：</td>
				<td><form:input path="address"/></td>
			</tr>
			<tr>
				<td>菜单描述：</td>
				<td>
					<form:textarea path="description"/>
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>部门详细</title>
</head>
<body>
	<form:form action="${commonMapper.rootPath}/department/save.do" commandName="department">
		<table>
			<tr>
				<td>名称：</td>
				<td>
					<form:input path="name"/>
					<form:hidden path="id"/>
				</td>
			</tr>
			<tr>
				<td>上级部门：</td>
				<td>
					<input type="hidden" name="parentId" id="parentId" value="${parentDepartment.id }"/>
					<input type="text" id="parentName" readonly="readonly" value="${parentDepartment.name }">
					<img alt="选择" title="选择上级部门" src="${commonMapper.rootPath}/images/choose.gif" style="margin: 0px;padding: 0px;">
				</td>
			</tr>
			<tr>
				<td>负责人：</td>
				<td>
					<form:input path="chargePerson"/>
				</td>
			</tr>
			<tr>
				<td>固话：</td>
				<td>
					<form:input path="chargePersonPhone"/>
				</td>
			</tr>
			<tr>
				<td>手机：</td>
				<td>
					<form:input path="chargePersonMobile"/>
				</td>
			</tr>
			<tr>
				<td>描述：</td>
				<td>
					<form:input path="description"/>
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>
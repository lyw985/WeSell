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
		$(".dateTime").datepicker();
	});
</script>
</head>
<body>
	<form:form id="yumaItemForm"
		action="${pageContext.request.contextPath}/yuma/item/save.do" method="post"
		commandName="yumaItem">
		<form:hidden path="id" />
		<table width="100%">
			<tr>
				<th width="15%" align="right"><font color="red">*</font>商品名称：</th>
				<td width="85%" align="left"><form:input path="name"
						maxlength="64" cssClass="validate[required]" />
				</td>
			</tr>
			<tr>
				<th width="15%" align="right"><font color="red">*</font>商品状态：</th>
				<td width="85%" align="left">
					<form:select path="status">
						<form:option value="1">可用</form:option>
						<form:option value="0">禁用</form:option>
					</form:select>
				</td>
			</tr>
			<tr>
				<th width="15%" align="right"><font color="red">*</font>计量单位：</th>
				<td width="85%" align="left">
					<form:select path="type">
						<form:option value="0">斤</form:option>
						<form:option value="1">条</form:option>
						<form:option value="2">只</form:option>
						<form:option value="3">瓶</form:option>
						<form:option value="4">个</form:option>
					</form:select>
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>

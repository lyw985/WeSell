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
		action="${commonMapper.rootPath}/yuma/item/save.do" method="post"
		commandName="yumaItem">
		<form:hidden path="id" />
		<table width="100%">
			<tr>
				<th width="15%" align="right"><font color="red">*</font>商品名称：</th>
				<td width="85%" align="left"><form:input path="name"
						maxlength="64" cssClass="validate[required]" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>

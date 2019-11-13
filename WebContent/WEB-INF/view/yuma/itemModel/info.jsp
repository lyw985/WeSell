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
	<form:form id="yumaItemModelForm"
		action="${commonMapper.rootPath}/yuma/itemModel/save.do" method="post"
		commandName="yumaItemModel">
		<form:hidden path="id" />
		<form:hidden path="yumaItem.id" />
		<table>
			<tr>
				<th><font color="red">*</font>型号名称：</th>
				<td><form:input path="name" maxlength="64"
						cssClass="validate[required]" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>

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
		initItemSel("mapping_item_id", "mapping_item_model_id", "${item_id}",
				"${item_model_id}");
	});
</script>
</head>
<body>
	<form:form id="yumaWeidianItemModelMappingForm"
		action="${pageContext.request.contextPath}/yuma/weidianItem/saveMapping.do"
		method="post" commandName="yumaWeidianItemModelMapping">
		<form:hidden path="yumaWeidianItemModel.id" />
		<form:hidden path="id" />
		<table>
			<tr>
				<th>对象：</th>
				<td>${yumaWeidianItemModelMapping.yumaWeidianItemModel.name}</td>
			</tr>
			<tr>
				<th>商品：</th>
				<td><select id="mapping_item_id"
					onchange="initItemModelSel('mapping_item_model_id',this.value)" cssClass="validate[required]" ></select> <form:select
						id="mapping_item_model_id" path="yumaItemModel.id" cssClass="validate[required]" ></form:select></td>
			</tr>
			<tr>
				<th>数量：</th>
				<td><form:input path="count" maxlength="64" cssClass="validate[required]" />
					<div id="itemType"></div></td>
			</tr>
		</table>
	</form:form>
</body>
</html>

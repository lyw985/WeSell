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
		$(".dateTime").datepicker({
			dateFormat : 'yy-mm-dd'
		});
	});
</script>
</head>
<body>
	<form:form id="yumaImportForm" enctype="multipart/form-data"
		action="${commonMapper.rootPath}/yuma/order/importFile.do"
		method="post">
		<table width="100%">
			<tr>
				<th width="30%" align="right"><font color="red">*</font>微店格式数据：</th>
				<td width="40%" align="left"><input id="weidianFile"
					name="weidianFile" type="file" /></td>
				<td width="30%" align="left"><a href="${commonMapper.rootPath}/attached/file/weidian_data_template.xls">样本下载</a></td>
			</tr>
		</table>
	</form:form>
</body>
</html>

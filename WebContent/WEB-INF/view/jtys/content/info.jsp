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
		showTab($("#msgType").val());
	});
	function showTab(val) {
		if (val == 1) {
			$("#contentTab").show();
			$("#tuwenTab").hide();
		} else {
			$("#contentTab").hide();
			$("#tuwenTab").show();
		}
	}
</script>
</head>
<body>
	<form:form id="jtysContentForm"
		action="${commonMapper.rootPath}/jtys/content/save.do" method="post"
		commandName="jtysContent">
		<form:hidden path="id" />
		<table>
			<tr>
				<th>内容类型：</th>
				<td><form:select path="type" cssClass="validate[required]"
						items="${jtysContentTypes }" itemValue="type" itemLabel="tip">
					</form:select></td>
			</tr>
			<tr>
				<th>消息类型：</th>
				<td><form:select path="msgType" cssClass="validate[required]"
						onchange="showTab(this.value)">
						<form:option value="1">文字消息</form:option>
						<form:option value="2">图文消息</form:option>
					</form:select>
				</td>
			</tr>
		</table>
		<table id="contentTab" style="display: none;">
			<tr>
				<th><font color="red">*</font>文本内容：</th>
				<td><form:textarea path="content" rows="3" maxlength="512"
						cssClass="validate[required]" />
				</td>
			</tr>
		</table>
		<table id="tuwenTab" style="display: none;">
			<tr>
				<th><font color="red">*</font>图文标题：</th>
				<td><form:input path="title" maxlength="512"
						cssClass="validate[required]" />
				</td>
			</tr>
			<tr>
				<th><font color="red">*</font>图文描述：</th>
				<td><form:textarea path="description" rows="3" maxlength="1024"
						cssClass="validate[required]" />
				</td>
			</tr>
			<tr>
				<th><font color="red">*</font>图片链接：</th>
				<td><form:input path="picUrl" maxlength="256"
						cssClass="validate[required]" /></td>
			</tr>
			<tr>
				<th><font color="red">*</font>全文链接：</th>
				<td><form:input path="url" maxlength="256"
						cssClass="validate[required]" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>

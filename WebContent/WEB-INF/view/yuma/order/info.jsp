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
		$(".dateTime").datepicker({dateFormat: 'yy-mm-dd'});
		
		if("${yumaOrder.yumaReceiver.id}"!=""){
			$("#receiverInfoDiv").html("( ${yumaOrder.yumaReceiver.yumaUser.name} ${yumaOrder.yumaReceiver.yumaUser.nickname} ) ${yumaOrder.yumaReceiver.name} ${yumaOrder.yumaReceiver.phone} ${yumaOrder.yumaReceiver.detail}");
			$("#receiverSearchDiv").hide();
			$("#searchedReceiverTr").hide();
			$("#receiverShowDiv").show();
		}
		
	});

	function searchReceiver(searchValue) {
		$("#searchedReceiverUl").html("");
		$.post("${commonMapper.rootPath}/yuma/receiver/searchReceiver.do",
				"&searchValue=" + searchValue, function(data) {
					if (data.error == '0') {
						if(data.data.length==0){
							$("#searchedReceiverUl").html("<li>搜索不到任何收件人信息</li>");
						}else{
							for(var i=0;i<data.data.length;i++){
								var username=data.data[i].username;
								if(!username){
									username="";
								}
								var usernickname=data.data[i].usernickname;
								if(!usernickname){
									usernickname="";
								}
								$("#searchedReceiverUl").append("<li onclick='selectReceiver("+data.data[i].id+", this)' >( "+username+" "+usernickname+" ) "+data.data[i].name+" "+data.data[i].phone+" "+data.data[i].detail+"</li>");
							}
						}
					} else {
						jAlert(data.msg, '失败');
					}
				}, "json");
	}
	function selectReceiver(id, obj){
		$("#receiverSearchDiv").hide();
		$("#searchedReceiverTr").hide();
		$("#receiverShowDiv").show();
		$("#receiverInfoDiv").html($(obj).text());
		$("[name='yumaReceiver.id']").val(id);
	}
	
	function goSelectReceiver(){
		$("#receiverSearchDiv").show();
		$("#searchedReceiverTr").show();
		$("#receiverShowDiv").hide();
		$("#receiverInfoDiv").html("");
		$("[name='yumaReceiver.id']").val("");
	}
</script>
</head>
<body>
	<form:form id="yumaOrderForm"
		action="${commonMapper.rootPath}/yuma/order/save.do" method="post"
		commandName="yumaOrder">
		<form:hidden path="id" />
		<form:hidden path="yumaReceiver.id" value=""/>
		<table width="100%">
			<tr>
				<th width="15%" align="right"><font color="red">*</font>收件人信息：</th>
				<td width="85%" align="left">
					<div id="receiverSearchDiv">
						<input id="receiverInput" type="text" value="请输入内容"
							onfocus="if(this.value=='请输入内容'){this.value=''}"
							onblur="if(this.value==''){this.value='请输入内容'}" /> <input
							type="button" value="搜索"
							onclick="searchReceiver($('#receiverInput').val())" />
					</div>
					<div id="receiverShowDiv" style="display: none;">
						<div id="receiverInfoDiv"></div>
						<input type="button" value="重选" onclick="goSelectReceiver()" />
					</div>
				</td>
			</tr>
			<tr id="searchedReceiverTr">
				<td colspan="2">
					<ul id="searchedReceiverUl">
					</ul>
				</td>
			</tr>
			<tr>
				<th width="15%" align="right"><font color="red">*</font>收货日期：</th>
				<td width="85%" align="left"><form:input path="payDate" class="dateTime"/></td>
			</tr>
		</table>
	</form:form>
</body>
</html>

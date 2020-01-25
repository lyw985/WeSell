<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>${commonMapper.title}</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hodanet.css"  />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination/linkbutton.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination/pagination.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/alert/jquery.alerts.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cupertino/jquery-ui-1.8.15.custom.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/validationEngine.jquery.css"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.linkbutton.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hodanet.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
		
		$(".sendCustomMsg").click(function(){
			$("#divDialog").load('/weixin/msg/gotoSendCustomMsg.do?serverCode=${serverCode}',null,function(){
				$("#sendCustomMsgForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({ 'width': 400,'height': 200,'title': '发送客服消息',
					'buttons':{
						'保存':function(){
							if($(this).find('#sendCustomMsgForm').validationEngine("validate")){
								$(this).find('#sendCustomMsgForm').ajaxSubmit({ 
							        dataType:  'json',
							        type: 'POST',
							        success:   showAddResponse 
							    }); 
							}
						},
						'取消':function(){
						}
					},
					modal:true,
					beforeClose:function(event, ui) {
						$(this).find('#sendCustomMsgForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
					}
				});
			});
		});
		
		function showAddResponse(data)  { 
			if(data.flag){
				jAlert(data.msg,'成功');
			}else{
				jAlert(data.msg,'失败');
			}
		}
		
	});
	
</script>
</head>
<body>
	<div class="handle">
		<span>基础信息</span>
		<a href="http://mp.weixin.qq.com/debug/" target="_blank" class="add" id="addButton">在线调试</a>
	</div>
	<!-- --------------------信息记录列表-------------------- -->
	<div class="listDiv">
		<table class="tabCls" cellpadding="0" cellspacing="0" width="99%">
			<tr>
				<th width="20%">操作类型</th>
				<th width="20%">调用接口</th>
				<th width="60%">详情</th>
			</tr>
			<tr class="odd">
				<td width="20%">获取accessToken</td>
				<td width="20%"><a
					href="${pageContext.request.contextPath}/weixin/msg/getAccessToken.do?serverCode=${serverCode}">调用接口</a>
				</td>
				<td width="60%">当前accessToken值:<font color="green">${accessToken}</font><br />
					(access_token是公众号的全局唯一票据，正常情况下access_token有效期为7200秒，重复获取将导致上次获取的access_token失效。)</td>
			</tr>
			<tr class="even">
				<td width="20%">发送客服消息</td>
				<td width="20%"><a href="javascript:void(0)"
					class="sendCustomMsg">调用接口</a>
				</td>
				<td width="60%"></td>
			</tr>
			<!-- 各类接口处理 -->
		</table>
	</div>
	<!-- --------------------弹出页面-------------------- -->
	<div id="divDialog"></div>
</body>
</html>
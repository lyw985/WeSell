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
			$("#divDialog").load('/weixin/msg/gotoSendCustomMsg.do',null,function(){
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
		<a href="#" class="add" rel="${busiCode}" id="addButton">新增</a>
	</div>
	<!-- --------------------查询表单-------------------- -->
	<div class="ui-widget">
		<form:form id="queryForm" action="${pageContext.request.contextPath}/weixin/menu/list.do?serverCode=${serverCode }" method="post">
		</form:form>
	</div>
	<!-- --------------------信息记录列表-------------------- -->
	<div class="listDiv">
		<table class="tabCls" cellpadding="0" cellspacing="0" width="99%">
			<tr>
				<th width="20%">菜单类型</th>
				<th width="20%">菜单名称</th>
				<th width="20%">响应动作类型</th>
				<th width="40%">响应详情</th>
			</tr>
			<c:choose>
				<c:when test="${buttonObject != null}">
					<c:forEach items="${buttonObject}" var="button" varStatus="vs">
						<tr class="even" style="font-weight: bold;">
							<td>一级菜单</td>
							<td>${button.name}</td>
							<td>${button.type}</td>
							<td>
								<c:if test="${!empty button.type }">
									<c:choose>
										<c:when test="${button.type=='click'}">
											${button.key}
										</c:when>
										<c:otherwise>
											${button.url}
										</c:otherwise>
									</c:choose>
								</c:if>
							</td>
						</tr>
						<c:choose>
							<c:when test="${button.sub_button != null}">
								<c:forEach items="${button.sub_button}" var="subButton" varStatus="svs">
									<tr class="odd">
										<td>二级菜单</td>
										<td>${subButton.name}</td>
										<td>${subButton.type}</td>
										<c:choose>
											<c:when test="${subButton.type=='click'}">
												<td>${subButton.key}</td>
											</c:when>
											<c:otherwise>
												<td>${subButton.url}</td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="bottomCls">
						<td colspan="6" style="text-align: center">
							<div class="emptyData">没有查询到相关的数据。</div>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
	<!-- --------------------弹出页面-------------------- -->
	<div id="divDialog"></div>
</body>
</html>
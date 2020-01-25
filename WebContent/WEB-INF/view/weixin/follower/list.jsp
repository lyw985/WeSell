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
		
		$(".viewInfo").click(function(){
			var tmp = "&openid="+$(this).attr('rel');
			$("#divDialog").load('/weixin/follower/info.do?serverCode=${serverCode}',tmp.substring(1),function(){
				$("#followerInfoForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({ 'width': 500,'height': 500,'title': '用户详细信息',
					'buttons':{
						
						'关闭':function(){
						}
					},
					modal:true,
					beforeClose:function(event, ui) {
						$(this).find('#followerInfoForm').validationEngine("hideAll");
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
		<span>关注者列表信息</span>
	</div>
	<!-- --------------------查询表单-------------------- -->
	<div class="ui-widget">
		<form:form id="queryForm" action="${pageContext.request.contextPath}/weixin/follower/list.do" method="post">
		</form:form>
	</div>
	<!-- --------------------信息记录列表-------------------- -->
	<div class="listDiv">
		<table class="tabCls" cellpadding="0" cellspacing="0" width="99%">
			<tr>
				<th width="30%">openid</th>
				<th width="30%">备注</th>
				<th width="40%">操作</th>
			</tr>
			<c:choose>
				<c:when test="${follower != null && follower.data != null}">
					<c:forEach items="${follower.data.openid}" var="openid" varStatus="vs">
						<tr class="even">
							<td>${openid}</td>
							<td></td>
							<td><a href="javascript:void(0)" class="viewInfo"  rel="${openid}">详情</a></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="bottomCls">
						<td colspan="3" style="text-align: center">
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
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
<title>${commonMweixiner.title}</title>
<link rel="stylesheet" type="text/css" href="${commonMweixiner.rootPath}/css/hodanet.css"  />
<link rel="stylesheet" type="text/css" href="${commonMweixiner.rootPath}/css/pagination/linkbutton.css">
<link rel="stylesheet" type="text/css" href="${commonMweixiner.rootPath}/css/pagination/icon.css">
<link rel="stylesheet" type="text/css" href="${commonMweixiner.rootPath}/css/pagination/pagination.css">
<link rel="stylesheet" type="text/css" href="${commonMweixiner.rootPath}/css/alert/jquery.alerts.css"/>
<link rel="stylesheet" type="text/css" href="${commonMweixiner.rootPath}/css/cupertino/jquery-ui-1.8.15.custom.css"/>
<link rel="stylesheet" type="text/css" href="${commonMweixiner.rootPath}/css/validationEngine.jquery.css"/>

<script type="text/javascript" src="${commonMweixiner.rootPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${commonMweixiner.rootPath}/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="${commonMweixiner.rootPath}/js/jquery.linkbutton.js"></script>
<script type="text/javascript" src="${commonMweixiner.rootPath}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${commonMweixiner.rootPath}/js/jquery.alerts.js"></script>
<script type="text/javascript" src="${commonMweixiner.rootPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${commonMweixiner.rootPath}/js/hodanet.js"></script>
<script type="text/javascript" src="${commonMweixiner.rootPath}/js/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="${commonMweixiner.rootPath}/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${commonMweixiner.rootPath}/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
	$(function() {
		var pager = $("#pager");
		pager.pagination({
			pageSize: pager.attr("pageSize"),
			pageNumber: pager.attr("pageNumber"),
			total: pager.attr("total"),
			onSelectPage:
				function(pageNumber, pageSize) {
					pager.pagination({loading:true});
					$("#pageSize").val(pageSize);
					$("#pageNumber").val(pageNumber);
					$("#queryForm").submit();
				}
		});

		$(".selAll").click(function() {
			$(".selOne").attr("checked", !!$(".selAll").attr("checked"));
		});

		$(".selOne").click(function() {
			if($(".selOne").length == $(".selOne:checked").length) {
				$(".selAll").attr("checked", true);
			} else {
				$(".selAll").attr("checked", false);
			}
		});
		
		//新增按钮
		$("#addButton").click(function(){
			$("#divDialog").load('${commonMweixiner.rootPath}/weixin/order/new.do?serverCode=' + $(this).attr('rel'),null,function(){
				$("#orderForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({ 'width': 400,'height': 400,'title': '系统配置新增',
					'buttons':{
						'保存':function(){
							if($(this).find('#orderForm').validationEngine("validate")){
								$(this).find('#orderForm').ajaxSubmit({ 
							        dataType:  'json',
							        type: 'POST',
							        success:   showAddResponse 
							    }); 
							}
						},
						'取消':function(){
							$("#queryForm").submit();
						}
					},
					modal:true,
					beforeClose:function(event, ui) {
						$(this).find('#orderForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		//修改按钮
		$(".btnModify").click(function(){
			$("#divDialog").load('${commonMweixiner.rootPath}/weixin/order/modify/'+$(this).attr('rel')+'.do',null,function(){
				$("#orderForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({title:'系统配置修改',resizable: false,height:400,width:400,
					'buttons':{
						'保存':function(){
							if($(this).find('#orderForm').validationEngine("validate")){
								$(this).find('#orderForm').ajaxSubmit({ 
							        dataType:  'json',
							        type: 'POST',
							        success:   showAddResponse 
							    }); 
							}
						},
						'取消':function(){
							$("#queryForm").submit();
						}
					},
					modal:true,
					beforeClose:function(event, ui) {
						$(this).find('#orderForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		// 批量删除
		$("#deleteButton").click(function() {
			if ($(".selOne:checked").length <=0 ) {
				jAlert("请选择要删除的记录", "警告");
				return;
			}
			jConfirm('确认要删除记录吗？', '确认操作', function(result){
				if(result == true){
					var ids = "";
					$(".selOne:checked").each(function() {
						ids += "&id=" + $(this).val();
					});
					$.post("${commonMweixiner.rootPath}/weixin/order/delete.do", ids.substring(1), function(data) {
						if (data.flag) {
							jAlert(data.msg, "成功", function() {
							$("#queryForm").submit();
							});
						} else {
							jAlert(data.msg, "失败");
						}
					}, "json");
				}
			});
		});
		
		// 单条删除
		$(".singleDelete").click(function(){
			var tmp = "&id="+$(this).attr('rel');
			jConfirm('确认要删除记录吗？', '确认操作', function(result){
				if(result == true){
					$.post('${commonMweixiner.rootPath}/weixin/order/delete.do',tmp.substring(1),function(data){
						if(data.flag){
							jAlert(data.msg,'成功',function(){
								$("#queryForm").submit();
							});
						}else{
							jAlert(data.msg,'失败');
						}
					},'json');
				}
			});
		});
		
		$(".queryOrder").click(function(){
			var tmp = "&outTradeNo="+$(this).attr('rel');
			$("#divDialog").load('/weixin/order/queryOrder.do?serverCode=${serverCode}',tmp.substring(1),function(){
				$("#divDialog").dialog({ 'width': 500,'height': 500,'title': '订单详细信息',
					modal:true,
					beforeClose:function(event, ui) {
					},
					close:function(event, ui) {
					}
				});
			});
		});
		
		$(".pass").click(function(){
			var tmp = "&id="+$(this).attr('rel');
			jConfirm('确认执行生效操作吗？', '确认操作', function(result){
				if(result == true){
					$.post('${commonMweixiner.rootPath}/weixin/order/pass.do',tmp.substring(1),function(data){
						if(data.flag){
							jAlert(data.msg,'成功',function(){
								$("#queryForm").submit();
							});
						}else{
							jAlert(data.msg,'失败');
						}
					},'json');
				}
			});
		});
		
		$(".refuse").click(function(){
			var tmp = "&id="+$(this).attr('rel');
			jConfirm('确认执行失效操作吗？', '确认操作', function(result){
				if(result == true){
					$.post('${commonMweixiner.rootPath}/weixin/order/refuse.do',tmp.substring(1),function(data){
						if(data.flag){
							jAlert(data.msg,'成功',function(){
								$("#queryForm").submit();
							});
						}else{
							jAlert(data.msg,'失败');
						}
					},'json');
				}
			});
		});
		
		$("#status").val("${status}");
	});
	
	//添加结果
	function showAddResponse(data)  { 
		if(data.flag){
			jAlert(data.msg,'成功',function(){
				$("#queryForm").submit();
			});
		}else{
			jAlert(data.msg,'失败');
		}
	}
</script>
</head>
<body>
	<div class="handle">
		<span>订单列表</span>
		<!-- <a href="#" class="add" rel="${serverCode}" id="addButton">新增</a>
		<a href="#" class="remove" id="deleteButton">删除</a> -->
	</div>
	<!-- --------------------查询表单-------------------- -->
	<div class="ui-widget">
		<form:form id="queryForm" action="${commonMweixiner.rootPath}/weixin/order/query.do" method="post">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageData.pageSize }"/> 
			<input type="hidden" id="pageNumber" name="pageNumber" value="${pageData.pageNumber }"/>
			<input type="hidden" id="serverCode" name="serverCode" value="${serverCode}"/>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>商品名称：</td>
					<td>
						<input type="text" id="searchName" name="searchName" value="${searchName}" style="width:150px;" />
					</td>
					<td>状态：</td>
					<td>
						<select id="status" name="status" value="${status}">
							<option value="">--选择订单状态--</option>
							<option value="0">未支付</option>
							<option value="1">支付取消</option>
							<option value="2">支付失败</option>
							<option value="3">支付成功</option>
							<option value="4">收到通知</option>
						</select>
					</td>
					<td>
						<input type="submit" id="btnSearch" name="btnSearch" class="buttonStyle" value="查询" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
	<!-- --------------------信息记录列表-------------------- -->
	<div class="listDiv">
		<table class="tabCls" cellpadding="0" cellspacing="0" width="99%">
			<tr>
				<th cssClass="width:"><input type="checkbox" class="selAll"></th>
				<th>系统订单号</th>
				<th>用户</th>
				<th>商品名称</th>
				<th>价格(分)</th>
				<th>创建时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			<c:choose>
				<c:when test="${pageData.data != null && fn:length(pageData.data) > 0}">
					<c:forEach items="${pageData.data }" var="order" varStatus="vs">
						<tr ${vs.index % 2 == 0 ? "class='odd'" : "class='even'"} >
							<td><input type="checkbox" class="selOne" value="${order.id}"></td>
							<td>${order.outTradeNo }</td>
							<td>${order.openId}</td>
							<td>${order.body}</td>
							<td>${order.totalFee}</td>
							<td><fmt:formatDate value="${order.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
							<td>
								<c:if test="${order.status==0 }"><font color='blue'>${order.statusStr}</font></c:if>
								<c:if test="${order.status==1 }"><font color='orange'>${order.statusStr}</font></c:if>
								<c:if test="${order.status==2 }"><font color='red'>${order.statusStr}</font></c:if>
								<c:if test="${order.status==3 }"><font color='cyan'>${order.statusStr}</font></c:if>
								<c:if test="${order.status==4 }"><font color='green'>${order.statusStr}</font></c:if>
							</td>
							<td>
								<a href="javascript:void(0)" class="queryOrder"  rel="${order.outTradeNo}">详情</a>
							</td>
						</tr>
					</c:forEach>
					
					<!-- 分页 -->
					<tr class="bottomCls">
						<td colspan="20">
							<div id="pager" pageSize="${pageData.pageSize}" pageNumber="${pageData.pageNumber}" total="${pageData.total}"></div>
						</td>
					</tr>
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
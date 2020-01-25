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
			$("#divDialog").load('/yuma/item/new.do',null,function(){
				$("#yumaItemForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({ 'width': 600,'height': 400,'title': '信息新增',
					'buttons':{
						'保存':function(){
							if($(this).find('#yumaItemForm').validationEngine("validate")){
								$(this).find('#yumaItemForm').ajaxSubmit({ 
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
						$(this).find('#yumaItemForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		//修改按钮
		$(".btnModify").click(function(){
			$("#divDialog").load('/yuma/item/modify/'+$(this).attr('rel')+'.do',null,function(){
				$("#yumaItemForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({title:'信息修改',resizable: false,height:400,width:600,
					'buttons':{
						'保存':function(){
							if($(this).find('#yumaItemForm').validationEngine("validate")){
								$(this).find('#yumaItemForm').ajaxSubmit({ 
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
						$(this).find('#yumaItemForm').validationEngine("hideAll");
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
					$.post("${pageContext.request.contextPath}/yuma/item/delete.do", ids.substring(1), function(data) {
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
					$.post('/yuma/item/delete.do',tmp.substring(1),function(data){
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
		
		$(".inavailableItem").click(function(){
			var tmp = "&id="+$(this).attr('rel');
			jConfirm('确认设置商品为禁用吗？', '确认操作', function(result){
				if(result == true){
					$.post('/yuma/item/inavailableItem.do',tmp.substring(1),function(data){
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
		
		$(".availableItem").click(function(){
			var tmp = "&id="+$(this).attr('rel');
			jConfirm('确认设置商品为可用吗？', '确认操作', function(result){
				if(result == true){
					$.post('/yuma/item/availableItem.do',tmp.substring(1),function(data){
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
		
		$(".itemModelList").click(function(){
			window.location.href="${pageContext.request.contextPath}/yuma/itemModel/list.do?item_id="+$(this).attr('rel');
		});
		
		$("#name").val("${name}");
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
		<span>鱼妈海鲜-->商品管理</span>
		<a href="#" class="add" id="addButton">新增</a>
		<!-- <a href="#" class="remove" id="deleteButton">删除</a> -->
	</div>
	<!-- --------------------查询表单-------------------- -->
	<div class="ui-widget">
		<form:form id="queryForm" action="${pageContext.request.contextPath}/yuma/item/query.do" method="post">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageData.pageSize }"/> 
			<input type="hidden" id="pageNumber" name="pageNumber" value="${pageData.pageNumber }"/>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>商品名称：</td>
					<td>
						<input type="text" id="name" name="name" value="${name}" style="width:100px;" />
					</td>
					<td>状态：</td>
					<td>
						<select id="status" name="status" value="${status}">
							<option value="">--选择商品状态--</option>
							<option value="0">禁用</option>
							<option value="1">可用</option>
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
				<th width="5%"><input type="checkbox" class="selAll"></th>
				<th width="10%">商品名称</th>
				<th width="20%">型号列表</th>
				<th width="10%">计量单位</th>
				<th width="10%">状态</th>
				<th width="20%">操作</th>
			</tr>
			<c:choose>
				<c:when test="${pageData.data != null && fn:length(pageData.data) > 0}">
					<c:forEach items="${pageData.data }" var="yumaItem" varStatus="vs">
						<tr ${vs.index % 2 == 0 ? "class='odd'" : "class='even'"} >
							<td><input type="checkbox" class="selOne" value="${yumaItem.id}"></td>
							<td>${yumaItem.name}</td>
							<td>
								<c:forEach items="${yumaItem.yumaItemModels }" var="yumaItemModel">
									${yumaItemModel.name} <c:if test="${yumaItemModel.status==0}"><font color="red">(${yumaItemModel.statusStr})</font></c:if>
									<c:if test="${yumaItemModel.status==1}"><font color="green">(${yumaItemModel.statusStr})</font></c:if><br/>
								</c:forEach>
							</td>
							<td>
								${yumaItem.typeStr}
							</td>
							<td>
								<c:if test="${yumaItem.status==0}"><font color="red">${yumaItem.statusStr}</font></c:if>
								<c:if test="${yumaItem.status==1}"><font color="green">${yumaItem.statusStr}</font></c:if>
							</td>
							<td>
								<c:if test="${yumaItem.status!=1}">
									<a href="javascript:void(0)" rel="${yumaItem.id}" class="availableItem">设为可用</a>
								</c:if>
								<c:if test="${yumaItem.status!=0}">
									<a href="javascript:void(0)" rel="${yumaItem.id}" class="inavailableItem">设为禁用</a>
								</c:if>
								<a href="javascript:void(0)" rel="${yumaItem.id}" class="itemModelList">查看型号</a>
								<a href="javascript:void(0)" rel="${yumaItem.id}" class="btnModify">修改</a>
								<c:if test="${fn:length(yumaItem.yumaItemModels)==0 }">
									<a href="javascript:void(0)" rel="${yumaItem.id}" class="singleDelete">删除</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
					
					<!-- 分页 -->
					<tr class="bottomCls">
						<td colspan="7">
							<div id="pager" pageSize="${pageData.pageSize}" pageNumber="${pageData.pageNumber}" total="${pageData.total}"></div>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr class="bottomCls">
						<td colspan="7" style="text-align:center">
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
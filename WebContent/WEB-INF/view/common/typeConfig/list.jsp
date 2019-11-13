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
<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/hodanet.css"  />
<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/pagination/linkbutton.css">
<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/pagination/icon.css">
<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/pagination/pagination.css">
<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/alert/jquery.alerts.css"/>
<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/cupertino/jquery-ui-1.8.15.custom.css"/>
<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/validationEngine.jquery.css"/>

<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.linkbutton.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.alerts.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/hodanet.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.ui.datepicker-zh-CN.js"></script>
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
			$("#divDialog").load('${commonMapper.rootPath}/common/typeConfig/new.do',null,function(){
				$("#typeConfigForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({ 'width': 300,'height': 300,'title': '会议新增',
					'buttons':{
						'保存':function(){
							if($(this).find('#typeConfigForm').validationEngine("validate")){
								$(this).find('#typeConfigForm').ajaxSubmit({ 
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
						$(this).find('#typeConfigForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		

		//修改按钮
		$(".btnModify").click(function(){
			$("#divDialog").load('${commonMapper.rootPath}/common/typeConfig/modify/'+$(this).attr('rel')+'.do',null,function(){
				$("#typeConfigForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({title:'会议修改',resizable: false,height:300,width:300,
					'buttons':{
						'保存':function(){
							if($(this).find('#typeConfigForm').validationEngine("validate")){
								$(this).find('#typeConfigForm').ajaxSubmit({ 
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
						$(this).find('#typeConfigForm').validationEngine("hideAll");
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
					$.post("${commonMapper.rootPath}/common/typeConfig/delete.do", ids.substring(1), function(data) {
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
					$.post('${commonMapper.rootPath}/common/typeConfig/delete.do',tmp.substring(1),function(data){
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
		<span>类型配置列表</span>
		<a href="#" class="add" id="addButton">新增</a>
		<a href="#" class="remove" id="deleteButton">删除</a>
	</div>
	<!-- --------------------查询表单-------------------- -->
	<div class="ui-widget">
		<form:form id="queryForm" action="${commonMapper.rootPath}/common/typeConfig/query.do" method="post">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageData.pageSize }"/> 
			<input type="hidden" id="pageNumber" name="pageNumber" value="${pageData.pageNumber }"/>
			
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>名称：</td>
					<td>
						<input type="text" id="searchName" name="searchName" value="${searchName}" style="width:150px;" />
					</td>
					<td>编码：</td>
					<td>
						<input type="text" id="searchCode" name="searchCode" value="${searchCode}" style="width:150px;" />
					</td>
					<td>所属类型名称：</td>
					<td>
						<input type="text" id="searchModule" name="searchModule" value="${searchModule}" style="width:150px;" />
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
				<th><input type="checkbox" class="selAll"></th>
				<th>名称</th>
				<th>编码</th>
				<th>所属类型名称</th>
				<th>排序</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
			<c:choose>
				<c:when test="${pageData.data != null && fn:length(pageData.data) > 0}">
					<c:forEach items="${pageData.data }" var="typeConfig" varStatus="vs">
						<tr ${vs.index % 2 == 0 ? "class='odd'" : "class='even'"}>
							<td><input type="checkbox" class="selOne" value="${typeConfig.id}"></td>
							<td><c:out value="${typeConfig.name}"></c:out></td>
							<td><c:out value="${typeConfig.code}"></c:out></td>
							<td><c:out value="${typeConfig.module}"></c:out></td>
							<td><c:out value="${typeConfig.ordering}"></c:out></td>
							<td><c:out value="${typeConfig.remark}"></c:out></td>
							<td>
								<a href="javascript:void(0)" rel="${typeConfig.id}" class="btnModify">修改</a>
								<a href="javascript:void(0)" rel="${typeConfig.id}" class="singleDelete">删除</a>
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
						<td colspan="7" style="text-align: center">
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
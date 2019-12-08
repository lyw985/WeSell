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
<script type="text/javascript" src="${commonMapper.rootPath}/js/util/yuma.js"></script>
<script type="text/javascript">
	$(function() {
		$(".dateTime").datepicker({dateFormat: 'yy-mm-dd',changeMonth: true, changeYear: true});
		
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
		
		//导入按钮
		$("#importButton").click(function(){
			$("#divDialog").load('${commonMapper.rootPath}/yuma/order/import.do',null,function(){
				$("#yumaImportForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({ 'width': 600,'height': 400,'title': '导入数据',
					'buttons':{
						'保存':function(){
							if($(this).find('#yumaImportForm').validationEngine("validate")){
								$(this).find('#yumaImportForm').ajaxSubmit({ 
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
						$(this).find('#yumaOrderForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		//新增按钮
		$("#addButton").click(function(){
			$("#divDialog").load('${commonMapper.rootPath}/yuma/order/new.do',null,function(){
				$("#yumaOrderForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({ 'width': 600,'height': 400,'title': '订单新增',
					'buttons':{
						'保存':function(){
							var receiverRadioValue=$(this).find("[name='yumaReceiver.id']").val();
							if(!receiverRadioValue){
								alert("未选择收件人信息，请选择后重试。");
							}else{
								if($(this).find('#yumaOrderForm').validationEngine("validate")){
									$(this).find('#yumaOrderForm').ajaxSubmit({ 
								        dataType:  'json',
								        type: 'POST',
								        success:   showAddResponse 
								    }); 
								}
							}
						},
						'取消':function(){
							$("#queryForm").submit();
						}
					},
					modal:true,
					beforeClose:function(event, ui) {
						$(this).find('#yumaOrderForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		//修改按钮
		$(".btnModify").click(function(){
			$("#divDialog").load('${commonMapper.rootPath}/yuma/order/modify/'+$(this).attr('rel')+'.do',null,function(){
				$("#yumaOrderForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({title:'订单修改',resizable: false,height:400,width:600,
					'buttons':{
						'保存':function(){
							if($(this).find('#yumaOrderForm').validationEngine("validate")){
								$(this).find('#yumaOrderForm').ajaxSubmit({ 
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
						$(this).find('#yumaOrderForm').validationEngine("hideAll");
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
					$.post("${commonMapper.rootPath}/yuma/order/delete.do", ids.substring(1), function(data) {
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
					$.post('${commonMapper.rootPath}/yuma/order/delete.do',tmp.substring(1),function(data){
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
		
		$(".passVersion").click(function(){
			var tmp = "&id="+$(this).attr('rel');
			jConfirm('确认执行生效操作吗？', '确认操作', function(result){
				if(result == true){
					$.post('${commonMapper.rootPath}/yuma/order/pass.do',tmp.substring(1),function(data){
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
		
		$(".refuseVersion").click(function(){
			var tmp = "&id="+$(this).attr('rel');
			jConfirm('确认执行失效操作吗？', '确认操作', function(result){
				if(result == true){
					$.post('${commonMapper.rootPath}/yuma/order/refuse.do',tmp.substring(1),function(data){
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
		
		$("#backButton").click(function() {
			window.location.href="${commonMapper.rootPath}/yuma/user/list.do";
		});
		
		$("#startDate").val("${startDate}")
		$("#endDate").val("${endDate}")
		initProvinceSel("province_id","city_id","area_id","${province_id}","${city_id}","${area_id}");
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
		<span>鱼妈海鲜-->订单列表</span>
		<a href="#" class="import" id="importButton">导入数据</a>
		<!--  <a href="#" class="add" id="addButton">新增</a>
		<a href="#" class="remove" id="deleteButton">删除</a> -->
	</div>
	<!-- --------------------查询表单-------------------- -->
	<div class="ui-widget">
		<form:form id="queryForm" action="${commonMapper.rootPath}/yuma/order/query.do" method="post">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageData.pageSize }"/> 
			<input type="hidden" id="pageNumber" name="pageNumber" value="${pageData.pageNumber }"/>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>姓名：</td>
					<td>
						<input type="text" id="receiverName" name="receiverName" value="${receiverName}" style="width:100px;" />
					</td>
					<td>号码：</td>
					<td>
						<input type="text" id="receiverPhone" name="receiverPhone" value="${receiverPhone}" style="width:100px;" />
					</td>
					<!-- <td>订单包含：</td>
					<td>
						<input type="text" id="itemName" name="itemName" value="${itemName}" style="width:100px;" />
					</td> -->
					<td>地区：</td>
					<td>
						<select id="province_id" name="province_id" onchange="initCitySel('city_id','area_id',this.value)"></select>
						<select id="city_id" name="city_id" onchange="initAreaSel('area_id',this.value)"></select>
						<select id="area_id" name="area_id" ></select>
					</td>
					<td>日期：</td>
					<td>
						<input id="startDate" name="startDate"  class="dateTime" style="width:75px;"/>-<input id="endDate" name="endDate"  class="dateTime" style="width:75px;"/>
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
				<th width="5%">收货人姓名</th>
				<th width="10%">收货人号码</th>
				<th width="30%">收货人地址</th>
				<th width="10%">订单金额</th>
				<th width="10%">发货日期</th>
				<th width="20%">订单内容</th>
			</tr>
			<c:choose>
				<c:when test="${pageData.data != null && fn:length(pageData.data) > 0}">
					<c:forEach items="${pageData.data }" var="yumaOrder" varStatus="vs">
						<tr ${vs.index % 2 == 0 ? "class='odd'" : "class='even'"} >
							<td><input type="checkbox" class="selOne" value="${yumaOrder.id}"></td>
							<td>${yumaOrder.yumaReceiver.name}</td>
							<td>${yumaOrder.yumaReceiver.phone}</td>
							<td>${yumaOrder.yumaReceiver.addressDetail}</td>
							<td>${yumaOrder.payPrice} (原价 ${yumaOrder.originalPrice})</td>
							<td>${yumaOrder.deliverDateStr}</td>
							<td>
								<!-- <a href="javascript:void(0)" rel="${yumaOrder.id}" class="btnModify">修改</a>
								<a href="javascript:void(0)" rel="${yumaOrder.id}" class="singleDelete">删除</a> -->
								<c:forEach items="${yumaOrder.yumaOrderItems }" var="yumaOrderItem" varStatus="vs2">
									<c:if test="${vs2.index !=0 }">
										<br/>
									</c:if>
									${yumaOrderItem.weidianItemModel.name }【${yumaOrderItem.count }】
								</c:forEach>
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
						<td colspan="20" style="text-align:center">
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
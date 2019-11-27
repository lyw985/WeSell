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
<script type="text/javascript" src="${commonMapper.rootPath}/js/chart/Chart.min.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/chart/utils.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/util/yuma.js"></script>
<script type="text/javascript">
	$(function() {
		$(".dateTime").datepicker({dateFormat: 'yy-mm-dd',changeMonth: true, changeYear: true});
		
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
		
		initItemSel("item_id", "item_model_id", "${item_id}","${item_model_id}");
		initProvinceSel("province_id","city_id","area_id","${province_id}","${city_id}","${area_id}");
		
		var chartConfig=eval("("+$("#chartConfig").val()+")");
		
		window.onload = function() {
			var ctx = document.getElementById('canvas').getContext('2d');
			window.myLine = new Chart(ctx, chartConfig);
		};
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
		<span>鱼妈海鲜-->数据统计</span>
		<!-- 
		<a href="#" class="add" id="addButton">新增</a>
		<a href="#" class="remove" id="deleteButton">删除</a> -->
	</div>
	<!-- --------------------查询表单-------------------- -->
	<div class="ui-widget">
		<form:form id="queryForm" action="${commonMapper.rootPath}/yuma/chart/query.do" method="post">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageData.pageSize }"/> 
			<input type="hidden" id="pageNumber" name="pageNumber" value="${pageData.pageNumber }"/>
			<input type="hidden" id="chartConfig" name="chartConfig" value="${chartConfig }"/>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>商品：</td>
					<td>
						<select id="item_id" onchange="initItemModelSel('item_model_id',this.value)"></select> 
						<select id="item_model_id"></select>
					</td>
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
				</tr>
			</table>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>查询类型：</td>
					<td>
						<select id="queryType" >
							<option value="1" selected="selected">订单数量</option>
							<option value="2">订单金额</option>
							<option value="3">销货数量</option>
						</select> 
					</td>
					<td>
						<select id="query1" >
							<option value="0">---请选择---</option>
							<option value="1">按日</option>
							<option value="1">按周</option>
							<option value="1">按周一到周日</option>
							<option value="1">按月</option>
							<option value="1">按年</option>
						</select> 
					</td>
					<td>
						<select id="query2" >
							<option value="0">---请选择---</option>
							<option value="1">按日期(每日)</option>
							<option value="1">按日期(每周)</option>
							<option value="1">按日期(每月)</option>
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
	<div class="chartDiv" style="background-color: white;width: 90%;">
		<canvas id="canvas"></canvas>
		<br>
	</div>
	<!-- --------------------弹出页面-------------------- -->
	<div id="divDialog"></div>
</body>
</html>
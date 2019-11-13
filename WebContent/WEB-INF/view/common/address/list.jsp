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
			$("#divDialog").load('${commonMapper.rootPath}/common/address/new.do',null,function(){
				$("#provinceForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({ 'width': 400,'height': 400,'title': '信息新增',
					'buttons':{
						'保存':function(){
							if($(this).find('#provinceForm').validationEngine("validate")){
								$(this).find('#provinceForm').ajaxSubmit({ 
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
						$(this).find('#provinceForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		//修改按钮
		$(".btnModify").click(function(){
			$("#divDialog").load('${commonMapper.rootPath}/common/address/modify/'+$(this).attr('rel')+'.do',null,function(){
				$("#provinceForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({title:'信息修改',resizable: false,height:400,width:400,
					'buttons':{
						'保存':function(){
							if($(this).find('#provinceForm').validationEngine("validate")){
								$(this).find('#provinceForm').ajaxSubmit({ 
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
						$(this).find('#provinceForm').validationEngine("hideAll");
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
					$.post("${commonMapper.rootPath}/common/address/delete.do", ids.substring(1), function(data) {
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
					$.post('${commonMapper.rootPath}/common/address/delete.do',tmp.substring(1),function(data){
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
		
		$(".beShadow").change(function(){
			var tmp = "&id="+$(this).attr('rel')+"&body_id="+$(this).val();
			jConfirm('确认将选择的省份设置为本省份的本体吗？', '确认操作', function(result){
				if(result == true){
					$.post('${commonMapper.rootPath}/common/address/beShadow.do',tmp.substring(1),function(data){
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
		
		$(".beBody").click(function(){
			var tmp = "&id="+$(this).attr('rel');
			jConfirm('确认设置为本体吗？', '确认操作', function(result){
				if(result == true){
					$.post('${commonMapper.rootPath}/common/address/beBody.do',tmp.substring(1),function(data){
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
		
		$(".showCity").click(function(){
			var tmp = "&id="+$(this).attr('rel');
			var cityName=$(this).attr('relName');
			jConfirm('需要展示【'+cityName+'】吗？', '确认操作', function(result){
				if(result == true){
					$.post('${commonMapper.rootPath}/common/address/showCity.do',tmp.substring(1),function(data){
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
		
		$(".hideCity").click(function(){
			var tmp = "&id="+$(this).attr('rel');
			var cityName=$(this).attr('relName');
			jConfirm('需要隐藏【'+cityName+'】吗？', '确认操作', function(result){
				if(result == true){
					$.post('${commonMapper.rootPath}/common/address/hideCity.do',tmp.substring(1),function(data){
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
		
		$("#name").val("${name}");
		$("#bodyType").val("${bodyType}");
		
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
		<span>微店管理->地区管理</span>
		<!-- <a href="#" class="add" id="addButton">新增</a>
		<a href="#" class="remove" id="deleteButton">删除</a> -->
	</div>
	<!-- --------------------查询表单-------------------- -->
	<div class="ui-widget">
		<form:form id="queryForm" action="${commonMapper.rootPath}/common/address/query.do" method="post">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageData.pageSize }"/> 
			<input type="hidden" id="pageNumber" name="pageNumber" value="${pageData.pageNumber }"/>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						省份:
					</td>
					<td>
						<input type="text" id="name" name="name" value="${name}" style="width:100px;" />
					</td>
					<td>
						展示类型:
					</td>
					<td>
						<select id="bodyType" name="bodyType" value="${bodyType}">
							<option value="">--选择类型--</option>
							<option value="0">分身</option>
							<option value="1">本体</option>
							<option value="11">有分身的本体</option>
							<option value="12">无分身的本体</option>
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
				<th width="10%">省份</th>
				<th width="10%">城市</th>
				<th width="20%">本体</th>
			</tr>
			<c:choose>
				<c:when
					test="${pageData.data != null && fn:length(pageData.data) > 0}">
					<c:forEach items="${pageData.data }" var="province"
						varStatus="vs">
						<tr ${vs.index % 2 == 0 ? "class='odd'" : "class='even'"}>
							<td><input type="checkbox" class="selOne"
								value="${province.id}"></td>
							<td>${province.name}</td>
							<td><c:forEach
									items="${province.citys }"
									var="city" varStatus="vs2">
									<c:if test="${vs2.index !=0 }">
										<br />
									</c:if>
									${city.name }
									<c:if test="${city.displayStatus ==0 }">
										(<a href="javascript:void(0)" rel="${city.id}" relName="${city.name }" class="hideCity"><font color="green">已显示</font></a>)
									</c:if>
									<c:if test="${city.displayStatus ==1 }">
										(<a href="javascript:void(0)" rel="${city.id}" relName="${city.name }" class="showCity"><font color="red">已隐藏</font></a>)
									</c:if>
								</c:forEach></td>
							<td><c:if test="${ empty province.body }">
									<c:if test="${ fn:length(province.shadows) == 0 }">
										<select rel="${province.id}" class="beShadow">
											<option>寻找本体</option>
											<c:forEach
												items="${bodys }"
												var="body" varStatus="vs3">
												<c:if test="${body.id !=province.id }">
													<option value="${body.id }">${body.name }</option>
												</c:if>
											</c:forEach>
										</select>
									</c:if>
									<c:if test="${ fn:length(province.shadows) != 0 }">
										(共有${ fn:length(province.shadows)}个分身)
										<c:forEach items="${province.shadows }"
												var="shadow" varStatus="vs3">
												<br/>${shadow.name }
										</c:forEach>
									</c:if>
								</c:if> <c:if test="${ ! empty province.body }">该省份是【${ province.body.name }】的分身(<a
										href="javascript:void(0)" rel="${province.id}"
										class="beBody">成为本体</a>)</c:if></td>
						</tr>
					</c:forEach>

					<!-- 分页 -->
					<tr class="bottomCls">
						<td colspan="20">
							<div id="pager" pageSize="${pageData.pageSize}"
								pageNumber="${pageData.pageNumber}" total="${pageData.total}"></div>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr class="bottomCls">
						<td colspan="20" style="text-align: center">
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
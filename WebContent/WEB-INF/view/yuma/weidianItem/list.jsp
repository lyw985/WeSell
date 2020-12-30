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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/util/yuma.js"></script>
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
			$("#divDialog").load('/yuma/weidianItem/new.do',null,function(){
				$("#yumaWeidianItemForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({ 'width': 500,'height': 300,'title': '信息新增',
					'buttons':{
						'保存':function(){
							if($(this).find('#yumaWeidianItemForm').validationEngine("validate")){
								$(this).find('#yumaWeidianItemForm').ajaxSubmit({ 
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
						$(this).find('#yumaWeidianItemForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		//修改按钮
		$(".btnModify").click(function(){
			$("#divDialog").load('/yuma/weidianItem/modify/'+$(this).attr('rel')+'.do',null,function(){
				$("#yumaWeidianItemForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({title:'信息修改',resizable: false,height:300,width:500,
					'buttons':{
						'保存':function(){
							if($(this).find('#yumaWeidianItemForm').validationEngine("validate")){
								$(this).find('#yumaWeidianItemForm').ajaxSubmit({ 
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
						$(this).find('#yumaWeidianItemForm').validationEngine("hideAll");
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
					$.post("${pageContext.request.contextPath}/yuma/weidianItem/delete.do", ids.substring(1), function(data) {
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
					$.post('/yuma/weidianItem/delete.do',tmp.substring(1),function(data){
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
			jConfirm('确认将选择的商品设置为本商品的本体吗？', '确认操作', function(result){
				if(result == true){
					$.post('/yuma/weidianItem/beShadow.do',tmp.substring(1),function(data){
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
					$.post('/yuma/weidianItem/beBody.do',tmp.substring(1),function(data){
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
		
		//增加关系按钮
		$(".addMapping").click(function(){
			$("#divDialog").load('/yuma/weidianItem/addMapping/'+$(this).attr('rel')+'.do',null,function(){
				$("#yumaWeidianItemModelMappingForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({title:'增加商品映射关系',resizable: false,height:300,width:500,
					'buttons':{
						'保存':function(){
							if($(this).find('#yumaWeidianItemModelMappingForm').validationEngine("validate")){
								$(this).find('#yumaWeidianItemModelMappingForm').ajaxSubmit({ 
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
						$(this).find('#yumaWeidianItemModelMappingForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		//修改关系按钮
		$(".modifyMapping").click(function(){
			var tmp = "&id="+$(this).attr('rel');
			$("#divDialog").load('/yuma/weidianItem/modifyMapping/'+$(this).attr('rel')+'.do',null,function(){
				$("#yumaWeidianItemModelMappingForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({title:'修改商品映射关系',resizable: false,height:300,width:500,
					'buttons':{
						'删除':function(){
							jConfirm('确认将这个关系删除吗？', '确认操作', function(result){
								if(result == true){
									$.post('/yuma/weidianItem/deleteMapping.do',tmp.substring(1),function(data){
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
						},
						'保存':function(){
							if($(this).find('#yumaWeidianItemModelMappingForm').validationEngine("validate")){
								$(this).find('#yumaWeidianItemModelMappingForm').ajaxSubmit({ 
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
						$(this).find('#yumaWeidianItemModelMappingForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		$(".receiverList").click(function(){
			window.location.href="${pageContext.request.contextPath}/yuma/receiver/list.do?weidianItem_id="+$(this).attr('rel');
		});
		
		$(".orderListByUserId").click(function(){
			window.location.href="${pageContext.request.contextPath}/yuma/order/list.do?weidianItem_id="+$(this).attr('rel');
		});
		
		$("#bodyType").val("${bodyType}")
		$("#mappingType").val("${mappingType}")
		$("#mappingShowType").val("${mappingShowType}")
		$("#weidianItemName").val("${weidianItemName}")
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
		<span>鱼妈海鲜-->微店商品</span>
		<!-- <a href="#" class="add" id="addButton">新增</a>
		<a href="#" class="remove" id="deleteButton">删除</a> -->
	</div>
	<!-- --------------------查询表单-------------------- -->
	<div class="ui-widget">
		<form:form id="queryForm" action="${pageContext.request.contextPath}/yuma/weidianItem/query.do" method="post">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageData.pageSize }"/> 
			<input type="hidden" id="pageNumber" name="pageNumber" value="${pageData.pageNumber }"/>
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>商品名称：</td>
					<td>
						<input type="text" id="weidianItemName" name="weidianItemName" value="${weidianItemName}" style="width:150px;" />
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
						显示映射:
					</td>
					<td>
						<select id="mappingShowType" name="mappingShowType" value="${mappingShowType}">
							<option value="">--选择显示映射--</option>
							<option value="1">绿色(单映射)</option>
							<option value="2">橙色(多映射)</option>
							<option value="3">红色(未映射)</option>
						</select>
					</td>
					<td>
						映射关系:
					</td>
					<td>
						<select id="mappingType" name="mappingType" value="${mappingType}">
							<option value="">--是否完成映射关系--</option>
							<option value="0">未完成</option>
							<option value="1">已完成</option>
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
				<th width="20%">型号</th>
				<th width="20%">本体</th>
			</tr>
			<c:choose>
				<c:when
					test="${pageData.data != null && fn:length(pageData.data) > 0}">
					<c:forEach items="${pageData.data }" var="yumaWeidianItem"
						varStatus="vs">
						<tr ${vs.index % 2 == 0 ? "class='odd'" : "class='even'"}>
							<td><input type="checkbox" class="selOne"
								value="${yumaWeidianItem.id}"></td>
							<td>${yumaWeidianItem.name}</td>
							<td><c:forEach
									items="${yumaWeidianItem.yumaWeidianItemModels }"
									var="yumaWeidianItemModel" varStatus="vs2">
									<c:if test="${vs2.index !=0 }">
										<br />
									</c:if>
									<c:if test="${fn:length(yumaWeidianItemModel.yumaWeidianItemModelMappings)==0 }">
										<font color="red">${yumaWeidianItemModel.name } </font>
									</c:if>
									<c:if test="${fn:length(yumaWeidianItemModel.yumaWeidianItemModelMappings)==1 }">
										<font color="green">${yumaWeidianItemModel.name } </font>
									</c:if>
									<c:if test="${fn:length(yumaWeidianItemModel.yumaWeidianItemModelMappings)>1 }">
										<font color="orange">${yumaWeidianItemModel.name } </font>
									</c:if>
									(<c:forEach
									items="${yumaWeidianItemModel.yumaWeidianItemModelMappings }"
									var="yumaWeidianItemModelMapping" varStatus="vs3">
									<a
										href="javascript:void(0)" rel="${yumaWeidianItemModelMapping.id}"
										class="modifyMapping">【${yumaWeidianItemModelMapping.yumaItemModel.name} ${yumaWeidianItemModelMapping.count}${yumaWeidianItemModelMapping.yumaItemModel.yumaItem.typeStr}】</a>
								</c:forEach><a href="javascript:void(0)" class="addMapping" rel="${yumaWeidianItemModel.id}">新增</a>)
								</c:forEach></td>
							<td><c:if test="${ empty yumaWeidianItem.body }">
									<c:if test="${ fn:length(yumaWeidianItem.shadows) == 0 }">
										<select rel="${yumaWeidianItem.id}" class="beShadow" style="width: 150px;">
											<option>寻找本体</option>
											<c:forEach items="${pageData.data }" var="yumaWeidianItem2" varStatus="vs3">
												<c:if test="${ empty yumaWeidianItem2.body && yumaWeidianItem2.id!=yumaWeidianItem.id}">
													<option value="${yumaWeidianItem2.id }">${yumaWeidianItem2.name }</option>
												</c:if>
											</c:forEach>
											<!--<c:forEach
												items="${bodys }"
												var="body" varStatus="vs3">
												<c:if test="${body.id !=yumaWeidianItem.id }">
													<option value="${body.id }">${body.name }</option>
												</c:if>
											</c:forEach>-->
										</select>
									</c:if>
									<c:if test="${ fn:length(yumaWeidianItem.shadows) != 0 }">
										(共有${ fn:length(yumaWeidianItem.shadows)}个分身)
										<c:forEach items="${yumaWeidianItem.shadows }"
												var="shadow" varStatus="vs3">
												<br/>${shadow.name }
										</c:forEach>
									</c:if>
								</c:if> <c:if test="${ ! empty yumaWeidianItem.body }">该商品是【${ yumaWeidianItem.body.name }】的分身(<a
										href="javascript:void(0)" rel="${yumaWeidianItem.id}"
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
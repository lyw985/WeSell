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
			$("#divDialog").load('${commonMapper.rootPath}/jtys/communicate/new.do',null,function(){
				$("#jtysCommunicateForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({ 'width': 400,'height': 400,'title': '信息新增',
					'buttons':{
						'保存':function(){
							if($(this).find('#jtysCommunicateForm').validationEngine("validate")){
								$(this).find('#jtysCommunicateForm').ajaxSubmit({ 
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
						$(this).find('#jtysCommunicateForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		//修改按钮
		$(".btnModify").click(function(){
			$("#divDialog").load('${commonMapper.rootPath}/jtys/communicate/modify/'+$(this).attr('rel')+'.do',null,function(){
				$("#jtysCommunicateForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({title:'信息修改',resizable: false,height:400,width:400,
					'buttons':{
						'保存':function(){
							if($(this).find('#jtysCommunicateForm').validationEngine("validate")){
								$(this).find('#jtysCommunicateForm').ajaxSubmit({ 
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
						$(this).find('#jtysCommunicateForm').validationEngine("hideAll");
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
					$.post("${commonMapper.rootPath}/jtys/communicate/delete.do", ids.substring(1), function(data) {
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
					$.post('${commonMapper.rootPath}/jtys/communicate/delete.do',tmp.substring(1),function(data){
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
		
		$(".play").click(function(){
			$("#divDialog").load('${commonMapper.rootPath}/play.jsp?src='+$(this).attr('rel'),null,function(){
				$("#divDialog").dialog({title:'播放',resizable: false,height:120,width:400,
					'buttons':{
						'关闭':function(){
							$("#queryForm").submit();
						}
					},
					modal:true,
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		$("#backButton").click(function() {
			window.location.href="${commonMapper.rootPath}/jtys/communicate/list.do"
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
		<span>家庭医生-->问题详情</span>
		<a href="#" class="add" id="backButton">返回上级</a>
		<!-- 
		<a href="#" class="add" id="addButton">新增</a>
		<a href="#" class="remove" id="deleteButton">删除</a> 
		-->
	</div>
	<!-- --------------------查询表单-------------------- -->
	<div class="ui-widget">
		<form:form id="queryForm" action="${commonMapper.rootPath}/jtys/communicate/detail.do" method="post">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageData.pageSize }"/> 
			<input type="hidden" id="pageNumber" name="pageNumber" value="${pageData.pageNumber }"/>
			<input type="hidden" id="problemId" name="problemId" value="${problemId }"/>
			<!-- 
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>信息内容：</td>
					<td>
						<input type="text" id="searchName" name="searchName" value="${searchName}" style="width:150px;" />
					</td>
					<td>状态：</td>
					<td>
						<select id="status" name="status" value="${status}">
							<option value="">--选择审核状态--</option>
							<option value="0">未审核</option>
							<option value="1">通过审核</option>
							<option value="2">未通过审核</option>
						</select>
					</td>
					<td>
						<input type="submit" id="btnSearch" name="btnSearch" class="buttonStyle" value="查询" />
					</td>
				</tr>
			</table> -->
		</form:form>
	</div>
	<!-- --------------------信息记录列表-------------------- -->
	<div class="listDiv">
		<table class="tabCls" cellpadding="0" cellspacing="0" width="99%">
			<tr>
				<th width="3%"><input type="checkbox" class="selAll"></th>
				<th width="16%">用户</th>
				<th width="10%">医生</th>
				<th width="44%">内容</th>
				<th width="15%">创建时间</th>
				<th width="6%">类型</th>
				<th width="6%">状态</th>
			</tr>
			<c:choose>
				<c:when test="${pageData.data != null && fn:length(pageData.data) > 0}">
					<c:forEach items="${pageData.data }" var="jtysCommunicate" varStatus="vs">
						<tr ${vs.index % 2 == 0 ? "class='odd'" : "class='even'"} >
							<td><input type="checkbox" class="selOne" value="${jtysCommunicate.id}"></td>
							<td>${jtysCommunicate.user.openId }</td>
							<td>
								<c:if test="${!empty jtysCommunicate.doctor}">
									${jtysCommunicate.doctor.name }
								</c:if>
							</td>
							<td>
								<c:if test="${!empty jtysCommunicate.text}">
									<div style="text-align:left; width:500px; overflow:hidden; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; -icab-text-overflow:ellipsis; -khtml-text-overflow:ellipsis; -moz-text-overflow:ellipsis; -webkit-text-overflow:ellipsis; " title ="${ jtysCommunicate.text }">${ jtysCommunicate.text }</div>
								</c:if>
								<c:if test="${!empty jtysCommunicate.imageUrl}">
									<img src="${jtysCommunicate.imageUrl }" height="50px" title="点击查看大图" onclick="window.open('${jtysCommunicate.imageUrl }')"/>
								</c:if>
								<c:if test="${!empty jtysCommunicate.audioUrl}">
									<a href="javascript:void(0)" rel="${jtysCommunicate.audioUrl}" class="play">播放试听</a>
								</c:if>
							</td>
							<td><fmt:formatDate value="${jtysCommunicate.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
							<td>
								<c:if test="${jtysCommunicate.type==0}"><font color="blue">${jtysCommunicate.typeStr}</font></c:if>
								<c:if test="${jtysCommunicate.type==1}"><font color="green">${jtysCommunicate.typeStr}</font></c:if>
								<c:if test="${jtysCommunicate.type==2}"><font color="orange">${jtysCommunicate.typeStr}</font></c:if>
							</td>
							<td>
								<c:if test="${jtysCommunicate.status==0}"><font color="blue">${jtysCommunicate.statusStr}</font></c:if>
								<c:if test="${jtysCommunicate.status==1}"><font color="green">${jtysCommunicate.statusStr}</font></c:if>
								<c:if test="${jtysCommunicate.status==2}"><font color="orange">${jtysCommunicate.statusStr}</font></c:if>
								<c:if test="${jtysCommunicate.status==3}"><font color="cyan">${jtysCommunicate.statusStr}</font></c:if>
								<c:if test="${jtysCommunicate.status==4}"><font color="red">${jtysCommunicate.statusStr}</font></c:if>
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
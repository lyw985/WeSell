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
			$("#divDialog").load('/blsh/content/new.do',null,function(){
				$("#blshContentForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({ 'width': 400,'height': 400,'title': '信息新增',
					'buttons':{
						'保存':function(){
							if($(this).find('#blshContentForm').validationEngine("validate")){
								$(this).find('#blshContentForm').ajaxSubmit({ 
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
						$(this).find('#blshContentForm').validationEngine("hideAll");
					},
					close:function(event, ui) {
						$("#queryForm").submit();
					}
				});
			});
		});
		
		//修改按钮
		$(".btnModify").click(function(){
			$("#divDialog").load('/blsh/content/modify/'+$(this).attr('rel')+'.do',null,function(){
				$("#blshContentForm").validationEngine({ promptPosition : "topRight" });
				$("#divDialog").dialog({title:'信息修改',resizable: false,height:400,width:400,
					'buttons':{
						'保存':function(){
							if($(this).find('#blshContentForm').validationEngine("validate")){
								$(this).find('#blshContentForm').ajaxSubmit({ 
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
						$(this).find('#blshContentForm').validationEngine("hideAll");
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
					$.post("${pageContext.request.contextPath}/blsh/content/delete.do", ids.substring(1), function(data) {
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
					$.post('/blsh/content/delete.do',tmp.substring(1),function(data){
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
					$.post('/blsh/content/pass.do',tmp.substring(1),function(data){
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
					$.post('/blsh/content/refuse.do',tmp.substring(1),function(data){
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
		$("#type").val("${type}");
		//为了区别info.jsp内的msgType进行改动
		$("#msgType_p").val("${msgType}");
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
		<span>便利生活-->内容采编</span>
		<a href="#" class="add" id="addButton">新增</a>
		<a href="#" class="remove" id="deleteButton">删除</a>
	</div>
	<!-- --------------------查询表单-------------------- -->
	<div class="ui-widget">
		<form:form id="queryForm" action="${pageContext.request.contextPath}/blsh/content/query.do" method="post">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageData.pageSize }"/> 
			<input type="hidden" id="pageNumber" name="pageNumber" value="${pageData.pageNumber }"/>
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
					<td>消息类型：</td>
					<td>
						<select id="msgType_p" name="msgType" value="${msgType}">
							<option value="">--选择消息类型--</option>
							<option value="1">文字消息</option>
							<option value="2">图文消息</option>
						</select>
					</td>
						<td>内容类型：</td>
					<td>
						<select id="type" name="type" value="${type}">
							<option value="">--选择消息类型--</option>
							<c:forEach items="${blshContentTypes }" var="item">
								<option value="${item.type}">${item.tip }</option>
							</c:forEach>
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
				<th width="3%"><input type="checkbox" class="selAll"></th>
				<th width="50%">信息内容</th>
				<th width="8%">创建时间</th>
				<th width="8%">采编人员</th>
				<th width="8%">类型</th>
				<th width="8%">状态</th>
				<th width="15%">操作</th>
			</tr>
			<c:choose>
				<c:when test="${pageData.data != null && fn:length(pageData.data) > 0}">
					<c:forEach items="${pageData.data }" var="blshContent" varStatus="vs">
						<tr ${vs.index % 2 == 0 ? "class='odd'" : "class='even'"} >
							<td><input type="checkbox" class="selOne" value="${blshContent.id}"></td>
							<td style="text-align: left;">
								<c:choose>
									<c:when test="${empty blshContent.content}">
										<img src="${blshContent.picUrl }" height="50px" title="点击查看大图" onclick="window.open('${blshContent.picUrl }')"/>
										<a href="${blshContent.url }" target="_blank" style="font-size: 18px;" title="点击查看原文">${blshContent.title }</a>
									</c:when>
									<c:otherwise>
										<div style="width:500px; overflow:hidden; white-space:nowrap; text-overflow:ellipsis; -o-text-overflow:ellipsis; -icab-text-overflow:ellipsis; -khtml-text-overflow:ellipsis; -moz-text-overflow:ellipsis; -webkit-text-overflow:ellipsis; " title ="${blshContent.content}">${blshContent.content}</div>
									</c:otherwise>
								</c:choose>
							</td>
							<td><fmt:formatDate value="${blshContent.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
							<td>${blshContent.user.name}</td>
							<td>${blshContent.typeStr}</td>
							<td>
								<c:if test="${blshContent.status==0}"><font color="blue">${blshContent.statusStr}</font></c:if>
								<c:if test="${blshContent.status==1}"><font color="green">${blshContent.statusStr}</font></c:if>
								<c:if test="${blshContent.status==2}"><font color="red">${blshContent.statusStr}</font></c:if>
							</td>
							<td>
								<c:if test="${blshContent.status!=1}">
									<a href="javascript:void(0)" rel="${blshContent.id}" class="passVersion">通过</a>
								</c:if>
								<c:if test="${blshContent.status!=2}">
									<a href="javascript:void(0)" rel="${blshContent.id}" class="refuseVersion">不通过</a>
								</c:if>
								<a href="javascript:void(0)" rel="${blshContent.id}" class="btnModify">修改</a>
								<a href="javascript:void(0)" rel="${blshContent.id}" class="singleDelete">删除</a>
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
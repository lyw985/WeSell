<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>角色管理</title>
<link href="${pageContext.request.contextPath}/css/cupertino/jquery-ui-1.8.15.custom.css" rel="stylesheet" type="text/css" media="screen" />
<link href="${pageContext.request.contextPath}/css/hodanet.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/alert/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
select{
	width: 155px;
}
input[type='text']{
	width: 150px;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hodanet.js"></script>
<script type="text/javascript">
$.ajaxSetup({cache:false});
$(function(){
	$('.roleTree').jstree({
		"ui":{"select_limit" : 1},
		"json_data":{
			"ajax":{
				"url":"${pageContext.request.contextPath}/role/getRoleTree.do",
				"data":function(node){//该函数用来获取提交参数
					if(node!='-1'){
						return {'moduleId':node.attr('id')};
					}else{
						return {'moduleId':''};
					}
				}
				
			}
			,"after_json_load":function(tree,node){
				if(node != "-1"){
					var li = $("li:first",node);
					if(li.attr("checkbox") == "true"){
						tree.select_node(li);
					}
				}
			}
		}
		,"sort":function(a,b){
			//返回1 交换位置；返回-1不交换
			var $a = $(a),$b=$(b);
			var ca = $a.attr("ordering"),cb = $b.attr("ordering");
			if(ca == null || cb == null) return -1;
			var a1 = parseInt(ca,10);
			var b1 = parseInt(cb,10);
			return a1 > b1 ? 1 : -1;
		}
		,"search":{
			"ajax":{
				"url":"${pageContext.request.contextPath}/role/search.do",
				"data":function(str){
					return {"name":str};
				}
			}
			//,show_only_matches:true
		},
		"themes":{"theme":"default",dots:false,icons:true},
		"plugins":["themes", "json_data", "ui", "sort", "search"]
	}).bind('select_node.jstree',function(event, data){
		var n = data.rslt.obj;
		if(n.find("a:first>ins.role").length<=0){
			data.inst.deselect_node(n);
			return;
		}
		$("#context").load("${pageContext.request.contextPath}/role/show.do?id=" + n.attr('id'));
	});
	
	//新建
	$(".add").click(function(){
		$('.roleTree').jstree('deselect_all');
		$("#context").load("${pageContext.request.contextPath}/role/show.do");
	});
	//保存
	$(".save").click(function(){
		var name = $("#name","#context").val();
		if(name.length <= 0){
			jError('请输入角色名称!',"错误");
			return
		}
		
		$("#context").find('form').ajaxSubmit({
			dataType:'json',
			success:function(data, statusText, xhr, $form){
				if(data.flag == true){
					jAlert(data.msg,'成功',function(){
						$(".roleTree").jstree('refresh');
					});
				}else{
					jAlert(data.msg,'失败');
				}
			}
		});
	});
	//删除
	$(".remove").click(function(){
		var selNode = $(".roleTree").jstree('get_selected');
		if(selNode.length <= 0){
			jError('请选择要删除的角色!','错误');
			return;
		}
		var nodeText = $(".roleTree").jstree('get_text',selNode.eq(0));
		jConfirm('确认删除['+nodeText+']角色','删除确认',function(s){
			if(s){
				$.post('/role/delete.do',{roleId:selNode.eq(0).attr('id')},function(json){
					if(json.flag == true){
						jAlert(json.msg,'消息',function(){
							$(".roleTree").jstree('refresh');
							$("#context").empty();
						});
					}else{
						jError('删除失败!','错误');
					}
				});
			}
		});
	});

	//搜索按钮
	$("#btnSearch").button().click(function(){
		if($("#searchValue").val().length > 0){
			$(".roleTree").jstree("close_all");
			$(".roleTree").jstree("deselect_all");
			$("#context").empty();
			$(".roleTree").jstree("search",$("#searchValue").val());
		}else{
			$(".roleTree").jstree("clear_search");
		}
	});
});
</script>
</head>
<body>
<div class="handle">
	<span>角色管理</span>
	<a href="#" class="add">新建</a>
	<a href="#" class="save">保存</a>
	<a href="#" class="remove">删除</a>
</div>
<div style="padding-right: 10px;padding-left: 10px; font-size: 12px;">
	<table style="border-collapse: ;" border="1" bordercolor="#92b2d3" width="100%" cellpadding="10px" cellspacing="10px;">
		<tr>
			<td width="200px" style="vertical-align: top;background-color: #fff;">
				<div>
					名称:<input id="searchValue" style="width: 100px;"><input type="button" id="btnSearch" value="查找">
				</div>
				<div style="border-bottom: 1px solid #92b2d3; margin-bottom: 10px;">角色结构树</div>
				<div class="roleTree" style="float: left;">
				</div>
			</td>
			<td style="vertical-align: top;background-color: #fff;padding-top: 10px;">
				<div style="margin-top: 30px;" id="context">
					
				</div>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
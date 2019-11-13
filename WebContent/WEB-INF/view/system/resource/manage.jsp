<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url var="root" value="/"></c:url>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>资源管理</title>
<link href="${commonMapper.rootPath}/css/cupertino/jquery-ui-1.8.15.custom.css" rel="stylesheet" type="text/css" />
<link href="${commonMapper.rootPath}/css/hodanet.css" rel="stylesheet" type="text/css" />
<link href="${commonMapper.rootPath}/css/alert/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" /> 
<style type="text/css">
select{
	width: 150px;
}
input[type='text']{
	width: 145px;
}
textarea{
	width: 200px;
	height: 50px;
	font-size: 12px;
}
</style>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.alerts.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/hodanet.js"></script>
<script type="text/javascript">
$.ajaxSetup({cache:false});
/** 刷新父节点 */
function funRefreshParent(){
	var selNode = $(".resourceTree").jstree("get_selected");
	if(selNode.length<=0) return;
	var parent = selNode.parents("li:first");
	$(".resourceTree").jstree("refresh",parent);
	//$(".resourceTree").jstree("select_node",parent);
}
/* 交换排序 */
function funSwapOrdering(type,one,two){
	$.post("${commonMapper.rootPath}/resource/swap.do",{'type':type,'one':one,'two':two},function(json){
		funRefreshParent();
	},'json');
}
/* 将下拉框替换为文本框个和隐藏域 */
function funChangeSelect(sel){
	if(sel.length<=0)return;
	var p = sel.parent("td");
	p.append("<input type='hidden'>");
	p.append("<input type='text'>");
	p.find("input[type='hidden']").val(sel.val()).attr('name',sel.attr('name')).attr('id',sel.attr('id'));
	p.find("input[type='text']").val(sel.find("option:selected").text()).attr("readonly",true);
	sel.remove();
}
/* 打开选择菜单弹出框 */
function funChooseMenuTree(context){
	var module = $(context).find("#module").val();
	var parentInput = $(context).find("#parentId");	
	
	var text = $(context).find("#parentName");
	var dialog = $(".chooseMenu").empty();
	var tree = $("<div class='chooseMenuTree'></div>");
	dialog.append(tree);
	tree.jstree({
		"plugins" : [ "themes", "json_data", "ui", "sort"]
		,"themes":{"theme":"default",dots:false,icons:true}
		,"ui":{"select_limit" : 1}
		,"json_data":{
			"ajax":{
				"url":"${commonMapper.rootPath}/resource/menuResource/"+module+".do"
			}
		},"sort":function(a,b){
			if($(a).attr("ordering") == null || $(b).attr("ordering") == null) return 1;
			
			var a1 = parseInt($(a).attr("ordering"),10);
			var b1 = parseInt($(b).attr("ordering"),10);
			return a1 > b1 ? 1 : -1;
		}
	});
	dialog.dialog({title:'选择父菜单',width:300,height:300,
		buttons:{
			"确定":function(){
				var selNode = tree.jstree("get_selected");
				var txt = tree.jstree("get_text",selNode);
				var id = selNode.attr("id");
				if(id == null || id == '' || id == 'undefined'){
					$(parentInput).val("");
					$(text).val("");
				}else{
					$(parentInput).val(id);
					$(text).val(txt);
				}
				dialog.dialog("close");
			},
			"取消":function(){dialog.dialog("close");}
		}
	});
}
/* 加载详细页面 */
function funLoadInfoPage(url,callback){
	$(".dialog").load(url,null,function(){
		$(".dialog").find(".type").change(function(){
			var u = "${commonMapper.rootPath}/resource/"+$(this).val()+"/add.do";
			$(".dialog").dialog('option','title',"新建"+$("option:selected",this).text());
			funLoadInfoPage(u);
		});
		var type = $(".dialog").find(".type").val();
		if(type == 'menu' || type == 'button'){
			$(".dialog").find("img").click(function(){
				funChooseMenuTree($(".dialog"));
			});
		}
		//执行回调
		if($.isFunction(callback)){
			callback.call();
		}
	});
}
/* 使用AJAX form提交表单 */
function funAjaxSubmit(context,success){
	var type = $(context).find('.type').val();
	if(type=='module'){
		var value = $("#name",context).val();
		if(value.length <=0){
			jError('请输入系统名称!','错误');
			return false;
		}
		value = $("#code",context).val();
		if(value.length <=0){
			jError('请输入系统编码!','错误');
			return false;
		}
		var checkUser = true;
		var mid = $("#id",context).val();
		$.ajax({type:'post',url:'${commonMapper.rootPath}/resource/module/check.do',data:"id="+mid+"&code="+value,dataType:'json',cache:false,async:false,success:function(json){
			checkUser = json.flag;
		}});
		if(checkUser == false){
			jError("系统编码[<b>"+value+"</b>]已经存在，请重新输入!","错误");
			return false;
		}
	}else if(type=='menu'){
		var value = $("#name",context).val();
		if(value.length <=0){
			jError('请输入菜单名称!','错误');
			return false;
		}
		
	}

	$(context).find('form').ajaxSubmit({
		dataType:'json',
		success:function(data, statusText, xhr, $form){
			if(data.flag == true){
				jAlert(data.msg,'成功',function(){
					$(".dialog").dialog('close');
					//执行回调
					if($.isFunction(success)){
						success.call();
					}
				});
			}else{
				jAlert(data.msg,'失败');
			}
		}
	});
}
//初始化
$(function(){
	//定义资源树
	$(".resourceTree").jstree({
		"ui":{"select_limit" : 1},
		"json_data":{
			"ajax":{
				"url":"${commonMapper.rootPath}/resource/childResource.do",
				"data":function(node){
					if(node!='-1'){
						return {'moduleId':node.attr('moduleId'),'t':node.attr('t'),'id':node.attr('id')};
					}else{
						return {'moduleId':'','t':'','id':''};
					}
				}
			}
		},"search":{
			"ajax":{
				"url":"${commonMapper.rootPath}/resource/search.do",
				"data":function(str){
					return {"name":str};
				}
			}
			//,show_only_matches:true
		},"sort":function(a,b){
			if($(a).attr("ordering") == null || $(b).attr("ordering") == null) return 1;
			
			var a1 = parseInt($(a).attr("ordering"),10);
			var b1 = parseInt($(b).attr("ordering"),10);
			return a1 > b1 ? 1 : -1;
		},
		"themes":{"theme":"default",dots:false,icons:true},
		"plugins" : [ "themes", "json_data", "ui", "search", "sort"]
	}).bind("select_node.jstree",function(event,data){ //绑定节点选中事件
		var o = data.rslt.obj;
		var effect = 'blind',speed = 100;
		//隐藏窗体，加载内容后再打开
		$("#context").hide(effect,null,speed,function(){
			var u = "";
			switch (o.attr('t')){
				case 's':{
					u = "module";
					break;
				}
				case 'm':{
					u = "menu";
					break;
				}
			}
			if(u.length>0){
				u = "${commonMapper.rootPath}/resource/" + u + "/modify/" + o.attr('id') + ".do";
				$("#context").load(u, null, function(){
					$("#context").find(".type").attr('disabled',true);
					funChangeSelect($("#context").find(".module"));
					if(o.attr('t')=="m" || o.attr('t')=="b"){//绑定选择父菜单事件
						$("#context").find("img").click(function(){
							funChooseMenuTree($("#context"));
						});
					}
					$("#context").show(effect,null,speed);
				});
			}
		});
	});
	//新建按钮事件
	$(".add").click(function(){
		var url = "${commonMapper.rootPath}/resource/module/add.do";
		var title = "子系统";
		var select = $("#context").find(".type");
		if(select.length>0){
			//根据当前打开的资源类型选择显示内容
			url = "${commonMapper.rootPath}/resource/"+select.val()+"/add.do";
			title = $("option:selected",select).text();
		}
		//加载页面
		funLoadInfoPage(url,function(){
			$(".dialog").dialog({
				'title':"新建"+title,
				width:350,
				modal:true,
				buttons:{
					"保存":function(){
						//使用AJAX方式提交表单
						funAjaxSubmit($(this),function(){
							var d = $(".dialog");
							if(d.find(".type").val()=='module'){
								$(".resourceTree").jstree("refresh");
							} else if(d.find(".type").val()=='menu') {
								var mc = d.find(".module").val();
								var li = $(".resourceTree").find("li[t='s'][moduleId='"+mc+"']");
								$(".resourceTree").jstree("refresh",li);
							} else if(d.find(".type").val()=='button') {
								var mc = d.find(".module").val();
								var li = $(".resourceTree").find("li[t='s'][moduleId='"+mc+"']");
								$(".resourceTree").jstree("refresh",li);
							}
						});
					},
					"取消":function(){
						$(this).dialog('close');
					}
				}
			});
		});
	});
	//保存按钮
	$(".save").click(function(){
		if($("#context").find(".type").length<1){
			jAlert("请先编辑要修改的内容!","警告");
			return;
		}
		var text = "";
		switch ($("#context").find(".type").val()){
			case 'module':{
				text = "确认保存子系统内容？";
				break;
			}
			case 'menu':{
				text = "确认保存菜单内容？";
				break;
			}
		};
		var val = $("#context").find("#name").val();
		jConfirm(text,"确认",function(result){
			if(result){
				funAjaxSubmit($("#context"),function(){
					//修改节点文字
					$(".resourceTree").jstree("set_text",$(".resourceTree").jstree("get_selected"),val);
				});
			}
		});
	});
	//删除按钮事件
	$(".remove").click(function(){
		if($("#context").find(".type").length<1){
			jAlert("请先选择要删除的内容!","警告");
			return;
		}
		var text = "";var url = "";
		switch ($("#context").find(".type").val()){
			case 'module':{
				text = "确认删除子系统？";
				url = "module";
				break;
			}
			case 'menu':{
				text = "确认删除菜单？";
				url = "menu";
				break;
			}
		};
		jConfirm(text,"确认",function(result){
			if(result){
				url = "${commonMapper.rootPath}/resource/" + url + "/delete.do";
				var id = $("#context").find("#id").val();
				$.post(url,{"id":id},function(json){
					if(json.flag){
						jAlert(json.msg,'成功',function(){
							funRefreshParent();
							$("#context").empty();
						});
					}else{
						jAlert(json.msg,'失败');
					}
				},'json');
			}
		});
	});
	
	//搜索资源按钮事件
	$("#btnSearch").button().click(function(){
		if($("#searchValue").val().length > 0){
			$(".resourceTree").jstree("close_all");
			$(".resourceTree").jstree("search",$("#searchValue").val());
		}else{
			$(".resourceTree").jstree("clear_search");
		}
	});
	//上移 、 下移
	$("img.moveTop").click(function(){
		var sel = $(".resourceTree").jstree("get_selected");
		if(sel.length<=0)return;
		sel = sel.eq(sel.length-1);
		var prev = sel.prev("li");
		if(prev.length <= 0)return;
		funSwapOrdering(sel.attr("t"),sel.attr('id'),prev.attr('id'));
	});
	$("img.moveDown").click(function(){
		var sel = $(".resourceTree").jstree("get_selected");
		if(sel.length<=0)return;
		sel = sel.eq(sel.length-1);
		var next = sel.next("li");
		if(next.length <= 0)return;
		funSwapOrdering(sel.attr("t"),sel.attr('id'),next.attr('id'));
	});
});
</script>
</head>
<body>
<div class="handle">
	<span>资源管理</span>
	<a href="#" class="add">添加</a>
	<a href="#" class="save">保存</a>
	<a href="#" class="remove">删除</a>
</div>
<div style="padding-right: 10px;padding-left: 10px; font-size: 12px;">
	<table style="border-collapse: ;" border="1" bordercolor="#92b2d3"  width="100%" cellpadding="10px" cellspacing="10px;">
		<tr>
			<td width="300px" style="vertical-align: top;background-color: #fff;"><!-- 定义树 -->
				<div>
					名称:<input id="searchValue" style="width:100px;"><input type="button" id="btnSearch" value="查找">
				</div>
				<div style="border-bottom: 1px solid #92b2d3; margin-bottom: 10px;">资源结构树</div>
				<div class="resourceTree" style="float: left;">
					
				</div>
				<!-- END/定义树 -->
				<div style="border: 0px solid #92b2d3;float: right;padding: 0px;margin-top: 60px;">
					<img src="${commonMapper.rootPath}/images/system-up.gif" title="上移" class="moveTop" alt="上移" style="margin: 5px;cursor: pointer;"><br>
					<img src="${commonMapper.rootPath}/images/system-down.gif" title="下移" class="moveDown" alt="下移" style="margin: 5px;cursor: pointer;">
				</div>
			</td>
			<td style="vertical-align: top;background-color: #fff;padding-top: 10px;">
				<div style="margin-top: 30px;" id="context">
					
				</div>
			</td>
		</tr>
	</table>
</div>
<div class="dialog" style="display: none;"></div>
<div class="expDialog" style="display: none;"></div>
<div class="chooseMenu" style="display: none">
	<div class="chooseMenuTree"></div>
</div>
<iframe id="downFrame" style="display: none;"></iframe>
</body>
</html>
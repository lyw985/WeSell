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
<link href="${commonMapper.rootPath}/css/cupertino/jquery-ui-1.8.15.custom.css" rel="stylesheet" type="text/css" />
<link href="${commonMapper.rootPath}/css/hodanet.css" rel="stylesheet" type="text/css" />
<link href="${commonMapper.rootPath}/css/alert/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jstree/jquery.jstree.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.alerts.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/hodanet.js"></script>
<script type="text/javascript">
function funGetData(key){
	return $(".handle").data(key);
}
function funSetData(key,data){
	$(".handle").data(key,data);
}

function funAddPermission(node){
	var pid = node.attr('id');
	var db = funGetData('rolePower');
	var isDB = false;
	$.each(db,function(i,o){
		if(o == pid){
			isDB = true;
		}
	});
	if(isDB){
		var del = funGetData('delMenu');
		for(var i=0;i<del.length;i++){
			if(del[i]==pid){
				del.splice(i,1);
				funSetData('delMenu',del);
				break;
			}
		}
	}else{
		var add = funGetData('addMenu');
		add.push(pid);
		funSetData('addMenu',add);
	}
}
function funRemovePermission(node){
	var pid = node.attr('id');
	var db = funGetData('rolePower');
	var isDB = false;
	$.each(db,function(i,o){
		if(o == pid){
			isDB = true;
		}
	});
	if(isDB){
		var del = funGetData('delMenu');
		del.push(pid);
		funSetData('delMenu',del);
	}else{
		var add = funGetData('addMenu');
		for(var i=0;i<add.length;i++){
			if(add[i]==pid){
				add.splice(i,1);
				funSetData('addMenu',add);
				break;
			}
		}
	}
}
function funShowPermission(json,parent){
	var tree = $(".resourceTree");
	//去除节点选择事件
	tree.unbind("check_node.jstree");
	tree.unbind("uncheck_node.jstree");
	//遍历设置选中状态
	$.each(json,function(i,o){
		if($("#" + o, parent).length > 0){
			tree.jstree("check_node",$("#" + o, parent));
		}
	});
	//绑定节点选择事件
	tree.bind("check_node.jstree",function(event,data){
		var o = data.rslt.obj;
		var t = $(".resourceTree");
		o.children("ul").children("li").each(function(){
			if(!t.jstree("is_checked", $(this)))
				t.jstree("check_node", $(this));
		});
		funAddPermission(o);
	});
	tree.bind("uncheck_node.jstree",function(event,data){
		var o = data.rslt.obj;
		var t = $(".resourceTree");
		o.children("ul").children("li").each(function(){
			if(t.jstree("is_checked", $(this)))
				t.jstree("uncheck_node", $(this));
		});
		funRemovePermission(o);
	});
}
$(function(){
	
	$('.roleTree').jstree({
		"ui":{"select_limit" : 1},
		"json_data":{
			"ajax":{
				"url":"${commonMapper.rootPath}/role/getRoleTree.do",
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
					if(li.length > 0 && li.attr("checkbox") == "true"){
						tree.select_node(li);
					}
				}
			}
		}
		,"checkbox":{
			"two_state":true
		}
		,"sort":function(a,b){
			//返回1 交换位置；返回-1不交换
			var $a = $(a),$b=$(b);
			var ca = $a.attr("create"),cb = $b.attr("create");
			if(ca == null || cb == null) return -1;
			var a1 = parseInt(ca,10);
			var b1 = parseInt(cb,10);
			return a1 > b1 ? 1 : -1;
		}
		,"search":{
			"ajax":{
				"url":"${commonMapper.rootPath}/role/search.do",
				"data":function(str){
					return {"name":str};
				}
			}
			//,show_only_matches:true
		},
		"themes":{"theme":"default",dots:false,icons:true},
		"plugins":["themes", "json_data", "ui", "sort", "search"]
	}).bind('select_node.jstree',function(event,data){
		//清除选中状态
		$(".resourceTree").jstree("uncheck_all");
		funSetData('rolePower',[]);
		var n = data.rslt.obj;
		if(n.attr('checkbox') != 'true') {
			data.inst.deselect_node(n);
			return;
		}
		//查询用户权限
		$('.load').show();
		$.post("${commonMapper.rootPath}/permission/getRoleResource.do",{"roleId":n.attr("id")},function(json){
			$('.load').hide();
			funSetData('rolePower',json);
			funSetData('addMenu',[]);
			funSetData('delMenu',[]);
			funShowPermission(json,$(".resourceTree"));
		},'json');
	});
	
	//资源结构树
	$(".resourceTree").jstree({
		"plugins" : [ "themes", "json_data", "ui", "search", "checkbox", "sort"]
		,"themes":{"theme":"default",dots:false,icons:true}
		,"ui":{"select_limit" : 1}
		,"json_data":{
			"ajax":{
				"url":"${commonMapper.rootPath}/permission/getResourceTree.do",
				"data":function(node){
					if(node!='-1'){
						return {'moduleId':node.attr('moduleId'),'t':node.attr('t'),'id':node.attr('id')};
					}else{
						return {'moduleId':'','t':'','id':''};
					}
				}
			}
		},"checkbox":{
			"two_state":true
		},"sort":function(a,b){
			if($(a).attr("create") == null || $(b).attr("create") == null) return 1;
			
			var a1 = parseInt($(a).attr("create"),10);
			var b1 = parseInt($(b).attr("create"),10);
			return a1 > b1 ? 1 : -1;
		},"search":{
			"ajax":{
				"url":"${commonMapper.rootPath}/resource/search.do",
				"data":function(str){
					return {"name":str};
				}
			}
		}
	}).bind("select_node.jstree",function(event,data){ //点击文字，控制复选框
		var o = data.rslt.obj;
		if(o.attr('t') == 's') return;
		var t = $(".resourceTree");
		if(t.jstree("is_checked", o)){
			t.jstree("uncheck_node", o);
		}else{
			t.jstree("check_node", o);
		}
	}).bind("after_open.jstree",function(event,data){//展开事件
		var o = data.rslt.obj;
		if(o.attr('t')=='s' && o.attr('showed')==null){
			var json = funGetData('rolePower');
			funShowPermission(json,o);
			o.attr('showed','showed');
		}
	});
	
	//搜索角色按钮
	$("#btnSearchRole").button().click(function(){
		if($("#searchRoleValue").val().length > 0){
			$(".roleTree").jstree("close_all");
			$(".roleTree").jstree("search",$("#searchRoleValue").val());
		}else{
			$(".roleTree").jstree("clear_search");
		}
	});
	
	//搜索资源按钮
	$("#btnSearchResource").button().click(function(){
		if($("#searchResourceValue").val().length > 0){
			$(".resourceTree").jstree("close_all");
			$(".resourceTree").jstree("search",$("#searchResourceValue").val());
		}else{
			$(".resourceTree").jstree("clear_search");
		}
	});
	
	//保存权限
	$(".add").click(function(){
		var roleNode = $(".roleTree").jstree("get_selected");

		var delMenu = funGetData('delMenu');
		var addMenu = funGetData('addMenu');
		if(delMenu.length + addMenu.length > 0){
			var params = "roleId="+roleNode.attr("id");
			$.each(delMenu,function(i,o){
				params += "&delMenu="+o;
			});
			$.each(addMenu,function(i,o){
				params += "&addMenu="+o;
			});
			//alert(params);
			$.post("${commonMapper.rootPath}/permission/saveRoleResource.do", params, function(json){
				jAlert(json.msg, "提示");
				$.post("${commonMapper.rootPath}/permission/getRoleResource.do",{"roleId":roleNode.attr("id")},function(json){
					$('.load').hide();
					funSetData('rolePower',json);
					funSetData('addMenu',[]);
					funSetData('delMenu',[]);
					funShowPermission(json,$(".resourceTree"));
				},'json');
			}, "json");
		} else {
			jAlert('未进行修改',"提示");return;
		}
	});
	
	funSetData('rolePower',[]);
	funSetData('addMenu',[]);
	funSetData('delMenu',[]);
});
</script>
</head>
<body>
<div class="handle">
	<span>角色资源管理</span>
	<a href="#" class="add">保存角色资源关系</a>
</div>
<div style="padding-right: 10px;padding-left: 10px;font-size: 12px;">
	<table style="border-collapse: ;" border="1" bordercolor="#92b2d3" width="100%" cellpadding="10px" cellspacing="10px;">
		<tr>
			<td width="200px" style="vertical-align: top;background-color: #fff;"><!-- 定义树 -->
				<div>
					名称:<input id="searchRoleValue" style="width: 100px;"><input type="button" id="btnSearchRole" value="查找">
				</div>
				<div style="border-bottom: 1px solid #92b2d3; margin-bottom: 10px;">角色结构树</div>
				<div class="roleTree">
					
				</div>
				<!-- END/定义树 -->
			</td>
			<td style="vertical-align: top;background-color: #fff;padding-top: 10px;">
				<div>
					名称:<input id="searchResourceValue" style="width: 100px;"><input type="button" id="btnSearchResource" value="查找">
					<span class="load" style="display: none;color: red;"><img src="${commonMapper.rootPath}/js/jstree/themes/default/throbber.gif">正在读取角色权限...</span>
				</div>
				<div style="border-bottom: 1px solid #92b2d3; margin-bottom: 10px;">资源结构树</div>
				<div class="resourceTree">
					
				</div>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>人员角色关系管理</title>
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

function funAddRole(roleId){
	var db = funGetData('userRole');
	var isDB = false;
	$.each(db,function(i,o){
		if(o == roleId){
			isDB = true;
		}
	});
	if(isDB){
		var del = funGetData('delRole');
		for(var i=0;i<del.length;i++){
			if(del[i]==roleId){
				del.splice(i,1);
				funSetData('delRole',del);
				break;
			}
		}
	}else{
		var add = funGetData('addRole');
		add.push(roleId);
		funSetData('addRole',add);
	}
}
function funRemoveRole(roleId){
	var db = funGetData('userRole');
	var isDB = false;
	$.each(db,function(i,o){
		if(o == roleId){
			isDB = true;
		}
	});
	if(isDB){
		var del = funGetData('delRole');
		del.push(roleId);
		funSetData('delRole',del);
	}else{
		var add = funGetData('addRole');
		for(var i=0;i<add.length;i++){
			if(add[i]==roleId){
				add.splice(i,1);
				funSetData('addRole',add);
				break;
			}
		}
	}
}
function funShowRoles(json,context){
	var tree = $(".roleTree");
	//去除节点选择事件
	tree.unbind("check_node.jstree");
	tree.unbind("uncheck_node.jstree");
	//遍历设置选中状态
	$.each(json,function(i,o){
		if($("#" + o, context).length > 0){
			tree.jstree("check_node",$("#" + o, context));
		}
	});
	tree.bind('check_node.jstree',function(event,data){
		var n = data.rslt.obj;
		/*var t = data.inst;
		n.children("ul").children("li").each(function(){
			if(!t.is_checked($(this)))
				t.check_node($(this));
		});*/
		funAddRole(n.attr('id'));
	});
	tree.bind('uncheck_node.jstree',function(event,data){
		var n = data.rslt.obj;
		/*var t = data.inst;
		n.children("ul").children("li").each(function(){
			if(t.is_checked($(this)))
				t.uncheck_node($(this));
		});*/
		funRemoveRole(n.attr('id'));
	});
}
$(function(){
	//人员结构树
	$(".userTree").jstree({
		"plugins" : [ "themes", "json_data", "ui", "search", "sort"]
		,"themes":{"theme":"default",dots:false,icons:true}
		,"ui":{"select_limit" : 1}//,"initially_select":["597e6ef4dd8f4ac5a06e038b7ee838d8"]}
		,"json_data":{
			"ajax":{
				"url":"${commonMapper.rootPath}/user/getChooseData.do",
				"data":function(node){//该函数用来获取提交参数
					if(node!='-1'){
						return {'pid':node.attr('id')};
					}else{
						return {'pid':''};
					}
				}
			},"after_json_load":function(tree,node){
				if(node != "-1"){
					var li = $("li:first",node);
					if(li.attr("t")=='department'){
						tree.open_node(li);
					}else if(li.attr("t")=='user'){
						tree.deselect_all();
						tree.select_node(li);
					}
				}
			}
		}
		,"sort":function(a,b){
			//返回1 交换位置；返回-1不交换
			var $a = $(a),$b=$(b);
			var ca = $a.attr("create"),cb = $b.attr("create");
			if(ca == null || cb == null) return -1;
			//部门在前，人员在后
			var ta = $a.attr('t'),tb = $b.attr('t');
			if(ta == 'department' && tb == 'user') return -1;
			if(ta == 'user' && tb == 'department') return 1;
			var a1 = parseInt(ca,10);
			var b1 = parseInt(cb,10);
			return a1 > b1 ? 1 : -1;
		}
		,"search":{
			"ajax":{
				"url":"${commonMapper.rootPath}/user/search.do",
				"data":function(str){
					return {"name":str};
				}
			}
		}
	}).bind("select_node.jstree",function(event,data){ //绑定节点选中事件
		//清除选中状态
		$(".roleTree").jstree("uncheck_all");
		funSetData('userRole',[]);
		var o = data.rslt.obj;
		if(o.attr('t')!='user') {
			data.inst.deselect_node(o);
			return;
		}
		//查询用户角色
		$('.load').show();
		$.post("${commonMapper.rootPath}/permission/getUserRole.do",{"userId":o.attr("id")},function(json){
			$('.load').hide();
			funSetData('userRole',json);
			funSetData('addRole',[]);
			funSetData('delRole',[]);
			funShowRoles(json,$(".roleTree"));
		},'json');
	});
	
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
		"plugins":["themes", "json_data", "ui", "sort", "search", "checkbox"]
	}).bind('select_node.jstree',function(event,data){
		var n = data.rslt.obj;
		if(n.attr('checkbox') != 'true') {
			data.inst.deselect_node(n);
			return;
		}
		if(data.inst.is_checked(n)){
			data.inst.uncheck_node(n);
		}else{
			data.inst.check_node(n);
		}
	}).bind('after_open.jstree',function(event,data){
		var o = data.rslt.obj;
		if(o.attr('showed')==null){
			var json = funGetData('userRole');
			funShowRoles(json,o);
			o.attr('showed','showed');
		}
	});
	
	//保存权限
	$(".add").click(function(){
		var userNode = $(".userTree").jstree("get_selected");
		if(userNode.length <= 0 || userNode.attr("id")==null){
			jAlert('请先选择人员',"失败");return;
		}

		var del = funGetData('delRole');
		var add = funGetData('addRole');
		if(del.length + add.length > 0){
			var params = "userId="+userNode.attr("id");
			$.each(del,function(i,o){
				params += "&del="+o;
			});
			$.each(add,function(i,o){
				params += "&add="+o;
			});
		
			$.post("${commonMapper.rootPath}/permission/saveUserRole.do", params, function(json){
				jAlert(json.msg, "提示");
				$.post("${commonMapper.rootPath}/permission/getUserRole.do",{"userId":userNode.attr("id")},function(json){
					$('.load').hide();
					funSetData('userRole',json);
					funSetData('addRole',[]);
					funSetData('delRole',[]);
					funShowRoles(json,$(".roleTree"));
				},'json');
			}, "json");
		} else {
			jAlert('未进行修改',"提示");return;
		}
	});
	
	//搜索人员按钮
	$("#btnSearchUser").button().click(function(){
		if($("#searchUserValue").val().length > 0){
			$(".userTree").jstree("close_all");
			$(".userTree").jstree("search",$("#searchUserValue").val());
		}else{
			$(".userTree").jstree("clear_search");
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
	
	funSetData('userRole',[]);
	funSetData('addRole',[]);
	funSetData('delRole',[]);
});
</script>
</head>
<body>
<div class="handle">
	<span>人员角色管理</span>
	<a href="#" class="add">保存人员角色关系</a>
</div>
<div style="padding-right: 10px;padding-left: 10px;font-size: 12px;">
	<table style="border-collapse: ;" border="1" bordercolor="#92b2d3" width="100%" cellpadding="10px" cellspacing="10px;">
		<tr>
			<td width="200px" style="vertical-align: top;background-color: #fff;"><!-- 定义树 -->
				<div>
					名称:<input id="searchUserValue" style="width: 100px;"><input type="button" id="btnSearchUser" value="查找">
				</div>
				<div style="border-bottom: 1px solid #92b2d3; margin-bottom: 10px;">人员结构树</div>
				<div class="userTree">
					
				</div>
				<!-- END/定义树 -->
			</td>
			<td style="vertical-align: top;background-color: #fff;padding-top: 10px;">
				<div>
					名称:<input id="searchRoleValue" style="width: 100px;"><input type="button" id="btnSearchRole" value="查找">
					<span class="load" style="display: none;color: red;"><img src="${commonMapper.rootPath}/js/jstree/themes/default/throbber.gif">正在读取人员角色...</span>
				</div>
				<div style="border-bottom: 1px solid #92b2d3; margin-bottom: 10px;">角色结构树</div>
				<div class="roleTree">
					
				</div>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
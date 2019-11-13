<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url var="root" value="/"></c:url>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>组织结构管理</title>
<link href="${commonMapper.rootPath}/css/cupertino/jquery-ui-1.8.15.custom.css" rel="stylesheet" type="text/css" media="screen" />
<link href="${commonMapper.rootPath}/css/hodanet.css" rel="stylesheet" type="text/css" />
<link href="${commonMapper.rootPath}/css/alert/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" /> 
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
	var selNode = $(".departmentTree").jstree("get_selected");
	if(selNode.length<=0) return;
	var parent = selNode.parents("li:first");
	$(".departmentTree").jstree("refresh",parent);
}

/* 交换创建时间 */
function funSwapOrdering(one,two){
	$.post("${commonMapper.rootPath}/department/order.do",{'one':one,'two':two},function(json){
		funRefreshParent();
	},'json');
}

function funDialogCallBack(type,result){
	if(type == '1' && result!=null && result.length > 0){
		$("#context").find("#parentId").val(result[0].id);
		$("#context").find("#parentName").val(result[0].name);
	}
	var d = $(".dialog");
	if(d.length > 0){
		d.dialog('close');
	}
}

/*选择父部门*/
function funChooseParent(){
	var d = $(".dialog");
	if(d.length < 1){
		$('body').append("<iframe class='dialog' src='' frameborder='no' border='0' marginwidth='0' marginheight='0' allowtransparency='yes'></iframe>");
		d = $(".dialog");
	}
	d.attr('src','${commonMapper.rootPath}/department/choose.do?type=1&flag=1');
	d.dialog({autoOpen:true,position: 'top',title:'部门选择',resizable: false,height:370,width:260,modal:true});
}

function funOpenDetail(url){
	var effect = 'blind',speed = 300;
	//隐藏窗体，加载内容后再打开
	$("#context").hide();
	$("#context").load(url, null, function(){
		$("#context").find("img").click(function(){
			funChooseParent();
		});
		$("#context").show(effect,null,speed);
	});
}

/*提交表单*/
function funAjaxSubmit(){
	if($("#context").find('#id').val().length > 0 && $("#context").find('#parentId').val().length > 0){
		if($("#context").find('#id').val() == $("#context").find('#parentId').val()) {
			jAlert("不能选择自身作为其父部门！",'失败');
			return;
		};
		var self = $(".departmentTree").find("#"+$("#context").find('#id').val());
		if(self.length > 0 ){
			var newParent = self.find("#"+$("#context").find('#parentId').val());
			if(newParent.length > 0){
				jAlert("不能选择自身的子部门作为其父部门！",'失败');
				return;
			}
		}
	}
	$("#context").find('form').ajaxSubmit({
		dataType:'json',
		success:function(data, statusText, xhr, $form){
			if(data.flag == true){
				jAlert(data.msg,'成功',function(){
					$(".departmentTree").jstree('refresh');
				});
			}else{
				jAlert(data.msg,'失败');
			}
		}
	});
}

$(function(){
	//部门结构树
	$(".departmentTree").jstree({
		"ui":{"select_limit" : 1},
		"json_data":{
			"ajax":{
				"url":"${commonMapper.rootPath}/department/getChooseData.do",
				"data":function(node){//该函数用来获取提交参数
					if(node!='-1'){
						return {'pid':node.attr('id')};
					}else{
						return {'pid':''};
					}
				}
			}
			,"after_json_load":function(tree,node){
				if(node != "-1"){
					var li = $("li:first",node);
					if(li.length > 0){
						tree.open_node(li);
					}else{
						tree.deselect_all();
						tree.select_node(node);
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
				"url":"${commonMapper.rootPath}/department/search.do",
				"data":function(str){
					return {"name":str};
				}
			}
			//,show_only_matches:true
		},
		"themes":{"theme":"default",dots:false,icons:true},
		"plugins":["themes", "json_data", "ui", "sort", "search"]
	}).bind('select_node.jstree',function(event,data){
		//alert(data.rslt.obj.attr('id'));
		var o = data.rslt.obj;
		var url = "${commonMapper.rootPath}/department/modify/" + o.attr('id') + ".do";
		funOpenDetail(url);
	});
	
	//上移 、 下移
	$("img.moveTop").click(function(){
		var sel = $(".departmentTree").jstree("get_selected");
		if(sel.length<=0)return;
		sel = sel.eq(sel.length-1);
		var prev = sel.prev("li");
		if(prev.length <= 0)return;
		funSwapOrdering(sel.attr('id'),prev.attr('id'));
	});
	$("img.moveDown").click(function(){
		var sel = $(".departmentTree").jstree("get_selected");
		if(sel.length<=0)return;
		sel = sel.eq(sel.length-1);
		var next = sel.next("li");
		if(next.length <= 0)return;
		funSwapOrdering(sel.attr('id'),next.attr('id'));
	});

	//搜索部门按钮
	$("#btnSearch").button().click(function(){
		if($("#searchValue").val().length > 0){
			$(".departmentTree").jstree("close_all");
			$(".departmentTree").jstree("search",$("#searchValue").val());
		}else{
			$(".departmentTree").jstree("clear_search");
		}
	});
	
	//新建按钮
	$(".add").click(function(){
		$('.departmentTree').jstree('deselect_all');
		var url = "${commonMapper.rootPath}/department/new.do";
		funOpenDetail(url);
	});
	
	//保存按钮
	$(".save").click(function(){
		jConfirm("确认保存数据?","确认",function(result){
			if(result){
				funAjaxSubmit();
			}
		});
	});
	
	//删除按钮
	$(".remove").click(function(){
		var name = $("#context").find("#name").val();
		jConfirm("确认删除部门:["+name+"]?","确认",function(result){
			if(result){
				var url = "${commonMapper.rootPath}/department/delete.do";
				var id = $("#context").find("#id").val();
				$.post(url,{'id':id},function(json){
					if(json.flag){
						$("#context").hide();
						$(".departmentTree").jstree('refresh');
					}else{
						jAlert(json.msg);
					}
				},'json');
			}
		});
	});
	
});
</script>
</head>
<body>
<input id="btnCodes" type="hidden" value="${btnCodes }">
<div class="handle">
	<span>部门管理</span>
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
				<div style="border-bottom: 1px solid #92b2d3; margin-bottom: 10px;">组织结构树</div>
				<div class="departmentTree" style="float: left;">
				</div>
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
</body>
</html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>人员管理</title>
<link href="${pageContext.request.contextPath}/css/cupertino/jquery-ui-1.8.15.custom.css" rel="stylesheet" type="text/css" media="screen" />
<link href="${pageContext.request.contextPath}/css/hodanet.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/alert/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
input[type='password'],input[type='text']{
	width: 150px;
}
.detail td{
	
}
.required{
	color: red;
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

/** 刷新父节点 */
function funRefreshParent(){
	var selNode = $(".userTree").jstree("get_selected");
	if(selNode.length<=0) return;
	var parent = selNode.parents("li:first");
	$(".userTree").jstree("refresh",parent);
}

//交换排序，修改显示顺序
function funSwapOrdering(one,two){
	$.post("${pageContext.request.contextPath}/user/order.do",{'one':one,'two':two},function(json){
		funRefreshParent();
	},'json');
}

function funDialogCallBack(type,result){
	if(type == '1' && result!=null && result.length > 0){
		$("#context").find("#departmentId").val(result[0].id);
		$("#context").find("#departmentName").val(result[0].name);
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
	d.attr('src','/department/choose.do?type=1&flag=1');
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
	$("#context").find('form').ajaxSubmit({
		dataType:'json',
		success:function(data, statusText, xhr, $form){
			if(data.flag == true){
				jAlert(data.msg,'成功',function(){
					$(".userTree").jstree('refresh');
				});
			}else{
				jAlert(data.msg,'失败');
			}
		}
	});
}

/**验证表单*/
function checkForm(){
	var loginId = $("#context").find("#loginId").val();
	if(loginId.length <= 0){
		jError("请输入用户登录id!","错误");
		return false;
	}
	var name = $("#context").find("#name").val();
	if(name.length <= 0){
		jError("请输入名称!","错误");
		return false;
	}
	var checkUser = true;
	var id = $("#context").find("#id").val();
	$.ajax({type:'post',url:'/user/check.do',data:"loginId="+loginId+"&id="+id,dataType:'json',cache:false,async:false,success:function(json){
		checkUser = json.flag;
	}});
	
	if(checkUser == false){
		jError("用户登录id[<b>"+loginId+"</b>]已经存在，请重新输入!","错误");
		return false;
	}
	var pid = $("#context").find("#departmentId").val();
	if(pid.length <= 0){
		jError("请选择所属部门!","错误");
		return false;
	}
	return true;
}

$(function(){
	$(".userTree").jstree({
		"ui":{"select_limit" : 1},
		"json_data":{
			"ajax":{
				"url":"${pageContext.request.contextPath}/user/getChooseData.do",
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
			var ca = $a.attr("ordering"),cb = $b.attr("ordering");
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
				"url":"${pageContext.request.contextPath}/user/search.do",
				"data":function(str){
					return {"name":str};
				}
			}
			//,show_only_matches:true
		},
		"themes":{"theme":"default",dots:false,icons:true},
		"plugins":["themes", "json_data", "ui", "sort", "search"]
	}).bind('select_node.jstree',function(event,data){
		var n = data.rslt.obj;
		if(n.find("a:first>ins.user").length<=0){
			data.inst.deselect_node(n);
			return;
		}
		var url = "/user/modify/" + n.attr('id') + ".do";
		funOpenDetail(url);
	});

	//搜索按钮
	$("#btnSearch").button().click(function(){
		if($("#searchValue").val().length > 0){
			$(".userTree").jstree("close_all");
			$(".userTree").jstree("deselect_all");
			$("#context").hide();
			$(".userTree").jstree("search",$("#searchValue").val());
		}else{
			$(".userTree").jstree("clear_search");
		}
	});
	
	//上移 、 下移
	$("img.moveTop").click(function(){
		var sel = $(".userTree").jstree("get_selected");
		if(sel.length<=0)return;
		sel = sel.eq(sel.length-1);
		var prev = sel.prev("li");
		if(prev.length <= 0)return;
		funSwapOrdering(sel.attr('id'),prev.attr('id'));
	});
	$("img.moveDown").click(function(){
		var sel = $(".userTree").jstree("get_selected");
		if(sel.length<=0)return;
		sel = sel.eq(sel.length-1);
		var next = sel.next("li");
		if(next.length <= 0)return;
		funSwapOrdering(sel.attr('id'),next.attr('id'));
	});
	
	//新建按钮
	$(".add").click(function(){
		$('.userTree').jstree('deselect_all');
		var url = "/user/new.do";
		funOpenDetail(url);
	});
	
	//保存按钮
	$(".save").click(function(){
		if(!checkForm()) return;
		
		jConfirm("确认保存数据?","确认",function(result){
			if(result){
				funAjaxSubmit();
			}
		});
	});
	
	//删除按钮
	$(".remove").click(function(){
		var name = $("#context").find("#name").val();
		jConfirm("确认删除人员:["+name+"]?","确认",function(result){
			if(result){
				var url = "/user/delete.do";
				var id = $("#context").find("#id").val();
				$.post(url,{'id':id},function(json){
					if(json.flag){
						$("#context").hide();
						$(".userTree").jstree('refresh');
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
<div class="handle">
	<span>人员管理</span>
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
				<div style="border-bottom: 1px solid #92b2d3; margin-bottom: 10px;">人员结构树</div>
				<div class="userTree" style="float: left;">
				</div>
				<div style="border: 0px solid #92b2d3;float: right;padding: 0px;margin-top: 60px;">
					<img src="${pageContext.request.contextPath}/images/system-up.gif" title="上移" class="moveTop" alt="上移" style="margin: 5px;cursor: pointer;"><br>
					<img src="${pageContext.request.contextPath}/images/system-down.gif" title="下移" class="moveDown" alt="下移" style="margin: 5px;cursor: pointer;">
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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>选择人员</title>
<link href="${commonMapper.rootPath}/css/cupertino/jquery-ui-1.8.15.custom.css" rel="stylesheet" type="text/css" media="screen" />
<link href="${commonMapper.rootPath}/css/hodanet.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="${commonMapper.rootPath}/js/jstree/jquery.jstree.js"></script>
<script type="text/javascript">
$.ajaxSetup({cache:false});

$(function(){
	$(".userTree").jstree({
		"ui":{"select_limit" : 1},
		"json_data":{
			"ajax":{
				"url":"${commonMapper.rootPath}/user/getChooseData.do",
				"data":function(node){//该函数用来获取提交参数
					if(node!='-1'){
						return {'pid':node.attr('id')};
					}else{
						return {'pid':'${pid }${pidFlag }'};
					}
				}
			},
			'after_json_load':function(tree,node){
				if($("#flag").val()=='1'){
					tree.hide_checkboxes();
				}
				if(node != "-1"){
					var li = $("li:first",node);
					if(li.length > 0 && li.attr("t")=='department'){
						tree.open_node(li);
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
			//部门在前，人员在后
			var ta = $a.attr('t'),tb = $b.attr('t');
			if(ta == 'department' && tb == 'user') return -1;
			if(ta == 'user' && tb == 'department') return 1;
			var a1 = parseInt(ca,10);
			var b1 = parseInt(cb,10);
			return a1 > b1 ? 1 : -1;
		},"search":{
			"ajax":{
				"url":"${commonMapper.rootPath}/user/search.do",
				"data":function(str){
					return {"name":str};
				}
			}
			,show_only_matches:true
		},
		"themes":{"theme":"default",dots:false,icons:true},
		"checkbox":{'two_state':true},
		"plugins":["themes", "json_data", "ui", "checkbox", "sort", "search"]
	}).bind('select_node.jstree',function(event,data){
		var n = data.rslt.obj;//$('.userTree').jstree('get_selected');
		if(n.children('a').find('.user').length<=0){
			data.inst.deselect_node(n);
			return;
		}
		if(data.inst.is_checked(n)){
			data.inst.uncheck_node(n);
		}else{
			if(n.children('a').find('.jstree-checkbox').length>0)
				data.inst.check_node(n);
		}
	});

	$('.ok').button().click(function(){
		var nodes = $(".userTree").jstree('get_checked',null,true);
		if($("#flag").val()=='1'){
			nodes = $(".userTree").jstree('get_selected');
		}
		var tmp = new Array();
		
		if(nodes.length <=0){
			alert('请选择人员！');
			return;
		}
		
		nodes.each(function(i,o){
			tmp.push({'id':$(this).attr('id'),
					'name':$(".userTree").jstree('get_text',this)
					});
		});
		//调用父页面的函数，传递参数回去
		window.parent.funDialogCallBack('${param.type}','${specParam}',tmp);
	});
	$('.cancle').button().click(function(){
		//调用父窗体的JS函数，关闭
		window.parent.funDialogCallBack('${param.type}');
	});

	//搜索按钮
	$("#btnSearch").button().click(function(){
		if($("#searchValue").val().length > 0){
			$(".userTree").jstree("close_all");
			$(".userTree").jstree("search",$("#searchValue").val());
		}else{
			$(".userTree").jstree("clear_search");
		}
	});
});
</script>
</head>
<body>
<div style="width: 245px;font-size: 12px;">
	<div>
		<label for="searchValue" style="">名称:</label><input id="searchValue" style="width: 100px;"><input type="button" id="btnSearch" value="查找">
	</div>
	<div class="userTree" style="width: 240px;height: 280px;overflow: auto;">
	</div>
	<div style="text-align: right;">
		<span class="ok">确定</span><span class="cancle">取消</span>
	</div>
</div>
</body>
</html>
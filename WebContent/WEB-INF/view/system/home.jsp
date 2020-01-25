<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache"> 
<meta http-equiv="Expires" content="0"> 
<title>${commonMapper.title}</title>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>
<link href="${pageContext.request.contextPath}/css/cupertino/jquery-ui-1.8.15.custom.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/alert/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<link href="${pageContext.request.contextPath}/css/hodanet.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.ui-layout-pane { /* all 'panes' */ 
		border: 0px solid #91b5d7; 
		overflow: auto;
	} 
	.ui-layout-resizer { /* all 'resizer-bars' */ 
		background: #e0eaf7;
	} 
	.ui-layout-toggler { /* all 'toggler-buttons' */ 
		background: #AAA; 
	}
	.ui-layout-toggler-north-open{
		background: url(/images/closeV.jpg) repeat-x 0 0 !important;
	}
	.ui-layout-toggler-north-closed{
	    background: url(/images/openV.jpg) no-repeat 0 0 !important;
	}
	.ui-layout-toggler-west-open{
	    background: url(/images/close.jpg) no-repeat 0 0 !important;
	}
	.ui-layout-toggler-west-closed{
	    background: url(/images/open.jpg) no-repeat 0 0 !important;
	}
	.paneClass {
		border-color: red;
		border-style: solid;
		border-width: 1px;
	}
	
	.ui-widget{
		font-size: 12px;
	}
	.ui-layout-west{
		padding: 0 !important;
		border-left: #87bbf5 1px solid;
		border-right: #87bbf5 1px solid;
		border-bottom: #87bbf5 1px solid;
		overflow-x: hidden;
	}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.layout.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
<script type="text/javascript">
$.ajaxSetup({'cache':false});
var outLayout,innerLayout;

function funGetData(key){
	return $(".logo").data(key);
}
function funSetData(key,data){
	$(".logo").data(key,data);
}

//修改个人信息
function funModifySelf(){
	if($('.divModifySelf').length > 0) $('.divModifySelf').remove(); 
	var div = $("<div class='divModifySelf'></div>");
	div.load("${pageContext.request.contextPath}/user/modifySelf.do",function(){
		div.dialog({title:'修改个人信息',modal:true,removeble:false,
			'buttons':{
				"保存":function(){
					var p = div.find("#password").val();
					var showP = div.find("#showPassword").val();
					var newP = div.find("#newPassword").val();
					var reP = div.find("#rePassword").val();
					if(newP.length > 0){
						if(showP.length <= 0){
							jAlert('请输入旧密码',"警告");
							return;
						}
						if(newP != reP){
							jAlert('重复密码需要和新密码相同!',"警告");
							return;
						}
						var id = div.find("#id").val();
						var checkUser = true;
						$.ajax({type:'post',url:'/user/checkPassword.do',data:"password="+showP+"&id="+id,dataType:'json',cache:false,async:false,success:function(json){
							checkUser = json.flag;
						},error:function(){checkUser=false}});
						if(checkUser == false){
							jError("旧密码不正确，请重新输入!","错误");
							return false;
						}
						div.find("#password").val(newP);
					}
					div.find('form').ajaxSubmit({
						dataType:'json',
						success:function(data, statusText, xhr, $form){
							if(data.flag == true){
								jAlert(data.msg,'成功',function(){
									div.dialog('close');
								});
							}else{
								jAlert(data.msg,'失败');
							}
						}
					});
				},
				"取消":function(){$(this).dialog('close');}
			}
		});
	});
}

/**退出系统*/
function funLogout(){
	$.post("home/logout.do",null,function(data){
		window.location.href="${pageContext.request.contextPath}/home.do";
	},'text');
}

//打开链接
function funOpenMenuInIframe(obj){
	var link = $(obj);
	var url = link.attr("url");
	if(url != null && url.length > 0){
		$(".inner-center").attr("src",url);
		$(".liebiao1").text(link.text());
	}else{
		//无链接
	}
}

//创建菜单<a href='#'>菜单</a>
function funCreateLink(json){
	var a = $("<a style='cursor: pointer;'><span>"+json.name+"</span></a>")
		.attr("moduleId",	json.module.id)
		.attr("mid",		json.id)
		.attr("url",		json.address != null ? "${pageContext.request.contextPath}" + json.address : "");
	if(a.attr("url")!="")
		a.attr("href","#");
	return a;
}
//创建第二级菜单
function funShowMenu2(data2){
	if(data2 == null || data2.length <= 0){
		innerLayout.hide('north');
		return;
	}
	innerLayout.show('north');
	var menu2 = $(".inner-north .tab").empty();
	var ulInner2 = $("<ul></ul>").appendTo(menu2);
	for(var k=0;k<data2.length;k++){
		ulInner2.append($("<li></li>").append(funCreateLink(data2[k])));
	}
	menu2.find('li').click(function(){
		menu2.find('li.selected').removeClass('selected');
		$(this).addClass('selected');
		funOpenMenuInIframe($(this).find('a'));
	}).eq(0).click();
}
//创建右侧菜单
function funShowSideMenu(data){
	if(data == null || data.length <=0){
		outLayout.hide('west');
		return;
	}
	outLayout.show('west');
	var ul = $("<ul></ul>").appendTo($("#left"));
	for(var i=0;i<data.length;i++){
		var li = $("<li><a href='javascript:void(0);'>"+data[i].name+"</a></li>");
		 if(data[i].childMenus.length > 0){
			var ulInner = $("<ul></ul>").appendTo(li);
			for(var j=0;j<data[i].childMenus.length;j++){
				var liInner = $("<li></li>").append(funCreateLink(data[i].childMenus[j]));
				if(data[i].childMenus[j].childMenus.length > 0){
					liInner.find('a').data('childMenus',data[i].childMenus[j].childMenus);
				}
				ulInner.append(liInner);
			}
			ulInner.find("a").click(function(){
				$("#left").find("a.navover").removeClass('navover');
				$(this).addClass('navover');
				funOpenMenuInIframe($(this));
				//生成第二级菜单
				funShowMenu2($(this).data('childMenus'));
			});
		} 
		ul.append(li);
	}
	if(ul.find("ul:first li:first a").length > 0){
		ul.find("ul:first li:first a").click();
	}else{
		innerLayout.hide('north');
	}
}
//创建菜单
function funSideMenu(moduleId){
	$("#left").empty();
	if(moduleId == null || moduleId == undefined || moduleId == 'undefined' || moduleId == 'welcome'){
		outLayout.hide('west');
		innerLayout.hide('north');
		$(".inner-center").attr('src','home/welcome.do');
		return;
	}else{
		outLayout.show('west');
	}
	if(funGetData(moduleId)==null){
		$.post("${pageContext.request.contextPath}/home/getTopMenus.do",{'moduleId':moduleId,'parentMenuId':'-1'},function(data){
			funSetData(moduleId,data);
			funShowSideMenu(data);
		},'json');
	}else{
		funShowSideMenu(funGetData(moduleId));
	}
}

$(function(){
	//布局
	outLayout = $('body').layout({
		north:{resizable:false,
			minWidth:800,
			onopen_end:function(){innerLayout.resizeAll();},
			onclose_end:function(){innerLayout.resizeAll();}
		},
		west:{resizable:false,
			size:200,
			onopen_end:function(){innerLayout.resizeAll();},
			onclose_end:function(){innerLayout.resizeAll();}
		},
		south:{resizable:false,closable:false}});
	innerLayout = $('div.ui-layout-center').layout({resizable:false,center__paneSelector:".inner-center",north__paneSelector:".inner-north",north__spacing_open:0});
	
	//子系统点击事件
	$(".nav").find("a").click(function(){
		$(".nav").find("a.navover").removeClass('navover');
		$(this).addClass('navover');
		funSideMenu($(this).attr('rel'));
	}).eq(0).click();
	
	//退出按钮
	$(".logout").click(function(){
		funLogout(); return false;
	});
	
	//修改个人信息
	$(".modifySelf").click(function(){
		funModifySelf(); return false;
	});
	
	//防止内部嵌套项目
	if (window.parent != window) {
		window.parent.location.href = "${pageContext.request.contextPath}/index.jsp";
	}
});
</script>
</head>
<body style="min-width: 800px;">
<div class="ui-layout-north nopadding" id="top" style="overflow: hidden;">
	<div class="nav">
	<ul>
		<c:forEach items="${modules}" var="module" varStatus="vs">
			<li mid="${module.id}"><a href="javascript:void(0);" rel="${module.id}" style="cursor: pointer;"><img src="${pageContext.request.contextPath}/images/hodanet/nav_8.png" border="0" />${module.name}</a></li>
		</c:forEach>
		
	</ul>
	</div></div>
</div>
<div class="ui-layout-west">
	<!-- 右侧导航栏 -->
	<div id="left">
	</div>
</div>
<div class="ui-layout-center">
	<div class="inner-north">
		<div class="tab">
			<ul>
			</ul>
		</div>
	</div>
	<iframe class="inner-center" src="" frameborder="no" border="0" marginwidth="0" marginheight="0" allowtransparency="yes" style="border: none;"></iframe>
</div>
<div class="ui-layout-south">
	<div class="footer">
		<div class="footer_left">
		    <ul>
			    <li class="liebiao1"></li>
			    <li><img src="${pageContext.request.contextPath}/images/hodanet/shu.jpg" /></li>
			    <li>欢迎您，<c:out value="${user.name}"></c:out></li>
			    <li>[上次登录时间：<fmt:formatDate value="${userLoginInfo.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>]</li>
			    <li>[<a href="#" class="modifySelf">修改信息</a>]</li>
			    <li><a href="#" class="logout">退出</a></li>
		    </ul>
	    </div>
	    <div class="footer_right">杭州鸿大网络发展有限公司 版权所有</div>
    </div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport"></meta>
<meta name="format-detection" content="telephone=no"></meta>
<meta content="320" name="MobileOptimized"></meta>
<meta content="yes" name="apple-mobile-web-app-capable"></meta>
<meta content="black" name="apple-mobile-web-app-status-bar-style"></meta>
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>家庭医生</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.6.2.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jtys.css" media="screen" />
<script type="text/javascript">
	function updateUserInfo() {
		var age = $("#age").val();
		var sex = $("input:checked").eq(0).val();
		var prop = {};
		prop.openId = $("#openId").val();
		prop.age = age;
		prop.sex = sex;
		if(checkAge(age)){
			$("#notice").html("提示：正在提交请求，请稍候……");
			$.ajax({
				url : "/jtys/api/updateUserInfo.do",
				data : prop,
				cache : false,
				success : function(responseJson) {
					var obj = eval('(' + responseJson + ')');
					if (obj.flag == 1) {
						window.location.href = "/jtys/api/updateUserInfoSuccess.do?openId="
								+ obj.openId;
						return;
					} else {
						$("#notice").html("提示：" + obj.msg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					$("#notice").html("提示：网络异常，请稍候再试……");
				}
			});
		}else{
			$("#notice").html("提示：年龄请输入1-99。");
		}
	}
	function checkAge(text) {
		var re = /^[1-9][0-9]?$/;
		return re.test(text);
	}
	$(function(){
		$("label").click(function(){
			$("label").removeClass("checked").addClass("nochecked");
			$("input[type=radio]").removeAttr("checked");
			$(this).addClass("checked");
			$(this).prev().click();
		});
		$('input:radio[name="sex"][value="'+$("#sex").val()+'"]').prop('checked', true);
		$('input:radio[name="sex"][value="'+$("#sex").val()+'"]').next().eq(0).click();
	});
	
</script>
</head>
<body>
	<div id="wrape">
		<div id="main">
			<div class="main-top">基本资料</div>
			<div class="userinfo-main">
				<input type="hidden" id="openId" name="openId" value="${openId }"></input>
				<input type="hidden" id="sex" name="sex" value="${jtysUser.sex }"></input>
				<p>昵称&nbsp;:&nbsp;&nbsp;<span>&nbsp;&nbsp; ${nickName } </span>
				<p>性别&nbsp;:
							<input type="radio"  value="0"  name="sex" value="0" id="sex" checked="checked" /> <label  class="checked ">&nbsp;男</label>
							<input type="radio"  value="1"  name="sex" value="1" id="sex" /><label  class="nochecked ">&nbsp;女</label></p>
				<p>年龄&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;<input id="age" name="age" type="text" value="${jtysUser.age }" maxlength="2" style="width:30px;"/>&nbsp;&nbsp;&nbsp;岁</p>
				<p style="font-size: 16px;" id="notice"></p>
			</div>
			<!--提交
			<img src="${pageContext.request.contextPath}/images/yhbd_02.png" width="90%" height="65"
				onclick="updateUserInfo();" style="margin: 5%;" />
			<div class="tijiao" onclick="updateUserInfo();">提交</div>-->
			<div class="tijiao" onclick="updateUserInfo();">确&nbsp;认</div>
		</div>
	</div>
</body>
</html>
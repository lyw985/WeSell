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
	src="${commonMapper.rootPath}/js/jquery-1.6.2.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${commonMapper.rootPath}/css/jtys.css" media="screen" />
<script type="text/javascript">
	var sendCodeFlag=true;
	function sendCode() {
		if(sendCodeFlag){
			var prop = {};
			prop.openId = $("#openId").val();
			prop.phone = $("#phone").val();
			$("#notice").html("提示：正在提交请求，请稍候……");
			$.ajax({
				url : "${commonMapper.rootPath}/jtys/api/sendCode.do",
				data : prop,
				cache : false,
				success : function(responseJson) {
					var obj = eval('(' + responseJson + ')');
					if (obj.flag == 1) {
						//$("#sendCodeBtn").attr('disabled','disabled');
						sendCodeFlag=false;
						reduceTime(60);
					}
					$("#notice").html("提示：" + obj.msg);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					$("#notice").html("提示：网络异常，请稍候再试……");
				}
			});
		}
	}

	function bindPhone() {
		var prop = {};
		prop.openId = $("#openId").val();
		prop.phone = $("#phone").val();
		prop.code = $("#code").val();
		$("#notice").html("提示：正在提交请求，请稍候……");
		$.ajax({
			url : "${commonMapper.rootPath}/jtys/api/bindPhone.do",
			data : prop,
			cache : false,
			success : function(responseJson) {
				var obj = eval('(' + responseJson + ')');
				if (obj.flag == 1) {
					window.location.href = "${commonMapper.rootPath}/jtys/api/bindSuccess.do?phone="
							+ obj.phone;
					return;
				} else {
					$("#notice").html("提示：" + obj.msg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#notice").html("提示：网络异常，请稍候再试……");
			}
		});
	}
	
	function reduceTime(leftSec){
		if(leftSec ==0 ){
			$("#sendCodeBtn").html("获取验证码");
			//$("#sendCodeBtn").removeAttr('disabled');
			sendCodeFlag=true;
		}else{
			$("#sendCodeBtn").html("剩余"+leftSec+"秒");
			setTimeout('reduceTime('+(leftSec-1)+')',1000);
		}
	}
	
</script>
</head>
<body>
	<div id="wrape">
		<div id="main">
			<div class="main-top">短信用户验证</div>
			<div class="user-main">
				<input type="hidden" id="openId" name="openId" value="${openId }"></input>
				<!-- <p style="height: 20px;">
					微信用户: <span>${nickName } </span>
				</p> -->
				<p>
					手机号: <span> <input id="phone" name="phone" type="tel" maxlength="11"
						style="font-size:15px; line-height:30px; border:2px solid #cfcfcf; border-radius:5px; width:60%;" />
					</span>
				</p>
				<!-- 
				<p>
					验证码: <span> <input id="code" name="code" type="text" maxlength="6"
						style="line-height:30px; border:2px solid #cfcfcf; border-radius:5px; width:30%;" />
					</span>
					<span id="sendCodeBtn" onclick="sendCode();" class="yzm">获取验证码</span>
				</p> -->
				<input type="hidden" id="code" name="code" value="123456"/>
				<p style="font-size: 16px;" id="notice">注：验证成功后，获赠5元话费<font color="red">（仅限电信短信包月用户）</font>
				</p>
			</div>
			<!--提交
			<img src="/images/yhbd_02.png" width="90%" height="65"
				onclick="bindPhone();" style="margin: 5%;" />-->
			<div class="tijiao" onclick="bindPhone();">确&nbsp;认</div>
		</div>
	</div>
</body>
</html>
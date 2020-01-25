<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	function gotoBind() {
		window.location.href = "/jtys/api/gotoBind.do?openId="+$("#openId").val()+"&accessToken="+$("#accessToken").val();
	}
	
	function gotoPay() {
		window.location.href = "/weixin/pay/jtys/jsapi.do?showwxpaytitle=1&openId="+$("#openId").val()+"&accessToken="+$("#accessToken").val();
	}
	
</script>
</head>

<body>
	<input type="hidden" id="openId" name="openId" value="${openId }"></input>
	<input type="hidden" id="accessToken" name="accessToken" value="${accessToken }"></input>
	<div id="wrape">
		<div id="main">
			<div class="main-top">开通会员</div>
			<div class="huiyuan-main">
				<ul>
					<li onclick="gotoBind()"><div class="huiyuan-left">
							手机号码验证<br />
							<span style="font-size: 13px">电信短信“家庭医生”包月用户</span>
						</div>
						<div class="huiyuan-right">
							<img src="${pageContext.request.contextPath}/images/more.png" width="20" height="25" />
						</div>
					</li>
					<li onclick="gotoPay()"><div class="huiyuan-left">
							微信支付<br />
							<span style="font-size: 13px">开通会员服务，最低只需20元</span>
						</div>
						<div class="huiyuan-right">
							<img src="${pageContext.request.contextPath}/images/more.png" width="20" height="25" />
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>

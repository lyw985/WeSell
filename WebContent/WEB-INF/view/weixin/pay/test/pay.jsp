<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
<script>
	$(function() {
		$(".pay-right").clic(".pay-right-nochoice", "click", function() {
			$(this).toggleClass("pay-right-choice");
		})
	})
</script>
</head>

<body>
	<div id="wrape">
		<div id="main">
			<div class="main-top">开通会员</div>
			<div class="pay-main">
				<ul>
					<li><div class="pay-left"
							style="font-size: 18px; paddding: 0px;">服务</div>
						<div class="pay-center"
							style="color: #000; font-size: 18px; padding-left: 15px;">原价</div>
						<div class="pay-right" style="font-size: 18px; paddding: 0px;">优惠价</div>
					</li>
					<li><div class="pay-left">1个月会员</div>
						<div class="pay-center">20元</div>
						<div class="pay-right">
							<div class="pay-right-price">
								<span>20</span>元
							</div>
							<div class="pay-right-nochoice"></div>
						</div></li>
					<li><div class="pay-left">3个月会员</div>
						<div class="pay-center">
							<s>60元</s><span>&nbsp;&nbsp;9折</span>
						</div>
						<div class="pay-right">
							<div class="pay-right-price">
								<span id="price">54</span>元
							</div>
							<div class="pay-right-nochoice"></div>
						</div></li>
					<li><div class="pay-left">6个月会员</div>
						<div class="pay-center">
							<s>120元</s><span class="zhekou">8折</span>
						</div>
						<div class="pay-right">
							<div class="pay-right-price">
								<span>96</span>元
							</div>
							<div class="pay-right-nochoice"></div>
						</div></li>
					<li><div class="pay-left">12个月会员</div>
						<div class="pay-center">
							<s>240元</s><span class="zhekou">6折</span>
						</div>
						<div class="pay-right">
							<div class="pay-right-price">
								<span>144</span>元
							</div>
							<div class="pay-right-nochoice"></div>
						</div></li>
				</ul>
			</div>
			<p class="sum">共需支付0元</p>
			<!--提交-->
			<div class="tijiao">开通会员</div>
		</div>
	</div>
</body>
</html>
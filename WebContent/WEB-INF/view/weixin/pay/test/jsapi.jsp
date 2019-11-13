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
	href="${commonMapper.rootPath}/
	css/jtys.css" media="screen" />
<script type="text/javascript">
	$(function() {
		$(".pay-item").click(function() {
			$('.pay-item').filter(".sel").removeClass("sel").addClass("unsel");
			$(this).removeClass("unsel").addClass("sel");
			$("#sysProductId").val($(this).attr("def"));
		});
	});
	
	function callpay() {
		var prop = {};
		prop.openId = $("#openId").val();
		prop.serverCode = $("#serverCode").val();
		prop.sysProductId = $("#sysProductId").val();
		$("#notice").html("提示：正在提交请求，请稍候……");
		$.ajax({
			url : "${commonMapper.rootPath}/weixin/pay/createPrepayOrderByJsapi.do",
			data : prop,
			type : "POST",
			cache : false,
			success : function(responseJson) {
				var obj = eval('(' + responseJson + ')');
				if (obj.flag == 1) {
					jsApiCall(obj);
				}
				$("#notice").html("提示：" + obj.msg);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$("#notice").html("提示：网络异常，请稍候再试……");
			}
		});
	}
	//调用微信JS api 支付
	function doCall(prop) {
		var param = {};
		param.appId = prop.appId;
		param.timeStamp = prop.timeStamp;
		param.nonceStr = prop.nonceStr;
		param.package = prop.package;
		param.signType = prop.signType;
		param.paySign = prop.paySign;
		var orderId = prop.orderId;
		WeixinJSBridge.invoke('getBrandWCPayRequest', param, function(res) {
			if (res.err_msg == "get_brand_wcpay_request:ok") {
				$("#notice").html("提示：支付成功");
				updateStatus(orderId, 3);
			}
			if (res.err_msg == "get_brand_wcpay_request:cancel") {
				$("#notice").html("提示：支付取消");
				updateStatus(orderId, 1);
			}
			if (res.err_msg == "get_brand_wcpay_request:fail") {
				$("#notice").html("提示：支付失败");
				updateStatus(orderId, 2);
			}
		});
	}

	function updateStatus(orderId, status) {
		var prop = {};
		prop.id = orderId;
		prop.status = status;
		$.post('${commonMapper.rootPath}/weixin/pay/updateOrderStatus.do',
				prop, function(data) {
					if (data.flag) {

					}
				}, 'json');
	}

	function jsApiCall(prop) {
		if (typeof WeixinJSBridge == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', jsApiCall,
						false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', jsApiCall);
				document.attachEvent('onWeixinJSBridgeReady', jsApiCall);
			}
		} else {
			doCall(prop);
		}
	}
</script>
</head>
<body>
	<input type="hidden" id="openId" name="openId" value="${openId }"></input>
	<input type="hidden" id="serverCode" name="serverCode" value="jtys"></input>
	<input type="hidden" id="sysProductId" name="sysProductId" value="jtys1"></input>
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
					<li class="pay-item sel" def="jtys1"><div class="pay-left">1个月会员</div>
						<div class="pay-center">20元</div>
						<div class="pay-right">
							<div class="pay-right-price">
								<span>20</span>元
							</div>
							<div class="pay-right-img"></div>
						</div>
					</li>
					<li class="pay-item unsel" def="jtys2"><div class="pay-left">3个月会员</div>
						<div class="pay-center">
							<s>60元</s><span>&nbsp;&nbsp;9折</span>
						</div>
						<div class="pay-right">
							<div class="pay-right-price">
								<span id="price">54</span>元
							</div>
							<div class="pay-right-img"></div>
						</div>
					</li>
					<li class="pay-item unsel" def="jtys3"><div class="pay-left">6个月会员</div>
						<div class="pay-center">
							<s>120元</s><span class="zhekou">8折</span>
						</div>
						<div class="pay-right">
							<div class="pay-right-price">
								<span>96</span>元
							</div>
							<div class="pay-right-img"></div>
						</div>
					</li>
					<li class="pay-item unsel" def="jtys4"><div class="pay-left">12个月会员</div>
						<div class="pay-center">
							<s>240元</s><span class="zhekou">6折</span>
						</div>
						<div class="pay-right">
							<div class="pay-right-price">
								<span>144</span>元
							</div>
							<div class="pay-right-img"></div>
						</div>
					</li>
				</ul>
			</div>
			<p class="sum" id="notice"></p>
			<!--提交-->
			<div class="tijiao" onclick="callpay()">开通会员</div>
		</div>
	</div>
</body>
</html>
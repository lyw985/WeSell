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
	src="/js/jquery-1.6.2.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/css/jtys.css" media="screen" />
<script type="text/javascript">
	function gotoHuiyuan() {
		window.location.href = "/weixin/pay/jtys/jsapi.do?showwxpaytitle=1&openId="
				+ $("#openId").val()
				+ "&accessToken="
				+ $("#accessToken").val();
	}
</script>
</head>

<body>
	<input type="hidden" id="openId" name="openId" value="${openId }"></input>
	<input type="hidden" id="accessToken" name="accessToken"
		value="${accessToken }"></input>
	<div id="wrape">
		<div id="main">
			<div
				style="width: 100%; font-size: 200%; font-weight: bold; color: #24c223; background: #f5f4f2; border-bottom: 1px solid #E5E1E1; text-align: center; padding: 2% 0px;">我的特权</div>
			<div class="tequan-states">
				<b>状态 : </b><span class="tequan-huiyuan"><c:if
						test="${jtysUser.status ==0 }">非会员</c:if> <c:if
						test="${jtysUser.status !=0 }">会员</c:if> </span>
			</div>
			<div class="tequan-content">
				<P>会员专享5大特权</P>
			</div>
			<div class="tequan-content-detail">
				<div class="tequan-detail" style="background: #f5f4f2;">
					<div class="left">服务</div>
					<div class="center" style="font-size: 15px; font-weight: bold;">会员</div>
					<div class="right" style="font-size: 15px; font-weight: bold;">非会员</div>
				</div>
				<div class="tequan-detail">
					<div class="left">免费咨询</div>
					<div class="center">不限次数</div>
					<div class="right">仅限首次</div>
				</div>
				<div class="tequan-detail">
					<div class="left">全家享用</div>
					<div class="center">
						<img src="/images/success.png" width="20" height="20" />
					</div>
					<div class="right">
						<img src="/images/error.png" width="20" height="20" />
					</div>
				</div>
				<div class="tequan-detail">
					<div class="left_2">
						专家阵容<br /> <span style="font-size: 12px">全国3万余名专家医生</span>
					</div>
					<div class="center_2">
						<img src="/images/success.png" width="20" height="20" />
					</div>
					<div class="right_2">
						<img src="/images/error.png" width="20" height="20" />
					</div>
				</div>
				<div class="tequan-detail">
					<div class="left">30分钟内回复</div>
					<div class="center">
						<img src="/images/success.png" width="20" height="20" />
					</div>
					<div class="right">
						<img src="/images/error.png" width="20" height="20" />
					</div>
				</div>
				<div class="tequan-detail">
					<div class="left">24小时响应</div>
					<div class="center">
						<img src="/images/success.png" width="20" height="20" />
					</div>
					<div class="right">
						<img src="/images/error.png" width="20" height="20" />
					</div>
				</div>
			</div>

			<!--开通微信会员-->
			<c:if test="${jtysUser.status ==0 }">
				<div class="tijiao" onclick="gotoHuiyuan();">立即开通</div>
			</c:if>

		</div>
	</div>
</body>
</html>

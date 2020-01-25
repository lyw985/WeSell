<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jtys.css" media="screen" />
</head>
<body>
	<div id="wrape">
		<div id="main">
			<div class="main-top">短信用户验证</div>
			<div class="main-content">
				<div class="main-success">
					<p>
						<img src="${pageContext.request.contextPath}/images/success2.png" height="88" width="87" />
					</p>
				</div>
				<div class="line"></div>
				<c:if test="${empty flag}"><p class="text">&nbsp;&nbsp;&nbsp;&nbsp;系统赠送的5元话费会在15个工作日内充值到您的手机账户</p>
				</c:if>
				<c:if test="${!empty flag}"><p>已验证号码:<font color="red">${fn:replace(phone,fn:substring(phone,3,7),'****')}</font></p>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
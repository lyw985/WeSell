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
<body style="background:#fff;">
	<div id="wrape">
		<div id="main">
			<div class="main-top">我的资料</div>
			<div class="main-updateuser">
                <img src="${pageContext.request.contextPath}/images/success2.png" width="70" height="70" />
                <h1>更新成功！</h1>
                
                <div class="updateuser-detail">
                	<h2>您的资料已更新为</h2>
                    <h1>${jtysUser.sexStr } ${jtysUser.age }岁</h1>
                </div>
            </div>
		</div>
	</div>
</body>
</html>
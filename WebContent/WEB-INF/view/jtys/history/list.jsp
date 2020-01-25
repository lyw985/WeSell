<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.6.2.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jtys.css" media="screen" />
<script type="text/javascript">
	function showDetail(openId, problemId, accessToken) {
		window.location.href = "/jtys/api/detail.do?openId="
				+ openId
				+ "&problemId="
				+ problemId
				+ "&accessToken="
				+ accessToken;
	}
</script>
</head>
<body>
	<div id="wrape">
		<div id="main">
			<c:if test="${fn:length(pageData.data)==0}">
				<div class="main-top">暂无问诊记录</div>
				<div class="clear"></div>
			</c:if>
			<c:if test="${fn:length(pageData.data)>0}">
				<div class="main-top">问诊记录</div>
				<div class="history-content">
					<c:forEach items="${pageData.data }" var="jtytCommunicate"
						varStatus="vs">
						<div class="content"
							onclick="showDetail('${jtytCommunicate.user.openId }','${jtytCommunicate.problemId }','${accessToken }');">
							<div style="float:right;width:20%;margin-top:15px"><img src="${pageContext.request.contextPath}/images/jiantou.png" width="20" height="20"/></div>
							<c:if test="${!empty jtytCommunicate.doctor }">
								<h2>${jtytCommunicate.doctor.clinic }
									${jtytCommunicate.doctor.name }</h2>
							</c:if>
							<p style="width: 65%;">${jtytCommunicate.text }</p>
							<font class="answer"><c:if
									test="${!empty jtytCommunicate.doctor }">已解答</c:if>
								<c:if test="${empty jtytCommunicate.doctor }">未解答</c:if>
							</font> <span class="time">${jtytCommunicate.distanceDateStr }</span>
						</div>
					</c:forEach>
				</div>
				<div class="clear"></div>
			</c:if>

		</div>
	</div>
</body>
</html>

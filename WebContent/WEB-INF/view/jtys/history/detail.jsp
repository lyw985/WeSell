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
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jtys.css" media="screen" />
</head>

<body>
	<div id="wrape">
		<div id="main">
			<div class="main-top">问题详情</div>
			<div class="question" style="margin-top: 10%;">
				<c:forEach items="${pageData.data }" var="jtysCommunicate"
					varStatus="vs">

					<!-- 用户提问  -->
					<c:if test="${jtysCommunicate.type ==0 }">
						<div class="question-content">
							<div class="quest-ico">
								<c:if test="${empty headImgUrl}">
									<img src="${pageContext.request.contextPath}/images/weixin_default.png" height="70px"
										width="70px" />
								</c:if>
								<c:if test="${!empty headImgUrl}">
									<img src="${headImgUrl }" height="70px" width="70px" />
								</c:if>
							</div>
							<div class="quest-detail">
								<c:if test="${!empty jtysCommunicate.text}">${ jtysCommunicate.text }</c:if>
								<c:if test="${!empty jtysCommunicate.imageUrl}">
									<img src="${jtysCommunicate.imageUrl }" width="100px"
										onclick="window.open('${jtysCommunicate.imageUrl }')" />
								</c:if>
								<c:if test="${!empty jtysCommunicate.audioUrl}">
									<font color="red">（暂无法播放该录音文件）</font>
								</c:if>
							</div>
						</div>
					</c:if>

					<!--医生回答-->
					<c:if test="${jtysCommunicate.type ==1 }">
						<div class="answer-content">
							<div class="answer-ico">
								<img src="${jtysCommunicate.doctor.image }" height="70px"
									width="70px"
									onclick="window.open('${jtysCommunicate.doctor.image }')" />
							</div>
							<div class="answer-detail">
								<c:if test="${!empty jtysCommunicate.text}">${ jtysCommunicate.text }</c:if>
								<c:if test="${!empty jtysCommunicate.imageUrl}">
									<img src="${jtysCommunicate.imageUrl }" width="100px"
										onclick="window.open('${jtysCommunicate.imageUrl }')" />
								</c:if>
								<c:if test="${!empty jtysCommunicate.audioUrl}">
									<!-- <audio controls="controls" src="${jtysCommunicate.audioUrl}"></audio> -->
									<font color="red">（暂无法播放该录音文件）</font>
								</c:if>
							</div>
						</div>
					</c:if>
					
					<!--系统回复-->
					<c:if test="${jtysCommunicate.type ==2 }">
						<div class="answer-content">
							<div class="answer-ico">
								<img src="${pageContext.request.contextPath}/images/weixin_jtys.jpg" height="70px"
									width="70px" />
							</div>
							<div class="answer-detail">
								<c:if test="${!empty jtysCommunicate.text}">${ jtysCommunicate.text }</c:if>
								<c:if test="${!empty jtysCommunicate.imageUrl}">
									<img src="${jtysCommunicate.imageUrl }" width="100px"
										onclick="window.open('${jtysCommunicate.imageUrl }')" />
								</c:if>
								<c:if test="${!empty jtysCommunicate.audioUrl}">
									<!-- <audio controls="controls" src="${jtysCommunicate.audioUrl}"></audio> -->
									<font color="red">（暂无法播放该录音文件）</font>
								</c:if>
							</div>
						</div>
					</c:if>

				</c:forEach>
			</div>

		</div>
	</div>
	<br/>
	<br/>
	<br/>
	<br/>
</body>
</html>

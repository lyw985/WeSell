<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>${commonMapper.title}</title>
<script type="text/javascript">
	$(function() {
		//日期
	});
</script>
</head>
<body>
	<form:form id="followerInfoForm" action="" method="post">
		<table>
			<tr>
				<th width="25%">openid：</th>
				<td width="75%">${follower.openid}</td>
			</tr>
			<tr>
				<th>用户昵称：</th>
				<td>${follower.nickname}</td>
			</tr>
			<tr>
				<th>是否订阅：</th>
				<td>${follower.subscribe}</td>
			</tr>
			<tr>
				<th>性别：</th>
				<td>${follower.sex}</td>
			</tr>
			<tr>
				<th>所在国家：</th>
				<td>${follower.country}</td>
			</tr>
			<tr>
				<th>所在省份：</th>
				<td>${follower.province}</td>
			</tr>
			<tr>
				<th>所在城市：</th>
				<td>${follower.city}</td>
			</tr>
			<tr>
				<th>用户语言：</th>
				<td>${follower.language}</td>
			</tr>
			<tr>
				<th>用户关注时间：</th>
				<td>${follower.subscribe_time}</td>
			</tr>
			<tr>
				<th>用户图像：</th>
				<td><img src="${follower.headimgurl}"></td>
			</tr>
			
		</table>
	</form:form>
</body>
</html>

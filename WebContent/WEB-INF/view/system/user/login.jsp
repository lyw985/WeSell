<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">
	<title>${commonMapper.title}</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/hodanet.css"  />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination/linkbutton.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pagination/pagination.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/alert/jquery.alerts.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/validationEngine.jquery.css"/>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.15.custom.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.linkbutton.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/hodanet.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/multiselect/a.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/multiselect/b.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validationEngine-en.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validationEngine.js"></script>
	</head>
	<body>
		
		<div class="divmain_login" align="center">
		<div class="loginform">
			<form:form modelAttribute="user" action="${pageContext.request.contextPath}/user/login.do" method="post">
			    <table>
				  <tr>
				     <td align="right">用户名：</td>
				     <td>
				        <form:input path="loginId" maxlength="12"/>
				     </td>
				  </tr>
				  <tr>
				     <td align="right">密码：</td>
				     <td>
				        <form:password path="password" maxlength="15"/>
				     </td>
				  </tr>
				  <tr>
				     <td colspan="2" align="center"><input type="submit" value="登录"/> &nbsp;&nbsp; 
				     <!-- <a href="${pageContext.request.contextPath}/user/gotoRegister.do">注册</a> -->
				     <a href=".do">取消</a>
				     </td>
				  </tr>
			    </table>
			    </br></br>
			    
			</form:form>
			<table border="0" cellspacing="0" cellpadding="0" class="loginfoot">
				  <tr>
				    <td align="right">杭州鸿大网络发展有限公司 版权所有</td>
				  </tr>
				</table>
		</div>
		</div>
	</body>
</html>

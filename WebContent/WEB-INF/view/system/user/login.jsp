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
	<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/hodanet.css"  />
	<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/pagination/linkbutton.css">
	<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/pagination/icon.css">
	<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/pagination/pagination.css">
	<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/alert/jquery.alerts.css"/>
	<link rel="stylesheet" type="text/css" href="${commonMapper.rootPath}/css/validationEngine.jquery.css"/>
	
	<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery-ui-1.8.15.custom.min.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.linkbutton.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.pagination.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.alerts.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.form.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/js/hodanet.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/js/multiselect/a.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/js/multiselect/b.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.validationEngine-en.js"></script>
	<script type="text/javascript" src="${commonMapper.rootPath}/js/jquery.validationEngine.js"></script>
	</head>
	<body>
		
		<div class="divmain_login" align="center">
		<div class="loginform">
			<form:form modelAttribute="user" action="${commonMapper.rootPath}/user/login.do" method="post">
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
				     <!-- <a href="${commonMapper.rootPath}/user/gotoRegister.do">注册</a> -->
				     <a href="${commonMapper.rootPath}.do">取消</a>
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

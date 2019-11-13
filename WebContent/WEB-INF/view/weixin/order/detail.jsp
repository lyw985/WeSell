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
	<table>
		<tr>
			<th>微信订单号：</th>
			<td>${order.transactionId}</td>
		</tr>
		<tr>
			<th>商户订单号：</th>
			<td>${order.outTradeNo}</td>
		</tr>
		<tr>
			<th width="25%">订单状态：</th>
			<td width="75%"><c:if test="${order.tradeState=='SUCCESS'}">支付成功</c:if>
				<c:if test="${order.tradeState=='REFUND'}">转入退款</c:if> <c:if
					test="${order.tradeState=='NOTPAY'}">未支付</c:if> <c:if
					test="${order.tradeState=='CLOSED'}">已关闭</c:if> <c:if
					test="${order.tradeState=='REVOKED'}">已撤销</c:if> <c:if
					test="${order.tradeState=='USERPAYING'}">用户支付中</c:if> <c:if
					test="${order.tradeState=='NOPAY'}">未支付(输入密码或确认支付超时)</c:if> <c:if
					test="${order.tradeState=='PAYERROR'}">支付失败(其他原因，如银行返回失败)</c:if></td>
		</tr>
		<tr>
			<th>总金额（分）：</th>
			<td>${order.totalFee}</td>
		</tr>
		<tr>
			<th>货币种类：</th>
			<td>${order.feeType}</td>
		</tr>
		<tr>
			<th>付款银行：</th>
			<td>${order.bankType}</td>
		</tr>
		<tr>
			<th>付款时间：</th>
			<td>${order.timeEnd}</td>
		</tr>
	</table>
</body>
</html>

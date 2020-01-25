<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"></meta>
<meta content="320" name="MobileOptimized"></meta>
<meta content="yes" name="apple-mobile-web-app-capable"></meta>
<meta content="black" name="apple-mobile-web-app-status-bar-style"></meta>
<title>家庭医生抢话费</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jtys_hd.css" media="screen" />
<script type="text/javascript">
	function hdHelp() {
		var id = $("#ownerId").val();
		var prop = {};
		prop.openId = $("#openId").val();
		$.ajax({
			url : "/jtys/api/hd" + id + "/hdHelp.do",
			data : prop,
			cache : false,
			success : function(responseJson) {
				var obj = eval('(' + responseJson + ')');
				if (obj.flag == 1) {
					$("#helperMoneyDiv").show();
					$("#helperMoney").html(obj.money);
					$("#hdHelpDiv").hide();
					return;
				} else {
					// TODO 错误
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// TODO 错误
			}
		});
	}

	function hdSubmit() {
		var id = $("#ownerId").val();
		var prop = {};
		prop.phone = $("#phone").val();
		$.ajax({
			url : "/jtys/api/hd" + id
					+ "/hdSubmit.do",
			data : prop,
			cache : false,
			success : function(responseJson) {
				var obj = eval('(' + responseJson + ')');
				if (obj.flag == 1) {
					$("#hdPhoneDiv").hide();
					$("#hdSubmitDiv").hide();
					$("#hdSubmitResultDiv").show();
					return;
				} else {
					// TODO 错误
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// TODO 错误
			}
		});
	}
	function showPhoneDiv() {
		$("#showHdPhoneDiv").hide();
		$("#huaFeiDiv").hide();
		$("#hdPhoneDiv").show();
		$("#hdSubmitDiv").show();
	}

	var imgUrl = '/images/weixin_jtys.jpg';
	var link = '/jtys/api/hd${owner.id }/hdDetail.do';
	var desc = '家庭医生迎新年话费抢抢抢！';
	var title = '家庭医生抢话费';
	var appid = 'wx009ab4828e47d9c1';

	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '${appId}', // 必填，公众号的唯一标识
	    timestamp: '${timestamp}', // 必填，生成签名的时间戳
	    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
	    signature: '${signature}',// 必填，签名，见附录1
	    jsApiList: ['onMenuShareAppMessage','onMenuShareTimeline','onMenuShareQQ','onMenuShareWeibo'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	wx.ready(function(){
		wx.onMenuShareTimeline({
			title: title,
			link: link,
			imgUrl: imgUrl,
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		
		wx.onMenuShareAppMessage({
			title: title,
			desc: desc,
			link: link,
			imgUrl: imgUrl,
			trigger: function (res) {
				// 用户点击发送给朋友后执行的回调函数
			},
			success: function (res) {
				// 用户确认分享后执行的回调函数
			},
			cancel: function (res) {
				// 用户取消分享后执行的回调函数
			},
			fail: function (res) {
				// 用户分享失败后执行的回调函数
			}
		});
		
		wx.onMenuShareQQ({
		    title: title, // 分享标题
		    desc: desc, // 分享描述
		    link: link, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
		    }
		});
		
		wx.onMenuShareWeibo({
		    title: title, // 分享标题
		    desc: desc, // 分享描述
		    link: link, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		
	});
	
</script>
<div id='wx_pic' style='margin:0 auto;display:none;'>
<img src='/images/weixin_jtys.jpg' />
</div>
</head>

<body>
	<input id="ownerId" type="hidden" value="${owner.id }" />
	<input id="openId" type="hidden" value="${openId }" />
	<div id="main">
		<div style="width: 100%; height: 45%;">
			<img src="${pageContext.request.contextPath}/images/photo.jpg"
				style="width: 100%; height: 100%; display: block" />
		</div>
		
		<c:if test="${over }">
			<div class="nav">
				<div class="main-huoqu">
					<div class="main-juzhong">
						<span>话费已抢完，感谢各位支持。</span>
					</div>
				</div>
			</div>			
		</c:if>
		<c:if test="${!over }">
			<c:if test="${usertype==1 }">
				<div class="nav" id="huaFeiDiv">
					<div class="main-huoqu">
						<div class="main-juzhong">
							你已经获取了<span>${hdUser.money}</span>元话费
						</div>
					</div>
				</div>
				<c:if test="${hdUser.status ==0 }">
					<div class="nav">
						<div class="main-chongzhi" onclick="_system._guide(true);">
							<div class="main-juzhong">邀请好友助力领取话费</div>
						</div>
					</div>
				</c:if>
				<c:if test="${hdUser.status ==1 }">
					<div class="nav" id="showHdPhoneDiv">
						<div class="main-chongzhi" onclick="showPhoneDiv()">
							<div class="main-juzhong">立即充值</div>
						</div>
					</div>
					<div class="nav" id="hdPhoneDiv" style="display: none;">
						<div class="main-shouji">
							<div class="main-juzhong">
								<p style="font-size: 20px;">
									手机&nbsp;:&nbsp;&nbsp;<input id="phone" type="tel" maxlength="11" 
									style="font-size:15px; line-height:30px; border:2px solid #cfcfcf; border-radius:5px; width:60%;"/>
								</p>
							</div>
						</div>
					</div>
					<div class="nav" id="hdSubmitDiv" style="display: none;">
						<div class="main-tijiao" onclick="hdSubmit()">
							<div class="main-juzhong">提交</div>
						</div>
					</div>
					<div class="nav" id="hdSubmitResultDiv" style="display: none;">
						<div class="text">
							感谢您的配合，话费将会在1月25日<br /> 前充值到您的账户，请注意查收!
						</div>
					</div>
				</c:if>
				<c:if test="${hdUser.status ==2 }">
					<div class="nav">
						<div class="text">
							感谢您的配合，话费将会在1月25日<br /> 前充值到您的账户，请注意查收!
						</div>
					</div>
				</c:if>
			</c:if>
			<c:if test="${usertype==0 }">
				<c:if test="${helped==1 }">
					<div class="nav">
						<div class="main-huoqu">
							<div class="main-juzhong">
								您成功帮TA领取到<span>${money }</span>元话费！
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${helped==0 }">
					<div class="nav" id="helperMoneyDiv" style="display: none;">
						<div class="main-huoqu">
							<div class="main-juzhong">
								您成功帮TA领取到<span id="helperMoney">${money }</span>元话费！
							</div>
						</div>
					</div>
					<div class="nav" id="hdHelpDiv">
						<div class="main-chongzhi" onclick="hdHelp()">
							<div class="main-juzhong">助TA一臂之力</div>
						</div>
					</div>
				</c:if>
				<div class="nav">
					<div class="main-chongzhi" onclick="window.location='${clickUrl}'">
						<div class="main-juzhong">我也要抢</div>
					</div>
				</div>
			</c:if>
		</c:if>
		<div class="nav" style="margin-top: 10%">
			<p>
				卡数有限,快快参加!满100即可兑换<br />
			</p>
			<br />
			<div
				onclick="window.location='http://mp.weixin.qq.com/s?__biz=MzAwMDA0ODQzNQ==&mid=202532592&idx=1&sn=9f9fdaf996cf8bda0db8f635d9f53443#rd'">
				<p>[查看活动规则]</p>
			</div>
		</div>
		<div class="nav" style="margin-top: 8%">
			<p>鸿大家庭医生</p>
		</div>
	</div>
	<style type="text/css">
#cover {
	display: none;
	position: absolute;
	left: 0;
	top: 0;
	z-index: 777;
	background-color: #000000;
	opacity: 0.8;
	width: 100%;
	height: 100%
}

#guide {
	display: none;
	position: absolute;
	right: 18px;
	top: 5px;
	z-index: 888;
}
</style>
	<script type="text/javascript">
		var _system = {
			$ : function(id) {
				return document.getElementById(id);
			},
			_client : function() {
				return {
					w : document.documentElement.scrollWidth,
					h : document.documentElement.scrollHeight,
					bw : document.documentElement.clientWidth,
					bh : document.documentElement.clientHeight
				};
			},
			_scroll : function() {
				return {
					x : document.documentElement.scrollLeft ? document.documentElement.scrollLeft
							: document.body.scrollLeft,
					y : document.documentElement.scrollTop ? document.documentElement.scrollTop
							: document.body.scrollTop
				};
			},
			_cover : function(show) {
				if (show) {
					this.$("cover").style.display = "block";
					this.$("cover").style.width = (this._client().bw > this
							._client().w ? this._client().bw : this._client().w)
							+ "px";
					this.$("cover").style.height = (this._client().bh > this
							._client().h ? this._client().bh : this._client().h)
							+ "px";
				} else {
					this.$("cover").style.display = "none";
				}
			},
			_guide : function(click) {
				this._cover(true);
				this.$("guide").style.display = "block";
				this.$("guide").style.top = (_system._scroll().y + 5) + "px";
				window.onresize = function() {
					_system._cover(true);
					_system.$("guide").style.top = (_system._scroll().y + 5)
							+ "px";
				};
				if (click) {
					_system.$("cover").onclick = function() {
						_system._cover();
						_system.$("guide").style.display = "none";
						_system.$("cover").onclick = null;
						window.onresize = null;
					};
				}
			},
			_zero : function(n) {
				return n < 0 ? 0 : n;
			}
		}
	</script>
	<div id="cover"></div>
	<div id="guide">
		<img src="${pageContext.request.contextPath}/images/share.png" width="240px">
	</div>
</body>
</html>

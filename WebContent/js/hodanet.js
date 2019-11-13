$(function(){
	//不使用缓存
	$.ajaxSetup({cache:false,
		error:function (XMLHttpRequest, textStatus, errorThrown) {
		    // 通常 textStatus 和 errorThrown 之中
		    // 只有一个会包含信息
		    //this; // 调用本次AJAX请求时传递的options参数
		    //alert("XMLHttpRequest:"+XMLHttpRequest.responseText+",textStatus:"+textStatus+",errorThrown:"+errorThrown);
		    var text = $(XMLHttpRequest.responseText);
		    //alert($("#errorPage",text).text() + "\n" + $("#errorMessage",text).text());
		}
	});
	//加载loading
	//$('body').append('<div class="loading">正在加载...</div>');
	//$('.loading').ajaxStart(function(){$(this).show();}).ajaxStop(function(){$(this).hide();});
	var checkButton = $("#checkButton");
	if(checkButton.length > 0){
		$("a",".handle").hide();
		var menuId =checkButton.val();
		$.post(checkButton.attr("root")+"home/button.do",{'menuId':menuId},function(json){
			if(json.length>0){
				for(var i=0;i<json.length;i++){
					$("."+json[i],".handle").show();
				}
			}
		});
	}
});


function funHideMain(pos){
	if(window.parent!=null && window.parent.funHideNav!=null)
		window.parent.funHideNav(pos);
	else if(funHideNav !=null){
		funHideNav(pos);
	}
}
function funShowMain(pos){
	if(window.parent!=null && window.parent.funShowNav!=null)
		window.parent.funShowNav(pos);
	else if(funShowNav !=null){
		funShowNav(pos);
	}
}

//处理键盘事件
function doKey(e){
	var ev = e || window.event;//获取event对象
	var obj = ev.target || ev.srcElement;//获取事件源
	var t = obj.type || obj.getAttribute('type');//获取事件源类型
	if(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea"){
		return false;
	}
}
//禁止后退键 作用于Firefox、Opera
document.onkeypress=doKey;
//禁止后退键  作用于IE、Chrome
document.onkeydown=doKey;

// 打印、导出
function printAndExport(url, params) {
	for (var i = 0; params != undefined && i < params.length; i++) {
		var param = params[i];

		url = url + "&" + param.key + "=" + param.value;
	}

	window.open(url, "_blank", "location=no,menubar=no,toolbar=no,resizable=yes,scrollbars=yes,left=0,top=0,width=" + (screen.width-12) + ",height=" + (screen.height-100));
}

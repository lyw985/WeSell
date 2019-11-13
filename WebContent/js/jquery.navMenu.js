(function($){
	$.fn.navMenu = function(){
		//初始化菜单
		return this.each(function(index,obj){
			var $this = $(this);
			var ul = $this.children('ul');
			var lis = ul.find('li');
			lis.each(function(){
				var width = $(this).width();
				$(this).css('width',width);
			});
			
			//初始显示菜单
			displayR(0,lis);
			//左移按钮事件
			$("#menu_l").click(function(){
				var i = lis.index($("li:visible:first",ul));
				displayL(i-1,lis);
				//菜单翻页后,如果菜单被隐藏,则显示第一个菜单
				if($("li.current:visible",ul).length <= 0)
					$("li:visible:first",ul).find('a').click();
			});
			//右移按钮事件
			$("#menu_r").click(function(){
				var i = lis.index($("li:visible:last",ul));
				displayR(i+1,lis);
				//菜单翻页后,如果菜单被隐藏,则显示第一个菜单
				if($("li.current:visible",ul).length <= 0)
					$("li:visible:first",ul).find('a').click();
			});
			//窗体改变自适应
			jQuery(window).wresize(function(){
				var i = lis.index($("li:visible:first",ul));
				displayR(i,lis);
				//if($("li.current:visible",ul).length <= 0) $("li:visible:first",ul).find('a').click();
				//窗体改变时，控制显示当前菜单
				while($("li.current:visible",ul).length <= 0){
					var j = lis.index($("li:visible:last",ul));
					displayR(j+1,lis);
				}
			});
			

			//******************** 以下是方法定义
			//向右显示窗口可见的菜单数
			//首先隐藏所有菜单，然后逐个显示，知道宽度达到文档宽度
			function displayR(index,lis){
				var tempWidth = 0,windowWidth = document.documentElement.clientWidth - 80;
				lis.css('display','none');
				var b = index,e=index;
				for(var i=index;i<lis.length;i++,e=i){
					tempWidth += lis.eq(i).width();
					//alert(tempWidth + ":" +document.documentElement.clientWidth);
					if(tempWidth < windowWidth){
						lis.eq(i).css('display','inline-block');
					}else{
						break;
					}
				}
				//显示向左翻页箭头
				$("#menu_l").css('display',(b>0)?'inline-block':'none');
				//显示向右翻页箭头
				$("#menu_r").css('display',(e < lis.size())?'inline-block':'none');
			};

			//向左显示窗口可见的菜单数
			//首先隐藏所有菜单，然后逐个显示，知道宽度达到文档宽度，若还未达到整体宽度，则继续显示右侧菜单
			function displayL(index,lis){
				var tempWidth = 0,windowWidth = document.documentElement.clientWidth - 80;
				lis.css('display','none');
				var b = index,e=index;
				for(var i=index;i>=0;i--,b=i){
					tempWidth += lis.eq(i).width();
					if(tempWidth < windowWidth){
						lis.eq(i).css('display','inline-block');
					}else{
						break;
					}
				}
				if(tempWidth < windowWidth){//加入宽度还没有填充满，那么就向右继续显示
					for(var i=index+1;i<lis.length;i++,e=i){
						tempWidth += lis.eq(i).width();
						if(tempWidth < windowWidth){
							lis.eq(i).css('display','inline-block');
						}else{
							break;
						}
					}
				}
				//显示向左翻页箭头
				$("#menu_l").css('display',(b>0)?'inline-block':'none');
				//显示向右翻页箭头
				$("#menu_r").css('display',(e < lis.size())?'inline-block':'none');
			};
		});
	};
	
})(jQuery);
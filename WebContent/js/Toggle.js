
//新闻图片切换
var imgSrc = ['images/flimg02.jpg', 'images/flimg01.jpg', 'images/flimg02.jpg', 'images/flimg01.jpg'];
var txts = ['省委领导来我市视察工作1', '省委领导来我市视察工作2', '省委领导来我市视察工作3', '省委领导来我市视察工作4'];
var link = ['1', '2', '3', '4'];
var newsImg = document.getElementById('newsImg');
var a_Txt = document.getElementById('a_Txt');
var newsImgContent = document.getElementById('newsImgContent');
var bigImg = newsImg?newsImg.getElementsByTagName('img')[0]:null;
var bigTxt = a_Txt?a_Txt.getElementsByTagName('a')[0]:null;
var smallImg = newsImgContent?newsImgContent.getElementsByTagName('li'):null;
var autoIndex = 0;
var timerPause = false;

function autoNews(){
	if(!smallImg){
		return;
	}
	for(var i = 0; i < smallImg.length; ++i){
		smallImg[i].tabIndex = i;
		smallImg[i].onmouseover = getHoverOver;
		smallImg[i].onmouseout = getHoverOut;
	}
};

function getHoverOver(noPause){
	if(noPause !== true){
		timerPause = true;
	}
	for(var i = 0; i < smallImg.length; ++i){
		if(i == this.tabIndex){
			smallImg[i].className = 'current';
			bigImg.src = imgSrc[i];
			bigTxt.href = link[i];
			bigTxt.innerHTML = txts[i];
		}
		else{
			smallImg[i].className = '';
		}
	}
};

function getHoverOut(){
	autoIndex = this.tabIndex;
	timerPause = false;
};

autoNews();

function onTime(){
	if(timerPause || !smallImg){
		return;
	}
	autoIndex = (autoIndex + 1) % 4;
	smallImg[autoIndex].onmouseover(true);
};

setInterval(onTime, 2000);

/*控制左右高度*/
function ltHeight(){
var left_h=document.getElementById("lt_nav").clientHeight;
var right_h=document.getElementById("right").clientHeight;
if(left_h >right_h){
	document.getElementById("lt_nav").style.height=left_h-185+"px";
	document.getElementById("right").style.height=left_h+"px";
	}
	else{
	document.getElementById("lt_nav").style.height=right_h-185+"px";
	document.getElementById("right").style.height=right_h+"px";
		}
}

/*左部导航*/
function ltList(){
var nav=document.getElementById("listnav");
var lis=nav.childNodes;
for(i=0;i<lis.length;i++){
	if(lis[i].tagName=="LI"&&lis[i].getElementsByTagName("ul")[0]){
		lisa=lis[i].firstChild;
		lisa.onclick=function showUl(){
			var ul=this.parentNode.getElementsByTagName("ul")[0];
			if(ul.className=="close_ul"){
				ul.className="show_ul";}
				else{
				ul.className="close_ul";}
			}
		}
	}
}
ltList();

/*新闻切换*/
function opentab_con1(){
	document.getElementById("li1").className="li1";
	document.getElementById("li2").className="";
	document.getElementById("tab_cons1").style.display="block";
	document.getElementById("tab_cons2").style.display="none";
	}
function opentab_con2(){
	document.getElementById("li1").className="";
	document.getElementById("li2").className="li2";
	document.getElementById("tab_cons1").style.display="none";
	document.getElementById("tab_cons2").style.display="block";
}

function showCon(showContent,selfObj){
	var tag = document.getElementById("tab").getElementsByTagName("li");
	var taglength = tag.length;
	for(i=0; i<taglength; i++){
		tag[i].className = "";
	}
	selfObj.parentNode.className = "li2";
	for(i=0; j=document.getElementById("tab_Con"+i); i++){
		j.style.display = "none";
	}
	document.getElementById(showContent).style.display = "block";

}

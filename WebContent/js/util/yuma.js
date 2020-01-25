var localObj = window.location;
var contextPath = "/"+localObj.pathname.split("/")[1];
function loadSelByJson(selId,json){
	if(!!!json.length){ 
		$("#"+selId).append("<option value=''>--没有数据--</option>");
	}else if(json.length==1){
		$("#"+selId).append("<option value="+json[0].id+">"+json[0].name+"</option>");
		$("#"+selId).val(json[0].id);
		$("#"+selId).change();
	}else{
		$("#"+selId).append("<option value=''>--请选择--</option>");
		for(var i=0;i<json.length;i++){
			$("#"+selId).append("<option value="+json[i].id+">"+json[i].name+"</option>");
		}
	}
}
function initProvinceSel(provinceSelId,citySelId,areaSelId,province_id,city_id,area_id){
	$("#"+provinceSelId).find("option").remove();
	resetCitySel(citySelId);
	resetAreaSel(areaSelId);
	var prop={};
	$.ajax({
		url : contextPath+"/common/address/getProvinces.do",
		type: 'POST',
		dataType: "json",
		data : prop,
		cache : false,
		success : function(json) {
			loadSelByJson(provinceSelId,json);
			if(!!province_id){
				$("#"+provinceSelId).val(province_id);
				initCitySel(citySelId,areaSelId,province_id,city_id,area_id);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载出错，请稍候重试")
		}
	});
}
function initCitySel(citySelId,areaSelId,province_id,city_id,area_id){
	if(!!!province_id){
		resetCitySel(citySelId);
		resetAreaSel(areaSelId);
		return;
	}
	$("#"+citySelId).find("option").remove();
	resetAreaSel(areaSelId);
	var prop={};
	prop.province_id=province_id;
	$.ajax({
		url : contextPath+"/common/address/getCitys.do",
		type: 'POST',
		dataType: "json",
		data : prop,
		cache : false,
		success : function(json) {
			loadSelByJson(citySelId,json);
			$("#"+citySelId).show();
			if(!!city_id){
				$("#"+citySelId).val(city_id);
				initAreaSel(areaSelId,city_id,area_id);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载出错，请稍候重试")
		}
	});
}
function initAreaSel(areaSelId,city_id,area_id){
	if(!!!city_id){
		resetAreaSel(areaSelId);
		return;
	}
	$("#"+areaSelId).find("option").remove();
	var prop={};
	prop.city_id=city_id;
	$.ajax({
		url : contextPath+"/common/address/getAreas.do",
		type: 'POST',
		dataType: "json",
		data : prop,
		cache : false,
		success : function(json) {
			loadSelByJson(areaSelId,json);
			$("#"+areaSelId).show();
			if(!!area_id){
				$("#"+areaSelId).val(area_id);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载出错，请稍候重试")
		}
	});
}

function resetCitySel(citySelId){
	$("#"+citySelId).hide();
	$("#"+citySelId).val("");
}
function resetAreaSel(areaSelId){
	$("#"+areaSelId).hide();
	$("#"+areaSelId).val("");
}
function initItemSel(itemSelId,itemModelSelId,item_id,item_model_id){
	//alert(itemSelId+":"+itemModelSelId+":"+item_id+":"+item_model_id)
	$("#"+itemSelId).find("option").remove();
	resetItemModelSel(itemModelSelId);
	var prop={};
	$.ajax({
		url : contextPath+"/yuma/item/getItems.do",
		type: 'POST',
		dataType: "json",
		data : prop,
		cache : false,
		success : function(json) {
			if(!!!json.length){ 
				$("#"+itemSelId).append("<option value=''>--没有数据--</option>");
			}else if(json.length==1){
				$("#"+itemSelId).append("<option value="+json[0].id+">"+json[0].name+"【按"+json[0].type+"计算】</option>");
				$("#"+itemSelId).val(json[0].id);
				$("#"+itemSelId).change();
			}else{
				$("#"+itemSelId).append("<option value=''>--请选择--</option>");
				for(var i=0;i<json.length;i++){
					$("#"+itemSelId).append("<option value="+json[i].id+">"+json[i].name+"【按"+json[i].type+"计算】</option>");
				}
			}
			if(!!item_id){
				$("#"+itemSelId).val(item_id);
				initItemModelSel(itemModelSelId,item_id,item_model_id);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载出错，请稍候重试")
		}
	});
}
function initItemModelSel(itemModelSelId,item_id,item_model_id){
	if(!!!item_id){
		resetItemModelSel(itemModelSelId);
		return;
	}
	$("#"+itemModelSelId).find("option").remove();
	var prop={};
	prop.item_id=item_id;
	$.ajax({
		url : contextPath+"/yuma/item/getItemModels.do",
		type: 'POST',
		dataType: "json",
		data : prop,
		cache : false,
		success : function(json) {
			loadSelByJson(itemModelSelId,json);
			$("#"+itemModelSelId).show();
			if(!!item_model_id){
				$("#"+itemModelSelId).val(item_model_id);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载出错，请稍候重试")
		}
	});
}

function resetItemModelSel(itemModelSelId){
	$("#"+itemModelSelId).hide();
	$("#"+itemModelSelId).val("");
}
function initItemSel(itemSelId,itemModelSelId,item_id,item_model_id){
	//alert(itemSelId+":"+itemModelSelId+":"+item_id+":"+item_model_id)
	$("#"+itemSelId).find("option").remove();
	resetItemModelSel(itemModelSelId);
	var prop={};
	$.ajax({
		url : contextPath+"/yuma/item/getItems.do",
		type: 'POST',
		dataType: "json",
		data : prop,
		cache : false,
		success : function(json) {
			if(!!!json.length){ 
				$("#"+itemSelId).append("<option value=''>--没有数据--</option>");
			}else if(json.length==1){
				$("#"+itemSelId).append("<option value="+json[0].id+">"+json[0].name+"【按"+json[0].type+"计算】</option>");
				$("#"+itemSelId).val(json[0].id);
				$("#"+itemSelId).change();
			}else{
				$("#"+itemSelId).append("<option value=''>--请选择--</option>");
				for(var i=0;i<json.length;i++){
					$("#"+itemSelId).append("<option value="+json[i].id+">"+json[i].name+"【按"+json[i].type+"计算】</option>");
				}
			}
			if(!!item_id){
				$("#"+itemSelId).val(item_id);
				initItemModelSel(itemModelSelId,item_id,item_model_id);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载出错，请稍候重试")
		}
	});
}
function initItemModelSel(itemModelSelId,item_id,item_model_id){
	if(!!!item_id){
		resetItemModelSel(itemModelSelId);
		return;
	}
	$("#"+itemModelSelId).find("option").remove();
	var prop={};
	prop.item_id=item_id;
	$.ajax({
		url : contextPath+"/yuma/item/getItemModels.do",
		type: 'POST',
		dataType: "json",
		data : prop,
		cache : false,
		success : function(json) {
			loadSelByJson(itemModelSelId,json);
			$("#"+itemModelSelId).show();
			if(!!item_model_id){
				$("#"+itemModelSelId).val(item_model_id);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载出错，请稍候重试")
		}
	});
}

function resetItemModelSel(itemModelSelId){
	$("#"+itemModelSelId).hide();
	$("#"+itemModelSelId).val("");
}
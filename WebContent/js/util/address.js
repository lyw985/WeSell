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
function initProvinceSel(provinceSelId,citySelId,areaId,province_id,city_id,area_id){
	//alert(provinceSelId+":"+citySelId+":"+areaId+":"+province_id+":"+city_id+":"+area_id)
	$("#"+provinceSelId).find("option").remove();
	resetCitySel(citySelId);
	resetAreaSel(areaId);
	var prop={};
	$.ajax({
		url : "/common/address/getProvinces.do",
		type: 'POST',
		dataType: "json",
		data : prop,
		cache : false,
		success : function(json) {
			loadSelByJson(provinceSelId,json);
			if(!!province_id){
				$("#"+provinceSelId).val(province_id);
				initCitySel(citySelId,areaId,province_id,city_id,area_id);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载出错，请稍候重试")
		}
	});
}
function initCitySel(citySelId,areaId,province_id,city_id,area_id){
	$("#"+citySelId).find("option").remove();
	resetAreaSel(areaId);
	var prop={};
	prop.province_id=province_id;
	$.ajax({
		url : "/common/address/getCitys.do",
		type: 'POST',
		dataType: "json",
		data : prop,
		cache : false,
		success : function(json) {
			loadSelByJson(citySelId,json);
			$("#"+citySelId).show();
			if(!!city_id){
				$("#"+citySelId).val(city_id);
				initAreaSel(areaId,city_id,area_id);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载出错，请稍候重试")
		}
	});
}
function initAreaSel(areaSelId,city_id,area_id){
	$("#"+areaSelId).find("option").remove();
	var prop={};
	prop.city_id=city_id;
	$.ajax({
		url : "/common/address/getAreas.do",
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
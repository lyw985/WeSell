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
		$(".dateTime").datepicker();
		var province_id=$("#province_id").val();
		var city_id=$("#city_id").val();
		var area_id=$("#area_id").val();
		loadSelect("province","",province_id);
		if(province_id){
			loadSelect("city",province_id,city_id);
		}
		if(city_id){
			loadSelect("area",city_id,area_id);
		}
		if($("#name").val()==""){
			$("#name").val($("#defaultName").val());
		}
		if($("#phone").val()==""){
			$("#phone").val($("#defaultPhone").val());
		}
	});
	
	
	function loadSelect(selectId,parentId,val){
		$("#"+selectId).find("option").remove();
		var prop={};
		prop.parent_id=parentId;
		$.ajax({
			url : "${commonMapper.rootPath}/common/address/getFollowAddresses.do",
			type: 'POST',
			dataType: "json",
			data : prop,
			cache : false,
			success : function(json) {
				if(!!!json.length){ 
					$("#"+selectId).append("<option value=''>没有发现子分类</option>");
				}else if(json.length==1){
					$("#"+selectId).append("<option value="+json[0].id+">"+json[0].name+"</option>");
					$("#"+selectId).change();
				}else{
					$("#"+selectId).append("<option value=''>--请选择--</option>");
					for(var i=0;i<json.length;i++){
						//alert(json[i].id+":"+json[i].name);
						$("#"+selectId).append("<option value="+json[i].id+">"+json[i].name+"</option>");
					}
				}
				$("#"+selectId).val(val);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert()
			}
		});
	}
	
	function resetSelect(selectId,parentId){
		$("#"+selectId).find("option").remove();
		$("#"+selectId).val("");
	}
	
</script>
</head>
<body>
	<form:form id="yumaReceiverForm"
		action="${commonMapper.rootPath}/yuma/receiver/save.do" method="post"
		commandName="yumaReceiver">
		<form:hidden path="id" />
		<form:hidden path="yumaUser.id" />
		<input id="province_id" type="hidden" value="${yumaReceiver.province.id}" />
		<input id="city_id" type="hidden" value="${yumaReceiver.city.id}" />
		<input id="area_id" type="hidden" value="${yumaReceiver.area.id}" />
		<input id="defaultName" type="hidden" value="${yumaReceiver.yumaUser.name}" />
		<input id="defaultPhone" type="hidden" value="${yumaReceiver.yumaUser.phone}" />
		<table>
			<tr>
				<th><font color="red">*</font>收货人姓名：</th>
				<td><form:input path="name" maxlength="64"
						cssClass="validate[required]" />
				</td>
			</tr>
			<tr>
				<th><font color="red">*</font>联系号码：</th>
				<td><form:input path="phone" maxlength="64"
						cssClass="validate[required]" />
				</td>
			</tr>
			<tr>
				<th>省份：</th>
				<td><select id="province" name="province.id" onchange="if(this.value){loadSelect('city',this.value,'')}else{resetSelect('city');};resetSelect('area');">
					</select>
				</td>
			</tr>
			<tr>
				<th>城市：</th>
				<td><select id="city" name="city.id" onchange="if(this.value){loadSelect('area',this.value,'')}else{resetSelect('area');}">
					</select>
				</td>
			</tr>
			<tr>
				<th>县区：</th>
				<td><select id="area" name="area.id">
					</select>
				</td>
			</tr>
			<tr>
				<th><font color="red">*</font>详细地址：</th>
				<td><form:textarea path="addressDetail" rows="3" maxlength="512"
						cssClass="validate[required]" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>

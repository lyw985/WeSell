package com.hodanet.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.constant.DisplayStatus;
import com.hodanet.common.entity.po.Area;
import com.hodanet.common.entity.po.City;
import com.hodanet.common.entity.po.Province;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.service.AreaService;
import com.hodanet.common.service.CityService;
import com.hodanet.common.service.ProvinceService;
import com.hodanet.common.util.WebUtil;

/**
 * @anthor lyw
 * @address 2013-7-9 3:04:35
 */
@Controller
@RequestMapping(value = "/common/address")
public class AddressController {

	private static final String LIST_PAGE = "common/address/list";
	private static final String INFO_PAGE = "common/address/info";

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private CityService cityService;

	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/list")
	public String list(Model model, PageData<Province> pageData, HttpServletRequest request) {
		List<Province> bodys = provinceService.getBodyProvinces();
		Province province = new Province();
		province.setShowCitys(true);
		pageData = provinceService.getProvinceByPage(pageData, province);
		model.addAttribute("pageData", pageData);
		model.addAttribute("bodys", bodys);
		return LIST_PAGE;
	}

	@RequestMapping(value = "/query")
	public String query(Model model, PageData<Province> pageData, HttpServletRequest request) {
		String SHADOW = "0";
		String BODY = "1";
		String BODY_WITH_SHADOW = "11";
		String BODY_WITHOUT_SHADOW = "12";
		String NO_MAPPING = "0";
		String PART_MAPPING = "1";
		String ALL_MAPPING = "2";
		String name = request.getParameter("name");
		String bodyType = request.getParameter("bodyType");
		String mappingType = request.getParameter("mappingType");

		Province province = new Province();
		province.setShowCitys(true);
		province.setName(name);
		if (SHADOW.equals(bodyType)) {
			province.setIsBody(false);
		}
		if (BODY.equals(bodyType) || BODY_WITH_SHADOW.equals(bodyType) || BODY_WITHOUT_SHADOW.equals(bodyType)) {
			province.setIsBody(true);
			if (BODY_WITH_SHADOW.equals(bodyType) || BODY_WITHOUT_SHADOW.equals(bodyType)) {
				pageData.setPageSize(Integer.MAX_VALUE);
			}
		}
		pageData = provinceService.getProvinceByPage(pageData, province);
		if (pageData.getData().size() > 0) {
			List<Province> provinces = pageData.getData();
			for (int i = provinces.size() - 1; i >= 0; i--) {
				Province p = provinces.get(i);
				if (BODY_WITH_SHADOW.equals(bodyType) && p.getShadows().size() == 0) {
					provinces.remove(i);
				}
				if (BODY_WITHOUT_SHADOW.equals(bodyType) && p.getShadows().size() > 0) {
					provinces.remove(i);
				}
			}
			if (BODY_WITH_SHADOW.equals(bodyType) || BODY_WITHOUT_SHADOW.equals(bodyType)) {
				pageData.setTotal(provinces.size());
			}
		}

		List<Province> bodys = provinceService.getBodyProvinces();
		model.addAttribute("pageData", pageData);
		model.addAttribute("bodys", bodys);
		model.addAttribute("name", name);
		model.addAttribute("bodyType", bodyType);
		model.addAttribute("mappingType", mappingType);
		return LIST_PAGE;
	}

	@RequestMapping(value = "/getProvinces")
	public void getProvinces(Model model, HttpServletResponse response, HttpServletRequest request) {
		Province province = new Province();
		Province body = new Province();
		body.setId(0);
		province.setBody(body);
		PageData<Province> pageData = new PageData<Province>();
		pageData.setPageSize(Integer.MAX_VALUE);
		pageData = provinceService.getProvinceByPage(pageData, province);
		if (pageData != null && pageData.getData().size() > 0) {
			JSONArray array = new JSONArray();
			for (int i = 0; i < pageData.getData().size(); i++) {
				province = pageData.getData().get(i);
				JSONObject object = new JSONObject();
				object.put("id", province.getId());
				object.put("name", province.getName());
				array.add(object);
			}
			WebUtil.responseText(response, array.toJSONString());
		} else {
			WebUtil.responseText(response, "{}");
		}
	}

	@RequestMapping(value = "/getCitys")
	public void getCitys(Model model, HttpServletResponse response, HttpServletRequest request) {
		String provinceIdString = request.getParameter("province_id");
		City city = new City();
		Province province = new Province();
		if (provinceIdString != null) {
			province.setId(Integer.parseInt(provinceIdString));
		} else {
			province.setId(0);
		}
		city.setProvince(province);
		city.setDisplayStatus(DisplayStatus.DISPLAY.getValue());
		PageData<City> pageData = new PageData<City>();
		pageData.setPageSize(Integer.MAX_VALUE);
		pageData = cityService.getCityByPage(pageData, city);
		if (pageData != null && pageData.getData().size() > 0) {
			JSONArray array = new JSONArray();
			for (int i = 0; i < pageData.getData().size(); i++) {
				city = pageData.getData().get(i);
				JSONObject object = new JSONObject();
				object.put("id", city.getId());
				object.put("name", city.getName());
				array.add(object);
			}
			WebUtil.responseText(response, array.toJSONString());
		} else {
			WebUtil.responseText(response, "{}");
		}
	}

	@RequestMapping(value = "/getAreas")
	public void getAreas(Model model, HttpServletResponse response, HttpServletRequest request) {
		String cityIdString = request.getParameter("city_id");
		Area area = new Area();
		City city = new City();
		if (cityIdString != null) {
			city.setId(Integer.parseInt(cityIdString));
		} else {
			city.setId(0);
		}
		area.setCity(city);
		PageData<Area> pageData = new PageData<Area>();
		pageData.setPageSize(Integer.MAX_VALUE);
		pageData = areaService.getAreaByPage(pageData, area);
		if (pageData != null && pageData.getData().size() > 0) {
			JSONArray array = new JSONArray();
			for (int i = 0; i < pageData.getData().size(); i++) {
				area = pageData.getData().get(i);
				JSONObject object = new JSONObject();
				object.put("id", area.getId());
				object.put("name", area.getName());
				array.add(object);
			}
			WebUtil.responseText(response, array.toJSONString());
		} else {
			WebUtil.responseText(response, "{}");
		}
	}

	@RequestMapping(value = "/beBody", method = RequestMethod.POST)
	public void beBody(HttpServletResponse response, @RequestParam("id") Integer id) {
		provinceService.updateProvinceByBody(id, 0);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "更新成功")));
	}

	@RequestMapping(value = "/beShadow", method = RequestMethod.POST)
	public void beShadow(HttpServletResponse response, @RequestParam("id") Integer id,
			@RequestParam("body_id") Integer bodyId) {
		provinceService.updateProvinceByBody(id, bodyId);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "更新成功")));
	}

	@RequestMapping(value = "/hideCity", method = RequestMethod.POST)
	public void hideCity(HttpServletResponse response, @RequestParam("id") Integer id) {
		cityService.updateCityDisplayStatus(id, DisplayStatus.HIDDEN.getValue());
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "更新成功")));
	}

	@RequestMapping(value = "/showCity", method = RequestMethod.POST)
	public void showCity(HttpServletResponse response, @RequestParam("id") Integer id) {
		cityService.updateCityDisplayStatus(id, DisplayStatus.DISPLAY.getValue());
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "更新成功")));
	}

}

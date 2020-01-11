package com.hodanet.yuma.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.hodanet.yuma.entity.vo.jquery.JQueryChart;
import com.hodanet.yuma.entity.vo.jquery.JQueryLineChart;
import com.hodanet.yuma.service.YumaChartService;
import com.hodanet.yuma.service.impl.chart.ChartQueryFactory;

/**
 * @anthor lyw
 * @yumaOrder 2016-11-27 10:31:35
 */
@Controller
@RequestMapping(value = "/yuma/chart")
public class YumaChartController {

	private final Logger logger = Logger.getLogger(YumaChartController.class);

	private static final String LIST_PAGE = "yuma/chart/list";

	@Autowired
	private YumaChartService yumaChartService;

	/**
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, HttpServletRequest request) {
		JQueryChart chart = new JQueryLineChart();
		chart.testData();
		model.addAttribute("chartConfig", StringEscapeUtils.escapeHtml(JSONObject.toJSONString(chart)));
		return LIST_PAGE;
	}

	/**
	 * ѯ
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/query")
	public String query(Model model, HttpServletRequest request) {
		ChartQueryFactory chartQueryFactory = new ChartQueryFactory();
		String chartParamertType = request.getParameter("chartParamertType");
		String chartGroupByType1 = request.getParameter("chartGroupByType1");
		String chartGroupByType2 = request.getParameter("chartGroupByType2");
		String chartOrderByType = request.getParameter("chartOrderByType");
		String chartLimitNumber = request.getParameter("chartLimitNumber");
		String item_id = request.getParameter("item_id");
		String item_model_id = request.getParameter("item_model_id");
		String province_id = request.getParameter("province_id");
		String city_id = request.getParameter("city_id");
		String area_id = request.getParameter("area_id");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		chartQueryFactory.setParameter(Integer.parseInt(chartParamertType));
		if (StringUtils.isNotBlank(chartGroupByType1)) {
			chartQueryFactory.setGroupBy(Integer.parseInt(chartGroupByType1));
		}
		if (StringUtils.isNotBlank(chartGroupByType2)) {
			chartQueryFactory.setGroupBy(Integer.parseInt(chartGroupByType2));
		}
		if (StringUtils.isNotBlank(chartOrderByType)) {
			chartQueryFactory.setOrderBy(Integer.parseInt(chartOrderByType));
		}
		if (StringUtils.isNotBlank(chartLimitNumber)) {
			chartQueryFactory.setLimitNumber(Integer.parseInt(chartLimitNumber));
		}
		if (StringUtils.isNotBlank(startDate)) {
			chartQueryFactory.setCondition("startDate", startDate);
		}
		if (StringUtils.isNotBlank(endDate)) {
			chartQueryFactory.setCondition("endDate", endDate);
		}
		if (StringUtils.isNotBlank(item_id)) {
			chartQueryFactory.setCondition("item_id", item_id);
		}
		if (StringUtils.isNotBlank(item_model_id)) {
			chartQueryFactory.setCondition("item_model_id", item_model_id);
		}
		if (StringUtils.isNotBlank(province_id)) {
			chartQueryFactory.setCondition("province_id", province_id);
		}
		if (StringUtils.isNotBlank(city_id)) {
			chartQueryFactory.setCondition("city_id", city_id);
		}
		if (StringUtils.isNotBlank(area_id)) {
			chartQueryFactory.setCondition("area_id", area_id);
		}
		JQueryChart chart = yumaChartService.getChart(chartQueryFactory);
//		System.out.println(JSONObject.toJSONString(chart));
		model.addAttribute("chartConfig", StringEscapeUtils.escapeHtml(JSONObject.toJSONString(chart)));
		model.addAttribute("chartParamertType", chartParamertType);
		model.addAttribute("chartGroupByType1", chartGroupByType1);
		model.addAttribute("chartGroupByType2", chartGroupByType2);
		model.addAttribute("chartOrderByType", chartOrderByType);
		model.addAttribute("chartLimitNumber", chartLimitNumber);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("item_id", item_id);
		model.addAttribute("item_model_id", item_model_id);
		model.addAttribute("province_id", province_id);
		model.addAttribute("city_id", city_id);
		model.addAttribute("area_id", area_id);
		return LIST_PAGE;
	}

}

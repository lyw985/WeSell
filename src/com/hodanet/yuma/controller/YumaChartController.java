package com.hodanet.yuma.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hodanet.yuma.entity.vo.jquery.JQueryChart;
import com.hodanet.yuma.entity.vo.jquery.JQueryLineChart;
import com.hodanet.yuma.service.YumaChartService;

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
		JQueryChart chart = yumaChartService.getChart();
//		System.out.println(JSONObject.toJSONString(chart));
		model.addAttribute("chartConfig", StringEscapeUtils.escapeHtml(JSONObject.toJSONString(chart)));
		return LIST_PAGE;
	}

}

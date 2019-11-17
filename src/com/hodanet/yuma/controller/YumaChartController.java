package com.hodanet.yuma.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hodanet.yuma.service.YumaOrderService;
import com.hodanet.yuma.service.YumaWeidianDataService;

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
	private YumaOrderService yumaOrderService;

	@Autowired
	private YumaWeidianDataService yumaWeidianDataService;

	/**
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, HttpServletRequest request) {
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
	public String query(Model model,  HttpServletRequest request) {
		return LIST_PAGE;
	}

}

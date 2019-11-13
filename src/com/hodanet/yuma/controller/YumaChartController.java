package com.hodanet.yuma.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.entity.po.Area;
import com.hodanet.common.entity.po.City;
import com.hodanet.common.entity.po.Province;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.DateConverterUtil;
import com.hodanet.common.util.StringUtil;
import com.hodanet.common.util.WebUtil;
import com.hodanet.system.util.XlSUtil;
import com.hodanet.yuma.entity.po.YumaOrder;
import com.hodanet.yuma.entity.po.YumaReceiver;
import com.hodanet.yuma.entity.po.YumaUser;
import com.hodanet.yuma.entity.po.YumaWeidianData;
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

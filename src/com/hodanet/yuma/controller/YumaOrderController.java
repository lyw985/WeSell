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
import com.hodanet.yuma.entity.po.YumaOrderItem;
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
@RequestMapping(value = "/yuma/order")
public class YumaOrderController {

	private final Logger logger = Logger.getLogger(YumaOrderController.class);

	private static final String LIST_PAGE = "yuma/order/list";
	private static final String INFO_PAGE = "yuma/order/info";
	private static final String IMPORT_PAGE = "yuma/order/import";

	@Autowired
	private YumaOrderService yumaOrderService;

	@Autowired
	private YumaWeidianDataService yumaWeidianDataService;

	/**
	 * б
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, PageData<YumaOrder> pageData, HttpServletRequest request) {
		YumaOrder yumaOrder = new YumaOrder();
		String userIdString = request.getParameter("user_id");
		if (userIdString != null) {
			YumaUser yumaUser = new YumaUser();
			yumaUser.setId(Integer.parseInt(userIdString));
			yumaOrder.setYumaUser(yumaUser);
			model.addAttribute("user_id", userIdString);
		}
		String recevierIdString = request.getParameter("receiver_id");
		if (recevierIdString != null) {
			YumaReceiver yumaReceiver = new YumaReceiver();
			yumaReceiver.setId(Integer.parseInt(recevierIdString));
			yumaOrder.setYumaReceiver(yumaReceiver);
			model.addAttribute("receiver_id", recevierIdString);
		}

		pageData = yumaOrderService.getYumaOrderByPage(pageData, yumaOrder);
		model.addAttribute("pageData", pageData);
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
	public String query(Model model, PageData<YumaOrder> pageData, HttpServletRequest request) {
		String receiverName = request.getParameter("receiverName");
//		String itemName = request.getParameter("itemName");
		String status = request.getParameter("status");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String provinceIdString = request.getParameter("province_id");
		String cityIdString = request.getParameter("city_id");
		String areaIdString = request.getParameter("area_id");
		YumaOrder yumaOrder = new YumaOrder();
		if (StringUtil.isNotBlank(status)) {
			yumaOrder.setStatus(Integer.parseInt(status));
		}
		try {
			if (StringUtil.isNotBlank(startDate)) {
				yumaOrder.setStartDate(DateConverterUtil.parse(startDate, DateConverterUtil.yyyy_MM_dd));
			}
			if (StringUtil.isNotBlank(endDate)) {
				yumaOrder.setEndDate(DateConverterUtil.parse(endDate, DateConverterUtil.yyyy_MM_dd));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		YumaReceiver yumaReceiver = new YumaReceiver();
		yumaReceiver.setName(receiverName);

		if (StringUtil.isNotBlank(areaIdString)) {
			Area area = new Area();
			area.setId(Integer.parseInt(areaIdString));
			yumaReceiver.setArea(area);
		} else {
			if (StringUtil.isNotBlank(cityIdString)) {
				City city = new City();
				city.setId(Integer.parseInt(cityIdString));
				yumaReceiver.setCity(city);
			} else {
				if (StringUtil.isNotBlank(provinceIdString)) {
					Province province = new Province();
					province.setId(Integer.parseInt(provinceIdString));
					yumaReceiver.setProvince(province);
				}
			}
		}
		yumaOrder.setYumaReceiver(yumaReceiver);

		pageData = yumaOrderService.getYumaOrderByPage(pageData, yumaOrder);
		model.addAttribute("receiverName", receiverName);
//		model.addAttribute("itemName", itemName);
		model.addAttribute("status", status);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("startDate", startDate);
		model.addAttribute("province_id", provinceIdString);
		model.addAttribute("city_id", cityIdString);
		model.addAttribute("area_id", areaIdString);
		return LIST_PAGE;
	}

	/**
	 * ҳ
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String gotoNew(Model model) {
		YumaOrder yumaOrder = new YumaOrder();
		model.addAttribute("yumaOrder", yumaOrder);
		return INFO_PAGE;
	}

	@RequestMapping(value = "/import", method = RequestMethod.GET)
	public String gotoimport(Model model) {
		return IMPORT_PAGE;
	}

	@RequestMapping(value = "/importFile", method = RequestMethod.POST)
	public void importFile(@RequestParam("weidianFile") CommonsMultipartFile weidianFile,
			HttpServletResponse response) {
		try {
			List<String[]> xlsDetailList = XlSUtil.readExcel(weidianFile.getInputStream(),
					weidianFile.getFileItem().getName());
			logger.info("===============一共有" + (xlsDetailList.size() - 1) + "条数据需要导入==============");
			if (xlsDetailList.size() > 1) {
				List<YumaWeidianData> yumaWeidianDataList = new ArrayList<YumaWeidianData>();
				// TODO 过滤一些不合格的数据
				for (int i = 1; i < xlsDetailList.size(); i++) {
					logger.debug("===============初始化第 " + i + "/" + (xlsDetailList.size() - 1) + "条数据==============");
					YumaWeidianData yumaWeidianData = YumaWeidianData
							.getYumaWeidianDataFromXlsData(xlsDetailList.get(0), xlsDetailList.get(i));
					logger.debug(yumaWeidianData);
					yumaWeidianDataList.add(yumaWeidianData);
				}
				logger.info("===============导入数据 " + yumaWeidianDataList.size() + " 条数据开始==============");
				yumaWeidianDataService.saveYumaWeidianDatas(
						yumaWeidianDataList.toArray(new YumaWeidianData[yumaWeidianDataList.size()]));
				logger.info("===============导入数据 " + yumaWeidianDataList.size() + " 条数据完成==============");
				WebUtil.responseText(response,
						JSONObject.toJSONString(new JsonMessage(true, "成功导入" + yumaWeidianDataList.size() + "条数据")));
			}
			WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(false, "没有任何数据可以导入")));
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(false, "导入失败，请稍候重试")));
		}

	}

	/**
	 * 
	 * 
	 * @param resHospital
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(Model model, HttpServletRequest request, HttpServletResponse response, YumaOrder yumaOrder) {
		yumaOrder = yumaOrderService.saveYumaOrder(yumaOrder);
		model.addAttribute("yumaOrder", yumaOrder);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "success")));
	}

	/**
	 * ޸
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public String modify(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("yumaOrder", yumaOrderService.getYumaOrderById(id));
		return INFO_PAGE;
	}

	/**
	 * ɾ
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletResponse response, @RequestParam("id") Integer[] ids) {
		yumaOrderService.deleteYumaOrder(ids);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "ɾɹ")));
	}

	/**
	 * ͨ
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	public void pass(HttpServletResponse response, @RequestParam("id") Integer id) {
		yumaOrderService.updateYumaOrderStatus(id, 1);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "ͨ˲ɹ")));
	}

	/**
	 * ͨ
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/refuse", method = RequestMethod.POST)
	public void refuse(HttpServletResponse response, @RequestParam("id") Integer id) {
		yumaOrderService.updateYumaOrderStatus(id, 2);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "ͨ˲ɹ")));
	}

}

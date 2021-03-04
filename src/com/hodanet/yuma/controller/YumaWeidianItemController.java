package com.hodanet.yuma.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.WebUtil;
import com.hodanet.yuma.constant.YumaCached;
import com.hodanet.yuma.entity.po.YumaWeidianItem;
import com.hodanet.yuma.entity.po.YumaWeidianItemModel;
import com.hodanet.yuma.entity.po.YumaWeidianItemModelMapping;
import com.hodanet.yuma.service.YumaWeidianItemModelMappingService;
import com.hodanet.yuma.service.YumaWeidianItemModelService;
import com.hodanet.yuma.service.YumaWeidianItemService;

/**
 * @anthor lyw
 * @yumaWeidianItem 2016-11-27 10:31:35
 */
@Controller
@RequestMapping(value = "/yuma/weidianItem")
public class YumaWeidianItemController {

	private final Logger logger = Logger.getLogger(YumaWeidianItemController.class);

	private static final String LIST_PAGE = "yuma/weidianItem/list";
	private static final String INFO_PAGE = "yuma/weidianItem/info";
	private static final String MAPPING_PAGE = "yuma/weidianItem/mapping";

	@Autowired
	private YumaWeidianItemService yumaWeidianItemService;

	@Autowired
	private YumaWeidianItemModelService yumaWeidianItemModelService;

	@Autowired
	private YumaWeidianItemModelMappingService yumaWeidianItemModelMappingService;

	/**
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, PageData<YumaWeidianItem> pageData) {
		long l = System.currentTimeMillis();

		YumaWeidianItem yumaWeidianItem = new YumaWeidianItem();

		yumaWeidianItem.setShowModels(true);
		yumaWeidianItem.setShowShadows(true);

		String weidianItemName = "请输入";
		yumaWeidianItem.setName(weidianItemName);

		String SHADOW = "0";
		String BODY = "1";
		String bodyType = BODY;
		if (SHADOW.equals(bodyType)) {
			yumaWeidianItem.setIsBody(false);
		}
		if (BODY.equals(bodyType)) {
			yumaWeidianItem.setIsBody(true);
		}

		String doneStatusStr = "0";
		if (StringUtils.isNotEmpty(doneStatusStr)) {
			Integer doneStatus = Integer.parseInt(doneStatusStr);
			yumaWeidianItem.setDoneStatus(doneStatus);
		}

		String mappingShowTypeStr = "0";
		if (StringUtils.isNotEmpty(mappingShowTypeStr)) {
			Integer mappingShowType = Integer.parseInt(mappingShowTypeStr);
			yumaWeidianItem.setMappingShowType(mappingShowType);
		}

		pageData = yumaWeidianItemService.getYumaWeidianItemByPage(pageData, yumaWeidianItem);
		logger.info(System.currentTimeMillis() - l);
		List<YumaWeidianItem> bodys = yumaWeidianItemService.getBodyYumaWeidianItems(yumaWeidianItem, 0);
		model.addAttribute("bodys", bodys);
		logger.info(System.currentTimeMillis() - l);
		model.addAttribute("pageData", pageData);
		model.addAttribute("bodyType", bodyType);
		model.addAttribute("doneStatus", doneStatusStr);
		model.addAttribute("mappingShowType", mappingShowTypeStr);
		model.addAttribute("weidianItemName", weidianItemName);
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
	public String query(Model model, PageData<YumaWeidianItem> pageData, HttpServletRequest request) {
		long l = System.currentTimeMillis();
		YumaWeidianItem yumaWeidianItem = new YumaWeidianItem();

		yumaWeidianItem.setShowModels(true);
		yumaWeidianItem.setShowShadows(true);

		String weidianItemName = request.getParameter("weidianItemName");
		yumaWeidianItem.setName(weidianItemName);

		String SHADOW = "0";
		String BODY = "1";
		String bodyType = request.getParameter("bodyType");
		if (SHADOW.equals(bodyType)) {
			yumaWeidianItem.setIsBody(false);
		}
		if (BODY.equals(bodyType)) {
			yumaWeidianItem.setIsBody(true);
		}

		String doneStatusStr = request.getParameter("doneStatus");
		if (StringUtils.isNotEmpty(doneStatusStr)) {
			Integer doneStatus = Integer.parseInt(doneStatusStr);
			yumaWeidianItem.setDoneStatus(doneStatus);
		}

		String mappingShowTypeStr = request.getParameter("mappingShowType");
		if (StringUtils.isNotEmpty(mappingShowTypeStr)) {
			Integer mappingShowType = Integer.parseInt(mappingShowTypeStr);
			yumaWeidianItem.setMappingShowType(mappingShowType);
		}

		pageData = yumaWeidianItemService.getYumaWeidianItemByPage(pageData, yumaWeidianItem);
		logger.info(System.currentTimeMillis() - l);

		model.addAttribute("weidianItemName", weidianItemName);
		model.addAttribute("pageData", pageData);
		List<YumaWeidianItem> bodys = yumaWeidianItemService.getBodyYumaWeidianItems(yumaWeidianItem, 0);
		model.addAttribute("bodys", bodys);
		model.addAttribute("bodyType", bodyType);
		model.addAttribute("doneStatus", doneStatusStr);
		model.addAttribute("mappingShowType", mappingShowTypeStr);
		return LIST_PAGE;
	}

	/**
	 * ҳ
	 * 
	 * @param model
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String gotoNew(Model model) {
		YumaWeidianItem yumaWeidianItem = new YumaWeidianItem();
		model.addAttribute("yumaWeidianItem", yumaWeidianItem);
		return INFO_PAGE;
	}

	/**
	 * 
	 * 
	 * @param resHospital
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(Model model, HttpServletRequest request, HttpServletResponse response,
			YumaWeidianItem yumaWeidianItem) {
		yumaWeidianItem = yumaWeidianItemService.saveYumaWeidianItem(yumaWeidianItem);
		model.addAttribute("yumaWeidianItem", yumaWeidianItem);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "成功")));
	}

	/**
	 * ޸
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public String modify(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("yumaWeidianItem", yumaWeidianItemService.getYumaWeidianItemById(id));
		return INFO_PAGE;
	}

	@RequestMapping(value = "/modifyMapping/{id}", method = RequestMethod.GET)
	public String modifyMapping(Model model, @PathVariable("id") Integer id) {
		YumaWeidianItemModelMapping yumaWeidianItemModelMapping = yumaWeidianItemModelMappingService
				.getYumaWeidianItemModelMappingById(id);
		model.addAttribute("yumaWeidianItemModelMapping", yumaWeidianItemModelMapping);
		model.addAttribute("item_id", yumaWeidianItemModelMapping.getYumaItemModel().getYumaItem().getId());
		model.addAttribute("item_model_id", yumaWeidianItemModelMapping.getYumaItemModel().getId());
		return MAPPING_PAGE;
	}

	@RequestMapping(value = "/addMapping/{id}", method = RequestMethod.GET)
	public String addMapping(Model model, @PathVariable("id") Integer id) {
		YumaWeidianItemModelMapping yumaWeidianItemModelMapping = new YumaWeidianItemModelMapping();
		YumaWeidianItemModel yumaWeidianItemModel = yumaWeidianItemModelService.getWeidianItemModelById(id, false);
		yumaWeidianItemModelMapping.setYumaWeidianItemModel(yumaWeidianItemModel);
		model.addAttribute("yumaWeidianItemModelMapping", yumaWeidianItemModelMapping);
		model.addAttribute("item_id", YumaCached.ADD_MAPPING_DEFAULT_ITEM_ID);
		return MAPPING_PAGE;
	}

	@RequestMapping(value = "/saveMapping", method = RequestMethod.POST)
	public void saveMapping(Model model, HttpServletRequest request, HttpServletResponse response,
			YumaWeidianItemModelMapping yumaWeidianItemModelMapping,
			@RequestParam("mapping_item_id") Integer mapping_item_id) {
		yumaWeidianItemModelMapping = yumaWeidianItemModelMappingService
				.saveYumaWeidianItemModelMapping(yumaWeidianItemModelMapping);
		yumaWeidianItemModelMappingService.updateYumaWeidianItemDetail(yumaWeidianItemModelMapping);
		yumaWeidianItemModelMappingService.updateYumaWeidianItemModelMappingPercent(
				yumaWeidianItemModelMapping.getYumaWeidianItemModel().getId());
		YumaCached.ADD_MAPPING_DEFAULT_ITEM_ID = mapping_item_id;
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "成功")));
	}

	@RequestMapping(value = "/deleteMapping", method = RequestMethod.POST)
	public void deleteMapping(HttpServletResponse response, @RequestParam("id") Integer id) {
		YumaWeidianItemModelMapping yumaWeidianItemModelMapping = yumaWeidianItemModelMappingService
				.getYumaWeidianItemModelMappingById(id);
		yumaWeidianItemModelMappingService.deleteYumaWeidianItemModelMapping(id);
		yumaWeidianItemModelMappingService.updateYumaWeidianItemDetail(yumaWeidianItemModelMapping);
		yumaWeidianItemModelMappingService.updateYumaWeidianItemModelMappingPercent(
				yumaWeidianItemModelMapping.getYumaWeidianItemModel().getId(), id);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "成功")));
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletResponse response, @RequestParam("id") Integer[] ids) {
		yumaWeidianItemService.deleteYumaWeidianItem(ids);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "删除成功")));
	}

	@RequestMapping(value = "/beBody", method = RequestMethod.POST)
	public void beBody(HttpServletResponse response, @RequestParam("id") Integer id) {
		yumaWeidianItemService.updateYumaWeidianItemByBody(id, 0);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "更新成功")));
	}

	@RequestMapping(value = "/beShadow", method = RequestMethod.POST)
	public void beShadow(HttpServletResponse response, @RequestParam("id") Integer id,
			@RequestParam("body_id") Integer bodyId) {
		yumaWeidianItemService.updateYumaWeidianItemByBody(id, bodyId);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "更新成功")));
	}
}

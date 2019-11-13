package com.hodanet.yuma.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.constant.MessageType;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.WebUtil;
import com.hodanet.yuma.constant.YumaItemStatus;
import com.hodanet.yuma.entity.po.YumaItem;
import com.hodanet.yuma.entity.po.YumaItemModel;
import com.hodanet.yuma.service.YumaItemModelService;
import com.hodanet.yuma.service.YumaItemService;

/**
 * @anthor lyw
 * @yumaItemModel 2016-11-11 10:31:35
 */
@Controller
@RequestMapping(value = "/yuma/itemModel")
public class YumaItemModelController {

	private static final String LIST_PAGE = "yuma/itemModel/list";
	private static final String INFO_PAGE = "yuma/itemModel/info";

	@Autowired
	private YumaItemService yumaItemService;

	@Autowired
	private YumaItemModelService yumaItemModelService;

	/**
	 * б
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, PageData<YumaItemModel> pageData, @RequestParam("item_id") Integer itemId) {
		YumaItemModel yumaItemModel = new YumaItemModel();
		YumaItem yumaItem = new YumaItem();
		yumaItem.setId(itemId);
		yumaItemModel.setYumaItem(yumaItem);
		pageData = yumaItemModelService.getYumaItemModelByPage(pageData, yumaItemModel);
		model.addAttribute("item_id", itemId);
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
	public String query(Model model, PageData<YumaItemModel> pageData, @RequestParam("name") String name,
			@RequestParam("status") Integer status, @RequestParam("item_id") Integer itemId) {
		YumaItemModel yumaItemModel = new YumaItemModel();
		YumaItem yumaItem = new YumaItem();
		yumaItem.setId(itemId);
		yumaItemModel.setYumaItem(yumaItem);
		yumaItemModel.setName(name);
		yumaItemModel.setStatus(status);
		pageData = yumaItemModelService.getYumaItemModelByPage(pageData, yumaItemModel);
		model.addAttribute("item_id", itemId);
		model.addAttribute("name", name);
		model.addAttribute("status", status);
		model.addAttribute("pageData", pageData);
		return LIST_PAGE;
	}

	/**
	 * ҳ
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String gotoNew(Model model, @RequestParam("item_id") Integer itemId) {
		YumaItemModel yumaItemModel = new YumaItemModel();
		YumaItem yumaItem = yumaItemService.getYumaItemById(itemId);
		yumaItemModel.setYumaItem(yumaItem);
		model.addAttribute("yumaItemModel", yumaItemModel);
//		model.addAttribute("addressList", CommonConstants.getAddressList());
		return INFO_PAGE;
	}

	/**
	 * 
	 * 
	 * @param resHospital
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(Model model, HttpServletRequest request, HttpServletResponse response,
			YumaItemModel yumaItemModel) {
		yumaItemModel = yumaItemModelService.saveYumaItemModel(yumaItemModel);
		model.addAttribute("yumaItemModel", yumaItemModel);
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, MessageType.SAVE_SUCCESS.toString())));
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
		model.addAttribute("yumaItemModel", yumaItemModelService.getYumaItemModelById(id));
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
		yumaItemModelService.deleteYumaItemModel(ids);
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, MessageType.DELETE_SUCCESS.toString())));
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/availableItemModel", method = RequestMethod.POST)
	public void availableItemModel(HttpServletResponse response, @RequestParam("id") Integer id) {
		yumaItemModelService.updateYumaItemModelStatus(id, YumaItemStatus.AVAILABLE.getValue());
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, MessageType.UPDATE_SUCCESS.toString())));
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/inavailableItemModel", method = RequestMethod.POST)
	public void inavailableItemModel(HttpServletResponse response, @RequestParam("id") Integer id) {
		yumaItemModelService.updateYumaItemModelStatus(id, YumaItemStatus.INAVAILABLE.getValue());
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, MessageType.UPDATE_SUCCESS.toString())));
	}

}

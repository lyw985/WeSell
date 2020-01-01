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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.WebUtil;
import com.hodanet.yuma.constant.YumaItemModelStatus;
import com.hodanet.yuma.constant.YumaItemStatus;
import com.hodanet.yuma.constant.YumaItemType;
import com.hodanet.yuma.entity.po.YumaItem;
import com.hodanet.yuma.entity.po.YumaItemModel;
import com.hodanet.yuma.entity.po.YumaWeidianItemModel;
import com.hodanet.yuma.entity.po.YumaWeidianItemModelMapping;
import com.hodanet.yuma.service.YumaItemModelService;
import com.hodanet.yuma.service.YumaItemService;

/**
 * @anthor lyw
 * @yumaItem 2016-11-27 10:31:35
 */
@Controller
@RequestMapping(value = "/yuma/item")
public class YumaItemController {

	private static final String LIST_PAGE = "yuma/item/list";
	private static final String INFO_PAGE = "yuma/item/info";

	@Autowired
	private YumaItemService yumaItemService;

	@Autowired
	private YumaItemModelService yumaItemModelService;

	@RequestMapping(value = "/getItems")
	public void getItems(Model model, HttpServletResponse response, HttpServletRequest request) {
		YumaItem yumaItem = new YumaItem();
		yumaItem.setStatus(YumaItemStatus.AVAILABLE.getValue());
		PageData<YumaItem> pageData = new PageData<YumaItem>();
		pageData.setPageSize(Integer.MAX_VALUE);
		long l = System.currentTimeMillis();
		pageData = yumaItemService.getYumaItemByPage(pageData, yumaItem);
		System.out.println(System.currentTimeMillis() - l);
		if (pageData != null && pageData.getData().size() > 0) {
			JSONArray array = new JSONArray();
			for (int i = 0; i < pageData.getData().size(); i++) {
				yumaItem = pageData.getData().get(i);
				JSONObject object = new JSONObject();
				object.put("id", yumaItem.getId());
				object.put("name", yumaItem.getName());
				object.put("type", YumaItemType.getYumaItemType(yumaItem.getType()).toString());
				array.add(object);
			}
			System.out.println(System.currentTimeMillis() - l);
			WebUtil.responseText(response, array.toJSONString());
		} else {
			WebUtil.responseText(response, "{}");
		}
	}

	@RequestMapping(value = "/getItemModels")
	public void getItemModels(Model model, HttpServletResponse response, HttpServletRequest request) {
		String itemIdString = request.getParameter("item_id");
		YumaItem yumaItem = new YumaItem();
		YumaItemModel yumaItemModel = new YumaItemModel();
		if (itemIdString != null) {
			yumaItem.setId(Integer.parseInt(itemIdString));
		} else {
			yumaItem.setId(0);
		}
		yumaItemModel.setYumaItem(yumaItem);
		yumaItemModel.setStatus(YumaItemModelStatus.AVAILABLE.getValue());
		PageData<YumaItemModel> pageData = new PageData<YumaItemModel>();
		pageData.setPageSize(Integer.MAX_VALUE);
		pageData = yumaItemModelService.getYumaItemModelByPage(pageData, yumaItemModel);
		if (pageData != null && pageData.getData().size() > 0) {
			JSONArray array = new JSONArray();
			for (int i = 0; i < pageData.getData().size(); i++) {
				yumaItemModel = pageData.getData().get(i);
				JSONObject object = new JSONObject();
				object.put("id", yumaItemModel.getId());
				object.put("name", yumaItemModel.getName());
				array.add(object);
			}
			WebUtil.responseText(response, array.toJSONString());
		} else {
			WebUtil.responseText(response, "{}");
		}
	}

	/**
	 * б
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, PageData<YumaItem> pageData) {
		YumaItem yumaItem = new YumaItem();
		pageData = yumaItemService.getYumaItemByPage(pageData, yumaItem);
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
	public String query(Model model, PageData<YumaItem> pageData, @RequestParam("name") String name,
			@RequestParam("status") Integer status) {
		YumaItem yumaItem = new YumaItem();
		yumaItem.setName(name);
		yumaItem.setStatus(status);
		pageData = yumaItemService.getYumaItemByPage(pageData, yumaItem);
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
	public String gotoNew(Model model) {
		YumaItem yumaItem = new YumaItem();
		model.addAttribute("yumaItem", yumaItem);
		return INFO_PAGE;
	}

	/**
	 * 
	 * 
	 * @param resHospital
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(Model model, HttpServletRequest request, HttpServletResponse response, YumaItem yumaItem) {
		yumaItem = yumaItemService.saveYumaItem(yumaItem);
		model.addAttribute("yumaItem", yumaItem);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "成功")));
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
		model.addAttribute("yumaItem", yumaItemService.getYumaItemById(id));
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
		yumaItemService.deleteYumaItem(ids);
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "成功")));
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/availableItem", method = RequestMethod.POST)
	public void availableItem(HttpServletResponse response, @RequestParam("id") Integer id) {
		yumaItemService.updateYumaItemStatus(id, YumaItemStatus.AVAILABLE.getValue());
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "成功修改")));
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/inavailableItem", method = RequestMethod.POST)
	public void inavailableItem(HttpServletResponse response, @RequestParam("id") Integer id) {
		yumaItemService.updateYumaItemStatus(id, YumaItemStatus.INAVAILABLE.getValue());
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "成功修改")));
	}
}

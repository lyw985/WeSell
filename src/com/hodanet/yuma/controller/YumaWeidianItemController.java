package com.hodanet.yuma.controller;

import java.util.List;

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
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.WebUtil;
import com.hodanet.yuma.entity.po.YumaWeidianItem;
import com.hodanet.yuma.service.YumaWeidianItemService;

/**
 * @anthor lyw
 * @yumaWeidianItem 2016-11-27 10:31:35
 */
@Controller
@RequestMapping(value = "/yuma/weidianItem")
public class YumaWeidianItemController {

	private static final String LIST_PAGE = "yuma/weidianItem/list";
	private static final String INFO_PAGE = "yuma/weidianItem/info";

	@Autowired
	private YumaWeidianItemService yumaWeidianItemService;

	/**
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, PageData<YumaWeidianItem> pageData) {
		YumaWeidianItem yumaWeidianItem = new YumaWeidianItem();
		pageData = yumaWeidianItemService.getYumaWeidianItemByPage(pageData, yumaWeidianItem);
		List<YumaWeidianItem> bodys = yumaWeidianItemService.getBodyYumaWeidianItems(yumaWeidianItem);
		model.addAttribute("pageData", pageData);
		model.addAttribute("bodys", bodys);
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
		String weidianItemName = request.getParameter("weidianItemName");
		YumaWeidianItem yumaWeidianItem = new YumaWeidianItem();
		yumaWeidianItem.setName(weidianItemName);
		pageData = yumaWeidianItemService.getYumaWeidianItemByPage(pageData, yumaWeidianItem);
		List<YumaWeidianItem> bodys = yumaWeidianItemService.getBodyYumaWeidianItems(yumaWeidianItem);
		model.addAttribute("weidianItemName", weidianItemName);
		model.addAttribute("pageData", pageData);
		model.addAttribute("bodys", bodys);
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
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public String modify(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("yumaWeidianItem", yumaWeidianItemService.getYumaWeidianItemById(id));
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

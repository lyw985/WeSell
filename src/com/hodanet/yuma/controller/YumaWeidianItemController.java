package com.hodanet.yuma.controller;

import java.util.List;
import java.util.Set;

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
		pageData = yumaWeidianItemService.getYumaWeidianItemByPage(pageData, yumaWeidianItem);
		System.out.println(System.currentTimeMillis() - l);
		List<YumaWeidianItem> bodys = yumaWeidianItemService.getBodyYumaWeidianItems(yumaWeidianItem);
		System.out.println(System.currentTimeMillis() - l);
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
		String SHADOW = "0";
		String BODY = "1";
		String BODY_WITH_SHADOW = "11";
		String BODY_WITHOUT_SHADOW = "12";
		String bodyType = request.getParameter("bodyType");
		String weidianItemName = request.getParameter("weidianItemName");
		String MAPPING_NOT_DONE = "0";
		String MAPPING_DONE = "1";
		String mappingType = request.getParameter("mappingType");

		String MAPPING_SHOW_ONE_MATCH = "1";
		String MAPPING_SHOW_MANY_MATCH = "2";
		String MAPPING_SHOW_NO_MATCH = "3";
		String mappingShowType = request.getParameter("mappingShowType");

		YumaWeidianItem yumaWeidianItem = new YumaWeidianItem();
		yumaWeidianItem.setName(weidianItemName);
		if (SHADOW.equals(bodyType)) {
			yumaWeidianItem.setIsBody(false);
		}
		if (BODY.equals(bodyType) || BODY_WITH_SHADOW.equals(bodyType) || BODY_WITHOUT_SHADOW.equals(bodyType)) {
			yumaWeidianItem.setIsBody(true);
		}
		if (BODY_WITH_SHADOW.equals(bodyType) || BODY_WITHOUT_SHADOW.equals(bodyType)
				|| MAPPING_NOT_DONE.equals(mappingType) || MAPPING_DONE.equals(mappingType)) {
			pageData.setPageSize(Integer.MAX_VALUE);
		}
		pageData = yumaWeidianItemService.getYumaWeidianItemByPage(pageData, yumaWeidianItem);
		if (pageData.getData().size() > 0) {
			List<YumaWeidianItem> yumaWeidianItems = pageData.getData();
			for (int i = yumaWeidianItems.size() - 1; i >= 0; i--) {
				YumaWeidianItem p = yumaWeidianItems.get(i);
				if (BODY_WITH_SHADOW.equals(bodyType) && p.getShadows().size() == 0) {
					yumaWeidianItems.remove(i);
					continue;
				}
				if (BODY_WITHOUT_SHADOW.equals(bodyType) && p.getShadows().size() > 0) {
					yumaWeidianItems.remove(i);
					continue;
				}
				if (MAPPING_NOT_DONE.equals(mappingType) || MAPPING_DONE.equals(mappingType)) {
					List<YumaWeidianItemModel> list = p.getYumaWeidianItemModels();
					boolean isDone = true;
					if (list != null) {
						for (YumaWeidianItemModel yumaWeidianItemModel : list) {
							if (yumaWeidianItemModel.getYumaWeidianItemModelMappings() == null
									|| yumaWeidianItemModel.getYumaWeidianItemModelMappings().size() == 0) {
								isDone = false;
								break;
							}
						}
					}
					if (MAPPING_NOT_DONE.equals(mappingType) && isDone) {
						yumaWeidianItems.remove(i);
					}
					if (MAPPING_DONE.equals(mappingType) && !isDone) {
						yumaWeidianItems.remove(i);
					}
				}
			}
			if (MAPPING_SHOW_ONE_MATCH.equals(mappingShowType) || MAPPING_SHOW_MANY_MATCH.equals(mappingShowType)
					|| MAPPING_SHOW_NO_MATCH.equals(mappingShowType)) {
				for (int i = yumaWeidianItems.size() - 1; i >= 0; i--) {
					YumaWeidianItem p = yumaWeidianItems.get(i);
					List<YumaWeidianItemModel> list = p.getYumaWeidianItemModels();
					if (list != null) {
						for (int j = list.size() - 1; j >= 0; j--) {
							YumaWeidianItemModel model2 = list.get(j);
							Set<YumaWeidianItemModelMapping> set = model2.getYumaWeidianItemModelMappings();
							if (MAPPING_SHOW_ONE_MATCH.equals(mappingShowType) && set.size() != 1) {
								list.remove(j);
							}
							if (MAPPING_SHOW_MANY_MATCH.equals(mappingShowType) && set.size() <= 1) {
								list.remove(j);
							}
							if (MAPPING_SHOW_NO_MATCH.equals(mappingShowType) && set.size() > 0) {
								list.remove(j);
							}
						}
					}
				}
			}
			if (BODY_WITH_SHADOW.equals(bodyType) || BODY_WITHOUT_SHADOW.equals(bodyType)
					|| MAPPING_NOT_DONE.equals(mappingType) || MAPPING_DONE.equals(mappingType)) {
				pageData.setTotal(yumaWeidianItems.size());
			}
		}
		List<YumaWeidianItem> bodys = yumaWeidianItemService.getBodyYumaWeidianItems(yumaWeidianItem);
		model.addAttribute("weidianItemName", weidianItemName);
		model.addAttribute("pageData", pageData);
		model.addAttribute("bodys", bodys);
		model.addAttribute("bodyType", bodyType);
		model.addAttribute("mappingType", mappingType);
		model.addAttribute("mappingShowType", mappingShowType);
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
		YumaWeidianItemModel yumaWeidianItemModel = yumaWeidianItemModelService.getWeidianItemModelById(id);
		yumaWeidianItemModelMapping.setYumaWeidianItemModel(yumaWeidianItemModel);
		model.addAttribute("yumaWeidianItemModelMapping", yumaWeidianItemModelMapping);
		return MAPPING_PAGE;
	}

	@RequestMapping(value = "/saveMapping", method = RequestMethod.POST)
	public void saveMapping(Model model, HttpServletRequest request, HttpServletResponse response,
			YumaWeidianItemModelMapping yumaWeidianItemModelMapping) {
		yumaWeidianItemModelMapping = yumaWeidianItemModelMappingService
				.saveYumaWeidianItemModelMapping(yumaWeidianItemModelMapping);
		yumaWeidianItemModelMappingService.updateYumaWeidianItemModelMappingPercent(
				yumaWeidianItemModelMapping.getYumaWeidianItemModel().getId());
		WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "成功")));
	}

	@RequestMapping(value = "/deleteMapping", method = RequestMethod.POST)
	public void deleteMapping(HttpServletResponse response, @RequestParam("id") Integer id) {
		YumaWeidianItemModelMapping yumaWeidianItemModelMapping = yumaWeidianItemModelMappingService
				.getYumaWeidianItemModelMappingById(id);
		yumaWeidianItemModelMappingService.deleteYumaWeidianItemModelMapping(id);
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

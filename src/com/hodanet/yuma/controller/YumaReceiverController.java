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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.WebUtil;
import com.hodanet.yuma.entity.po.YumaReceiver;
import com.hodanet.yuma.entity.po.YumaUser;
import com.hodanet.yuma.service.YumaReceiverService;
import com.hodanet.yuma.service.YumaUserService;

/**
 * @anthor lyw
 * @yumaReceiver 2016-11-11 10:31:35
 */
@Controller
@RequestMapping(value = "/yuma/receiver")
public class YumaReceiverController {

	private static final String LIST_PAGE = "yuma/receiver/list";
	private static final String INFO_PAGE = "yuma/receiver/info";

	@Autowired
	private YumaReceiverService yumaReceiverService;

	@Autowired
	private YumaUserService yumaUserService;

	/**
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, PageData<YumaReceiver> pageData,
			@RequestParam("user_id") Integer userId) {
		YumaReceiver yumaReceiver = new YumaReceiver();
		YumaUser yumaUser = new YumaUser();
		yumaUser.setId(userId);
		yumaReceiver.setYumaUser(yumaUser);
		pageData = yumaReceiverService.getYumaReceiverByPage(pageData,
				yumaReceiver);
		model.addAttribute("user_id", userId);
		model.addAttribute("pageData", pageData);
		return LIST_PAGE;
	}

	/**
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/query")
	public String query(Model model, PageData<YumaReceiver> pageData,
			@RequestParam("searchName") String searchName,
			@RequestParam("status") Integer status,
			@RequestParam("user_id") Integer userId) {
		YumaReceiver yumaReceiver = new YumaReceiver();
		YumaUser yumaUser = new YumaUser();
		yumaUser.setId(userId);
		yumaReceiver.setYumaUser(yumaUser);
		yumaReceiver.setStatus(status);
		pageData = yumaReceiverService.getYumaReceiverByPage(pageData,
				yumaReceiver);
		model.addAttribute("user_id", userId);
		model.addAttribute("searchName", searchName);
		model.addAttribute("status", status);
		model.addAttribute("pageData", pageData);
		return LIST_PAGE;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String gotoNew(Model model, @RequestParam("user_id") Integer userId) {
		YumaReceiver yumaReceiver = new YumaReceiver();
		YumaUser yumaUser = yumaUserService.getYumaUserById(userId);
		yumaReceiver.setYumaUser(yumaUser);
		model.addAttribute("yumaReceiver", yumaReceiver);
//		model.addAttribute("addressList", CommonConstants.getAddressList());
		return INFO_PAGE;
	}

	/**
	 * 
	 * @param resHospital
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(Model model, HttpServletRequest request,
			HttpServletResponse response, YumaReceiver yumaReceiver) {
		yumaReceiver = yumaReceiverService.saveYumaReceiver(yumaReceiver);
		model.addAttribute("yumaReceiver", yumaReceiver);
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, "ɹ")));
	}

	/**
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public String modify(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("yumaReceiver",
				yumaReceiverService.getYumaReceiverById(id));
		return INFO_PAGE;
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletResponse response,
			@RequestParam("id") Integer[] ids) {
		yumaReceiverService.deleteYumaReceiver(ids);
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, "ɾɹ")));
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/setDefaultAddress", method = RequestMethod.POST)
	public void setDefaultAddress(HttpServletResponse response,
			@RequestParam("id") Integer id) {
		yumaReceiverService.updateYumaReceiverDefaultAddress(id, 1);
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, "ΪĬջ˳ɹ")));
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/unsetDefaultAddress", method = RequestMethod.POST)
	public void unsetDefaultAddress(HttpServletResponse response,
			@RequestParam("id") Integer id) {
		yumaReceiverService.updateYumaReceiverDefaultAddress(id, 0);
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, "ȡĬջ˳ɹ")));
	}


	@RequestMapping(value = "/searchReceiver", method = RequestMethod.POST)
	public void searchReceiver(HttpServletResponse response,
			@RequestParam("searchValue") String searchValue) {
		JSONObject jsonObject = new JSONObject();
		try {
			PageData<YumaReceiver> pageData = new PageData<YumaReceiver>();
			pageData.setPageSize(Integer.MAX_VALUE);
			pageData = yumaReceiverService.getYumaReceiverBySearchValue(
					pageData, searchValue);
			List<YumaReceiver> list = pageData.getData();
			JSONArray jsonArray = new JSONArray();

			for (int i = 0; i < list.size(); i++) {
				YumaReceiver yumaReceiver = list.get(i);
				JSONObject object = new JSONObject();
				object.put("id", yumaReceiver.getId());
				object.put("username", yumaReceiver.getYumaUser().getName());
				object.put("usernickname", yumaReceiver.getYumaUser()
						.getNickname());
				object.put("name", yumaReceiver.getName());
				object.put("phone", yumaReceiver.getPhone());
				object.put("detail",yumaReceiver.getDetail());
				jsonArray.add(object);
			}
			jsonObject.put("data", jsonArray);
			jsonObject.put("error", 0);
		} catch (Exception e) {
			jsonObject.put("error", 1);
			jsonObject.put("error_msg", "ϵͳԺ");
		}
		WebUtil.responseText(response, jsonObject.toJSONString());
	}
}

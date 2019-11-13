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
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.WebUtil;
import com.hodanet.yuma.entity.po.YumaUser;
import com.hodanet.yuma.service.YumaUserService;

/**
 * @anthor lyw
 * @yumaUser 2016-11-11 10:31:35
 */
@Controller
@RequestMapping(value = "/yuma/user")
public class YumaUserController {

	private static final String LIST_PAGE = "yuma/user/list";
	private static final String INFO_PAGE = "yuma/user/info";

	@Autowired
	private YumaUserService yumaUserService;

	/**
	 * б
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Model model, PageData<YumaUser> pageData) {
		YumaUser yumaUser = new YumaUser();
		pageData = yumaUserService.getYumaUserByPage(pageData, yumaUser);
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
	public String query(Model model, PageData<YumaUser> pageData,
			@RequestParam("name") String name,
			@RequestParam("phone") String phone,
			@RequestParam("status") Integer status) {
		YumaUser yumaUser = new YumaUser();
		yumaUser.setName(name);
		yumaUser.setPhone(phone);
		yumaUser.setStatus(status);
		pageData = yumaUserService.getYumaUserByPage(pageData, yumaUser);
		model.addAttribute("name", name);
		model.addAttribute("phone", phone);
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
		YumaUser yumaUser = new YumaUser();
		model.addAttribute("yumaUser", yumaUser);
		return INFO_PAGE;
	}

	/**
	 * 
	 * 
	 * @param resHospital
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(Model model, HttpServletRequest request,
			HttpServletResponse response, YumaUser yumaUser) {
		yumaUser = yumaUserService.saveYumaUser(yumaUser);
		model.addAttribute("yumaUser", yumaUser);
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, "ɹ")));
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
		model.addAttribute("yumaUser", yumaUserService.getYumaUserById(id));
		return INFO_PAGE;
	}

	/**
	 * ɾ
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletResponse response,
			@RequestParam("id") Integer[] ids) {
		yumaUserService.deleteYumaUser(ids);
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, "ɾɹ")));
	}

	/**
	 * ͨ
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	public void pass(HttpServletResponse response,
			@RequestParam("id") Integer id) {
		yumaUserService.updateYumaUserStatus(id, 1);
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, "ͨ˲ɹ")));
	}

	/**
	 * ͨ
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/refuse", method = RequestMethod.POST)
	public void refuse(HttpServletResponse response,
			@RequestParam("id") Integer id) {
		yumaUserService.updateYumaUserStatus(id, 2);
		WebUtil.responseText(response,
				JSONObject.toJSONString(new JsonMessage(true, "ͨ˲ɹ")));
	}
}

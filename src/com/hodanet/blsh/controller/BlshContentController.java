package com.hodanet.blsh.controller;

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
import com.hodanet.blsh.constant.BlshContentType;
import com.hodanet.blsh.entity.po.BlshContent;
import com.hodanet.blsh.service.BlshContentService;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.WebUtil;
import com.hodanet.system.constant.PermissionConstants;
import com.hodanet.system.entity.po.User;

/**
 * @anthor lyw
 * @blshContent 2013-7-9 3:04:35
 */
@Controller
@RequestMapping(value = "/blsh/content")
public class BlshContentController {

    private static final String APP_LIST_PAGE = "blsh/content/list";
    private static final String APP_INFO_PAGE = "blsh/content/info";

    @Autowired
    private BlshContentService  blshContentService;

    /**
     * б
     * 
     * @param model
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, PageData<BlshContent> pageData) {
        BlshContent blshContent = new BlshContent();
        pageData = blshContentService.getBlshContentByPage(pageData, blshContent);
        model.addAttribute("pageData", pageData);
        model.addAttribute("blshContentTypes", BlshContentType.values());
        return APP_LIST_PAGE;
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
    public String query(Model model, PageData<BlshContent> pageData, @RequestParam("searchName")
    String searchName, @RequestParam("status")
    Integer status, @RequestParam("type")
    Integer type, @RequestParam("msgType")
    Integer msgType) {
        BlshContent blshContent = new BlshContent();
        blshContent.setContent(searchName);
        blshContent.setStatus(status);
        blshContent.setMsgType(msgType);
        blshContent.setType(type);
        pageData = blshContentService.getBlshContentByPage(pageData, blshContent);
        model.addAttribute("blshContentTypes", BlshContentType.values());
        model.addAttribute("searchName", searchName);
        model.addAttribute("status", status);
        model.addAttribute("type", type);
        model.addAttribute("msgType", msgType);
        model.addAttribute("pageData", pageData);
        return APP_LIST_PAGE;
    }

    /**
     * ҳ
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String gotoNew(Model model) {
        BlshContent blshContent = new BlshContent();
        model.addAttribute("blshContent", blshContent);
        model.addAttribute("blshContentTypes", BlshContentType.values());
        return APP_INFO_PAGE;
    }

    /**
     * 
     * 
     * @param resHospital
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(Model model, HttpServletRequest request, HttpServletResponse response, BlshContent blshContent) {
        User user = (User) request.getSession().getAttribute(PermissionConstants.CONSTANT_PARAM_USER);
        blshContent.setUser(user);
        blshContent = blshContentService.saveBlshContent(blshContent);
        model.addAttribute("blshContent", blshContent);
        WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "ɹ")));
    }

    /**
     * ޸
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String modify(Model model, @PathVariable("id")
    Integer id) {
        model.addAttribute("blshContent", blshContentService.getBlshContentById(id));
        model.addAttribute("blshContentTypes", BlshContentType.values());
        return APP_INFO_PAGE;
    }

    /**
     * ɾ
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(HttpServletResponse response, @RequestParam("id")
    Integer[] ids) {
        blshContentService.deleteBlshContent(ids);
        WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "ɾɹ")));
    }

    /**
     * ͨ
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public void pass(HttpServletResponse response, @RequestParam("id")
    Integer id) {
        blshContentService.updateBlshContentStatus(id, 1);
        WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "ͨ˲ɹ")));
    }

    /**
     * ͨ
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    public void refuse(HttpServletResponse response, @RequestParam("id")
    Integer id) {
        blshContentService.updateBlshContentStatus(id, 2);
        WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "ͨ˲ɹ")));
    }
}

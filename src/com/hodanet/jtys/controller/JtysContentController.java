package com.hodanet.jtys.controller;

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
import com.hodanet.jtys.constant.JtysContentType;
import com.hodanet.jtys.entity.po.JtysContent;
import com.hodanet.jtys.service.JtysContentService;
import com.hodanet.system.constant.PermissionConstants;
import com.hodanet.system.entity.po.User;

/**
 * @anthor lyw
 * @jtysContent 2013-7-9 3:04:35
 */
@Controller
@RequestMapping(value = "/jtys/content")
public class JtysContentController {

    private static final String APP_LIST_PAGE = "jtys/content/list";
    private static final String APP_INFO_PAGE = "jtys/content/info";

    @Autowired
    private JtysContentService  jtysContentService;

    /**
     * б
     * 
     * @param model
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, PageData<JtysContent> pageData) {
        JtysContent jtysContent = new JtysContent();
        pageData = jtysContentService.getJtysContentByPage(pageData, jtysContent);
        model.addAttribute("pageData", pageData);
        model.addAttribute("jtysContentTypes", JtysContentType.values());
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
    public String query(Model model, PageData<JtysContent> pageData, @RequestParam("searchName")
    String searchName, @RequestParam("status")
    Integer status, @RequestParam("type")
    Integer type, @RequestParam("msgType")
    Integer msgType) {
        JtysContent jtysContent = new JtysContent();
        jtysContent.setContent(searchName);
        jtysContent.setStatus(status);
        jtysContent.setMsgType(msgType);
        jtysContent.setType(type);
        pageData = jtysContentService.getJtysContentByPage(pageData, jtysContent);
        model.addAttribute("jtysContentTypes", JtysContentType.values());
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
        JtysContent jtysContent = new JtysContent();
        model.addAttribute("jtysContent", jtysContent);
        model.addAttribute("jtysContentTypes", JtysContentType.values());
        return APP_INFO_PAGE;
    }

    /**
     * 
     * 
     * @param resHospital
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(Model model, HttpServletRequest request, HttpServletResponse response, JtysContent jtysContent) {
        User user = (User) request.getSession().getAttribute(PermissionConstants.CONSTANT_PARAM_USER);
        jtysContent.setUser(user);
        jtysContent = jtysContentService.saveJtysContent(jtysContent);
        model.addAttribute("jtysContent", jtysContent);
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
        model.addAttribute("jtysContent", jtysContentService.getJtysContentById(id));
        model.addAttribute("jtysContentTypes", JtysContentType.values());
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
        jtysContentService.deleteJtysContent(ids);
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
        jtysContentService.updateJtysContentStatus(id, 1);
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
        jtysContentService.updateJtysContentStatus(id, 2);
        WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "ͨ˲ɹ")));
    }
}

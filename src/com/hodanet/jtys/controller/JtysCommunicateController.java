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
import com.hodanet.jtys.entity.po.JtysCommunicate;
import com.hodanet.jtys.service.JtysCommunicateService;

/**
 * @anthor lyw
 * @jtysCommunicate 2013-7-9 3:04:35
 */
@Controller
@RequestMapping(value = "/jtys/communicate")
public class JtysCommunicateController {

    private static final String    LIST_PAGE = "jtys/communicate/list";
    private static final String    INFO_PAGE = "jtys/communicate/info";

    @Autowired
    private JtysCommunicateService jtysCommunicateService;

    /**
     * б
     * 
     * @param model
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, PageData<JtysCommunicate> pageData) {
        JtysCommunicate jtysCommunicate = new JtysCommunicate();
        pageData = jtysCommunicateService.getJtysCommunicateByPage(pageData, jtysCommunicate);
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
    public String query(Model model, PageData<JtysCommunicate> pageData, @RequestParam("searchName")
    String searchName, @RequestParam("status")
    Integer status) {
        JtysCommunicate jtysCommunicate = new JtysCommunicate();
        jtysCommunicate.setStatus(status);
        pageData = jtysCommunicateService.getJtysCommunicateByPage(pageData, jtysCommunicate);
        model.addAttribute("searchName", searchName);
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
        JtysCommunicate jtysCommunicate = new JtysCommunicate();
        model.addAttribute("jtysCommunicate", jtysCommunicate);
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
                     JtysCommunicate jtysCommunicate) {
        jtysCommunicate = jtysCommunicateService.saveJtysCommunicate(jtysCommunicate);
        model.addAttribute("jtysCommunicate", jtysCommunicate);
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
        model.addAttribute("jtysCommunicate", jtysCommunicateService.getJtysCommunicateById(id));
        return INFO_PAGE;
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
        jtysCommunicateService.deleteJtysCommunicate(ids);
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
        jtysCommunicateService.updateJtysCommunicateStatus(id, 1);
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
        jtysCommunicateService.updateJtysCommunicateStatus(id, 2);
        WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "ͨ˲ɹ")));
    }
}

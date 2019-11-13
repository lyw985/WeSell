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
import com.hodanet.jtys.entity.po.JtysUser;
import com.hodanet.jtys.service.JtysUserService;

/**
 * @anthor lyw
 * @jtysUser 2013-7-9 3:04:35
 */
@Controller
@RequestMapping(value = "/jtys/user")
public class JtysUserController {

    private static final String LIST_PAGE = "jtys/user/list";
    private static final String INFO_PAGE = "jtys/user/info";

    @Autowired
    private JtysUserService     jtysUserService;

    /**
     * б
     * 
     * @param model
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, PageData<JtysUser> pageData) {
        JtysUser jtysUser = new JtysUser();
        pageData = jtysUserService.getJtysUserByPage(pageData, jtysUser);
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
    public String query(Model model, PageData<JtysUser> pageData, @RequestParam("searchName")
    String searchName, @RequestParam("status")
    Integer status) {
        JtysUser jtysUser = new JtysUser();
        jtysUser.setStatus(status);
        pageData = jtysUserService.getJtysUserByPage(pageData, jtysUser);
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
        JtysUser jtysUser = new JtysUser();
        model.addAttribute("jtysUser", jtysUser);
        return INFO_PAGE;
    }

    /**
     * 
     * 
     * @param resHospital
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(Model model, HttpServletRequest request, HttpServletResponse response, JtysUser jtysUser) {
        jtysUser = jtysUserService.saveJtysUser(jtysUser);
        model.addAttribute("jtysUser", jtysUser);
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
        model.addAttribute("jtysUser", jtysUserService.getJtysUserById(id));
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
        jtysUserService.deleteJtysUser(ids);
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
        jtysUserService.updateJtysUserStatus(id, 1);
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
        jtysUserService.updateJtysUserStatus(id, 2);
        WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "ͨ˲ɹ")));
    }
}

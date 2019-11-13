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
import com.hodanet.yuma.constant.YumaItemStatus;
import com.hodanet.yuma.entity.po.YumaItem;
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
    private YumaItemService     yumaItemService;

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
    public String query(Model model, PageData<YumaItem> pageData, @RequestParam("name")
    String name, @RequestParam("status")
    Integer status) {
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
    public String modify(Model model, @PathVariable("id")
    Integer id) {
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
    public void delete(HttpServletResponse response, @RequestParam("id")
    Integer[] ids) {
        yumaItemService.deleteYumaItem(ids);
        WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "成功")));
    }

    /**
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/availableItem", method = RequestMethod.POST)
    public void availableItem(HttpServletResponse response, @RequestParam("id")
    Integer id) {
        yumaItemService.updateYumaItemStatus(id, YumaItemStatus.AVAILABLE.getValue());
        WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "成功修改")));
    }

    /**
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/inavailableItem", method = RequestMethod.POST)
    public void inavailableItem(HttpServletResponse response, @RequestParam("id")
    Integer id) {
        yumaItemService.updateYumaItemStatus(id, YumaItemStatus.INAVAILABLE.getValue());
        WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "成功修改")));
    }
}

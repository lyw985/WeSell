package com.hodanet.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.entity.po.Menu;
import com.hodanet.system.service.MenuService;

/**
 * @author lance.lengcs
 * @version 2012-8-7 9:45:39
 * 
 * <pre>
 * ˵
 * </pre>
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController {

    private static final String MENU_LIST_PAGE = "system/menu/list";
    private static final String MENU_INFO_PAGE = "system/menu/info";

    @Autowired
    private MenuService         menuService;

    /**
     * ѯв˵
     * 
     * @param model
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, PageData<Menu> pageData) {

        pageData = menuService.getAllMenuByPage(pageData, null);

        model.addAttribute("pageData", pageData);

        return MENU_LIST_PAGE;
    }

    @RequestMapping(value = "/query")
    public String query(Model model, PageData<Menu> pageData, @RequestParam("searchName")
    String searchName) {

        pageData = menuService.getAllMenuByPage(pageData, searchName);

        model.addAttribute("pageData", pageData);
        model.addAttribute("searchName", searchName);

        return MENU_LIST_PAGE;
    }

    /**
     * ˵ҳ
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String gotoNew(Model model) {

        Menu menu = new Menu();
        model.addAttribute("menu", menu);
        return MENU_INFO_PAGE;
    }

    /**
     * ˵
     * 
     * @param menu
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage save(Model model, Menu menu, HttpServletRequest request) {

        if (menu == null) {
            return new JsonMessage(false, "ʧ");
        }

        if (StringUtil.isBlank(menu.getId())) {
            menu.setId(null);
        }

        menu = menuService.saveMenu(menu);
        model.addAttribute("menu", menu);

        return new JsonMessage(true, "ɹ");
    }

    /**
     * ˵޸
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String modify(Model model, @PathVariable("id")
    String id) {

        model.addAttribute("menu", menuService.getMenuById(id));

        return MENU_INFO_PAGE;
    }

    /**
     * ɾ˵
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage delete(@RequestParam("id")
    String[] ids) {

        menuService.deleteMenus(ids);
        return new JsonMessage(true, "ɾɹ");
    }
}

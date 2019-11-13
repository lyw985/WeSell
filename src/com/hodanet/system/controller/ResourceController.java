package com.hodanet.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.entity.po.Menu;
import com.hodanet.system.entity.po.Module;
import com.hodanet.system.service.MenuService;
import com.hodanet.system.service.ModuleService;

/**
 * @author lance.lengcs
 * @version 2012-8-12 12:16:49
 * 
 * <pre>
 * Դ
 * </pre>
 */
@Controller
@RequestMapping(value = "/resource")
public class ResourceController {

    private static final String RESOURCE_MANAGE_PAGE      = "system/resource/manage";
    private static final String RESOURCE_MODULE_INFO_PAGE = "system/resource/moduleInfo";
    private static final String RESOURCE_MENU_INFO_PAGE   = "system/resource/menuInfo";

    @Autowired
    private MenuService         menuService;
    @Autowired
    private ModuleService       moduleService;

    /**
     * ˵Ĭҳ.
     * 
     * @return .
     */
    @RequestMapping
    public String home() {
        return RESOURCE_MANAGE_PAGE;
    }

    /**
     * ȡԴ¼Դ.
     * 
     * @param code Դ.
     * @param id ԴID.
     * @param type Դ.
     * @return .
     */
    @RequestMapping(value = "/childResource")
    @ResponseBody
    public List<JsTreeNode> getChildResource(@RequestParam("moduleId")
    String moduleId, @RequestParam("id")
    String id, @RequestParam("t")
    String type) {
        List<JsTreeNode> jsTreeNodes = menuService.getResourceTree(moduleId, id, type);

        return jsTreeNodes;
    }

    /**
     * ȡϵͳĲ˵.
     * 
     * @param moduleCode ϵͳid.
     * @return .
     */
    @RequestMapping(value = "/menuResource/{moduleId}")
    @ResponseBody
    public List<JsTreeNode> getMenuResourceByModule(@PathVariable("moduleId")
    String moduleId) {
        List<JsTreeNode> jsTreeNodes = menuService.getMenuResourceByModule(moduleId);
        return jsTreeNodes;
    }

    /**
     * ϵͳ½ҳ.
     * 
     * @param model .
     * @return .
     */
    @RequestMapping(value = "/module/add", method = RequestMethod.GET)
    public String toAddModule(Model model) {
        model.addAttribute("module", new Module());
        return RESOURCE_MODULE_INFO_PAGE;
    }

    /**
     * ϵͳ޸ҳ.
     * 
     * @param model .
     * @param id ϵͳID
     * @return .
     */
    @RequestMapping(value = "/module/modify/{id}", method = RequestMethod.GET)
    public String modifyModule(Model model, @PathVariable("id")
    String id) {
        model.addAttribute("module", moduleService.getModuleById(id));
        return RESOURCE_MODULE_INFO_PAGE;
    }

    /**
     * ϵͳ.
     * 
     * @param module ϵͳ
     * @return .
     */
    @RequestMapping(value = "/module/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage saveModule(Module module) {
        moduleService.saveModule(module);
        return new JsonMessage(true, "ϵͳɹ!");
    }

    /**
     * ɾϵͳ.
     * 
     * @param model .
     * @param id ϵͳID
     * @return .
     */
    @RequestMapping(value = "/module/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage deleteModule(Model model, @RequestParam("id")
    String id) {
        moduleService.deleteModules(new String[] { id });
        return new JsonMessage(true, "ɾϵͳɹ!");
    }

    /**
     * ϵͳǷظ.
     * 
     * @param id .
     * @param code .
     * @return .
     */
    @RequestMapping(value = "/module/check", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage checkModule(@RequestParam("id")
    String id, @RequestParam("code")
    String code) {
        Module module = moduleService.getModuleByCode(code);
        if (module != null && !StringUtil.equals(module.getId(), id)) {
            return new JsonMessage(false, "ϵͳظ!");
        }
        return new JsonMessage(true, "ϵͳ벻ظ!");
    }

    /**
     * ˵½ҳ.
     * 
     * @param model .
     * @return .
     */
    @RequestMapping(value = "/menu/add", method = RequestMethod.GET)
    public String toAddMenu(Model model) {
        model.addAttribute("menu", new Menu());
        model.addAttribute("modules", moduleService.getAllModuleList());
        return RESOURCE_MENU_INFO_PAGE;
    }

    /**
     * ˵޸ҳ.
     * 
     * @param model .
     * @param id ˵ID
     * @return .
     */
    @RequestMapping(value = "/menu/modify/{id}", method = RequestMethod.GET)
    public String modifyMenu(Model model, @PathVariable("id")
    String id) {
        Menu menu = menuService.getMenuById(id);
        model.addAttribute("menu", menu);
        model.addAttribute("modules", moduleService.getAllModuleList());
        model.addAttribute("parent", menuService.getMenuById(menu.getParentId()));

        return RESOURCE_MENU_INFO_PAGE;
    }

    /**
     * ˵.
     * 
     * @param menu ˵
     * @return .
     */
    @RequestMapping(value = "/menu/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage saveMenu(Menu menu) {
        menuService.saveMenu(menu);
        return new JsonMessage(true, "˵ɹ!");
    }

    /**
     * ɾ˵.
     * 
     * @param model .
     * @param id ˵ID
     * @return .
     */
    @RequestMapping(value = "/menu/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage deleteMenu(Model model, @RequestParam("id")
    String id) {
        menuService.deleteMenus(new String[] { id });
        return new JsonMessage(true, "ɾ˵ɹ!");
    }

    /**
     * 
     * 
     * @param type
     * @param one .
     * @param two .
     * @return .
     */
    @RequestMapping(value = "/swap")
    @ResponseBody
    public JsonMessage swapCreateTime(@RequestParam("type")
    String type, @RequestParam("one")
    String one, @RequestParam("two")
    String two) {
        if (StringUtil.equals("s", type)) {
            moduleService.saveSwapModuleOrder(one, two);
        } else if (StringUtil.equals("m", type)) {
            menuService.saveSwapMenuOrder(one, two);
        }
        return new JsonMessage(true, "");
    }

}

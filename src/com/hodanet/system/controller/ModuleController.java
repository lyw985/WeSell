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
import com.hodanet.system.entity.po.Module;
import com.hodanet.system.service.ModuleService;

/**
 * @author lance.lengcs
 * @version 2012-8-7 2:14:34
 * 
 * <pre>
 * ϵͳ
 * </pre>
 */
@Controller
@RequestMapping(value = "/module")
public class ModuleController {

    private static final String MODULE_LIST_PAGE = "system/module/list";
    private static final String MODULE_INFO_PAGE = "system/module/info";

    @Autowired
    private ModuleService       moduleService;

    /**
     * ѯϵͳ
     * 
     * @param model
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, PageData<Module> pageData) {

        pageData = moduleService.getAllModuleByPage(pageData, null);

        model.addAttribute("pageData", pageData);

        return MODULE_LIST_PAGE;
    }

    @RequestMapping(value = "/query")
    public String query(Model model, PageData<Module> pageData, @RequestParam("searchName")
    String searchName) {

        pageData = moduleService.getAllModuleByPage(pageData, searchName);

        model.addAttribute("pageData", pageData);
        model.addAttribute("searchName", searchName);

        return MODULE_LIST_PAGE;
    }

    /**
     * ϵͳҳ
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String gotoNew(Model model) {

        Module module = new Module();
        model.addAttribute("module", module);
        return MODULE_INFO_PAGE;
    }

    /**
     * ϵͳ
     * 
     * @param Module
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage save(Model model, Module module, HttpServletRequest request) {

        if (module == null) {
            return new JsonMessage(false, "ʧ");
        }

        if (StringUtil.isBlank(module.getId())) {
            module.setId(null);
        }

        module = moduleService.saveModule(module);
        model.addAttribute("module", module);

        return new JsonMessage(true, "ɹ");
    }

    /**
     * ϵͳ޸
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String modify(Model model, @PathVariable("id")
    String id) {

        model.addAttribute("module", moduleService.getModuleById(id));

        return MODULE_INFO_PAGE;
    }

    /**
     * ɾϵͳ
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage delete(@RequestParam("id")
    String[] ids) {

        moduleService.deleteModules(ids);
        return new JsonMessage(true, "ɾɹ");
    }
}

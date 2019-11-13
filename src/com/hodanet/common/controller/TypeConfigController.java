package com.hodanet.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hodanet.common.entity.po.TypeConfig;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.service.TypeConfigService;

/**
 * @author lance.lengcs
 * @version 2012-8-21 11:11:47
 * 
 * <pre>
 * 	ÿϵͳһЩԵֵã
 * </pre>
 */
@Controller
@RequestMapping(value = "/common/typeConfig")
public class TypeConfigController {

    private static final String TYPE_CONFIG_LIST_PAGE = "common/typeConfig/list";
    private static final String TYPE_CONFIG_INFO_PAGE = "common/typeConfig/info";

    @Autowired
    private TypeConfigService   typeConfigService;

    /**
     * listб
     * 
     * @param model
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model, PageData<TypeConfig> pageData) {

        pageData = typeConfigService.getTypeConfigByPage(pageData, null, null, null);

        model.addAttribute("pageData", pageData);

        return TYPE_CONFIG_LIST_PAGE;
    }

    @RequestMapping(value = "/query")
    public String query(Model model, PageData<TypeConfig> pageData, @RequestParam("searchName")
    String searchName, @RequestParam("searchCode")
    String searchCode, @RequestParam("searchModule")
    String searchModule) {

        pageData = typeConfigService.getTypeConfigByPage(pageData, searchName, searchCode, searchModule);

        model.addAttribute("pageData", pageData);
        model.addAttribute("searchName", searchName);
        model.addAttribute("searchCode", searchCode);
        model.addAttribute("searchModule", searchModule);

        return TYPE_CONFIG_LIST_PAGE;
    }

    /**
     * ҳ
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String gotoNew(Model model) {

        TypeConfig typeConfig = new TypeConfig();
        model.addAttribute("typeConfig", typeConfig);
        return TYPE_CONFIG_INFO_PAGE;
    }

    /**
     * 
     * 
     * @param BlshContent
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage save(Model model, TypeConfig typeConfig) {

        if (typeConfig == null) {
            return new JsonMessage(false, "ʧ");
        }

        typeConfig = typeConfigService.saveTypeConfig(typeConfig);
        model.addAttribute("typeConfig", typeConfig);

        return new JsonMessage(true, "ɹ");
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
    String id) {

        model.addAttribute("typeConfig", typeConfigService.getTypeConfigById(id));

        return TYPE_CONFIG_INFO_PAGE;
    }

    /**
     * ɾ
     * 
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage delete(@RequestParam("id")
    String[] ids) {

        typeConfigService.deleteTypeConfigs(ids);
        return new JsonMessage(true, "ɾɹ");
    }
}

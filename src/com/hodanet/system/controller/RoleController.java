package com.hodanet.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.entity.po.Role;
import com.hodanet.system.service.ModuleService;
import com.hodanet.system.service.RoleService;

/**
 * @author lance.lengcs
 * @version 2012-8-7 2:14:22
 * 
 * <pre>
 * ɫ
 * </pre>
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {

    private static final String ROLE_LIST_PAGE   = "system/role/list";
    private static final String ROLE_INFO_PAGE   = "system/role/info";
    private static final String ROLE_CHOOSE_PAGE = "system/role/choose";

    @Autowired
    private RoleService         roleService;
    @Autowired
    private ModuleService       moduleService;

    /**
     * 򿪽ɫѡҳ.
     * 
     * @param pid ĬID eg. id1,id2,id3
     * @param model .
     * @return .
     */
    @RequestMapping(value = "/choose")
    public String choose(@RequestParam(value = "pid", required = false)
    String pid, Model model) {
        if (pid != null) {
            model.addAttribute("pid", pid);
            model.addAttribute("pidFlag", "_flag");
        }
        return ROLE_CHOOSE_PAGE;
    }

    /**
     * ȡûɫṹ(ֲ).
     * 
     * @param parentId .
     * @return .
     */
    @RequestMapping(value = "/getUserRole")
    @ResponseBody
    public List<JsTreeNode> getUserRole(@RequestParam("pid")
    String parentId) {

        if (StringUtil.isBlank(parentId)) {
            List<JsTreeNode> moduleNodes = moduleService.getModuleTree();
            return moduleNodes;
        } else {
            List<JsTreeNode> roleNodes = roleService.getRoleByModuleId(parentId);
            return roleNodes;
        }
    }

    /**
     * ȡɫ֯ṹ(src).
     * 
     * @return .
     */
    @RequestMapping(value = "/getRoleTree")
    @ResponseBody
    public List<JsTreeNode> getRoleTree(@RequestParam(value = "moduleId", defaultValue = "")
    String moduleId) {
        if (StringUtil.isBlank(moduleId)) {
            return moduleService.getModuleTree();
        } else {
            return roleService.getRoleByModuleId(moduleId);
        }
    }

    /**
     * ɫҳ.
     * 
     * @return .
     */
    @RequestMapping(value = "/list")
    public String list() {
        return ROLE_LIST_PAGE;
    }

    /**
     * ϸҳ.
     * 
     * @param role .
     * @param model .
     * @return .
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String showRole(Role role, Model model) {
        if (StringUtil.isNotBlank(role.getId())) {
            model.addAttribute("role", roleService.getRoleById(role.getId()));
        }
        model.addAttribute("moduleList", moduleService.getAllModuleList());

        return ROLE_INFO_PAGE;
    }

    /**
     * Ȩ.
     * 
     * @param role .
     * @return .
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage saveRoleGroup(Role role) {
        roleService.saveRole(role);
        return new JsonMessage(true, "ɹ!");
    }

    /**
     * ɾȨ.
     * 
     * @param roleId .
     * @return .
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage deleteRoleGroup(@RequestParam("roleId")
    String roleId) {
        roleService.deleteRoles(new String[] { roleId });
        return new JsonMessage(true, "ɾɹ!");
    }

    /**
     * ɫ.
     * 
     * @param name Ҫ
     * @return ڵID
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public List<String> searchUser(@RequestParam("name")
    String name) {
        // TODO
        List<String> resuldIds = null;
        return resuldIds;
    }
}

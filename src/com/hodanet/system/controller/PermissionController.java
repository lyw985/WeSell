package com.hodanet.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.service.MenuService;
import com.hodanet.system.service.ModuleService;
import com.hodanet.system.service.PermissionService;

/**
 * @author lance.lengcs
 * @version 2012-8-5 3:58:18
 * 
 * <pre>
 * Ȩ޹
 * </pre>
 */
@Controller
@RequestMapping(value = "/permission")
public class PermissionController {

    private static final String PERMISSION_ROLE_RESOURCE_PAGE = "system/permission/roleResource";
    private static final String PERMISSION_USER_ROLE_PAGE     = "system/permission/userRole";

    @Autowired
    private PermissionService   permissionService;
    @Autowired
    private ModuleService       moduleService;
    @Autowired
    private MenuService         menuService;

    /**
     * Աɫҳ.
     * 
     * @return .
     */
    @RequestMapping("/userRole")
    public String gotoUserRole() {
        return PERMISSION_USER_ROLE_PAGE;
    }

    /**
     * ȡûнɫ.
     * 
     * @param userId ûID
     * @return ɫID
     */
    @RequestMapping("/getUserRole")
    @ResponseBody
    public List<String> getUserRole(@RequestParam("userId")
    String userId) {

        List<String> roleIds = permissionService.getRoleListByUserId(userId);
        // ҳjsմ bug
        if (roleIds == null) {
            roleIds = new ArrayList();
        }
        return roleIds;
    }

    /**
     * ûĽɫ.
     * 
     * @param userId ûID
     * @param adds ӵĽɫID's
     * @param dels ɾĽɫID's
     * @return .
     */
    @RequestMapping("/saveUserRole")
    @ResponseBody
    public JsonMessage saveUserRole(@RequestParam("userId")
    String userId, @RequestParam(value = "add", required = false)
    String[] adds, @RequestParam(value = "del", required = false)
    String[] dels) {
        permissionService.saveUserRoles(userId, adds, dels);
        return new JsonMessage(true, "ûɫɹ");
    }

    /**
     * ɫԴҳ.
     * 
     * @return .
     */
    @RequestMapping("/roleResource")
    public String gotoRoleResource() {
        return PERMISSION_ROLE_RESOURCE_PAGE;
    }

    /**
     * ȡԴṹ.
     * 
     * @param moduleId ϵͳid
     * @return .
     */
    @RequestMapping("/getResourceTree")
    @ResponseBody
    public List<JsTreeNode> getResourceTree(@RequestParam("moduleId")
    String moduleId) {
        List<JsTreeNode> nodes = null;
        if (StringUtil.isBlank(moduleId)) {
            nodes = moduleService.getModuleTree();
        } else {
            nodes = menuService.getMenuResourceByModule(moduleId);
            if (nodes.size() > 0) {
                nodes = nodes.get(0).getChildren();
            }
        }
        return nodes;
    }

    /**
     * ȡɫԴ.
     * 
     * @param roleId ɫID
     * @return ԴID
     */
    @RequestMapping("/getRoleResource")
    @ResponseBody
    public List<String> getRoleResource(@RequestParam("roleId")
    String roleId) {
        List<String> resourceIds = permissionService.getMenuListByRoleId(roleId);
        // ҳjsմ bug
        if (resourceIds == null) {
            resourceIds = new ArrayList();
        }
        return resourceIds;
    }

    /**
     * ɫԴϵ.
     * 
     * @param roleId ɫID
     * @param addMenus ӵĲ˵ID's
     * @param delMenus ɾĲ˵ID's
     * @return .
     */
    @RequestMapping("/saveRoleResource")
    @ResponseBody
    public JsonMessage saveRoleResource(@RequestParam("roleId")
    String roleId, @RequestParam(value = "addMenu", required = false)
    String[] addMenus, @RequestParam(value = "delMenu", required = false)
    String[] delMenus) {
        permissionService.saveRoleMenus(roleId, addMenus, delMenus);

        return new JsonMessage(true, "ɫԴɹ");
    }

}

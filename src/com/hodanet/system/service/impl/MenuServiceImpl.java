package com.hodanet.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.constant.PermissionConstants;
import com.hodanet.system.entity.po.Menu;
import com.hodanet.system.entity.po.Module;
import com.hodanet.system.service.MenuService;
import com.hodanet.system.service.ModuleService;
import com.hodanet.system.service.PermissionService;

/**
 * @author lance.lengcs
 * @version 2012-8-7 9:55:03
 * 
 * <pre>
 * MenuServiceʵ
 * </pre>
 */
@Service
public class MenuServiceImpl extends AbstractDaoService implements MenuService {

    @Autowired
    private ModuleService     moduleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Menu getMenuById(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("from Menu o where o.id = '");
        sb.append(id);
        sb.append("' order by o.ordering");
        return getDao().queryHqlUniqueResult(sb.toString());
    }

    @Override
    public List<Menu> getMenuListByModuleId(String moduleId) {

        StringBuilder sb = new StringBuilder();
        sb.append("from Menu o where o.module.id = '");
        sb.append(moduleId);
        sb.append("'");

        sb.append(" order by o.ordering");

        return getDao().queryHql(sb.toString());
    }

    @Override
    public List<Menu> getAllMenuList() {

        String hql = "from Menu o order by o.ordering";
        return getDao().queryHql(hql);
    }

    @Override
    public List<Menu> getMenuList(String moduleId, String parentId) {

        List<Menu> menus = queryMenuList(moduleId, parentId);

        for (Menu menu : menus) {
            List<Menu> childs = getMenuList(moduleId, menu.getId());
            menu.setChildMenus(childs);
        }

        return menus;
    }

    // ѯ˵list
    private List<Menu> queryMenuList(String moduleId, String parentId) {
        StringBuilder sb = new StringBuilder();
        sb.append("from Menu o where o.module.id = '");
        sb.append(moduleId);
        sb.append("'");
        if (StringUtil.isNotBlank(parentId)) {
            sb.append(" and o.parentId = '");
            sb.append(parentId);
            sb.append("'");
        }
        sb.append(" order by o.ordering");

        return getDao().queryHql(sb.toString());
    }

    @Override
    public PageData<Menu> getAllMenuByPage(PageData<Menu> pageData, String name) {

        StringBuilder sb = new StringBuilder();
        sb.append("from Menu o ");
        if (StringUtil.isNotBlank(name)) {
            sb.append("where o.name like '%").append(name).append("%'");
        }
        sb.append(" order by o.ordering");

        return getDao().queryHqlPageData(sb.toString(), pageData);
    }

    @Override
    public Menu saveMenu(Menu menu) {

        if (menu == null) {
            return null;
        }
        if (StringUtil.isBlank(menu.getParentId())) {
            menu.setParentId(PermissionConstants.CONSTANT_ROOT_PARENT_ID);
        }

        if (StringUtil.isBlank(menu.getId())) {
            menu.setOrdering(permissionService.queryMaxOrdering("auth_menu"));
            this.getDao().save(menu);
        } else {
            Menu orginal = getMenuById(menu.getId());

            orginal.setName(menu.getName());
            orginal.setParentId(menu.getParentId());
            orginal.setAddress(menu.getAddress());
            orginal.setDescription(menu.getDescription());
        }

        return menu;
    }

    @Override
    public void deleteMenus(String[] ids) {
        this.getDao().delete(Menu.class, ids);
    }

    @Override
    public List<JsTreeNode> getResourceTree(String moduleId, String parentId, String type) {

        List<JsTreeNode> jsTreeNodes = new ArrayList<JsTreeNode>();

        if (StringUtil.isBlank(type)) {
            List<Module> modules = moduleService.getAllModuleList();
            for (Module module : modules) {
                JsTreeNode node = new JsTreeNode();
                node.setData(module.getName());
                node.addAttribute("t", "s");
                node.addAttribute("id", module.getId());
                node.addAttribute("moduleId", module.getId());
                node.addAttribute("ordering", String.valueOf(module.getOrdering()));
                node.setIcon("module");
                jsTreeNodes.add(node);
            }
        } else if ("s".equals(type)) {
            List<Menu> menus = getMenuList(moduleId, "-1");
            for (Menu menu : menus) {
                JsTreeNode node = new JsTreeNode();
                node.setData(menu.getName());
                node.addAttribute("t", "m");
                node.addAttribute("id", menu.getId());
                node.addAttribute("moduleId", menu.getModule().getId());
                node.addAttribute("ordering", String.valueOf(menu.getOrdering()));
                node.setIcon("menu");
                jsTreeNodes.add(node);
            }
        } else if ("m".equals(type)) {
            List<Menu> menus = getMenuList(moduleId, parentId);
            for (Menu menu : menus) {
                JsTreeNode node = new JsTreeNode();
                node.setData(menu.getName());
                node.addAttribute("t", "m");
                node.addAttribute("id", menu.getId());
                node.addAttribute("moduleId", moduleId);
                node.addAttribute("ordering", String.valueOf(menu.getOrdering()));
                node.setIcon("menu");
                jsTreeNodes.add(node);
            }
        }

        return jsTreeNodes;
    }

    @Override
    public List<JsTreeNode> getMenuResourceByModule(String moduleId) {
        Map<String, JsTreeNode> map = new HashMap<String, JsTreeNode>();
        Module module = moduleService.getModuleById(moduleId);
        JsTreeNode node = new JsTreeNode();
        node.setData(module.getName());
        node.addAttribute("t", "s");
        node.addAttribute("ordering", String.valueOf(module.getOrdering()));
        node.setIcon("module");
        node.setState(JsTreeNode.STATE_OPEN);
        String rootId = module.getId();
        map.put(rootId, node);
        // ȡ˵
        List<Menu> menus = getMenuListByModuleId(moduleId);
        for (Menu menu : menus) {
            node = new JsTreeNode();
            node.setData(menu.getName());
            node.addAttribute("t", "m");
            node.addAttribute("id", menu.getId());
            node.addAttribute("moduleCode", menu.getModule().getCode());
            node.addAttribute("moduleId", moduleId);
            node.addAttribute("ordering", String.valueOf(menu.getOrdering()));
            node.setIcon("menu");
            node.setCheckbox(true);
            node.setState(JsTreeNode.STATE_OPEN);
            if (StringUtil.equals(menu.getParentId(), PermissionConstants.CONSTANT_ROOT_PARENT_ID)) {
                node.setParentId(rootId);
            } else {
                node.setParentId(menu.getParentId());
            }
            map.put(menu.getId(), node);
        }
        return JsTreeNode.buildTree(map);
    }

    @Override
    public void saveSwapMenuOrder(String one, String two) {
        Menu menu1 = getMenuById(one);
        Menu menu2 = getMenuById(two);

        if (menu1 != null && menu2 != null) {
            Integer tmp = menu1.getOrdering();
            menu1.setOrdering(menu2.getOrdering());
            menu2.setOrdering(tmp);
        }
    }

}

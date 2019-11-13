package com.hodanet.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.entity.po.Role;
import com.hodanet.system.service.PermissionService;
import com.hodanet.system.service.RoleService;

/**
 * @author lance.lengcs
 * @version 2012-8-7 1:49:27
 * 
 * <pre>
 * RoleServiceʵ
 * </pre>
 */
@Service
public class RoleServiceImpl extends AbstractDaoService implements RoleService {

    @Autowired
    private PermissionService permissionService;

    @Override
    public Role getRoleById(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("from Role o where o.id = '");
        sb.append(id);
        sb.append("' order by o.ordering");
        return getDao().queryHqlUniqueResult(sb.toString());
    }

    @Override
    public List<Role> getRoleListByModuleId(String moduleId) {
        StringBuilder sb = new StringBuilder();
        sb.append("from Role o ");
        if (StringUtil.isNotBlank(moduleId)) {
            sb.append(" where o.module = '");
            sb.append(moduleId);
            sb.append("'");
        }
        sb.append("order by o.ordering");
        return getDao().queryHql(sb.toString());
    }

    @Override
    public PageData<Role> getAllRoleByPage(PageData<Role> pageData, String name) {

        StringBuilder sb = new StringBuilder();
        sb.append("from Role o ");
        if (StringUtil.isNotBlank(name)) {
            sb.append("where o.name like '%").append(name).append("%'");
        }
        sb.append(" order by o.ordering");

        return getDao().queryHqlPageData(sb.toString(), pageData);
    }

    @Override
    public Role saveRole(Role role) {

        if (role == null) {
            return null;
        }
        if (StringUtil.isBlank(role.getId())) {
            role.setOrdering(permissionService.queryMaxOrdering("auth_role"));
            this.getDao().save(role);
        } else {
            Role orginal = getRoleById(role.getId());

            orginal.setName(role.getName());
            orginal.setDescription(role.getDescription());
        }

        return role;
    }

    @Override
    public void deleteRoles(String[] ids) {
        this.getDao().delete(Role.class, ids);
    }

    @Override
    public List<JsTreeNode> getRoleByModuleId(String moduleId) {

        List<JsTreeNode> jsTreeNodes = new ArrayList<JsTreeNode>();
        List<Role> roles = getRoleListByModuleId(moduleId);
        if (roles == null) {
            return null;
        }
        for (Role role : roles) {
            JsTreeNode node = new JsTreeNode();
            node.setData(role.getName());
            node.setParentId(role.getModule().getId());
            node.addAttribute("id", role.getId());
            node.addAttribute("ordering", String.valueOf(role.getOrdering()));
            node.addAttribute("t", "role");
            node.setState(JsTreeNode.STATE_OPEN);
            node.setIcon("role");
            node.setCheckbox(true);
            jsTreeNodes.add(node);
        }
        return jsTreeNodes;
    }
}

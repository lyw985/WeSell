package com.hodanet.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.entity.po.Menu;
import com.hodanet.system.entity.po.Module;
import com.hodanet.system.entity.po.Role;
import com.hodanet.system.entity.po.RoleMenu;
import com.hodanet.system.entity.po.User;
import com.hodanet.system.entity.po.UserRole;
import com.hodanet.system.service.MenuService;
import com.hodanet.system.service.ModuleService;
import com.hodanet.system.service.PermissionService;

/**
 * @author lance.lengcs
 * @version 2012-8-5 10:26:51
 * 
 * <pre>
 * 	PermissionService ʵ
 * </pre>
 */
@Service
public class PermissionServiceImpl extends AbstractDaoService implements PermissionService {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private MenuService   menuService;

    @Override
    public List<Module> getModuleListByUserId(String userId) {
        if (StringUtil.isBlank(userId)) {
            return null;
        }

        String sql = "select distinct(a.id) from auth_module a where a.id in ( select b.module_id from auth_role b inner join auth_user_role c on b.id = c.role_id and c.user_id = '"
                     + userId + "')";

        List<Object> permissions = getDao().querySql(sql);

        if (permissions == null || permissions.size() < 1) {
            return null;
        }

        List<Module> modules = moduleService.getAllModuleList();

        for (int i = modules.size() - 1; i >= 0; i--) {
            if (!permissions.contains(modules.get(i).getId())) {
                modules.remove(i);
            }
        }
        return modules;
    }

    @Override
    public List<Object> getRoleListByUserIdAndModuleId(String userId, String moduleId) {
        if (StringUtil.isBlank(userId)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select  distinct(a.id) from auth_role a where a.id in (select b.role_id from auth_user_role b where b.user_id = '");
        sb.append(userId);
        sb.append("' ");
        if (StringUtil.isNotBlank(moduleId)) {
            sb.append(" and b.module_id = '");
            sb.append(moduleId);
            sb.append("' ");
        }

        sb.append(")");
        return getDao().querySql(sb.toString());
    }

    @Override
    public List<String> getRoleListByUserId(String userId) {
        List<Object> list = getRoleListByUserIdAndModuleId(userId, null);

        if (list == null || list.size() < 1) {
            return null;
        }

        List<String> roles = new ArrayList<String>();
        for (Object object : list) {
            roles.add((String) object);
        }

        return roles;
    }

    @Override
    public List<String> getMenuListByRoleId(String roleId) {
        if (StringUtil.isBlank(roleId)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select distinct(a.id) from auth_menu a where a.id in (select b.menu_id from auth_role_menu b where b.role_id = '");
        sb.append(roleId);
        sb.append("') ");

        List<Object> list = getDao().querySql(sb.toString());
        if (list == null || list.size() < 1) {
            return null;
        }

        List<String> menus = new ArrayList<String>();
        for (Object object : list) {
            menus.add((String) object);
        }

        return menus;
    }

    @Override
    public List<Menu> getMenuList(String userId, String moduleId, String parentMenuId) {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(moduleId)) {
            return null;
        }

        String sql = "select distinct(a.id) from auth_menu a where a.module_id = '"
                     + moduleId
                     + "' and a.id in (select b.menu_id from auth_role_menu b inner join auth_user_role c on b.role_id = c.role_id and c.user_id = '"
                     + userId + "')";

        List<Object> permissions = getDao().querySql(sql);

        if (permissions == null || permissions.size() < 1) {
            return null;
        }

        List<Menu> menus = menuService.getMenuList(moduleId, parentMenuId);

        removeForbidden(menus, permissions);

        return menus;
    }

    /**
     * ƳȨ޷ʵĲ˵.
     * 
     * @param menus ˵
     * @param permissions Ȩid
     */
    private void removeForbidden(List<Menu> menus, List<Object> permissions) {
        if (menus == null || menus.size() <= 0) {
            return;
        }
        for (int i = menus.size() - 1; i >= 0; i--) {
            if (!permissions.contains(menus.get(i).getId())) {
                menus.remove(i);
            } else {
                removeForbidden(menus.get(i).getChildMenus(), permissions);
            }
        }
    }

    @Override
    public void saveRoleMenus(String roleId, String[] adds, String[] dels) {
        if (StringUtil.isBlank(roleId)) {
            return;
        }

        if (adds != null) {
            this.addRoleMenus(roleId, adds);
        }
        if (dels != null) {
            this.delRoleMenus(roleId, dels);
        }
    }

    @Override
    public void saveUserRoles(String userId, String[] adds, String[] dels) {
        if (StringUtil.isBlank(userId)) {
            return;
        }

        if (adds != null) {
            this.addUserRoles(userId, adds);
        }
        if (dels != null) {
            this.delUserRoles(userId, dels);
        }
    }

    @Override
    public Integer queryMaxOrdering(String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select max(o.ordering) from ");
        sb.append(tableName);
        sb.append(" o");

        List<Object> list = getDao().querySql(sb.toString());

        if (list == null || list.size() < 1) {
            return null;
        }

        if (list.get(0) == null) {
            return 1;
        }

        Integer maxOrdering = (Integer) list.get(0);
        return maxOrdering + 1;
    }

    /**
     * ɫ˵ϵ.
     * 
     * @param roleId ɫID
     * @param adds Ĳ˵ID's
     */
    private void addRoleMenus(String roleId, String[] adds) {
        if (StringUtil.isBlank(roleId) || adds == null || adds.length < 1) {
            return;
        }

        RoleMenu[] roleMenus = new RoleMenu[adds.length];

        for (int i = 0; i < adds.length; i++) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRole(new Role(roleId));
            roleMenu.setMenu(new Menu(adds[i]));

            roleMenus[i] = roleMenu;
        }

        getDao().save(roleMenus);
    }

    /**
     * hɫ˵ϵ.
     * 
     * @param roleId ɫID
     * @param dels ɾĲ˵ID's
     */
    private void delRoleMenus(String roleId, String[] dels) {

        StringBuilder sb = new StringBuilder();
        sb.append("delete from auth_role_menu where role_id = '");
        sb.append(roleId);
        sb.append("' and menu_id in (");
        sb.append(join(dels));
        sb.append(")");

        getDao().executeSql(sb.toString());
    }

    /**
     * Ñɫϵ.
     * 
     * @param userId ÑID
     * @param adds ĽɫID's
     */
    private void addUserRoles(String userId, String[] adds) {
        if (StringUtil.isBlank(userId) || adds == null || adds.length < 1) {
            return;
        }

        UserRole[] userRoles = new UserRole[adds.length];

        for (int i = 0; i < adds.length; i++) {
            UserRole userRole = new UserRole();

            userRole.setUser(new User(userId));
            userRole.setRole(new Role(adds[i]));

            userRoles[i] = userRole;
        }

        getDao().save(userRoles);
    }

    /**
     * hûɫϵ.
     * 
     * @param userId ûID
     * @param dels ɾĽɫID's
     */
    private void delUserRoles(String userId, String[] dels) {

        StringBuilder sb = new StringBuilder();
        sb.append("delete from auth_user_role where user_id = '");
        sb.append(userId);
        sb.append("' and role_id in (");
        sb.append(join(dels));
        sb.append(")");

        getDao().executeSql(sb.toString());
    }

    // ƴids
    private String join(String[] ids) {
        if (ids == null || ids.length < 1) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            sb.append("'");
            sb.append(id);
            sb.append("'");
            sb.append(",");
        }

        // ȡһַ","
        return StringUtil.substring(sb.toString(), 0, sb.toString().length() - 1);
    }
}

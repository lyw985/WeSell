package com.hodanet.system.service;

import java.util.List;

import com.hodanet.system.entity.po.Menu;
import com.hodanet.system.entity.po.Module;

/**
 * @author lance.lengcs
 * @version 2012-8-5 10:26:31
 * 
 * <pre>
 * Ȩ޷ӿ
 * </pre>
 */
public interface PermissionService {

    /**
     * userIdѯϵͳб
     * 
     * @param userId
     * @return
     */
    public List<Module> getModuleListByUserId(String userId);

    /**
     * userIdmoduleIdѯɫб
     * 
     * @param userId
     * @param moduleId
     * @return
     */
    public List<Object> getRoleListByUserIdAndModuleId(String userId, String moduleId);

    /**
     * userIdѯɫбID
     * 
     * @param userId
     * @return
     */
    public List<String> getRoleListByUserId(String userId);

    /**
     * roleIdѯ˵бID
     * 
     * @param roleId
     * @return
     */
    public List<String> getMenuListByRoleId(String roleId);

    /**
     * userIdmoduleIdparentMenuIdѯ˵б
     * 
     * @param userId
     * @param moduleId
     * @param parentMenuId
     * @return
     */
    public List<Menu> getMenuList(String userId, String moduleId, String parentMenuId);

    /**
     * ûɫϵ.
     * 
     * @param userId ûID
     * @param adds ӵĲ˵ID's
     * @param dels ɾĲ˵ID's
     */
    public void saveUserRoles(String userId, String[] adds, String[] dels);

    /**
     * ɫ˵ϵ.
     * 
     * @param roleId ɫID
     * @param adds ӵĲ˵ID's
     * @param dels ɾĲ˵ID's
     */
    public void saveRoleMenus(String roleId, String[] adds, String[] dels);

    /**
     * ѯĳţ + 1.
     * 
     * @param tableName
     */
    public Integer queryMaxOrdering(String tableName);
}

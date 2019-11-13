package com.hodanet.system.service;

import java.util.List;

import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.system.entity.po.Role;

/**
 * @author lance.lengcs
 * @version 2012-8-7 1:45:21
 * 
 * <pre>
 * ɫӿ
 * </pre>
 */
public interface RoleService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public Role getRoleById(String id);

    /**
     * ϵͳidѯɫб
     * 
     * @param moduleId
     * @return
     */
    public List<Role> getRoleListByModuleId(String moduleId);

    /**
     * ҳѯнɫ
     * 
     * @param pageData
     * @return
     */
    public PageData<Role> getAllRoleByPage(PageData<Role> pageData, String name);

    /**
     * һɫ
     * 
     * @param Role
     * @return
     */
    public Role saveRole(Role role);

    /**
     * ɾɫ
     * 
     * @param ids
     */
    public void deleteRoles(String[] ids);

    /**
     * ϵͳidѯɫ
     * 
     * @param moduleId
     * @return
     */
    public List<JsTreeNode> getRoleByModuleId(String moduleId);
}

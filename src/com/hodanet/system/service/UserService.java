package com.hodanet.system.service;

import java.util.List;

import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.system.entity.po.User;

/**
 * @author lance.lengcs
 * @version 2012-7-24 3:33:40
 * 
 * <pre>
 *    ûӿ.
 * </pre>
 */
public interface UserService {

    /**
     * idȡuser
     * 
     * @param id
     * @return
     */
    public User getUserById(String id);

    /**
     * loginIdѯuser
     * 
     * @param loginId
     * @return
     */
    public User getUserByLoginId(String loginId);

    /**
     * һuser
     * 
     * @param user
     * @return
     */
    public User saveUser(User user);

    /**
     * ݲŻȡԱ.
     * 
     * @param deptId .
     * @return .
     */
    public List<JsTreeNode> getUserByDeptId(String deptId);

    /**
     * ɾ(߼ɾ״̬)
     * 
     * @param ids
     */
    public void deleteUsers(String[] ids);

    /**
     * id
     * 
     * @param name
     */
    public List<String> searchUser(String name);

    /**
     * 
     * 
     * @param id one
     * @param id two
     */
    public void saveSwapUserOrder(String one, String two);

    /**
     * ݲIDȡû
     * 
     * @param deptId
     * @return
     */
    public List<User> getUserByDept(String deptId);
}

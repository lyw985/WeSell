package com.hodanet.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.constant.PermissionConstants;
import com.hodanet.system.entity.po.Department;
import com.hodanet.system.entity.po.User;
import com.hodanet.system.service.PermissionService;
import com.hodanet.system.service.UserService;

/**
 * @author lance.lengcs
 * @version 2012-7-24 3:34:01
 * 
 * <pre>
 *    UserService ʵ.
 * </pre>
 */
@Service
public class UserServiceImpl extends AbstractDaoService implements UserService {

    @Autowired
    private PermissionService permissionService;

    @Override
    public User getUserById(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("from User o where o.id ='");
        sb.append(id);
        sb.append("' and o.status = 1");

        List userList = getDao().queryHql(sb.toString());

        if (userList != null && userList.size() > 0) {
            return (User) userList.get(0);
        }

        return null;
    }

    @Override
    public User getUserByLoginId(String loginId) {
        String hql = "from User o where o.loginId ='" + loginId + "' and o.status = 1";
        List userList = getDao().queryHql(hql);

        if (userList != null && userList.size() > 0) {
            return (User) userList.get(0);
        }

        return null;
    }

    @Override
    public User saveUser(User user) {
        if (user == null) {
            return null;
        }
        User orginal = this.getUserById(user.getId());
        if (orginal == null) {
            // ͬ
            Integer ordering = permissionService.queryMaxOrdering("auth_user");
            user.setJobNumber(String.valueOf(ordering));
            user.setOrdering(ordering);
            this.getDao().save(user);
        } else {
            orginal.setName(user.getName());
            orginal.setPhone(user.getPhone());
            orginal.setMobile(user.getMobile());
            orginal.setEmail(user.getEmail());
            orginal.setPostCode(user.getPostCode());
            orginal.setAddress(user.getAddress());

            if (StringUtil.isNotBlank(user.getPassword())) {
                orginal.setPassword(user.getPassword());
            }
        }

        return user;
    }

    @Override
    public List<JsTreeNode> getUserByDeptId(String deptId) {

        List<JsTreeNode> jsTreeNodes = new ArrayList<JsTreeNode>();

        List<User> users = getUserByDept(deptId);

        for (User user : users) {
            JsTreeNode node = new JsTreeNode();
            node.setData(user.getName());
            node.setParentId(user.getDepartment().getId());
            node.addAttribute("id", user.getId());
            node.addAttribute("ordering", String.valueOf(user.getOrdering()));
            node.addAttribute("t", "user");
            node.setState(JsTreeNode.STATE_OPEN);
            node.setIcon("user");
            node.setCheckbox(true);
            jsTreeNodes.add(node);
        }
        return jsTreeNodes;
    }

    @Override
    public void deleteUsers(String[] ids) {
        for (String id : ids) {
            User user = getUserById(id);
            if (user != null) {
                user.setStatus(PermissionConstants.CONSTANT_STATUS_2);
            }
        }
    }

    @Override
    public List<String> searchUser(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("from User o where o.name like '%");
        sb.append(name);
        sb.append("%'");

        List<User> users = getDao().queryHql(sb.toString());
        if (users == null || users.size() < 1) {
            return null;
        }

        List<String> result = new ArrayList<String>();
        for (User user : users) {
            result.add(user.getDepartment().getId());
            parseIds(user.getDepartment(), result);
        }

        return result;
    }

    private void parseIds(Department department, List<String> result) {
        if (department != null
            && !StringUtil.equals(department.getParentId(), PermissionConstants.CONSTANT_ROOT_PARENT_ID)) {
            result.add(department.getId());
        }
    }

    @Override
    public void saveSwapUserOrder(String one, String two) {
        User user1 = getUserById(one);
        User user2 = getUserById(two);

        if (user1 != null && user2 != null) {
            Integer tmp = user1.getOrdering();
            user1.setOrdering(user2.getOrdering());
            user2.setOrdering(tmp);
        }
    }

    @Override
    // ݲŲѯûб
    public List<User> getUserByDept(String deptId) {

        if (StringUtil.isBlank(deptId)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("from User o where o.status = 1 and o.department.id = '");
        sb.append(deptId);
        sb.append("' order by o.ordering");

        return getDao().queryHql(sb.toString());
    }

}

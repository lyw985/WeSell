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
import com.hodanet.system.service.DepartmentService;
import com.hodanet.system.service.PermissionService;

/**
 * @author lance.lengcs
 * @version 2012-8-13 2:50:53
 * 
 * <pre>
 * 	DepartmentService ʵ
 * </pre>
 */
@Service
public class DepartmentServiceImpl extends AbstractDaoService implements DepartmentService {

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<JsTreeNode> getDepartments(String parentId) {
        List<JsTreeNode> treeNodes = new ArrayList<JsTreeNode>();
        if (StringUtil.isBlank(parentId)) {
            List<Department> departments = getRootDepartments();
            for (Department department : departments) {
                JsTreeNode jsTreeNode = parseDepartment(department);
                treeNodes.add(jsTreeNode);
            }
        } else if (parentId.contains("_flag")) {
            parentId = parentId.split("_")[0];
            String[] pids = parentId.split(",");
            for (String id : pids) {
                Department department = getDepartment(id);
                treeNodes.add(parseDepartment(department));
            }
        } else {
            List<Department> departments = getChildDepartment(parentId);
            for (Department department : departments) {
                treeNodes.add(parseDepartment(department));
            }
        }
        return treeNodes;
    }

    @Override
    public void saveSwapDepartOrder(String one, String two) {
        Department department1 = getDepartment(one);
        Department department2 = getDepartment(two);

        if (department1 != null && department2 != null) {
            Integer tmp = department1.getOrdering();
            department1.setOrdering(department2.getOrdering());
            department2.setOrdering(tmp);
        }
    }

    @Override
    public List<String> searchDepartment(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("from Department o where o.name like '%");
        sb.append(name);
        sb.append("%'");

        List<Department> departments = getDao().queryHql(sb.toString());
        if (departments == null || departments.size() < 1) {
            return null;
        }

        List<String> result = new ArrayList<String>();
        for (Department department : departments) {
            parseIds(department.getParentId(), result);
            result.add(department.getId());
        }

        return result;
    }

    private void parseIds(String parentId, List<String> result) {
        if (!StringUtil.equals(parentId, PermissionConstants.CONSTANT_ROOT_PARENT_ID)) {
            result.add(parentId);
        }
        result.add(PermissionConstants.CONSTANT_ROOT_PARENT_ID);
    }

    @Override
    public Department getDepartment(String id) {
        return this.getDao().get(Department.class, id);
    }

    @Override
    public Department getParentDepartment(String id) {
        if (StringUtil.isBlank(id)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select b.parent_id from auth_department b where b.id = '");
        sb.append(id);
        sb.append("'");

        List<Object> list = getDao().querySql(sb.toString());

        if (list == null || list.size() < 1) {
            return null;
        }

        String parentId = (String) list.get(0);
        return getDepartment(parentId);
    }

    @Override
    public Department saveDepartment(Department department) {

        if (department == null) {
            return null;
        }
        if (StringUtil.isBlank(department.getParentId())) {
            department.setParentId(PermissionConstants.CONSTANT_ROOT_PARENT_ID);
        }

        if (StringUtil.isBlank(department.getId())) {
            // ȡ
            department.setOrdering(permissionService.queryMaxOrdering("auth_department"));
            this.getDao().save(department);
        } else {
            Department orginal = getDepartment(department.getId());

            orginal.setName(department.getName());
            orginal.setDescription(department.getDescription());
        }

        return department;
    }

    @Override
    public void deleteDepartment(String id) {
        this.getDao().delete(Department.class, id);
    }

    @Override
    public Department getDepartmentByUser(String userId) {
        StringBuilder sb = new StringBuilder();

        sb.append("select b.department_id from auth_user b where b.id = '");
        sb.append(userId);
        sb.append("'");

        List<Object> list = getDao().querySql(sb.toString());

        if (list == null || list.size() < 1) {
            return null;
        }

        String departmentId = (String) list.get(0);
        return getDepartment(departmentId);
    }

    /**
     * ѯrootlist.
     */
    private List<Department> getRootDepartments() {

        String hql = " from Department o where o.parentId = '-1' order by ordering ";

        return getDao().queryHql(hql);
    }

    /**
     * parentIdѯӲlist.
     */
    private List<Department> getChildDepartment(String parentId) {

        StringBuilder sb = new StringBuilder();
        sb.append("from Department o where o.parentId = '");
        sb.append(parentId);
        sb.append("' order by ordering");

        return getDao().queryHql(sb.toString());
    }

    /**
     * DepartmentתΪJsTreeNode.
     */
    private JsTreeNode parseDepartment(Department department) {
        JsTreeNode treeNode = new JsTreeNode();
        treeNode.setData(department.getName());
        treeNode.addAttribute("id", department.getId());
        treeNode.addAttribute("ordering", String.valueOf(department.getOrdering()));
        treeNode.addAttribute("t", "department");
        treeNode.setIcon("department");
        treeNode.setCheckbox(true);
        return treeNode;
    }
}

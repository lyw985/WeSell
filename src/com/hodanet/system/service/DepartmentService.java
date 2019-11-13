package com.hodanet.system.service;

import java.util.List;

import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.system.entity.po.Department;

/**
 * @author lance.lengcs
 * @version 2012-8-13 2:50:42
 * 
 * <pre>
 * ŷӿ
 * </pre>
 */
public interface DepartmentService {

    /**
     * ݸIDȡӲбJsTree.
     * 
     * @param parentId ID
     * @return .
     */
    public List<JsTreeNode> getDepartments(String parentId);

    /**
     * ޸Ĳʾ˳.
     * 
     * @param one .
     * @param two .
     */
    public void saveSwapDepartOrder(String one, String two);

    /**
     * ѯŵϼ.
     * 
     * @param name ģƥ䣩
     * @return IDб
     */
    public List<String> searchDepartment(String name);

    /**
     * IDȡ.
     * 
     * @param id ID
     * @return .
     */
    public Department getDepartment(String id);

    /**
     * IDȡϼ.
     * 
     * @param id ID
     * @return .
     */
    public Department getParentDepartment(String id);

    /**
     * ûIDȡ.
     * 
     * @param userId ûID
     * @return .
     */
    public Department getDepartmentByUser(String userId);

    /**
     * 沿Ϣ.
     * 
     * @param department Ŷ
     */
    public Department saveDepartment(Department department);

    /**
     * ɾ.
     * 
     * @param id ID
     */
    public void deleteDepartment(String id);

}

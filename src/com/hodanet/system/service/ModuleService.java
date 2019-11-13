package com.hodanet.system.service;

import java.util.List;

import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.system.entity.po.Module;

/**
 * @author lance.lengcs
 * @version 2012-8-7 1:45:38
 * 
 * <pre>
 * ϵͳӿ
 * </pre>
 */
public interface ModuleService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public Module getModuleById(String id);

    /**
     * codeѯ¼
     * 
     * @param code
     * @return
     */
    public Module getModuleByCode(String code);

    /**
     * ѯϵͳ
     * 
     * @return
     */
    public List<Module> getAllModuleList();

    /**
     * ҳѯϵͳ
     * 
     * @param pageData
     * @return
     */
    public PageData<Module> getAllModuleByPage(PageData<Module> pageData, String name);

    /**
     * һϵͳ
     * 
     * @param Module
     * @return
     */
    public Module saveModule(Module module);

    /**
     * ɾϵͳ
     * 
     * @param ids
     */
    public void deleteModules(String[] ids);

    /**
     * ѯϵͳУ
     * 
     * @return
     */
    public List<JsTreeNode> getModuleTree();

    /**
     * .
     * 
     * @param one
     * @param two
     */
    public void saveSwapModuleOrder(String one, String two);
}

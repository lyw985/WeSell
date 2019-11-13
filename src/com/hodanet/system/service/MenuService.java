package com.hodanet.system.service;

import java.util.List;

import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.system.entity.po.Menu;

/**
 * @author lance.lengcs
 * @version 2012-8-7 9:48:35
 * 
 * <pre>
 * ˵ӿ
 * </pre>
 */
public interface MenuService {

    /**
     * idѯ¼
     * 
     * @param id
     * @return
     */
    public Menu getMenuById(String id);

    /**
     * moduleIdѯ¼
     * 
     * @param moduleId
     * @return
     */
    public List<Menu> getMenuListByModuleId(String moduleId);

    /**
     * ѯв˵
     * 
     * @return
     */
    public List<Menu> getAllMenuList();

    /**
     * ϵͳid͸˵IDȡӲ˵.
     * 
     * @param moduleId ģid
     * @param parentId ˵ID.
     * @return .
     */
    public List<Menu> getMenuList(String moduleId, String parentId);

    /**
     * ҳѯв˵
     * 
     * @param pageData
     * @return
     */
    public PageData<Menu> getAllMenuByPage(PageData<Menu> pageData, String name);

    /**
     * һ˵
     * 
     * @param menu
     * @return
     */
    public Menu saveMenu(Menu menu);

    /**
     * ɾ˵
     * 
     * @param ids
     */
    public void deleteMenus(String[] ids);

    /**
     * ϵͳidͻȡJSTreeڵ.
     * 
     * @param moduleId ϵͳid.
     * @param parentId ڵID
     * @param type ͣs:ϵͳm:˵.
     * @return .
     */
    public List<JsTreeNode> getResourceTree(String moduleId, String parentId, String type);

    /**
     * ȡϵͳµв˵Ľṹģڵ㣩.
     * 
     * @param moduleId ϵͳid
     * @return ˵ṹ
     */

    public List<JsTreeNode> getMenuResourceByModule(String moduleId);

    /**
     * .
     * 
     * @param one
     * @param two
     */
    public void saveSwapMenuOrder(String one, String two);
}

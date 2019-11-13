package com.hodanet.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.common.dao.AbstractDaoService;
import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.entity.vo.PageData;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.entity.po.Module;
import com.hodanet.system.service.ModuleService;
import com.hodanet.system.service.PermissionService;

/**
 * @author lance.lengcs
 * @version 2012-8-7 1:49:10
 * 
 * <pre>
 * ModuleServiceʵ
 * </pre>
 */
@Service
public class ModuleServiceImpl extends AbstractDaoService implements ModuleService {

    @Autowired
    private PermissionService permissionService;

    @Override
    public Module getModuleById(String id) {
        return this.getDao().get(Module.class, id);
    }

    @Override
    public Module getModuleByCode(String code) {
        String hql = "from Module o where o.code = '" + code + "' order by o.ordering";
        List<Module> list = getDao().queryHql(hql);
        if (list == null || list.size() < 1) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<Module> getAllModuleList() {

        String hql = "from Module o order by o.ordering";
        return getDao().queryHql(hql);
    }

    @Override
    public PageData<Module> getAllModuleByPage(PageData<Module> pageData, String name) {

        StringBuilder sb = new StringBuilder();
        sb.append("from Module o ");
        if (StringUtil.isNotBlank(name)) {
            sb.append("where o.name like '%").append(name).append("%'");
        }
        sb.append(" order by o.ordering");

        return getDao().queryHqlPageData(sb.toString(), pageData);
    }

    @Override
    public Module saveModule(Module module) {

        if (module == null) {
            return null;
        }
        if (StringUtil.isBlank(module.getId())) {
            module.setOrdering(permissionService.queryMaxOrdering("auth_module"));
            this.getDao().save(module);
        } else {
            Module orginal = getModuleById(module.getId());

            orginal.setName(module.getName());
            orginal.setCode(module.getCode());
            orginal.setDescription(module.getDescription());
        }

        return module;
    }

    @Override
    public void deleteModules(String[] ids) {
        this.getDao().delete(Module.class, ids);
    }

    @Override
    public List<JsTreeNode> getModuleTree() {
        List<JsTreeNode> treeNodes = new ArrayList<JsTreeNode>();
        List<Module> modules = getAllModuleList();
        for (Module module : modules) {
            JsTreeNode jsTreeNode = parseModule(module);
            treeNodes.add(jsTreeNode);
        }
        return treeNodes;
    }

    @Override
    public void saveSwapModuleOrder(String one, String two) {
        Module module1 = getModuleById(one);
        Module module2 = getModuleById(two);

        if (module1 != null && module2 != null) {
            Integer tmp = module1.getOrdering();
            module1.setOrdering(module2.getOrdering());
            module2.setOrdering(tmp);
        }
    }

    // module
    private JsTreeNode parseModule(Module module) {
        JsTreeNode treeNode = new JsTreeNode();
        treeNode.setData(module.getName());
        treeNode.addAttribute("id", module.getId());
        treeNode.addAttribute("moduleId", module.getId());
        treeNode.addAttribute("ordering", String.valueOf(module.getOrdering()));
        treeNode.addAttribute("t", "s");
        treeNode.setIcon("module");
        treeNode.setCheckbox(false);
        return treeNode;
    }
}

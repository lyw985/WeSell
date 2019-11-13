package com.hodanet.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.system.entity.po.Department;
import com.hodanet.system.service.DepartmentService;

/**
 * @author lance.lengcs
 * @version 2012-8-13 6:09:26
 * 
 * <pre>
 * Ź
 * </pre>
 */
@Controller
@RequestMapping(value = "/department")
public class DepartmentController {

    private static final String DEPARTMENT_LIST_PAGE   = "system/department/list";
    private static final String DEPARTMENT_INFO_PAGE   = "system/department/info";
    private static final String DEPARTMENT_CHOOSE_PAGE = "system/department/choose";

    @Autowired
    private DepartmentService   departmentService;

    /**
     * 򿪲ѡҳ.
     * 
     * @param pid ĬID eg. id1,id2,id3
     * @param model .
     * @return .
     */
    @RequestMapping(value = "/choose")
    public String chooseDepartment(@RequestParam(value = "pid", required = false)
    String pid, Model model) {
        if (pid != null) {
            model.addAttribute("pid", pid);
            model.addAttribute("pidFlag", "_flag");
        }
        return DEPARTMENT_CHOOSE_PAGE;
    }

    /**
     * ȡӲ.
     * 
     * @param parentId .
     * @return .
     */
    @RequestMapping("/getChooseData")
    @ResponseBody
    public List<JsTreeNode> getChooseData(@RequestParam("pid")
    String parentId) {
        List<JsTreeNode> treeNodes = departmentService.getDepartments(parentId);
        return treeNodes;
    }

    /**
     * ޸Ĳʾ˳.
     * 
     * @param one .
     * @param two .
     * @return .
     */
    @RequestMapping(value = "/order")
    @ResponseBody
    public JsonMessage swapDepartOrder(@RequestParam("one")
    String one, @RequestParam("two")
    String two) {
        departmentService.saveSwapDepartOrder(one, two);
        return new JsonMessage(true, "");
    }

    /**
     * ţȡڵID.
     * 
     * @param name Ҫ
     * @return ڵID
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public List<String> searchDepartment(@RequestParam("name")
    String name) {
        List<String> resuldIds = departmentService.searchDepartment(name);
        return resuldIds;
    }

    /**
     * 벿Źҳ.
     * 
     * @param model .
     * @return .
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return DEPARTMENT_LIST_PAGE;
    }

    /**
     * 벿޸ҳ.
     * 
     * @param model .
     * @param id ID
     * @return .
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String modify(Model model, @PathVariable("id")
    String id) {
        model.addAttribute("department", departmentService.getDepartment(id));
        model.addAttribute("parentDepartment", departmentService.getParentDepartment(id));
        return DEPARTMENT_INFO_PAGE;
    }

    /**
     * ½ҳ.
     * 
     * @param model .
     * @return .
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String gotoNew(Model model) {
        model.addAttribute("department", new Department());
        return DEPARTMENT_INFO_PAGE;
    }

    /**
     * 沿.
     * 
     * @param department 
     * @return .
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage save(Department department) {
        departmentService.saveDepartment(department);
        return new JsonMessage(true, "ɹ!");
    }

    /**
     * ɾ.
     * 
     * @param id ID
     * @return .
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage delete(@RequestParam("id")
    String id) {
        departmentService.deleteDepartment(id);
        return new JsonMessage(true, "ɾɹ!");
    }
}

package com.hodanet.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hodanet.common.entity.vo.JsTreeNode;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.constant.PermissionConstants;
import com.hodanet.system.entity.po.Department;
import com.hodanet.system.entity.po.User;
import com.hodanet.system.entity.po.UserLoginInfo;
import com.hodanet.system.service.DepartmentService;
import com.hodanet.system.service.UserLoginInfoService;
import com.hodanet.system.service.UserService;
import com.hodanet.system.util.SecurityUtil;

/**
 * @author lance.lengcs
 * @version 2012-7-24 下午2:24:44
 * 
 * <pre>
 *    用户管理控制器.
 * </pre>
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger  logger                    = Logger.getLogger(UserController.class);

    private static final String  USER_GOTO_REGISTER_PAGE   = "system/user/register";
    private static final String  USER_REGISTER_RESULT_PAGE = "system/user/registerResult";
    private static final String  USER_REDIRECT_LOGIN_PAGE  = "redirect:/home.do";
    private static final String  USER_LIST_PAGE            = "system/user/list";
    private static final String  USER_INFO_PAGE            = "system/user/info";
    private static final String  USER_MODIFY_SELF_PAGE     = "system/user/modifySelf";
    private static final String  USER_CHOOSE_PAGE          = "system/user/choose";

    @Autowired
    private UserService          userService;

    @Autowired
    private DepartmentService    departmentService;

    @Autowired
    private UserLoginInfoService userLoginInfoService;

    /**
     * 用户登录
     * 
     * @param model
     * @param session
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, HttpSession session, @RequestParam("loginId")
    String loginId, @RequestParam("password")
    String password) {

        if (StringUtil.isBlank(loginId) || StringUtil.isBlank(password)) {
            model.addAttribute(PermissionConstants.CONSTANT_PARAM_USER, new User());
            return USER_REDIRECT_LOGIN_PAGE;
        }

        String md5HexPassword = DigestUtils.md5Hex(password);
        User user = userService.getUserByLoginId(loginId);
        if (user != null && StringUtil.equals(md5HexPassword, user.getPassword())) {
            // 获取上次登录时间
            UserLoginInfo userLoginInfo = userLoginInfoService.saveUserLoginInfo(loginId);

            session.setAttribute(PermissionConstants.CONSTANT_PARAM_USER, user);
            session.setAttribute(PermissionConstants.CONSTANT_PARAM_USER_LOGIN_INFO, userLoginInfo);
            session.setAttribute(PermissionConstants.CONSTANT_PARAM_USER_ID, user.getId());
            return USER_REDIRECT_LOGIN_PAGE;
        } else {
            logger.info("用户登录失败,登录id-" + loginId);
            model.addAttribute(PermissionConstants.CONSTANT_PARAM_USER, new User());
            return USER_REDIRECT_LOGIN_PAGE;
        }

    }

    /**
     * 进入用户注册页面
     * 
     * @param model
     */
    @RequestMapping(value = "/gotoRegister")
    public String gotoRegister(Model model) {
        User user = new User();
        model.addAttribute(PermissionConstants.CONSTANT_PARAM_USER, user);
        return USER_GOTO_REGISTER_PAGE;
    }

    /**
     * 用户注册
     * 
     * @param model
     * @param request
     * @param session
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @Valid
    public String register(Model model, @Valid
    User user, BindingResult userBindingResult, HttpServletRequest request, HttpSession session) {

        // 密码md5加密
        String password = SecurityUtil.getMD5(user.getPassword());
        user.setPassword(password);

        user.setStatus(PermissionConstants.CONSTANT_STATUS_1);

        user = userService.saveUser(user);
        model.addAttribute(PermissionConstants.CONSTANT_PARAM_USER, user);
        return USER_REGISTER_RESULT_PAGE;
    }

    /**
     * 打开人员选择页面.
     * 
     * @param pid 默认ID eg. id1,id2,id3
     * @param model .
     * @return .
     */
    @RequestMapping(value = "/choose")
    public String chooseUser(@RequestParam(value = "pid", required = false)
    String pid, @RequestParam("specParam")
    String specParam, Model model) {
        if (pid != null) {
            model.addAttribute("pid", pid);
            model.addAttribute("pidFlag", "_flag");
        }
        model.addAttribute("specParam", specParam);
        return USER_CHOOSE_PAGE;
    }

    /**
     * 获取用户部门结构树(分层加载).
     * 
     * @param parentId .
     * @return .
     */
    @RequestMapping(value = "/getChooseData")
    @ResponseBody
    public List<JsTreeNode> getChooseData(@RequestParam("pid")
    String parentId) {
        List<JsTreeNode> deptNodes = departmentService.getDepartments(parentId);
        for (JsTreeNode jsTreeNode : deptNodes) {
            jsTreeNode.setCheckbox(false);
        }
        if (StringUtil.isNotBlank(parentId)) {
            List<JsTreeNode> userNodes = userService.getUserByDeptId(parentId);

            if (userNodes != null) {
                deptNodes.addAll(userNodes);
            }
        }
        return deptNodes;
    }

    /**
     * 搜索用户的部门，获取父节点ID集合.
     * 
     * @param name 要搜索的内容
     * @return 父节点ID集合
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public List<String> searchUser(@RequestParam("name")
    String name) {
        // TODO
        List<String> resuldIds = userService.searchUser(name);
        return resuldIds;
    }

    /**
     * 修改人员排序.
     * 
     * @param one .
     * @param two .
     * @return .
     */
    @RequestMapping(value = "/order")
    @ResponseBody
    public JsonMessage swapUserOrder(@RequestParam("one")
    String one, @RequestParam("two")
    String two) {

        userService.saveSwapUserOrder(one, two);
        return new JsonMessage(true, "");
    }

    /**
     * 进入人员管理页面.
     * 
     * @return .
     */
    @RequestMapping(value = "/list")
    public String userList() {
        return USER_LIST_PAGE;
    }

    /**
     * 进入人员修改页面.
     * 
     * @param model .
     * @param id 人员ID
     * @return .
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String modify(Model model, @PathVariable("id")
    String id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("department", departmentService.getDepartmentByUser(id));
        return USER_INFO_PAGE;
    }

    /**
     * 进入新建人员页面.
     * 
     * @param model .
     * @return .
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String gotoNew(Model model) {
        model.addAttribute("user", new User());
        return USER_INFO_PAGE;
    }

    /**
     * 保存人员.
     * 
     * @param user 人员
     * @return .
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage save(User user, HttpServletRequest request) {
        if (user == null) {
            return new JsonMessage(false, "操作失败");
        }

        String departmentId = request.getParameter("departmentId");
        if (StringUtil.isNotBlank(departmentId)) {
            user.setDepartment(new Department(departmentId));
        }

        // 密码md5加密
        if (StringUtil.isNotBlank(user.getPassword())) {
            String password = SecurityUtil.getMD5(user.getPassword());
            user.setPassword(password);
        }

        user.setStatus(PermissionConstants.CONSTANT_STATUS_1);

        user = userService.saveUser(user);

        return new JsonMessage(true, "保存成功");
    }

    /**
     * 删除人员.
     * 
     * @param id 人员ID
     * @return .
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage delete(@RequestParam("id")
    String id) {
        userService.deleteUsers(new String[] { id });
        return new JsonMessage(true, "删除成功!");
    }

    /**
     * 用户id重复校验.
     * 
     * @param id 用户ID
     * @return .
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public JsonMessage check(@RequestParam("id")
    String id, @RequestParam("loginId")
    String loginId) {
        User user = userService.getUserByLoginId(loginId);
        if (user != null && !StringUtil.equals(id, user.getId())) {
            return new JsonMessage(false, "登录id重复!");
        }
        return new JsonMessage(true, "");
    }

    /**
     * 进入个人信息修改页面.
     * 
     * @param model .
     * @return .
     */
    @RequestMapping(value = "/modifySelf", method = RequestMethod.GET)
    public String modifySelf(Model model, HttpSession session) {

        if (session == null || session.getAttribute(PermissionConstants.CONSTANT_PARAM_USER_ID) == null) {
            return null;
        }
        String id = (String) session.getAttribute(PermissionConstants.CONSTANT_PARAM_USER_ID);
        model.addAttribute("user", userService.getUserById(id));

        return USER_MODIFY_SELF_PAGE;
    }

    /**
     * 登陆密码正确性校验.
     * 
     * @param id
     * @param password
     * @return .
     */
    @RequestMapping(value = "/checkPassword")
    @ResponseBody
    public JsonMessage checkPassword(@RequestParam("id")
    String id, @RequestParam("password")
    String password) {
        String encyPassword = SecurityUtil.getMD5(password);
        User user = userService.getUserById(id);
        if (user.getPassword().equals(encyPassword)) {
            return new JsonMessage(true, "");
        } else {
            return new JsonMessage(false, "");
        }
    }
}

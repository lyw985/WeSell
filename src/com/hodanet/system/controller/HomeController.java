package com.hodanet.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hodanet.system.constant.PermissionConstants;
import com.hodanet.system.entity.po.Menu;
import com.hodanet.system.entity.po.Module;
import com.hodanet.system.entity.po.User;
import com.hodanet.system.entity.po.UserLoginInfo;
import com.hodanet.system.service.PermissionService;

/**
 * @author lance.lengcs
 * @version 2012-8-16 11:14:39
 * 
 * <pre>
 * ϵͳĬڿ
 * </pre>
 */
@Controller
@RequestMapping(value = "/home")
public class HomeController {

    private static final Logger LOGGER            = Logger.getLogger(HomeController.class);

    private static final String USER_LOGIN_PAGE   = "system/user/login";
    private static final String HOME_PAGE         = "system/home";
    private static final String HOME_WELCOME_PAGE = "system/home/welcome";

    @Autowired
    private PermissionService   permissionService;

    @RequestMapping()
    public String home(Model model, HttpServletRequest request, HttpSession session) {

        if (session != null && session.getAttribute(PermissionConstants.CONSTANT_PARAM_USER) != null) {
            assembleHomeData(model, session);
            return HOME_PAGE;
        }

        model.addAttribute(PermissionConstants.CONSTANT_PARAM_USER, new User());
        return USER_LOGIN_PAGE;
    }

    // װҳ
    private void assembleHomeData(Model model, HttpSession session) {
        String userId = (String) session.getAttribute(PermissionConstants.CONSTANT_PARAM_USER_ID);

        List<Module> modules = permissionService.getModuleListByUserId(userId);

        model.addAttribute("modules", modules);
        model.addAttribute("userLoginInfo",
                           (UserLoginInfo) session.getAttribute(PermissionConstants.CONSTANT_PARAM_USER_LOGIN_INFO));
    }

    /**
     * ϵͳĬҳ.
     * 
     * @return ӭҳַ
     */
    @RequestMapping("/welcome")
    public String welcome(Model model) {

        // TODO
        return HOME_WELCOME_PAGE;
    }

    /**
     * ϵͳ˳,SessionеϢ,AJAX.
     * 
     * @param session Session
     * @return ԶϢ
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public String logout(HttpSession session) {
        session.removeAttribute(PermissionConstants.CONSTANT_PARAM_USER);
        return null;
    }

    /**
     * ȡһӲ˵.
     * 
     * @param moduleCode ϵͳ
     * @return һ˵б
     */
    @RequestMapping(value = "/getTopMenus", method = RequestMethod.POST)
    @ResponseBody
    public List<Menu> getTopMenus(@RequestParam("moduleId")
    String moduleId, HttpSession session) {
        String userId = (String) session.getAttribute(PermissionConstants.CONSTANT_PARAM_USER_ID);
        return permissionService.getMenuList(userId, moduleId, PermissionConstants.CONSTANT_ROOT_PARENT_ID);
    }

    /**
     * ȡһӲ˵.
     * 
     * @param moduleId ϵͳID
     * @param parentMenuId ˵ID
     * @return һ˵б
     */
    @RequestMapping(value = "/getNotTopMenus", method = RequestMethod.POST)
    @ResponseBody
    public List<Menu> getNotTopMenus(@RequestParam("moduleId")
    String moduleId, @RequestParam("parentMenuId")
    String parentMenuId, HttpSession session) {
        String userId = (String) session.getAttribute(PermissionConstants.CONSTANT_PARAM_USER_ID);

        return permissionService.getMenuList(userId, moduleId, parentMenuId);
    }
}

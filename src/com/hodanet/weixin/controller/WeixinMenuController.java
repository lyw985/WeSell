package com.hodanet.weixin.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.util.CommonPropUtil;
import com.hodanet.system.util.HttpURLConnectionUtil;
import com.hodanet.weixin.constant.WeixinApiConstants;

@Controller
@RequestMapping(value = "/weixin/menu")
public class WeixinMenuController {

    private final Logger        log              = Logger.getLogger(WeixinMenuController.class);
    private static final String WEIXIN_MENU_LIST = "weixin/menu/list";

    @RequestMapping(value = "/list")
    public String list(Model model, @RequestParam("serverCode")
    String serverCode) {

        String accessToken = CommonPropUtil.getAccessTokenByServerCode(serverCode);
        if (accessToken != null) {
            log.info("menu list param  " + WeixinApiConstants.getMenuUrl + accessToken);

            String result = HttpURLConnectionUtil.process(WeixinApiConstants.getMenuUrl + accessToken, "");
            // String result =
            // "{'menu':{'button':[{'type':'click','name':'ո','key':'V1001_TODAY_MUSIC','sub_button':[]},{'type':'click','name':'ּ','key':'V1001_TODAY_SINGER','sub_button':[]},{'name':'˵','sub_button':[{'type':'view','name':'','url':'http://www.soso.com/','sub_button':[]},{'type':'view','name':'Ƶ','url':'http://v.qq.com/','sub_button':[]},{'type':'click','name':'һ','key':'V1001_GOOD','sub_button':[]}]}]}}";
            log.info("menu list result  " + result);
            if (StringUtil.isNotBlank(result)) {
                JSONObject object = JSONObject.parseObject(result);
                if (object.get("menu") != null) {
                    JSONObject menuObject = object.getJSONObject("menu");
                    JSONArray buttonObject = menuObject.getJSONArray("button");
                    // һ˵
                    model.addAttribute("buttonObject", buttonObject);
                }
            }
        }
        model.addAttribute("serverCode", serverCode);
        return WEIXIN_MENU_LIST;
    }
}

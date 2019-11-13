package com.hodanet.weixin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.entity.vo.JsonMessage;
import com.hodanet.common.util.StringUtil;
import com.hodanet.common.util.WebUtil;
import com.hodanet.system.util.CommonPropUtil;
import com.hodanet.system.util.HttpURLConnectionUtil;
import com.hodanet.weixin.constant.WeixinApiConstants;
import com.hodanet.weixin.entity.weixin.WeixinServerProp;

@Controller
@RequestMapping(value = "/weixin/msg")
public class WeixinMessageController {

    private final Logger        log                          = Logger.getLogger(WeixinMessageController.class);
    private static final String WEIXIN_API_LIST              = "weixin/message/list";
    private static final String WEIXIN_API_SEND_CUSTOMER_MSG = "weixin/message/sendCustomerMsg";

    @RequestMapping(value = "/list")
    public String list(Model model, @RequestParam("serverCode")
    String serverCode) {
        model.addAttribute("accessToken", CommonPropUtil.getAccessTokenByServerCode(serverCode));
        model.addAttribute("serverCode", serverCode);
        return WEIXIN_API_LIST;
    }

    /**
     * ȡaccessToken
     * 
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(value = "/getAccessToken")
    public String getAccessToken(Model model, @RequestParam("serverCode")
    String serverCode) {
        Map<String, WeixinServerProp> weixinServerPropMap = CommonPropUtil.weixinServerPropMap;
        // ѭȡ
        if (weixinServerPropMap.containsKey(serverCode)) {
            WeixinServerProp weixinServerProp = weixinServerPropMap.get(serverCode);
            String param = "grant_type=client_credential&appid=" + weixinServerProp.getAppId() + "&secret="
                           + weixinServerProp.getAppSecret();
            String result = HttpURLConnectionUtil.process(WeixinApiConstants.getAccessTokenUrl, param);
            String accessToken = "";
            if (StringUtil.isNotBlank(result)) {
                JSONObject object = JSONObject.parseObject(result);
                if (object.get("access_token") != null) {
                    accessToken = (String) object.get("access_token");
                }
                weixinServerProp.setAccessToken(accessToken);
            }
        }

        model.addAttribute("accessToken", CommonPropUtil.getAccessTokenByServerCode(serverCode));
        model.addAttribute("serverCode", serverCode);
        return WEIXIN_API_LIST;
    }

    /***
     * תϢҳ
     * 
     * @param model
     * @param serverCode
     * @return
     */
    @RequestMapping(value = "/gotoSendCustomMsg")
    public String gotoSendCustomMsg(Model model, @RequestParam("serverCode")
    String serverCode) {
        model.addAttribute("serverCode", serverCode);
        return WEIXIN_API_SEND_CUSTOMER_MSG;
    }

    /**
     * ͿͷϢҪͨӦȨ
     * 
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(value = "/sendCustomMsg")
    public void sendCustomMsg(Model model, @RequestParam("serverCode")
    String serverCode, @RequestParam("openid")
    String openid, @RequestParam("content")
    String content, HttpServletResponse response) {

        String accessToken = CommonPropUtil.getAccessTokenByServerCode(serverCode);
        JSONObject subJsonObject = new JSONObject();
        subJsonObject.put("content", content);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", openid);
        jsonObject.put("msgtype", "text");
        jsonObject.put("text", subJsonObject);

        String param = JSONObject.toJSONString(jsonObject);

        log.info("sendCustomMsg param:" + param);

        HttpURLConnectionUtil.process(WeixinApiConstants.sendCustomMsgUrl + accessToken, param);

        WebUtil.responseText(response, JSONObject.toJSONString(new JsonMessage(true, "")));
    }

}

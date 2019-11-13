package com.hodanet.weixin.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.util.CommonPropUtil;
import com.hodanet.system.util.HttpURLConnectionUtil;
import com.hodanet.weixin.constant.WeixinApiConstants;

@Controller
@RequestMapping(value = "/weixin/follower")
public class WeixinFollowerController {

    private final Logger        log                  = Logger.getLogger(WeixinFollowerController.class);
    private static final String WEIXIN_FOLLOWER_LIST = "weixin/follower/list";
    private static final String WEIXIN_FOLLOWER_INFO = "weixin/follower/info";

    @RequestMapping(value = "/list")
    public String list(Model model, @RequestParam("serverCode")
    String serverCode) {

        String accessToken = CommonPropUtil.getAccessTokenByServerCode(serverCode);
        if (accessToken != null) {
            log.info("follower list param  " + WeixinApiConstants.getFollowerListUrl + accessToken);
            String result = HttpURLConnectionUtil.process(WeixinApiConstants.getFollowerListUrl + accessToken, "");
            // String result =
            // "{"total":2,"count":2,"data":{"openid":["","OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}";
            log.info("follower list result  " + result);

            if (StringUtil.isNotBlank(result)) {
                JSONObject object = JSONObject.parseObject(result);
                model.addAttribute("follower", object);
            }
        }
        model.addAttribute("serverCode", serverCode);
        return WEIXIN_FOLLOWER_LIST;
    }

    /**
     * @param model
     * @param openid { "subscribe": 1, "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", "nickname": "Band", "sex": 1,
     * "language": "zh_CN", "city": "", "province": "㶫", "country": "й", "headimgurl":
     * "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0"
     * , "subscribe_time": 1382694957 }
     * @return
     */
    @RequestMapping(value = "/info")
    public String info(Model model, @RequestParam("openid")
    String openid, @RequestParam("serverCode")
    String serverCode) {
        String accessToken = CommonPropUtil.getAccessTokenByServerCode(serverCode);
        if (accessToken != null) {
            log.info("follower info param  " + WeixinApiConstants.getFollowerInfoUrl + accessToken + "&openid="
                     + openid);
            String result = HttpURLConnectionUtil.process(WeixinApiConstants.getFollowerInfoUrl + accessToken
                                                          + "&openid=" + openid, "");
            // String result =
            // "{'total':2,'count':2,'data':{'openid':['','OPENID1','OPENID2']},'next_openid':'NEXT_OPENID'}";
            log.info("follower info result  " + result);

            if (StringUtil.isNotBlank(result)) {
                JSONObject object = JSONObject.parseObject(result);
                model.addAttribute("follower", object);
            }
        }
        model.addAttribute("serverCode", serverCode);
        return WEIXIN_FOLLOWER_INFO;
    }
}

package com.hodanet.weixin.task;

import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.util.StringUtil;
import com.hodanet.system.util.CommonPropUtil;
import com.hodanet.system.util.HttpURLConnectionUtil;
import com.hodanet.weixin.constant.WeixinApiConstants;
import com.hodanet.weixin.entity.weixin.WeixinServerProp;

public class AutoGetAccessTokenTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        getAccessToken();
        System.out.println("2-scheduled");
    }

    public static void getAccessToken() {
        Map<String, WeixinServerProp> weixinServerPropMap = CommonPropUtil.weixinServerPropMap;
        // ѭȡ
        if (weixinServerPropMap != null && weixinServerPropMap.size() > 0) {
            for (WeixinServerProp weixinServerProp : weixinServerPropMap.values()) {
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
        }
        for (WeixinServerProp weixinServerProp : weixinServerPropMap.values()) {
            System.out.println(weixinServerProp.getName() + ":" + weixinServerProp.getAccessToken());
        }
    }
}

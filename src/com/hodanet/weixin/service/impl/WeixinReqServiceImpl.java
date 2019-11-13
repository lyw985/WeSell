package com.hodanet.weixin.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.util.FileUtil;
import com.hodanet.system.util.HttpURLConnectionUtil;
import com.hodanet.weixin.constant.WeixinApiConstants;
import com.hodanet.weixin.entity.weixin.customer.WeixinBasicCustomerMsg;
import com.hodanet.weixin.service.WeixinReqService;

/**
 * @anthor lyw
 * @version 2014-9-26 1:53:11
 */
@Service
public class WeixinReqServiceImpl implements WeixinReqService {

    @Override
    public JSONObject uploadMedia(String accessToken, String type, File file) {
        JSONObject jsonObject = new JSONObject();
        try {
            String contentType = "image/jpeg";
            String resultStr = HttpURLConnectionUtil.processFile(WeixinApiConstants.uploadMediaUrl, accessToken, type,
                                                                 file.getName(), (int) file.length(), contentType,
                                                                 new FileInputStream(file));
            return JSONObject.parseObject(resultStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void getMedia(String accessToken, String media_id, String toFilePath) {
        try {
            URL url = new URL(WeixinApiConstants.getMediaUrl + accessToken + "&media_id=" + media_id);
            URLConnection con = url.openConnection();
            FileUtil.copy(con.getInputStream(), new FileOutputStream(toFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendCustomerMsg(String accessToken, WeixinBasicCustomerMsg weixinBasicCustomerMsg) {
        HttpURLConnectionUtil.process(WeixinApiConstants.sendCustomMsgUrl + accessToken,
                                      weixinBasicCustomerMsg.toJsonObject().toJSONString());
    }

    public static void main(String[] args) {
        WeixinReqServiceImpl reqServiceImpl = new WeixinReqServiceImpl();
        reqServiceImpl.getMedia("HIOLMaAGuMl6VOZpgOcqSSJF5l8aJgNRrDUbzGHrWOmfF5Vqzd2UBvC2Rwevy-lQ19dNgiOW-RiIXEcGo45_6Q",
                                "WLt402sqVVvesNknpCI43gBMyfe59gdU_Sbf3H99YOX9JqtL6u4a_fCd7xq8f6CA",
                                "d:/test123456789.jpg");
    }

}

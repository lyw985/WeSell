package com.hodanet.weixin.service;

import java.io.File;

import com.alibaba.fastjson.JSONObject;
import com.hodanet.weixin.entity.weixin.customer.WeixinBasicCustomerMsg;

/**
 * ΢
 * 
 * @anthor lyw
 * @version 2014-9-26 1:47:21
 */
public interface WeixinReqService {

    /**
     * ϴýļ
     * 
     * @param accessToken
     * @param type
     * @param file
     * @return
     */
    public JSONObject uploadMedia(String accessToken, String type, File file);

    /***
     * ȡýļ
     * 
     * @param accessToken
     * @param media_id
     * @param toFilePath
     */
    public void getMedia(String accessToken, String media_id, String toFilePath);

    public void sendCustomerMsg(String accessToken, WeixinBasicCustomerMsg weixinBasicCustomerMsg);

}

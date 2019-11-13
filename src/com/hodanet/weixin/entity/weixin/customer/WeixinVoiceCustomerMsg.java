package com.hodanet.weixin.entity.weixin.customer;

import com.alibaba.fastjson.JSONObject;

/**
 * ͼƬͷϢ
 * 
 * @anthor lyw
 * @version 2014-9-26 4:02:29
 */
public class WeixinVoiceCustomerMsg extends WeixinBasicCustomerMsg {

    private String media_id;//͵ýID

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = getBasicJsonObject();
        JSONObject sonJsonObject = new JSONObject();
        sonJsonObject.put("media_id", media_id);
        jsonObject.put("voice", sonJsonObject);
        return jsonObject;
    }
}

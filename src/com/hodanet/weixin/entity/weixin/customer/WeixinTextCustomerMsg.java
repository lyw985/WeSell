package com.hodanet.weixin.entity.weixin.customer;

import com.alibaba.fastjson.JSONObject;

/**
 * ıͷϢ
 * @anthor lyw
 * @version 2014-9-26 4:02:29
 */
public class WeixinTextCustomerMsg extends WeixinBasicCustomerMsg {

    private String content;//ıϢ

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = getBasicJsonObject();
        JSONObject sonJsonObject = new JSONObject();
        sonJsonObject.put("content", content);
        jsonObject.put("text", sonJsonObject);
        return jsonObject;
    }
}

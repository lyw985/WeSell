package com.hodanet.weixin.entity.weixin.customer;

import com.alibaba.fastjson.JSONObject;

/**
 * ͷϢ
 * 
 * @anthor lyw
 * @version 2014-9-26 3:58:06
 */
public abstract class WeixinBasicCustomerMsg {

    protected String touser;//ͨûopenid
    protected String msgtype;//Ϣ

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public JSONObject getBasicJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", touser);
        jsonObject.put("msgtype", msgtype);
        return jsonObject;
    }

    public abstract JSONObject toJsonObject();

}

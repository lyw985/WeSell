package com.hodanet.weixin.entity.weixin.resp;

import com.hodanet.weixin.entity.weixin.req.WeiXinBasicReqMsg;

/**
 * ΢Ϣظ
 * 
 * @anthor lyw
 * @version 2014-4-14 3:01:54
 */
public abstract class WeiXinBasicRespMsg {

    protected String  toUserName;
    protected String  fromUserName;
    protected Integer createTime;
    protected String  msgType;

    protected WeiXinBasicRespMsg(WeiXinBasicReqMsg reqMsg, String msgType){
        this.toUserName = reqMsg.getFromUserName();
        this.fromUserName = reqMsg.getToUserName();
        this.createTime = reqMsg.getCreateTime();
        this.msgType = msgType;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /***
     * װسɷ͸΢ŵXMLʽַ
     * 
     * @return
     */
    public abstract String toRespXmlStr();

}

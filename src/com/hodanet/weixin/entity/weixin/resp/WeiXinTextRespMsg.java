package com.hodanet.weixin.entity.weixin.resp;

import com.hodanet.weixin.constant.WeixinMessageType;
import com.hodanet.weixin.entity.weixin.req.WeiXinBasicReqMsg;

/**
 * ΢ıϢظ
 * 
 * @anthor lyw
 * @version 2014-4-14 3:05:30
 */
public class WeiXinTextRespMsg extends WeiXinBasicRespMsg {

    private String content;

    public WeiXinTextRespMsg(WeiXinBasicReqMsg reqMsg, String content){
        super(reqMsg, WeixinMessageType.TEXT.getType());
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toRespXmlStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(toUserName);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(fromUserName);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(createTime);
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType>");
        sb.append("<Content><![CDATA[");
        sb.append(content);
        sb.append("]]></Content>");
        sb.append("<FuncFlag>0</FuncFlag></xml>");
        return sb.toString();
    }

}

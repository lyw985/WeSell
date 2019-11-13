package com.hodanet.weixin.entity.weixin.resp;

import com.hodanet.weixin.constant.WeixinMessageType;
import com.hodanet.weixin.entity.weixin.req.WeiXinBasicReqMsg;

/**
 * ΢ͼƬϢظ
 * 
 * @anthor lyw
 * @version 2014-4-14 3:05:30
 */
public class WeiXinImageRespMsg extends WeiXinBasicRespMsg {

    private String content;
    private String mediaId;

    public WeiXinImageRespMsg(WeiXinBasicReqMsg reqMsg, String content){
        super(reqMsg, WeixinMessageType.IMAGE.getType());
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
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

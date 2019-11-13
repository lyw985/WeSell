package com.hodanet.weixin.entity.weixin.req;

import java.io.IOException;
import java.util.Map;

import org.jdom.JDOMException;

import com.hodanet.weixin.constant.WeixinMessageType;
import com.hodanet.weixin.util.XmlUtil;

/**
 * ΢Ϣջ
 * 
 * @anthor lyw
 * @version 2014-4-10 5:38:37
 */
public abstract class WeiXinBasicReqMsg {

    // ToUserName ΢ź
    // FromUserName ͷʺţһOpenID
    // CreateTime Ϣʱ ͣ
    protected String  toUserName;
    protected String  fromUserName;
    protected Integer createTime;
    protected String  msgType;

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

    protected void parseBasicParameter(Map<String, String> map) {
        this.toUserName = map.get("xml.ToUserName");
        this.fromUserName = map.get("xml.FromUserName");
        this.createTime = Integer.parseInt(map.get("xml.CreateTime"));
        this.msgType = map.get("xml.MsgType");
    }

    /***
     * MapװضӦϢ
     * 
     * @param map
     */
    protected abstract void parseMessageParameter(Map<String, String> map);

    public static WeiXinBasicReqMsg parseXmlRequest(String xmlRequest) throws JDOMException, IOException {
        Map<String, String> map = XmlUtil.xml2Map(xmlRequest);
        String msgType = map.get("xml.MsgType");
        WeixinMessageType weixinMessageType = WeixinMessageType.getWeixinMessageType(msgType);
        WeiXinBasicReqMsg reqMsg = null;
        switch (weixinMessageType) {
            case TEXT:
                reqMsg = new WeiXinTextReqMsg();
                break;
            case IMAGE:
                reqMsg = new WeiXinImageReqMsg();
                break;
            case VOICE:
                reqMsg = new WeiXinVoiceReqMsg();
                break;
            case VIDEO:
                reqMsg = new WeiXinVideoReqMsg();
                break;
            case LOCATION:
                reqMsg = new WeiXinLocationReqMsg();
                break;
            case LINK:
                reqMsg = new WeiXinLinkReqMsg();
                break;
            case EVENT:
                String event = map.get("xml.Event");
                if ("CLICK".equals(event) || "VIEW".equals(event)) {
                    // ˵ȡϢʱ¼
                    reqMsg = new WeiXinMenuEventReqMsg();
                } else if ("subscribe".equals(event) || "unsubscribe".equals(event)) {
                    // ע/ȡע¼
                    reqMsg = new WeiXinSubscribeEventReqMsg();
                } else {
                    // TODO ¼
                    // ɨά¼
                    // ϱλ¼
                    // Զ˵¼
                    // ˵תʱ¼
                    return null;
                }
                break;
            default:
                return null;
        }
        reqMsg.parseMessageParameter(map);
        return reqMsg;
    }

}

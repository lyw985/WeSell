package com.hodanet.weixin.entity.weixin.req;

import java.util.Map;

/**
 * ΢Ϣ
 * 
 * @anthor lyw
 * @version 2014-4-10 5:38:37
 */
public class WeiXinLinkReqMsg extends WeiXinBasicReqMsg {

    // MsgId Ϣid64λ
    // Title Ϣ
    // Description Ϣ
    // Url Ϣ
    private Long   msgId;
    private String title;
    private String description;
    private String url;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected void parseMessageParameter(Map<String, String> map) {
        parseBasicParameter(map);
        this.msgId = Long.parseLong(map.get("xml.MsgId"));
        this.title = map.get("xml.Title");
        this.description = map.get("xml.Description");
        this.url = map.get("xml.Url");
    }

}

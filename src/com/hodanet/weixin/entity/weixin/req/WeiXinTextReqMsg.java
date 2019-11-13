package com.hodanet.weixin.entity.weixin.req;

import java.util.Map;

/**
 * ΢ıϢ
 * 
 * @anthor lyw
 * @version 2014-4-10 5:38:37
 */
public class WeiXinTextReqMsg extends WeiXinBasicReqMsg {

    // MsgId Ϣid64λ
    private Long   msgId;
    private String content;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void parseMessageParameter(Map<String, String> map) {
        parseBasicParameter(map);
        this.msgId = Long.parseLong(map.get("xml.MsgId"));
        this.content = map.get("xml.Content");
    }

}

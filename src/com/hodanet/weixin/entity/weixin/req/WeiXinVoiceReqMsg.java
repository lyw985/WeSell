package com.hodanet.weixin.entity.weixin.req;

import java.util.Map;

/**
 * ΢Ϣ
 * 
 * @anthor lyw
 * @version 2014-4-10 5:38:37
 */
public class WeiXinVoiceReqMsg extends WeiXinBasicReqMsg {

    // MsgId Ϣid64λ
    // MediaId ͼƬϢýidԵöýļؽӿȡݡ
    // Format ʽamrspeex
    private Long   msgId;
    private String mediaId;
    private String format;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    protected void parseMessageParameter(Map<String, String> map) {
        parseBasicParameter(map);
        this.msgId = Long.parseLong(map.get("xml.MsgId"));
        this.mediaId = map.get("xml.MediaId");
        this.format = map.get("xml.Format");
    }

}

package com.hodanet.weixin.entity.weixin.req;

import java.util.Map;

/**
 * ΢ͼƬϢ
 * 
 * @anthor lyw
 * @version 2014-4-10 5:38:37
 */
public class WeiXinImageReqMsg extends WeiXinBasicReqMsg {

    // MsgId Ϣid64λ
    // MediaId ͼƬϢýidԵöýļؽӿȡݡ
    private Long   msgId;
    private String mediaId;

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

    @Override
    protected void parseMessageParameter(Map<String, String> map) {
        parseBasicParameter(map);
        this.msgId = Long.parseLong(map.get("xml.MsgId"));
        this.mediaId = map.get("xml.MediaId");
    }

}

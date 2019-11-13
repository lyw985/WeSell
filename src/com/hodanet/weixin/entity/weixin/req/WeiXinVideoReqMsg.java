package com.hodanet.weixin.entity.weixin.req;

import java.util.Map;

/**
 * ΢ƵϢ
 * 
 * @anthor lyw
 * @version 2014-4-10 5:38:37
 */
public class WeiXinVideoReqMsg extends WeiXinBasicReqMsg {

    // MsgId Ϣid64λ
    // MediaId ͼƬϢýidԵöýļؽӿȡݡ
    // ThumbMediaId ƵϢͼýidԵöýļؽӿȡݡ
    private Long   msgId;
    private String mediaId;
    private String thumbMediaId;

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getMsgId() {
        return msgId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    @Override
    protected void parseMessageParameter(Map<String, String> map) {
        parseBasicParameter(map);
        this.msgId = Long.parseLong(map.get("xml.MsgId"));
        this.mediaId = map.get("xml.MediaId");
        this.thumbMediaId = map.get("xml.ThumbMediaId");
    }

}

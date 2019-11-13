package com.hodanet.weixin.entity.weixin.req;

import java.util.Map;

/**
 * ΢λϢ
 * 
 * @anthor lyw
 * @version 2014-4-10 5:38:37
 */
public class WeiXinLocationReqMsg extends WeiXinBasicReqMsg {

    // MsgId Ϣid64λ
    // Location_X λά
    // Location_Y λþ
    // Scale ͼŴС
    // Label λϢ
    private Long   msgId;
    private String location_x;
    private String location_y;
    private String scale;
    private String label;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getLocation_x() {
        return location_x;
    }

    public void setLocation_x(String location_x) {
        this.location_x = location_x;
    }

    public String getLocation_y() {
        return location_y;
    }

    public void setLocation_y(String location_y) {
        this.location_y = location_y;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    protected void parseMessageParameter(Map<String, String> map) {
        parseBasicParameter(map);
        this.msgId = Long.parseLong(map.get("xml.MsgId"));
        this.location_x = map.get("xml.Location_X");
        this.location_y = map.get("xml.Location_Y");
        this.scale = map.get("xml.Scale");
        this.label = map.get("xml.Label");
    }

}

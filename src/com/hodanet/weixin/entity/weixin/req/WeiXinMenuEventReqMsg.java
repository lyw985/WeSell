package com.hodanet.weixin.entity.weixin.req;

import java.util.Map;

/**
 * ΢Ų˵¼Ϣ
 * 
 * @anthor lyw
 * @version 2014-4-10 5:38:37
 */
public class WeiXinMenuEventReqMsg extends WeiXinBasicReqMsg {

    // Event ¼ͣCLICK VIEW
    // EventKey CLICK:Զ˵ӿKEYֵӦ VIEW:õתURL
    private String event;
    private String eventKey;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @Override
    protected void parseMessageParameter(Map<String, String> map) {
        parseBasicParameter(map);
        this.event = map.get("xml.Event");
        this.eventKey = map.get("xml.EventKey");
    }

}

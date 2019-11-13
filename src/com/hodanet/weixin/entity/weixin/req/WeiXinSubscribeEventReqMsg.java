package com.hodanet.weixin.entity.weixin.req;

import java.util.Map;

/**
 * ΢Ŷĺȡ¼Ϣ
 * 
 * @anthor lyw
 * @version 2014-4-10 5:38:37
 */
public class WeiXinSubscribeEventReqMsg extends WeiXinBasicReqMsg {

    // Event ¼ͣsubscribe()unsubscribe(ȡ)
    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    protected void parseMessageParameter(Map<String, String> map) {
        parseBasicParameter(map);
        this.event = map.get("xml.Event");
    }

}

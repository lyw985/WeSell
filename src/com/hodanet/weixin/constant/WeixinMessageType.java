package com.hodanet.weixin.constant;

public enum WeixinMessageType {
    TEXT("text", "文本消息"), IMAGE("image", "图片消息"), VOICE("voice", "语音消息"), VIDEO("video", "视频消息"), LOCATION("location",
                                                                                                           "地理位置消息"),
    LINK("link", "链接消息"), EVENT("event", "事件消息"), TUWEN("tuwen", "图文消息");

    private String type;
    private String tip;

    private WeixinMessageType(String type, String tip){
        this.type = type;
        this.tip = tip;
    }

    public String getType() {
        return type;
    }

    public String getTip() {
        return tip;
    }

    public static WeixinMessageType getWeixinMessageType(String type) {
        for (WeixinMessageType messageType : WeixinMessageType.values()) {
            if (messageType.getType().equals(type)) {
                return messageType;
            }
        }
        return null;
    }
}

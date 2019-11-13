package com.hodanet.weixin.entity.weixin.customer;

import com.alibaba.fastjson.JSONObject;

/**
 * ͼƬͷϢ
 * 
 * @anthor lyw
 * @version 2014-9-26 4:02:29
 */
public class WeixinVideoCustomerMsg extends WeixinBasicCustomerMsg {

    private String media_id;       // ͵ƵýID
    private String thumb_media_id; // ͼýID
    private String title;          // ƵϢı
    private String description;    // ƵϢ

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
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

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = getBasicJsonObject();
        JSONObject sonJsonObject = new JSONObject();
        sonJsonObject.put("media_id", media_id);
        sonJsonObject.put("thumb_media_id", thumb_media_id);
        sonJsonObject.put("title", title);
        sonJsonObject.put("description", description);
        jsonObject.put("video", sonJsonObject);
        return jsonObject;
    }
}

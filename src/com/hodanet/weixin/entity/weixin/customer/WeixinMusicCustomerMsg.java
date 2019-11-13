package com.hodanet.weixin.entity.weixin.customer;

import com.alibaba.fastjson.JSONObject;

/**
 * ͼƬͷϢ
 * 
 * @anthor lyw
 * @version 2014-9-26 4:02:29
 */
public class WeixinMusicCustomerMsg extends WeixinBasicCustomerMsg {

    private String title;         // ֱ
    private String description;   // 
    private String musicurl;      // 
    private String hqmusicurl;    // ƷӣwifiʹøӲ
    private String thumb_media_id; // ͼýID

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

    public String getMusicurl() {
        return musicurl;
    }

    public void setMusicurl(String musicurl) {
        this.musicurl = musicurl;
    }

    public String getHqmusicurl() {
        return hqmusicurl;
    }

    public void setHqmusicurl(String hqmusicurl) {
        this.hqmusicurl = hqmusicurl;
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = getBasicJsonObject();
        JSONObject sonJsonObject = new JSONObject();
        sonJsonObject.put("title", title);
        sonJsonObject.put("description", description);
        sonJsonObject.put("musicurl", musicurl);
        sonJsonObject.put("hqmusicurl", hqmusicurl);
        sonJsonObject.put("thumb_media_id", thumb_media_id);
        jsonObject.put("music", sonJsonObject);
        return jsonObject;
    }
}

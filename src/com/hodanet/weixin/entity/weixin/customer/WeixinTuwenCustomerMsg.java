package com.hodanet.weixin.entity.weixin.customer;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hodanet.weixin.entity.weixin.TuwenMsg;

/**
 * ıͷϢ
 * 
 * @anthor lyw
 * @version 2014-9-26 4:02:29
 */
public class WeixinTuwenCustomerMsg extends WeixinBasicCustomerMsg {

    private List<TuwenMsg> tuwenList = new ArrayList<TuwenMsg>();

    public List<TuwenMsg> getTuwenList() {
        return tuwenList;
    }

    public void setTuwenList(List<TuwenMsg> tuwenList) {
        this.tuwenList = tuwenList;
    }

    public void addTuwen(String title, String description, String picUrl, String url) {
        tuwenList.add(new TuwenMsg(title, description, picUrl, url));
    }

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = getBasicJsonObject();
        JSONObject sonJsonObject = new JSONObject();
        JSONArray articles = new JSONArray();
        for (TuwenMsg tuwen : tuwenList) {
            JSONObject tuwenJsonObject = new JSONObject();
            tuwenJsonObject.put("title", tuwen.getTitle());
            tuwenJsonObject.put("description", tuwen.getDescription());
            tuwenJsonObject.put("url", tuwen.getUrl());
            tuwenJsonObject.put("picurl", tuwen.getPicUrl());
            articles.add(tuwenJsonObject);
        }
        sonJsonObject.put("articles", articles);
        jsonObject.put("news", sonJsonObject);
        return jsonObject;
    }

}

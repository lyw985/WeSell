package com.hodanet.weixin.entity.weixin;

/**
 * ΢ŷز
 * 
 * @anthor lyw
 * @version 2014-4-10 11:22:26
 */
public class WeixinServerProp {

    private String name;
    private String token;
    private String appId;
    private String appSecret;
    private String accessToken;

    public WeixinServerProp(){

    }

    public WeixinServerProp(String name, String token, String appId, String appSecret){
        this.name = name;
        this.token = token;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}

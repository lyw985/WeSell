package com.hodanet.weixin.constant;

public class WeixinApiConstants {

    /**
     * ȡaccess token ӿڵurl
     * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     */
    public static final String getAccessTokenUrl  = "https://api.weixin.qq.com/cgi-bin/token";

    /**
     * Զ˵ѯӿڵurl https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN
     */
    public static final String getMenuUrl         = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";

    /**
     * עбѯӿڵurl https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
     * һȡȡ10000עߵOpenIDһȡOPENIDĬϴͷʼȡ
     */
    public static final String getFollowerListUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=";

    /**
     * ȡûϢӿڵurl https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID
     */
    public static final String getFollowerInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
    
    /**
     * ͷϢ ӿڵurl https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     */
    public static final String sendCustomMsgUrl   = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    
    /***
     * ϴýӿڵurl http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
     */
    public static final String uploadMediaUrl="http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=";
    
    /***
     * ȡýӿڵurl http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
     */
    public static final String getMediaUrl="http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";

}

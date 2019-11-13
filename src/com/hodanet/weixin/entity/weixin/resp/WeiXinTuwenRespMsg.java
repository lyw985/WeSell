package com.hodanet.weixin.entity.weixin.resp;

import java.util.ArrayList;
import java.util.List;

import com.hodanet.weixin.constant.WeixinMessageType;
import com.hodanet.weixin.entity.weixin.TuwenMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinBasicReqMsg;

/**
 * ΢ıϢظ
 * 
 * @anthor lyw
 * @version 2014-4-14 3:05:30
 */
public class WeiXinTuwenRespMsg extends WeiXinBasicRespMsg {

    private String         content;

    private List<TuwenMsg> tuwenList = new ArrayList<TuwenMsg>();

    public WeiXinTuwenRespMsg(WeiXinBasicReqMsg reqMsg){
        super(reqMsg, WeixinMessageType.TUWEN.getType());
    }

    public void addTuwen(String title, String description, String picUrl, String url) {
        tuwenList.add(new TuwenMsg(title, description, picUrl, url));
    }

    // <xml>
    // <ToUserName><![CDATA[toUser]]></ToUserName>
    // <FromUserName><![CDATA[fromUser]]></FromUserName>
    // <CreateTime>12345678</CreateTime>
    // <MsgType><![CDATA[news]]></MsgType>
    // <ArticleCount>2</ArticleCount>
    // <Articles>
    // <item>
    // <Title><![CDATA[title1]]></Title>
    // <Description><![CDATA[description1]]></Description>
    // <PicUrl><![CDATA[picurl]]></PicUrl>
    // <Url><![CDATA[url]]></Url>
    // </item>
    // <item>
    // <Title><![CDATA[title]]></Title>
    // <Description><![CDATA[description]]></Description>
    // <PicUrl><![CDATA[picurl]]></PicUrl>
    // <Url><![CDATA[url]]></Url>
    // </item>
    // </Articles>
    // </xml>
    @Override
    public String toRespXmlStr() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>");
        sb.append("<CreateTime>").append(createTime).append("</CreateTime>");
        sb.append("<MsgType><![CDATA[news]]></MsgType>");
        sb.append("<ArticleCount>").append(tuwenList.size()).append("</ArticleCount>");
        sb.append("<Articles>");

        for (TuwenMsg tuwen : tuwenList) {
            sb.append("<item>");
            sb.append("<Title><![CDATA[").append(tuwen.getTitle()).append("]]></Title>");
            sb.append("<Description><![CDATA[").append(tuwen.getDescription()).append("]]></Description>");
            sb.append("<PicUrl><![CDATA[").append(tuwen.getPicUrl()).append("]]></PicUrl>");
            sb.append("<Url><![CDATA[").append(tuwen.getUrl()).append("]]></Url>");
            sb.append("</item>");
        }

        sb.append("</Articles>");
        sb.append("<FuncFlag>0</FuncFlag></xml>");
        return sb.toString();
    }

}

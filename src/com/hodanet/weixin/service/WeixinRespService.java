package com.hodanet.weixin.service;

import com.hodanet.weixin.entity.weixin.req.WeiXinImageReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinLinkReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinLocationReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinMenuEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinSubscribeEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinTextReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinVideoReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinVoiceReqMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinBasicRespMsg;

/**
 * ΢Ϣظ
 * @anthor lyw
 * @version 2014-4-10 1:20:13
 */
public interface WeixinRespService {

    /***
     * ȡûıϢظϢ
     * 
     * @param reqMsg
     * @return
     */
    public WeiXinBasicRespMsg getRespByTextReqMsg(WeiXinTextReqMsg reqMsg);

    /***
     * ȡûͼƬϢظϢ
     * 
     * @param reqMsg
     * @return
     */
    public WeiXinBasicRespMsg getRespByImageReqMsg(WeiXinImageReqMsg reqMsg);

    /***
     * ȡûϢظϢ
     * 
     * @param reqMsg
     * @return
     */
    public WeiXinBasicRespMsg getRespByVoiceReqMsg(WeiXinVoiceReqMsg reqMsg);

    /***
     * ȡûƵϢظϢ
     * 
     * @param reqMsg
     * @return
     */
    public WeiXinBasicRespMsg getRespByVideoReqMsg(WeiXinVideoReqMsg reqMsg);

    /***
     * ȡûλϢظϢ
     * 
     * @param reqMsg
     * @return
     */
    public WeiXinBasicRespMsg getRespByLocationReqMsg(WeiXinLocationReqMsg reqMsg);

    /***
     * ȡûϢظϢ
     * 
     * @param reqMsg
     * @return
     */
    public WeiXinBasicRespMsg getRespByLinkReqMsg(WeiXinLinkReqMsg reqMsg);

    /***
     * ȡ˵ϢظϢ
     * 
     * @param reqMsg
     * @return
     */
    public WeiXinBasicRespMsg getRespByMenuEventReqMsg(WeiXinMenuEventReqMsg reqMsg);

    /***
     * ȡû˶ϢظϢ
     * 
     * @param reqMsg
     * @return
     */
    public WeiXinBasicRespMsg getRespBySubscribeEventReqMsg(WeiXinSubscribeEventReqMsg reqMsg);

}

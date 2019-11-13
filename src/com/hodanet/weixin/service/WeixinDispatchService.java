package com.hodanet.weixin.service;

import com.hodanet.weixin.entity.weixin.req.WeiXinBasicReqMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinBasicRespMsg;

/**
 * ΢ַ
 * 
 * @anthor lyw
 * @version 2014-4-14 3:29:00
 */
public interface WeixinDispatchService {

    public WeiXinBasicRespMsg doReqMsgDispatch(WeixinRespService weixinService, WeiXinBasicReqMsg reqMsg);
}

package com.hodanet.weixin.service.impl;

import com.hodanet.weixin.entity.weixin.req.WeiXinImageReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinLinkReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinLocationReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinMenuEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinSubscribeEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinTextReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinVideoReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinVoiceReqMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinBasicRespMsg;
import com.hodanet.weixin.service.WeixinRespService;

/**
 * @anthor lyw
 * @version 2014-4-14 4:19:23
 */
public abstract class WeixinAbstractServiceImpl implements WeixinRespService {

    @Override
    public WeiXinBasicRespMsg getRespByTextReqMsg(WeiXinTextReqMsg reqMsg) {
        return null;
    }

    @Override
    public WeiXinBasicRespMsg getRespByImageReqMsg(WeiXinImageReqMsg reqMsg) {
        return null;
    }

    @Override
    public WeiXinBasicRespMsg getRespByVoiceReqMsg(WeiXinVoiceReqMsg reqMsg) {
        return null;
    }

    @Override
    public WeiXinBasicRespMsg getRespByVideoReqMsg(WeiXinVideoReqMsg reqMsg) {
        return null;
    }

    @Override
    public WeiXinBasicRespMsg getRespByLocationReqMsg(WeiXinLocationReqMsg reqMsg) {
        return null;
    }

    @Override
    public WeiXinBasicRespMsg getRespByLinkReqMsg(WeiXinLinkReqMsg reqMsg) {
        return null;
    }

    @Override
    public WeiXinBasicRespMsg getRespByMenuEventReqMsg(WeiXinMenuEventReqMsg reqMsg) {
        return null;
    }

    @Override
    public WeiXinBasicRespMsg getRespBySubscribeEventReqMsg(WeiXinSubscribeEventReqMsg reqMsg) {
        return null;
    }

}

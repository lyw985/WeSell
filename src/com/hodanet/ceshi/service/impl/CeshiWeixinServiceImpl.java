package com.hodanet.ceshi.service.impl;

import org.springframework.stereotype.Service;

import com.hodanet.weixin.entity.weixin.req.WeiXinMenuEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinSubscribeEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinTextReqMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinBasicRespMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinTextRespMsg;
import com.hodanet.weixin.service.impl.WeixinAbstractServiceImpl;

/**
 * @anthor lyw
 * @version 2014-4-10 3:46:56
 */
@Service
public class CeshiWeixinServiceImpl extends WeixinAbstractServiceImpl {

    @Override
    public WeiXinBasicRespMsg getRespBySubscribeEventReqMsg(WeiXinSubscribeEventReqMsg reqMsg) {
        if (("subscribe").equals(reqMsg.getEvent())) {
            return new WeiXinTextRespMsg(reqMsg, "Զɹ");
        } else {
            return new WeiXinTextRespMsg(reqMsg, "˶ɹ");
        }
    }

    @Override
    public WeiXinBasicRespMsg getRespByTextReqMsg(WeiXinTextReqMsg reqMsg) {
        WeiXinBasicRespMsg respMsg = new WeiXinTextRespMsg(reqMsg, "Ϊ:" + reqMsg.getContent());
        return respMsg;
    }

    @Override
    public WeiXinBasicRespMsg getRespByMenuEventReqMsg(WeiXinMenuEventReqMsg reqMsg) {
        WeiXinBasicRespMsg respMsg = new WeiXinTextRespMsg(reqMsg, "eventKeyΪ:" + reqMsg.getEventKey());
        return respMsg;
    }
}

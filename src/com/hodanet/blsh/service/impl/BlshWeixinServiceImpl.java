package com.hodanet.blsh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hodanet.blsh.constant.BlshConstant;
import com.hodanet.blsh.constant.BlshContentType;
import com.hodanet.blsh.entity.po.BlshContent;
import com.hodanet.blsh.service.BlshContentService;
import com.hodanet.weixin.entity.weixin.req.WeiXinMenuEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinSubscribeEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinTextReqMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinBasicRespMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinTextRespMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinTuwenRespMsg;
import com.hodanet.weixin.service.impl.WeixinAbstractServiceImpl;

/**
 * @anthor lyw
 * @version 2014-4-10 3:46:56
 */
@Service
public class BlshWeixinServiceImpl extends WeixinAbstractServiceImpl {

    @Autowired
    private BlshContentService blshContentService;

    @Override
    public WeiXinBasicRespMsg getRespBySubscribeEventReqMsg(WeiXinSubscribeEventReqMsg reqMsg) {
        if (("subscribe").equals(reqMsg.getEvent())) {
            return new WeiXinTextRespMsg(reqMsg, BlshConstant.SUBSCRIBE_CONTENT);
        } else {
            return new WeiXinTextRespMsg(reqMsg, BlshConstant.UNSUBSCRIBE_CONTENT);
        }
    }

    @Override
    public WeiXinBasicRespMsg getRespByTextReqMsg(WeiXinTextReqMsg reqMsg) {
        String content = reqMsg.getContent();
        // if (BlshConstant.blshDispatchMap.containsKey(content)) {
        // BlshContentType blshContentType = BlshConstant.blshDispatchMap.get(content);
        // int type = blshContentType.getType();
        // BlshContent blshContent = blshContentService.sendBlshContent(reqMsg.getFromUserName(), type);
        // if (blshContent != null) {
        // if (blshContent.getMsgType() == 1) {
        // WeiXinTextRespMsg respMsg = new WeiXinTextRespMsg(reqMsg, blshContent.getContent());
        // return respMsg;
        // } else if (blshContent.getMsgType() == 2) {
        // WeiXinTuwenRespMsg respMsg = new WeiXinTuwenRespMsg(reqMsg);
        // respMsg.addTuwen(blshContent.getTitle(), blshContent.getDescription(), blshContent.getPicUrl(),
        // blshContent.getUrl());
        // return respMsg;
        // }
        // }
        // }
        // TODO ΪԤ
        return null;
    }

    @Override
    public WeiXinBasicRespMsg getRespByMenuEventReqMsg(WeiXinMenuEventReqMsg reqMsg) {
        String keyEvent = reqMsg.getEventKey();
        if (BlshConstant.blshDispatchMap.containsKey(keyEvent)) {
            BlshContentType blshContentType = BlshConstant.blshDispatchMap.get(keyEvent);
            int type = blshContentType.getType();
            BlshContent blshContent = blshContentService.sendBlshContent(reqMsg.getFromUserName(), type);
            if (blshContent != null) {
                if (blshContent.getMsgType() == 1) {
                    WeiXinTextRespMsg respMsg = new WeiXinTextRespMsg(reqMsg, blshContent.getContent());
                    return respMsg;
                } else if (blshContent.getMsgType() == 2) {
                    WeiXinTuwenRespMsg respMsg = new WeiXinTuwenRespMsg(reqMsg);
                    respMsg.addTuwen(blshContent.getTitle(), blshContent.getDescription(), blshContent.getPicUrl(),
                                     blshContent.getUrl());
                    return respMsg;
                }
            }
        }
        // TODO ΪԤ
        return null;
    }
}

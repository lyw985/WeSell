package com.hodanet.weixin.service.impl;

import org.springframework.stereotype.Service;

import com.hodanet.weixin.constant.WeixinMessageType;
import com.hodanet.weixin.entity.weixin.req.WeiXinBasicReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinImageReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinLinkReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinLocationReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinMenuEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinSubscribeEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinTextReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinVideoReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinVoiceReqMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinBasicRespMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinTextRespMsg;
import com.hodanet.weixin.service.WeixinDispatchService;
import com.hodanet.weixin.service.WeixinRespService;

/**
 * @anthor lyw
 * @version 2014-4-14 3:30:54
 */
@Service
public class WeixinDispatchServiceImpl implements WeixinDispatchService {

    @Override
    public WeiXinBasicRespMsg doReqMsgDispatch(WeixinRespService weixinService, WeiXinBasicReqMsg reqMsg) {
        WeiXinBasicRespMsg weiXinBasicRespMsg = null;
        switch (WeixinMessageType.getWeixinMessageType(reqMsg.getMsgType())) {
            case TEXT:
                weiXinBasicRespMsg = weixinService.getRespByTextReqMsg((WeiXinTextReqMsg) reqMsg);
                break;
            case IMAGE:
                weiXinBasicRespMsg = weixinService.getRespByImageReqMsg((WeiXinImageReqMsg) reqMsg);
                break;
            case VOICE:
                weiXinBasicRespMsg = weixinService.getRespByVoiceReqMsg((WeiXinVoiceReqMsg) reqMsg);
                break;
            case VIDEO:
                weiXinBasicRespMsg = weixinService.getRespByVideoReqMsg((WeiXinVideoReqMsg) reqMsg);
                break;
            case LOCATION:
                weiXinBasicRespMsg = weixinService.getRespByLocationReqMsg((WeiXinLocationReqMsg) reqMsg);
                break;
            case LINK:
                weiXinBasicRespMsg = weixinService.getRespByLinkReqMsg((WeiXinLinkReqMsg) reqMsg);
                break;
            case EVENT:
                if (reqMsg instanceof WeiXinMenuEventReqMsg) {
                    WeiXinMenuEventReqMsg weiXinMenuEventReqMsg = (WeiXinMenuEventReqMsg) reqMsg;
                    if ("CLICK".equals(weiXinMenuEventReqMsg.getEvent())) {
                        // Ҫĵ¼
                        weiXinBasicRespMsg = weixinService.getRespByMenuEventReqMsg(weiXinMenuEventReqMsg);
                    } else if ("VIEW".equals(weiXinMenuEventReqMsg.getEvent())) {
                        // Ҫתĵ¼
                        return null;
                    }
                    break;
                } else if (reqMsg instanceof WeiXinSubscribeEventReqMsg) {
                    weiXinBasicRespMsg = weixinService.getRespBySubscribeEventReqMsg((WeiXinSubscribeEventReqMsg) reqMsg);
                    break;
                } else {
                    // TODO ¼
                    // ɨά¼
                    // ϱλ¼
                    // Զ˵¼
                    // ˵תʱ¼
                }
        }
        if (weiXinBasicRespMsg == null) {
            weiXinBasicRespMsg = new WeiXinTextRespMsg(reqMsg, "δҵָӦĴƣȷϺ");
        }
        return weiXinBasicRespMsg;
    }

}

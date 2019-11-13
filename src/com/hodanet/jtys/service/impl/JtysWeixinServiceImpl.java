package com.hodanet.jtys.service.impl;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hodanet.common.util.DateConverterUtil;
import com.hodanet.common.util.FileUtil;
import com.hodanet.jtys.constant.JtysConstant;
import com.hodanet.jtys.constant.JtysUserStatus;
import com.hodanet.jtys.entity.po.JtysUser;
import com.hodanet.jtys.service.JtysCommunicateService;
import com.hodanet.jtys.service.JtysUserService;
import com.hodanet.system.util.CommonPropUtil;
import com.hodanet.weixin.entity.weixin.req.WeiXinImageReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinMenuEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinSubscribeEventReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinTextReqMsg;
import com.hodanet.weixin.entity.weixin.req.WeiXinVoiceReqMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinBasicRespMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinTextRespMsg;
import com.hodanet.weixin.service.WeixinReqService;
import com.hodanet.weixin.service.impl.WeixinAbstractServiceImpl;

/**
 * @anthor lyw
 * @version 2014-4-10 3:46:56
 */
@Service
public class JtysWeixinServiceImpl extends WeixinAbstractServiceImpl {

    @Autowired
    private JtysUserService        jtysUserService;

    @Autowired
    private JtysCommunicateService jtysCommunicateService;

    @Autowired
    private WeixinReqService       weixinReqService;

    @Override
    public WeiXinBasicRespMsg getRespBySubscribeEventReqMsg(WeiXinSubscribeEventReqMsg reqMsg) {
        if (("subscribe").equals(reqMsg.getEvent())) {
            return new WeiXinTextRespMsg(reqMsg, JtysConstant.SUBSCRIBE_CONTENT);
        } else {
            return new WeiXinTextRespMsg(reqMsg, JtysConstant.UNSUBSCRIBE_CONTENT);
        }
    }

    @Override
    public WeiXinBasicRespMsg getRespByTextReqMsg(WeiXinTextReqMsg reqMsg) {
        WeiXinBasicRespMsg respMsg = new WeiXinTextRespMsg(reqMsg, "Ϊ:" + reqMsg.getContent());
        JtysUser jtysUser = jtysUserService.getJtysUserByOpenId(reqMsg.getFromUserName());
        // жûʸ
        if (jtysUser.getStatus() != JtysUserStatus.GUEST.getValue() || jtysUser.getCanFree() == 1) {
            jtysCommunicateService.createJtysCommunicate(jtysUser, reqMsg.getContent(), null, null);
        } else {
            // ظͨ
        }
        return respMsg;
    }

    @Override
    public WeiXinBasicRespMsg getRespByMenuEventReqMsg(WeiXinMenuEventReqMsg reqMsg) {
        WeiXinBasicRespMsg respMsg = new WeiXinTextRespMsg(reqMsg, "eventKeyΪ:" + reqMsg.getEventKey());
        return respMsg;
    }

    @Override
    public WeiXinBasicRespMsg getRespByImageReqMsg(WeiXinImageReqMsg reqMsg) {
        JtysUser jtysUser = jtysUserService.getJtysUserByOpenId(reqMsg.getFromUserName());
        // жûʸ
        if (jtysUser.getStatus() != JtysUserStatus.GUEST.getValue() || jtysUser.getCanFree() == 1) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String dirRootPath = request.getRealPath(".");
            String fileDirPath = CommonPropUtil.CONSTANT_IMAGE_PATH + File.separator
                                 + DateConverterUtil.format(DateConverterUtil.yyyyMMdd);
            File dir = new File(dirRootPath + File.separator + fileDirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try {
                String fileName = FileUtil.getFileName(dir.getAbsolutePath(), "jpg");
                String fileUrl = File.separator + fileDirPath + File.separator + fileName;
                weixinReqService.getMedia(CommonPropUtil.getAccessTokenByServerCode(JtysConstant.SERVER_CODE),
                                          reqMsg.getMediaId(), dirRootPath + File.separator + fileDirPath
                                                               + File.separator + fileName);
                fileUrl = fileUrl.replaceAll("\\\\", "/");
                jtysCommunicateService.createJtysCommunicate(jtysUser, null, fileUrl, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // ظͨ
        }
        return super.getRespByImageReqMsg(reqMsg);
    }

    @Override
    public WeiXinBasicRespMsg getRespByVoiceReqMsg(WeiXinVoiceReqMsg reqMsg) {
        JtysUser jtysUser = jtysUserService.getJtysUserByOpenId(reqMsg.getFromUserName());
        // жûʸ
        if (jtysUser.getStatus() != JtysUserStatus.GUEST.getValue() || jtysUser.getCanFree() == 1) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String dirRootPath = request.getRealPath(".");
            String fileDirPath = CommonPropUtil.CONSTANT_IMAGE_PATH + File.separator
                                 + DateConverterUtil.format(DateConverterUtil.yyyyMMdd);
            File dir = new File(dirRootPath + File.separator + fileDirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try {
                String fileName = FileUtil.getFileName(dir.getAbsolutePath(), "mp3");
                String fileUrl = File.separator + fileDirPath + File.separator + fileName;
                weixinReqService.getMedia(CommonPropUtil.getAccessTokenByServerCode(JtysConstant.SERVER_CODE),
                                          reqMsg.getMediaId(), dirRootPath + File.separator + fileDirPath
                                                               + File.separator + fileName);
                fileUrl = fileUrl.replaceAll("\\\\", "/");
                jtysCommunicateService.createJtysCommunicate(jtysUser, null, null, fileUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // ظͨ
        }
        return super.getRespByVoiceReqMsg(reqMsg);
    }

}

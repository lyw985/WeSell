package com.hodanet.weixin.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hodanet.common.spring.SpringContextUtil;
import com.hodanet.system.util.CommonPropUtil;
import com.hodanet.weixin.entity.weixin.WeixinServerProp;
import com.hodanet.weixin.entity.weixin.req.WeiXinBasicReqMsg;
import com.hodanet.weixin.entity.weixin.resp.WeiXinBasicRespMsg;
import com.hodanet.weixin.service.WeixinDispatchService;
import com.hodanet.weixin.service.WeixinRespService;
import com.hodanet.weixin.service.impl.WeixinDispatchServiceImpl;
import com.hodanet.weixin.util.EncryptUtil;

public class WeChatServlet extends HttpServlet {

    private static final long            serialVersionUID = 1L;
    private static final Logger          log              = Logger.getLogger(WeChatServlet.class);

    private static WeixinDispatchService weixinDispatchService;

    static {
        weixinDispatchService = SpringContextUtil.getBean(WeixinDispatchServiceImpl.class);
        log.info("weixinDispatchService:" + weixinDispatchService);
    }

    /**
     * ΢Źƽ̨֤÷
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serverCode = request.getParameter("serverCode");

        if (CommonPropUtil.weixinServerPropMap.containsKey(serverCode)) {
            WeixinServerProp weixinServerProp = CommonPropUtil.weixinServerPropMap.get(serverCode);
            String token = weixinServerProp.getToken();

            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");

            log.info("signature:" + signature);
            log.info("timestamp:" + timestamp);
            log.info("nonce:" + nonce);

            String[] ArrTmp = { token, timestamp, nonce };
            Arrays.sort(ArrTmp);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ArrTmp.length; i++) {
                sb.append(ArrTmp[i]);
            }
            String pwd = EncryptUtil.encrypt(sb.toString());
            String echostr = request.getParameter("echostr");
            log.info("echostr:" + echostr);

            if (pwd.equals(signature)) {
                if (!"".equals(echostr) && echostr != null) {
                    log.info("echostr-result: success");
                    response.getWriter().print(echostr);
                }
            }
        }
    }

    /**
     * ûƽ̨ϢԶϢ
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                   IOException {
        log.info("wx post xml comming...");
        String serverCode = request.getParameter("serverCode");
        log.info("serverCode:" + serverCode);
        if (CommonPropUtil.weixinServerPropMap.containsKey(serverCode)) {
            WeixinServerProp weixinServerProp = CommonPropUtil.weixinServerPropMap.get(serverCode);
            WeixinRespService weixinService = (WeixinRespService) SpringContextUtil.getBean(weixinServerProp.getName()
                                                                                    + "WeixinServiceImpl");

            StringBuffer sb = new StringBuffer();
            String line;
            try {
                request.setCharacterEncoding("UTF-8");
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                log.info("xml-request:" + sb.toString());

                WeiXinBasicReqMsg reqMsg = WeiXinBasicReqMsg.parseXmlRequest(sb.toString());
                WeiXinBasicRespMsg respMsg = weixinDispatchService.doReqMsgDispatch(weixinService, reqMsg);

                if (respMsg == null) {
                    return;
                }

                String respXmlStr = respMsg.toRespXmlStr();
                log.info("xml-response:" + respXmlStr);
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(respXmlStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}

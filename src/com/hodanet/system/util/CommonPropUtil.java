package com.hodanet.system.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hodanet.common.entity.vo.CommonMapper;
import com.hodanet.common.util.StringUtil;
import com.hodanet.weixin.entity.weixin.WeixinServerProp;
import com.hodanet.weixin.task.AutoGetAccessTokenTask;

/**
 * @author lance.lengcs
 * @version 2012-7-27 3:59:59
 * 
 * <pre>
 * 	ȫֳ壬ϵͳصModel
 * </pre>
 */
@Component
public class CommonPropUtil {

    private static final Logger                 logger                      = Logger.getLogger(CommonPropUtil.class);

    // TODO ע⣬ֱͨӶȡļʽ
    // @Value("#{commonProperties['rootPath']}")
    private static final String                 CONSTANT_FILE_PATH          = "/common.properties";
    private static final String                 CONSTANT_TITLE              = "title";

    private static final String                 CONSTANT_SERVERNAMES        = "serverNames";
    private static final String                 CONSTANT_TOKEN              = "token";
    private static final String                 CONSTANT_APPID              = "appid";
    private static final String                 CONSTANT_APPSECRET          = "appsecret";

    public static String                        CONSTANT_IMAGE_PATH         = "attached" + File.separator + "image";

    public static String                        CONSTANT_AUDIO_PATH         = "attached" + File.separator + "audio";

    public static String                        CONSTANT_APK_PATH           = "attached" + File.separator + "apk";

    public static Map<String, WeixinServerProp> weixinServerPropMap         = new HashMap<String, WeixinServerProp>();

    private static CommonMapper                 commonMapper                = new CommonMapper();

    static {
        Properties prop = new Properties();
        InputStream in = CommonPropUtil.class.getClassLoader().getResourceAsStream(CONSTANT_FILE_PATH);
        try {
            prop.load(in);
            commonMapper.setTitle(StringUtil.trim(prop.getProperty(CONSTANT_TITLE)));

            String serverNames = StringUtil.trim(prop.getProperty(CONSTANT_SERVERNAMES));

            if (StringUtil.isNotBlank(serverNames)) {
                for (String name : serverNames.split(",")) {
                    String token = StringUtil.trim(prop.getProperty(name + "." + CONSTANT_TOKEN));
                    String appId = StringUtil.trim(prop.getProperty(name + "." + CONSTANT_APPID));
                    String appSecret = StringUtil.trim(prop.getProperty(name + "." + CONSTANT_APPSECRET));
                    WeixinServerProp serverProp = new WeixinServerProp(name, token, appId, appSecret);
                    weixinServerPropMap.put(name, serverProp);
                }
            }
            AutoGetAccessTokenTask.getAccessToken();
            System.out.println("1-static");

        } catch (IOException e) {
            logger.error("CommonProperties load error!", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("CommonProperties close error!", e);
            }
        }
    }

    public static String getAccessTokenByServerCode(String serverCode) {
        if (weixinServerPropMap.containsKey(serverCode)) {
            return weixinServerPropMap.get(serverCode).getAccessToken();
        }
        return null;
    }

    public static CommonMapper getCommonMapper() {
        return commonMapper;
    }
}

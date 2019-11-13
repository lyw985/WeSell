package com.hodanet.jtys.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.NameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hodanet.jtys.constant.ChunyuConstant;
import com.hodanet.jtys.entity.po.JtysCommunicate;
import com.hodanet.jtys.entity.po.JtysUser;
import com.hodanet.jtys.service.ChunyuService;
import com.hodanet.system.util.HttpURLConnectionUtil;
import com.hodanet.system.util.MD5Util;

/**
 * @anthor lyw
 * @version 2014-9-29 2:01:09
 */
@Service
public class ChunyuServiceImpl implements ChunyuService {

    @Override
    public String uploadFile(String type, File file) {
        Map<String, File> map = new HashMap<String, File>();
        map.put("file", file);
        String resultJson = HttpURLConnectionUtil.multipartPostMethod(ChunyuConstant.ROOT_PATH
                                                                              + ChunyuConstant.UPLOAD_FILE,
                                                                      new NameValuePair[] { new NameValuePair("type",
                                                                                                              type) },
                                                                      map);
        JSONObject json = JSONObject.parseObject(Unicode2GBK(resultJson));
        String fileStr = json.getString("file");
        if (fileStr.startsWith("images") || fileStr.startsWith("audios")) {
            // {"file": "images/2014/09/29/38c4a4e5a7ab_w204_h204_.jpg"}
            // {"file": "audios/2014/09/29/5fca96c62414.mp3.mp3"}
        } else {
            // {"file": ["not an audio file."]}
            // {"file": ["ֶǱ"]}
            // TODO ô
        }
        return fileStr;
    }

    @Override
    public void createVip(JtysUser jtysUser) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String atime = System.currentTimeMillis() / 1000 + "";
        String sign = MD5Util.encrypt(atime + "_" + jtysUser.getId() + "_testchunyu");
        params.add(new NameValuePair("user_id", jtysUser.getId() + ""));
        params.add(new NameValuePair("atime", atime));
        params.add(new NameValuePair("sign", sign));
        params.add(new NameValuePair("days", "90"));
        String resultJson = HttpURLConnectionUtil.postMethod(ChunyuConstant.ROOT_PATH + ChunyuConstant.CREATE_VIP,
                                                             params.toArray(new NameValuePair[params.size()]));
        JSONObject json = JSONObject.parseObject(Unicode2GBK(resultJson));
        int error = json.getInteger("error");
        if (error == 0) {
            // TODO ô
        } else {
            // TODO ô
        }
    }

    @Override
    public void createProblem(JtysCommunicate jtysCommunicate) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String atime = System.currentTimeMillis() / 1000 + "";
        String sign = MD5Util.encrypt(atime + "_" + jtysCommunicate.getUser().getId() + "_testchunyu");
        params.add(new NameValuePair("user_id", jtysCommunicate.getUser().getId() + ""));
        params.add(new NameValuePair("atime", atime));
        params.add(new NameValuePair("sign", sign));
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        if (jtysCommunicate.getText() != null) {
            object.put("type", "text");
            object.put("text", jtysCommunicate.getText());
            array.add(object);
        }
        if (jtysCommunicate.getImageUrl() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String dirRootPath = request.getRealPath(".");
            String chunyuUrl = uploadFile("image", new File(dirRootPath + jtysCommunicate.getImageUrl()));
            object.put("type", "image");
            object.put("file", chunyuUrl);
            array.add(object);
        }
        
        if (jtysCommunicate.getAudioUrl() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String dirRootPath = request.getRealPath(".");
            String chunyuUrl = uploadFile("audio", new File(dirRootPath + jtysCommunicate.getAudioUrl()));
            object.put("type", "audio");
            object.put("file", chunyuUrl);
            array.add(object);
        }
        params.add(new NameValuePair("content", array.toJSONString()));
        String resultJson = HttpURLConnectionUtil.postMethod(ChunyuConstant.ROOT_PATH + ChunyuConstant.CREATE_PROBLEM,
                                                             params.toArray(new NameValuePair[params.size()]));
        JSONObject json = JSONObject.parseObject(Unicode2GBK(resultJson));
        int error = json.getInteger("error");
        if (error == 0) {
            // TODO ô
        } else {
            // TODO ô
        }
    }

    public static String Unicode2GBK(String dataStr) {
        int index = 0;
        StringBuffer buffer = new StringBuffer();
        int li_len = dataStr.length();
        while (index < li_len) {
            if (index >= li_len - 1 || !"\\u".equals(dataStr.substring(index, index + 2))) {
                buffer.append(dataStr.charAt(index));
                index++;
                continue;
            }
            String charStr = "";
            charStr = dataStr.substring(index + 2, index + 6);
            char letter = (char) Integer.parseInt(charStr, 16);
            buffer.append(letter);
            index += 6;
        }
        return buffer.toString();
    }

}

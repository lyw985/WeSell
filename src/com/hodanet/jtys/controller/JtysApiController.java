package com.hodanet.jtys.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hodanet.common.util.DateConverterUtil;
import com.hodanet.common.util.FileUtil;
import com.hodanet.common.util.WebUtil;
import com.hodanet.jtys.constant.ChunyuConstant;
import com.hodanet.jtys.constant.JtysCommunicateStatus;
import com.hodanet.jtys.constant.JtysCommunicateType;
import com.hodanet.jtys.entity.po.JtysCommunicate;
import com.hodanet.jtys.entity.po.JtysDoctor;
import com.hodanet.jtys.service.JtysCommunicateService;
import com.hodanet.jtys.service.JtysDoctorService;
import com.hodanet.system.util.CommonPropUtil;
import com.hodanet.system.util.MD5Util;

@Controller
@RequestMapping(value = "/jtys/api")
public class JtysApiController {

    @Autowired
    private JtysDoctorService      jtysDoctorService;

    @Autowired
    private JtysCommunicateService jtysCommunicateService;

    /***
     * doctorʾشϢ { id: ҽid name: ҽ, title: ҽְ, imageҽͷ, clinicҽڿ, hospitalҽҽԺ }
     * problem_idʾID33324580 atimeǩʱǰʱ䡿:137322417 signǩԿ: 47d85cf7740d796cc1561f1eb1c1355a
     * contentҽشݣı [ {"type":"text", "text":"ҵĲ"}, ## ı {"type":"audio", "file":"audio_url"} ##  ]
     * Կɷʽatime_problem_id_testchunyu eg: md5('137322417_33324580_testchunyu')=> 47d85cf7740d796cc1561f1eb1c1355a
     * 
     * @param model
     * @param response
     * @param doctorJson
     * @param problem_id
     * @param atime
     * @param sign
     * @param contentJson
     */
    @RequestMapping(value = "/replyProblem")
    public void replyProblem(Model model, HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("doctor")
                             String doctorJson, @RequestParam("problem_id")
                             String problemId, @RequestParam("atime")
                             String atime, @RequestParam("sign")
                             String sign, @RequestParam("content")
                             String contentJson) {
        JSONObject jsonObject = new JSONObject();
        try {
            String selfSign = MD5Util.encrypt(atime + "_" + problemId + "_testchunyu");
            if (!selfSign.equals(sign)) {
                jsonObject.put("error", 1);
                jsonObject.put("error_msg", "֤ʧ");
            }
            JtysDoctor jtysDoctor = JtysDoctor.parseFromJson(doctorJson);
            jtysDoctorService.saveJtysDoctor(jtysDoctor);

            JSONArray jsonArray = JSONArray.parseArray(contentJson);
            for (int i = 0; i < jsonArray.size(); i++) {
                JtysCommunicate jtysCommunicate = new JtysCommunicate();
                jtysCommunicate.setType(JtysCommunicateType.ANSWER.getValue());
                jtysCommunicate.setStatus(JtysCommunicateStatus.INIT.getValue());
                jtysCommunicate.setDoctor(jtysDoctor);
                jtysCommunicate.setProblemId(problemId);
                jtysCommunicate.setIsFirst(0);
                JSONObject object = jsonArray.getJSONObject(i);
                String type = object.getString("type");
                if ("text".equals(type)) {
                    String text = object.getString("text");
                    jtysCommunicate.setText(text);
                }
                if ("image".equals(type)) {
                    String chunyuFileUrl = object.getString("file");
                    String dirRootPath = request.getRealPath(".");
                    String fileDirPath = CommonPropUtil.CONSTANT_IMAGE_PATH + File.separator
                                         + DateConverterUtil.format(DateConverterUtil.yyyyMMdd);
                    File dir = new File(dirRootPath + File.separator + fileDirPath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    String fileName = FileUtil.getFileName(dir.getAbsolutePath(), "jpg");
                    String fileUrl = File.separator + fileDirPath + File.separator + fileName;

                    URL url = new URL(ChunyuConstant.GET_FILE_ROOT_PATH + chunyuFileUrl);
                    URLConnection con = url.openConnection();
                    FileUtil.copy(con.getInputStream(), new FileOutputStream(dirRootPath + File.separator + fileDirPath
                                                                             + File.separator + fileName));
                    fileUrl = fileUrl.replaceAll("\\\\", "/");
                    jtysCommunicate.setImageUrl(fileUrl);
                }
                if ("audio".equals(type)) {
                    String chunyuFileUrl = object.getString("file");
                    String dirRootPath = request.getRealPath(".");
                    String fileDirPath = CommonPropUtil.CONSTANT_AUDIO_PATH + File.separator
                                         + DateConverterUtil.format(DateConverterUtil.yyyyMMdd);
                    File dir = new File(dirRootPath + File.separator + fileDirPath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    String fileName = FileUtil.getFileName(dir.getAbsolutePath(), "jpg");
                    String fileUrl = File.separator + fileDirPath + File.separator + fileName;

                    URL url = new URL(ChunyuConstant.GET_FILE_ROOT_PATH + chunyuFileUrl);
                    URLConnection con = url.openConnection();
                    FileUtil.copy(con.getInputStream(), new FileOutputStream(dirRootPath + File.separator + fileDirPath
                                                                             + File.separator + fileName));
                    fileUrl = fileUrl.replaceAll("\\\\", "/");
                    jtysCommunicate.setAudioUrl(fileUrl);
                }
                jtysCommunicateService.saveJtysCommunicate(jtysCommunicate);
            }

            jsonObject.put("error", 0);
        } catch (Exception e) {
            jsonObject.put("error", 1);
            jsonObject.put("error_msg", "ϵͳԺ");
        }
        WebUtil.responseText(response, jsonObject.toJSONString());
    }

}

package com.hodanet.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hodanet.system.util.MD5Util;

/**
 * @anthor lyw
 * @version 2014-8-25 14:07:39
 */
public class HttpTest {

    /***
     * /partner/hd/create_vip
     */
    public static void createVip(String urlMain) {
        String url = urlMain + "/partner/hd/create_vip";
        PostMethod postMethod = null;
        HttpClient client = null;
        int status;
        try {
            postMethod = new PostMethod(url) {

                public String getRequestCharSet() {
                    return "UTF-8";
                }
            };
            client = new HttpClient();
            client.setTimeout(100000);
            String user_id = "123456";
            String days = "90";
            String atime = System.currentTimeMillis() / 1000 + "";
            String sign = getSign(user_id, atime);
            postMethod.addParameter("user_id", user_id);
            postMethod.addParameter("days", days);
            postMethod.addParameter("atime", atime);
            postMethod.addParameter("sign", sign);
            status = client.executeMethod(postMethod);
            if (status != HttpStatus.SC_OK) {
                // TODO error option
            }
            InputStreamReader isr = new InputStreamReader(postMethod.getResponseBodyAsStream());
            BufferedReader br = new BufferedReader(isr);
            String str = null;
            while ((str = br.readLine()) != null) {
                System.out.println(Unicode2GBK(str));
            }
            postMethod.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSign(String user_id, String atime) {
        return MD5Util.encrypt(atime + "_" + user_id + "_testchunyu");
    }

    /***
     * 上传：/files/upload/ 返回：{"file": "images/2014/09/29/38c4a4e5a7ab_w204_h204_.jpg"}
     * 下载：/media/images/2014/09/29/38c4a4e5a7ab_w204_h204_.jpg
     */
    public static void upload(String urlMain) {
        String url = urlMain + "/files/upload/";
        MultipartPostMethod postMethod = null;
        HttpClient client = null;
        int status;
        try {
            postMethod = new MultipartPostMethod(url) {

                public String getRequestCharSet() {
                    return "UTF-8";
                }
            };
            client = new HttpClient();
            client.setTimeout(100000);
            postMethod.addParameter("type", "audio");
            // postMethod.addParameter("file", new File("D:/test20140926.jpg"));
            postMethod.addParameter("file", new File("E:/test.mp3"));

            status = client.executeMethod(postMethod);
            if (status != HttpStatus.SC_OK) {
                // TODO error option
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
            String str = null;
            while ((str = br.readLine()) != null) {
                System.out.println(Unicode2GBK(str));
            }
            postMethod.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * /partner/hd/problem/create {"problem_id": 12662074, "error": 0}
     */
    public static void createProblem(String urlMain) {
        String url = urlMain + "/partner/hd/problem/create";
        PostMethod postMethod = null;
        HttpClient client = null;
        int status;
        try {
            postMethod = new PostMethod(url) {

                public String getRequestCharSet() {
                    return "UTF-8";
                }
            };
            client = new HttpClient();
            client.setTimeout(100000);
            String user_id = "123456";
            String atime = System.currentTimeMillis() / 1000 + "";
            String sign = getSign(user_id, atime);
            postMethod.addParameter("user_id", user_id);
            postMethod.addParameter("atime", atime);
            postMethod.addParameter("sign", sign);
            JSONArray array = new JSONArray();
            JSONObject object = new JSONObject();
            object.put("type", "text");
            object.put("text", "测试文本");
            array.add(object);
            postMethod.addParameter("content", array.toJSONString());
            status = client.executeMethod(postMethod);
            if (status != HttpStatus.SC_OK) {
                // TODO error option
            }
            InputStreamReader isr = new InputStreamReader(postMethod.getResponseBodyAsStream());
            BufferedReader br = new BufferedReader(isr);
            String str = null;
            while ((str = br.readLine()) != null) {
                System.out.println(Unicode2GBK(str));
            }
            postMethod.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * /partner/hd/problem_content/create {"problem_id": 12662074, "error": 0}
     */
    public static void addProblem(String urlMain) {
        String url = urlMain + "/partner/hd/problem_content/create";
        PostMethod postMethod = null;
        HttpClient client = null;
        int status;
        try {
            postMethod = new PostMethod(url) {

                public String getRequestCharSet() {
                    return "UTF-8";
                }
            };
            client = new HttpClient();
            client.setTimeout(100000);
            String user_id = "123456";
            String atime = System.currentTimeMillis() / 1000 + "";
            String sign = getSign("12662074", atime);
            postMethod.addParameter("problem_id", "12662074");
            postMethod.addParameter("user_id", user_id);
            postMethod.addParameter("atime", atime);
            postMethod.addParameter("sign", sign);
            JSONArray array = new JSONArray();
            JSONObject object = new JSONObject();
            object.put("type", "text");
            object.put("text", "测试追问文本");
            array.add(object);
            postMethod.addParameter("content", array.toJSONString());
            status = client.executeMethod(postMethod);
            if (status != HttpStatus.SC_OK) {
                // TODO error option
            }
            InputStreamReader isr = new InputStreamReader(postMethod.getResponseBodyAsStream());
            BufferedReader br = new BufferedReader(isr);
            String str = null;
            while ((str = br.readLine()) != null) {
                System.out.println(Unicode2GBK(str));
            }
            postMethod.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * http://localhost/jtys/api/replyProblem.do
     */
    public static void replyProblem() {
        String url = "http://localhost:8080/jtys/api/replyProblem.do";
        url = "http://cnet.hodanet.com/jtys/api/replyProblem.do";
        PostMethod postMethod = null;
        HttpClient client = null;
        int status;
        try {
            postMethod = new PostMethod(url) {

                public String getRequestCharSet() {
                    return "UTF-8";
                }
            };
            client = new HttpClient();
            client.setTimeout(100000);
            String user_id = "123456";
            String atime = System.currentTimeMillis() / 1000 + "";
            String sign = getSign("12662074", atime);
            postMethod.addParameter("problem_id", "12662074");
            postMethod.addParameter("atime", atime);
            postMethod.addParameter("sign", sign);
            postMethod.addParameter("content", "回复语");
            JSONObject object = new JSONObject();
            object.put("id", "123");
            object.put("name", "名称");
            object.put("title", "职称");
            object.put("image", "头像");
            object.put("clinic", "科室");
            object.put("hospital", "医院");
            postMethod.addParameter("doctor", object.toJSONString());
            status = client.executeMethod(postMethod);
            if (status != HttpStatus.SC_OK) {
                // TODO error option
            }
            InputStreamReader isr = new InputStreamReader(postMethod.getResponseBodyAsStream());
            BufferedReader br = new BufferedReader(isr);
            String str = null;
            while ((str = br.readLine()) != null) {
                System.out.println(Unicode2GBK(str));
            }
            postMethod.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void main(String[] args) throws UnsupportedEncodingException {
        String urlTest = "http://hd.summer2.chunyu.me";
        String urlReal = "http://hd.chunyuyisheng.com";
        // createVip(urlTest);
        // upload(urlTest);
        // createProblem(urlTest);
        // addProblem(urlTest);
        replyProblem();

    }
}

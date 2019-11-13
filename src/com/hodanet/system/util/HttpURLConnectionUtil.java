package com.hodanet.system.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

public class HttpURLConnectionUtil {

    private static final Logger log      = Logger.getLogger(HttpURLConnectionUtil.class);
    private static final String PREFIX   = "--";
    private static final String LINE_END = "\r\n";
    private static final String BOUNDARY = UUID.randomUUID().toString();

    public static String processFile(String url, String accessToken, String type, String fileName, int fileLength,
                                     String contentType, InputStream is) {
        HttpURLConnection conn = null;
        BufferedReader buffer = null;
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            url = url + accessToken + "&type=" + type;
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(15000);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
            dos.writeBytes("Content-Disposition: form-data; filename=\"" + fileName + ";filelength=\"" + fileLength
                           + "\";Content-Type=\"" + contentType + "\"" + LINE_END);
            dos.writeBytes(LINE_END);
            int bufferSize = 1024;
            byte[] bs = new byte[bufferSize];
            int length = -1;
            while ((length = is.read(bs)) != -1) {
                dos.write(bs, 0, length);
            }
            dos.writeBytes(LINE_END);
            dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
            dos.flush();
            is.close();
            sb = new StringBuilder();
            buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            return line;
        } finally {
            try {
                if (null != buffer) {
                    buffer.close();
                    buffer = null;
                }
                conn.disconnect();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String processFile(String url, Map<String, String> paramMap, Map<String, InputStream> inputMap) {
        HttpURLConnection conn = null;
        BufferedReader buffer = null;
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(15000);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            if (paramMap != null) {
                for (String key : paramMap.keySet()) {
                    sb = new StringBuilder();
                    dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
                    dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + LINE_END);
                    dos.writeBytes(LINE_END);
                    dos.write(paramMap.get(key).getBytes("UTF-8"));
                    dos.writeBytes(LINE_END);
                    dos.write(sb.toString().getBytes());
                }
            }

            if (inputMap != null) {
                for (String key : inputMap.keySet()) {
                    InputStream is = inputMap.get(key);
                    if (is != null) {
                        dos.writeBytes(PREFIX + BOUNDARY + LINE_END);
                        // dos.writeBytes("Content-Disposition:form-data; name=\"" + key + "\"; filename=\""
                        // + System.currentTimeMillis() + "\"" + LINE_END);
                        dos.writeBytes("Content-Disposition: form-data; filename=\"" + key + "\";filelength=\""
                                       + is.available() + "\";Content-Type=\"image/jpeg\"" + LINE_END);
                        dos.writeBytes(LINE_END);
                        int bufferSize = 1024;
                        byte[] bs = new byte[bufferSize];
                        int length = -1;
                        while ((length = is.read(bs)) != -1) {
                            dos.write(bs, 0, length);
                        }
                        dos.writeBytes(LINE_END);
                        dos.flush();
                        is.close();
                    }
                }
                dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
            }
            sb = new StringBuilder();
            buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            return line;
        } finally {
            try {
                if (null != buffer) {
                    buffer.close();
                    buffer = null;
                }
                conn.disconnect();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String process(String url, String param) {
        HttpURLConnection conn = null;
        BufferedReader buffer = null;
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);// 
            conn.setDoOutput(true);// 
            conn.setUseCaches(false);// ʹCache
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(15000);
            conn.connect();

            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print(param);
            ps.flush();
            ps.close();

            buffer = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            conn.disconnect();
            // log.info("wx response result:" + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            log.error("url process error,url=" + url + ",param=" + param + ";error detail:" + e.getMessage());
        } finally {
            try {
                if (null != buffer) {
                    buffer.close();
                    buffer = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return "";
    }

    public static String postMethod(String url, NameValuePair... params) {
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
            if (params != null && params.length > 0) {
                for (NameValuePair param : params) {
                    postMethod.addParameter(param);
                }
            }
            status = client.executeMethod(postMethod);
            if (status != HttpStatus.SC_OK) {
                // TODO error option
            }
            InputStreamReader isr = new InputStreamReader(postMethod.getResponseBodyAsStream());
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String str = null;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            postMethod.releaseConnection();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String multipartPostMethod(String url, NameValuePair[] params, Map<String, File> map) {
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
            if (params != null && params.length > 0) {
                for (NameValuePair param : params) {
                    postMethod.addParameter(param.getName(), param.getValue());
                }
            }
            if (map != null && map.size() > 0) {
                for (String key : map.keySet()) {
                    postMethod.addParameter(key, map.get(key));
                }
            }
            status = client.executeMethod(postMethod);
            if (status != HttpStatus.SC_OK) {
                // TODO error option
            }
            InputStreamReader isr = new InputStreamReader(postMethod.getResponseBodyAsStream());
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String str = null;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            postMethod.releaseConnection();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        // String url =
        // "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=HIOLMaAGuMl6VOZpgOcqSSJF5l8aJgNRrDUbzGHrWOmfF5Vqzd2UBvC2Rwevy-lQ19dNgiOW-RiIXEcGo45_6Q";
        // Map<String, String> paramMap = new HashMap<String, String>();
        // // paramMap.put("access_token",
        // // "OvE4U9_JZNZUVsLXQjdXV5WC_C4D3TOKvJwKxmh2Vxk9CblcHke6ckp1pnsDeCHEwppQrrUaTlkfuoDsDITMsA");
        // paramMap.put("type", "image");
        // Map<String, InputStream> inputMap = new HashMap<String, InputStream>();
        // inputMap.put("2.jpg", new FileInputStream("d:/test20140926.jpg"));
        // String resultStr = processFile(url, paramMap, inputMap);
        // System.out.println(resultStr);
        // JSON json= uploadmedia("image",
        // "OvE4U9_JZNZUVsLXQjdXV5WC_C4D3TOKvJwKxmh2Vxk9CblcHke6ckp1pnsDeCHEwppQrrUaTlkfuoDsDITMsA", "2.jpg", new
        // File("d:\\test20140926.jpg"), "image/jepg", "5419");
        // System.out.println(json.toJSONString());

        // System.out.println(processFile("http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=",
        // "HIOLMaAGuMl6VOZpgOcqSSJF5l8aJgNRrDUbzGHrWOmfF5Vqzd2UBvC2Rwevy-lQ19dNgiOW-RiIXEcGo45_6Q",
        // "image", "2.jpg", 5419, "image/jepg", new FileInputStream("d:/test20140926.jpg")));

        // InputStream is = getInputStream(WeixinApiConstants.getMediaUrl,
        // "HIOLMaAGuMl6VOZpgOcqSSJF5l8aJgNRrDUbzGHrWOmfF5Vqzd2UBvC2Rwevy-lQ19dNgiOW-RiIXEcGo45_6Q&media_id=WLt402sqVVvesNknpCI43gBMyfe59gdU_Sbf3H99YOX9JqtL6u4a_fCd7xq8f6CA");
        // FileUtil.copy(is, new FileOutputStream("D:/test201409261521.jpg"));
    }

}

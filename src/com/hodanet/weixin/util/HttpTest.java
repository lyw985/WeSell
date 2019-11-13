package com.hodanet.weixin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @anthor lyw
 * @version 2014-2-27 涓婂崍9:59:37
 */
public class HttpTest {

    static void testPost(String urlStr, String xmlInfo) {
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");

            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            System.out.println("urlStr=" + urlStr);
            System.out.println("xmlInfo=" + xmlInfo);
            out.write(new String(xmlInfo.getBytes("UTF-8")));
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test(String url_base) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("    <ToUserName><![CDATA[toUser]]></ToUserName>");
        sb.append("    <FromUserName><![CDATA[fromUser]]></FromUserName> ");
        sb.append("    <CreateTime>1348831860</CreateTime>");
        sb.append("    <MsgType><![CDATA[text]]></MsgType>");
        sb.append("    <Content><![CDATA[test]]></Content>");
        sb.append("    <MsgId>1234567890123456</MsgId>");
        sb.append("</xml>");
        // sb.append("<xml><ToUserName><![CDATA[gh_57f3719554f1]]></ToUserName><FromUserName><![CDATA[oLujot4xsq_nMUXlx6PoSzCaiM4I]]></FromUserName><CreateTime>1397464296</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[CLICK]]></Event><EventKey><![CDATA[YULE_LVYOU]]></EventKey></xml>");
        testPost(url_base, sb.toString());
    }

    public static void testImg(String url_base) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("    <ToUserName><![CDATA[toUser]]></ToUserName>");
        sb.append("    <FromUserName><![CDATA[fromUser]]></FromUserName> ");
        sb.append("    <CreateTime>1348831860</CreateTime>");
        sb.append("    <MsgType><![CDATA[image]]></MsgType>");
        sb.append("    <MediaId><![CDATA[6YCZ7iww9Ned0hUjQtklPYlxH5EOQGQujHn7qQRBCE_rm39eqV8zQVUt85GXNQF5]]></MediaId>");
        sb.append("    <MsgId>1234567890123456</MsgId>");
        sb.append("</xml>");
        // sb.append("<xml><ToUserName><![CDATA[gh_57f3719554f1]]></ToUserName><FromUserName><![CDATA[oLujot4xsq_nMUXlx6PoSzCaiM4I]]></FromUserName><CreateTime>1397464296</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[CLICK]]></Event><EventKey><![CDATA[YULE_LVYOU]]></EventKey></xml>");
        testPost(url_base, sb.toString());
    }

    public static void main(String[] args) {
        String url_base = "http://localhost:8080/WeChatServlet?serverCode=jtys";
        // url_base = "http://wap.zj.sjqm.net";

        // test(url_base);
        testImg(url_base);
    }

}

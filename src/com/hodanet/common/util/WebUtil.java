package com.hodanet.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class WebUtil {

    public static void addOrReplaceCookie(HttpServletResponse response, String key, String value, int expiry) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void removeCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void responseText(HttpServletResponse response, String text) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.print(text);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    public static void responseXML(HttpServletResponse response, String text) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.print(text);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    public static void responseFile(HttpServletResponse response, String path) throws IOException {
        String fileName = path.substring(path.lastIndexOf(File.separator) + 1);
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);// inline
        setFileTypeHeader(response, fileName);

        FileInputStream ins = new FileInputStream(new File(path));
        OutputStream osm = response.getOutputStream();
        byte[] buffer = new byte[1000];
        int len = 0;
        while ((len = ins.read(buffer)) > 0) {
            osm.write(buffer, 0, len);
        }
        osm.flush();
        ins.close();
        osm.close();
    }

    private static void setFileTypeHeader(HttpServletResponse response, String fileName) {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        if ("pdf".equals(type)) {
            response.setContentType("application/pdf");
        } else if ("doc".equals(type)) {
            response.setContentType("application/msword");
        } else if ("ppt".equals(type)) {
            response.setContentType("application/vnd.ms-powerpoint");
        }
    }

    public static byte[][] getByteArrayFromServlet(HttpServletRequest request, String paramName) throws IOException,
                                                                                                FileUploadException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        List<FileItem> fileItemList = servletFileUpload.parseRequest(request);
        List<byte[]> list = new ArrayList<byte[]>();
        for (FileItem fileItem : fileItemList) {
            String fileName = fileItem.getFieldName();
            if (fileName.equals(paramName)) {
                InputStream is = fileItem.getInputStream();
                byte[] bs = new byte[is.available()];
                is.read(bs);
                list.add(bs);
                is.close();
            }
        }
        return list.toArray(new byte[list.size()][]);
    }

    public static byte[] getBinary(HttpServletRequest request, String paramName) throws IOException {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> files = multipartRequest.getFiles(paramName);
            if (files.size() == 0) {
                return null;
            }
            byte[] param = null;
            for (MultipartFile file : files) {
                InputStream in = file.getInputStream();
                param = new byte[in.available()];
                in.read(param);
                in.close();
            }
            return param;
        }
        return null;
    }

    public static byte[][] getBinarys(HttpServletRequest request, String paramName) throws IOException {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> files = multipartRequest.getFiles(paramName);
            if (files.size() == 0) {
                return null;
            }
            byte[][] param = new byte[files.size()][];
            for (int i = 0; i < files.size(); i++) {
                InputStream in = files.get(i).getInputStream();
                byte[] bs = new byte[in.available()];
                in.read(bs);
                in.close();
                param[i] = bs;
            }
            return param;
        }
        return null;
    }

    public static Object getObject(HttpServletRequest request, String paramName, Object parent) throws IOException,
                                                                                               FileUploadException {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> files = multipartRequest.getFiles(paramName);
            for (MultipartFile file : files) {
                InputStream in = file.getInputStream();
                byte[] bs = new byte[in.available()];
                in.read(bs);
                in.close();
                return bs;
            }
        }
        return null;
    }

    public static String getString(HttpServletRequest request, String paramName) {
        return request.getParameter(paramName);
    }

    public static String[] getStrings(HttpServletRequest request, String paramName) {
        return request.getParameterValues(paramName);
    }

    public static Integer getInt(HttpServletRequest request, String paramName, Integer defaultValue) {
        try {
            String str = request.getParameter(paramName);
            if (str == null) {
                return defaultValue;
            }
            return Integer.parseInt(request.getParameter(paramName));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Integer getInt(HttpServletRequest request, String paramName) {
        return getInt(request, paramName, null);
    }

    public static Integer[] getInts(HttpServletRequest request, String paramName) {
        String[] params = request.getParameterValues(paramName);
        if (params == null) {
            return null;
        }
        Integer[] param = new Integer[params.length];
        for (int i = 0; i < params.length; i++) {
            param[i] = Integer.parseInt(params[i]);
        }
        return param;
    }

}

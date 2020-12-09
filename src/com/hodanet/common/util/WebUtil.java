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
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class WebUtil {

	public static JSONObject getJsonStringFromUrl(String url, NameValuePair[] params) {
		String respString = getStringFromUrl(url, params);
		return (JSONObject) JSONObject.parse(respString);
	}

	public static String getStringFromUrl(String url, NameValuePair[] params) {
		/* 1 生成 HttpClinet 对象并设置参数 */
		HttpClient httpClient = new HttpClient();
		// 设置 Http 连接超时为5秒
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		/* 2 生成 GetMethod 对象并设置参数 */
		GetMethod getMethod = new GetMethod(url);
		getMethod.setQueryString(params);
		// 设置 get 请求超时为 5 秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// 设置请求重试处理，用的是默认的重试处理：请求三次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		String response = "";
		/* 3 执行 HTTP GET 请求 */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			/* 4 判断访问的状态码 */
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("请求出错: " + getMethod.getStatusLine());
			}
			/* 5 处理 HTTP 响应内容 */
			// HTTP响应头部信息，这里简单打印
			Header[] headers = getMethod.getResponseHeaders();
//			for (Header h : headers)
//				System.out.println(h.getName() + "------------ " + h.getValue());
			// 读取 HTTP 响应内容，这里简单打印网页内容
			byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
			response = new String(responseBody);
//			System.out.println("----------response:" + response);
			// 读取为 InputStream，在网页内容数据量大时候推荐使用
			// InputStream response = getMethod.getResponseBodyAsStream();
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("请检查输入的URL!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			System.out.println("发生网络异常!");
			e.printStackTrace();
		} finally {
			/* 6 .释放连接 */
			getMethod.releaseConnection();
		}
		return response;
	}

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

	public static byte[][] getByteArrayFromServlet(HttpServletRequest request, String paramName)
			throws IOException, FileUploadException {
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

	public static Object getObject(HttpServletRequest request, String paramName, Object parent)
			throws IOException, FileUploadException {
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

	public static void main(String[] args) {
		String url = "http://api.map.baidu.com/place/v2/search?";
		ArrayList<NameValuePair> arr = new ArrayList<NameValuePair>();
		arr.add(new NameValuePair("query", "上海 普陀区 城区  上海市普陀区富平路727弄5号101室"));
		arr.add(new NameValuePair("region", "上海"));
		arr.add(new NameValuePair("page_size", "1"));
		arr.add(new NameValuePair("output", "json"));
		arr.add(new NameValuePair("ak", "eX0d4EPG7wrIDjTKjQ9MYgSBhUwdw43v"));

//		arr.add(new NameValuePair("query", "上海 普陀区 城区  上海市普陀区富平路727弄5号101室"));

		JSONObject obj = getJsonStringFromUrl(url, arr.toArray(new NameValuePair[arr.size()]));
		System.out.println(obj.toJSONString());

		int jsonStatus = obj.getIntValue("status");
		if (jsonStatus != 0) {
			throw new RuntimeException("调用api异常");
		}
		int jsonTotal = obj.getIntValue("total");
	
		JSONArray results=obj.getJSONArray("results");
		if(results.size()==0) {
				throw new RuntimeException("未找到对应定位");
		}
		JSONObject jsonLocation = results.getJSONObject(0).getJSONObject("location");
		String lat=jsonLocation.getString("lat");
		String lng=jsonLocation.getString("lng");
		System.out.println(lat+":"+lng);
//		{
//		    "status":0,
//		    "message":"ok",
//		    "total":11,
//		    "results":[
//		        {
//		            "name":"金口路155弄-7号",
//		            "location":{
//		                "lat":31.255673,
//		                "lng":121.583896
//		            },
//		            "address":"上海市浦东新区金口路155弄7号",
//		            "province":"上海市",
//		            "city":"上海市",
//		            "area":"浦东新区",
//		            "street_id":"aa1afc83d3db16a0f03cba6e",
//		            "detail":1,
//		            "uid":"aa1afc83d3db16a0f03cba6e"
//		        }
//		    ]
//		}
	}

}

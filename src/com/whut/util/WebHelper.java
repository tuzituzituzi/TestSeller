package com.whut.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.whut.config.Constants;

/**
 * 处理网络请求
 * @author lx 
 */
public class WebHelper {
	
	/**
	 * 发送GET请求，获取返回Json字符串
	 * @param url 请求地址
	 * @param list 参数列表
	 * @return json字符串
	 * @throws Exception
	 */
	public static String getJsonString(String url)
			throws Exception {
		HttpGet get = new HttpGet(url);
		get.addHeader("Cookie", Constants.USER_COOKIE);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(get);
		return showResponseResult(response);
	}
	
	/**
	 * 发送POST请求，获取返回Json字符串
	 * @param url 请求地址
	 * @param list 参数列表
	 * @return json字符串
	 * @throws Exception
	 */
	public static String postJsonString(String url, List<NameValuePair> list)
			throws Exception {
		HttpEntity entity = new UrlEncodedFormEntity(list);
		HttpPost post = new HttpPost(url);
		post.addHeader("Cookie",Constants.USER_COOKIE);
		post.setEntity(entity);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(post);
		return showResponseResult(response);
	}

	/**
	 * 解析出返回内容
	 * @param response 返回HttpRespond对象
	 * @return 返回内容
	 * @throws Exception
	 */
	private static String showResponseResult(HttpResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		if (response == null)
			return null;
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = "";
		StringBuilder res = new StringBuilder("");
		while (null != (line = reader.readLine())) {
			res.append(line);
		}
		return res.toString();
	}

}

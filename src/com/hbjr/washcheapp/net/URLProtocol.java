package com.hbjr.washcheapp.net;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.hbjr.washcheapp.config.ConstantS;

public class URLProtocol {

	
			
	/**
	 * 获取JSON数据(GET)
	 * 
	 * @param map
	 * @return
	 */
	public static String getJSonString(Map<String, String> map) {
		try {
			//请求参数的map集合
			System.out.println("开始一次调用请求==================================================");			
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Entry<String, String> string : map.entrySet()) {
				System.out.println("调用参数是：===key："+string.getKey()+" ====value:"+string.getValue());
				params.add(new BasicNameValuePair(string.getKey(), string.getValue()));
			}
			HttpEntity entity = HttpConnection.getEntity(ConstantS.SERVER_HOST,
					params, HttpConnection.METHOD_GET);			
			String responseCode = EntityUtils.toString(entity, "GBK");
        	System.out.println("调用请求结果返回=================================================="+responseCode);			
			return responseCode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取JSON数据(POST)
	 * 
	 * @param map
	 * @return
	 */
	public static String postJSonString(Map<String, String> map) {
		try {
			System.out.println("开始一次调用请求=====POST========================================");			
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Entry<String, String> string : map.entrySet()) {
				System.out.println("调用参数是：===key："+string.getKey()+" ====value:"+string.getValue());				
				params.add(new BasicNameValuePair(string.getKey(), string
						.getValue()));
			}
			HttpEntity entity = HttpConnection.getEntity(ConstantS.SERVER_HOST,	params, HttpConnection.METHOD_POST);

			String responseCode = EntityUtils.toString(entity, "GBK");
			return responseCode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
}

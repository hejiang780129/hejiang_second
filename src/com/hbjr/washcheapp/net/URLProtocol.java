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
	 * ��ȡJSON����(GET)
	 * 
	 * @param map
	 * @return
	 */
	public static String getJSonString(Map<String, String> map) {
		try {
			//���������map����
			System.out.println("��ʼһ�ε�������==================================================");			
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Entry<String, String> string : map.entrySet()) {
				System.out.println("���ò����ǣ�===key��"+string.getKey()+" ====value:"+string.getValue());
				params.add(new BasicNameValuePair(string.getKey(), string.getValue()));
			}
			HttpEntity entity = HttpConnection.getEntity(ConstantS.SERVER_HOST,
					params, HttpConnection.METHOD_GET);			
			String responseCode = EntityUtils.toString(entity, "GBK");
        	System.out.println("��������������=================================================="+responseCode);			
			return responseCode;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ȡJSON����(POST)
	 * 
	 * @param map
	 * @return
	 */
	public static String postJSonString(Map<String, String> map) {
		try {
			System.out.println("��ʼһ�ε�������=====POST========================================");			
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Entry<String, String> string : map.entrySet()) {
				System.out.println("���ò����ǣ�===key��"+string.getKey()+" ====value:"+string.getValue());				
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

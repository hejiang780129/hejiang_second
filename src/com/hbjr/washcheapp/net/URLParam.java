package com.hbjr.washcheapp.net;

import java.util.List;

import org.apache.http.NameValuePair;

public class URLParam {
	/**
	 * ����URL�ַ���
	 * @param params ����
	 * @param sb ����
	 */
	public static String buildParams(List<NameValuePair> params, StringBuilder sb) {
		try {
			for (NameValuePair pair : params) {
				sb.append(pair.getName()).append('=').append(pair.getValue())
						.append('&');
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

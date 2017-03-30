package com.hbjr.washcheapp.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.hbjr.washcheapp.bean.WxcWeatherBean;

import android.util.Log;

public class WeatherQueryManageImpl implements WeatherQueryManage {
	private final String TAG = "message";
	@Override
	public WxcWeatherBean[] weatherquery(String CityId) {
		WxcWeatherBean[] WF = new WxcWeatherBean[3];
		//http://m.weather.com.cn/data/101070101.html
		String URL = "http://m.weather.com.cn/data/"+CityId+".html";
		String Weather_Result="";
		HttpGet httpRequest = new HttpGet(URL);
		// ���HttpResponse����
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// ȡ�÷��ص�����
				Weather_Result = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			Log.i(TAG, e.toString());
			WF[0] = new WxcWeatherBean();
			WF[1] = new WxcWeatherBean();
			WF[2] = new WxcWeatherBean();
			return WF;
	}
	//�����ǶԷ���JSON���ݵĽ���
	if(null!=Weather_Result&&!"".equals(Weather_Result)){
		try {
			JSONObject JO = new JSONObject(Weather_Result).getJSONObject("weatherinfo");
			for(int i=0;i<WF.length;i++){
				WxcWeatherBean weaf = new WxcWeatherBean();
				//3��������ʱ�����һ���
				weaf.setName(JO.getString("city"));
				weaf.setDdate(JO.getString("date_y"));
				weaf.setWeek(JO.getString("week"));
				weaf.setTemp(JO.getString("temp"+(i+1)));
				weaf.setWind(JO.getString("wind"+(i+1)));
				weaf.setWeather(JO.getString("weather"+(i+1)));
				WF[i]=weaf;
			}
		} catch (JSONException e) {
			Log.i(TAG, e.toString());
			WF[0] = new WxcWeatherBean();
			WF[1] = new WxcWeatherBean();
			WF[2] = new WxcWeatherBean();
			return WF;
		}
	}
	return WF;
	}
}

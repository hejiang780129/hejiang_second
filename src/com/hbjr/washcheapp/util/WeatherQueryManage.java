package com.hbjr.washcheapp.util;

import com.hbjr.washcheapp.bean.WxcWeatherBean;



public interface WeatherQueryManage {
	/**
	 * ��ѯ��������
	 * @param CityId ��Ӧ���е�id
	 * @return ��ʱ����3�����������
	 */
	public WxcWeatherBean[] weatherquery(String CityId);

}

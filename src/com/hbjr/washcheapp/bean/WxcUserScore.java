package com.hbjr.washcheapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WxcUserScore implements Serializable {

	   private static final long serialVersionUID = -758459502806858414L;	

	   private String score;//���ֶ�ȣ�get_typeΪ0����ʾ��ȡ�Ļ��ֶ�ȣ�Ϊ1����ʾ�һ��Ļ��ֶ��
		
	   private String get_type;//ȡ�÷�ʽ���� 0����ȡ����;1�����ֶһ�
		
		private String get_type_dsc;//��ȡ��ʽ�������һ����
		
		private String get_exercise_id;//ȡ�û��ֵĻid
				
		private String get_date;//���ֻ�ȡ��һ�ʱ��
		
		public static List<WxcUserScore> info = new ArrayList<WxcUserScore>();		
		
	    static  
	    {
	    	info.add(new WxcUserScore("0","ע���ͻ���","901","20","2014-11-01"));
	    	info.add(new WxcUserScore("0","�����ͻ���","901","30","2014-11-01"));
	    	info.add(new WxcUserScore("0","�����ͻ���","901","10","2014-11-01"));
	    	info.add(new WxcUserScore("0","ϴ���ͻ���","901","10","2014-11-01"));
	    	info.add(new WxcUserScore("1","�һ�5Ԫ","004","5","2014-10-06"));
	    	info.add(new WxcUserScore("1","�һ�20Ԫ","005","10","2014-11-08"));
	    	info.add(new WxcUserScore("1","�һ�20Ԫ","006","20","2014-11-27"));	    	

	    }

	    
	    public WxcUserScore(){
	    	
	    }
	    
		public WxcUserScore(String get_type,
				String get_type_dsc, String get_exercise_id, String score,
				 String get_date) {
			super();
			this.get_type = get_type;
			this.get_type_dsc = get_type_dsc;
			this.get_exercise_id = get_exercise_id;
			this.score = score;
			this.get_date = get_date;
		}

		
		public String getGet_type() {
			return get_type;
		}

		public void setGet_type(String get_type) {
			this.get_type = get_type;
		}

		public String getGet_type_dsc() {
			return get_type_dsc;
		}

		public void setGet_type_dsc(String get_type_dsc) {
			this.get_type_dsc = get_type_dsc;
		}

		public String getGet_exercise_id() {
			return get_exercise_id;
		}

		public void setGet_exercise_id(String get_exercise_id) {
			this.get_exercise_id = get_exercise_id;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}


		public String getGet_date() {
			return get_date;
		}

		public void setGet_date(String get_date) {
			this.get_date = get_date;
		}	    
	
}

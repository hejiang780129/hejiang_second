package com.hbjr.washcheapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WxcUserScore implements Serializable {

	   private static final long serialVersionUID = -758459502806858414L;	

	   private String score;//积分额度；get_type为0：表示获取的积分额度，为1：表示兑换的积分额度
		
	   private String get_type;//取得方式代码 0：获取积分;1：积分兑换
		
		private String get_type_dsc;//获取方式描述，兑换额度
		
		private String get_exercise_id;//取得积分的活动id
				
		private String get_date;//积分获取或兑换时间
		
		public static List<WxcUserScore> info = new ArrayList<WxcUserScore>();		
		
	    static  
	    {
	    	info.add(new WxcUserScore("0","注册送积分","901","20","2014-11-01"));
	    	info.add(new WxcUserScore("0","分享送积分","901","30","2014-11-01"));
	    	info.add(new WxcUserScore("0","评价送积分","901","10","2014-11-01"));
	    	info.add(new WxcUserScore("0","洗车送积分","901","10","2014-11-01"));
	    	info.add(new WxcUserScore("1","兑换5元","004","5","2014-10-06"));
	    	info.add(new WxcUserScore("1","兑换20元","005","10","2014-11-08"));
	    	info.add(new WxcUserScore("1","兑换20元","006","20","2014-11-27"));	    	

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

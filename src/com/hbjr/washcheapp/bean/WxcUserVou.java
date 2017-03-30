package com.hbjr.washcheapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hbjr.washcheapp.util.WxcPublicUtil;

public class WxcUserVou  implements Serializable {
	
    private static final long serialVersionUID = -758459502806858414L;	
	
	private String vou_id;//活动id
	
	private String total_num;//总额
	
	private String get_type;//活动类型://0:代金券临时活动;2：获取代金券方式
	
	private String get_type_dsc;//活动名称
	
	private String get_date;
	
	private int cur_status=-1;//0：有效，1：已过期
	
	private String cur_status_desc;//0：有效，1：已过期
	
	public String getCur_status_desc() {
		return cur_status_desc;
	}


	public void setCur_status_desc(String cur_status_desc) {
		this.cur_status_desc = cur_status_desc;
	}

	private String limit_date;//有效期
	
	private String limit_datetimes;//有效时间
	
	/**
	 * 
	 * 
	 */
	
	
	public static List<WxcUserVou> info = new ArrayList<WxcUserVou>();		
	
    static  
    {
    	info.add(new WxcUserVou("001","100", "0", "活动1获取", "2014-12-31",getIvalid("1"),getStatusDsc(getIvalid("1")),"1", " "));
    	info.add(new WxcUserVou("002","200", "0", "活动2获取", "2014-12-28",getIvalid("1"),getStatusDsc(getIvalid("1")),"1", " "));
    	info.add(new WxcUserVou("003","300", "0", "活动3获取", "2014-11-18",getIvalid("1"),getStatusDsc(getIvalid("1")),"1", " "));
    	info.add(new WxcUserVou("004","400", "0", "活动4获取", "2014-11-20",getIvalid("1"),getStatusDsc(getIvalid("1")),"1", " "));
    	info.add(new WxcUserVou("005","500", "0", "活动5获取", "2014-12-20",getIvalid( "1"),getStatusDsc(getIvalid( "1")),"1", " "));    	

    }
	
	public WxcUserVou(){
		
	}

	public WxcUserVou(String vou_id, String total_num, String get_type,String get_type_dsc, String get_date, int cur_status,String cur_status_dsc,
			String limit_date, String limit_datetimes) {
		super();
		this.vou_id = vou_id;
		this.total_num = total_num;
		this.get_type = get_type;
		this.get_type_dsc = get_type_dsc;
		this.get_date = get_date;
		this.cur_status = cur_status;
		this.cur_status_desc=cur_status_dsc;
		this.limit_date = limit_date;
		this.limit_datetimes = limit_datetimes;
	}
	
	
    public static int getIvalid(String limit_date){
		if (limit_date.equalsIgnoreCase("1")) return 0;
    	boolean isValid=WxcPublicUtil.compareDate(WxcPublicUtil.getCurDate(), limit_date);
		if (!isValid){
			return 0;
		}else{
            return 1;
		}
	}
    
    
 public static String getStatusDsc(int status){
	  
	  if (status==0){
		  return "有效";
	  }else{
		  return "已过期";
	  }
   
	}

	public String getVou_id() {
		return vou_id;
	}

	public void setVou_id(String vou_id) {
		this.vou_id = vou_id;
	}

	public String getTotal_num() {
		return total_num;
	}

	public void setTotal_num(String total_num) {
		this.total_num = total_num;
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

	public String getGet_date() {
		return get_date;
	}

	public void setGet_date(String get_date) {
		this.get_date = get_date;
	}

	public int getCur_status() {
		return cur_status;
	}

	public void setCur_status(int cur_status) {
		this.cur_status = cur_status;
	}

	public String getLimit_date() {
		return limit_date;
	}

	public void setLimit_date(String limit_date) {
		this.limit_date = limit_date;
	}

	public String getLimit_datetimes() {
		return limit_datetimes;
	}

	public void setLimit_datetimes(String limit_datetimes) {
		this.limit_datetimes = limit_datetimes;
	}
	
	
	
	
	

}

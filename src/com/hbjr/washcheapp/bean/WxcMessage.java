package com.hbjr.washcheapp.bean;

import java.util.ArrayList;
import java.util.List;

public class WxcMessage {
	
	 private static final long serialVersionUID = -758459502806858414L;
    
	 private String message_id;
	 private String message_type;//0��ϵͳ��Ϣ��1��������Ϣ
	 private String messaage_type_dsc;
	 private String message_title;
	 private String message_content;
	 private String message_date;
	 
	 public static List<WxcMessage> info = new ArrayList<WxcMessage>();		
		
	    static  
	    {
	    	info.add(new WxcMessage("001","0","ϵͳ��������","ϵͳ����2014��11��28�ս���1.0�汾������������","2014-11-28"));
	    	info.add(new WxcMessage("002","1","�󶨻���֪ͨ","����2014��11��2�ջ����2�����֣���鿴","2014-11-28"));	    	
	    	info.add(new WxcMessage("003","1","�󶨴���ȯ֪ͨ","����2014��11��8�ջ����100Ԫ����ȯ����鿴","2014-11-27"));	    	

	    }
	
	
	public WxcMessage(){
		
	}
	    
	public WxcMessage(String message_id, String message_type,
			String message_title, String message_content, String message_date) {
		super();
		this.message_id = message_id;
		this.message_type = message_type;
		this.message_title = message_title;
		this.message_content = message_content;
		this.message_date = message_date;
	}
	
	 public String getMessaage_type_dsc() {
		if (message_type.equalsIgnoreCase("0")){
			return "ϵͳ��Ϣ";
		}else{
			return "������Ϣ";
		}
	}
	public void setMessaage_type_dsc(String messaage_type_dsc) {
		this.messaage_type_dsc = messaage_type_dsc;
	}
	
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getMessage_type() {
		return message_type;
	}
	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}
	public String getMessage__title() {
		return message_title;
	}
	public void setMessage__title(String message__title) {
		this.message_title = message__title;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public String getMessage_date() {
		return message_date;
	}
	public void setMessage_date(String message_date) {
		this.message_date = message_date;
	}
	 
	 

}

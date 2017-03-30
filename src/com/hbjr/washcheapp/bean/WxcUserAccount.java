package com.hbjr.washcheapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.text.style.UnderlineSpan;

public class WxcUserAccount implements Serializable {
	
	private String vou_balance;//����ȯ���
	
	private String vou_total;//����ȯ��Ч�ܶ�
	
	private String vou_consume;//�����Ѵ���ȯ
	
	
	
	private String order_num;//���Ѵ���
	
	private String order_sum;//���Ѷ��
	
	private String order_pay_online;//֧����֧�����
	
	
	private String score_sum;//�����ܶ�
	
	private String score_exchange;//���ֶһ��ܶ�
	
	private String score_get;//���ֻ�ȡ�ܶ�
	
	
	public static WxcUserAccount useraccount;
	private static List<WxcUserVou> uservous;
	private static String user_balance,user_consume,user_total;
	
	
	private static WxcUserScore userscore;
	private static List<WxcUserScore> userscores;
	private static String str_score_sum,str_score_exchange,str_score_get;
	
	private static List<WxcUserOrder> userorders;
	private static String str_order_num,str_order_sum,str_order_sum_online;
	
	
	
	
    static  
    {
         invokeStatic(); 
    }
	
    public static void invokeStatic(){
   
//     	/**
//    	 * ����ȯ��� 
//    	 */
//    	 user_total=calc_total();
//    	 user_consume=calc_consume();
//    	 user_balance=calc_balance();
//    	 /**
//    	  * �������
//    	  */
//    	 calc_score();
    	 
    	 /**
    	  * �����������
    	  */
    	 useraccount=new WxcUserAccount(user_balance,user_total,user_consume,str_order_num,str_order_sum,str_order_sum_online,str_score_sum,str_score_exchange,str_score_get);
    }
	
	public WxcUserAccount(){
		
	}
    
    
	public WxcUserAccount(String vou_balance, String vou_total,
			String vou_consume,String order_num, String order_sum,
			String pay_online,
			String score_sum, String score_exchange, String score_get) {
		super();
		this.vou_balance = vou_balance;
		this.vou_total = vou_total;
		this.vou_consume = vou_consume;
		this.order_num = order_num;
		this.order_sum = order_sum;
		this.order_pay_online = pay_online;
		this.score_sum = score_sum;
		this.score_exchange = score_exchange;
		this.score_get = score_get;
	}


	public String getOrder_num() {
		return order_num;
	}

	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}

	public String getOrder_sum() {
		return order_sum;
	}

	public void setOrder_sum(String order_sum) {
		this.order_sum = order_sum;
	}


	public String getOrder_Pay_online() {
		return order_pay_online;
	}


	public void setOrder_Pay_online(String order_pay_online) {
		this.order_pay_online = order_pay_online;
	}


	public String getScore_sum() {
		return score_sum;
	}

	public void setScore_sum(String score_sum) {
		this.score_sum = score_sum;
	}

	public String getScore_exchange() {
		return score_exchange;
	}

	public void setScore_exchange(String score_exchange) {
		this.score_exchange = score_exchange;
	}

	public String getScore_get() {
		return score_get;
	}

	public void setScore_get(String score_get) {
		this.score_get = score_get;
	}

	public String getVou_balance() {
		return vou_balance;
	}

	public void setVou_balance(String vou_balance) {
		this.vou_balance = vou_balance;
	}

	public String getVou_total() {
		return vou_total;
	}

	public void setVou_total(String vou_total) {
		this.vou_total = vou_total;
	}

	public String getVou_consume() {
		return vou_consume;
	}

	public void setVou_consume(String vou_consume) {
		this.vou_consume = vou_consume;
	}

	
    public static String calc_balance(){
       String calc_total=calc_total();
       String calc_consum=calc_consume();
       int calc_balance=Integer.parseInt(calc_total)-Integer.parseInt(calc_consum);
  	    System.out.println("������Ĵ���ȯ����ǣ���"+calc_balance);       
       if (calc_balance<0) calc_balance=0;
    	return String.valueOf(calc_balance);
    }
	
    public static String calc_total(){
    	uservous=WxcUserVou.info;
    	WxcUserVou uservou;
    	int total=0;
    	for (int i=0;i<uservous.size();i++){
            uservou=(WxcUserVou)uservous.get(i);
            if (uservou.getCur_status()==0){
            	total=total+Integer.parseInt(uservou.getTotal_num());
            }
    	}    	
    	System.out.println("���������Ч����ȯ�ܶ���ǣ�"+total);
    	return String.valueOf(total);
    	
    }
    
    public static String calc_consume(){
    	userorders=WxcUserOrder.orderinfos;
    	WxcUserOrder userorder;
    	int total=0;
    	int total_online=0;//����֧���ܶ��
    	int total_sum=0;//�����ܶ��
    	for (int i=0;i<userorders.size();i++){
    		userorder=(WxcUserOrder)userorders.get(i);
    		if(!userorder.getPay_vou().equalsIgnoreCase("")){
    		  total=total+Integer.parseInt(userorder.getPay_vou());
    		}
    		if(!userorder.getPay_online().equalsIgnoreCase("")){
      		  total_online=total_online+Integer.parseInt(userorder.getPay_online());
      		}
    		  total_sum=total_sum+Integer.parseInt(userorder.getPay());
    	}
    	System.out.println("����������Ѵ���ȯ�ܶ���ǣ�"+total);
    	System.out.println("�����������֧���ܶ���ǣ�"+total_online);
    	System.out.println("����������ѵ��ܶ���ǣ�"+total_sum); 
    	System.out.println("����������ѵĴ�����"+userorders.size());
    	str_order_num=String.valueOf(userorders.size());
    	str_order_sum=String.valueOf(total_sum);
    	str_order_sum_online=String.valueOf(total_online);
    	return String.valueOf(total);
    	
    }
    
    public static void calc_score(){
    	userscores=WxcUserScore.info;
    	WxcUserScore userscore;
    	int score_total=0;
    	int score_exchange=0;
    	int score_get=0;
    	int total=0;
    	for (int i=0;i<userscores.size();i++){
    		userscore=(WxcUserScore)userscores.get(i);
    		if (userscore.getGet_type().equalsIgnoreCase("0")){
    			score_get=score_get+Integer.parseInt(userscore.getScore());
    			score_total=score_total+Integer.parseInt(userscore.getScore());
    			System.out.println("��һ�λ��֣�"+score_get);
    		}else{
    			score_exchange=score_exchange+Integer.parseInt(userscore.getScore());
    			score_total=score_total-Integer.parseInt(userscore.getScore());
    			System.out.println("��һ�λ��֣�"+score_exchange);
    		}	
    	}
    	System.out.println("������ܻ���Ϊ��"+score_total);
    	str_score_sum=String.valueOf(score_total);
    	str_score_exchange=String.valueOf(score_exchange);
    	str_score_get=String.valueOf(score_get);
    }
    
	
}

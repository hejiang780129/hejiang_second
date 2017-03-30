package com.hbjr.washcheapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WxcUserOrder  implements Serializable  {
	
	
       private static final long serialVersionUID = -758459502806858414L;
       
       private String id="-1";
	
	   private String order_no;
	   
	   private int order_state=-1;//0:已完成，1：未完成
	   
	   private String order_date;
	   
	   private String order_enddate;
	   
	   private String xcd_id;
	   
       private String xcd_mc;
	   
	   private String pay;
	   
	   private String pay_vou;//代金券消费额度
	   
	   private String pay_online;
	   
	   private List discuss=new ArrayList();//评论：为针对该订单的评论列表
	   
	   private String order_service_id;
	   
	   private String order_service_name;
	   
	   private int order_evaluate=-1; //评价：0:好评,1:中评,2:差评,-1:没有评价
	   private String order_rating="0";
	   
	   
	   
	    public String getOrder_rating() {
		return order_rating;
	}

	public void setOrder_rating(String order_rating) {
		this.order_rating = order_rating;
	}

		public int getOrder_evaluate() {
		return order_evaluate;
	}

	public void setOrder_evaluate(int order_evaluate) {
		this.order_evaluate = order_evaluate;
	}

		public String getOrder_service_id() {
		return order_service_id;
	}

	public void setOrder_service_id(String order_service_id) {
		this.order_service_id = order_service_id;
	}

	public String getOrder_service_name() {
		return order_service_name;
	}

	public void setOrder_service_name(String order_service_name) {
		this.order_service_name = order_service_name;
	}

		public static List<WxcUserOrder> orderinfos = new ArrayList<WxcUserOrder>(); 
		public static List<WxcUserOrder> orderinfos_nofinish = new ArrayList<WxcUserOrder>();
		
		public static List<WxcUserOrder> orderinfos_finish = new ArrayList<WxcUserOrder>();
		public static List<WxcUserOrder> orderinfos_nocomment = new ArrayList<WxcUserOrder>();		
		public static List<WxcUserOrder> orderinfos_cur_xcd = new ArrayList<WxcUserOrder>();		
		
	    static  
	    {
	    	
//	    	orderinfos.add(new WxcUserOrder("0","001", 0, "2014-11-20", "2014-11-21", "001", "测试洗车店1", "30", "10", "20", setDiscuss(1),"1","普通洗车",1,"3"));
//	    	orderinfos.add(new WxcUserOrder("1","002", 1, "2014-11-21", "2014-11-23", "003", "测试洗车店3", "40", "5", "35", setDiscuss(0),"2","精洗",1,"4"));
//	    	orderinfos.add(new WxcUserOrder("2","003", 1, "2014-11-22", "2014-11-22", "002", "测试洗车店2", "50", "15", "35", setDiscuss(0),"1","普通洗车",0,"5"));
//	    	orderinfos.add(new WxcUserOrder("3","004", 0, "2014-11-23", "2014-11-23", "004", "测试洗车店4", "20", "20", "0",setDiscuss(1),"3","打蜡，洗车",2,"1"));
//	    	orderinfos.add(new WxcUserOrder("4","005", 1, "2014-11-13", "2014-11-13", "003", "测试洗车店3", "25", "10", "15", setDiscuss(0),"1","普通洗车",1,"3"));
//	    	orderinfos.add(new WxcUserOrder("5","006", 1, "2014-11-14", "2014-11-15", "005", "测试洗车店5", "20", "15", "5", setDiscuss(0),"2","精洗",0,"4.5"));
//	    	orderinfos.add(new WxcUserOrder("6","007", 0, "2014-11-15", "2014-11-20", "006", "测试洗车店6", "15", "5", "10", setDiscuss(2),"2","精洗",2,"0.8"));
//	    	orderinfos.add(new WxcUserOrder("7","008", 1, "2014-11-13", "2014-11-16", "008", "测试洗车店8", "25", "5", "20", setDiscuss(0),"2","精洗",2,"0.1"));
//	    	orderinfos.add(new WxcUserOrder("8","009", 1, "2014-11-23", "2014-11-25", "009", "测试洗车店9", "40", "20", "20", setDiscuss(0),"2","精洗",1,"4.2"));
//	    	orderinfos.add(new WxcUserOrder("9","010", 0, "2014-11-12", "2014-11-16", "001", "测试洗车店1", "30", "5", "25", setDiscuss(0),"2","精洗",2,"1.8"));
  		   
	    }
	   
	public static void initOrderList(){
		WxcUserOrder tmp;
		int tmp_status=-1;
		orderinfos_finish.clear();
		orderinfos_nofinish.clear();
		orderinfos_nocomment.clear();
		for (int i=0;i<orderinfos.size();i++){
		    tmp=(WxcUserOrder) orderinfos.get(i);
		    tmp_status=tmp.getOrder_state();
		    if (tmp_status==0){
		    	orderinfos_finish.add(tmp);
		    }else if (tmp_status==1){
		    	orderinfos_nofinish.add(tmp);
		    }else{
		    	orderinfos_nocomment.add(tmp);
		    }
		}
	}
	
	public static List getCurXcdOrderList(String xcd_id){
		WxcUserOrder tmp;
		orderinfos_cur_xcd.clear();
		String tmp_xcd_id;
		for (int i=0;i<orderinfos.size();i++){
		    tmp=(WxcUserOrder) orderinfos.get(i);
		    tmp_xcd_id=tmp.getXcd_id();
		    if (tmp_xcd_id.equalsIgnoreCase(xcd_id)){
		    orderinfos_cur_xcd.add(tmp);	
		    }
		}
		return orderinfos_cur_xcd;
	}
	
	public static List getCurXcdCommList(List orders){
		List whole=new ArrayList();
		WxcUserOrder tmp;
		List tmp_list;	
		for (int i=0;i<orders.size();i++){
			tmp=(WxcUserOrder) orders.get(i);
			whole.addAll(tmp.getDiscuss());
		}
		System.out.println("所有用户评论数量为："+whole.size());
		return whole;
	}
	
	public WxcUserOrder(){
		
	}
	    
	public WxcUserOrder(String id,String order_no, int order_state,
				String order_date, String order_enddate, String xcd_id,
				String xcd_mc, String pay, String pay_vou, String pay_online,
				List discuss,String order_service_id,String order_service_name,int order_evaluate,String order_rating) {
			super();
			this.id=id;
			this.order_no = order_no;
			this.order_state = order_state;
			this.order_date = order_date;
			this.order_enddate = order_enddate;
			this.xcd_id = xcd_id;
			this.xcd_mc = xcd_mc;
			this.pay = pay;
			this.pay_vou = pay_vou;
			this.pay_online = pay_online;
			this.discuss = discuss;
			this.order_service_id=order_service_id;
			this.order_service_name=order_service_name;
			this.order_evaluate=order_evaluate;
			this.order_rating=order_rating;
		}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public int getOrder_state() {
		return order_state;
	}
	
	public String getOrder_state_dsc(){		
		if (this.order_state==0){
			return "已完成";
		}else if (order_state==1){
			return "未完成";
		}else if(order_state==2){
			return "待评价";
		}else{
			return "异常";
		}
	}
	
	public String getOrder_evaluate_dsc(){		
		if (this.order_evaluate==0){
			return "好评";
		}else if (order_evaluate==1){
			return "中评";
		}else if(order_evaluate==2){
			return "差评";
		}else{
			return "";
		}
	}
	

	public void setOrder_state(int order_state) {
		this.order_state = order_state;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getOrder_enddate() {
		return order_enddate;
	}

	public void setOrder_enddate(String order_enddate) {
		this.order_enddate = order_enddate;
	}

	public String getXcd_id() {
		return xcd_id;
	}

	public void setXcd_id(String xcd_id) {
		this.xcd_id = xcd_id;
	}

	public String getXcd_mc() {
		return xcd_mc;
	}

	public void setXcd_mc(String xcd_mc) {
		this.xcd_mc = xcd_mc;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getPay_vou() {
		return pay_vou;
	}

	public void setPay_vou(String pay_vou) {
		this.pay_vou = pay_vou;
	}

	public String getPay_online() {
		return pay_online;
	}

	public void setPay_online(String pay_online) {
		this.pay_online = pay_online;
	}

	public List getDiscuss() {
		return discuss;
	}

	public void setDiscuss(List discuss) {
		this.discuss = discuss;
	}

	

    public static List setDiscuss(int i){
        //测试方法：i=0：没有任何评论，i=1:有1条评论,i=2：有3条以上评论
     	List str = new ArrayList<Map<String, Object>>();
     	if (i==0){
     		
     	}else if(i==1){
     		Map<String, Object> map = new HashMap<String, Object>();
     		map.put("name",  "车主13xxxxxxxxx");
     		map.put("comment","这个店不错，洗的很干净，服务态度非常好");
     		map.put("date","[2014-11-2]");
     		str.add(map);
     		
     	}else if(i==2){
     		Map<String, Object> map = new HashMap<String, Object>();
     		map.put("name",  "车主13xxxxxxxxx");
     		map.put("comment","这个店不错，洗的很干净，服务态度非常好");
     		map.put("date","[2014-11-2]");
     		str.add(map);
     		
     		map = new HashMap<String, Object>();
     		map.put("name",  "车主158xxxxxxxx");
     		map.put("comment","这个店的服务还可以");
     		map.put("date","[2014-11-15]");
     		str.add(map);
     		
     		map = new HashMap<String, Object>();
     		map.put("name",  "车主1365xxxxxx");
     		map.put("comment","服务的不怎么样，洗的不干净");
     		map.put("date","[2014-11-3]");
     		str.add(map);
     		
     		map = new HashMap<String, Object>();
     		map.put("name",  "车主158xxxxxxxxx");
     		map.put("comment","价格有点高");
     		map.put("date","[2014-11-8]");
     		str.add(map);
     	}
     	
     	return str;
     }
	    
     public static WxcUserOrder findById(String id,List orders){
    	 WxcUserOrder order;
    	 String tmp;
    	 for(int i=0;i<orders.size();i++){
    		 order=(WxcUserOrder) orders.get(i);
    		 if (order.getId().equalsIgnoreCase(id)){
    		     return order;	 
    		 }  	 
    	 }
   	  return null;
     }
     
     public static boolean ReplaceOrder(List orders,WxcUserOrder order){
    	 WxcUserOrder order_tmp;
    	 String tmp;
    	 System.out.println("开始进行order替换");
    	 for(int i=0;i<orders.size();i++){
    		 order_tmp=(WxcUserOrder) orders.get(i);
    		 if (order.getId().equalsIgnoreCase(order_tmp.getId())){
    	    	 System.out.println("对orders中的"+order_tmp.getId()+" 进行了替换");
    		     orders.set(i, order);
    		     return true;
    		 }  	 
    	 }
   	  return false;
     }

}

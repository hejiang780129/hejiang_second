package com.hbjr.washcheapp.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.bean.WxcMessage;
import com.hbjr.washcheapp.bean.WxcScore;
import com.hbjr.washcheapp.bean.WxcUserAccount;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.bean.WxcUserScore;
import com.hbjr.washcheapp.bean.WxcUserVou;
import com.hbjr.washcheapp.bean.WxcVouExercise;
import com.hbjr.washcheapp.config.ConstConnectCode;

public class JSONTools {  
	  
    public JSONTools() {  
        // TODO Auto-generated constructor stub  
    }  
      

    /**
     * @param key
     * @param jsonString
     * @param handler
     * @param return_map:返回的参数
     * 
     * up:
     *   HashMap hashmp_up=new HashMap();
     *   hashmp_up.put("uname","")、pwd,rpwd,code(验证码))
     * return:
     * 
     * 
     */
    public static void register(String key, String jsonString,Handler handler){
		Message msg = new Message();
		HashMap  hmp_return=new HashMap();
		String rtmsg,rcode,version,msgnum;
		WxcUserAccount account=new WxcUserAccount();
        try {
			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	
            JSONObject jsonObject_info = new JSONObject(jsonString);
            String code=(String) jsonObject_info.get("code");
		    JSONObject jsonObject_sub_info = (JSONObject) jsonObject_info.getJSONObject(key);            
            //0表示成功
           if (code!=null&&code.equalsIgnoreCase("0")){
	            //返回json的数组              
					rtmsg=jsonObject_sub_info.getString("rtmsg");
					rcode=jsonObject_sub_info.getString("rcode");
					//如果注册成功，则返回所有信息
					if (rcode.equalsIgnoreCase("0")){
						version=jsonObject_sub_info.getString("version");
						msgnum=jsonObject_sub_info.getString("msgnum");
					    JSONObject jsonObject_account = (JSONObject) jsonObject_sub_info.getJSONObject("account");				    
					    account.setOrder_num(jsonObject_account.getString("order_num"));
					    account.setOrder_sum(jsonObject_account.getString("order_sum"));
					    account.setOrder_Pay_online(jsonObject_account.getString("order_Pay_online"));
					    account.setScore_exchange(jsonObject_account.getString("score_exchange"));
					    account.setScore_get(jsonObject_account.getString("score_get"));
					    account.setScore_sum(jsonObject_account.getString("score_sum"));
					    account.setVou_balance(jsonObject_account.getString("vou_balance"));
					    account.setVou_consume(jsonObject_account.getString("vou_consume"));
					    account.setVou_total(jsonObject_account.getString("vou_total"));				
					    
	                    hmp_return.put("rtmsg", rtmsg);			    
					    hmp_return.put("rcode", rcode);
					    hmp_return.put("version", version);
					    hmp_return.put("msgnum", msgnum);
					    hmp_return.put("account", account);				
						System.out.println("解析成功****");					    
		           }else{
	                    hmp_return.put("rtmsg", rtmsg);			    
					    hmp_return.put("rcode", rcode);
		           }
				    				    				    
				if (handler!=null){
					msg.what = 0;
					msg.obj=hmp_return;
					handler.sendMessage(msg);
				}	            
               }else{
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
					handler.sendMessage(msg);
				}
           }
		  } catch (Exception e) {  
		            // TODO: handle exception  
			  System.out.println(e);
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
					handler.sendMessage(msg);
				}			  
		    }                      	    	    	  
    	
    }
    
    
    
    /**
     * @param key
     * @param jsonString
     * @param handler
     * @param return_map:返回的参数
     * 
     * up:
     *   HashMap hashmp_up=new HashMap();
     *   hashmp_up.put("uname","")、pwd,rpwd,code(验证码))
     * return:
     * 
     * 
     */
    public static void login(String key, String jsonString,Handler handler,HashMap hmp){
		Message msg = new Message();
		HashMap  hmp_return=hmp;
		String rtmsg,rcode,version,msgnum;
		WxcUserAccount account=new WxcUserAccount();
        try {
			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	
            JSONObject jsonObject_info = new JSONObject(jsonString);
            String code=(String) jsonObject_info.get("code");
		    JSONObject jsonObject_sub_info = (JSONObject) jsonObject_info.getJSONObject(key);            
            //0表示成功
           if (code!=null&&code.equalsIgnoreCase("0")){
	            //返回json的数组              
					rtmsg=jsonObject_sub_info.getString("rtmsg");
					rcode=jsonObject_sub_info.getString("rcode");
					//如果注册成功，则返回所有信息
					if (rcode.equalsIgnoreCase("0")){
						version=jsonObject_sub_info.getString("version");
						msgnum=jsonObject_sub_info.getString("msgnum");
					    JSONObject jsonObject_account = (JSONObject) jsonObject_sub_info.getJSONObject("account");				    
					    account.setOrder_num(jsonObject_account.getString("order_num"));
					    account.setOrder_sum(jsonObject_account.getString("order_sum"));
					    account.setOrder_Pay_online(jsonObject_account.getString("order_Pay_online"));
					    account.setScore_exchange(jsonObject_account.getString("score_exchange"));
					    account.setScore_get(jsonObject_account.getString("score_get"));
					    account.setScore_sum(jsonObject_account.getString("score_sum"));
					    account.setVou_balance(jsonObject_account.getString("vou_balance"));
					    account.setVou_consume(jsonObject_account.getString("vou_consume"));
					    account.setVou_total(jsonObject_account.getString("vou_total"));				
					    hmp_return.put("code", code);
	                    hmp_return.put("rtmsg", rtmsg);			    
					    hmp_return.put("rcode", rcode);
					    hmp_return.put("version", version);
					    hmp_return.put("msgnum", msgnum);
					    hmp_return.put("account", account);
					    System.out.println("返回的参数："+hmp_return.get("rcode")
								  +"***"+hmp_return.get("rtmsg")
							      +"***"+hmp_return.get("version")
							      +"***"+hmp_return.get("msgnum")							      
								  +"***"+hmp_return.get("account")); 
					    
					    
						System.out.println("解析成功****");					    
		           }else{
					    hmp_return.put("code", code);		        	   
	                    hmp_return.put("rtmsg", rtmsg);			    
					    hmp_return.put("rcode", rcode);
		           }
				    				    				    
				if (handler!=null){
					msg.what = 0;
					msg.obj=hmp_return;					
					handler.sendMessage(msg);
				}	            
               }else{
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
					handler.sendMessage(msg);
				}
           }
		  } catch (Exception e) {  
		            // TODO: handle exception  
			  System.out.println(e);
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
					handler.sendMessage(msg);
				}			  
		    }                      	    	    	  
    	
    }    
    
        
    
    public static WxcBusInfo getWxcBusInfo(String key, String jsonString,Handler handler){  
		Message msg = new Message();
        WxcBusInfo info = new WxcBusInfo();		
        try {  
			System.out.println("开始解析调用结果，key:*******"+key+" *****************");
            JSONObject jsonObject_info = new JSONObject(jsonString);
            String code=(String) jsonObject_info.get("code");            
            //0表示成功
           if (code!=null&&code.equalsIgnoreCase("0")){
	            //返回json的数组              
				    JSONObject jsonObject_sub_info = (JSONObject) jsonObject_info.getJSONObject(key);        	       	            	    	                
	                info.setAddress(jsonObject_sub_info.getString("address"));
	                info.setBad_num(jsonObject_sub_info.getInt("bad_num"));
	                info.setBh(jsonObject_sub_info.getString("bh"));
	                info.setBuynum(jsonObject_sub_info.getString("buynum"));
	                info.setContactinfo(jsonObject_sub_info.getString("contactinfo"));
	                info.setCutdown(jsonObject_sub_info.getString("cutdown"));
	                info.setDistance(jsonObject_sub_info.getString("distance"));
	                info.setGood_num(jsonObject_sub_info.getInt("good_num"));
	                info.setImgId(jsonObject_sub_info.getInt("imgId"));
	                info.setImgUrl(jsonObject_sub_info.getString("imgUrl"));
	                info.setIntroduce(jsonObject_sub_info.getString("introduce"));
	                info.setLatitude(Double.valueOf(jsonObject_sub_info.get("latitude").toString()));
	                info.setLongitude(Double.valueOf(jsonObject_sub_info.get("longitude").toString()));
	                info.setMid_num(jsonObject_sub_info.getInt("mid_num"));
	                info.setName(jsonObject_sub_info.getString("name"));
	                info.setRating(Float.valueOf(jsonObject_sub_info.get("rating").toString()));
	                info.setXcd_id(jsonObject_sub_info.getString("xcd_id"));
	                info.setComm_num(jsonObject_sub_info.getString("comm_num"));
	                
	                //lst_comment
	                JSONArray jsonArray_comment = jsonObject_sub_info.getJSONArray("lst_order_comment");
	                List comment_list=new ArrayList();
	                for(int j = 0; j < jsonArray_comment.length(); j++){  
	                    JSONObject jsonObject_comment = jsonArray_comment.getJSONObject(j);  
	                    Map<String, Object> map = new HashMap<String, Object>();  
	                    // 通过org.json中的迭代器来取Map中的值。  
	                    Iterator<String> iterator = jsonObject_comment.keys();  
	                    while(iterator.hasNext()) {  
	                        String jsonKey = iterator.next();  
	                        Object jsonValue = jsonObject_comment.get(jsonKey);  
	                        //JSON的值是可以为空的，所以我们也需要对JSON的空值可能性进行判断。  
	                        if(jsonValue == null){  
	                            jsonValue = "";  
	                        }  
	                        map.put(jsonKey, jsonValue);  
	                    }//while
	                    comment_list.add(map);	                      
	                }//for  
	                
	                info.setLst_order_comment(comment_list);
	                	                	                	              
	                //prices	                	                
	                JSONArray jsonArray_prices = jsonObject_sub_info.getJSONArray("prices");
	                List prices_list=new ArrayList();
	                for(int j = 0; j < jsonArray_prices.length(); j++){  
	                    JSONObject jsonObject_price = jsonArray_prices.getJSONObject(j);  
	                    Map<String, Object> map = new HashMap<String, Object>();  
	                    // 通过org.json中的迭代器来取Map中的值。  
	                    Iterator<String> iterator = jsonObject_price.keys();  
	                    while(iterator.hasNext()) {  
	                        String jsonKey = iterator.next();  
	                        Object jsonValue = jsonObject_price.get(jsonKey);  
	                        //JSON的值是可以为空的，所以我们也需要对JSON的空值可能性进行判断。  
	                        if(jsonValue == null){  
	                            jsonValue = "";  
	                        }  
	                        map.put(jsonKey, jsonValue);  
	                    }//while
	                    prices_list.add(map);	                      
	                }//for  
	                	                	                
	                info.setPrices(prices_list) ;	                
					System.out.println("解析成功****");	                	                             
				if (handler!=null){
					msg.what = 0;
					msg.obj=info;
					handler.sendMessage(msg);
				}	            
               }else{
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
					handler.sendMessage(msg);
				}
           }
		  } catch (Exception e) {  
		            // TODO: handle exception  
			  System.out.println(e);
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
					handler.sendMessage(msg);
				}			  
		    }          
        
            return info;        
    }  
    
                        
    
    public static List<WxcBusInfo> getWxcBusInfoList(String key, String jsonString,Handler handler){  
		Message msg = new Message();
        List<WxcBusInfo> list = new ArrayList<WxcBusInfo>();  
        try {
			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	
            JSONObject jsonObject_info = new JSONObject(jsonString);            
            String code=(String) jsonObject_info.get("code");            
            //0表示成功
           if (code!=null&&code.equalsIgnoreCase("0")){        	   
	            //返回json的数组              
	            JSONArray jsonArray_info = jsonObject_info.getJSONArray(key);  
	            System.out.println("取得的array是："+jsonArray_info.length());
	            for(int i = 0; i < jsonArray_info.length(); i++){  
		            System.out.println("第"+(i+1)+"条");	            	
	                JSONObject jsonObject_subinfo = jsonArray_info.getJSONObject(i);  
	                WxcBusInfo info = new WxcBusInfo();  	                
	                info.setAddress(jsonObject_subinfo.getString("address"));
	                //info.setBad_num(jsonObject_subinfo.getInt("bad_num"));
	                //info.setBh(jsonObject_subinfo.getString("bh"));
	                info.setBuynum(jsonObject_subinfo.getString("buynum"));
	                //info.setContactinfo(jsonObject_subinfo.getString("contactinfo"));
	                info.setCutdown(jsonObject_subinfo.getString("cutdown"));
	                info.setDistance(jsonObject_subinfo.getString("distance"));
	                //info.setGood_num(jsonObject_subinfo.getInt("good_num"));
	                info.setImgId(jsonObject_subinfo.getInt("imgId"));
	                info.setImgUrl(jsonObject_subinfo.getString("imgUrl"));
	                //info.setIntroduce(jsonObject_subinfo.getString("introduce"));
	                info.setLatitude(Double.valueOf(jsonObject_subinfo.get("latitude").toString()));
	                info.setLongitude(Double.valueOf(jsonObject_subinfo.get("longitude").toString()));
	                //info.setMid_num(jsonObject_subinfo.getInt("mid_num"));
	                info.setName(jsonObject_subinfo.getString("name"));
	                info.setRating(Float.valueOf(jsonObject_subinfo.get("rating").toString()));
	                info.setXcd_id(jsonObject_subinfo.getString("xcd_id"));	                
	                
	                //lst_comment
//	                JSONArray jsonArray_comment = jsonObject_subinfo.getJSONArray("lst_comment");
//	                List comment_list=new ArrayList();
//	                for(int j = 0; j < jsonArray_comment.length(); j++){  
//	                    JSONObject jsonObject_comment = jsonArray_comment.getJSONObject(j);  
//	                    Map<String, Object> map = new HashMap<String, Object>();  
//	                    // 通过org.json中的迭代器来取Map中的值。  
//	                    Iterator<String> iterator = jsonObject_comment.keys();  
//	                    while(iterator.hasNext()) {  
//	                        String jsonKey = iterator.next();  
//	                        Object jsonValue = jsonObject_comment.get(jsonKey);  
//	                        //JSON的值是可以为空的，所以我们也需要对JSON的空值可能性进行判断。  
//	                        if(jsonValue == null){  
//	                            jsonValue = "";  
//	                        }  
//	                        map.put(jsonKey, jsonValue);  
//	                    }//while
//	                    comment_list.add(map);	                      
//	                }//for  
//	                
//	                info.setLst_comment(comment_list);
	                	                	                	              
	                //prices	                	                
	                JSONArray jsonArray_prices = jsonObject_subinfo.getJSONArray("prices");
	                List prices_list=new ArrayList();
	                for(int j = 0; j < jsonArray_prices.length(); j++){  
	                    JSONObject jsonObject_price = jsonArray_prices.getJSONObject(j);  
	                    Map<String, Object> map = new HashMap<String, Object>();  
	                    // 通过org.json中的迭代器来取Map中的值。  
	                    Iterator<String> iterator = jsonObject_price.keys();  
	                    while(iterator.hasNext()) {  
	                        String jsonKey = iterator.next();  
	                        Object jsonValue = jsonObject_price.get(jsonKey);  
	                        //JSON的值是可以为空的，所以我们也需要对JSON的空值可能性进行判断。  
	                        if(jsonValue == null){  
	                            jsonValue = "";  
	                        }  
	                        map.put(jsonKey, jsonValue);  
	                    }//while
	                    prices_list.add(map);	                      
	                }//for  
	                	                	                
	                info.setPrices(prices_list) ;	                	                	                
	                list.add(info);  
					System.out.println("解析成功****");	                
	            }	            
				if (handler!=null){
					msg.what = 0;
					msg.obj=list;
					handler.sendMessage(msg);
				}	            
           }else{
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
					handler.sendMessage(msg);
				}
           }
		  } catch (Exception e) {  
		            // TODO: handle exception  
			  System.out.println(e);
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
					handler.sendMessage(msg);
				}			  
		    }          
        
            System.out.println("返回的list长度是："+list.size());
            return list;        
    }  
    
    
    
    
    public static List getWxcBusInfoCommList(String key, String jsonString,Handler handler){  
		Message msg = new Message();
        List<HashMap> list = new ArrayList<HashMap>();  
        HashMap  hmp_comm;
        try {  
			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	
            JSONObject jsonObject_info = new JSONObject(jsonString);
            String code=(String) jsonObject_info.get("code");           
            //0表示成功
           if (code!=null&&code.equalsIgnoreCase("0")){
	            //返回json的数组              
	            JSONArray jsonArray_comment = jsonObject_info.getJSONArray(key);  
	            System.out.println("取得的array是："+jsonArray_comment.length());
	            for(int i = 0; i < jsonArray_comment.length(); i++){  
		            System.out.println("第"+(i+1)+"条");	            	
	                JSONObject jsonObject_subinfo = jsonArray_comment.getJSONObject(i);  
	                hmp_comm=new HashMap();
	                hmp_comm.put("date", jsonObject_subinfo.getString("date"));
	                hmp_comm.put("name", jsonObject_subinfo.getString("name"));
	                hmp_comm.put("comment", jsonObject_subinfo.getString("comment"));	                	                	                
	                list.add(hmp_comm);  
					System.out.println("解析成功****");	                
	            }	            
				if (handler!=null){
					msg.what = 0;
					msg.obj=list;
					handler.sendMessage(msg);
				}	            
           }else{
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
					handler.sendMessage(msg);
				}
           }
		  } catch (Exception e) {  
		            // TODO: handle exception  
			  System.out.println(e);
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
					handler.sendMessage(msg);
				}			  
		    }          
        
            System.out.println("返回的list长度是："+list.size());
            return list;        
    }  
    
    
    public static List<WxcUserOrder> getUserOrderList(String key, String jsonString,Handler handler){  
		Message msg = new Message();
        List<WxcUserOrder> list = new ArrayList<WxcUserOrder>();  
        try {  
			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	
            JSONObject jsonObject_info = new JSONObject(jsonString);
            String code=(String) jsonObject_info.get("code");           
            //0表示成功
           if (code!=null&&code.equalsIgnoreCase("0")){
	            //返回json的数组              
	            JSONArray jsonArray_order = jsonObject_info.getJSONArray(key);  
	            System.out.println("取得的array是："+jsonArray_order.length());
	            for(int i = 0; i < jsonArray_order.length(); i++){  
		            System.out.println("第"+(i+1)+"条");	            	
	                JSONObject jsonObject_subinfo = jsonArray_order.getJSONObject(i);  
	                WxcUserOrder order = new WxcUserOrder();  	             
	                
	                order.setId(jsonObject_subinfo.getString("id"));
	                order.setOrder_date(jsonObject_subinfo.getString("order_date"));	                
	                order.setOrder_enddate(jsonObject_subinfo.getString("order_enddate"));
	                order.setOrder_evaluate(jsonObject_subinfo.getInt("order_evaluate"));
	                order.setOrder_no(jsonObject_subinfo.getString("order_no"));
	                order.setOrder_rating(jsonObject_subinfo.getString("order_rating"));
	                order.setOrder_service_id(jsonObject_subinfo.getString("order_service_id"));
	                order.setOrder_service_name(jsonObject_subinfo.getString("order_service_name"));
	                order.setOrder_state(jsonObject_subinfo.getInt("order_state"));
	                order.setPay(jsonObject_subinfo.getString("pay"));	                
	                order.setPay_online(jsonObject_subinfo.getString("pay_online"));
	                order.setPay_vou(jsonObject_subinfo.getString("pay_vou"));
	                order.setXcd_id(jsonObject_subinfo.getString("xcd_id"));
	                order.setXcd_mc(jsonObject_subinfo.getString("xcd_mc"));	             
	                	                	                	                	                	               	                	                	                 	                
	                //lst_comment
	                JSONArray jsonArray_comment = jsonObject_subinfo.getJSONArray("discuss");
	                List comment_list=new ArrayList();
	                for(int j = 0; j < jsonArray_comment.length(); j++){  
	                    JSONObject jsonObject_comment = jsonArray_comment.getJSONObject(j);  
	                    Map<String, Object> map = new HashMap<String, Object>();  
	                    // 通过org.json中的迭代器来取Map中的值。  
	                    Iterator<String> iterator = jsonObject_comment.keys();  
	                    while(iterator.hasNext()) {  
	                        String jsonKey = iterator.next();  
	                        Object jsonValue = jsonObject_comment.get(jsonKey);  
	                        //JSON的值是可以为空的，所以我们也需要对JSON的空值可能性进行判断。  
	                        if(jsonValue == null){  
	                            jsonValue = "";  
	                        }  
	                        map.put(jsonKey, jsonValue);  
	                    }//while
	                    comment_list.add(map);	                      
	                }//for  
	                
	                order.setDiscuss(comment_list);	                	                
	                
	                list.add(order);  
					System.out.println("解析成功****");	                
	            }	            
				if (handler!=null){
					msg.what = 0;
					msg.obj=list;
					handler.sendMessage(msg);
				}	            
           }else{
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
					handler.sendMessage(msg);
				}
           }
		  } catch (Exception e) {  
		            // TODO: handle exception  
			  System.out.println(e);
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
					handler.sendMessage(msg);
				}			  
		    }          
        
            System.out.println("返回的list长度是："+list.size());
            return list;        
    }  
    
    public static List<WxcUserVou> getUserVouList(String key, String jsonString,Handler handler){  
		Message msg = new Message();
        List<WxcUserVou> list = new ArrayList<WxcUserVou>();  
        try {  
			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	
            JSONObject jsonObject_info = new JSONObject(jsonString);
            String code=(String) jsonObject_info.get("code");            
            //0表示成功
           if (code!=null&&code.equalsIgnoreCase("0")){
	            //返回json的数组              
	            JSONArray jsonArray_order = jsonObject_info.getJSONArray(key);  
	            System.out.println("取得的array是："+jsonArray_order.length());
	            for(int i = 0; i < jsonArray_order.length(); i++){  
		            System.out.println("第"+(i+1)+"条");	            	
	                JSONObject jsonObject_subinfo = jsonArray_order.getJSONObject(i);  
	                WxcUserVou vou = new WxcUserVou();  	             
	                
	                vou.setVou_id(jsonObject_subinfo.getString("vou_id"));
	                vou.setTotal_num(jsonObject_subinfo.getString("total_num"));
	                vou.setLimit_datetimes(jsonObject_subinfo.getString("limit_datetimes"));
	                vou.setLimit_date(jsonObject_subinfo.getString("limit_date"));
	                vou.setGet_type(jsonObject_subinfo.getString("get_type"));
	                vou.setGet_type_dsc(jsonObject_subinfo.getString("get_type_dsc"));
	                vou.setGet_date(jsonObject_subinfo.getString("get_date"));
	                vou.setCur_status(jsonObject_subinfo.getInt("cur_status"));
	                vou.setCur_status_desc(jsonObject_subinfo.getString("cur_status_desc"));	                	                	                	                	                	                	                	               	                	                	                 	                 	               	                	               	                
	                list.add(vou);  	                
	            }	            
				System.out.println("解析成功****");	            
				if (handler!=null){
					msg.what = 0;
					msg.obj=list;
					handler.sendMessage(msg);
				}	            
           }else{
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
					handler.sendMessage(msg);
				}
           }
		  } catch (Exception e) {  
		            // TODO: handle exception  
			  System.out.println(e);
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
					handler.sendMessage(msg);
				}			  
		    }          
        
            System.out.println("返回的list长度是："+list.size());
            return list;        
    }  
    
    
    
    public static List<WxcVouExercise> getVouList(String key, String jsonString,Handler handler){  
		Message msg = new Message();
        List<WxcVouExercise> list = new ArrayList<WxcVouExercise>();  
        try {  
			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	
            JSONObject jsonObject_info = new JSONObject(jsonString);
            String code=(String) jsonObject_info.get("code");           
            //0表示成功
           if (code!=null&&code.equalsIgnoreCase("0")){
	            //返回json的数组              
	            JSONArray jsonArray_order = jsonObject_info.getJSONArray(key);  
	            System.out.println("取得的array是："+jsonArray_order.length());
	            for(int i = 0; i < jsonArray_order.length(); i++){  
		            System.out.println("第"+(i+1)+"条");	            	
	                JSONObject jsonObject_subinfo = jsonArray_order.getJSONObject(i);  
	                WxcVouExercise vou = new WxcVouExercise();  	 	                
	                vou.setContent(jsonObject_subinfo.getString("content"));
	                vou.setEdate(jsonObject_subinfo.getString("edate"));
	                vou.setEdate_vou(jsonObject_subinfo.getString("edate_vou"));
	                vou.setId(jsonObject_subinfo.getString("id"));
	                vou.setImg_url(jsonObject_subinfo.getString("img_url"));	                
	                vou.setType(jsonObject_subinfo.getString("type"));
	                vou.setType_sub_id(jsonObject_subinfo.getString("type_sub_id"));
	                vou.setVou_num(jsonObject_subinfo.getString("vou_num"));	
	                vou.setName(jsonObject_subinfo.getString("name"));
	                list.add(vou);  
	            }	            
				System.out.println("解析成功****");	            
				if (handler!=null){
					msg.what = 0;
					msg.obj=list;
					handler.sendMessage(msg);
				}	            
           }else{
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
					handler.sendMessage(msg);
				}
           }
		  } catch (Exception e) {  
		            // TODO: handle exception  
			  System.out.println(e);
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
					handler.sendMessage(msg);
				}			  
		    }          
        
            System.out.println("返回的list长度是："+list.size());
            return list;        
    }  

    
    public static List<WxcUserScore> getUserScoreList(String key, String jsonString,Handler handler){  
		Message msg = new Message();
        List<WxcUserScore> list = new ArrayList<WxcUserScore>();  
        try {  
			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	
            JSONObject jsonObject_info = new JSONObject(jsonString);
            String code=(String) jsonObject_info.get("code");           
            //0表示成功
           if (code!=null&&code.equalsIgnoreCase("0")){
	            //返回json的数组              
	            JSONArray jsonArray_order = jsonObject_info.getJSONArray(key);  
	            System.out.println("取得的array是："+jsonArray_order.length());
	            for(int i = 0; i < jsonArray_order.length(); i++){  
		            System.out.println("第"+(i+1)+"条");	            	
	                JSONObject jsonObject_subinfo = jsonArray_order.getJSONObject(i);  
	                WxcUserScore score = new WxcUserScore();  	             	                
	                score.setGet_date(jsonObject_subinfo.getString("get_date"));
	                score.setGet_exercise_id(jsonObject_subinfo.getString("get_exercise_id"));	                
	                score.setGet_type(jsonObject_subinfo.getString("get_type"));	                
	                score.setGet_type_dsc(jsonObject_subinfo.getString("get_type_dsc"));	                
	                score.setScore(jsonObject_subinfo.getString("score"));	                	                	                	                	                	                	                	                	               	                	                	                 	                 	               	                	               	                
	                list.add(score);  
	            }	            
				System.out.println("解析成功****");	            
				if (handler!=null){
					msg.what = 0;
					msg.obj=list;
					handler.sendMessage(msg);
				}	            
           }else{
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
					handler.sendMessage(msg);
				}
           }
		  } catch (Exception e) {  
		            // TODO: handle exception  
			  System.out.println(e);
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
					handler.sendMessage(msg);
				}			  
		    }          
        
            System.out.println("返回的list长度是："+list.size());
            return list;        
    }
    
    
    
    
    public static List<WxcScore> getScoreList(String key, String jsonString,Handler handler){  
		Message msg = new Message();
        List<WxcScore> list = new ArrayList<WxcScore>();  
        try {  
			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	
            JSONObject jsonObject_info = new JSONObject(jsonString);
            String code=(String) jsonObject_info.get("code");            
            //0表示成功
           if (code!=null&&code.equalsIgnoreCase("0")){
	            //返回json的数组              
	            JSONArray jsonArray_order = jsonObject_info.getJSONArray(key);  
	            System.out.println("取得的array是："+jsonArray_order.length());
	            for(int i = 0; i < jsonArray_order.length(); i++){  
		            System.out.println("第"+(i+1)+"条");	            	
	                JSONObject jsonObject_subinfo = jsonArray_order.getJSONObject(i);  
	                WxcScore vou = new WxcScore();  	 	                
	                vou.setContent(jsonObject_subinfo.getString("content"));
	                vou.setEdate(jsonObject_subinfo.getString("edate"));
	                vou.setExchange_rule(jsonObject_subinfo.getString("exchange_rule"));
	                vou.setId(jsonObject_subinfo.getString("id"));
	                vou.setName(jsonObject_subinfo.getString("name"));
	                vou.setScore(jsonObject_subinfo.getString("score"));
	                vou.setScore_desc(jsonObject_subinfo.getString("score_desc"));
	                vou.setImg_url(jsonObject_subinfo.getString("img_url"));
	                vou.setType(jsonObject_subinfo.getString("type"));
	                vou.setType_sub_id(jsonObject_subinfo.getString("type_sub_id"));
	                
	                list.add(vou);  
	            }	            
				System.out.println("解析成功****");	            
				if (handler!=null){
					msg.what = 0;
					msg.obj=list;
					handler.sendMessage(msg);
				}	            
           }else{
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
					handler.sendMessage(msg);
				}
           }
		  } catch (Exception e) {  
		            // TODO: handle exception  
			  System.out.println(e);
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
					handler.sendMessage(msg);
				}			  
		    }          
        
            System.out.println("返回的list长度是："+list.size());
            return list;        
    }
    
    
    
    public static List<WxcMessage> getMessageList(String key, String jsonString,Handler handler){  
  		Message msg = new Message();
          List<WxcMessage> list = new ArrayList<WxcMessage>();  
          try {  
  			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	  
              JSONObject jsonObject_info = new JSONObject(jsonString);
              String code=(String) jsonObject_info.get("code");            
              //0表示成功
             if (code!=null&&code.equalsIgnoreCase("0")){
  	            //返回json的数组              
  	            JSONArray jsonArray_order = jsonObject_info.getJSONArray(key);  
  	            System.out.println("取得的array是："+jsonArray_order.length());
  	            for(int i = 0; i < jsonArray_order.length(); i++){  
  		            System.out.println("第"+(i+1)+"条");	            	
  	                JSONObject jsonObject_subinfo = jsonArray_order.getJSONObject(i);  
  	               WxcMessage message = new WxcMessage();  	 	                
  	               message.setMessage_id(jsonObject_subinfo.getString("message_id"));
  	               message.setMessage_type(jsonObject_subinfo.getString("message_type"));
  	               message.setMessage__title(jsonObject_subinfo.getString("message_title"));
  	               message.setMessage_content(jsonObject_subinfo.getString("message_content"));  	               
  	               message.setMessage_date(jsonObject_subinfo.getString("message_date"));  	                   	                
  	               list.add(message);
  	               
  	            }	            
				System.out.println("解析成功****");  	            
  				if (handler!=null){
  					msg.what = 0;
  					msg.obj=list;
  					handler.sendMessage(msg);
  				}	            
             }else{
  				if (handler!=null){
  					msg.what = 1;
  					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
  					handler.sendMessage(msg);
  				}
             }
  		  } catch (Exception e) {  
  		            // TODO: handle exception  
  			  System.out.println(e);
  				if (handler!=null){
  					msg.what = 1;
  					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
  					handler.sendMessage(msg);
  				}			  
  		    }          
          
              System.out.println("返回的list长度是："+list.size());
              return list;        
      }
    
    
    
    
    public static void getSysMessage(String key, String jsonString,Handler handler,HashMap hmp){
		Message msg = new Message();
		HashMap  hmp_return=hmp;
		String version,msgnum;
        try {
			System.out.println("  getSysMessage  开始解析调用结果，key:*******"+key+" *****************");        	
            JSONObject jsonObject_info = new JSONObject(jsonString);
            String code=(String) jsonObject_info.get("code");
		    JSONObject jsonObject_sub_info = (JSONObject) jsonObject_info.getJSONObject(key);            
            //0表示成功
           if (code!=null&&code.equalsIgnoreCase("0")){
	            //返回json的数组              
						version=jsonObject_sub_info.getString("version");
						msgnum=jsonObject_sub_info.getString("msgnum");								    
					    hmp_return.put("version", version);
					    hmp_return.put("msgnum", msgnum);		
						System.out.println("解析成功****");					    
				if (handler!=null){
					msg.what = 0;
					msg.obj=hmp_return;
					handler.sendMessage(msg);
				}	            
               }else{
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
					handler.sendMessage(msg);
				}
           }
		  } catch (Exception e) {  
		            // TODO: handle exception  
			  System.out.println(e);
				if (handler!=null){
					msg.what = 1;
					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
					handler.sendMessage(msg);
				}			  
		    }                      	    	    	  
    	
    }    

    //===============================================================================================
    
    
    public static void getPutInfoComment(String key, String jsonString,Handler handler){
 		Message msg = new Message();
 		HashMap  hmp_return=new HashMap();
 		String rcode,rtmsg;
         try {
 			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	 
             JSONObject jsonObject_info = new JSONObject(jsonString);
             String code=(String) jsonObject_info.get("code");
 		    JSONObject jsonObject_sub_info = (JSONObject) jsonObject_info.getJSONObject(key);            
             //0表示成功
            if (code!=null&&code.equalsIgnoreCase("0")){
 	            //返回json的数组              
            	        rtmsg=jsonObject_sub_info.getString("rtmsg");
            	        rcode=jsonObject_sub_info.getString("rcode");	 											   
	                    hmp_return.put("rtmsg", rtmsg);			    
					    hmp_return.put("rcode", rcode);
						System.out.println("解析成功****"); 					    
 				if (handler!=null){
 					msg.what = 0;
 					msg.obj=hmp_return;
 					handler.sendMessage(msg);
 				}	            
                }else{
 				if (handler!=null){
 					msg.what = 1;
 					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
 					handler.sendMessage(msg);
 				}
            }
 		  } catch (Exception e) {  
 		            // TODO: handle exception  
 			  System.out.println(e);
 				if (handler!=null){
 					msg.what = 1;
 					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
 					handler.sendMessage(msg);
 				}			  
 		    }                      	    	    	  
     	
     }    
    
    
    public static void getPutOrder(String key, String jsonString,Handler handler){
 		Message msg = new Message();
 		HashMap  hmp_return=new HashMap();
 		String rcode,rtmsg,ordid,ordate;
         try {  
 			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	 
             JSONObject jsonObject_info = new JSONObject(jsonString);
             String code=(String) jsonObject_info.get("code");
 		    JSONObject jsonObject_sub_info = (JSONObject) jsonObject_info.getJSONObject(key);            
             //0表示成功
            if (code!=null&&code.equalsIgnoreCase("0")){
 	            //返回json的数组              
            	        rtmsg=jsonObject_sub_info.getString("rtmsg");
            	        rcode=jsonObject_sub_info.getString("rcode");	 			
            	        ordid=jsonObject_sub_info.getString("ordid");
            	        ordate=jsonObject_sub_info.getString("ordate");            	        
	                    hmp_return.put("rtmsg", rtmsg);			    
					    hmp_return.put("rcode", rcode);
					    hmp_return.put("ordid", ordid);					    
					    hmp_return.put("ordate", ordate);					    
						System.out.println("解析成功****"); 					    
 				if (handler!=null){
 					msg.what = 0;
 					msg.obj=hmp_return;
 					handler.sendMessage(msg);
 				}	            
                }else{
 				if (handler!=null){
 					msg.what = 1;
 					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
 					handler.sendMessage(msg);
 				}
            }
 		  } catch (Exception e) {  
 		            // TODO: handle exception  
 			  System.out.println(e);
 				if (handler!=null){
 					msg.what = 1;
 					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
 					handler.sendMessage(msg);
 				}			  
 		    }                      	    	    	  
     	
     }    
    

    public static void getPutOrderEva(String key, String jsonString,Handler handler){
 		Message msg = new Message();
 		HashMap  hmp_return=new HashMap();
 		String rcode,rtmsg,evadate,rat;
         try {
 			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	 
             JSONObject jsonObject_info = new JSONObject(jsonString);
             String code=(String) jsonObject_info.get("code");
 		    JSONObject jsonObject_sub_info = (JSONObject) jsonObject_info.getJSONObject(key);            
             //0表示成功
            if (code!=null&&code.equalsIgnoreCase("0")){
 	            //返回json的数组              
            	        rtmsg=jsonObject_sub_info.getString("rtmsg");
            	        rcode=jsonObject_sub_info.getString("rcode");	 			
            	        evadate=jsonObject_sub_info.getString("evadate");
            	        rat=jsonObject_sub_info.getString("rat");            	        
	                    hmp_return.put("rtmsg", rtmsg);			    
					    hmp_return.put("rcode", rcode);
					    hmp_return.put("evadate", evadate);					    
					    hmp_return.put("rat", rat);										    					    	             					    					    
						System.out.println("解析成功****"); 					    
 				if (handler!=null){
 					msg.what = 0;
 					msg.obj=hmp_return;
 					handler.sendMessage(msg);
 				}	            
                }else{
 				if (handler!=null){
 					msg.what = 1;
 					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
 					handler.sendMessage(msg);
 				}
            }
 		  } catch (Exception e) {  
 		            // TODO: handle exception  
 			  System.out.println(e);
 				if (handler!=null){
 					msg.what = 1;
 					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
 					handler.sendMessage(msg);
 				}			  
 		    }        
     	
     }    
    
            
    public static void getPutShareXcd(String key, String jsonString,Handler handler){
 		Message msg = new Message();
 		HashMap  hmp_return=new HashMap();
 		String rcode,rtmsg;
         try {
 			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	 
             JSONObject jsonObject_info = new JSONObject(jsonString);
             String code=(String) jsonObject_info.get("code");
 		    JSONObject jsonObject_sub_info = (JSONObject) jsonObject_info.getJSONObject(key);            
             //0表示成功
            if (code!=null&&code.equalsIgnoreCase("0")){
 	            //返回json的数组              
            	        rtmsg=jsonObject_sub_info.getString("rtmsg");
            	        rcode=jsonObject_sub_info.getString("rcode");	 			    	        
	                    hmp_return.put("rtmsg", rtmsg);			    
					    hmp_return.put("rcode", rcode);	
						System.out.println("解析成功****");					    
 				if (handler!=null){
 					msg.what = 0;
 					msg.obj=hmp_return;
 					handler.sendMessage(msg);
 				}	            
                }else{
 				if (handler!=null){
 					msg.what = 1;
 					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
 					handler.sendMessage(msg);
 				}
            }
 		  } catch (Exception e) {  
 		            // TODO: handle exception  
 			  System.out.println(e);
 				if (handler!=null){
 					msg.what = 1;
 					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
 					handler.sendMessage(msg);
 				}			  
 		    }        
     	
     }    
    
    

    public static void getPutBenefit(String key, String jsonString,Handler handler){
 		Message msg = new Message();
 		HashMap  hmp_return=new HashMap();
 		String rcode,rtmsg,scid,vid,bdate;
         try {
 			System.out.println("开始解析调用结果，key:*******"+key+" *****************");        	 
             JSONObject jsonObject_info = new JSONObject(jsonString);
             String code=(String) jsonObject_info.get("code");
 		    JSONObject jsonObject_sub_info = (JSONObject) jsonObject_info.getJSONObject(key);            
             //0表示成功
            if (code!=null&&code.equalsIgnoreCase("0")){
 	            //返回json的数组              
            	        rtmsg=jsonObject_sub_info.getString("rtmsg");
            	        rcode=jsonObject_sub_info.getString("rcode");	 			    	        
            	        //scid=jsonObject_sub_info.getString("scid");
            	        //vid=jsonObject_sub_info.getString("vid");
            	        if (rcode.equalsIgnoreCase("0")){
            	        	bdate=jsonObject_sub_info.getString("bdate");
            	        }else{
            	        	bdate="";
            	        }
	                    hmp_return.put("rtmsg", rtmsg);			    
					    hmp_return.put("rcode", rcode);							    					    	             					    					     					    
//	                    hmp_return.put("scid", scid);			    
//					    hmp_return.put("vid", vid);
	                    hmp_return.put("bdate", bdate);
						System.out.println("解析成功****");	                    
 				if (handler!=null){
 					msg.what = 0;
 					msg.obj=hmp_return;
 					handler.sendMessage(msg);
 				}	            
                }else{
 				if (handler!=null){
 					msg.what = 1;
 					msg.obj = ConstConnectCode.RETURN_INFO_ERROR;
 					handler.sendMessage(msg);
 				}
            }
 		  } catch (Exception e) {  
 		            // TODO: handle exception  
 			  System.out.println(e);
 				if (handler!=null){
 					msg.what = 1;
 					msg.obj = ConstConnectCode.RETURN_SYS_ERROR;
 					handler.sendMessage(msg);
 				}			  
 		    }        
     	
     }    

    
    
    
}  

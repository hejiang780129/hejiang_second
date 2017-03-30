package com.hbjr.washcheapp.net;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.util.Base64;

public class JSONPostTools {
	
	  /**
	   * 
	   * @param key : JSON 名值对中的的名字
	   * @param value : JSON 名值对中的值,值可以有多种类型 
	   * @return
	   */
	  public static String createJsonString(String key, Object value){  
		  Gson gson=new Gson();
		  String str=gson.toJson(value);
	      System.out.println("json str is :"+str);
		  return str;
//	      JSONObject jsonObject = new JSONObject();  
//	      try{
//		      jsonObject.put(key, value);
//		      System.out.println("key is :"+key);
//		      System.out.println("json str is :"+jsonObject.toString());
//		      return jsonObject.toString(); //就可以转换成Json数据格式		      
//	      }catch(Exception e){
//	    	  e.printStackTrace();
//	    	  return "";
//	      }//catch  
	  }  
			
	  
 
      public static String[] getPutImageList(Bitmap[] imgs){
    	  String[] imgarr=new String[imgs.length];
    	  Bitmap bmp;
    	  byte[] img_byte;
    	  for (int i=0;i<imgs.length;i++){
    		  bmp=imgs[i];
    		  img_byte=Bitmap2Bytes(bmp);    		  
    		  imgarr[i]= new String(Base64.encode(img_byte));    		      		      		  
    	  }    	      	  
    	  return imgarr;
      }
	  
      public static byte[] Bitmap2Bytes(Bitmap bm) {
    	   ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	   bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
    	   byte[] result=baos.toByteArray();
    	   try {
			   baos.close();
		   } catch (Exception e) {
			// TODO: handle exception
			   e.printStackTrace();
		   }    	       	   
    	   return result;
       }
	  
	  
	  
	  
	  
	  
	  
	  /**
	   *  for example========================================================================
	   * @return
	   */
	  	  	  	  	  
	  public List<String> getListString(){  
	      List<String> list = new ArrayList<String>();  
	      list.add("Hello");  
	      list.add("World");  
	      list.add("AHuier");  
	      return list;  
	  }  
	  
	  public List<Map<String, Object>> getListMaps(){  
	      List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();  
	      Map<String, Object> map1 = new HashMap<String, Object>();  
	      map1.put("color", "red");  
	      map1.put("id", 01);  
	      map1.put("name", "Polu");  
	      listMap.add(map1);  
	      Map<String, Object> map2 = new HashMap<String, Object>();  
	      map2.put("id", 07);  
	      map2.put("color", "green");  
	      map2.put("name", "Zark");  
	      listMap.add(map2);  
	      return listMap;   
	  }  
	  
	  	  	  	  	  
	  
	  /**
	   * WxcBusInfo
	   * @return
	   */
	  public static WxcBusInfo getWxcBusInfo(String xcd_id){  
	      WxcBusInfo info=WxcBusInfo.getInfoById(xcd_id);
    	  info.setImgUrl("http://wyxc.nat123.net/WyxcServerProject/uploadimages/"+xcd_id+"/"+xcd_id+"-1.jpg");	      
	      return info;  
	  }   
	  
	  
	  
	  
	  
	  
	  

}

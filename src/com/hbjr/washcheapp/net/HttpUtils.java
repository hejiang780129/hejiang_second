package com.hbjr.washcheapp.net;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
   public HttpUtils(){
	   
   }
   
   public static String getJsonContent(String url_path){  
       try {  
           URL url = new URL(url_path);  
           HttpURLConnection connection = (HttpURLConnection)url.openConnection();  
           connection.setConnectTimeout(3000); // ����ʱʱ��3s  
           connection.setRequestMethod("POST");  
           connection.setDoInput(true);  
           int code = connection.getResponseCode(); // ����״̬��  
           if (code == 200) {  
               // ��õ�����������ʱ�������Ѿ������˷���˷��ػ�����JSON������,��ʱ��Ҫ�������ת�����ַ���  
               return changeInputStream(connection.getInputStream());  
           }  
       } catch (Exception e) {  
           // TODO: handle exception  
    	   e.printStackTrace();
       }  
       return "";  
   }  
 
   private static String changeInputStream(InputStream inputStream) {  
       // TODO Auto-generated method stub  
       String jsonString = "";  
       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();  
       int length = 0;  
       byte[] data = new byte[1024];  
       try {  
           while (-1 != (length = inputStream.read(data))) {  
               outputStream.write(data, 0, length);  
           }  
           // inputStream�������õ�����д��ByteArrayOutputStream����,  
           // Ȼ��ͨ��outputStream.toByteArrayת���ֽ����飬��ͨ��new String()����һ���µ��ַ�����  
           jsonString = new String(outputStream.toByteArray());  
       } catch (Exception e) {  
           // TODO: handle exception  
       }  
       return jsonString;  
   }  
}


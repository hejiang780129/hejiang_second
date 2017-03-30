package com.hbjr.washcheapp.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.WxcLoginActivity;
import com.hbjr.washcheapp.WxcMainActivity;
import com.hbjr.washcheapp.WxcRegisterActivity;
import com.hbjr.washcheapp.WxcShareXcdUploadActivity;
import com.hbjr.washcheapp.WxcSplashActivity;
import com.hbjr.washcheapp.WxcWyxcInfoActivity;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.bean.WxcUserAccount;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.config.ConstConnectCode;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.config.SinglePreferences;
import com.hbjr.washcheapp.fragment.WxcBottomFragment.OnIndicateListener;
import com.hbjr.washcheapp.fragment.WxcBottomMyFragment;
import com.hbjr.washcheapp.net.JSONTools;
import com.hbjr.washcheapp.net.URLProtocol;
import com.hbjr.washcheapp.net.WxcHttpConnect;
import com.hbjr.washcheapp.widget.BadgeView;
import com.hbjr.washcheapp.widget.LoadingDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WxcPublicUtil {

	
//	// create a bitmap variable before anything;
//	  
//	private static Bitmap bitmap;
//	  
//	// variable to set a name to the image into SD card;
//	// this variable, you have to put the path for the File, It's up to you;
//	  
//	public static String exsistingFileName;
//	  
//	// sendData is the function name, to call it, you can use something like sendData(null);
//	// remember to wrap it into a try catch;
	
	
	/** 
	 * С�� �������� 
	 *  
	 * @param val 
	 * @param precision 
	 * @return 
	 */  
	  
	public static Double roundDouble(double val, int precision)  
	{  
	 Double ret = null;  
	 try  
	 {  
	  double factor = Math.pow(10, precision);  
	  ret = Math.floor(val * factor + 0.5) / factor;  
	 }  
	 catch (Exception e)  
	 {  
	  e.printStackTrace();  
	 }  
	 return ret;  
	}  
	
	
	
	/**
	 * �ַ����ָ�
	 */
	
	public static String[] split(String str, String regex) 	
	{ 		// ��ŷָ����ַ��� 		
		    String newStr[] = new String[str.length()]; 		
		    // ��ʱ����ַ��������һ��Ԫ�� 		
		    String temp = null; 		
		    // ���ƴ�ŷָ����ַ��� 		
		    String[] result = null; 		
		    // �ָ������ڵ���һ�������͵�ǰ���� 		
		    int start = 0, end = 0; 		
		    // �ַ������������ 		
		    int index = 0; 		 		
		    /** �����ַ�����ÿһ���ַ� */ 		
		    for (int i = 0; i < str.length(); i++) 		{
		    	temp = str.substring(i, i + 1); // ��ȡһ���ַ� 			
		    	// �жϽ�ȡ���ַ��Ƿ�Ϊ�ָ��� 			
		    	if (temp.equals(regex)) 			{
		    		temp = null; 				
		    		end = i; // ��ǰ�ķָ���������(�ַ�����ȡ�����λ��) 				
		    		newStr[index] = str.substring(start, end); // ��ȡ�ַ�������ֵ���ַ������� 				
		    		index++;  // �ַ�������������1 				
		    		start = end + 1; // ��ǰ�ķָ�����������һ����(�ַ�����ȡ�Ŀ�ʼλ��) 
		    		} 		
		    	}
		    
		      // ���һ���ָ����������������ַ������� 
				if (str.lastIndexOf(regex) != str.length()) 		{ 
					newStr[index] = str.substring(start, str.length());
					index++; 
					} 	
				result = new String[index]; 	
				// ��ָ��Դ�����и���һ�����飬���ƴ�ָ����λ�ÿ�ʼ����Ŀ�������ָ��λ�ý��� 	
				System.arraycopy(newStr, 0, result, 0, index); 
				return result; 
	}

    /**
     * ȡ��ǰʱ��
     */
	
	public static String getCurDate(){
		
		 SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd");   
	     String   date   =   sDateFormat.format(new   java.util.Date());
		 return date;
	}
	
	
	 /**
     * ȡ��ǰʱ��
     */
	
	public static String getCurShortDate(){
		
		 SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyMMddHHmm");   
	     String   date   =   sDateFormat.format(new   java.util.Date());
		 return date;
	}
	
	public static String getCurDate_long(){
		
		 SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy��MM��dd��");   
	     String   date   =   sDateFormat.format(new   java.util.Date());
		 return date;
	}
	
	
	public static String getCurDateTime(){
		
		 SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("dd�� HHʱ:mm��:ss��");   
	     String   date   =   sDateFormat.format(new   java.util.Date());
		 return date;
	}
	
	
	/**
	 * ������ʾToast��Ϣ
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShortToast(Context context, String message) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.custom_toast, null);
		TextView text = (TextView) view.findViewById(R.id.toast_message);
		text.setText(message);
		Toast toast = new Toast(context);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 300);
		toast.setView(view);
		toast.show();
	}

	/**
	 * �����ֻ��ֱ��ʴ�dpת��px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	/**
	 * ��ȡ�ֻ�״̬���߶�
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}

	
	/**
	 * �ж��ֻ�����*/
	public static boolean isMobileNO(String mobiles){
		
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher matcher = pattern.matcher(mobiles);  
		
		return matcher.matches();
		
	}
	
	
	/**
	 * ��Toast��ʾ��Ϣ
	 * 
	 * @param context
	 *            ������
	 * @param text
	 *            ��ʾ�ı�
	 */
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	
	public static String subtraction(String value1,String value2){
		if (value1.equalsIgnoreCase("")||value1==null){
			value1="0";
		}
		if (value2.equalsIgnoreCase("")||value2==null){
			value2="0";
		}		
		int int_value1= Integer.parseInt(value1);
		int int_value2= Integer.parseInt(value2);
		int tmp=int_value1-int_value2;
        if (tmp<0) tmp=0;
		return String.valueOf(tmp);
	}
	
	
	/**
	 * ��ʾ�Ի���(ȷ�����˳�����)
	 * 
	 * @param context
	 *            ������
	 * @param title
	 *            ����
	 */
//	public static void showDialog(Context context, String title) {
	
//		int i=res.getIdentifier("icon","drawable",getPackageName());  
//
//		new AlertDialog.Builder(context)
//				.setTitle(title)
//				.setNegativeButton(R.string.submit);
//				.setPositiveButton(R.string.cancel,
//						new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								dialog.dismiss();
//							}
//						}).show();
//	}
//	
	
	/* ����һ������ʱ���ڲ��� */
	public static class TimeCount extends CountDownTimer {
	private View view;
	private Button bt;
	private EditText edt;
	private TextView tv;
	private String title;
	public TimeCount(long millisInFuture, long countDownInterval,View view,String title) {
	super(millisInFuture, countDownInterval);//��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
	this.view=view;
	this.title=title;
	if (view instanceof Button){
		bt=(Button) view;
	}else if(view instanceof EditText){
		edt=(EditText) view;
	}else if(view instanceof TextView){
		tv=(TextView) view;
	}
	}
	@Override
	public void onFinish() {//��ʱ���ʱ����
		if (view instanceof Button){
			bt.setText(title);
			bt.setClickable(true);
		}else if(view instanceof EditText){
			edt.setText(title);
			edt.setClickable(true);
			
		}else if(view instanceof TextView){
			tv.setText(title);
			tv.setClickable(true);
		}
	}
	@Override
	public void onTick(long millisUntilFinished){//��ʱ������ʾ
			if (view instanceof Button){
			bt.setText(millisUntilFinished /1000+"��");
			bt.setClickable(false);
		}else if(view instanceof EditText){
			edt.setText(millisUntilFinished /1000+"��");
			edt.setClickable(false);
			
		}else if(view instanceof TextView){
			tv.setText(millisUntilFinished /1000+"��");
			tv.setClickable(false);
		}
		
	}
	}
	

	/** 
	    * �������������͵�ʱ��������ʱ�� 
	    * @param startDate  ��ʼ���� 
	    * @param endDate    �������� 
	      * @return 
	    */  
	    public  String twoDateDistance(Date startDate,Date endDate){  
	          
	        if(startDate == null ||endDate == null){  
	            return null;  
	        }  
	        long timeLong = endDate.getTime() - startDate.getTime();  
	        if (timeLong<60*1000)  
	            return timeLong/1000 + "��ǰ";  
	        else if (timeLong<60*60*1000){  
	            timeLong = timeLong/1000 /60;  
	            return timeLong + "����ǰ";  
	        }  
	        else if (timeLong<60*60*24*1000){  
	            timeLong = timeLong/60/60/1000;  
	            return timeLong+"Сʱǰ";  
	        }  
	        else if (timeLong<60*60*24*1000*7){  
	            timeLong = timeLong/1000/ 60 / 60 / 24;  
	            return timeLong + "��ǰ";  
	        }  
	        else if (timeLong<60*60*24*1000*7*4){  
	            timeLong = timeLong/1000/ 60 / 60 / 24/7;  
	            return timeLong + "��ǰ";  
	        }  
	        else {  
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));  
	            return sdf.format(startDate);  
	        }  
	}  
	
	
	public static boolean compareDate(String date1,String date2){
	    java.text.DateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd");
	    java.util.Calendar c1=java.util.Calendar.getInstance();
	    java.util.Calendar c2=java.util.Calendar.getInstance();
	    try
	    {
	    c1.setTime(df.parse(date1));
	    c2.setTime(df.parse(date2));
	    }catch(java.text.ParseException e){
	    return false;
	    }
	    int result=c1.compareTo(c2);
	    if(result==0)
	    return false;	
	    else if(result<0)
	    return false;
	    else
	    return true;
	} 
	
	
	public  static String getRegin_City(String city){
		String tmp_city="";
		int indexof=0;
		if (city==null||city.equalsIgnoreCase("")) return city;
		indexof=city.indexOf("��");
		if (indexof==(city.length()-1)) tmp_city=city.substring(0,indexof);
		return tmp_city;
	}
	
	
	
	  
//	public void sendData(String[] args) throws Exception {
//	    try {
//	  
//	        HttpClient httpClient = new DefaultHttpClient();
//	        HttpContext localContext = new BasicHttpContext();
//	  
//	        // here, change it to your php;
//	  
//	        HttpPost httpPost = new HttpPost("http://www.myURL.com/myPHP.php");
//	        //MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//	        bitmap = BitmapFactory.decodeFile(exsistingFileName);
//	  
//	        // you can change the format of you image compressed for what do you want;
//	        //now it is set up to 640 x 480;
//	  
//	        Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 640, 480, true);
//	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//	        // CompressFormat set up to JPG, you can change to PNG or whatever you want;
//	        bmpCompressed.compress(CompressFormat.JPEG, 100, bos);
//	        byte[] data = bos.toByteArray();
//	        // sending a String param;
//	        entity.addPart("myParam", new StringBody("my value"));
//	        // sending a Image;
//	        // note here, that you can send more than one image, just add another param, same rule to the String;
//	  
//	        entity.addPart("myImage", new ByteArrayBody(data, "temp.jpg"));
//	  
//	        httpPost.setEntity(entity);
//	        HttpResponse response = httpClient.execute(httpPost, localContext);
//	        BufferedReader reader = new BufferedReader(new InputStreamReader( response.getEntity().getContent(), "UTF-8"));
//	        String sResponse = reader.readLine();
//	  
//	    } catch (Exception e) {
//	  
//	        Log.v("myApp", "Some error came up");
//	  
//	    }
//	  
//	}
	
	
	
	/**
	 * �첽��¼����
	
	 *
	 */
	public static class FrameTask extends AsyncTask<Integer, String, Integer> {
		Handler handler;
		String username,password;
		Context context;
		public FrameTask(Context context,Handler handler,String username,String password) {
			this.handler=handler;
			this.username=username;
			this.password=password;
			this.context=context;
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				//Toast.makeText(WxcLoginActivity.this, "��¼�ж�", 3000).show();				
			}
			return null;
		}
		protected void onPostExecute(Integer result) {
			System.out.println("============��ʼִ���첽��¼===============");
			loginToServer(context,handler,username,password);
		}
	}
	
	
	/**
	 * �첽ȡϵͳ��Ϣ
	
	 *
	 */
	public static class FrameTask_getInfo extends AsyncTask<Integer, String, Integer> {
		Context context;
		public FrameTask_getInfo(Context context) {
			this.context=context;
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {			
			}
			return null;
		}
		protected void onPostExecute(Integer result) {						
					                                  						
			//================================�첽����====================================================
			final Handler handler_post = new Handler() {
		        public void handleMessage(Message msg) {
					String rcode,msgnum,version;				        	
	                switch (msg.what) {
					case 0:						
		                HashMap  hmp_get= (HashMap) msg.obj;				    
		                msgnum=(String) hmp_get.get("msgnum");
		                version=(String) hmp_get.get("version");		                		                		        
                if (version==null||version.equalsIgnoreCase("")) version="";		            		
                if (msgnum==null||msgnum.equalsIgnoreCase("")) msgnum="0";                    
				int con_information=(Integer) SinglePreferences.getInstance().initSP(context).redeSpData(ConstantS.INFORMATION, 0);
				String con_version=(String) SinglePreferences.getInstance().initSP(context).redeSpData(ConstantS.VERSION, "");			
				//ȡϵͳ��Ϣ�����ֻ��Ϣ������ȯ���Ϣ��ϵͳ��Ϣ��ϵͳ�汾
				int get_information=Integer.parseInt(msgnum);
				String get_version=version;			
				
				if (con_information!=get_information){
					ConstantS.information_isnew=get_information-con_information;
					ConstantS.information=get_information;
				}
				if (con_version!=get_version){
					ConstantS.version_isnew=1;				
				}					                                        				                				                	
						break;
					case 1:		
	                	System.out.println("��ȡϵͳ��Ϣʧ��=======================");
						break;
					}		        	                    		        	
		        }
		  };	
	    new Thread(new Runnable() {
	        public void run() {
				HashMap map_return=new HashMap();
	            try {
		        		Map map_put = new HashMap();
		        		map_put.put("fid", ConstConnectCode.FID_GET_SYSINFO);		            			            		
		        		//��ȡ����������ĵ�ַ
		        		String json = URLProtocol.getJSonString(map_put);        		
		        		JSONTools.getSysMessage(ConstConnectCode.FID_GET_SYSINFO, json,handler_post,map_return);        		        		        		
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
	        }
	    }).start();
			
			
			
			//=====================================================================================
		}
	}
	
	
	
	
	
	/**
	 * ������� �����¼
	 * @param name �û���
	 * @param password ����
	 */
	public static void loginToServer(Context context_login,Handler handler_login,String username,String password) {
		final Handler handler=handler_login;
		final String rname=username;
		final String rpassword=password;	
		final Context context=context_login;
		final LoadingDialog loadingdialog=new LoadingDialog(context, LoadingDialog.TYPE_POST);
		Message msg = new Message();
		try {
//			String rname = (String) SinglePreferences.getInstance().initSP(context).redeSpData(ConstantS.USER_NAME, "-1");
//			String rpassword = (String) SinglePreferences.getInstance().initSP(context).redeSpData(ConstantS.USER_PWD, "-1");
			if (!ConstantS.isDemo){	
			//������������¼�Ĳ���
			Map<String, String> map = new HashMap<String, String>();
			// ��¼(cmd=0,uid=?)
			//map.put("cmd", ConstConnectCode.LOGIN + "");
//			map.put("uid", uid);
			//��ȡ����������ĵ�ַ
			String json ="";
					//URLProtocol.getJSonString(map,null);
			// code=0,uid=?,cmd=0,login=0
			//��ȡ���������صĽ��
			JSONObject object = new JSONObject(json);
			String code = (String) object.get("code");
			//��¼�ɹ�
			if (code.equals("0")) {			
				WxcUserAccount useraccount=WxcUserAccount.useraccount;//�ӷ�������ȡ�ø��û����˻���Ϣ
				//ConstantS.user_account=useraccount;
				ConstantS.user_isLogin=true;
				if (handler!=null){
					msg.what = 1;
					
					handler.sendMessage(msg);
				}
				
			} else {
				if (handler!=null){
					msg.what = 2;
					msg.obj = "��¼ʧ�ܣ������������";
					handler.sendMessage(msg);
				}
				ConstantS.user_isLogin=false;
			}			
		}
		//����ǲ���demo����
		else{			
	                                  		         
		    //===============�첽���õ�¼===================================================     
                    if (handler!=null){
    					loadingdialog.show();   	    				
                    }
					final Handler handler_post = new Handler() {
				        public void handleMessage(Message msg) {
							String rcode,rtmsg,msgnum,version;
							WxcUserAccount account;
			                switch (msg.what) {
							case 0:						
				                HashMap  hmp_get= (HashMap) msg.obj;								
					            rcode=(String) hmp_get.get("rcode");
					            rtmsg=(String) hmp_get.get("rtmsg");					            
					            version=(String) hmp_get.get("version");
					            msgnum=(String) hmp_get.get("msgnum");
					            account=(WxcUserAccount) hmp_get.get("account");

								WxcUserAccount useraccount=account;//�ӷ�������ȡ�ø��û����˻���Ϣ
								WxcUserAccount.useraccount=useraccount;
								ConstantS.user_isLogin=true;
								System.out.println("����bottomfragment ���������и�ֵ=========");
								WxcBottomMyFragment bmf=WxcBottomMyFragment.getInstance();
								if (bmf!=null) bmf.setStatus();								
								System.out.println("��ֵ���==============================");								
								//�ж��Ƿ�����Ϣ���£�����ȯ������
								int con_user_score = (Integer) SinglePreferences.getInstance().initSP(context).redeSpData(ConstantS.USER_SCORE, 0);
								int con_user_vou = (Integer) SinglePreferences.getInstance().initSP(context).redeSpData(ConstantS.USER_VOU, 0);

								if (con_user_score!=Integer.valueOf(WxcUserAccount.useraccount.getScore_sum())){
									ConstantS.user_score_isnew=1;
									ConstantS.user_score=Integer.valueOf(WxcUserAccount.useraccount.getScore_sum());
								}
								if (con_user_vou!=Integer.valueOf(WxcUserAccount.useraccount.getVou_total())){
									ConstantS.user_vou_isnew=1;
									ConstantS.user_vou=Integer.valueOf(WxcUserAccount.useraccount.getVou_total());
								}						
								System.out.println("�첽��¼�ɹ�-----");										
					 			if (handler!=null){
				    				loadingdialog.dismiss();   	    								 				
					 				Message msg_login=new Message();
					 				System.out.println("��¼�ɹ�-------");				
					 				msg_login.what=1;
					 				msg_login.obj=rtmsg;
					 				handler.sendMessage(msg_login);	
					 			}					            					            					            								
								break;
							case 1:		
			                	System.out.println("��¼ʧ��=======================");
			                	rtmsg=(String) msg.obj;
					 			if (handler!=null){
				    				loadingdialog.dismiss();					 				
					 				Message msg_login=new Message();
					 				System.out.println("��¼ʧ��-------");				
					 				msg_login.what=2;
					 				msg_login.obj=rtmsg;
					 				handler.sendMessage(msg_login);	
					 			}			                				                				                				                	
								break;
							}		        	                    		        	
				        }
				  };	
			    new Thread(new Runnable() {
			        public void run() {
			        	HashMap map_return=new HashMap();        	
			            try {
				        		Map map_put = new HashMap();
				        		map_put.put("fid", ConstConnectCode.FID_LOGIN);
				        		map_put.put("uname",rname);
				        		map_put.put("pwd",rpassword);		            			            		
				        		//��ȡ����������ĵ�ַ
				        		String json = URLProtocol.getJSonString(map_put);
    			        		JSONTools.login(ConstConnectCode.FID_LOGIN, json, handler_post,map_return);				        			        		        		        		
				            } catch (Exception e) {
				                e.printStackTrace();
				            }
			        }
			    }).start();		         
		         
		         
		         
		         
		         
		    //=========================================================================		         
		         
		}
			
		} catch (Exception e) {
			e.printStackTrace();
			ConstantS.user_isLogin=false;
			if (handler!=null){
			System.out.println("�첽��¼�ɹ�-----ִ��handle");				
			msg.what = 2;
			msg.obj="ϵͳ�쳣";
			handler.sendMessage(msg);
		}			
			
		}
	}
	
	
	  /**
	  	 * ��λ���
	  	 */
	  	public static LocationClient mLocClient;
	  	static MyLocationListenner myListener = new MyLocationListenner();
	  	static LatLng currentPt;
	  	static LatLng latlng;
	  	static GeoCoder mSearch = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
	  	static boolean isFirstLoc = true;// �Ƿ��״ζ�λ
	  	static Context cur_context;
	  	private static Handler handler_location_city;
	  	private static Handler handler_location_regin;
	  	private static boolean cur_isneedregin=false;
	  	private static boolean cur_isneedweather=false;
 	   public static void initLocation(Context context,boolean firstloc,boolean isneedregin,boolean isneedweather,Handler han_location_city,Handler han_location_regin,Handler han_weather){
 		   System.out.println("��ʼ������λ==========================");
 		   isFirstLoc=firstloc;
 		   cur_context=context;
 		   handler_weather=han_weather;
  		   handler_location_regin=han_location_regin;
  		   handler_location_city=han_location_city; 		   
 		   cur_isneedregin=isneedregin;
 		   cur_isneedweather=isneedweather;
 			// ��λ��ʼ��
 			mLocClient = new LocationClient(cur_context);
 			mLocClient.registerLocationListener(myListener);
  		   System.out.println("���ؼ���==========================");
 			LocationClientOption option = new LocationClientOption();
 			option.setOpenGps(true);// ��gps
 			option.setCoorType("bd09ll"); // ������������
 			option.setScanSpan(1000);
 			option.setIsNeedAddress(true);			
 			mLocClient.setLocOption(option);
 			mLocClient.start();
   		   System.out.println("��ʼ��λ==========================");						
 		}
	
	
 	  /**
 			 * ��λSDK��������
 			 */
 			public static class MyLocationListenner implements BDLocationListener {

 				@Override
 				public void onReceiveLocation(BDLocation location) {
 					//System.out.println("��ʼ������λ���ź�");
 					// map view ���ٺ��ڴ����½��յ�λ��
 					if (location == null){
 						System.out.println("locationis null");
 						ConstantS.cur_regin="";
 						ConstantS.Regin_City="";
 						isFirstLoc=true;
 						return;
 					}
 					if (isFirstLoc) {
 			   		   System.out.println("��һ�ζ�λ=========================="); 						
 						latlng = new LatLng(location.getLatitude(),location.getLongitude());
 						ConstantS.cur_latlng=latlng;
 						System.out.println("��ǰ��ȡ�ľ�γ��Ϊ��==========================="+ConstantS.cur_latlng+"=======================");
 						if (latlng==null){
 	 						ConstantS.cur_regin="";
 	 						ConstantS.Regin_City="";
 	 						ConstantS.cur_latlng=null; 	 						
 	 						isFirstLoc=true;
 							return;
 						}
 						isFirstLoc = false;
  			   		   System.out.println("ȡ�ö�λ��Ϣ=========================="); 						
 						System.out.println("ȡ�õ�latlng:"+latlng.latitude+"  "+latlng.longitude);
 						System.out.println("ȡ�õ�����������Ϣ�ǣ�"+ location.getProvince()+"  "+ location.getCity());
 						ConstantS.Regin_Province = location.getProvince();  // ��ȡʡ����Ϣ
 						ConstantS.Regin_City= WxcPublicUtil.getRegin_City(location.getCity());  // ��ȡ������Ϣ
 						ConstantS.Regin_City_ID = cityCodes.get(ConstantS.Regin_City); 						
 						System.out.println("��ȡ��ĳ�����Ϣ�ǣ���"+ ConstantS.Regin_City);
 						ConstantS.Regin_District = location.getDistrict(); // ��ȡ������Ϣ
 						if (handler_location_city!=null){
 							Message msg = new Message();
 							msg.what = 1;
 							msg.obj = ConstantS.Regin_City;							
 							handler_location_city.sendMessage(msg); 							
 						}
 						

 						if (cur_isneedweather){ 						
		 				   if (ConstantS.weather_weather==null||ConstantS.weather_weather.equalsIgnoreCase("")){
		 						System.out.println("�����ȡ������Ϣ");
		 					    requestWeather(ConstantS.Regin_City,handler_weather);
		 					}
 						}
 						
 						if (cur_isneedregin){
 	 			   		   System.out.println("��ʼ���õ�ַ����=========================="); 							
 							System.out.println("����õ�ַ��Ϣ=========="); 							
 	 						geoCoder(latlng); 							
 						}
 					}
 				}

 				public void onReceivePoi(BDLocation poiLocation) {
 				}
 			}
 			
 			
 			private static void geoCoder(LatLng ptCenter){
 				// ��ʼ������ģ�飬ע���¼�����
 				mSearch = GeoCoder.newInstance();
 				mSearch.setOnGetGeoCodeResultListener(new MyGeoCoderListener());
 				// ��Geo����
 				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
 						.location(ptCenter));
 				System.out.println("���÷������ȡ��ǰ��ַ======================="); 				
 			}
 			
 			/**
 			 * ��������������
 			 */
 			public static class  MyGeoCoderListener implements  OnGetGeoCoderResultListener{

 				@Override
 				public void onGetGeoCodeResult(GeoCodeResult arg0) {
 					// TODO Auto-generated method stub
 					
 				}

 				@Override
 				public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
 					// TODO Auto-generated method stub
 					System.out.println("��ʼ��ȡ��ַ��Ϣ������������������������");
 					if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                        System.out.println("δ�ҳ���ַ���");
                        ConstantS.cur_regin="";
 						return;
 					}						
 				    System.out.println("ȡ��cur_regin��Ϣ=======================");
 					ConstantS.cur_regin=result.getAddress();
 				    System.out.println("ȡ�õ�cur_regin�ǣ�======================="+ConstantS.cur_regin); 					
						if (handler_location_regin!=null){
		 				    System.out.println("����locatoion_regin handle");							
 							Message msg = new Message();
 							msg.what = 1;
 							msg.obj =ConstantS.cur_regin;							
 							handler_location_regin.sendMessage(msg);
 		 					System.out.println("ȡ�õĵ�ǰ��ַ�ǣ�������������������������"+ConstantS.cur_regin+"=============="); 							
 						}else{
 		 				    System.out.println("�������regin handle======================="); 							
 						}
 					
 				}
 				
 			}
 		    
	
 			
 		   /**
 			 * ������������صĳ���
 			 */
 			
 			/***************************************************************************************
 			 * ע���ڶ���txt��ʱ����UTF-8���Լ������Լ���txt�ı���ʽ�������Ϊ�Ϳ��Կ�������
 			 */
 			private static Map<String,String> cityCodes;	//���ݳ�����Ϣ�����Լ���code
 			private static List<String> citys;				//������������Դ	
 			public static void getAssetsContent(Context context){
 				try {
 					String buf;
 					citys = new ArrayList<String>();
 					cityCodes = new HashMap<String, String>();
 					InputStream input = context.getAssets().open("cityCode.txt");
 					BufferedReader br = new BufferedReader(new InputStreamReader(input,"UTF-8"));
 					while((buf = br.readLine())!=null){
 						String[] codeCity = buf.split("=");
 						citys.add(codeCity[1]);
 						cityCodes.put(codeCity[1], codeCity[0]);
 					}
 					System.out.println("������������������");
 				} catch (IOException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
 			}
 			
 			
 			/**************************************************************************************
 			 * ��ȡhttp://m.weather.com.cn/data/101091106.html���������
 			 * ����101091106Ϊ���д��룬�������Ѿ��ѳ��д��������޸ģ�ȥ���˿��У���ʽΪUTF-8
 			 * ÿ��ֻ������һ���̣߳�Ȼ��������̻߳���
 			 * @param city_str
 			 * @throws JSONException 
 			 * @throws Exception 
 			 * @throws ClientProtocolException 
 			 */
 			private static Thread thread;
 			private static Handler handler_weather;
 			
 			/************************************************************************
 			 * �����߳��в�����������
 			 * �߳�û��whileѭ��ֻ��ִ��һ�Σ������������ܲ��ܷ�����Ϣ
 			 * @param city_str
 			 */
 			public static void requestWeather(final String city_str,Handler h_weather){ 
 				handler_weather=h_weather;
 				thread = new Thread(new Runnable(){
 					@Override
 					public void run() {
 						// TODO Auto-generated method stub
 						System.out.println("����requestWeather������������������");
 						String code = cityCodes.get(city_str);
 						String url;
 						if("����".equalsIgnoreCase(city_str)){
 							url = "http://m.weather.com.cn/atad/"+101010100+".html";
 						}else{
 							url = "http://m.weather.com.cn/atad/"+code+".html";
 						}
 						HttpClient client = new DefaultHttpClient();
 						HttpGet httpget = new HttpGet(url);
 						System.out.println("����httpget������url ��"+url);
 						HttpResponse response;
 						String sbuf = null;
 						try {
 							response = client.execute(httpget);
 							HttpEntity httpentity = response.getEntity();
 							System.out.println("ȡ��httpclient����");
 							if(httpentity != null){
 								System.out.println("ȡ�õ�httpclient������Ϊ��");
 								BufferedReader br = new BufferedReader(new InputStreamReader(httpentity.getContent(),"utf-8"));	
 								sbuf = br.readLine();	
 								System.out.println("��ȡhttpclient���");
 							}	
 							JSONObject object = new JSONObject(sbuf);
 							JSONObject data = (JSONObject) object.getJSONObject("weatherinfo");
 							 System.out.println("���ص����������ǣ�"+data.toString());
 							
 	 						try{					
 	 	 						System.out.println("��splash��ȡ��������Ϣ");		
 	 	 						ConstantS.weather_weather=data.getString("weather1");
 	 	 						int icon = parseIcon(data.getString("img1")+".gif");					
 	 	 						ConstantS.weather_icon=icon;
 	 	 						ConstantS.weather_date=data.getString("date_y")+" "+data.getString("week");
 	 	 						ConstantS.weather_xc=data.getString("index_xc")+"ϴ��";					
 	 	 						ConstantS.weather_temp=data.getString("temp1");
 	 	 						ConstantS.weather_wind=data.getString("wind1");	
 	 	 						System.out.println("��ȡ��������Ϣ�ǣ�"+ConstantS.weather_weather+";"+ConstantS.weather_date
 	 	 								+";"+ConstantS.weather_temp+";"+ConstantS.weather_wind+";"+ConstantS.weather_xc);					
 	 	 						}catch(Exception e){
 	 	 						   e.printStackTrace();	
 	 	 						} 	 		
 	 						if (handler_weather!=null){
		 							Message msg = new Message();
		 							msg.what = 1;
		 							msg.obj = data;
		 							handler_weather.sendMessage(msg);
 	 						}
 	 						try {
 	 							thread.join();
 	 						} catch (InterruptedException e) {
 	 							// TODO Auto-generated catch block
 	 							e.printStackTrace();
 	 						}
 							
 						} catch (ClientProtocolException e) {
 							// TODO Auto-generated catch block
 							e.printStackTrace();
 						} catch (IOException e) {
 							// TODO Auto-generated catch block			
 							e.printStackTrace();
 						} catch (JSONException e) {
 							// TODO Auto-generated catch block					
 							e.printStackTrace();
 						}
 					}	
 				});
 				thread.start(); 				
 			}
 			
 			
 			// ���߷������÷�������ѷ��ص�����ͼ���ַ�����ת��Ϊ�����ͼƬ��ԴID��
 				public static int parseIcon(String strIcon)
 				{
 					if (strIcon == null)
 						return -1;
 					if ("0.gif".equals(strIcon))
 						return R.drawable.a_0;
 					if ("1.gif".equals(strIcon))
 						return R.drawable.a_1;
 					if ("2.gif".equals(strIcon))
 						return R.drawable.a_2;
 					if ("3.gif".equals(strIcon))
 						return R.drawable.a_3;
 					if ("4.gif".equals(strIcon))
 						return R.drawable.a_4;
 					if ("5.gif".equals(strIcon))
 						return R.drawable.a_5;
 					if ("6.gif".equals(strIcon))
 						return R.drawable.a_6;
 					if ("7.gif".equals(strIcon))
 						return R.drawable.a_7;
 					if ("8.gif".equals(strIcon))
 						return R.drawable.a_8;
 					if ("9.gif".equals(strIcon))
 						return R.drawable.a_9;
 					if ("10.gif".equals(strIcon))
 						return R.drawable.a_10;
 					if ("11.gif".equals(strIcon))
 						return R.drawable.a_11;
 					if ("12.gif".equals(strIcon))
 						return R.drawable.a_12;
 					if ("13.gif".equals(strIcon))
 						return R.drawable.a_13;
 					if ("14.gif".equals(strIcon))
 						return R.drawable.a_14;
 					if ("15.gif".equals(strIcon))
 						return R.drawable.a_15;
 					if ("16.gif".equals(strIcon))
 						return R.drawable.a_16;
 					if ("17.gif".equals(strIcon))
 						return R.drawable.a_17;
 					if ("18.gif".equals(strIcon))
 						return R.drawable.a_18;
 					if ("19.gif".equals(strIcon))
 						return R.drawable.a_19;
 					if ("20.gif".equals(strIcon))
 						return R.drawable.a_20;
 					if ("21.gif".equals(strIcon))
 						return R.drawable.a_21;
 					if ("22.gif".equals(strIcon))
 						return R.drawable.a_22;
 					if ("23.gif".equals(strIcon))
 						return R.drawable.a_23;
 					if ("24.gif".equals(strIcon))
 						return R.drawable.a_24;
 					if ("25.gif".equals(strIcon))
 						return R.drawable.a_25;
 					if ("26.gif".equals(strIcon))
 						return R.drawable.a_26;
 					if ("27.gif".equals(strIcon))
 						return R.drawable.a_27;
 					if ("28.gif".equals(strIcon))
 						return R.drawable.a_28;
 					if ("29.gif".equals(strIcon))
 						return R.drawable.a_29;
 					if ("30.gif".equals(strIcon))
 						return R.drawable.a_30;
 					if ("31.gif".equals(strIcon))
 						return R.drawable.a_31;
 					return 0;
 				}
 			
 		
 				
 	public static BadgeView addRedPoint(Context context,View view,String text,float textsize,int position){
 	  System.out.println("view is:"+view);
      BadgeView  badge1 = new BadgeView(context,view);
      badge1.setText(text); //��ʾ����
      badge1.setTextSize(textsize); //�ı���С      
      badge1.setBadgePosition(position);//��ʾ��λ��.�м䣬��������λ������
      badge1.setTextColor(Color.WHITE);  //�ı���ɫ
      badge1.setBadgeBackgroundColor(Color.RED); //������ɫ
      badge1.setBadgeMargin(0, 0); //ˮƽ����ֱ����ļ��
      return badge1;
 	}
 				
 				
 				
    public static void writeConfData(Context context,int type){   	
    	//дmy
    	if (type==0){
    		if (ConstantS.user_isLogin){
			 SinglePreferences.getInstance().initSP(context).writeSpData(ConstantS.USER_SCORE, ConstantS.user_score);
			 SinglePreferences.getInstance().initSP(context).redeSpData(ConstantS.USER_VOU, ConstantS.user_vou);
    		}
			 SinglePreferences.getInstance().initSP(context).writeSpData(ConstantS.INFORMATION, ConstantS.information);    		
			 SinglePreferences.getInstance().initSP(context).writeSpData(ConstantS.USER_SCORE_BENFIT, ConstantS.user_score_benfit);			 
			 SinglePreferences.getInstance().initSP(context).redeSpData(ConstantS.USER_VOU_BENFIT, ConstantS.user_vou_benfit);
    	}
    	//дset
    	if (type==1){
			 SinglePreferences.getInstance().initSP(context).writeSpData(ConstantS.VERSION, ConstantS.version);
    	}
    	//����дversion
    	if (type==2){
			 SinglePreferences.getInstance().initSP(context).writeSpData(ConstantS.VERSION, ConstantS.version);
    	}    	    	
    	
    }
    
    
    
    public static byte[] Bitmap2Bytes(Bitmap bm){    
        ByteArrayOutputStream baos = new ByteArrayOutputStream();      
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);      
        return baos.toByteArray();    
      }  
    
    public static Bitmap Bytes2Bitmap(byte[] buff){
    	Bitmap bmp=BitmapFactory.decodeByteArray(buff, 0, buff.length);//���±����Bitmap����
    	return bmp;
    }
    
    
    
    /** 
     * ʹ��AsyncTask�첽����ͼƬ 
     *  
     * @author Administrator 
     *  
     */  
    public static class AsyncImageTask extends AsyncTask<String, Integer, Uri> {  
        private ImageView imageView;  
        private File cache;
  
        public AsyncImageTask(ImageView imageView,File cache) {  
            this.imageView = imageView;
            this.cache=cache;
        }  
  
        protected Uri doInBackground(String... params) {// ���߳���ִ�е�  
            try {  
            	System.out.println("���û�ȡurlͼƬ��URL �ǣ�"+params[0]);
                return WxcHttpConnect.getImage(params[0], cache);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return null;  
        }  
  
        protected void onPostExecute(Uri result) {// ���������߳�  
            if (result != null && imageView != null) { 
        		System.out.println("��ȡ��ͼƬ��uri");
                imageView.setImageURI(result);  
            }else{
        		System.out.println("û�л�ȡ��ͼƬ��uri");            	
            }
        }  
    }  
            
	    
    //===============================================================================    
    
    
    public static void invokeAlertDlg111(Context context,String title,String content){
		AlertDialog dlg= new AlertDialog.Builder(context)
		.setIcon(android.R.drawable.ic_dialog_info)
		.setTitle(title).setMessage(content).setCancelable(true).
		setPositiveButton("ȷ��",new MyDialogOnclick()).show();	  		
    }
    
   static class MyDialogOnclick implements android.content.DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int arg1) {
			// TODO Auto-generated method stub
		    dialog.dismiss();		    
		}
		
	}	
    
}

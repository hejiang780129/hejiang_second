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
	 * 小数 四舍五入 
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
	 * 字符串分割
	 */
	
	public static String[] split(String str, String regex) 	
	{ 		// 存放分割后的字符串 		
		    String newStr[] = new String[str.length()]; 		
		    // 临时存放字符串数组的一个元素 		
		    String temp = null; 		
		    // 复制存放分割后的字符串 		
		    String[] result = null; 		
		    // 分隔符所在的下一个索引和当前索引 		
		    int start = 0, end = 0; 		
		    // 字符串数组的索引 		
		    int index = 0; 		 		
		    /** 遍历字符串的每一个字符 */ 		
		    for (int i = 0; i < str.length(); i++) 		{
		    	temp = str.substring(i, i + 1); // 截取一个字符 			
		    	// 判断截取的字符是否为分隔符 			
		    	if (temp.equals(regex)) 			{
		    		temp = null; 				
		    		end = i; // 当前的分隔符的索引(字符串截取的最后位置) 				
		    		newStr[index] = str.substring(start, end); // 截取字符串并赋值给字符串数组 				
		    		index++;  // 字符串数组索引加1 				
		    		start = end + 1; // 当前的分隔符的索引后一索引(字符串截取的开始位置) 
		    		} 		
		    	}
		    
		      // 最后一个分隔符的索引不等于字符串长度 
				if (str.lastIndexOf(regex) != str.length()) 		{ 
					newStr[index] = str.substring(start, str.length());
					index++; 
					} 	
				result = new String[index]; 	
				// 从指定源数组中复制一个数组，复制从指定的位置开始，到目标数组的指定位置结束 	
				System.arraycopy(newStr, 0, result, 0, index); 
				return result; 
	}

    /**
     * 取当前时间
     */
	
	public static String getCurDate(){
		
		 SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd");   
	     String   date   =   sDateFormat.format(new   java.util.Date());
		 return date;
	}
	
	
	 /**
     * 取当前时间
     */
	
	public static String getCurShortDate(){
		
		 SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyMMddHHmm");   
	     String   date   =   sDateFormat.format(new   java.util.Date());
		 return date;
	}
	
	public static String getCurDate_long(){
		
		 SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy年MM月dd日");   
	     String   date   =   sDateFormat.format(new   java.util.Date());
		 return date;
	}
	
	
	public static String getCurDateTime(){
		
		 SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("dd日 HH时:mm分:ss秒");   
	     String   date   =   sDateFormat.format(new   java.util.Date());
		 return date;
	}
	
	
	/**
	 * 短暂显示Toast消息
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
	 * 根据手机分辨率从dp转成px
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
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f) - 15;
	}

	/**
	 * 获取手机状态栏高度
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
	 * 判断手机号码*/
	public static boolean isMobileNO(String mobiles){
		
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher matcher = pattern.matcher(mobiles);  
		
		return matcher.matches();
		
	}
	
	
	/**
	 * 用Toast显示信息
	 * 
	 * @param context
	 *            上下文
	 * @param text
	 *            显示文本
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
	 * 显示对话框(确定则退出程序)
	 * 
	 * @param context
	 *            上下文
	 * @param title
	 *            标题
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
	
	/* 定义一个倒计时的内部类 */
	public static class TimeCount extends CountDownTimer {
	private View view;
	private Button bt;
	private EditText edt;
	private TextView tv;
	private String title;
	public TimeCount(long millisInFuture, long countDownInterval,View view,String title) {
	super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
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
	public void onFinish() {//计时完毕时触发
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
	public void onTick(long millisUntilFinished){//计时过程显示
			if (view instanceof Button){
			bt.setText(millisUntilFinished /1000+"秒");
			bt.setClickable(false);
		}else if(view instanceof EditText){
			edt.setText(millisUntilFinished /1000+"秒");
			edt.setClickable(false);
			
		}else if(view instanceof TextView){
			tv.setText(millisUntilFinished /1000+"秒");
			tv.setClickable(false);
		}
		
	}
	}
	

	/** 
	    * 计算两个日期型的时间相差多少时间 
	    * @param startDate  开始日期 
	    * @param endDate    结束日期 
	      * @return 
	    */  
	    public  String twoDateDistance(Date startDate,Date endDate){  
	          
	        if(startDate == null ||endDate == null){  
	            return null;  
	        }  
	        long timeLong = endDate.getTime() - startDate.getTime();  
	        if (timeLong<60*1000)  
	            return timeLong/1000 + "秒前";  
	        else if (timeLong<60*60*1000){  
	            timeLong = timeLong/1000 /60;  
	            return timeLong + "分钟前";  
	        }  
	        else if (timeLong<60*60*24*1000){  
	            timeLong = timeLong/60/60/1000;  
	            return timeLong+"小时前";  
	        }  
	        else if (timeLong<60*60*24*1000*7){  
	            timeLong = timeLong/1000/ 60 / 60 / 24;  
	            return timeLong + "天前";  
	        }  
	        else if (timeLong<60*60*24*1000*7*4){  
	            timeLong = timeLong/1000/ 60 / 60 / 24/7;  
	            return timeLong + "周前";  
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
		indexof=city.indexOf("市");
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
	 * 异步登录程序
	
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
				//Toast.makeText(WxcLoginActivity.this, "登录中断", 3000).show();				
			}
			return null;
		}
		protected void onPostExecute(Integer result) {
			System.out.println("============开始执行异步登录===============");
			loginToServer(context,handler,username,password);
		}
	}
	
	
	/**
	 * 异步取系统信息
	
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
					                                  						
			//================================异步调用====================================================
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
				//取系统信息：积分活动信息、代金券活动信息、系统信息、系统版本
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
	                	System.out.println("获取系统信息失败=======================");
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
		        		//获取请求服务器的地址
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
	 * 向服务器 请求登录
	 * @param name 用户名
	 * @param password 密码
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
			//向服务器请求登录的参数
			Map<String, String> map = new HashMap<String, String>();
			// 登录(cmd=0,uid=?)
			//map.put("cmd", ConstConnectCode.LOGIN + "");
//			map.put("uid", uid);
			//获取请求服务器的地址
			String json ="";
					//URLProtocol.getJSonString(map,null);
			// code=0,uid=?,cmd=0,login=0
			//获取服务器返回的结果
			JSONObject object = new JSONObject(json);
			String code = (String) object.get("code");
			//登录成功
			if (code.equals("0")) {			
				WxcUserAccount useraccount=WxcUserAccount.useraccount;//从服务器端取得该用户的账户信息
				//ConstantS.user_account=useraccount;
				ConstantS.user_isLogin=true;
				if (handler!=null){
					msg.what = 1;
					
					handler.sendMessage(msg);
				}
				
			} else {
				if (handler!=null){
					msg.what = 2;
					msg.obj = "登录失败，请检查网络情况";
					handler.sendMessage(msg);
				}
				ConstantS.user_isLogin=false;
			}			
		}
		//如果是测试demo程序
		else{			
	                                  		         
		    //===============异步调用登录===================================================     
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

								WxcUserAccount useraccount=account;//从服务器端取得该用户的账户信息
								WxcUserAccount.useraccount=useraccount;
								ConstantS.user_isLogin=true;
								System.out.println("调用bottomfragment 方法，进行赋值=========");
								WxcBottomMyFragment bmf=WxcBottomMyFragment.getInstance();
								if (bmf!=null) bmf.setStatus();								
								System.out.println("赋值完成==============================");								
								//判断是否有信息更新，代金券、积分
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
								System.out.println("异步登录成功-----");										
					 			if (handler!=null){
				    				loadingdialog.dismiss();   	    								 				
					 				Message msg_login=new Message();
					 				System.out.println("登录成功-------");				
					 				msg_login.what=1;
					 				msg_login.obj=rtmsg;
					 				handler.sendMessage(msg_login);	
					 			}					            					            					            								
								break;
							case 1:		
			                	System.out.println("登录失败=======================");
			                	rtmsg=(String) msg.obj;
					 			if (handler!=null){
				    				loadingdialog.dismiss();					 				
					 				Message msg_login=new Message();
					 				System.out.println("登录失败-------");				
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
				        		//获取请求服务器的地址
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
			System.out.println("异步登录成功-----执行handle");				
			msg.what = 2;
			msg.obj="系统异常";
			handler.sendMessage(msg);
		}			
			
		}
	}
	
	
	  /**
	  	 * 定位相关
	  	 */
	  	public static LocationClient mLocClient;
	  	static MyLocationListenner myListener = new MyLocationListenner();
	  	static LatLng currentPt;
	  	static LatLng latlng;
	  	static GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	  	static boolean isFirstLoc = true;// 是否首次定位
	  	static Context cur_context;
	  	private static Handler handler_location_city;
	  	private static Handler handler_location_regin;
	  	private static boolean cur_isneedregin=false;
	  	private static boolean cur_isneedweather=false;
 	   public static void initLocation(Context context,boolean firstloc,boolean isneedregin,boolean isneedweather,Handler han_location_city,Handler han_location_regin,Handler han_weather){
 		   System.out.println("开始启动定位==========================");
 		   isFirstLoc=firstloc;
 		   cur_context=context;
 		   handler_weather=han_weather;
  		   handler_location_regin=han_location_regin;
  		   handler_location_city=han_location_city; 		   
 		   cur_isneedregin=isneedregin;
 		   cur_isneedweather=isneedweather;
 			// 定位初始化
 			mLocClient = new LocationClient(cur_context);
 			mLocClient.registerLocationListener(myListener);
  		   System.out.println("加载监听==========================");
 			LocationClientOption option = new LocationClientOption();
 			option.setOpenGps(true);// 打开gps
 			option.setCoorType("bd09ll"); // 设置坐标类型
 			option.setScanSpan(1000);
 			option.setIsNeedAddress(true);			
 			mLocClient.setLocOption(option);
 			mLocClient.start();
   		   System.out.println("开始定位==========================");						
 		}
	
	
 	  /**
 			 * 定位SDK监听函数
 			 */
 			public static class MyLocationListenner implements BDLocationListener {

 				@Override
 				public void onReceiveLocation(BDLocation location) {
 					//System.out.println("开始监听到位置信号");
 					// map view 销毁后不在处理新接收的位置
 					if (location == null){
 						System.out.println("locationis null");
 						ConstantS.cur_regin="";
 						ConstantS.Regin_City="";
 						isFirstLoc=true;
 						return;
 					}
 					if (isFirstLoc) {
 			   		   System.out.println("第一次定位=========================="); 						
 						latlng = new LatLng(location.getLatitude(),location.getLongitude());
 						ConstantS.cur_latlng=latlng;
 						System.out.println("当前获取的经纬度为：==========================="+ConstantS.cur_latlng+"=======================");
 						if (latlng==null){
 	 						ConstantS.cur_regin="";
 	 						ConstantS.Regin_City="";
 	 						ConstantS.cur_latlng=null; 	 						
 	 						isFirstLoc=true;
 							return;
 						}
 						isFirstLoc = false;
  			   		   System.out.println("取得定位信息=========================="); 						
 						System.out.println("取得的latlng:"+latlng.latitude+"  "+latlng.longitude);
 						System.out.println("取得的行政区域信息是："+ location.getProvince()+"  "+ location.getCity());
 						ConstantS.Regin_Province = location.getProvince();  // 获取省份信息
 						ConstantS.Regin_City= WxcPublicUtil.getRegin_City(location.getCity());  // 获取城市信息
 						ConstantS.Regin_City_ID = cityCodes.get(ConstantS.Regin_City); 						
 						System.out.println("截取后的城市信息是：："+ ConstantS.Regin_City);
 						ConstantS.Regin_District = location.getDistrict(); // 获取区县信息
 						if (handler_location_city!=null){
 							Message msg = new Message();
 							msg.what = 1;
 							msg.obj = ConstantS.Regin_City;							
 							handler_location_city.sendMessage(msg); 							
 						}
 						

 						if (cur_isneedweather){ 						
		 				   if (ConstantS.weather_weather==null||ConstantS.weather_weather.equalsIgnoreCase("")){
		 						System.out.println("需调用取天气信息");
		 					    requestWeather(ConstantS.Regin_City,handler_weather);
		 					}
 						}
 						
 						if (cur_isneedregin){
 	 			   		   System.out.println("开始调用地址解析=========================="); 							
 							System.out.println("需调用地址信息=========="); 							
 	 						geoCoder(latlng); 							
 						}
 					}
 				}

 				public void onReceivePoi(BDLocation poiLocation) {
 				}
 			}
 			
 			
 			private static void geoCoder(LatLng ptCenter){
 				// 初始化搜索模块，注册事件监听
 				mSearch = GeoCoder.newInstance();
 				mSearch.setOnGetGeoCodeResultListener(new MyGeoCoderListener());
 				// 反Geo搜索
 				mSearch.reverseGeoCode(new ReverseGeoCodeOption()
 						.location(ptCenter));
 				System.out.println("调用饭编码获取当前地址======================="); 				
 			}
 			
 			/**
 			 * 地理编码监听函数
 			 */
 			public static class  MyGeoCoderListener implements  OnGetGeoCoderResultListener{

 				@Override
 				public void onGetGeoCodeResult(GeoCodeResult arg0) {
 					// TODO Auto-generated method stub
 					
 				}

 				@Override
 				public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
 					// TODO Auto-generated method stub
 					System.out.println("开始获取地址信息。。。。。。。。。。。。");
 					if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                        System.out.println("未找出地址结果");
                        ConstantS.cur_regin="";
 						return;
 					}						
 				    System.out.println("取得cur_regin信息=======================");
 					ConstantS.cur_regin=result.getAddress();
 				    System.out.println("取得的cur_regin是：======================="+ConstantS.cur_regin); 					
						if (handler_location_regin!=null){
		 				    System.out.println("调用locatoion_regin handle");							
 							Message msg = new Message();
 							msg.what = 1;
 							msg.obj =ConstantS.cur_regin;							
 							handler_location_regin.sendMessage(msg);
 		 					System.out.println("取得的当前地址是：。。。。。。。。。。。。"+ConstantS.cur_regin+"=============="); 							
 						}else{
 		 				    System.out.println("无需进行regin handle======================="); 							
 						}
 					
 				}
 				
 			}
 		    
	
 			
 		   /**
 			 * 以下是天气相关的程序
 			 */
 			
 			/***************************************************************************************
 			 * 注意在读入txt的时候是UTF-8，自己看好自己的txt文本格式，在另存为就可以看出来。
 			 */
 			private static Map<String,String> cityCodes;	//根据城市信息索引自己的code
 			private static List<String> citys;				//给城市做数据源	
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
 					System.out.println("加载行政区域代码完成");
 				} catch (IOException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
 			}
 			
 			
 			/**************************************************************************************
 			 * 获取http://m.weather.com.cn/data/101091106.html上面的数据
 			 * 其中101091106为城市代码，上面我已经把城市代码做了修改，去除了空行，格式为UTF-8
 			 * 每次只是运行一次线程，然后加入主线程回收
 			 * @param city_str
 			 * @throws JSONException 
 			 * @throws Exception 
 			 * @throws ClientProtocolException 
 			 */
 			private static Thread thread;
 			private static Handler handler_weather;
 			
 			/************************************************************************
 			 * 在主线程中不能请求网络
 			 * 线程没有while循环只是执行一次，如果有溢出可能不能发送消息
 			 * @param city_str
 			 */
 			public static void requestWeather(final String city_str,Handler h_weather){ 
 				handler_weather=h_weather;
 				thread = new Thread(new Runnable(){
 					@Override
 					public void run() {
 						// TODO Auto-generated method stub
 						System.out.println("启动requestWeather方法。。。。。。。");
 						String code = cityCodes.get(city_str);
 						String url;
 						if("北京".equalsIgnoreCase(city_str)){
 							url = "http://m.weather.com.cn/atad/"+101010100+".html";
 						}else{
 							url = "http://m.weather.com.cn/atad/"+code+".html";
 						}
 						HttpClient client = new DefaultHttpClient();
 						HttpGet httpget = new HttpGet(url);
 						System.out.println("调用httpget方法。url 是"+url);
 						HttpResponse response;
 						String sbuf = null;
 						try {
 							response = client.execute(httpget);
 							HttpEntity httpentity = response.getEntity();
 							System.out.println("取得httpclient反馈");
 							if(httpentity != null){
 								System.out.println("取得的httpclient反馈不为空");
 								BufferedReader br = new BufferedReader(new InputStreamReader(httpentity.getContent(),"utf-8"));	
 								sbuf = br.readLine();	
 								System.out.println("读取httpclient完成");
 							}	
 							JSONObject object = new JSONObject(sbuf);
 							JSONObject data = (JSONObject) object.getJSONObject("weatherinfo");
 							 System.out.println("返回的天气数据是："+data.toString());
 							
 	 						try{					
 	 	 						System.out.println("在splash中取得天气信息");		
 	 	 						ConstantS.weather_weather=data.getString("weather1");
 	 	 						int icon = parseIcon(data.getString("img1")+".gif");					
 	 	 						ConstantS.weather_icon=icon;
 	 	 						ConstantS.weather_date=data.getString("date_y")+" "+data.getString("week");
 	 	 						ConstantS.weather_xc=data.getString("index_xc")+"洗车";					
 	 	 						ConstantS.weather_temp=data.getString("temp1");
 	 	 						ConstantS.weather_wind=data.getString("wind1");	
 	 	 						System.out.println("获取的天气信息是："+ConstantS.weather_weather+";"+ConstantS.weather_date
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
 			
 			
 			// 工具方法，该方法负责把返回的天气图标字符串，转换为程序的图片资源ID。
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
      badge1.setText(text); //显示类容
      badge1.setTextSize(textsize); //文本大小      
      badge1.setBadgePosition(position);//显示的位置.中间，还有其他位置属性
      badge1.setTextColor(Color.WHITE);  //文本颜色
      badge1.setBadgeBackgroundColor(Color.RED); //背景颜色
      badge1.setBadgeMargin(0, 0); //水平和竖直方向的间距
      return badge1;
 	}
 				
 				
 				
    public static void writeConfData(Context context,int type){   	
    	//写my
    	if (type==0){
    		if (ConstantS.user_isLogin){
			 SinglePreferences.getInstance().initSP(context).writeSpData(ConstantS.USER_SCORE, ConstantS.user_score);
			 SinglePreferences.getInstance().initSP(context).redeSpData(ConstantS.USER_VOU, ConstantS.user_vou);
    		}
			 SinglePreferences.getInstance().initSP(context).writeSpData(ConstantS.INFORMATION, ConstantS.information);    		
			 SinglePreferences.getInstance().initSP(context).writeSpData(ConstantS.USER_SCORE_BENFIT, ConstantS.user_score_benfit);			 
			 SinglePreferences.getInstance().initSP(context).redeSpData(ConstantS.USER_VOU_BENFIT, ConstantS.user_vou_benfit);
    	}
    	//写set
    	if (type==1){
			 SinglePreferences.getInstance().initSP(context).writeSpData(ConstantS.VERSION, ConstantS.version);
    	}
    	//单独写version
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
    	Bitmap bmp=BitmapFactory.decodeByteArray(buff, 0, buff.length);//重新编码出Bitmap对象
    	return bmp;
    }
    
    
    
    /** 
     * 使用AsyncTask异步加载图片 
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
  
        protected Uri doInBackground(String... params) {// 子线程中执行的  
            try {  
            	System.out.println("调用获取url图片，URL 是："+params[0]);
                return WxcHttpConnect.getImage(params[0], cache);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return null;  
        }  
  
        protected void onPostExecute(Uri result) {// 运行在主线程  
            if (result != null && imageView != null) { 
        		System.out.println("获取到图片的uri");
                imageView.setImageURI(result);  
            }else{
        		System.out.println("没有获取到图片的uri");            	
            }
        }  
    }  
            
	    
    //===============================================================================    
    
    
    public static void invokeAlertDlg111(Context context,String title,String content){
		AlertDialog dlg= new AlertDialog.Builder(context)
		.setIcon(android.R.drawable.ic_dialog_info)
		.setTitle(title).setMessage(content).setCancelable(true).
		setPositiveButton("确定",new MyDialogOnclick()).show();	  		
    }
    
   static class MyDialogOnclick implements android.content.DialogInterface.OnClickListener{
		@Override
		public void onClick(DialogInterface dialog, int arg1) {
			// TODO Auto-generated method stub
		    dialog.dismiss();		    
		}
		
	}	
    
}

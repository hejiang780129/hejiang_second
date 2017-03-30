package com.hbjr.washcheapp.config;

import java.util.HashMap;

import com.baidu.mapapi.model.LatLng;
import com.hbjr.washcheapp.WxcRegisterActivity;
import com.hbjr.washcheapp.bean.WxcUserAccount;

import android.annotation.SuppressLint;
import android.os.Environment;

@SuppressLint("SdCardPath")
public class ConstantS {

//	public static String APP_NAME = "";
//	// 数据库放入手机内存的路径
//	public static String PACKAGE_NAMES = "com.dsx.sundroid";
//	// 数据库放入手机内存的路径
//	public static String FILE_PATH = "/data/data/" + PACKAGE_NAMES + "/databases";
	
	public static int SCREEN_HEIGHT = 800;

	public static int SCREEN_WIDTH = 480;

	public static float SCREEN_DENSITY = 1.5f;
	
	public static final String PREFS_NAME = "UserInfo";
	
	
	/** http网络连接会话id */
	public static String JSESSIONID = null;
	
	//public static String SERVER_HOST = "http://wyxc.nat123.net/WyxcServerProject/servlet/JsonAction";
	
	public static String SERVER_HOST="http://123.57.81.116:8080/WyxcServerProject/servlet/JsonAction";
	
	
	public static String PIC_PATH = "mnt/sdcard/freelife";
	
	public static final int THREADPOOL_CORESIZE = 15;
	
	public static final int THREADPOOL_MAXNUM = 15;
	
	public static final int THREADPOOL_KEEPALIVETIME = 3000;// 毫秒

	public static boolean isDemo=true;
	
	public static int top_long=80;
	
	public static String phone_number="13671078962";
	
	public static final int PHOTO_NUM=5;//至允许拍5张照片
	public static final String PHOTO_PATH="/sdcard/XcdImage/";
	public static final String PHOTO_NAME_PRE="share";//账号名+“share”+数量
	
	public static HashMap cur_obj;//用于传递系统临时对象
	
	public static String Regin_Province;
	public static String Regin_City;
	public static String Regin_City_ID;	
	public static String Regin_District;
	
	
	public static String weather_weather;
	public static int weather_icon;
	public static String weather_temp;
	public static String weather_wind;
	public static String weather_date;
	public static String weather_xc;
	
	public static String cur_regin;
	public static LatLng cur_latlng;
	
	
	public static String user_name;//用户名 ；需写入本地文件
	public static final String USER_NAME="userName";//配置文件中的键值
	public static String user_pwd;//用户密码；需写入本地文件	
	public static final String USER_PWD="userPwd";//配置文件中的键值	
	public static boolean user_isLogin=false;//是否已登录，无需写入配置文件中，在用户使用本app时，如果内存中的islogin为false，则需从服务器上获取一次用户信息，置为true
	//public static WxcUserAccount user_account;//用户对象，如果用户islogin是false,则需要与服务器通讯，取得对象，并置islogin为true；
	
	
	public static final String USER_SCORE="user_score";  //用于存放当前积分，判断是否有更新
	public static int user_score;
	public static int user_score_isnew=0;	
	public static final String USER_SCORE_BENFIT="user_score_benfit";  //用于存放积分活动信息，判断是否有更新
	public static int user_score_benfit;
	public static int user_score_benfit_isnew=0;
	public static final String USER_VOU="user_vou";      //用于存放当前代金券，判断是否有更新
	public static int user_vou;
	public static int user_vou_isnew=0;	
	public static final String USER_VOU_BENFIT="user_vou_benfit";      //用于存放当前代金券活动信息，判断是否有更新
	public static int user_vou_benfit;
	public static int user_vou_benfit_isnew=0;	
	public static final String INFORMATION="information";//用于存放当前信息数量，判断是否有更新
	public static int information;
	public static int information_isnew=0;	
    public static final String VERSION="version";//用于存放当前版本
    public static String version="车主版1.0";
	public static int version_isnew=0;
	
   
	//SinglePreferences.getInstance().initSP(WxcRegisterActivity.this).writeSpData("userPwd", password);
	//SinglePreferences.getInstance().initSP(WxcRegisterActivity.this).writeSpData("userName", phone);
	
	
	
	
	
	

//	public static final String QQ_APP_ID = "101044453";
//   //人人网登陆
//	public static final String RENREN_APP_ID = "168802";
//
//	public static final String RENREN_API_KEY = "e884884ac90c4182a426444db12915bf";
//
//	public static final String RENREN_SECRET_KEY = "094de55dc157411e8a5435c6a7c134c5";
//
//	public static final String RENREN_SECRET_SCOPE = "read_user_blog read_user_photo read_user_status read_user_album "
//			+ "read_user_comment read_user_share publish_blog publish_share "
//			+ "send_notification photo_upload status_update create_album "
//			+ "publish_comment publish_feed";
//
//	// 应用的APP_KEY359683609
//	public static final String APP_KEY = "3064780259";
//	// 新浪的URL
//	public static final String REDIRECT_URL = "http://www.bdysc.com/";
//	// public static final String Sina_PREFS_NAME = "SinaUserInfo";
//
//	public static final String APP_SECRET = "06e902a88ec87d85d4bfb6489ee0ea9d";
//
//	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
//			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
//			+ "follow_app_official_microblog";
//	public static String SINA_ACCESS_TOKEN = "https://api.weibo.com/oauth2/access_token?client_id="
//			+ APP_KEY
//			+ "&client_secret="
//			+ APP_SECRET
//			+ "&grant_type=authorization_code&redirect_uri=http://deker.sinaapp.com/&code=";
//	public static String SINA_UID_TOKEN = "https://api.weibo.com/2/users/show.json?uid=";
//	public static final String SD_PATH = Environment
//			.getExternalStorageDirectory().getAbsolutePath();
//
//	public static final String BASE_PATH = SD_PATH + "/dsx/";
//
//	public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";
//
//	public static final String SHARE_FILE = BASE_PATH + "QrShareImage.png";

//	public static String MAIN_NEWS_URL="http://seecool.sinaapp.com/?cat=34";

}

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
//	// ���ݿ�����ֻ��ڴ��·��
//	public static String PACKAGE_NAMES = "com.dsx.sundroid";
//	// ���ݿ�����ֻ��ڴ��·��
//	public static String FILE_PATH = "/data/data/" + PACKAGE_NAMES + "/databases";
	
	public static int SCREEN_HEIGHT = 800;

	public static int SCREEN_WIDTH = 480;

	public static float SCREEN_DENSITY = 1.5f;
	
	public static final String PREFS_NAME = "UserInfo";
	
	
	/** http�������ӻỰid */
	public static String JSESSIONID = null;
	
	//public static String SERVER_HOST = "http://wyxc.nat123.net/WyxcServerProject/servlet/JsonAction";
	
	public static String SERVER_HOST="http://123.57.81.116:8080/WyxcServerProject/servlet/JsonAction";
	
	
	public static String PIC_PATH = "mnt/sdcard/freelife";
	
	public static final int THREADPOOL_CORESIZE = 15;
	
	public static final int THREADPOOL_MAXNUM = 15;
	
	public static final int THREADPOOL_KEEPALIVETIME = 3000;// ����

	public static boolean isDemo=true;
	
	public static int top_long=80;
	
	public static String phone_number="13671078962";
	
	public static final int PHOTO_NUM=5;//��������5����Ƭ
	public static final String PHOTO_PATH="/sdcard/XcdImage/";
	public static final String PHOTO_NAME_PRE="share";//�˺���+��share��+����
	
	public static HashMap cur_obj;//���ڴ���ϵͳ��ʱ����
	
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
	
	
	public static String user_name;//�û��� ����д�뱾���ļ�
	public static final String USER_NAME="userName";//�����ļ��еļ�ֵ
	public static String user_pwd;//�û����룻��д�뱾���ļ�	
	public static final String USER_PWD="userPwd";//�����ļ��еļ�ֵ	
	public static boolean user_isLogin=false;//�Ƿ��ѵ�¼������д�������ļ��У����û�ʹ�ñ�appʱ������ڴ��е�isloginΪfalse������ӷ������ϻ�ȡһ���û���Ϣ����Ϊtrue
	//public static WxcUserAccount user_account;//�û���������û�islogin��false,����Ҫ�������ͨѶ��ȡ�ö��󣬲���isloginΪtrue��
	
	
	public static final String USER_SCORE="user_score";  //���ڴ�ŵ�ǰ���֣��ж��Ƿ��и���
	public static int user_score;
	public static int user_score_isnew=0;	
	public static final String USER_SCORE_BENFIT="user_score_benfit";  //���ڴ�Ż��ֻ��Ϣ���ж��Ƿ��и���
	public static int user_score_benfit;
	public static int user_score_benfit_isnew=0;
	public static final String USER_VOU="user_vou";      //���ڴ�ŵ�ǰ����ȯ���ж��Ƿ��и���
	public static int user_vou;
	public static int user_vou_isnew=0;	
	public static final String USER_VOU_BENFIT="user_vou_benfit";      //���ڴ�ŵ�ǰ����ȯ���Ϣ���ж��Ƿ��и���
	public static int user_vou_benfit;
	public static int user_vou_benfit_isnew=0;	
	public static final String INFORMATION="information";//���ڴ�ŵ�ǰ��Ϣ�������ж��Ƿ��и���
	public static int information;
	public static int information_isnew=0;	
    public static final String VERSION="version";//���ڴ�ŵ�ǰ�汾
    public static String version="������1.0";
	public static int version_isnew=0;
	
   
	//SinglePreferences.getInstance().initSP(WxcRegisterActivity.this).writeSpData("userPwd", password);
	//SinglePreferences.getInstance().initSP(WxcRegisterActivity.this).writeSpData("userName", phone);
	
	
	
	
	
	

//	public static final String QQ_APP_ID = "101044453";
//   //��������½
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
//	// Ӧ�õ�APP_KEY359683609
//	public static final String APP_KEY = "3064780259";
//	// ���˵�URL
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

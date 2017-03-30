package com.hbjr.washcheapp;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.config.SinglePreferences;
import com.hbjr.washcheapp.util.WxcPublicUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

public class WxcSplashActivity extends Activity {
	
	public static final String TAG = WxcSplashActivity.class.getSimpleName();
				
    private ImageView mSplashItem_iv=null;
    		
	
	   @Override
	    protected void onCreate(Bundle savedInstanceState) {
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
	        super.onCreate(savedInstanceState);
			SDKInitializer.initialize(getApplicationContext());
	        setContentView(R.layout.activity_wxc_splash);
	        System.out.println("开始加载启动页面。。。。。。。。。。");
			WxcPublicUtil.getAssetsContent(WxcSplashActivity.this);
			WxcPublicUtil.initLocation(WxcSplashActivity.this, true,true,true,null,null,null);
			onlinelogin();
            DisplayMetrics metrics = new DisplayMetrics();//取得分辨率
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			ConstantS.SCREEN_DENSITY = metrics.density;//密度：1，1.5等
			ConstantS.SCREEN_HEIGHT = metrics.heightPixels;
			ConstantS.SCREEN_WIDTH = metrics.widthPixels;
	       	new Handler(getMainLooper());
			mSplashItem_iv=(ImageView) findViewById(R.id.splash_loading_item);
			initView();
	    }

	   
	   //异步取更新信息
	   
	   private void onlineGetInfo(){
        	System.out.println("开始异步获取服务器信息=========================");        	   
            WxcPublicUtil.FrameTask_getInfo task_getinfo=new WxcPublicUtil.FrameTask_getInfo(WxcSplashActivity.this);
            task_getinfo.execute();		    							   
			
	   }
	   
	   
	   //异步登录
	   private void onlinelogin(){ 
		  // SinglePreferences.getInstance().initSP(WxcSplashActivity.this).writeSpData(ConstantS.USER_NAME, "");		   
			ConstantS.user_name = (String) SinglePreferences.getInstance().initSP(WxcSplashActivity.this).redeSpData(ConstantS.USER_NAME, "");
			ConstantS.user_pwd= (String) SinglePreferences.getInstance().initSP(WxcSplashActivity.this).redeSpData(ConstantS.USER_PWD, "");
           if (ConstantS.user_name==null||ConstantS.user_name.equalsIgnoreCase("")||ConstantS.user_pwd==null||ConstantS.user_pwd.equalsIgnoreCase("")){
        	    System.out.println("配置文件中用户名密码为空，退出异步登录，取一次系统信息=================");
   			    onlineGetInfo();
        	    return;   
           }else{
        	System.out.println("该用户已注册过，开始执行异步登录=========================");        	   
            WxcPublicUtil.FrameTask task=new WxcPublicUtil.FrameTask(WxcSplashActivity.this, null,ConstantS.user_name, ConstantS.user_pwd)	;
            task.execute();
           }		   
	   }
	   
	  	     

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	    	getMenuInflater().inflate(R.menu.wash_che, menu);
	        return true;
	    }
	    
	    
	    //初始化界面
	    
	    public void initView(){
	    	Animation translate=AnimationUtils.loadAnimation(this, R.anim.load_splash);
	    	
	        System.out.println("加载启动动画。。。。。。。。");
	    	
	    	translate.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(WxcSplashActivity.this,WxcMainActivity.class);
					System.out.println("准备跳转到主界面。。。。。。。");
					startActivity(intent);
					System.out.println("已跳转到主界面。。。。。。。");
					overridePendingTransition(R.anim.push_left, R.anim.push_left_out);
					WxcSplashActivity.this.finish();
				}
			});
	    	
	    	mSplashItem_iv.setAnimation(translate);
	    }

		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub			
			WxcPublicUtil.mLocClient.stop();
			System.out.println("splash中停止调用定位");
			super.onDestroy();
		}	   
	    
	    
	    
	    
}

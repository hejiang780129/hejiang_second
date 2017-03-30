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
	        System.out.println("��ʼ��������ҳ�档������������������");
			WxcPublicUtil.getAssetsContent(WxcSplashActivity.this);
			WxcPublicUtil.initLocation(WxcSplashActivity.this, true,true,true,null,null,null);
			onlinelogin();
            DisplayMetrics metrics = new DisplayMetrics();//ȡ�÷ֱ���
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			ConstantS.SCREEN_DENSITY = metrics.density;//�ܶȣ�1��1.5��
			ConstantS.SCREEN_HEIGHT = metrics.heightPixels;
			ConstantS.SCREEN_WIDTH = metrics.widthPixels;
	       	new Handler(getMainLooper());
			mSplashItem_iv=(ImageView) findViewById(R.id.splash_loading_item);
			initView();
	    }

	   
	   //�첽ȡ������Ϣ
	   
	   private void onlineGetInfo(){
        	System.out.println("��ʼ�첽��ȡ��������Ϣ=========================");        	   
            WxcPublicUtil.FrameTask_getInfo task_getinfo=new WxcPublicUtil.FrameTask_getInfo(WxcSplashActivity.this);
            task_getinfo.execute();		    							   
			
	   }
	   
	   
	   //�첽��¼
	   private void onlinelogin(){ 
		  // SinglePreferences.getInstance().initSP(WxcSplashActivity.this).writeSpData(ConstantS.USER_NAME, "");		   
			ConstantS.user_name = (String) SinglePreferences.getInstance().initSP(WxcSplashActivity.this).redeSpData(ConstantS.USER_NAME, "");
			ConstantS.user_pwd= (String) SinglePreferences.getInstance().initSP(WxcSplashActivity.this).redeSpData(ConstantS.USER_PWD, "");
           if (ConstantS.user_name==null||ConstantS.user_name.equalsIgnoreCase("")||ConstantS.user_pwd==null||ConstantS.user_pwd.equalsIgnoreCase("")){
        	    System.out.println("�����ļ����û�������Ϊ�գ��˳��첽��¼��ȡһ��ϵͳ��Ϣ=================");
   			    onlineGetInfo();
        	    return;   
           }else{
        	System.out.println("���û���ע�������ʼִ���첽��¼=========================");        	   
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
	    
	    
	    //��ʼ������
	    
	    public void initView(){
	    	Animation translate=AnimationUtils.loadAnimation(this, R.anim.load_splash);
	    	
	        System.out.println("����������������������������");
	    	
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
					System.out.println("׼����ת�������档������������");
					startActivity(intent);
					System.out.println("����ת�������档������������");
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
			System.out.println("splash��ֹͣ���ö�λ");
			super.onDestroy();
		}	   
	    
	    
	    
	    
}

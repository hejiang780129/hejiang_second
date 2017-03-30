package com.hbjr.washcheapp;

import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.util.MyApplication;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WxcAboutActivity extends Activity  implements OnClickListener  {

	private LinearLayout ll_frg_my_about_feeback;
	private LinearLayout ll_frg_my_about_version;
	private LinearLayout ll_frg_my_about_tofriend;
	private LinearLayout frg_my_about_51;
	private LinearLayout ll_frg_my_about_phone;
	
	private LinearLayout mLl_parent;
	private TextView frg_my_about_version_num;
	
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_about);
		MyApplication.getInstance().addActivity(this);
		ll_frg_my_about_feeback=(LinearLayout) findViewById(R.id.ll_frg_my_about_feeback);
		ll_frg_my_about_version=(LinearLayout) findViewById(R.id.ll_frg_my_about_version);
		ll_frg_my_about_tofriend=(LinearLayout) findViewById(R.id.ll_frg_my_about_tofriend);
		frg_my_about_51=(LinearLayout) findViewById(R.id.ll_frg_my_about_51);		
		frg_my_about_version_num=(TextView) findViewById(R.id.frg_my_about_version_num);
		ll_frg_my_about_phone=(LinearLayout) findViewById(R.id.ll_frg_my_about_phone);
		ll_frg_my_about_feeback.setOnClickListener(this);
		ll_frg_my_about_version.setOnClickListener(this);
		ll_frg_my_about_tofriend.setOnClickListener(this);
		frg_my_about_51.setOnClickListener(this);
		frg_my_about_version_num.setText("V1.0");
		addTop();
	}

	private void addTop(){
		mLl_parent=(LinearLayout) findViewById(R.id.ll_my_about_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("关于");
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		ib_handle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(WxcAboutActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_about, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		switch (arg0.getId()) {
		case R.id.ll_frg_my_about_feeback:
            intent.setClass(WxcAboutActivity.this, WxcFeedBackActivity.class);
            startActivity(intent);
			break;
		case R.id.ll_frg_my_about_version:

			break;
		case R.id.ll_frg_my_about_51:
			intent.setClass(WxcAboutActivity.this, WxcAboutIntroActivity.class);
			 startActivity(intent);
			break;
								
		case R.id.ll_frg_my_about_tofriend:
//			intent.setAction(Intent.ACTION_SEND);  
//			intent.putExtra(Intent.EXTRA_TEXT, "这是一个测试的分享字符串");  
//			intent.setType("text/plain");  
//			startActivity(intent);
			
			Intent intentsend=new Intent(Intent.ACTION_SEND);   
			intentsend.setType("image/*");   
			intentsend.putExtra(Intent.EXTRA_SUBJECT, "分享");   
			intentsend.putExtra(Intent.EXTRA_TEXT, "这是一个测试的分享字符串");    
			intentsend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
	        startActivity(Intent.createChooser(intentsend, getTitle()));   
			
			break;
		case R.id.ll_frg_my_about_phone:
            String phone_number=ConstantS.phone_number;
            intent  = new Intent();   
            intent.setAction("android.intent.action.DIAL");  
            intent.setData(Uri.parse("tel:"+phone_number));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);              
            /**
             * 
             * 1）直接拨打
				Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
				startActivity(intentPhone);
				2）跳转到拨号界面
				Intent intent = newIntent(Intent.ACTION_DIAL,Uri.parse("tel:" + phoneNumber));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
             * 
             * 
             */
            
            
            
			break;		
		}
		
		
		
		
		
	}
	
	

}

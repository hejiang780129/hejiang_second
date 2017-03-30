package com.hbjr.washcheapp;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.hbjr.washcheapp.util.MyApplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WxcAboutIntroActivity extends WxcBaseActivity {

	LinearLayout mLl_parent;
	
	LinearLayout ll_wait;
	ScrollView scro_parent;
	
	GifView gifview;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.activity_wxc_about_intro);
		MyApplication.getInstance().addActivity(this);
		addTop();
		scro_parent=(ScrollView) findViewById(R.id.scro_parent);
        setLoadingView(scro_parent);	
	}
	
	
   
	@Override
	public void loadingData() {
		// TODO Auto-generated method stub
		try{
			Thread.sleep(10000);

			
			}catch(Exception e){
				e.printStackTrace();
			}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_about_intro, menu);
		return true;
	}
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_about_intro_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("¹ØÓÚ51");
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
				intent.setClass(WxcAboutIntroActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	

}

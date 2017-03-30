package com.hbjr.washcheapp;

import java.util.Timer;
import java.util.TimerTask;

import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.config.SinglePreferences;
import com.hbjr.washcheapp.fragment.WxcBottomFragment;
import com.hbjr.washcheapp.fragment.WxcBottomFragment.OnIndicateListener;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;

public class WxcMainActivity extends FragmentActivity implements OnClickListener {
	
	private SharedPreferences settings =null;
	
	public static Fragment[] mFragments;
	
	private boolean isExit=false;
	
	private TimerTask timeTask = null;
	
	private Timer timer = null;
	
	private WxcBottomFragment mIndicator;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_main);
		//将Activity加载到容器，退出程序时使用
		MyApplication.getInstance().addActivity(this);
		timer = new Timer();
		setFragmentIndicator(0);
		System.out.println("用户名为："+ConstantS.user_name);		
		}

	
	
	private void setFragmentIndicator(int whichIsDefault) {
		mFragments = new Fragment[3];
		mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_first);
		mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_my);
		mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_about);
		FragmentTransaction fts=getSupportFragmentManager().beginTransaction();
		fts.hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).show(mFragments[whichIsDefault]).commit();
		mIndicator = (WxcBottomFragment) findViewById(R.id.bottom_nav);
		mIndicator.setIndicator(whichIsDefault);
		mIndicator.setOnIndicateListener(new OnIndicateListener() {
			@Override
			public void onIndicate(View v, int which) {
				if (which==1){
					//mFragments[1].onResume();
					System.out.println("调用一次my的刷新");
				}
		FragmentTransaction fts=getSupportFragmentManager().beginTransaction();				
	    fts.setCustomAnimations(R.anim.push_left, R.anim.push_left_out);				
    	fts.hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).show(mFragments[which]).commit();
    	mIndicator.setIndicator(which);    	
        
			}
		});

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_main, menu);
		settings=getSharedPreferences(ConstantS.PREFS_NAME, Context.MODE_PRIVATE);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
			
		}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (isExit){
			WxcPublicUtil.writeConfData(WxcMainActivity.this, 0);			
			WxcPublicUtil.writeConfData(WxcMainActivity.this, 1);
			MyApplication.getInstance().exit();
		}else{
			isExit=true;
			Toast.makeText(WxcMainActivity.this,"再按一次退出51洗车", Toast.LENGTH_SHORT).show();
			timeTask = new TimerTask() {
				@Override
				public void run() {
					isExit = false;
				}
			};
			timer.schedule(timeTask, 2000);
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.exit(0);
	}
	

	
	
}

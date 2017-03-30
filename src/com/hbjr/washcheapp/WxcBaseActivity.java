package com.hbjr.washcheapp;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class WxcBaseActivity extends Activity {

	View view_content;
	GifView gifview;
	LinearLayout ll_wait;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_base, menu);
		return true;
	}
	
	
	Handler handle=new Handler() {
	     
		 @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	            case 0:
	            	ll_wait.setVisibility(View.GONE);
	        		view_content.setVisibility(View.VISIBLE); 	            	
	                break;
	            }
	        }
		
	};
	
	Runnable runable=new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//handle.obtainMessage(MSG_FAILURE).sendToTarget();// ªÒ»°Õº∆¨ ß∞‹
			//handle.obtainMessage(MSG_SUCCESS, bitmap).sendToTarget();
			loadingData();
			handle.obtainMessage(0).sendToTarget();			
		}
	};

	public void setLoadingView(View view_content){
		this.view_content=view_content;
		gifview=(GifView) findViewById(R.id.show_gif);
		gifview.setGifImage(R.drawable.loadgif);
		gifview.setGifImageType(GifImageType.SYNC_DECODER);
		ll_wait=(LinearLayout) findViewById(R.id.ll_wait);		
		ll_wait.setVisibility(View.VISIBLE);
		view_content.setVisibility(View.GONE);	
		new Thread(runable).start();
	}
	
	
	public void loadingData(){
		
	}
	
 

}

package com.hbjr.washcheapp;

import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.util.MyApplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WxcPrompActivity extends Activity {

	private TextView tv_title1;
	private TextView tv_title2;
	private TextView tv_title3;
	private TextView tv_promp_benfit;

	private LinearLayout mLl_parent;
	
	String title,content,benfit;
			
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wxc_promp);
		MyApplication.getInstance().addActivity(this);
		title=this.getIntent().getStringExtra("title");		
		content=this.getIntent().getStringExtra("content");
		benfit=this.getIntent().getStringExtra("benfit");		
		initById();
		addTop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_promp, menu);
		return true;
	}
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_promp_ok_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("信息提示");
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
				intent.setClass(WxcPrompActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}

	public void initById(){
		tv_title1 = (TextView) this.findViewById(R.id.promp_ok_title1);
		tv_title2 = (TextView) this.findViewById(R.id.promp_ok_title2);
		tv_title3 = (TextView) this.findViewById(R.id.promp_ok_title3);		
		tv_promp_benfit=(TextView) this.findViewById(R.id.promp_benfit_msg);
		
		if (title!=null&&!title.equalsIgnoreCase("")) {
			tv_title1.setText(title);
		}
		if (content!=null&&!content.equalsIgnoreCase("")) {
			tv_title3.setText(content);
		}
		if (benfit!=null&&!benfit.equalsIgnoreCase("")) {
			tv_promp_benfit.setText(benfit);
		}		
				
	}
	

}

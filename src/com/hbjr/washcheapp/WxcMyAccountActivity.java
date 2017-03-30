package com.hbjr.washcheapp;

import com.hbjr.washcheapp.bean.WxcUserAccount;
import com.hbjr.washcheapp.util.MyApplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WxcMyAccountActivity extends Activity {

	private TextView my_account_pay_date;
	private TextView my_account_pay_name_price;
	private TextView my_account_pay_voucher_str;
	private TextView my_account_pay_pay;
	private Button my_account_online_zhifubao_check;
	private Button my_account_online_weixin_check;
	private Button my_account_online_check_yinlian;
	
	private LinearLayout mLl_parent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_my_account);
		MyApplication.getInstance().addActivity(this);
		addTop();
		initById();
		initView();
	}

	public void initById(){
		my_account_pay_date=(TextView) findViewById(R.id.my_account_pay_date);
		my_account_pay_name_price=(TextView) findViewById(R.id.my_account_pay_name_price);
		my_account_pay_voucher_str=(TextView) findViewById(R.id.my_account_pay_voucher_str);
		my_account_pay_pay=(TextView) findViewById(R.id.my_account_pay_pay);
		my_account_online_zhifubao_check=(Button) findViewById(R.id.my_account_online_zhifubao_check);
		my_account_online_weixin_check=(Button) findViewById(R.id.my_account_online_weixin_check);
		my_account_online_check_yinlian=(Button) findViewById(R.id.my_account_online_check_yinlian);
	}
	
	public void initView(){
		
		my_account_pay_date.setText(WxcUserAccount.useraccount.getOrder_num()+" 次");
		my_account_pay_name_price.setText(WxcUserAccount.useraccount.getOrder_sum()+" 元");		
		my_account_pay_voucher_str.setText(WxcUserAccount.useraccount.getVou_consume()+" 元");
		my_account_pay_pay.setText(WxcUserAccount.useraccount.getOrder_Pay_online()+" 元");
		
	}
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_my_account_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("我的账户");
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
				intent.setClass(WxcMyAccountActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_my_account, menu);
		return true;
	}

}

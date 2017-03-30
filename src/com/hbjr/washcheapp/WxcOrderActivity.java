package com.hbjr.washcheapp;

import java.util.HashMap;

import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.bean.WxcUserAccount;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.config.SinglePreferences;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;

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

public class WxcOrderActivity extends Activity {

	private TextView tv_order_account;
	private TextView tv_order_voucher;
	private TextView tv_order_score;
	private TextView tv_order_xcd_name;
	private TextView tv_order_date;
	private TextView tv_order_name;
	private TextView tv_order_name_price;
	private TextView tv_order_voucher_str;
	private TextView tv_order_pay;
	private Button bt_order_do;
	
	private WxcBusInfo info;
	private HashMap price;
	
	private String str_account,str_voucher,str_score,str_xcd_name,str_date,str_name,str_price,str_pay;
	
	private LinearLayout mLl_parent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_order);
		MyApplication.getInstance().addActivity(this);
		info=(WxcBusInfo) getIntent().getSerializableExtra("info");
		price=(HashMap) getIntent().getSerializableExtra("price");
		addTop();
		initById();
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_order, menu);
		return true;
	}
	
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_order_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("用户下订单");
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
				intent.setClass(WxcOrderActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	public void initById(){
		tv_order_account = (TextView) this.findViewById(R.id.order_account);
		tv_order_voucher = (TextView) this.findViewById(R.id.order_voucher);
		tv_order_score = (TextView) this.findViewById(R.id.order_score);
		tv_order_xcd_name = (TextView) this.findViewById(R.id.order_xcd_name);
		tv_order_date = (TextView) this.findViewById(R.id.order_date);
		tv_order_name = (TextView) this.findViewById(R.id.order_name);
		tv_order_name_price = (TextView) this.findViewById(R.id.order_name_price);
		tv_order_voucher_str = (TextView) this.findViewById(R.id.order_voucher_str);
		tv_order_pay = (TextView) this.findViewById(R.id.order_pay);		
		bt_order_do = (Button) this.findViewById(R.id.order_do);
		}
	

	public void initView(){
		str_account= ConstantS.user_name;
		System.out.println("str_voucher is:"+WxcUserAccount.useraccount.getVou_balance());
		System.out.println("str_score is:"+WxcUserAccount.useraccount.getScore_sum());		
		str_voucher=WxcUserAccount.useraccount.getVou_balance();
	    str_score=WxcUserAccount.useraccount.getScore_sum();
        str_xcd_name=info.getName();
        str_date=WxcPublicUtil.getCurDate_long();
        str_name=(String)price.get("servicename");
        str_price=(String)price.get("serviceprice");
        str_pay=WxcPublicUtil.subtraction(str_price, str_voucher);
        
        tv_order_account.setText(str_account);
        tv_order_voucher.setText(str_voucher+"元");
        tv_order_score.setText(str_score+"分");
        tv_order_date.setText(str_date);
        tv_order_xcd_name.setText(str_xcd_name);
        tv_order_name.setText(str_name);
        tv_order_name_price.setText(str_price+"元");
        tv_order_voucher_str.setText(str_voucher+"元");
        tv_order_pay.setText(str_pay+"元");
        
        bt_order_do.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    Intent intent=new Intent();
			    intent.putExtra("info", info);
			    intent.putExtra("price", price);
			    intent.setClass(WxcOrderActivity.this, WxcPayActivity.class);
			    startActivity(intent);
			}
		});
	}

}

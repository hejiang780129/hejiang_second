package com.hbjr.washcheapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;



import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery.LayoutParams;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class WxcShareXcdActivity extends Activity   {

	private TextView edt_share_name;
	private TextView edt_share_address;
	private TextView edt_share_contact;
	private TextView edt_share_time;
	private TextView edt_share_service1_name;
	private TextView edt_share_service1_price;
	private TextView edt_share_service2_name;
	private TextView edt_share_service2_price;
	private TextView edt_share_service3_name;
	private TextView edt_share_service3_price;
	private TextView edt_share_introduce;
	private Button share_first_upload;
	
	private LinearLayout mLl_parent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.activity_wxc_share_xcd);
		MyApplication.getInstance().addActivity(this);
		addTop();
		initById();
		ConstantS.cur_obj=new HashMap();

	}
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_share_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ConstantS.top_long);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("分享洗车店  录入");
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
				intent.setClass(WxcShareXcdActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	
	public void initById(){
		edt_share_name=(TextView) findViewById(R.id.edt_share_name);
		edt_share_address=(TextView) findViewById(R.id.edt_share_address);
		edt_share_contact=(TextView) findViewById(R.id.edt_share_contact);
		edt_share_time=(TextView) findViewById(R.id.edt_share_time);
		edt_share_service1_name=(TextView) findViewById(R.id.edt_share_service1_name);
		edt_share_service1_price=(TextView) findViewById(R.id.edt_share_service1_price);
		edt_share_service2_name=(TextView) findViewById(R.id.edt_share_service2_name);
		edt_share_service2_price=(TextView) findViewById(R.id.edt_share_service2_price);
		edt_share_service3_name=(TextView) findViewById(R.id.edt_share_service3_name);
		edt_share_service3_price=(TextView) findViewById(R.id.edt_share_service3_price);		
		edt_share_introduce=(TextView) findViewById(R.id.edt_share_introduce);
		share_first_upload=(Button) findViewById(R.id.share_first_upload);
		share_first_upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
                String share_name,share_address,share_contact,share_time,service1_name,price1,service2_name,price2,service3_name,price3
                       ,introduce;
                String price;
				share_name=edt_share_name.getText()+"";
				share_address=edt_share_address.getText()+"";
				share_contact=edt_share_contact.getText()+"";
				share_time=edt_share_time.getText()+"";
				service1_name=edt_share_service1_name.getText()+"";
				price1=edt_share_service1_price.getText()+"";
				service2_name=edt_share_service2_name.getText()+"";
				price2=edt_share_service2_price.getText()+"";
				service3_name=edt_share_service3_name.getText()+"";
				price3=edt_share_service3_price.getText()+"";
				introduce=edt_share_introduce.getText()+"";
				if (share_name.equalsIgnoreCase("")||share_address.equalsIgnoreCase("")
						||service1_name.equalsIgnoreCase("")||price1.equalsIgnoreCase("")){
					WxcPublicUtil.showShortToast(WxcShareXcdActivity.this, "请输入必填项");	
					return;
				}
				
				WxcBusInfo xcd_info=new WxcBusInfo();
			   xcd_info.setXcd_id("999");//用户分享的洗车店id均为9开头:9XX
               xcd_info.setName(share_name);
               xcd_info.setAddress(share_address);
               xcd_info.setBh(share_time);
               xcd_info.setContactinfo(share_contact);
               price="1#"+service1_name+"#"+price1+"|"+price1+";";
               if (!service2_name.equalsIgnoreCase("")&&!price2.equalsIgnoreCase("")){
            	   price=price+"2#"+service2_name+"#"+price2+"|"+price2+";";
               }
               if (!service3_name.equalsIgnoreCase("")&&!price3.equalsIgnoreCase("")){
            	   price=price+"3#"+service3_name+"#"+price3+"|"+price3;
               }                       
               System.out.println("price is:"+price);
               xcd_info.setPrices(WxcBusInfo.setPrices((price)));
               xcd_info.setIntroduce(introduce);
			   ConstantS.cur_obj.put("xcd_info", xcd_info);
				
				Intent intent=new Intent();
				intent.setClass(WxcShareXcdActivity.this,WxcShareXcdTakePhotoActivity.class);
				startActivity(intent);
				
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_share_xcd, menu);
		return true;
	}

}

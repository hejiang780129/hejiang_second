package com.hbjr.washcheapp;

import com.baidu.navisdk.comapi.mapcontrol.MapParams.Const;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.config.ConstantS;
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


public class WxcPayResultActivity extends Activity {

	private TextView tv_title1;
	private TextView tv_title2;
	private TextView tv_title3;
	private TextView pay_benfit_msg;
	private Button bt_daohang;
	private LinearLayout mLl_parent;
	
	private String title_account;
	private String title_order_num;
	private String title_paynum;
	private String benfit_msg;
	
	WxcBusInfo info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_pay_result);
		MyApplication.getInstance().addActivity(this);
		info=(WxcBusInfo) getIntent().getSerializableExtra("info");
		title_account=this.getIntent().getStringExtra("account");
		title_order_num=this.getIntent().getStringExtra("order_num");
		title_paynum=this.getIntent().getStringExtra("paynum");		
		benfit_msg=this.getIntent().getStringExtra("benfit_msg")+"";		
		
		addTop();
		initById();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_pay_result, menu);
		return true;
	}
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_pay_ok_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("订单支付成功");
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
				intent.setClass(WxcPayResultActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}

	public void initById(){
		tv_title1 = (TextView) this.findViewById(R.id.pay_ok_title1);
		tv_title2 = (TextView) this.findViewById(R.id.pay_ok_title2);
		tv_title3 = (TextView) this.findViewById(R.id.pay_ok_title3);		
		bt_daohang = (Button) this.findViewById(R.id.pay_daohang);
		pay_benfit_msg=(TextView) this.findViewById(R.id.pay_benfit_msg);
		tv_title1.setText("账号为"+title_account+"的用户，您已支付成功。");
		tv_title2.setText("本次订单号为"+title_order_num);
		tv_title3.setText("本次订单实付款为"+title_paynum+"元");
		pay_benfit_msg.setText(benfit_msg);
	    bt_daohang.setOnClickListener(new MyListener());				
	}
	
	
	private class MyListener implements OnClickListener{   
	      public MyListener(){    
	      }  
	      @Override  
	      public void onClick(View v) {  
	          // TODO Auto-generated method stub  
	          System.out.println("触发导航去按钮，当前位置是："+ConstantS.cur_latlng);
	          Intent intent =new Intent();
	          intent.putExtra("dest_latitude", info.getLatitude());
	          intent.putExtra("dest_longitud", info.getLongitude());
	          intent.putExtra("dest_info", info.getName());
	          intent.setClass(WxcPayResultActivity.this, WxcRoutePlanActivity.class);
	          startActivity(intent);          
	      }  
	        
	  }  	
	
}

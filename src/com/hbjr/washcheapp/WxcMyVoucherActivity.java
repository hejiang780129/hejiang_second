package com.hbjr.washcheapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.adapter.MyVouListViewAdapter;
import com.hbjr.washcheapp.bean.WxcUserAccount;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.bean.WxcUserVou;
import com.hbjr.washcheapp.config.ConstConnectCode;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.net.JSONTools;
import com.hbjr.washcheapp.net.URLProtocol;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;
import com.hbjr.washcheapp.widget.LoadingDialog;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WxcMyVoucherActivity extends Activity {

	private TextView my_vou_total;
	private TextView my_vou_total_limit;
	private TextView my_vou_total_consume;
	private ListView my_vou_list;
	private LinearLayout mLl_parent;
	private ImageView my_vou_total_consume_img;
	
	LoadingDialog  loadingdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_my_voucher);
		MyApplication.getInstance().addActivity(this);
		addTop();
		initById();
		initView();
		invokeServer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_my_voucher, menu);
		return true;
	}
	
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_my_vou_head);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ConstantS.top_long);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		ImageButton ib_handle2=(ImageButton) view.findViewById(R.id.ib_handle2);
		ib_handle.setVisibility(View.GONE);
		ib_handle2.setVisibility(View.VISIBLE);
		tv.setText("我的代金券");
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		ib_handle2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(WxcMyVoucherActivity.this, WxcMyGainVouActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	public void invokeServer(){
    
		loadingdialog=new LoadingDialog(WxcMyVoucherActivity.this, LoadingDialog.TYPE_GET);
		loadingdialog.show();		
        
		final Handler handler_voulist = new Handler() {
	        public void handleMessage(Message msg) {		        	
                switch (msg.what) {
				case 0:						
	                List  listData_get= (List<WxcUserVou>) msg.obj;	
	                my_vou_list.setAdapter(new MyVouListViewAdapter(WxcMyVoucherActivity.this, listData_get));
	        		loadingdialog.dismiss();
					break;
				case 1:		
					WxcPublicUtil.showShortToast(WxcMyVoucherActivity.this,(String) msg.obj);
	        		loadingdialog.dismiss();						
					break;
				}		        	                    		        	
	        }
	  };	
    new Thread(new Runnable() {
        public void run() {
            try {
            	System.out.println("开始异步加载数据===================");
        		Map map_info = new HashMap();
        		map_info.put("fid", ConstConnectCode.FID_GET_USER_VOU_LIST);
        		map_info.put("uname",ConstantS.user_name);
        		//获取请求服务器的地址
        		String json = URLProtocol.getJSonString(map_info);
        		JSONTools.getUserVouList(ConstConnectCode.FID_GET_USER_VOU_LIST, json, handler_voulist);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
        
        
        
        
	}
	
	
	
	public void initById(){
	my_vou_total=(TextView) findViewById(R.id.my_vou_total);
	my_vou_total_limit=(TextView) findViewById(R.id.my_vou_total_limit);
	my_vou_total_consume=(TextView) findViewById(R.id.my_vou_total_consume);
	my_vou_list=(ListView) findViewById(R.id.my_vou_list);
	my_vou_total_consume_img=(ImageView) findViewById(R.id.my_vou_total_consume_img);
	} 
	
	public void initView(){
		my_vou_total.setText(WxcUserAccount.useraccount.getVou_balance()+"元");
		my_vou_total_limit.setText(WxcUserAccount.useraccount.getVou_total()+"元");
		my_vou_total_consume.setText(WxcUserAccount.useraccount.getVou_consume()+"元");
        my_vou_total_consume_img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    System.out.println("查看代金券消费历史");
			    Intent intent=new Intent();
			    intent.setClass(WxcMyVoucherActivity.this, WxcMyVoucherHisActivity.class);
			    startActivity(intent);
			}
		});
        
		
	}

}

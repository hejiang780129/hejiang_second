package com.hbjr.washcheapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.adapter.MyVouHisListViewAdapter;
import com.hbjr.washcheapp.bean.WxcUserOrder;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WxcMyVoucherHisActivity extends Activity {

	private ListView my_vou_his_list;
	private List orderlist;
	private LinearLayout mLl_parent;

	LoadingDialog loadingdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_my_voucher_his);
		MyApplication.getInstance().addActivity(this);
		my_vou_his_list=(ListView) findViewById(R.id.my_vou_his_list);		
        invokeServer();
		addTop();
		
	}

	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_vou_his_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("代金券消费历史");
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
				intent.setClass(WxcMyVoucherHisActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	public void invokeServer(){
		if (WxcUserOrder.orderinfos.size()>0){
	        orderlist=WxcUserOrder.orderinfos;
			my_vou_his_list.setAdapter(new MyVouHisListViewAdapter(this, orderlist));            
            return;
		}
		
		loadingdialog=new LoadingDialog(WxcMyVoucherHisActivity.this, LoadingDialog.TYPE_GET);
		loadingdialog.show();		
		
		final Handler handler_orderlist = new Handler() {
	        public void handleMessage(Message msg) {		        	
                switch (msg.what) {
				case 0:						
	                List  listData_get= (List<WxcUserOrder>) msg.obj;	
	                WxcUserOrder.orderinfos=listData_get;
	                WxcUserOrder.initOrderList();
	    	        orderlist=WxcUserOrder.orderinfos;
	    			my_vou_his_list.setAdapter(new MyVouHisListViewAdapter(WxcMyVoucherHisActivity.this, orderlist));
	        		loadingdialog.dismiss();
					break;
				case 1:		
					WxcPublicUtil.showShortToast(WxcMyVoucherHisActivity.this,(String) msg.obj);
	        		loadingdialog.dismiss();						
					break;
				}		        	                    		        	
	        }
	  };	
    new Thread(new Runnable() {
        public void run() {
            try {
            	System.out.println("开始异步加载订单数据===================");
        		Map map_info = new HashMap();
        		map_info.put("fid", ConstConnectCode.FID_GET_USER_ORDER_LIST);
        		map_info.put("uname",ConstantS.user_name);
        		//获取请求服务器的地址
        		String json = URLProtocol.getJSonString(map_info);
        		JSONTools.getUserOrderList(ConstConnectCode.FID_GET_USER_ORDER_LIST, json, handler_orderlist);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_my_voucher_his, menu);
		return true;
	}

}

package com.hbjr.washcheapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.adapter.ListViewAdapter;
import com.hbjr.washcheapp.adapter.ListViewCommentAdapter;
import com.hbjr.washcheapp.bean.WxcBusInfo;
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

public class WxcWyxcInfoAllComActivity extends Activity {

	private ListView myListViewComment;
	WxcBusInfo info;
	List str;
	LinearLayout mLl_parent;

	LoadingDialog  loadingdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.activity_wxc_wyxc_info_all_com);
		MyApplication.getInstance().addActivity(this);
		info=(WxcBusInfo) getIntent().getSerializableExtra("info");			
		invokeServer();
		addTop();
		myListViewComment = (ListView) this.findViewById(R.id.lw_info_pinglun);		
	}
	
	private void invokeServer(){		
		
		loadingdialog=new LoadingDialog(WxcWyxcInfoAllComActivity.this, LoadingDialog.TYPE_GET);
		loadingdialog.show();  
		final Handler handler_commlist = new Handler() {
	        public void handleMessage(Message msg) {		        	
                switch (msg.what) {
				case 0:						
	                List  listData_get= (List<HashMap>) msg.obj;
	                info.setLst_order_comment(listData_get);
                    setContent();	                
	        		loadingdialog.dismiss();
					break;
				case 1:		
					WxcPublicUtil.showShortToast(WxcWyxcInfoAllComActivity.this,(String) msg.obj);
	        		loadingdialog.dismiss();						
					break;
				}		        	                    		        	
	        }
	  };	
    new Thread(new Runnable() {
        public void run() {
            try {
            	System.out.println("开始异步加载洗车店数据===================");
        		Map map_info = new HashMap();
        		map_info.put("fid", ConstConnectCode.FID_GET_XCD_COMMENT);		
        		map_info.put("xcd_id", info.getXcd_id());
        		//获取请求服务器的地址
        		String json = URLProtocol.getJSonString(map_info);
        		JSONTools.getWxcBusInfoCommList(ConstConnectCode.FID_GET_XCD_COMMENT, json, handler_commlist);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();		
								
	//异步加载服务器数据：============================================================================================										
				
		
	}
	
	
	private void setContent(){
		str=info.getLst_order_comment();
		System.out.println("显示所有评论时str size is："+str.size());
		TextView tv=(TextView) findViewById(R.id.empty);		
		myListViewComment.setEmptyView(tv);
		myListViewComment.setAdapter(new ListViewCommentAdapter(this, str, null));		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_wyxc_info_all_com, menu);
		return true;
	}
	
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_info_addcom_head);
		mLl_parent.setVisibility(View.VISIBLE);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ConstantS.top_long);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText(info.getName());
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
				intent.setClass(WxcWyxcInfoAllComActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	

}

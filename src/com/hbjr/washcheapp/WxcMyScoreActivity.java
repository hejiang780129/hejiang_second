package com.hbjr.washcheapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.adapter.WxcScoreListViewAdapter;
import com.hbjr.washcheapp.adapter.WxcVouGainListViewAdapter;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.bean.WxcScore;
import com.hbjr.washcheapp.bean.WxcUserAccount;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.bean.WxcVouExercise;
import com.hbjr.washcheapp.config.ConstConnectCode;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class WxcMyScoreActivity extends Activity {

	private ListView my_score_get_list;
	private ListView my_score_exchange_list;
	
	
	private List score_list;
	private List score_list_0;//积分活动
	private List score_list_1;//积分兑换
	
	private LinearLayout mLl_parent;
	
	LoadingDialog loadingdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_my_score);
		MyApplication.getInstance().addActivity(this);
		my_score_get_list=(ListView) findViewById(R.id.my_score_get_list);
		my_score_exchange_list=(ListView) findViewById(R.id.my_score_exchange_list);		
		invokeServer();		
		addTop();		
	}
	
	public void initList(){
		my_score_get_list.setAdapter(new WxcScoreListViewAdapter(WxcMyScoreActivity.this, score_list_0));
		my_score_exchange_list.setAdapter(new WxcScoreListViewAdapter(WxcMyScoreActivity.this, score_list_1));	                		
		my_score_get_list.setOnItemClickListener(new MyOnItemClickListener(score_list_0));
		my_score_exchange_list.setOnItemClickListener(new MyOnItemClickListener(score_list_1));
	}

	private void invokeServer(){

		if (WxcScore.info.size()>0){
			score_list=WxcScore.info;	        		
    		score_list_0=WxcScore.info_0;
    		score_list_1=WxcScore.info_1;
			initList();
			return;
		}		
		loadingdialog=new LoadingDialog(WxcMyScoreActivity.this, LoadingDialog.TYPE_GET);
		loadingdialog.show();		
		
		final Handler handler_orderlist = new Handler() {
	        public void handleMessage(Message msg) {		        	
                switch (msg.what) {
				case 0:						
	                List  listData_get= (List<WxcScore>) msg.obj;	
	        		WxcScore.info=listData_get;	        		        	
	        		WxcScore.initScoreList();
	        		score_list=WxcScore.info;	        		
	        		score_list_0=WxcScore.info_0;
	        		score_list_1=WxcScore.info_1;		
                    initList();	        		
	        		loadingdialog.dismiss();
					break;
				case 1:		
					WxcPublicUtil.showShortToast(WxcMyScoreActivity.this,(String) msg.obj);
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
        		map_info.put("fid", ConstConnectCode.FID_GET_SCORE_LIST);
        		//获取请求服务器的地址
        		String json = URLProtocol.getJSonString(map_info);
        		JSONTools.getScoreList(ConstConnectCode.FID_GET_SCORE_LIST, json, handler_orderlist);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
								
									
		
	}
	
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_score_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		ImageButton ib_handle2=(ImageButton) view.findViewById(R.id.ib_handle2);
		ib_handle.setVisibility(View.GONE);
		ib_handle2.setVisibility(View.VISIBLE);
		tv.setText("积分活动");
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
				intent.setClass(WxcMyScoreActivity.this, WxcMyScoreMoreActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	private class MyOnItemClickListener implements OnItemClickListener{
        
		List  list;
		MyOnItemClickListener(List list){
			this.list=list;
		}
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
		   WxcScore score=(WxcScore) list.get(position);
		   System.out.println("点击活动名:"+score.getName());
		   Intent intent = new Intent(WxcMyScoreActivity.this,WxcBeneifActivity.class);
		   intent.putExtra("beneif", score);
		   startActivity(intent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_my_score, menu);
		return true;
	}

}

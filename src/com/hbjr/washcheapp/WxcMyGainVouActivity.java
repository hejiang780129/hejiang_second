package com.hbjr.washcheapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.adapter.MyVouHisListViewAdapter;
import com.hbjr.washcheapp.adapter.WxcVouGainListViewAdapter;
import com.hbjr.washcheapp.bean.WxcScore;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.bean.WxcVouExercise;
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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class WxcMyGainVouActivity extends Activity {

	private ListView my_vou_get_list;
	private List vous;
	private LinearLayout mLl_parent;
	
	LoadingDialog loadingdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_my_gain_vou);
		MyApplication.getInstance().addActivity(this);
		my_vou_get_list=(ListView) findViewById(R.id.my_vou_get_list);
		invokeServer();
		addTop();
	}

	public void initList(){
		vous=WxcVouExercise.info;
		my_vou_get_list.setAdapter(new WxcVouGainListViewAdapter(WxcMyGainVouActivity.this, vous));
		my_vou_get_list.setOnItemClickListener(new MyOnItemClickListener(vous));	        				
	}
	
	private void invokeServer(){
		
		if (WxcVouExercise.info.size()>0){
            initList();
			return;
		}
		
		loadingdialog=new LoadingDialog(WxcMyGainVouActivity.this,LoadingDialog.TYPE_GET);
		loadingdialog.show();		
		
		final Handler handler_orderlist = new Handler() {
	        public void handleMessage(Message msg) {		        	
                switch (msg.what) {
				case 0:						
	                List  listData_get= (List<WxcVouExercise>) msg.obj;	
	                WxcVouExercise.info=listData_get;
	        		vous=WxcVouExercise.info;		
                    initList();
	        		loadingdialog.dismiss();
					break;
				case 1:		
					WxcPublicUtil.showShortToast(WxcMyGainVouActivity.this,(String) msg.obj);
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
        		map_info.put("fid", ConstConnectCode.FID_GET_VOU_LIST);
        		//获取请求服务器的地址
        		String json = URLProtocol.getJSonString(map_info);
        		JSONTools.getVouList(ConstConnectCode.FID_GET_VOU_LIST, json, handler_orderlist);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
    						
	}
	
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_vou_gain_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("更多代金券");
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
				intent.setClass(WxcMyGainVouActivity.this, WxcMainActivity.class);
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
		   WxcVouExercise vou=(WxcVouExercise) list.get(position);
		   System.out.println("点击活动名:"+vou.getName());
		   Intent intent = new Intent(WxcMyGainVouActivity.this,WxcBeneifActivity.class);
		   intent.putExtra("beneif", vou);
		   startActivity(intent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_my_gain_vou, menu);
		return true;
	}

}

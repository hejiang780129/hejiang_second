package com.hbjr.washcheapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.adapter.ListViewAdapter;
import com.hbjr.washcheapp.adapter.OrderListViewExpandAdapter;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.config.ConstConnectCode;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.net.JSONTools;
import com.hbjr.washcheapp.net.URLProtocol;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;
import com.hbjr.washcheapp.widget.LoadingDialog;

import android.app.ActionBar;
import android.app.TabActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

public class WxcMyOrderTabMainActivity extends TabActivity implements OnTabChangeListener {

	LinearLayout mLl_parent;
	
	TabHost tabHost;
	TabWidget tabWidget;
	ExpandableListView expandlistview1;
	ExpandableListView expandlistview2;
	ExpandableListView expandlistview3;
	ExpandableListView expandlistview4;
	
	List<WxcUserOrder> orderlist;
	List<WxcUserOrder> orderlist_finish;
	List<WxcUserOrder> orderlist_nofinish;
	List<WxcUserOrder> orderlist_nocommence;
	
	LinearLayout ll1;
	LinearLayout ll2;
	LinearLayout ll3;
	LinearLayout ll4;
	
	WxcUserOrder order_triggerby;
	String tmp_position;
	
	LoadingDialog loadingdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		MyApplication.getInstance().addActivity(this);
		tabHost = this.getTabHost();
		tabWidget =tabHost.getTabWidget();		
	    LayoutInflater.from(this).inflate(R.layout.my_order_tab_main, tabHost.getTabContentView(), true);
	    
	    order_triggerby=(WxcUserOrder) getIntent().getSerializableExtra("order");
	    if (order_triggerby!=null){
			System.out.println("main取得的order是："+order_triggerby.getId());
         	WxcUserOrder.ReplaceOrder(WxcUserOrder.orderinfos, order_triggerby);
	    }	    
                
        RelativeLayout niTab = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tabhost_indicator, null);  
        TextView text0 = (TextView) niTab.findViewById(R.id.tab_label);  
        text0.setText("全部");   
                
        RelativeLayout niTab1 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tabhost_indicator, null);  
        TextView text1 = (TextView) niTab1.findViewById(R.id.tab_label);
        text1.setText("已完成");          
        
        RelativeLayout niTab2 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tabhost_indicator, null);  
        TextView text2 = (TextView) niTab2.findViewById(R.id.tab_label);
        text2.setText("未完成)");         
        
//        expandlistview4=(ExpandableListView) findViewById(R.id.ll4_order_expand);
//        expandlistview4.setAdapter(new OrderListViewExpandAdapter(this, orderlist_nocommence,orderlist));
        tabHost.setup();
	    tabHost.addTab(tabHost.newTabSpec("One").setIndicator(niTab).setContent(R.id.ll1_order_expand));	    
	    tabHost.addTab(tabHost.newTabSpec("Two").setIndicator(niTab1).setContent(R.id.ll2_order_expand));	    
	    tabHost.addTab(tabHost.newTabSpec("Three").setIndicator(niTab2).setContent(R.id.ll3_order_expand));
//	    tabHost.addTab(tabHost.newTabSpec("four").setIndicator("未评价("+orderlist_nocommence.size()+")").setContent(R.id.four_tab_linearlayout));
	    
//	    for (int i =0; i < tabWidget.getChildCount(); i++) {  
//	         //修改Tabhost高度和宽度
//	         tabWidget.getChildAt(i).getLayoutParams().height = 100;  
//	         tabWidget.getChildAt(i).getLayoutParams().width = 150;
////	         //修改显示字体大小
////	         TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
////	         tv.setTextSize(16);
//	         //tv.setTextColor(this.getResources().getColorStateList(android.R.color.white));
//	        }	    
	    
        tabHost.setOnTabChangedListener(this);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_view);
        
        invokeServer();
        
		addTop();
	}

	
	public void initList(){
        expandlistview1=(ExpandableListView) findViewById(R.id.ll1_order_expand);
        expandlistview1.setAdapter(new OrderListViewExpandAdapter(this, orderlist,orderlist));
        
        expandlistview2=(ExpandableListView) findViewById(R.id.ll2_order_expand);
        expandlistview2.setAdapter(new OrderListViewExpandAdapter(this, orderlist_finish,orderlist));
        
        expandlistview3=(ExpandableListView) findViewById(R.id.ll3_order_expand);
        expandlistview3.setAdapter(new OrderListViewExpandAdapter(this, orderlist_nofinish,orderlist));				
	}
	
	public void setIndText(){
		TextView tv;
		 tv= (TextView) tabWidget.getChildAt(0).findViewById(R.id.tab_label);
	     tv.setText("全部("+orderlist.size()+")");        
		 tv= (TextView) tabWidget.getChildAt(1).findViewById(R.id.tab_label);	     
	     tv.setText("已完成("+orderlist_finish.size()+")");        
		 tv= (TextView) tabWidget.getChildAt(2).findViewById(R.id.tab_label);	     
	     tv.setText("未完成("+orderlist_nofinish.size()+")");
	}
	
	public void invokeServer(){				
		
		if (WxcUserOrder.orderinfos.size()>0){
            orderlist=WxcUserOrder.orderinfos;
            orderlist_finish=WxcUserOrder.orderinfos_finish;
            orderlist_nofinish=WxcUserOrder.orderinfos_nofinish;
            initList();            
            return;
		}
		
		loadingdialog=new LoadingDialog(WxcMyOrderTabMainActivity.this, LoadingDialog.TYPE_GET);
		loadingdialog.show();		
		
		final Handler handler_orderlist = new Handler() {
	        public void handleMessage(Message msg) {		        	
                switch (msg.what) {
				case 0:						
	                List  listData_get= (List<WxcUserOrder>) msg.obj;	
	                WxcUserOrder.orderinfos=listData_get;
	                WxcUserOrder.initOrderList();
	                orderlist=WxcUserOrder.orderinfos;
	                orderlist_finish=WxcUserOrder.orderinfos_finish;
	                orderlist_nofinish=WxcUserOrder.orderinfos_nofinish;
	                setIndText();
                    initList();
	        		loadingdialog.dismiss();
					break;
				case 1:		
					WxcPublicUtil.showShortToast(WxcMyOrderTabMainActivity.this,(String) msg.obj);
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
	
	
	
     
	private void addTop(){
		TextView tv=(TextView) findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) findViewById(R.id.ib_handle);
		tv.setText("我的订单");
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
				intent.setClass(WxcMyOrderTabMainActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
//		view.setLayoutParams(lp);
//		mLl_parent.addView(view);
		
	}
	
	
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		System.out.println("触发一次onTabChanged");
		   if (tabId.equals("One")) {
                   			   
		      }
		      if (tabId.equals("Two")) {
		      }
		      if (tabId.equals("Three")) {
		      }
		
		
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}


	
}

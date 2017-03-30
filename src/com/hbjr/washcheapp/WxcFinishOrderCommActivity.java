package com.hbjr.washcheapp;

import java.util.HashMap;
import java.util.List;

import com.hbjr.washcheapp.adapter.ListViewCommentAdapter;
import com.hbjr.washcheapp.bean.WxcUserOrder;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WxcFinishOrderCommActivity extends Activity {

	private LinearLayout mLl_parent;
	private TextView my_order_finish_comm_date;
	private ListView my_order_finish_comm_list;
	private EditText my_order_finish_comm;
	private Button my_order_finish_comm_finish;
	private WxcUserOrder order;
	private String tmp_position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_finish_order_comm);
		MyApplication.getInstance().addActivity(this);
		order =(WxcUserOrder) this.getIntent().getSerializableExtra("order");
		tmp_position=this.getIntent().getStringExtra("position");
		System.out.println("追加评论时所取得的orderlist 索引为："+tmp_position);
		addTop();
		initById();
		initView();
	}
	
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_finish_order_comm_head);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ConstantS.top_long);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("追加评论");
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
				intent.setClass(WxcFinishOrderCommActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	public void initById(){
		my_order_finish_comm_date=(TextView) findViewById(R.id.my_order_finish_comm_date);
		my_order_finish_comm_list=(ListView) findViewById(R.id.my_order_finish_comm_list);
		my_order_finish_comm=(EditText) findViewById(R.id.my_order_finish_comm);
		my_order_finish_comm_finish=(Button) findViewById(R.id.my_order_finish_comm_finish);		
	}
	
	public void initView(){
		my_order_finish_comm_date.setText(order.getOrder_enddate());
		TextView tv=(TextView) findViewById(R.id.empty);
		my_order_finish_comm_list.setEmptyView(tv);
		List discusss=order.getDiscuss();
		System.out.println("追加评论中评论list的数量为："+discusss.size());
		my_order_finish_comm_list.setAdapter(new ListViewCommentAdapter(this, discusss, null));
		my_order_finish_comm_finish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
                System.out.println("点击提交评论按钮");
				String tmp_discuss=my_order_finish_comm.getText()+"";
				if (tmp_discuss.equalsIgnoreCase("")){
				 WxcPublicUtil.showShortToast(WxcFinishOrderCommActivity.this, "请输入本次评论信息");
				 return;
				}
				String rname =ConstantS.user_name;	
				
				HashMap hm=new HashMap();
				hm.put("name", rname);
	           	hm.put("comment", tmp_discuss);
				hm.put("date", WxcPublicUtil.getCurDate());
				order.getDiscuss().add(hm);
				
				//刷新订单列表
				WxcUserOrder.ReplaceOrder(WxcUserOrder.orderinfos, order);				
				
				//已完成
				if (order.getOrder_state()==0){
				WxcUserOrder.ReplaceOrder(WxcUserOrder.orderinfos_finish, order);
				}
				//未完成
				if (order.getOrder_state()==1){
				WxcUserOrder.ReplaceOrder(WxcUserOrder.orderinfos_nofinish, order);
				}
				
				
				WxcPublicUtil.showShortToast(WxcFinishOrderCommActivity.this, "本次追加评论完成");
				Intent intent=new Intent();
				intent.putExtra("order",order);
				intent.putExtra("position", tmp_position);
				intent.setClass(WxcFinishOrderCommActivity.this, WxcMyOrderTabMainActivity.class);
				startActivity(intent);
			}
		});
		
//		HashMap hm=new HashMap();
//		hm.put("name", rname);
//      hm.put("comment", tmp_discuss);
//		hm.put("date", my_order_finishdate.getText()+"");
//		order.getDiscuss().add(hm);
		
	}


	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_finish_order_comm, menu);
		return true;
	}

}

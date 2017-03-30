package com.hbjr.washcheapp;

import java.util.HashMap;
import java.util.Map;

import com.hbjr.washcheapp.WxcPayActivity.MyCheckedChangeListener;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.bean.WxcUserAccount;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.config.ConstConnectCode;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.config.SinglePreferences;
import com.hbjr.washcheapp.net.JSONPostTools;
import com.hbjr.washcheapp.net.JSONTools;
import com.hbjr.washcheapp.net.URLProtocol;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;
import com.hbjr.washcheapp.widget.LoadingDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class WxcFinishOrderActivity extends Activity {

	private TextView my_order_finishdate;
	private CheckBox my_order_good,my_order_mid,my_order_bad;
	private EditText my_order_discuss;
	private RatingBar my_order_commenc;
	private TextView my_order_score;
	private Button my_order_bt_finish;
	private LinearLayout mLl_parent;
	private WxcUserOrder order;
	
	LoadingDialog loadingdialog;
	String tmp_discuss="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_finish_order);
		MyApplication.getInstance().addActivity(this);
		order =(WxcUserOrder) this.getIntent().getSerializableExtra("order");
		System.out.println("追加评论时取得的order是："+order.getId());
		addTop();
		initById();
		initView();
	}

	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_finish_order_head);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ConstantS.top_long);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("订单评价");
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
				intent.setClass(WxcFinishOrderActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	public void initById(){
		my_order_finishdate=(TextView) findViewById(R.id.my_order_finishdate);
		my_order_good=(CheckBox) findViewById(R.id.my_order_good);
		my_order_mid=(CheckBox) findViewById(R.id.my_order_mid);
		my_order_bad=(CheckBox) findViewById(R.id.my_order_bad);
		my_order_discuss=(EditText) findViewById(R.id.my_order_discuss);
		my_order_commenc=(RatingBar) findViewById(R.id.my_order_commenc);
		my_order_score=(TextView) findViewById(R.id.my_order_score);
		my_order_bt_finish=(Button) findViewById(R.id.my_order_bt_finish);
	}
	
	public void initView(){
		my_order_finishdate.setText(WxcPublicUtil.getCurDate());
		my_order_commenc.setMax(5);
		my_order_commenc.setOnRatingBarChangeListener(new MyOnRatingChanged());
		my_order_good.setChecked(true);
		my_order_good.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
		my_order_mid.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
		my_order_bad.setOnCheckedChangeListener(new MyOnCheckedChangeListener());	
		my_order_bt_finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("触发一次button事件=============");
                int com_value=0;
				if (!my_order_good.isChecked()&&!my_order_mid.isChecked()&&!my_order_bad.isChecked()){
					WxcPublicUtil.showShortToast(WxcFinishOrderActivity.this, "请选择评价");
					return;
				}
				if (my_order_commenc.getRating()==0){
					WxcPublicUtil.showShortToast(WxcFinishOrderActivity.this, "请给本次服务评分");
					return;
				}
				
				if (my_order_good.isChecked()) com_value=0;
				if (my_order_mid.isChecked()) com_value=1;
				if (my_order_bad.isChecked()) com_value=2;
				
				order.setOrder_evaluate(com_value);
				//order.setOrder_enddate(my_order_finishdate.getText()+"");
				order.setOrder_rating(String.valueOf(my_order_commenc.getRating()));
						
			//异步提交评价信息=========================================================================
			loadingdialog=new LoadingDialog(WxcFinishOrderActivity.this, LoadingDialog.TYPE_POST);   	    			    
			loadingdialog.show();		
			HashMap hmp_return=new HashMap();
			final Handler handler_post = new Handler() {
		        public void handleMessage(Message msg) {
					String rtmsg,rcode,evadate,rat;			        	
	                switch (msg.what) {
					case 0:						
		                HashMap  hmp_get= (HashMap) msg.obj;	
		                rcode=(String) hmp_get.get("rcode");			    
		                rtmsg=(String) hmp_get.get("rtmsg");		                
		                evadate=(String) hmp_get.get("evadate");			                
		                rat=(String) hmp_get.get("rat");		                
		                //如果添加失败
		                if (!rcode.equalsIgnoreCase("0")){
							WxcPublicUtil.showShortToast(WxcFinishOrderActivity.this,rtmsg);
			        		loadingdialog.dismiss();						
			        		return;
		                }

						if (!tmp_discuss.equalsIgnoreCase("")){
							String rname =ConstantS.user_name;				
							HashMap hm=new HashMap();
							hm.put("name", rname);
				           	hm.put("comment", tmp_discuss);
							hm.put("date", evadate);
							order.getDiscuss().add(hm);
						}					
		                
						WxcBusInfo info=WxcBusInfo.isHaveInfo(order.getXcd_id());
						if (info!=null){
							if (my_order_good.isChecked()){
								info.setGood_num(info.getGood_num()+1);
							}
							if (my_order_mid.isChecked()){
								info.setMid_num(info.getMid_num()+1);
							}
							if (my_order_bad.isChecked()){
								info.setBad_num(info.getBad_num()+1);
							}				
							float tmp_rating=Float.valueOf(rat);
							info.setRating(tmp_rating);
							//替换当前list中对应洗车店对象。
							WxcBusInfo.replaceInfoInList(info);
						}
						
						    WxcUserOrder.ReplaceOrder(WxcUserOrder.orderinfos, order);		
						    if (order.getOrder_state()==0){
						      WxcUserOrder.ReplaceOrder(WxcUserOrder.orderinfos_finish, order);
						    }else{
							  WxcUserOrder.ReplaceOrder(WxcUserOrder.orderinfos_nofinish, order);						    	
						    }
						
							
    						if (rtmsg!=null&&!rtmsg.equalsIgnoreCase("")){
    						    WxcPublicUtil.showShortToast(WxcFinishOrderActivity.this, rtmsg);
      						}else{
    						    WxcPublicUtil.showShortToast(WxcFinishOrderActivity.this, "本次订单完成");            							
      						}
																					
							Intent intent=new Intent();
							intent.putExtra("order",order);
							System.out.println("追加评论完成时传回的order是："+order.getId());
							intent.setClass(WxcFinishOrderActivity.this, WxcMyOrderTabMainActivity.class);
							startActivity(intent);		                		                		                		                
		                			                			                													                				                
						break;
					case 1:		
						WxcPublicUtil.showShortToast(WxcFinishOrderActivity.this,(String) msg.obj);
		        		loadingdialog.dismiss();						
						break;
					}		        	                    		        	
		        }
		  };	
	    new Thread(new Runnable() {
	        public void run() {
	            try {
	            	System.out.println("开始异步提交数据===================");
					tmp_discuss=my_order_discuss.getText()+"";
					
	        		Map map_info = new HashMap();
	        		map_info.put("fid", ConstConnectCode.FID_PUT_ORDER_EVA);
	        		map_info.put("uname", ConstantS.user_name);
			        map_info.put("ordid",order.getId());		
			        map_info.put("eva",String.valueOf(order.getOrder_evaluate()));			        
			        map_info.put("rat",order.getOrder_rating());
			        map_info.put("dis",tmp_discuss);
	        		//获取请求服务器的地址
	        		String json = URLProtocol.postJSonString(map_info);
	        		JSONTools.getPutOrderEva(ConstConnectCode.FID_PUT_ORDER_EVA, json, handler_post);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }).start();
			
			//====================================================================================									
			}
		});
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_finish_order, menu);
		return true;
	}

	
	class MyOnCheckedChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean isCheck) {
			// TODO Auto-generated method stub
			
		switch(arg0.getId()){
			
			case R.id.my_order_good:
			    if (isCheck){
			    	my_order_mid.setChecked(false);
			    	my_order_bad.setChecked(false);
			    }
			break;
			case R.id.my_order_mid:
			    if (isCheck){
			    	my_order_good.setChecked(false);
			    	my_order_bad.setChecked(false);
			    }
			break;
			
			case R.id.my_order_bad:
			    if (isCheck){
			    	my_order_good.setChecked(false);
			    	my_order_mid.setChecked(false);
			    }
			break;			
			
		}

		
		
		}
	
	}
	
	class MyOnRatingChanged implements OnRatingBarChangeListener{

		@Override
		public void onRatingChanged(RatingBar arg0, float rating, boolean fromUser) {
			// TODO Auto-generated method stub
			System.out.println("点击rating："+rating);
			my_order_score.setText(rating+"分");
			
		}
	
		
	
	}
	
}

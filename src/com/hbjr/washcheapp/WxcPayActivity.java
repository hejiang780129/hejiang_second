package com.hbjr.washcheapp;

import java.util.HashMap;
import java.util.Map;

import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.bean.WxcUserAccount;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.bean.WxcUserVou;
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
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WxcPayActivity extends Activity {

	private TextView tv_order_account;
	private TextView tv_order_voucher;
	private TextView tv_order_score;
	private TextView tv_order_xcd_name;
	private TextView tv_order_date;
	private TextView tv_order_name;
	private TextView tv_order_name_price;
	private TextView tv_order_voucher_str;
	private TextView tv_order_pay;
	private Button bt_order_do;
	
	private CheckBox cb_zhifubao;
	private CheckBox cb_weixin;
	private CheckBox cb_yinlian;
	
	private WxcBusInfo info;
	private HashMap price;
	
	private String str_account,str_voucher,str_score,str_xcd_name,str_date,str_name,str_price,str_pay;
	
	private LinearLayout mLl_parent;	
	
	private WxcUserOrder order;
	
	LoadingDialog loadingdialog;
	int pay_vouncher=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_pay);
		MyApplication.getInstance().addActivity(this);
		info=(WxcBusInfo) getIntent().getSerializableExtra("info");
		price=(HashMap) getIntent().getSerializableExtra("price");
		addTop();
		initById();
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_pay, menu);
		return true;
	}
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_pay_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("订单在线支付");
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
				intent.setClass(WxcPayActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	public void initById(){
		tv_order_account = (TextView) this.findViewById(R.id.pay_account);
		tv_order_voucher = (TextView) this.findViewById(R.id.pay_voucher);
		tv_order_score = (TextView) this.findViewById(R.id.pay_score);
		tv_order_xcd_name = (TextView) this.findViewById(R.id.pay_xcd_name);
		tv_order_date = (TextView) this.findViewById(R.id.pay_date);
		tv_order_name = (TextView) this.findViewById(R.id.pay_name);
		tv_order_name_price = (TextView) this.findViewById(R.id.pay_name_price);
		tv_order_voucher_str = (TextView) this.findViewById(R.id.pay_voucher_str);
		tv_order_pay = (TextView) this.findViewById(R.id.pay_pay);		
		bt_order_do = (Button) this.findViewById(R.id.pay_do);
		cb_zhifubao=(CheckBox) this.findViewById(R.id.online_zhifubao_check);
		cb_weixin=(CheckBox) this.findViewById(R.id.online_weixin_check);
		cb_yinlian=(CheckBox) this.findViewById(R.id.online_check_yinlian);		
		}
	

	public void initView(){
		str_account= ConstantS.user_name;	
        str_voucher=WxcUserAccount.useraccount.getVou_balance();
        str_score=WxcUserAccount.useraccount.getScore_sum();
        str_xcd_name=info.getName();
        str_date=WxcPublicUtil.getCurDate_long();
        str_name=(String)price.get("servicename");
        str_price=(String)price.get("serviceprice");
        str_pay=WxcPublicUtil.subtraction(str_price, str_voucher);
        
        tv_order_account.setText(str_account);
        tv_order_voucher.setText(str_voucher+"元");
        tv_order_score.setText(str_score+"分");
        tv_order_date.setText(str_date);
        tv_order_xcd_name.setText(str_xcd_name);
        tv_order_name.setText(str_name);
        tv_order_name_price.setText(str_price+"元");
        tv_order_voucher_str.setText(str_voucher+"元");
        tv_order_pay.setText(str_pay+"元");
        
        cb_zhifubao.setOnCheckedChangeListener(new MyCheckedChangeListener());
        cb_weixin.setOnCheckedChangeListener(new MyCheckedChangeListener());
        cb_yinlian.setOnCheckedChangeListener(new MyCheckedChangeListener());        
        
        bt_order_do.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!cb_zhifubao.isChecked()&&!cb_weixin.isChecked()&&!cb_yinlian.isChecked()){
        	        WxcPublicUtil.showShortToast(WxcPayActivity.this, "请选择支付方式");
        	        return;
                 }
			    if (Integer.parseInt(str_price)-Integer.parseInt(str_voucher)<=0){
			        	pay_vouncher=Integer.parseInt(str_price);
			    }else{
			    	    pay_vouncher=Integer.parseInt(str_voucher);
			    }
			    
			    //异步上传订单信息==========================================================================
			    				
				loadingdialog=new LoadingDialog(WxcPayActivity.this, LoadingDialog.TYPE_POST);   	    			    
				loadingdialog.show();		
				HashMap hmp_return=new HashMap();
				final Handler handler_post = new Handler() {
			        public void handleMessage(Message msg) {
						String rtmsg,rcode,ordid,ordate;			        	
		                switch (msg.what) {
						case 0:						
			                HashMap  hmp_get= (HashMap) msg.obj;	
			                rcode=(String) hmp_get.get("rcode");			    
			                rtmsg=(String) hmp_get.get("rtmsg");
			                ordid=(String) hmp_get.get("ordid");			                
			                ordate=(String) hmp_get.get("ordate");			                
			                //如果添加失败
			                if (!rcode.equalsIgnoreCase("0")){
								WxcPublicUtil.showShortToast(WxcPayActivity.this,rtmsg);
				        		loadingdialog.dismiss();						
				        		return;
			                }
			                order.setId(ordid);
			                order.setOrder_no(ordid);
			                order.setOrder_date(ordate);
							WxcUserOrder.orderinfos.add(order);
							WxcUserOrder.orderinfos_nofinish.add(order);
							WxcUserAccount useraccount=WxcUserAccount.useraccount;
							int tmp=0;
							useraccount.setOrder_num(String.valueOf(Integer.parseInt(useraccount.getOrder_num())+1));
							useraccount.setOrder_Pay_online(String.valueOf(Integer.parseInt(useraccount.getOrder_Pay_online())+Integer.parseInt(str_pay)));
							useraccount.setOrder_sum(String.valueOf(Integer.parseInt(useraccount.getOrder_sum())+Integer.parseInt(str_price)));				
							useraccount.setVou_consume(String.valueOf(Integer.parseInt(useraccount.getVou_consume())+pay_vouncher));
											
							tmp=Integer.parseInt(useraccount.getVou_total())-Integer.parseInt(useraccount.getVou_consume());
							if (tmp<=0){
							   useraccount.setVou_balance("0");
							}else{
							   useraccount.setVou_balance(String.valueOf(tmp));	
							}														
			        		loadingdialog.dismiss();					        			        	
			        		
						    Intent intent=new Intent();
							intent.putExtra("account",str_account);
							intent.putExtra("order_num",order.getId());		
							intent.putExtra("paynum",str_pay);
							intent.putExtra("info", info);
    						if (rtmsg!=null&&!rtmsg.equalsIgnoreCase("")){
    						intent.putExtra("benfit_msg", rtmsg);
      						}							
						    intent.setClass(WxcPayActivity.this, WxcPayResultActivity.class);
						    startActivity(intent);	
			                			                			                													                				                
							break;
						case 1:		
							WxcPublicUtil.showShortToast(WxcPayActivity.this,(String) msg.obj);
			        		loadingdialog.dismiss();						
							break;
						}		        	                    		        	
			        }
			  };	
		    new Thread(new Runnable() {
		        public void run() {
		            try {
						order=new WxcUserOrder("","", 1, "","", info.getXcd_id(),info.getName(), 
								str_price,String.valueOf(pay_vouncher), str_pay,null,(String)price.get("serviceid"),
								(String)price.get("servicename"),-1,"0");		            	
		            	System.out.println("开始异步提交数据===================");
		        		Map map_info = new HashMap();
		        		map_info.put("fid", ConstConnectCode.FID_PUT_ORDER);
		        		map_info.put("uname", ConstantS.user_name);
		        		map_info.put(ConstConnectCode.FID_PUT_ORDER,JSONPostTools.createJsonString(ConstConnectCode.FID_PUT_ORDER, order));			        		
		        		//获取请求服务器的地址
		        		String json = URLProtocol.postJSonString(map_info);
		        		JSONTools.getPutOrder(ConstConnectCode.FID_PUT_ORDER, json, handler_post);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
			       
			    //====================================================================================			    			    			 			   			    			   			    			    			    			    			    				
			}
		});
        
        
	}
        
        class MyCheckedChangeListener implements OnCheckedChangeListener{

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isCheck) {
				// TODO Auto-generated method stub
			switch(arg0.getId()){
			
			case R.id.online_zhifubao_check:
			    if (isCheck){
			    	cb_yinlian.setChecked(false);
			    	cb_weixin.setChecked(false);
			    }
			break;
			case R.id.online_weixin_check:
			    if (isCheck){
			    	cb_zhifubao.setChecked(false);
			    	cb_yinlian.setChecked(false);
			    }
			break;
			
			case R.id.online_check_yinlian:
			    if (isCheck){
			    	cb_zhifubao.setChecked(false);
			    	cb_weixin.setChecked(false);
			    }
			break;
			
			
			
			}
				
				
			}
        	
        }
        
        

	

}

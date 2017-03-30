package com.hbjr.washcheapp;

import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.hbjr.washcheapp.adapter.ListViewAdapter;
import com.hbjr.washcheapp.adapter.ListViewCommentAdapter;
import com.hbjr.washcheapp.adapter.ListViewPriceAdapter;
import com.hbjr.washcheapp.adapter.WxcScoreListViewAdapter;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.bean.WxcScore;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.config.ConstConnectCode;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.config.SinglePreferences;
import com.hbjr.washcheapp.net.JSONTools;
import com.hbjr.washcheapp.net.URLProtocol;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;
import com.hbjr.washcheapp.widget.LoadingDialog;


import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class WxcWyxcInfoActivity extends Activity {
    private WxcBusInfo info;
    private List<HashMap> order_comments;
	private ImageView img_head_info;
	
	private RatingBar img_info_pingjia;//评分
	private TextView txt_pingjia_avg;//评分
	private TextView txt_good_num;
	private TextView txt_bad_num;
	private TextView txt_buy_num;
	
	private TextView edt_info_phone_num;
	private TextView edt_info_address_str;
	private TextView edt_info_date_str;
	private LinearLayout mLl_parent;
	private ListView myListView;
	private List listData;
	private ListViewPriceAdapter adapter;
	private EditText myEtComment;
	private ListViewCommentAdapter myCommentListAdapter=null;
	private TextView tv_no;
	private SharedPreferences myConfig;
	
	private LinearLayout ll_info_commence_no;
	private RelativeLayout rl_info_no_commence2;
	private RelativeLayout rl_info_no_commence3;	
	private TextView edt_info_no_commence;
	private TextView edt_info_commence1_1;
	private TextView edt_info_commence1_2;
	private TextView edt_info_commence1_3;
	private TextView edt_info_commence2_1;
	private TextView edt_info_commence2_2;
	private TextView edt_info_commence2_3;
	private TextView edt_info_commence3_1;
	private TextView edt_info_commence3_2;
	private TextView edt_info_commence3_3;
	private TextView edt_info_introduce;
	
	private EditText edt_info_add_commence;
	private TextView txt_info_pinglun;
	
	private TextView tv_commenc_num;
	
	
	/**
	 * 以下是评论的相关变量
	 */
	private ListView myListViewComment;
	/**
	 * 每层楼显示数据的集合
	 */
	private List<HashMap> str_list = null;
	private List<HashMap> str_list_tmp = null;
	/**
	 * 每层楼的背景色，引数组的大小必须与str集合的大小相同
	 */
	private int[] color = new int[] { Color.CYAN, Color.RED, Color.BLUE };
	
	File cache;
	LoadingDialog  loadingdialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_wyxc_info);
		MyApplication.getInstance().addActivity(this);
		info=(WxcBusInfo) getIntent().getSerializableExtra("info");
		
        //从服务器获取某洗车店详细信息。
//		WxcBusInfo.getAllInfo(info);
//		//取得某洗车店所有订单的数量，取得该洗车店所有的订单列表，得到购买人数，从服务器获取。模拟时自己生成
//		orders=WxcUserOrder.getCurXcdOrderList(info.getXcd_id());
//		//取得所有的订单评论列表，从服务器获取。模拟时自己生成
//		order_comments=WxcUserOrder.getCurXcdCommList(orders);
			
		
		img_head_info=(ImageView) findViewById(R.id.img_head_info);
		img_info_pingjia=(RatingBar) findViewById(R.id.img_info_pingjia);
		txt_pingjia_avg=(TextView) findViewById(R.id.txt_pingjia_avg);
		txt_good_num=(TextView) findViewById(R.id.txt_good_num);				
		txt_bad_num=(TextView) findViewById(R.id.txt_bad_num);
		txt_buy_num=(TextView) findViewById(R.id.txt_buy_num);		
		edt_info_phone_num=(TextView) findViewById(R.id.edt_info_phone_num);
		edt_info_address_str=(TextView) findViewById(R.id.edt_info_address_str);
		edt_info_date_str=(TextView) findViewById(R.id.edt_info_date_str);
		addTop();
		invokeServer();
//		initComment();
//		//initCommentList();
//		initPriceButton();
//		addComment();
	}
	

	private void invokeServer(){
		WxcBusInfo cach_info=WxcBusInfo.isHaveInfo(info.getXcd_id());
        if (cach_info!=null){     
        	info=cach_info;
            setContent();
    		initComment();
    		initPriceButton();
    		addComment();  
    		return;
        }        	        	
        	
		loadingdialog=new LoadingDialog(WxcWyxcInfoActivity.this, LoadingDialog.TYPE_GET);
		loadingdialog.show();   	    
		//异步加载服务器数据：============================================================================================				
		//开线程加载数据，通过hanlder处理		
		cache = new File(Environment.getExternalStorageDirectory(), "cache_info"); // 实例化缓存文件
		if (!cache.exists())  
            cache.mkdirs(); // 如果文件不存在，创建  
		
		final Handler handler_info = new Handler() {
		        public void handleMessage(Message msg) {		        	
                    switch (msg.what) {
					case 0:						
		                WxcBusInfo  info_get= (WxcBusInfo) msg.obj;
		                info=info_get;
		                //加入缓存列表
		                WxcBusInfo.addCacheInfoList(info);
		                setContent();
		        		initComment();
		        		initPriceButton();
		        		addComment();
		        		loadingdialog.dismiss();
						break;
					case 1:		
						WxcPublicUtil.showShortToast(WxcWyxcInfoActivity.this,(String) msg.obj);
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
            		map_info.put("fid", ConstConnectCode.FID_GET_XCD_INFO);
            		map_info.put("xcd_id", info.getXcd_id());
            		//获取请求服务器的地址
            		String json = URLProtocol.getJSonString(map_info);
            		JSONTools.getWxcBusInfo(ConstConnectCode.FID_GET_XCD_INFO, json, handler_info);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();		
									
		//异步加载服务器数据：============================================================================================	
								
		
	}
	
	private void setContent(){
		
		txt_good_num.setText("("+info.getGood_num()+")");
		//img_head_info.setImageResource(info.getImgId());		
		asyncImageLoad(img_head_info, info.getImgUrl());		
		img_info_pingjia.setRating(info.getRating());		
		txt_pingjia_avg.setText(Float.valueOf(info.getRating()).toString());		
		txt_bad_num.setText("("+info.getBad_num()+")");		
		txt_buy_num.setText(info.getBuynum());
	    edt_info_phone_num.setText(info.getContactinfo());		
		edt_info_address_str.setText(info.getAddress());		
		edt_info_date_str.setText(info.getBh());
		
		
	}
	
	
	private void initComment(){
		edt_info_add_commence=(EditText) findViewById(R.id.edt_info_add_commence);
		ll_info_commence_no=(LinearLayout) findViewById(R.id.ll_info_commence_no);
		rl_info_no_commence2=(RelativeLayout) findViewById(R.id.rl_info_no_commence2);
		rl_info_no_commence3=(RelativeLayout) findViewById(R.id.rl_info_no_commence3);		
		edt_info_no_commence=(TextView) findViewById(R.id.edt_info_no_commence);
		edt_info_commence1_1=(TextView) findViewById(R.id.edt_info_commence1_1);
		edt_info_commence1_2=(TextView) findViewById(R.id.edt_info_commence1_2);
		edt_info_commence1_3=(TextView) findViewById(R.id.edt_info_commence1_3);
		edt_info_commence2_1=(TextView) findViewById(R.id.edt_info_commence2_1);
		edt_info_commence2_2=(TextView) findViewById(R.id.edt_info_commence2_2);
		edt_info_commence2_3=(TextView) findViewById(R.id.edt_info_commence2_3);
		edt_info_commence3_1=(TextView) findViewById(R.id.edt_info_commence3_1);
		edt_info_commence3_2=(TextView) findViewById(R.id.edt_info_commence3_2);
		edt_info_commence3_3=(TextView) findViewById(R.id.edt_info_commence3_3);
		edt_info_introduce=(TextView) findViewById(R.id.edt_info_introduce);
		txt_info_pinglun=(TextView) findViewById(R.id.txt_info_pinglun);
		tv_commenc_num=(TextView) findViewById(R.id.tv_commenc_num);
		if(info.getIntroduce()==null||info.getIntroduce().equalsIgnoreCase("")){
			edt_info_introduce.setText("目前暂时没有商家简介");
		}else{
			edt_info_introduce.setText(info.getIntroduce());
		}
    
		addCommentContent();
		
		ImageView myAddComment = (ImageView) this.findViewById(R.id.img_info_add_comnence);
		myAddComment.setOnClickListener(new OnClickListener() {		    
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("触发追加评论按钮");
				if ((edt_info_add_commence.getText()+"").equalsIgnoreCase("")){
					WxcPublicUtil.showShortToast(WxcWyxcInfoActivity.this, "请输入评论内容");
					return;
				}else{
					
					//异步post服务器评论信息：					
					loadingdialog.show();		
					HashMap hmp_return=new HashMap();
					final Handler handler_post = new Handler() {
				        public void handleMessage(Message msg) {
							String rtmsg,rcode;				        	
			                switch (msg.what) {
							case 0:						
				                HashMap  hmp_get= (HashMap) msg.obj;	
				                rcode=(String) hmp_get.get("rcode");			    
				                rtmsg=(String) hmp_get.get("rtmsg");
				                //如果添加失败
				                if (!rcode.equalsIgnoreCase("0")){
									WxcPublicUtil.showShortToast(WxcWyxcInfoActivity.this,rtmsg);
					        		loadingdialog.dismiss();						
					        		return;
				                }
				                
                                				                				                
								String str_date=WxcPublicUtil.getCurDate();
								String str_name;
								if (ConstantS.user_name==null||ConstantS.user_name.equalsIgnoreCase("")){
									str_name="匿名车主";
								}else{
									str_name=ConstantS.user_name;
									System.out.println("userName is"+ConstantS.user_name);
								}					
								HashMap map = new HashMap();
				        		map.put("name",str_name);
				        		map.put("comment",edt_info_add_commence.getText().toString());
				        		map.put("date","["+str_date+"]");
				        		str_list.add(map);
				        		//刷新内存中的数据,并赋值，将评论列表放入info中	        		
				        		info.setLst_order_comment(str_list);
				        		//将info列表中的对应对象进行替换
				        		WxcBusInfo.replaceInfoInList(info);	        			        			        		
				        		addCommentContent();	        
				        		
        						if (rtmsg!=null&&!rtmsg.equalsIgnoreCase("")){         						   
         							new AlertDialog.Builder(WxcWyxcInfoActivity.this)
         							.setIcon(android.R.drawable.ic_dialog_info)
         							.setTitle("后台提示").setMessage(rtmsg).show();	
         						}else{
    								WxcPublicUtil.showShortToast(WxcWyxcInfoActivity.this, "添加评论成功");            							
         						}				        			
								edt_info_add_commence.setText("");												                				                
				        		loadingdialog.dismiss();
				        		
          						if (rtmsg!=null&&!rtmsg.equalsIgnoreCase("")){
           						  //WxcPublicUtil.invokeAlertDlg(WxcShareXcdUploadActivity.this, "后台提示", rtmsg);
  				        		   Intent intent=new Intent();
          						   intent.putExtra("benfit", rtmsg);    
                               	   intent.putExtra("title", "添加评论成功");                                                   	   
	                           	   intent.setClass(WxcWyxcInfoActivity.this, WxcPrompActivity.class);
                           	       startActivity(intent);	   
          						}            						  
					        						        						        		
								break;
							case 1:		
								WxcPublicUtil.showShortToast(WxcWyxcInfoActivity.this,(String) msg.obj);
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
			        		map_info.put("fid", ConstConnectCode.FID_PUT_XCD_COMM);
			        		map_info.put("uname", ConstantS.user_name);
			        		map_info.put("xcd_id", info.getXcd_id());			        		
			        		map_info.put("content",edt_info_add_commence.getText()+"");			        		
			        		//获取请求服务器的地址
			        		String json = URLProtocol.postJSonString(map_info);
			        		JSONTools.getPutInfoComment(ConstConnectCode.FID_PUT_XCD_COMM, json, handler_post);
			            } catch (Exception e) {
			                e.printStackTrace();
			            }
			        }
			    }).start();
					
				}
			}
		});
		
		txt_info_pinglun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("触发追加评论textview");
			   Intent intent=new Intent();
			   intent.setClass(WxcWyxcInfoActivity.this, WxcWyxcInfoAllComActivity.class);			   
			   intent.putExtra("info", info);
			   startActivity(intent);
			}
		});
	}
	
	
	public void setText(HashMap hmp,TextView tv1,TextView tv2,TextView tv3){
		tv1.setText((String)hmp.get("name"));
		tv2.setText((String) hmp.get("comment"));
		tv3.setText((String )hmp.get("date"));		
	}
	
	public void addCommentContent(){
		System.out.println("调用一次刷新评论");
		str_list_tmp=new ArrayList();
		str_list_tmp=info.getLst_order_comment();
//		//取得洗车店的所有评论
//		str_list=info.getLst_comment();
//		//取得洗车店相关所有订单的评论
//		info.setLst_order_comment(order_comments);
//		
//		str_list_tmp.addAll(str_list);
//		str_list_tmp.addAll(order_comments);		
		int comment_num=Integer.parseInt(info.getComm_num());
		System.out.println("comment num is："+str_list_tmp.size());
		int comment_list_num=str_list_tmp.size();
		tv_commenc_num.setText("("+comment_num+")");
		HashMap tmp_hmp;
		if (comment_list_num>=3){
			tmp_hmp=(HashMap)str_list_tmp.get(comment_list_num-1);
			setText(tmp_hmp, edt_info_commence1_1, edt_info_commence1_2, edt_info_commence1_3);
			tmp_hmp=(HashMap)str_list_tmp.get(comment_list_num-2);
			setText(tmp_hmp, edt_info_commence2_1, edt_info_commence2_2, edt_info_commence2_3);
			tmp_hmp=(HashMap)str_list_tmp.get(comment_list_num-3);
			setText(tmp_hmp, edt_info_commence3_1, edt_info_commence3_2, edt_info_commence3_3);						
		}else{
			if (comment_list_num==0){
				ll_info_commence_no.setVisibility(View.GONE);
				edt_info_no_commence.setVisibility(View.VISIBLE);
			}
			if (comment_list_num==1){
				tmp_hmp=(HashMap)str_list_tmp.get(0);
				setText(tmp_hmp, edt_info_commence1_1, edt_info_commence1_2, edt_info_commence1_3);
				ll_info_commence_no.setVisibility(View.VISIBLE);
				edt_info_no_commence.setVisibility(View.GONE);
			}			
			if (comment_list_num==2){
				tmp_hmp=(HashMap)str_list_tmp.get(1);
				setText(tmp_hmp, edt_info_commence1_1, edt_info_commence1_2, edt_info_commence1_3);				
				tmp_hmp=(HashMap)str_list_tmp.get(0);
				setText(tmp_hmp, edt_info_commence2_1, edt_info_commence2_2, edt_info_commence2_3);
				ll_info_commence_no.setVisibility(View.VISIBLE);
				edt_info_no_commence.setVisibility(View.GONE);
			}			
			
		}		
	}
	
	private void initCommentList(){
		//myListViewComment = (ListView) this.findViewById(R.id.lw_info_pinglun);
		//str=info.getLst_comment();
		//TextView tv=(TextView) findViewById(R.id.empty);
		//myListViewComment.setEmptyView(tv);
		//myListViewComment.setAdapter(new ListViewCommentAdapter(this, str, color));
	}
	
	private void addComment(){
//		ImageView myAddComment = (ImageView) this.findViewById(R.id.img_info_pinglun);
//		myEtComment=(EditText)  this.findViewById(R.id.edt_info_pinglun);
//		myAddComment.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				System.out.println("点击添加评论");
//				String text=myEtComment.getText().toString();
//				System.out.println("评论内容："+text);
//				if (text.equalsIgnoreCase("")){
//					return;
//				}
//				myEtComment.setText("");
//				Map<String, Object> map = new HashMap<String, Object>();
//				String str_date=WxcPublicUtil.getCurDate();
//				System.out.println("当前日期："+str_date);
//        		map.put("name", new String[] { "匿名车主"});
//        		map.put("comment",new String[] {text});
//        		map.put("date",new String[] { "["+str_date+"]"});
//        		str.add(map);
//        		info.setLst_comment(str);
//        		if (myCommentListAdapter==null){
//        			myCommentListAdapter=new ListViewCommentAdapter(WxcWyxcInfoActivity.this, str, color);
//        			myListViewComment.setAdapter(myCommentListAdapter);
//        			myListViewComment.setVisibility(View.VISIBLE);
//        			tv_no.setVisibility(View.INVISIBLE);
//        		}else{
//        			myCommentListAdapter.notifyDataSetChanged();
//        		}
//			}
//		});
	}
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_info);
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
				intent.setClass(WxcWyxcInfoActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	
//	private void initList(){
//		myListView=(ListView) findViewById(R.id.lw_info_prices);
//		listData=info.getPrices();
//		int count = listData.size();
//		System.out.println("count is  "+ count);
//		adapter=new ListViewPriceAdapter(this, listData);
//		myListView.setAdapter(adapter);
//	    myListView.setOnItemClickListener(new MyOnItemClickListener());
//	}

private class MyOnItemClickListener implements OnItemClickListener{

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
	   HashMap info=(HashMap) listData.get(position);
	   System.out.println(info.get("servicename"));
	   System.out.println(info.get("serviceprice"));

	}
}

	
	private void initPriceButton(){
		
		LinearLayout ll=(LinearLayout) findViewById(R.id.ll_info_jiage);
		List prices= info.getPrices();
		String service_name;
		String service_price;
		String service_price_old;
		HashMap price;
		int ID_BTN1 = 1;
	    int ID_BTN2 = 2;
	    int ID_BTN3 = 3;
	    int ID_BTN4 = 4;
		
		RelativeLayout.LayoutParams btParams;
		
		LinearLayout.LayoutParams btParamsLine;
		
//		int price_high=100;
//		if (prices.size()==2){
//			price_high=70;
//		}else if (prices.size()==1){
//			price_high=35;
//		}
//		LinearLayout.LayoutParams tmp_line=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,price_high);
//		ll.setLayoutParams(tmp_line);
		
		for (int i=0;i<prices.size();i++){
			LinearLayout   ll_item=new LinearLayout(this);
			btParamsLine=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			btParamsLine.weight=1;
			ll.addView(ll_item,btParamsLine);
			
			RelativeLayout rl_item=new RelativeLayout(this);
			btParams=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);	
			ll_item.addView(rl_item,btParams);
			
			price=(HashMap)prices.get(i);
			service_name=(String)price.get("servicename");
			service_price=(String)price.get("serviceprice");
			service_price_old=(String)price.get("serviceprice_old");
			
			ImageView img=new ImageView(this);
			img.setId(ID_BTN1);
			img.setImageResource(R.drawable.renminbi);
			btParams=new RelativeLayout.LayoutParams(30,30);
			btParams.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
			btParams.leftMargin=20;		
			rl_item.addView(img,btParams);
			
			TextView tv_price=new TextView(this);
			tv_price.setId(ID_BTN2);
			btParams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			btParams.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
			btParams.addRule(RelativeLayout.RIGHT_OF,ID_BTN1);
			btParams.leftMargin=5;
			tv_price.setTextSize(15);
			tv_price.setText(service_price);
			rl_item.addView(tv_price,btParams);
			
			TextView tv_price_old=new TextView(this);
			tv_price_old.setId(ID_BTN3);
			btParams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			btParams.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
			btParams.addRule(RelativeLayout.RIGHT_OF,ID_BTN2);
			btParams.leftMargin=5;
			tv_price_old.setTextSize(10);
			tv_price_old.setText(service_price_old);
			tv_price_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
			rl_item.addView(tv_price_old,btParams);
			
			
			TextView tv_pricename=new TextView(this);
			btParams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			btParams.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
			btParams.addRule(RelativeLayout.RIGHT_OF,ID_BTN3);
			btParams.leftMargin=8;
			tv_pricename.setTextSize(12);
			tv_pricename.setText(service_name);
			rl_item.addView(tv_pricename,btParams);
			
			View more_view=getLayoutInflater().inflate(R.layout.layout_button, null);
			Button bt=(Button) more_view.findViewById(R.id.bt_go);
			ViewGroup p = (ViewGroup) bt.getParent(); 
            if (p != null) { 
                p.removeAllViewsInLayout(); 
            } 
            rl_item.addView(bt);
            bt.setTag(price);
            bt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					if (!ConstantS.user_isLogin){
					    Intent intent=new Intent();
					    intent.putExtra("triggerby", "WxcWyxcInfoActivity");
					    intent.putExtra("info", info);
					    intent.setClass(WxcWyxcInfoActivity.this, WxcLoginActivity.class);
					    startActivity(intent);
					}else{
						HashMap hmp=(HashMap)arg0.getTag();
						System.out.println("rname is "+ConstantS.user_name);
						System.out.println("点击下订单:"+hmp.get("serviceid"));						
						System.out.println("点击下订单:"+hmp.get("servicename"));
						System.out.println("点击下订单："+hmp.get("serviceprice"));
					    Intent intent=new Intent();
					    intent.putExtra("info", info);
					    intent.putExtra("price", hmp);
					    intent.setClass(WxcWyxcInfoActivity.this, WxcOrderActivity.class);
					    startActivity(intent);
					}
					
				}
			});
			
		
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_wyxc_info, menu);
		return true;
	}

	
	
	private void asyncImageLoad(ImageView imageView, String path) {  
        WxcPublicUtil.AsyncImageTask asyncImageTask = new WxcPublicUtil.AsyncImageTask(imageView,cache);  
        asyncImageTask.execute(path);  
    }


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//清楚缓存文件
		if (cache!=null){
			File[] files=cache.listFiles();
			if (files!=null){
			    for (File file :files) {  
			    	   System.out.println("wxcinfo缓存中删除的文件名是："+file.getPath()+file.getName());	    	
			            file.delete();  
			        }  
			        cache.delete();
			}		        
		}
		super.onDestroy();
	} 
	
	
	
	
	
}

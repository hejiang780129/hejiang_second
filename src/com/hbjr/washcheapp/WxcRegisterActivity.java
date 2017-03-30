package com.hbjr.washcheapp;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.hbjr.washcheapp.net.JSONTools;
import com.hbjr.washcheapp.net.URLProtocol;
import com.hbjr.washcheapp.WxcLoginActivity.NewClickListener;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.bean.WxcUserAccount;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.config.ConstConnectCode;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.config.SinglePreferences;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;
import com.hbjr.washcheapp.widget.LoadingDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.LinearLayout.LayoutParams;

public class WxcRegisterActivity extends Activity {

	//private EditText  edt_name;
	private EditText  edt_phonenum;
	private EditText  edt_ver_code;
	private EditText  edt_pass;
	private EditText  edt_repass;
	private Button  get_ver_code;
	private Button  register_normal_btn;
	private LinearLayout mLl_parent;
	private WxcPublicUtil.TimeCount time;
	private Intent cur_intent;
	private LoadingDialog loadingdialog;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_register);
		MyApplication.getInstance().addActivity(this);
		cur_intent=this.getIntent();
		addTop();
		initById();
		initView();
	}

	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_regist_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ConstantS.top_long);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("用户注册");
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
				intent.setClass(WxcRegisterActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	
	private void initById(){
		//this.edt_name = (EditText) this.findViewById(R.id.edt_name);
		this.edt_phonenum = (EditText) this.findViewById(R.id.edt_phonenum);
		this.edt_ver_code = (EditText) this.findViewById(R.id.edt_ver_code);
		this.edt_pass = (EditText) this.findViewById(R.id.edt_pass);
		this.edt_repass = (EditText) this.findViewById(R.id.edt_repass);
		this.get_ver_code = (Button) this.findViewById(R.id.get_ver_code);
		this.register_normal_btn = (Button) this.findViewById(R.id.register_normal_btn);
		
	}
	
	public void initView(){
		time=new WxcPublicUtil.TimeCount(60000, 1000, get_ver_code, get_ver_code.getText().toString());
		get_ver_code.setOnClickListener(new NewClickListener());
		register_normal_btn.setOnClickListener(new NewClickListener());
	}
	
	class NewClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Context context =arg0.getContext();
			Animation shake = AnimationUtils.loadAnimation(context,R.anim.shake);
			switch (arg0.getId()) {		
			case R.id.get_ver_code:
				// 判断是否是手机
				System.out.println("点击获取验证码。。。。。。");
				boolean flag=WxcPublicUtil.isMobileNO(edt_phonenum.getText().toString());
				if (flag == false) {
					System.out.println(edt_phonenum.getText()+"不是正确的手机号码");
					WxcPublicUtil.showShortToast(WxcRegisterActivity.this, "您输入的手机号不合法");
					break;
				}
				time.start();
				//异步调取服务器接口====================================================
				
			new Thread(new Runnable() {
		        public void run() {
		            try {
		            	System.out.println("开始调用服务器生成短信验证码===================");
		        		Map map_info = new HashMap();
		        		map_info.put("fid", ConstConnectCode.FID_GETCODE);
		        		map_info.put("phone", ConstantS.user_name);			        		
		        		//获取请求服务器的地址
		        		String json = URLProtocol.postJSonString(map_info);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    }).start();
																																		
               //=================================================================												
				break;
			case R.id.register_normal_btn:
			   try{    
			   //获取用户名和密码
			   //final String name = edt_name.getText().toString().trim();
				final String phone=edt_phonenum.getText().toString().trim();
				final String password = edt_pass.getText().toString().trim();
				final String re_password = edt_repass.getText().toString().trim();
				final String ver_code = edt_ver_code.getText().toString().trim();
			    if (phone.equalsIgnoreCase("")||password.equalsIgnoreCase("")||re_password.equalsIgnoreCase("")||ver_code.equalsIgnoreCase("")){
			    	WxcPublicUtil.showShortToast(WxcRegisterActivity.this, "您输入的信息不能为空");		
			    	break;
			    }
			    flag=WxcPublicUtil.isMobileNO(edt_phonenum.getText().toString());
				if (flag == false) {
					WxcPublicUtil.showShortToast(WxcRegisterActivity.this, "您输入的手机号不合法");
					break;
				}
			    if (!password.equalsIgnoreCase(re_password)){
			    	WxcPublicUtil.showShortToast(WxcRegisterActivity.this, "您两次输入的密码不一致");
			    	break;
			    }
			    
				loadingdialog=new LoadingDialog(WxcRegisterActivity.this, LoadingDialog.TYPE_POST);
				loadingdialog.show();   
			   if (!ConstantS.isDemo){
				//请求服务器注册地址
				Map<String, String> map = new HashMap<String, String>();
				// 注册(cmd=1,name=?, password=?)
				//map.put("cmd", ConstConnectCode.REG + "");
				map.put("name", phone);
				map.put("password", password);
				//获取注册服务器的地址
				String json = URLProtocol.getJSonString(map);
				// cmd=1,code=0,uid=?
				//返回的josn对象
				JSONObject object = new JSONObject(json);
				String code = (String) object.get("code");
				//判断是否注册成功
				if (code.equals("0")) {
					WxcPublicUtil.showToast(WxcRegisterActivity.this,"注册成功");
					//注册信息 写入手机SherPreferences
					SinglePreferences.getInstance().initSP(WxcRegisterActivity.this).writeSpData(ConstantS.USER_PWD, password);
					SinglePreferences.getInstance().initSP(WxcRegisterActivity.this).writeSpData(ConstantS.USER_NAME,phone);
					SinglePreferences.getInstance().initSP(WxcRegisterActivity.this).writeSpData(ConstantS.USER_PWD,password);
					ConstantS.user_name=phone;
					ConstantS.user_pwd=password;
					//ConstantS.user_account=WxcUserAccount.useraccount;//此处需要从服务器获取用户的账户信息；
					ConstantS.user_isLogin=true;
					new Thread() {
						public void run() {
							try {
								sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} finally {
								//跳转到登录界面
								Intent intent = new Intent(WxcRegisterActivity.this,WxcLoginActivity.class);
								String triggerby=cur_intent.getStringExtra("triggerby")+"";
								intent.putExtra("triggerby", triggerby);
								if (triggerby.equalsIgnoreCase("WxcWyxcInfoActivity")){
									intent.putExtra("info", (WxcBusInfo) getIntent().getSerializableExtra("info"));
								}
								startActivity(intent);
								WxcRegisterActivity.this.finish();
							}
						};
					}.start();
				} 
				else {
					WxcPublicUtil.showToast(WxcRegisterActivity.this, "注册失败!");
				}
								
			   }
			   //如果是演示环境
			   else{				   
					loadingdialog=new LoadingDialog(WxcRegisterActivity.this, LoadingDialog.TYPE_POST);
					loadingdialog.show();   	    									   
					final Handler handler_regist = new Handler() {
			        public void handleMessage(Message msg) {		        	
	                    switch (msg.what) {
						case 0:						
			                HashMap json_return= (HashMap) msg.obj;
			                String rcode=(String) json_return.get("rcode");
			                String rtmsg=(String) json_return.get("rtmsg");
			                String version,msgnum;
			                WxcUserAccount account;
			                //如果注册成功
			             if(rcode.equalsIgnoreCase("0")){			                
			                version=(String) json_return.get("version");
			                msgnum=(String) json_return.get("msgnum");
			                account=(WxcUserAccount) json_return.get("account");	
			                loadingdialog.dismiss();
							if (rtmsg!=null&&!rtmsg.equalsIgnoreCase("")){
								WxcPublicUtil.showShortToast(WxcRegisterActivity.this,"注册成功!  "+rtmsg);
							}else{			                			                
			                    WxcPublicUtil.showToast(WxcRegisterActivity.this,"注册成功");
							}
								//注册信息 写入手机SherPreferences
								SinglePreferences.getInstance().initSP(WxcRegisterActivity.this).writeSpData(ConstantS.USER_NAME,phone);
								SinglePreferences.getInstance().initSP(WxcRegisterActivity.this).writeSpData(ConstantS.USER_PWD,password);
								ConstantS.user_name=phone;
								ConstantS.user_pwd=password;
								WxcUserAccount.useraccount=account;					
								ConstantS.user_isLogin=true;
								System.out.println("注册时写入的用户名是："+phone);
								//判断是否有信息更新，代金券、积分
								int con_user_score = (Integer) SinglePreferences.getInstance().initSP(WxcRegisterActivity.this).redeSpData(ConstantS.USER_SCORE, 0);
								int con_user_vou = (Integer) SinglePreferences.getInstance().initSP(WxcRegisterActivity.this).redeSpData(ConstantS.USER_VOU, 0);

								if (con_user_score!=Integer.valueOf(WxcUserAccount.useraccount.getScore_sum())){
									ConstantS.user_score_isnew=1;
									ConstantS.user_score=Integer.valueOf(WxcUserAccount.useraccount.getScore_sum());
								}
								if (con_user_vou!=Integer.valueOf(WxcUserAccount.useraccount.getVou_total())){
									ConstantS.user_vou_isnew=1;
									ConstantS.user_vou=Integer.valueOf(WxcUserAccount.useraccount.getVou_total());
								}
																
								//跳转到登录界面
								Intent intent = new Intent(WxcRegisterActivity.this,WxcLoginActivity.class);
								String triggerby=cur_intent.getStringExtra("triggerby")+"";
								intent.putExtra("triggerby", triggerby);
								if (triggerby.equalsIgnoreCase("WxcWyxcInfoActivity")){
									intent.putExtra("info", (WxcBusInfo) getIntent().getSerializableExtra("info"));
								}
								startActivity(intent);
								WxcRegisterActivity.this.finish();								
			                }
			                //如果注册失败
			             else{
				              loadingdialog.dismiss();			            	 
						      WxcPublicUtil.showShortToast(WxcRegisterActivity.this,rtmsg);			                	
			             }
																                			                			                			                			                			                			               			                
							break;
						case 1:
			                loadingdialog.dismiss();							
							WxcPublicUtil.showShortToast(WxcRegisterActivity.this,(String) msg.obj);						
							break;
						}		        	                    		        	
			        }
			  };	
	        new Thread(new Runnable() {
	            public void run() {
	                try {
	            		Map map_put = new HashMap();
	            		map_put.put("fid", ConstConnectCode.FID_REG);
	            		map_put.put("uname",phone);
	            		map_put.put("pwd",password);	
	            		map_put.put("code",ver_code);	            			            		
	            		//获取请求服务器的地址
	            		String json = URLProtocol.getJSonString(map_put);
	            		JSONTools.register(ConstConnectCode.FID_REG, json, handler_regist);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }).start();		
				   				   				   				   				   				   				   				   				   				   				   				   				   				   				   				   

			   }
				
			   }
			   catch (Exception e) {
					WxcPublicUtil.showToast(WxcRegisterActivity.this, "注册失败!");
				}
			    
			   loadingdialog.dismiss();
				// 与服务器数据用户数据进行比对校正的登陆,登录时调用动画效果
				arg0.startAnimation(shake);
				break;
			
			}
			
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_register, menu);
		return true;
	}

}

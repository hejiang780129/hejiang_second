package com.hbjr.washcheapp;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;




import com.baidu.navisdk.model.MainMapModel;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.config.ConstConnectCode;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.config.SinglePreferences;
import com.hbjr.washcheapp.fragment.WxcBottomMyFragment;
import com.hbjr.washcheapp.net.URLProtocol;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;
import com.hbjr.washcheapp.widget.LoadingDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;

public class WxcLoginActivity extends Activity {
	private ImageView loginLogo;
	private ImageView login_more;
	private EditText  loginaccount;
	private EditText  loginpassword;
	private Button  loginBtn;
	private ToggleButton isShowPassword;
	private TextView register;
	/**��ס���� �Զ���¼*/
	//private CheckBox rememberPwd, autoLogin;
	/**��ס����and�Զ���¼  �Ƿ��Զ���¼  �Ƿ��ס����*/
	//private boolean isLogin = false;
	/**�û��� ���� */
	private String userName, userPwd;
	
	/**������¼��ҳ��*/
	private String triggerby;
	
	private LoadingDialog loadingdialog;
	
	
	private Intent mIntent;
	private SharedPreferences settings = null;
	private LinearLayout mLl_parent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_login);
		MyApplication.getInstance().addActivity(this);
		addTop();
		initById();
		initView();				
	}

	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_login_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("�û���¼");
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
				intent.setClass(WxcLoginActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	private void initById(){
		loginaccount = (EditText) this.findViewById(R.id.loginaccount);
		loginpassword = (EditText) this.findViewById(R.id.loginpassword);
		loginBtn = (Button) this.findViewById(R.id.login);
		isShowPassword = (ToggleButton) this.findViewById(R.id.isShowPassword);
		loginBtn = (Button) this.findViewById(R.id.login);
//		rememberPwd = (CheckBox) findViewById(R.id.rememberpwd);
//		autoLogin = (CheckBox) findViewById(R.id.autologin);
		register = (TextView) this.findViewById(R.id.register);
	}
	
	public void initView(){
		// ��ȡֵ
//		isAutoLogin = this.getIntent().getBooleanExtra("isAutoLogin", false);
//		isRemPwd = this.getIntent().getBooleanExtra("isRemPwd", false);
//		System.out.println("ȡ�����Զ���¼�ı�־,��ס����ı�־��"+isAutoLogin+"  "+isRemPwd);
//		try{
//		isAutoLogin = (Boolean) SinglePreferences.getInstance().initSP(WxcLoginActivity.this).redeSpData("isAutoLogin", (Boolean) true);
//		isRemPwd = (Boolean) SinglePreferences.getInstance().initSP(WxcLoginActivity.this).redeSpData("isRemPwd",  (Boolean) true);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		    System.out.println("ȡ�����ļ���Ϣ�쳣");
//		}
//		System.out.println("�������ļ���ȡ�����Զ���¼�ı�־,��ס����ı�־��"+isAutoLogin+"  "+isRemPwd);
	    userName= ConstantS.user_name;
		userPwd = ConstantS.user_pwd;	
		triggerby=this.getIntent().getStringExtra("triggerby")+"";
		System.out.println("������¼��ҳ���ǣ�"+triggerby);

		// ��ס������Զ���¼
//		if (isAutoLogin) {
//			rememberPwd.setChecked(true);
//			autoLogin.setChecked(true);
//			isLogin = true;
//			task.execute();
//			}
		//��ס����
//		if (isRemPwd) {
//			rememberPwd.setChecked(true);
//		}
		if (userName != null) {
			loginaccount.setText(userName);
		}
		if (userPwd != null) {
			loginpassword.setText(userPwd);
		}
		register.setOnClickListener(new NewClickListener());
		loginBtn.setOnClickListener(new NewClickListener());
		isShowPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						Log.i("TAG", "���ذ�ť״̬=" + isChecked);

						if (isChecked) {
							// ����
							loginpassword.setInputType(0x90);
							loginpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
						} else {
							// ������ʾ
							loginpassword.setInputType(0x81);
							loginpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
						}
						Log.i("togglebutton", "" + isChecked);
						loginpassword.postInvalidate();
					}

				});
	}
	
	
	class NewClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Context context =arg0.getContext();
			Animation shake = AnimationUtils.loadAnimation(context,
					R.anim.shake);
			switch (arg0.getId()) {
			
			case R.id.register:
				mIntent = new Intent(WxcLoginActivity.this, WxcRegisterActivity.class);
				mIntent.putExtra("triggerby", triggerby);
				if (triggerby.equalsIgnoreCase("WxcWyxcInfoActivity")){
				mIntent.putExtra("info", (WxcBusInfo) getIntent().getSerializableExtra("info"));
				}
				startActivity(mIntent);
				break;
			case R.id.login:
				//��ȡ�������û��� ������
				final String name = loginaccount.getText().toString().trim();
				final String password = loginpassword.getText().toString().trim();									
				if (ConstantS.user_name==null||ConstantS.user_name.equalsIgnoreCase("")){
					WxcPublicUtil.showShortToast(WxcLoginActivity.this,"����δע�ᣬ����ע��");
					return;
				}						
				//�ж��Ƿ�Ϊ��
				if (name != null && password != null && !name.equals("")&& !password.equals("")) {			
					//���ע����ɺ�islogin��Ϊtrue����ֱ����ת
					if (ConstantS.user_isLogin){						
						if (!name.equalsIgnoreCase(ConstantS.user_name)||!password.equalsIgnoreCase(ConstantS.user_pwd)){
							WxcPublicUtil.showShortToast(WxcLoginActivity.this,"�û����������!");
							return;
						}else{						
							Message msg = new Message();
							msg.what = 1;
							handler_login.sendMessage(msg);
							return;
						}
					}								
					//�첽ʵ�ֵ�¼
	               WxcPublicUtil.FrameTask task=new WxcPublicUtil.FrameTask(WxcLoginActivity.this,handler_login,name,password);
				   task.execute();
				} else {
					WxcPublicUtil.showShortToast(WxcLoginActivity.this,"�û��������벻��Ϊ��!");
				}
				arg0.startAnimation(shake);
				break;
			
			}
			
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_login, menu);
		return true;
	}
	
	
		
	private Handler handler_login = new  Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			case 1:
				if (msg.obj!=null&&!((String) msg.obj).equalsIgnoreCase("")){
				   WxcPublicUtil.showShortToast(WxcLoginActivity.this,(String) msg.obj);
				}
				System.out.println("����handleMessage");
				//��ת��������
				Intent intent;
				if (triggerby.equalsIgnoreCase("WxcWyxcInfoActivity")){
				intent= new Intent(WxcLoginActivity.this,WxcWyxcInfoActivity.class);
				intent.putExtra("info",(WxcBusInfo) getIntent().getSerializableExtra("info"));
				System.out.println("��¼�ɹ�����ת����WxcWyxcInfoActivity");
				}else{
				 if (triggerby.equalsIgnoreCase("WxcBottomMyFragment")){
					 intent= new Intent(WxcLoginActivity.this,WxcMainActivity.class);
					 intent.putExtra("isbylogin", "1");
					 startActivity(intent);
				 }else{
				
					 if (triggerby.equalsIgnoreCase("WxcShareXcdUploadActivity")){
						 intent= new Intent(WxcLoginActivity.this,WxcShareXcdUploadActivity.class);
						 startActivity(intent);
					 }else{								 
				         intent= new Intent(WxcLoginActivity.this,WxcMainActivity.class);
				         System.out.println("��¼�ɹ�����ת����WxcMainActivity");  
					 }
				 }
				}
				startActivity(intent);						
				WxcLoginActivity.this.finish();		
				break;
			case 2:
				WxcPublicUtil.showShortToast(WxcLoginActivity.this, (String) msg.obj);
				break;
				
			}
		}
	};
		

}

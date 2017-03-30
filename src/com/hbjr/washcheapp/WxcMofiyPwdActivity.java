package com.hbjr.washcheapp;

import com.hbjr.washcheapp.config.ConstantS;
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
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WxcMofiyPwdActivity extends Activity {

	private EditText mod_old_pwd;
	private EditText mod_new_pwd;
	private EditText mod_confirm_pwd;
	private Button mod_login;
    LinearLayout mLl_parent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_mofiy_pwd);
	     initById();
	     addTop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_mofiy_pwd, menu);
		return true;
	}
	
	public void initById(){
		mod_old_pwd=(EditText) findViewById(R.id.mod_old_pwd);
		mod_new_pwd=(EditText) findViewById(R.id.mod_new_pwd);
		mod_confirm_pwd=(EditText) findViewById(R.id.mod_confirm_pwd);
		mod_login=(Button) findViewById(R.id.mod_login);
		mod_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (ConstantS.user_name==null){
					WxcPublicUtil.showShortToast(WxcMofiyPwdActivity.this, "您还未注册，请先注册");
					return;
				}				
				String str_old,str_new,str_conf_new;
				str_old=mod_old_pwd.getText()+"";
				str_new=mod_new_pwd.getText()+"";
				str_conf_new=mod_confirm_pwd.getText()+"";
				if (str_old.equalsIgnoreCase("")||str_new.equalsIgnoreCase("")||str_conf_new.equalsIgnoreCase("")){
					WxcPublicUtil.showShortToast(WxcMofiyPwdActivity.this, "请输入密码");
					return;
				}								
				if (!str_old.equalsIgnoreCase(ConstantS.user_pwd)){
					WxcPublicUtil.showShortToast(WxcMofiyPwdActivity.this, "原密码不正确");
					return;
				}
				if (!str_new.equalsIgnoreCase(str_conf_new)){
					WxcPublicUtil.showShortToast(WxcMofiyPwdActivity.this, "两次密码输入不一致");
					return;
				}				
				
				//此处与服务器进行交互
				ConstantS.user_pwd=str_new;
				WxcPublicUtil.showShortToast(WxcMofiyPwdActivity.this, "密码修改成功");
				finish();
			}
		});		
		

	}
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.mod_ll_modpwd_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("用户登录");
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
				intent.setClass(WxcMofiyPwdActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	

}

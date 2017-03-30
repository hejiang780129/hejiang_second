package com.hbjr.washcheapp.fragment;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.WxcAboutActivity;
import com.hbjr.washcheapp.WxcAboutIntroActivity;
import com.hbjr.washcheapp.WxcFeedBackActivity;
import com.hbjr.washcheapp.WxcLoginActivity;
import com.hbjr.washcheapp.WxcMainActivity;
import com.hbjr.washcheapp.WxcMessageActivity;
import com.hbjr.washcheapp.WxcMofiyPwdActivity;
import com.hbjr.washcheapp.WxcMyAccountActivity;
import com.hbjr.washcheapp.WxcMyGainVouActivity;
import com.hbjr.washcheapp.WxcMyOrderTabMainActivity;
import com.hbjr.washcheapp.WxcMyScoreActivity;
import com.hbjr.washcheapp.WxcMyScoreBudgetActivity;
import com.hbjr.washcheapp.WxcMyVoucherActivity;
import com.hbjr.washcheapp.WxcShareXcdUploadActivity;
import com.hbjr.washcheapp.WxcWyxcInfoActivity;
import com.hbjr.washcheapp.bean.WxcUserAccount;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.config.SinglePreferences;
import com.hbjr.washcheapp.util.WxcPublicUtil;
import com.hbjr.washcheapp.widget.BadgeView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class WxcBottomAboutFragment extends Fragment implements OnClickListener  {
	private View mParent;
	private FragmentActivity mActivity;
   
	private BadgeView bgv_version;
	private boolean isShowVersion=false;
	
	private LinearLayout ll_frg_my_about_feeback;
	private LinearLayout ll_frg_my_about_version;
	private LinearLayout ll_frg_my_about_tofriend;
	private LinearLayout frg_my_about_51;
	private LinearLayout ll_frg_my_about_phone;
	private LinearLayout ll_frg_my_about_modpwd;
	
	private LinearLayout mLl_parent;
	private TextView frg_my_about_version_num;
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mActivity=getActivity();
		mParent=getView();
		super.onActivityCreated(savedInstanceState);
		ll_frg_my_about_feeback=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_about_feeback);
		ll_frg_my_about_version=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_about_version);
		ll_frg_my_about_tofriend=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_about_tofriend);
		frg_my_about_51=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_about_51);		
		frg_my_about_version_num=(TextView) mParent.findViewById(R.id.frg_my_about_version_num);
		ll_frg_my_about_phone=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_about_phone);
		ll_frg_my_about_modpwd=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_about_modpwd);
		ll_frg_my_about_phone.setOnClickListener(this);
		ll_frg_my_about_feeback.setOnClickListener(this);
		ll_frg_my_about_version.setOnClickListener(this);
		ll_frg_my_about_tofriend.setOnClickListener(this);
		frg_my_about_51.setOnClickListener(this);
		ll_frg_my_about_modpwd.setOnClickListener(this);
		frg_my_about_version_num.setText(ConstantS.version);
		
		bgv_version=WxcPublicUtil.addRedPoint(mActivity,ll_frg_my_about_version,"新", 10, BadgeView.POSITION_CENTER);
		if (ConstantS.version_isnew!=0){
			bgv_version.setTextColor(Color.WHITE);
			bgv_version.show();
		}else{
			bgv_version.hide();
		}
		addTop();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_wxc_about, container, false);
		return view;
	}

	

	private void addTop(){
		mLl_parent=(LinearLayout) mParent.findViewById(R.id.ll_my_about_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(mActivity);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("关于");
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mActivity.finish();
			}
		});
		ib_handle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(mActivity, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		switch (arg0.getId()) {
		case R.id.ll_frg_my_about_feeback:
            intent.setClass(mActivity, WxcFeedBackActivity.class);
            startActivity(intent);
			break;
		case R.id.ll_frg_my_about_version:
			ConstantS.version_isnew=0;
            bgv_version.hide();
			break;
		case R.id.ll_frg_my_about_51:
			intent.setClass(mActivity, WxcAboutIntroActivity.class);
			 startActivity(intent);
			break;
								
		case R.id.ll_frg_my_about_tofriend:
//			intent.setAction(Intent.ACTION_SEND);  
//			intent.putExtra(Intent.EXTRA_TEXT, "这是一个测试的分享字符串");  
//			intent.setType("text/plain");  
//			startActivity(intent);
			
			Intent intentsend=new Intent(Intent.ACTION_SEND);   
			intentsend.setType("image/*");   
			intentsend.putExtra(Intent.EXTRA_SUBJECT, "分享");   
			intentsend.putExtra(Intent.EXTRA_TEXT, "这是一个测试的分享字符串");    
			intentsend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
	        startActivity(Intent.createChooser(intentsend,"51洗车"));   
			
			break;
		case R.id.ll_frg_my_about_phone:
            String phone_number=ConstantS.phone_number;
            intent  = new Intent();   
            intent.setAction("android.intent.action.DIAL");  
            intent.setData(Uri.parse("tel:"+phone_number));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);              
            /**
             * 
             * 1）直接拨打
				Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
				startActivity(intentPhone);
				2）跳转到拨号界面
				Intent intent = newIntent(Intent.ACTION_DIAL,Uri.parse("tel:" + phoneNumber));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
             * 
             * 
             */
                                  
			break;		
			
		case R.id.ll_frg_my_about_modpwd:
			if (ConstantS.user_name==null){
				WxcPublicUtil.showShortToast(mActivity, "您还未注册，请先注册");
				return;
			}			
			intent=new Intent();
			intent.setClass(mActivity, WxcMofiyPwdActivity.class);
			startActivity(intent);
			break;
			
		}
		
		
		
		
		
	}		
	
}

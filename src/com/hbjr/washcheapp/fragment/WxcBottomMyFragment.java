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
import com.hbjr.washcheapp.WxcLoginActivity;
import com.hbjr.washcheapp.WxcMainActivity;
import com.hbjr.washcheapp.WxcMessageActivity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class WxcBottomMyFragment extends Fragment implements OnClickListener  {
	private View mParent;
	private FragmentActivity mActivity;
	private static WxcBottomMyFragment  wbm;
	BadgeView bgv_more_score,bgv_more_vou,bgv_score,bgv_vou,bgv_message;
	
	private TextView tv_frg_my_account;
	private TextView tv_frg_my_voucher;
	private TextView tv_frg_my_score;
	
	String str_frg_my_account,str_frg_my_voucher,str_frg_my_score;
	
	private ImageView img_frg_my_img_voucher;
	private ImageView img_frg_my_img_order;
	private ImageView img_frg_my_img_acc;
	private ImageView img_frg_my_img_sco;
	private ImageView img_frg_my_img_message;	
	private ImageView img_frg_my_img_tologin;	
	
	
	private RelativeLayout ll_frg_my_voucher;
	private LinearLayout ll_frg_my_order;
	private LinearLayout ll_frg_my_acc;
	private LinearLayout ll_frg_my_sco;
	private LinearLayout ll_frg_my_message;

	
	private LinearLayout ll_frg_my_more_vou;
	private LinearLayout  ll_frg_my_more_score;
	
	private LinearLayout ll_nologin;
	private LinearLayout ll_islogin;
	
	private Intent intent;
	
	LinearLayout  mLl_parent;
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mActivity=getActivity();
		mParent=getView();
		super.onActivityCreated(savedInstanceState);
		addTop();
		initByid();
		initView();		
		wbm=this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my, container, false);
		return view;
	}

	

	public static WxcBottomMyFragment getInstance(){
		return wbm;
	}
	
	private void addTop(){
		mLl_parent=(LinearLayout)mParent.findViewById(R.id.ll_my_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(mActivity);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("我的");
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
	
	
	public void initByid(){
		tv_frg_my_account=(TextView) mParent.findViewById(R.id.frg_my_account);
		tv_frg_my_voucher=(TextView) mParent.findViewById(R.id.frg_my_voucher);
		tv_frg_my_score=(TextView) mParent.findViewById(R.id.frg_my_score);
		
	    img_frg_my_img_voucher=(ImageView) mParent.findViewById(R.id.frg_my_img_voucher);
	    img_frg_my_img_order=(ImageView) mParent.findViewById(R.id.frg_my_img_order);
	    img_frg_my_img_acc=(ImageView) mParent.findViewById(R.id.frg_my_img_acc);
	    img_frg_my_img_sco=(ImageView) mParent.findViewById(R.id.frg_my_img_sco);
	    img_frg_my_img_message=(ImageView) mParent.findViewById(R.id.frg_my_img_message);
	    

	    ll_frg_my_voucher=(RelativeLayout) mParent.findViewById(R.id.ll_frg_my_voucher);
	    ll_frg_my_order=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_order);
	    ll_frg_my_acc=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_acc);
	    ll_frg_my_sco=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_sco);
	    ll_frg_my_message=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_message);
	    
	    
	    img_frg_my_img_tologin=(ImageView) mParent.findViewById(R.id.frg_my_img_tologin);
	    
	    ll_frg_my_more_vou=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_more_vou);
	    ll_frg_my_more_score=(LinearLayout) mParent.findViewById(R.id.ll_frg_my_more_score);	
	    
	    
	    ll_frg_my_voucher.setOnClickListener(this);
	    ll_frg_my_order.setOnClickListener(this);
	    ll_frg_my_acc.setOnClickListener(this);
	    ll_frg_my_sco.setOnClickListener(this);
	    ll_frg_my_message.setOnClickListener(this);
	    img_frg_my_img_tologin.setOnClickListener(this);
	    ll_frg_my_more_vou.setOnClickListener(this);
	    ll_frg_my_more_score.setOnClickListener(this);	   
	        	
	}
	
	public void initView(){
		ll_nologin=(LinearLayout) mParent.findViewById(R.id.ll_my_nologin);
		ll_islogin=(LinearLayout) mParent.findViewById(R.id.ll_my_islogin);		
		System.out.println("从WxcBottomMyFragment 中取得的username 是："+ConstantS.user_name);
		intent=new Intent();
        setStatus();
		       				
	}
	
	
	public void setStatus(){
		bgv_more_score=WxcPublicUtil.addRedPoint(mActivity,ll_frg_my_more_score,String.valueOf(ConstantS.user_score_benfit_isnew), 10, BadgeView.POSITION_CENTER);
	    if (ConstantS.user_score_benfit_isnew!=0){
	    	bgv_more_score.show();
	    }else{
	    	bgv_more_score.hide();
	    }
	    
    	bgv_more_vou=WxcPublicUtil.addRedPoint(mActivity,ll_frg_my_more_vou,String.valueOf(ConstantS.user_vou_benfit_isnew), 10, BadgeView.POSITION_CENTER);
	    if (ConstantS.user_vou_benfit_isnew!=0){
	    	bgv_more_vou.show();
	    }else{
	    	bgv_more_vou.hide();
	    }
	    
	    
    	bgv_score=WxcPublicUtil.addRedPoint(mActivity,ll_frg_my_sco,"新", 10, BadgeView.POSITION_CENTER);
	    if (ConstantS.user_score_isnew!=0){
	    	bgv_score.setTextColor(Color.WHITE);
	    	bgv_score.setText("新");
	    	bgv_score.show();
	    }else{
	    	bgv_score.hide();
	    }
	    
    	bgv_vou=WxcPublicUtil.addRedPoint(mActivity,ll_frg_my_voucher,"新", 10, BadgeView.POSITION_CENTER);
	    if (ConstantS.user_vou_isnew!=0){
	    	bgv_score.setTextColor(Color.WHITE);	    	
	    	bgv_vou.show();
	    }else{
	    	bgv_vou.hide();
	    }	    
	    
    	bgv_message=WxcPublicUtil.addRedPoint(mActivity,ll_frg_my_message,String.valueOf(ConstantS.information_isnew), 10, BadgeView.POSITION_CENTER);
	    if (ConstantS.information_isnew!=0){
	    	bgv_message.show();
	    }else{
	    	bgv_message.hide();
	    }	  
	    
		if (!ConstantS.user_isLogin){
			ll_nologin.setVisibility(View.VISIBLE);
			ll_islogin.setVisibility(View.GONE);
	    }else{
	    	ll_nologin.setVisibility(View.GONE);
	    	ll_islogin.setVisibility(View.VISIBLE);
	    	tv_frg_my_account.setText(ConstantS.user_name);
	        String str_voucher=WxcUserAccount.useraccount.getVou_balance();
	        String str_score=WxcUserAccount.useraccount.getScore_sum();
	    	tv_frg_my_score.setText(str_score+"分");
	    	tv_frg_my_voucher.setText(str_voucher+"元");	
	    }
				
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		System.out.println("监听到点击事件，点击id是："+arg0.getId());		
		switch (arg0.getId()) {
		case R.id.frg_my_img_tologin:
			System.out.println("监听到点击事件，login登陆");
			intent.putExtra("triggerby", "WxcBottomMyFragment");
            intent.setClass(mActivity, WxcLoginActivity.class);
		    startActivity(intent);
			break;
		case R.id.ll_frg_my_voucher:
			if (ConstantS.user_isLogin){
			    ConstantS.user_vou_isnew=0;
			    bgv_vou.hide();				
	            intent.setClass(mActivity, WxcMyVoucherActivity.class);
			    startActivity(intent);	
			}
			break;
			
		case R.id.ll_frg_my_order:
			if (ConstantS.user_isLogin){
				System.out.println("点击我的订单");
	            intent.setClass(mActivity, WxcMyOrderTabMainActivity.class);
			    startActivity(intent);	
			}
			break;
			
		case R.id.ll_frg_my_acc:
			if (ConstantS.user_isLogin){
	            intent.setClass(mActivity, WxcMyAccountActivity.class);
			    startActivity(intent);	
			}
			break;
		case R.id.ll_frg_my_sco:
			if (ConstantS.user_isLogin){
			    ConstantS.user_score_isnew=0;
			    bgv_score.hide();								
	            intent.setClass(mActivity, WxcMyScoreBudgetActivity.class);
			    startActivity(intent);	
			}
			break;
		case R.id.ll_frg_my_message:
			    ConstantS.information_isnew=0;
			    bgv_message.hide();
	            intent.setClass(mActivity, WxcMessageActivity.class);
			    startActivity(intent);			
			break;
		case R.id.ll_frg_my_more_vou:
		    ConstantS.user_vou_benfit_isnew=0;
		    bgv_more_vou.hide();			
	            intent.setClass(mActivity, WxcMyGainVouActivity.class);
			    startActivity(intent);		
			break;	
		case R.id.ll_frg_my_more_score:
		    ConstantS.user_score_benfit_isnew=0;
		    bgv_more_score.hide();
	            intent.setClass(mActivity, WxcMyScoreActivity.class);
			    startActivity(intent);			
			break;				
		}	
		
		
	}
	
	
	
	
}

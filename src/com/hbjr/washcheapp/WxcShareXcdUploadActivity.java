package com.hbjr.washcheapp;

import java.util.HashMap;
import java.util.Map;

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
import com.hbjr.washcheapp.bean.WxcBusInfo;
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
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class WxcShareXcdUploadActivity extends Activity {

	private LinearLayout mLl_parent;
	private TextView share_location;
	private Button share_whole_upload;
	private boolean islogin=false;
	
	private String locationDesc;//��ǰλ������
	
	
	private WxcBusInfo info;
	private Bitmap[]  photolist;
	
	LoadingDialog loadingdialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_wxc_share_xcd_upload);
		MyApplication.getInstance().addActivity(this);
		addTop();
		
		share_location=(TextView) findViewById(R.id.share_location);
		share_whole_upload=(Button) findViewById(R.id.share_whole_upload);
	       /**
	        * ���ȶ�λ��ȡ�ø�ϴ����ľ�ά����Ϣ��			
	        */
//			//��ȡ��ǰ��ַ��Ϣ
     	WxcPublicUtil.initLocation(WxcShareXcdUploadActivity.this, true, true, false, null, handler_location_regin, null);		
//			 if (ConstantS.cur_latlng!=null){		
//				share_location.setText(ConstantS.cur_regin);
//			 }else{				
//		     	WxcPublicUtil.initLocation(WxcShareXcdUploadActivity.this, true, true, false, null, handler_location_regin, null);
//			 }		
		String rname = ConstantS.user_name;	
		System.out.println("��WxcShareXcdUpload ��ȡ�õ�username �ǣ�"+rname);
		if (rname==null||rname.equalsIgnoreCase("")){
			islogin=false;
			share_whole_upload.setText("���ȵ�¼");
	     }else{
		    islogin=true;
            share_whole_upload.setText("�ϴ���Ϣ");    	 
	     }
		share_whole_upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
               if (islogin){
            	   if (ConstantS.cur_latlng==null){
            		   WxcPublicUtil.showShortToast(WxcShareXcdUploadActivity.this, "����Ŭ����λ����λ�ã����Ե�");            		   
            		   return;
            	   }else{
            			if (ConstantS.cur_obj!=null){
            				info=(WxcBusInfo) ConstantS.cur_obj.get("xcd_info");
            				System.out.println("���ڴ��л�ȡϴ�������,ϴ���������ǣ�"+info.getName());
            				photolist=(Bitmap[]) ConstantS.cur_obj.get("photolist");
            				System.out.println("���ڴ��л�ȡͼƬ���������ǣ�"+photolist.length);
                 		   info.setLatitude(ConstantS.cur_latlng.latitude);
                		   info.setLongitude(ConstantS.cur_latlng.longitude);            				
            				//�첽�ϴ������ϴ������Ϣ========================================================================            				            				            				
               			loadingdialog=new LoadingDialog(WxcShareXcdUploadActivity.this, LoadingDialog.TYPE_POST);   	    			    
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
            		                //������ʧ��
            		                if (!rcode.equalsIgnoreCase("0")){
            							WxcPublicUtil.showShortToast(WxcShareXcdUploadActivity.this,rtmsg);
            			        		loadingdialog.dismiss();						
            			        		return;
            		                }

                              	   Intent intent=new Intent();
            						if (rtmsg!=null&&!rtmsg.equalsIgnoreCase("")){
             						  //WxcPublicUtil.invokeAlertDlg(WxcShareXcdUploadActivity.this, "��̨��ʾ", rtmsg);            							            							
            						   intent.putExtra("benfit", rtmsg);      
            						}            						  
                             	   intent.putExtra("title", "�ϴ��ɹ�");                                                   	   
                             	   intent.setClass(WxcShareXcdUploadActivity.this, WxcPrompActivity.class);
                             	   startActivity(intent);	                		                		                		                            		                			                			                													                				                
            						break;
            					case 1:		
            						WxcPublicUtil.showShortToast(WxcShareXcdUploadActivity.this,(String) msg.obj);
            		        		loadingdialog.dismiss();						
            						break;
            					}		        	                    		        	
            		        }
            		  };	
            	    new Thread(new Runnable() {
            	        public void run() {
            	            try {
            	            	System.out.println("��ʼ�첽�ύ����===================");
            					info.setRegin_id(ConstantS.Regin_City_ID);
            					info.setRegin_name(ConstantS.Regin_City);
            	        		Map map_info = new HashMap();
            	        		map_info.put("fid", ConstConnectCode.FID_PUT_SHARE_XCD);
            	        		map_info.put("uname", ConstantS.user_name);
                                map_info.put("info", JSONPostTools.createJsonString("info", info));
                                map_info.put("images", JSONPostTools.createJsonString("images", JSONPostTools.getPutImageList(photolist)));                                
            	        		            	        		
            	        		//��ȡ����������ĵ�ַ
            	        		String json = URLProtocol.postJSonString(map_info);
            	        		JSONTools.getPutShareXcd(ConstConnectCode.FID_PUT_SHARE_XCD, json, handler_post);
            	            } catch (Exception e) {
            	                e.printStackTrace();
            	            }
            	        }
            	    }).start();
            				            				            			
            				//=========================================================================================            				

            			}            		                    		               		   
            	   }
            	               	               	   
               }else{
            	   Intent intent=new Intent();
            	   intent.putExtra("triggerby", "WxcShareXcdUploadActivity");
            	   intent.setClass(WxcShareXcdUploadActivity.this, WxcLoginActivity.class);
            	   startActivity(intent);            	
               }
		
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_share_xcd_upload, menu);
		return true;
	}
	
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_share_upload_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ConstantS.top_long);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("����ϴ����  �ϴ���Ϣ");
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
				intent.setClass(WxcShareXcdUploadActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
		
    /**************************************************************************************
    ��λ���handler
 */
private Handler handler_location_regin = new  Handler(){
	@Override
	public void handleMessage(Message msg){
		switch(msg.what){
		case 1:
			System.out.println("����handlelocation_regin");
            String cur_regin= (String) msg.obj;
        	share_location.setText(cur_regin);
			/**
			 * ���磺
			 * today_text.setText(jsonData.getString("date_y"));
			 * int icon = parseIcon(jsonData.getString("img1")+".gif");
		       image.setImageResource(icon);
			 * 
			 * 
			 */			
			break;
		}
	}
};


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		WxcPublicUtil.mLocClient.stop();
		System.out.println("sharexcdupload��ֹͣ���ö�λ");		
		super.onDestroy();
	}  			
	
	



}

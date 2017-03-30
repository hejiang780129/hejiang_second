package com.hbjr.washcheapp.fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.Inflater;

import com.hbjr.washcheapp.widget.LoadingDialog;
import com.hbjr.washcheapp.widget.XListView;
import com.hbjr.washcheapp.widget.XListView.IXListViewListener;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.baidu.nplatform.comapi.map.ItemizedOverlay;
import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.WxcLoginActivity;
import com.hbjr.washcheapp.WxcMainActivity;
import com.hbjr.washcheapp.WxcPicTestActivity;
import com.hbjr.washcheapp.WxcRegisterActivity;
import com.hbjr.washcheapp.WxcShareXcdActivity;
import com.hbjr.washcheapp.WxcShareXcdTakePhotoActivity;
import com.hbjr.washcheapp.WxcShareXcdUploadActivity;
import com.hbjr.washcheapp.WxcWyxcInfoActivity;
import com.hbjr.washcheapp.WxcWyxcList;
import com.hbjr.washcheapp.adapter.AdverAdapter;
import com.hbjr.washcheapp.adapter.ListViewAdapter;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.config.ConstConnectCode;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.config.SinglePreferences;
import com.hbjr.washcheapp.net.JSONTools;
import com.hbjr.washcheapp.net.URLProtocol;
import com.hbjr.washcheapp.util.WxcPublicUtil;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

public class WxcBottomFirFragment extends Fragment implements IXListViewListener  {
	private View mParent;
	private FragmentActivity mActivity;
	private ViewPager viewPager;
	private ViewPager viewPager_mid;
	private List pageViews; 
	private List pageViews_mid;
	private ViewGroup group;
	private ViewGroup group_mid;
	private ImageView[] imageViews;
	private ImageView[] imageViews_mid;
	private ImageView imageView;
	private ImageView imageView_mid;
	
	private XListView myListView;
	private View more_view;
	private int lastItem;
    private int count;
    private ListViewAdapter adapter;
    private List<WxcBusInfo> listData;    
    
    private ImageView image_wyxc;
    private ImageView image_share;
    
    private LinearLayout mLl_parent;
    
    
    private LinearLayout img1;
    private TextView regin;
    private TextView tv_weather;
    private TextView date;
    private TextView go;
    private ImageView img_weather;
    private TextView wind;
    private TextView weather_temp;
    
    
	private Handler mHandler_xlistview;
	private int start = 0;
	private static int refreshCnt = 0;
  		
		
	/**
	 * ��ǰ��ʾ��ͼƬid
	 */
	private int currentItem = 0;
	private int currentItem_mid = 0;
	
	
	/**
	 * ����file
	 */
	File cache; // �����ļ�  
	private LoadingDialog loadingdialog;
	
	
	// �л���ǰ��ʾ��ͼƬ
		private Handler handler = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
			   	viewPager.setCurrentItem(currentItem);  
			   	super.handleMessage(msg);  
			};
		};
		
		
		private Handler handler1 = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
			   	viewPager_mid.setCurrentItem(currentItem_mid);
			   	//System.out.println("mid ͼƬ�ڽ����л�������������������"+currentItem_mid);
			     super.handleMessage(msg);  
			};
		};	
		
	private ScheduledExecutorService scheduledexcutorservice;
		
	public WxcBottomFirFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mActivity=getActivity();
		mParent=getView();
		super.onActivityCreated(savedInstanceState);
		SDKInitializer.initialize(mActivity.getApplicationContext());
		
		WxcPublicUtil.getAssetsContent(mActivity);
		img1=(LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.weather_layout, null);
		regin=(TextView) img1.findViewById(R.id.weather_cur_regin);
		tv_weather=(TextView) img1.findViewById(R.id.weather_cur_desc);
		date=(TextView) img1.findViewById(R.id.weather_cur_date);
		go=(TextView) img1.findViewById(R.id.weather_cur_wash);
		img_weather=(ImageView) img1.findViewById(R.id.weather_cur_img);
	    wind=(TextView) img1.findViewById(R.id.weather_cur_wind);
	    weather_temp=(TextView) img1.findViewById(R.id.weather_cur_temp);
		
		viewPager=(ViewPager)mParent.findViewById(R.id.viewpager);	
		viewPager_mid=(ViewPager)mParent.findViewById(R.id.viewpager_mid);
		
		listData=new ArrayList<WxcBusInfo>();
		
		if (ConstantS.Regin_City!=null&&!ConstantS.Regin_City.equalsIgnoreCase("")){
		   regin.setText(" ��"+ConstantS.Regin_City+"�� ");
		}else{
           WxcPublicUtil.initLocation(mActivity, true,false,true,handler_location,null,handler_weather);		  
		   System.out.println("�� firfragment�е���һ��location=================================");
		}
		
		if (ConstantS.weather_weather!=null&&!ConstantS.weather_weather.equalsIgnoreCase("")){		
			System.out.println("���ڴ��л�ȡ��������Ϣ�ǣ�"+ConstantS.weather_weather+";"+ConstantS.weather_date
					+";"+ConstantS.weather_temp+";"+ConstantS.weather_wind+";"+ConstantS.weather_xc);			
			    tv_weather.setText(ConstantS.weather_weather);				
				img_weather.setImageResource(ConstantS.weather_icon);
				date.setText(ConstantS.weather_date);
				go.setText(ConstantS.weather_xc);
				wind.setText(ConstantS.weather_wind);
				weather_temp.setText(ConstantS.weather_temp);
		}else{			
			if (ConstantS.Regin_City!=null&&!ConstantS.Regin_City.equalsIgnoreCase("")){
				   System.out.println("�� firfragment�е���һ��requestWeather=================================");
				 WxcPublicUtil.requestWeather(ConstantS.Regin_City, handler_weather);				
			}else{
				   System.out.println("�� firfragment�е���һ��initLocation=================================");				
				   WxcPublicUtil.initLocation(mActivity,true,false,true,handler_location,null,handler_weather);
			}
		}
		addTop();
        initAdapt();
        initAdapt_mid();
        viewPager.setAdapter(new AdverAdapter(pageViews));
        viewPager_mid.setAdapter(new AdverAdapter(pageViews_mid));
        initCirclePoint();
        viewPager.setOnPageChangeListener(new PageListener());
        //����list
        initListData(-1);        
        initListById();
        image_wyxc=(ImageView) mParent.findViewById(R.id.imageView1);
        image_share=(ImageView) mParent.findViewById(R.id.imageView2);
        image_wyxc.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("���б���");
				Intent intent = new Intent();
				intent.setClass(mActivity, WxcWyxcList.class);
				startActivity(intent);
				mActivity.overridePendingTransition(R.anim.push_left, R.anim.push_left_out);								
			}
		});
        
        image_share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(mActivity, WxcShareXcdActivity.class);
				startActivity(intent);
				
			}
		});
              
	}
	
	
	private void addTop(){
		mLl_parent=(LinearLayout) mParent.findViewById(R.id.ll_first_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ConstantS.top_long);
		LayoutInflater inflate=LayoutInflater.from(mActivity);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		ib_handle.setVisibility(View.GONE);
		ImageButton ib_handle1=(ImageButton) view.findViewById(R.id.ib_handle1);
		ib_handle1.setVisibility(View.VISIBLE);
		tv.setText("51ϴ����ҳ");
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mActivity.finish();
			}
		});
		ib_handle1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub            
				System.out.println("ȡ�õ�username �ǣ�"+ConstantS.user_name);
				if (!ConstantS.user_isLogin){
					Intent intent=new Intent();
					intent.setClass(mActivity, WxcLoginActivity.class);
					startActivity(intent);
				}
				else{					
					new AlertDialog.Builder(mActivity)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("�˳���¼��ʾ").setMessage("���Ƿ���Ҫ�˳���¼").
					setPositiveButton("ȷ��",new MyDialogOnclick())
					.setNegativeButton("ȡ��", null)
					.show();	
					
				}
	
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}

	
	class MyDialogOnclick implements android.content.DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
            ConstantS.user_isLogin=false;
			WxcPublicUtil.showShortToast(mActivity, "�ѳɹ��˳���¼");		    
			Intent intent=new Intent();
			intent.setClass(mActivity, WxcMainActivity.class);
			startActivity(intent);
		}
		
	}	
	
	
	public void initAdapt(){
		pageViews=new ArrayList();
		
		pageViews.add(img1);
		
		ImageView img2=new ImageView(mActivity);
		img2.setBackgroundResource(R.drawable.t1);
		pageViews.add(img2);
		
		ImageView img3=new ImageView(mActivity);
		img3.setBackgroundResource(R.drawable.t2);
		pageViews.add(img3);
		
		ImageView img4=new ImageView(mActivity);
		img4.setBackgroundResource(R.drawable.t3);
		pageViews.add(img4);
	}
	
	public void initAdapt_mid(){
		pageViews_mid=new ArrayList();

		ImageView img1=new ImageView(mActivity);
		img1.setBackgroundResource(R.drawable.jifenduihuan);
		pageViews_mid.add(img1);
		
		ImageView img2=new ImageView(mActivity);
		img2.setBackgroundResource(R.drawable.jifenduihuan1);
		pageViews_mid.add(img2);
		
		ImageView img3=new ImageView(mActivity);
		img3.setBackgroundResource(R.drawable.daijinjuan);
		pageViews_mid.add(img3);
		
		ImageView img4=new ImageView(mActivity);
		img4.setBackgroundResource(R.drawable.daijinjuan1);
		pageViews_mid.add(img4);
	}
	
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_first, container, false);
		return view;
	}
	
	
	private void initCirclePoint(){
		group= (ViewGroup) mParent.findViewById(R.id.viewGroup);
		imageViews=new ImageView[pageViews.size()];
		for(int i=0;i<imageViews.length;i++){
			imageView = new ImageView(mActivity);  
			imageView.setLayoutParams(new LayoutParams(20,20));
			imageViews[i]=imageView;
			if (i==0){
				imageViews[i].setBackgroundResource(R.drawable.dotchecked);
			}else{
				imageViews[i].setBackgroundResource(R.drawable.dotdefault);
			}
			group.addView(imageView);
		}
        System.out.println("��ʼ��Բ��ͼƬ���");  
	}
	
	
	private class PageListener implements OnPageChangeListener{
		private int oldPosition = 0;
		@Override
		
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		/**
         * ҳ�������ʱ�򴥷�
         */
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		 /**
         * ҳ��ѡ�е�ʱ�򴥷�
         */
		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub			
			currentItem=arg0;
			imageViews[currentItem].setBackgroundResource(R.drawable.dotchecked);
			imageViews[oldPosition].setBackgroundResource(R.drawable.dotdefault);
			oldPosition = arg0;
			if (currentItem==0){
					if (ConstantS.weather_weather==null||ConstantS.weather_weather.equalsIgnoreCase("")){
						if (ConstantS.Regin_City!=null&&!ConstantS.Regin_City.equalsIgnoreCase("")){
							System.out.println("ͼƬˢ��ʱ���������ӿ�=============================");
						    WxcPublicUtil.requestWeather(ConstantS.Regin_City,handler_weather);				
						}else{
							System.out.println("ͼƬˢ��ʱ���ö�λ�ӿ�=============================");							
							WxcPublicUtil.initLocation(mActivity, true,false,true,handler_location,null,handler_weather);
						}
					}				
					
				}
		}
		
	}
	
	

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
		
		scheduledexcutorservice=Executors.newScheduledThreadPool(2);
		
		// ��Activity��ʾ������ÿ�������л�һ��ͼƬ��ʾ
		scheduledexcutorservice.scheduleWithFixedDelay(new ScrollTask(),1,5, TimeUnit.SECONDS);
		//scheduledexcutorservice.scheduleAtFixedRate(new ScrollTask(), 1, 3,TimeUnit.SECONDS);
		//System.out.println("������һ������");
		scheduledexcutorservice.scheduleAtFixedRate(new ScrollTask_mid(), 1, 6,	TimeUnit.SECONDS);
		//System.out.println("�����ڶ�������");
		super.onStart();
	}

	
	

//	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		// ��Activity���ɼ���ʱ��ֹͣ�л�
		scheduledexcutorservice.shutdown();
		super.onStop();
	}

	
	private class ScrollTask implements Runnable {

		@Override
		public void run() {
			synchronized (viewPager) {
				if (currentItem==0){
					try{
						Thread.sleep(8000);						
					}catch(Exception e){
	                    e.printStackTrace();
					}				
				}				
				currentItem = (currentItem + 1) % pageViews.size();
				//System.out.println("�Զ��л�ʱcurrentItem ��"+currentItem);
				handler.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
			}
		}

	}
	
	
	private class ScrollTask_mid implements Runnable {

		@Override
		public void run() {
			    try{
				currentItem_mid = (currentItem_mid + 1) % pageViews_mid.size();
			    }catch(Exception e){
			     }
				//System.out.println("�Զ��л�ʱcurrentItem_mid ��"+currentItem_mid);
				handler1.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
			}

	}
	
	private void initListById(){		
		myListView=(XListView) mParent.findViewById(R.id.listview_xcd);
		myListView.setPullLoadEnable(true);
		count = listData.size();
		System.out.println("count is  "+ count);
		adapter=new ListViewAdapter(mActivity, listData,cache);
		myListView.setAdapter(adapter);
		myListView.setXListViewListener(this);
		myListView.setOnItemClickListener(new MyOnItemClickListener());		
		mHandler_xlistview=new Handler();		
	}
	
	private void initListData(int size){
	    final int cursize=size;
	    
	    if (ConstantS.cur_latlng==null){
	    	System.out.println("��ǰ��λ����ϢΪ�գ��˳����÷������б�");
	    	return;
	    }
	    
		loadingdialog=new LoadingDialog(mActivity, LoadingDialog.TYPE_GET);
		loadingdialog.show();   	    				
		//Ϊ-1ʱ��ȫ��ˢ���б�����
		if (cursize==-1){
			listData.clear();
//			listData.addAll(WxcBusInfo.infos);			
			System.out.println("������������");
		}
			
		//�첽���ط��������ݣ�============================================================================================				
		//���̼߳������ݣ�ͨ��hanlder����		
		cache = new File(Environment.getExternalStorageDirectory(), "cache_frglst"); // ʵ���������ļ�
		if (!cache.exists())  
            cache.mkdirs(); // ����ļ������ڣ�����  
		
		final Handler handler_xcdlist = new Handler() {
		        public void handleMessage(Message msg) {		        	
                    switch (msg.what) {
					case 0:						
		                List  listData_get= (List<WxcBusInfo>) msg.obj;		                
		                listData.addAll(listData_get);
		                if (cursize==-1){
		                	System.out.println("ȫ������list=========");
		                	myListView.setAdapter(adapter=new ListViewAdapter(mActivity,listData,cache));
		                }else{
		                	adapter.notifyDataSetChanged();	
		                	System.out.println("ˢ������list=========");		                	
		                }
		        		loadingdialog.dismiss();
						break;
					case 1:		
						WxcPublicUtil.showShortToast(mActivity,(String) msg.obj);
		        		loadingdialog.dismiss();						
						break;
					}		        	                    		        	
		        }
		  };	
        new Thread(new Runnable() {
            public void run() {
                try {
                	System.out.println("��ʼ�첽����ϴ��������===================");
            		Map map_info = new HashMap();
            		map_info.put("fid", ConstConnectCode.FID_GET_XCDLIST);
            		map_info.put("poisition_lat",String.valueOf(ConstantS.cur_latlng.latitude));
            		map_info.put("poisition_lang",String.valueOf(ConstantS.cur_latlng.longitude));		
            		map_info.put("cursize", String.valueOf(cursize));
            		//��ȡ����������ĵ�ַ
            		String json = URLProtocol.getJSonString(map_info);
            		JSONTools.getWxcBusInfoList(ConstConnectCode.FID_GET_XCDLIST, json, handler_xcdlist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();		
									
		//�첽���ط��������ݣ�============================================================================================										
	    
	}
	
	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
		   System.out.println("���ϴ����...........");
		   WxcBusInfo info=(WxcBusInfo) listData.get(position);
		   System.out.println("���ϴ����:"+info.getName());
		   Intent intent = new Intent(mActivity,WxcWyxcInfoActivity.class);
		   intent.putExtra("info", info);
		   startActivity(intent);
		}
	}
	
	
	private List<WxcBusInfo> getData(List infos){
		listData=new ArrayList<WxcBusInfo>();
		WxcBusInfo info;
		for (int i=0 ;i<3;i++){
	    info=(WxcBusInfo) infos.get(i);
		listData.add(info);
		}
		return listData;
	}
	
	
	
	private class ListViewScrListener implements OnScrollListener{

		public ListViewScrListener() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			lastItem = firstVisibleItem + visibleItemCount-1;//��1����Ϊ������˸�addFooterView
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			//�����������ǣ������һ��item�����������ݵ�����ʱ�����и���
			System.out.println("��ʼˢ�¡���������������������lastItem="+lastItem+" count="+count);
            if(lastItem == count  && scrollState == this.SCROLL_STATE_IDLE){
            System.out.println("��ʾ���ؽ���");
			more_view.setVisibility(view.VISIBLE);
			more_view.requestLayout();
			mHandler.sendEmptyMessage(0);
            }else{
            more_view.setVisibility(view.GONE);
            }
		}
		
	}
	
	
	//����Handler
    private Handler mHandler = new Handler(){
            public void handleMessage(android.os.Message msg) {
                    switch (msg.what) {
                    case 0:
                         
                            try {
                                    Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                            }
                                               
                        if(count > 5){
                        	System.out.println("����5����¼���������µ����ݡ�������������");
                            Toast.makeText(mActivity, "ľ�и������ݣ�", 3000).show();
                             myListView.removeFooterView(more_view); //�Ƴ��ײ���ͼ
                        }else{
                        //loadMoreData(WxcBusInfo.infos);  //���ظ������ݣ��������ʹ���첽����
                        adapter.notifyDataSetChanged();
                        more_view.setVisibility(View.GONE);
                        }
                            break;
        case 1:
                            
                            break;
                    default:
                            break;
                    }
            };
    };
    

    /**************************************************************************************
        ��λ���handler
	 */
	private Handler handler_location = new  Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			case 1:
				System.out.println("����handlelocation");
                String cur_regin= (String) msg.obj;
     		    regin.setText(" ��"+cur_regin+"�� ");
				/**
				 * ���磺
				 * today_text.setText(jsonData.getString("date_y"));
				 * int icon = parseIcon(jsonData.getString("img1")+".gif");
			       image.setImageResource(icon);
				 * 
				 * 
				 */	
				
//				try {
//					thread.join();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				break;
			}
		}
	};  


	/**************************************************************************************
	 * ��ȡhttp://m.weather.com.cn/data/101091106.html���������
	 * ����101091106Ϊ���д��룬�������Ѿ��ѳ��д��������޸ģ�ȥ���˿��У���ʽΪUTF-8
	 * ÿ��ֻ������һ���̣߳�Ȼ��������̻߳���
	 * @param city_str
	 * @throws JSONException 
	 * @throws Exception 
	 * @throws ClientProtocolException 
	 */
	private Handler handler_weather = new  Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what){
			case 1:
				System.out.println("����handleMessage");
				JSONObject weather = (JSONObject) msg.obj;
				//�������ϵ�Ԫ�ظ�ֵ
				try{					
			    System.out.println("��firfag��ȡ�� ������Ϣ");					
				ConstantS.weather_weather=weather.getString("weather1");
				int icon = WxcPublicUtil.parseIcon(weather.getString("img1")+".gif");
				ConstantS.weather_icon=icon;
				ConstantS.weather_date=weather.getString("date_y")+" "+weather.getString("week");
				ConstantS.weather_xc=weather.getString("index_xc")+"ϴ��";					
				ConstantS.weather_temp=weather.getString("temp1");
				ConstantS.weather_wind=weather.getString("wind1");
				System.out.println("��frifag��ʵʱ��ȡ��������Ϣ�ǣ�"+ConstantS.weather_weather+";"+ConstantS.weather_date
						+";"+ConstantS.weather_temp+";"+ConstantS.weather_wind+";"+ConstantS.weather_xc);
			    tv_weather.setText(ConstantS.weather_weather);				
				img_weather.setImageResource(ConstantS.weather_icon);
				date.setText(ConstantS.weather_date);
				go.setText(ConstantS.weather_xc);
			    wind.setText(ConstantS.weather_wind);
			    weather_temp.setText(ConstantS.weather_temp);
				/**
				 * ���磺
				 * today_text.setText(jsonData.getString("date_y"));
				 * int icon = parseIcon(jsonData.getString("img1")+".gif");
			       image.setImageResource(icon);
				 * 
				 * 
				 */
				}catch(Exception e){
				   e.printStackTrace();	
				}
						
				break;
			}
		}
	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		WxcPublicUtil.mLocClient.stop();
		//��������ļ�
		if (cache!=null){
			File[] files=cache.listFiles();
			if (files!=null){
			    for (File file :files) {  
			    	   System.out.println("Bottomfirfrag������ɾ�����ļ����ǣ�"+file.getPath()+file.getName());	    	
			            file.delete();  
			        }  
			        cache.delete();
			}		        
		}
		System.out.println("frifrag��ֹͣ���ö�λ");
		super.onDestroy();
	}
	
	
	private void onLoad() {
		myListView.stopRefresh();
		myListView.stopLoadMore();
		myListView.setRefreshTime("ˢ��ʱ��"+WxcPublicUtil.getCurDateTime());
	}
	

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mHandler_xlistview.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
//				listData.clear();
//              loadMoreData();				
				initListData(-1);
				System.out.println("refresh ���listData�ǣ�"+listData.size());
				// mAdapter.notifyDataSetChanged();
				adapter=new ListViewAdapter(mActivity, listData,cache);
				myListView.setAdapter(adapter);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		mHandler_xlistview.postDelayed(new Runnable() {
			@Override
			public void run() {
//                loadMoreData(WxcBusInfo.infos);
				initListData(listData.size());
				adapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	
	
	//�첽���ظ������ݣ�ÿ�μ���3��
	private void loadMoreData(List infos){ //���ظ�������
		count = adapter.getCount();
		System.out.println("��ǰlist����Ϊ��"+count);
	    WxcBusInfo info;
		for (int i=3 ;i<6;i++){
			info=(WxcBusInfo) infos.get(i);
			listData.add(info);
			}
		
	   	count = listData.size();
	   	System.out.println("���¼��غ�list����Ϊ��"+count);
	   }
	
	
	
	
	
	
	
}

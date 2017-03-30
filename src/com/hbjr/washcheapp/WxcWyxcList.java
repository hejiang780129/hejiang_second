package com.hbjr.washcheapp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import com.baidu.lbsapi.BMapManager;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.hbjr.washcheapp.WxcWyxcLbsActivity.MyGeoCoderListener;
import com.hbjr.washcheapp.WxcWyxcLbsActivity.MyLocationListenner;
import com.hbjr.washcheapp.adapter.AdverAdapter;
import com.hbjr.washcheapp.adapter.ListViewAdapter;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.config.ConstConnectCode;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.net.JSONTools;
import com.hbjr.washcheapp.net.URLProtocol;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;
import com.hbjr.washcheapp.widget.LoadingDialog;
import com.hbjr.washcheapp.widget.XListView;
import com.hbjr.washcheapp.widget.XListView.IXListViewListener;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class WxcWyxcList extends Activity  implements IXListViewListener {

	private TextView tv_loaction;
	private XListView myListView;
	private ListView myListView_mid;
	private View more_view;
	private int lastItem;
    private int count;
    private ListViewAdapter adapter;
    private List<WxcBusInfo> listData;    	
    
    
    private ViewPager viewPager;
	private List pageViews; 
	private ImageView[] imageViews;
	private ImageView imageView;
	private int currentItem = 0;
	
	private LinearLayout mLl_parent;
	
	private Handler mHandler_xlistview;
	private int start = 0;
	private static int refreshCnt = 0;

	
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
			
	private ScheduledExecutorService scheduledexcutorservice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_wxc_wyxc_list);
		MyApplication.getInstance().addActivity(this);
		tv_loaction=(TextView) findViewById(R.id.id_loaction);
		
		listData=new ArrayList<WxcBusInfo>();
		
		addTop();
		//���ص�ͼ��ť�¼�
		ImageView iv_lbs=(ImageView) findViewById(R.id.imageDitu);
		iv_lbs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			Intent intent=new Intent();
			intent.setClass(WxcWyxcList.this, WxcWyxcLbsActivity.class);
			startActivity(intent);
			}
		});
		
		

        //����list
        initListData(-1);        
        initListById();
		
		
		//���ع��
		viewPager=(ViewPager) findViewById(R.id.viewpager_bottom);	
		initAdapt();
		 viewPager.setAdapter(new AdverAdapter(pageViews));
         WxcPublicUtil.initLocation(WxcWyxcList.this,true,true,false,null,handler_location_regin,null);
//		//��ȡ��ǰ��ַ��Ϣ
//		 if (ConstantS.cur_regin!=null&&!ConstantS.cur_regin.equalsIgnoreCase("")){	
//			 System.out.println("");
//			  tv_loaction.setText("����ǰ��λ���ڣ�"+ConstantS.cur_regin);
//		 }else{
//		     WxcPublicUtil.initLocation(WxcWyxcList.this,true,true,false,null,handler_location_regin,null);
//		 }		 		 
	}

	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ConstantS.top_long);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("�ܱ�ϴ����");
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
				intent.setClass(WxcWyxcList.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	private void initListData(int size){
	    final int cursize=size;
		loadingdialog=new LoadingDialog(WxcWyxcList.this, LoadingDialog.TYPE_GET);
		loadingdialog.show();   	    
		//Ϊ-1ʱ��ȫ��ˢ���б�����
		if (cursize==-1){
			listData.clear();
//			listData.addAll(WxcBusInfo.infos);			
			System.out.println("������������");
		}
			
		//�첽���ط��������ݣ�============================================================================================				
		//���̼߳������ݣ�ͨ��hanlder����		
		cache = new File(Environment.getExternalStorageDirectory(), "cach_xclst"); // ʵ���������ļ�
		if (!cache.exists())  
            cache.mkdirs(); // ����ļ������ڣ�����  
		
		final Handler handler_xcdlist = new Handler() {
		        public void handleMessage(Message msg) {		        	
                    switch (msg.what) {
					case 0:						
		                List  listData_get= (List<WxcBusInfo>) msg.obj;
		                listData.addAll(listData_get);
		                WxcBusInfo.infos_lbs_tmp=listData;
		                if (cursize==-1){
		                	System.out.println("ȫ������list=========");
		                	myListView.setAdapter(adapter=new ListViewAdapter(WxcWyxcList.this,listData,cache));
		                }else{
		                	adapter.notifyDataSetChanged();	
		                	System.out.println("ˢ������list=========");		                	
		                }
		        		loadingdialog.dismiss();
						break;
					case 1:		
						WxcPublicUtil.showShortToast(WxcWyxcList.this,(String) msg.obj);
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
            		map_info.put("condition", "0");//condition:0��û�и�ֵ:����������1�����۸�����2������������3����������������
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
	
	
	
	private void initListById(){
			myListView=(XListView) findViewById(R.id.listview_wyxc);	
			myListView.setPullLoadEnable(true);
			count = listData.size();
			System.out.println("count is  "+ count);
			adapter=new ListViewAdapter(WxcWyxcList.this, listData,cache);
			myListView.setAdapter(adapter);
			myListView.setXListViewListener(this);
			myListView.setOnItemClickListener(new MyOnItemClickListener());
			mHandler_xlistview=new Handler();		

		}
	
	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			WxcBusInfo info=(WxcBusInfo) listData.get(position);
		   System.out.println("���ϴ����:"+info.getName());
		   Intent intent = new Intent(WxcWyxcList.this,WxcWyxcInfoActivity.class);
		   intent.putExtra("info", info);
		   startActivity(intent);
		}
	}
	

	private List<WxcBusInfo> getData(){
		listData=new ArrayList<WxcBusInfo>();
		for (int i=0;i<6;i++){
	//		listData.add(WxcBusInfo.infos.get(i));
		}
		return listData;
	}
	
//	private HashMap getData(WxcBusInfo info){
//		HashMap<String, Object> map=new HashMap<String, Object>();
//		map.put("img_wxc",info.getImgId()); //
//		map.put("name_xcd", info.getName()); //
//		map.put("pingjia_xcd",info.getImgPingjiaId());//
//		map.put("dizhi_xcd", info.getAddress());//
//		map.put("kanpinglun_xcd", "������");
//		map.put("img_jiage", R.drawable.renminbi);
//		map.put("jiage", info.getPrice());//
//		map.put("img_daohang", R.drawable.daohang);
//		map.put("juli",info.getDistance());//
//		map.put("goumairenshu",info.getBuynum());//
//		return map;
//	}
	
	private void loadMoreData(){ //���ظ�������
		count = adapter.getCount();
		System.out.println("��ǰlist����Ϊ��"+count);
			
		for (int i=7;i<11;i++){
			//listData.add(WxcBusInfo.infos.get(i));
		}
		
	   	count = listData.size();
	   	System.out.println("���¼��غ�list����Ϊ��"+count);
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
                                               
                        if(count > 11){
                        	System.out.println("����5����¼���������µ����ݡ�������������");
                            Toast.makeText(WxcWyxcList.this, "ľ�и������ݣ�", 3000).show();
                             myListView.removeFooterView(more_view); //�Ƴ��ײ���ͼ
                        }else{
                        loadMoreData();  //���ظ������ݣ��������ʹ���첽����
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

	
    
    public void initAdapt(){
		pageViews=new ArrayList();
		
		ImageView img1=new ImageView(this);
		img1.setBackgroundResource(R.drawable.guanggao1);
		pageViews.add(img1);
		
		ImageView img2=new ImageView(this);
		img2.setBackgroundResource(R.drawable.guanggao2);
		pageViews.add(img2);
		
		ImageView img3=new ImageView(this);
		img3.setBackgroundResource(R.drawable.guanggao3);
		pageViews.add(img3);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_wyxc_list, menu);
		return true;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
		
		scheduledexcutorservice=Executors.newScheduledThreadPool(2);
		
		// ��Activity��ʾ������ÿ�������л�һ��ͼƬ��ʾ
		scheduledexcutorservice.scheduleAtFixedRate(new ScrollTask(), 1, 2,
				TimeUnit.SECONDS);
		super.onStart();
	}

	
	private class ScrollTask implements Runnable {

		@Override
		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % pageViews.size();
				//System.out.println("�Զ��л�ʱcurrentItem ��"+currentItem);
				handler.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
			}
		}

	}
	

//	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		// ��Activity���ɼ���ʱ��ֹͣ�л�
		scheduledexcutorservice.shutdown();
		super.onStop();
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
			tv_loaction.setText("����ǰ��λ���ڣ�"+cur_regin);
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
		System.out.println("Wyxclist��ֹͣ���ö�λ");
		//��������ļ�
		if (cache!=null){
			File[] files=cache.listFiles();
			if (files!=null){
			    for (File file :files) {  
			    	   System.out.println("Wxclist������ɾ�����ļ����ǣ�"+file.getPath()+file.getName());	    	
			            file.delete();  
			        }  
			        cache.delete();
			}		        
		}
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
				initListData(-1);				
				//loadMoreData();
				// mAdapter.notifyDataSetChanged();
				adapter=new ListViewAdapter(WxcWyxcList.this, listData,cache);
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
//				loadMoreData();
				initListData(listData.size());
				adapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
		
	}  



	
	


}

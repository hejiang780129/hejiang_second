package com.hbjr.washcheapp;

import java.io.File;
import java.util.List;
import java.util.Locale;




import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.nplatform.comapi.basestruct.GeoPoint;
import com.baidu.nplatform.comapi.map.MapController;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;

import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class WxcWyxcLbsActivity extends Activity {
    private MapView  mMapView;
    private BaiduMap mBaiduMap;
    
    private LinearLayout mMarkerInfoLy=null;
    private WxcBusInfo info;
    
	// ��λ���
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;    

	boolean isFirstLoc = true;// �Ƿ��״ζ�λ
	
	/**
	 * ��ǰ�ص����
	 */
	private LatLng currentPt;
	/**
	 * ��ǰ��λ�ĵ�
	 */
	private LatLng cur_pos;
	private Toast mToast;  
    private BMapManager mBMapManager;  
      
    GeoCoder mSearch = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
	    
	private InfoWindow mInfoWindow;
	// ��ʼ��ȫ�� bitmap ��Ϣ������ʱ��ʱ recycle
		BitmapDescriptor bd = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		BitmapDescriptor bdB = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_markb);
		

		/**
		 * ����file
		 */
		File cache; // �����ļ�	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext  
        //ע��÷���Ҫ��setContentView����֮ǰʵ��  
        SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_wxc_wyxc_lbs);
		MyApplication.getInstance().addActivity(this);
		mMapView=(MapView) findViewById(R.id.bmapView);
		mMarkerInfoLy=(LinearLayout) findViewById(R.id.id_marker_info);
		//��λ
		cur_location();
		//���Ӷ�����
		addTop();
		//���ø�����
		addOverlay(WxcBusInfo.infos_lbs_tmp);
		//demo_method();
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_wyxc_lbs, menu);
		return true;
	}

	
	private void addTop(){
		LinearLayout mLl_parent=(LinearLayout)findViewById(R.id.ll_lbs_parent);
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
				intent.setClass(WxcWyxcLbsActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		       // �˳�ʱ���ٶ�λ
				mLocClient.stop();
				// �رն�λͼ��
				mBaiduMap.setMyLocationEnabled(false);
				mMapView.onDestroy();
				mMapView = null;				
				//��������ļ�
			    for (File file : cache.listFiles()) {  
			    	   System.out.println("Wxclbs������ɾ�����ļ����ǣ�"+file.getPath()+file.getName());	    	
			            file.delete();  
			        }  
			        cache.delete();				
				
				super.onDestroy();
				// ���� bitmap ��Դ
				bd.recycle();
					
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}
	
	
	private void demo_method(){
//		mBaiduMap = mMapView.getMap();
//		//ȡ��ά�����꣬��ǰ��ͼ״̬�����ű�������ת���ӽǵ�
//		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
//			public void onMapClick(LatLng point) {
//				currentPt = point;
//				updateMapState();
//			}
//
//			public boolean onMapPoiClick(MapPoi poi) {
//				return false;
//			}
//		});
		
//		//��ͼ����
//		float zoomLevel = 13.1f;
//		MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(zoomLevel);
//		mBaiduMap.animateMapStatus(u);
		
		
	}
	
	
	private void geoCoder(LatLng ptCenter){
		
		// ��ʼ������ģ�飬ע���¼�����
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(new MyGeoCoderListener());
		// ��Geo����
		mSearch.reverseGeoCode(new ReverseGeoCodeOption()
				.location(ptCenter));
		System.out.println("���÷������ȡ��ǰ��ַ");
		
	}
	
	private void cur_location(){
	
		mBaiduMap = mMapView.getMap();
		mCurrentMode = LocationMode.NORMAL;
		//mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.dingwei);
		mCurrentMarker=null;
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);
		System.out.println("������λͼ��");
		// ��λ��ʼ��
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(myListener);
		System.out.println("���ؼ���");
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		
		mLocClient.setLocOption(option);
		mLocClient.start();
		System.out.println("��ʼ��λ");
	}
	
	
	/**
	 * ��λSDK��������
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//System.out.println("��ʼ������λ���ź�");
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null || mMapView == null){
				System.out.println("location or mapview is null");
				return;
			}
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				System.out.println("��һ�ζ�λ������������");
				isFirstLoc = false;
				cur_pos = new LatLng(location.getLatitude(),
						location.getLongitude());
				geoCoder(cur_pos);
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(cur_pos);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	

	/**
	 * ��������������
	 */
	public class  MyGeoCoderListener implements  OnGetGeoCoderResultListener{

		@Override
		public void onGetGeoCodeResult(GeoCodeResult arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			// TODO Auto-generated method stub
			System.out.println("��ʼ��ȡ��ַ��Ϣ������������������������");
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(WxcWyxcLbsActivity.this, "��Ǹ��δ���ҵ���ַ���", Toast.LENGTH_LONG)
						.show();
				return;
			}
			//mBaiduMap.clear();
			mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.icon_geo)));
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
					.getLocation()));
			Toast.makeText(WxcWyxcLbsActivity.this, result.getAddress(),
					Toast.LENGTH_LONG).show();
		}
		
	}
	
	
	/**
	 * ���µ�ͼ״̬��ʾ���
	 */
	private void updateMapState() {
		String state = "";
		if (currentPt == null) {
			state = "�����������˫����ͼ�Ի�ȡ��γ�Ⱥ͵�ͼ״̬";
		} else {
			state = String.format("��ǰ���ȣ� %f ��ǰγ�ȣ�%f",
					currentPt.longitude, currentPt.latitude);
		}
		state += "\n";
		MapStatus ms = mBaiduMap.getMapStatus();
		state += String.format(
				"zoom=%.1f rotate=%d overlook=%d",
				ms.zoom, (int) ms.rotate, (int) ms.overlook);
		System.out.println(state);
	}

	
	
	/**
	 * ���µ�ͼ״̬��ʾ���
	 */
	private void addOverlay(List<WxcBusInfo> infos){
		LatLng latLng = null;  
        OverlayOptions overlayOptions = null;  
        Marker marker = null;  
		// add marker overlay
//		LatLng llA = new LatLng(39.963175, 116.400244);
//		LatLng llB = new LatLng(39.942821, 116.369199);
//		LatLng llC = new LatLng(39.939723, 116.425541);
//		LatLng llD = new LatLng(39.906965, 116.401394);
//        OverlayOptions ooB = new MarkerOptions().position(llB).icon(bdB)
//				.zIndex(5);
//		mBaiduMap.addOverlay(ooB);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		for (WxcBusInfo info:infos){
			System.out.println("���1��������............");
			//λ��
            latLng = new LatLng(info.getLatitude(), info.getLongitude());
            System.out.println("lat:"+info.getLatitude()+"  long:"+info.getLongitude());
           // ͼ��  
            overlayOptions = new MarkerOptions().position(latLng).icon(bd).zIndex(5);
            marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
            Bundle bundle = new Bundle();  
            bundle.putSerializable("info", info);  
            marker.setExtraInfo(bundle);  
		}
		// ����ͼ�Ƶ������һ����γ��λ��  
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);  
        mBaiduMap.setMapStatus(u);
        
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				//���marker�е�����  
                WxcBusInfo info = (WxcBusInfo) marker.getExtraInfo().get("info");
                InfoWindow mInfoWindow;  
                //����һ��TextView�û��ڵ�ͼ����ʾInfoWindow  
                TextView location = new TextView(getApplicationContext());  
                location.setBackgroundResource(R.drawable.popup);  
                location.setPadding(30, 20, 30, 50);  
                location.setText(info.getName());  
                //��marker���ڵľ�γ�ȵ���Ϣת������Ļ�ϵ�����  
                final LatLng ll = marker.getPosition();
                //�������
                double dist=distance(cur_pos, ll);
                System.out.println(info.getName()+"���뵱ǰλ�õľ���Ϊ��"+dist +"����");
                info.setDistance(Double.toString(dist));
                //��marker���ڵľ�γ�ȵ���Ϣת������Ļ�ϵ�����
                Point p = mBaiduMap.getProjection().toScreenLocation(ll);  
                p.y -= 47;  
                LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p); 
              //Ϊ������InfoWindow��ӵ���¼�                                
                mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(location), llInfo,-47,new OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick() {
						// TODO Auto-generated method stub
						 //����InfoWindow  
                        mBaiduMap.hideInfoWindow();  
					}
				}); 
                 
                //��ʾInfoWindow  
                mBaiduMap.showInfoWindow(mInfoWindow);
                System.out.println("��ʾ��ϸҳ�档����������������");
                //��ʾҳ��
                mMarkerInfoLy.setVisibility(View.VISIBLE);
                popupInfo(mMarkerInfoLy, info);
                return true;
                
			}
		});
        
        mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				System.out.println("��������ͼ����¼���������ϸҳ�档����������������");
				mMarkerInfoLy.setVisibility(View.INVISIBLE);
				mBaiduMap.hideInfoWindow();
			}
		});
        
        
	}
	
	
	/** 
	 * ���õ������mMarkerLy�Ŀؼ�  
	 */  
	private class ViewHolder  
	{  
	    ImageView infoImg;  
	    TextView infoName;  
	    TextView infoDistance;  
	    RatingBar infopingjia;  
	}  
	
	/** 
     * ����infoΪ�����ϵĿؼ�������Ϣ 
     *  
     * @param mMarkerInfo2 
     * @param info 
     */  
    protected void popupInfo(LinearLayout mMarkerLy, WxcBusInfo info)  
    {  
    	final WxcBusInfo cur_info=info;
        ViewHolder viewHolder = null;  
        if (mMarkerLy.getTag() == null)  {  
            viewHolder = new ViewHolder();  
            viewHolder.infoImg = (ImageView) mMarkerLy  
                    .findViewById(R.id.mark_bus_img);  
            viewHolder.infoName = (TextView) mMarkerLy  
                    .findViewById(R.id.info_name);  
            viewHolder.infoDistance = (TextView) mMarkerLy  
                    .findViewById(R.id.info_distance);  
            viewHolder.infopingjia = (RatingBar) mMarkerLy  
                    .findViewById(R.id.img_pingjia);  
            mMarkerLy.setTag(viewHolder);  
        }  
        viewHolder = (ViewHolder) mMarkerLy.getTag();  
        
	    System.out.println("�첽��ȡͼƬ��url��"+info.getImgUrl());			
		asyncImageLoad(viewHolder.infoImg, info.getImgUrl());				
        
        
        viewHolder.infoDistance.setText(info.getDistance()+"����");  
        viewHolder.infoName.setText(info.getName());  
        viewHolder.infopingjia.setRating(info.getRating()); 
        viewHolder.infoImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				   System.out.println("���ϴ����:"+cur_info.getName());
				   Intent intent = new Intent(WxcWyxcLbsActivity.this,WxcWyxcInfoActivity.class);
				   intent.putExtra("info", cur_info);
				   startActivity(intent);				
			}
		});
        
        
    }  
    
    
    private double distance(LatLng start,LatLng end){
    	GeoPoint p1LL=new GeoPoint((int) (start.longitude*1E6), (int) (start.latitude*1E6));
    	GeoPoint p2LL=new GeoPoint((int) (end.longitude*1E6), (int) (end.latitude*1E6));
    	double distance=DistanceUtil.getDistance(start,end);
    	distance=WxcPublicUtil.roundDouble(distance/1000, 2);
    	return distance; 
    }
    
		
	private void asyncImageLoad(ImageView imageView, String path) {  
        WxcPublicUtil.AsyncImageTask asyncImageTask = new WxcPublicUtil.AsyncImageTask(imageView,cache);  
        asyncImageTask.execute(path);  
    }     
    
    
}

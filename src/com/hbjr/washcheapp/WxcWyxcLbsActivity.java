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
    
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;    

	boolean isFirstLoc = true;// 是否首次定位
	
	/**
	 * 当前地点击点
	 */
	private LatLng currentPt;
	/**
	 * 当前定位的点
	 */
	private LatLng cur_pos;
	private Toast mToast;  
    private BMapManager mBMapManager;  
      
    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	    
	private InfoWindow mInfoWindow;
	// 初始化全局 bitmap 信息，不用时及时 recycle
		BitmapDescriptor bd = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		BitmapDescriptor bdB = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_markb);
		

		/**
		 * 缓存file
		 */
		File cache; // 缓存文件	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_wxc_wyxc_lbs);
		MyApplication.getInstance().addActivity(this);
		mMapView=(MapView) findViewById(R.id.bmapView);
		mMarkerInfoLy=(LinearLayout) findViewById(R.id.id_marker_info);
		//定位
		cur_location();
		//增加顶部栏
		addTop();
		//设置覆盖物
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
		tv.setText("周边洗车店");
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
		       // 退出时销毁定位
				mLocClient.stop();
				// 关闭定位图层
				mBaiduMap.setMyLocationEnabled(false);
				mMapView.onDestroy();
				mMapView = null;				
				//清楚缓存文件
			    for (File file : cache.listFiles()) {  
			    	   System.out.println("Wxclbs缓存中删除的文件名是："+file.getPath()+file.getName());	    	
			            file.delete();  
			        }  
			        cache.delete();				
				
				super.onDestroy();
				// 回收 bitmap 资源
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
//		//取精维度坐标，当前地图状态：缩放比例，旋转、视角等
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
		
//		//地图缩放
//		float zoomLevel = 13.1f;
//		MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(zoomLevel);
//		mBaiduMap.animateMapStatus(u);
		
		
	}
	
	
	private void geoCoder(LatLng ptCenter){
		
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(new MyGeoCoderListener());
		// 反Geo搜索
		mSearch.reverseGeoCode(new ReverseGeoCodeOption()
				.location(ptCenter));
		System.out.println("调用饭编码获取当前地址");
		
	}
	
	private void cur_location(){
	
		mBaiduMap = mMapView.getMap();
		mCurrentMode = LocationMode.NORMAL;
		//mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.dingwei);
		mCurrentMarker=null;
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, null));
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		System.out.println("开启定位图层");
		// 定位初始化
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(myListener);
		System.out.println("加载监听");
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		
		mLocClient.setLocOption(option);
		mLocClient.start();
		System.out.println("开始定位");
	}
	
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//System.out.println("开始监听到位置信号");
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null){
				System.out.println("location or mapview is null");
				return;
			}
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				System.out.println("第一次定位。。。。。。");
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
	 * 地理编码监听函数
	 */
	public class  MyGeoCoderListener implements  OnGetGeoCoderResultListener{

		@Override
		public void onGetGeoCodeResult(GeoCodeResult arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			// TODO Auto-generated method stub
			System.out.println("开始获取地址信息。。。。。。。。。。。。");
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(WxcWyxcLbsActivity.this, "抱歉，未能找到地址结果", Toast.LENGTH_LONG)
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
	 * 更新地图状态显示面板
	 */
	private void updateMapState() {
		String state = "";
		if (currentPt == null) {
			state = "点击、长按、双击地图以获取经纬度和地图状态";
		} else {
			state = String.format("当前经度： %f 当前纬度：%f",
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
	 * 更新地图状态显示面板
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
			System.out.println("添加1个覆盖物............");
			//位置
            latLng = new LatLng(info.getLatitude(), info.getLongitude());
            System.out.println("lat:"+info.getLatitude()+"  long:"+info.getLongitude());
           // 图标  
            overlayOptions = new MarkerOptions().position(latLng).icon(bd).zIndex(5);
            marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
            Bundle bundle = new Bundle();  
            bundle.putSerializable("info", info);  
            marker.setExtraInfo(bundle);  
		}
		// 将地图移到到最后一个经纬度位置  
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);  
        mBaiduMap.setMapStatus(u);
        
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				//获得marker中的数据  
                WxcBusInfo info = (WxcBusInfo) marker.getExtraInfo().get("info");
                InfoWindow mInfoWindow;  
                //生成一个TextView用户在地图中显示InfoWindow  
                TextView location = new TextView(getApplicationContext());  
                location.setBackgroundResource(R.drawable.popup);  
                location.setPadding(30, 20, 30, 50);  
                location.setText(info.getName());  
                //将marker所在的经纬度的信息转化成屏幕上的坐标  
                final LatLng ll = marker.getPosition();
                //计算距离
                double dist=distance(cur_pos, ll);
                System.out.println(info.getName()+"距离当前位置的距离为："+dist +"公里");
                info.setDistance(Double.toString(dist));
                //将marker所在的经纬度的信息转化成屏幕上的坐标
                Point p = mBaiduMap.getProjection().toScreenLocation(ll);  
                p.y -= 47;  
                LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p); 
              //为弹出的InfoWindow添加点击事件                                
                mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(location), llInfo,-47,new OnInfoWindowClickListener() {
					@Override
					public void onInfoWindowClick() {
						// TODO Auto-generated method stub
						 //隐藏InfoWindow  
                        mBaiduMap.hideInfoWindow();  
					}
				}); 
                 
                //显示InfoWindow  
                mBaiduMap.showInfoWindow(mInfoWindow);
                System.out.println("显示详细页面。。。。。。。。。");
                //显示页面
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
				System.out.println("监听到地图点击事件，隐藏详细页面。。。。。。。。。");
				mMarkerInfoLy.setVisibility(View.INVISIBLE);
				mBaiduMap.hideInfoWindow();
			}
		});
        
        
	}
	
	
	/** 
	 * 复用弹出面板mMarkerLy的控件  
	 */  
	private class ViewHolder  
	{  
	    ImageView infoImg;  
	    TextView infoName;  
	    TextView infoDistance;  
	    RatingBar infopingjia;  
	}  
	
	/** 
     * 根据info为布局上的控件设置信息 
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
        
	    System.out.println("异步获取图片：url："+info.getImgUrl());			
		asyncImageLoad(viewHolder.infoImg, info.getImgUrl());				
        
        
        viewHolder.infoDistance.setText(info.getDistance()+"公里");  
        viewHolder.infoName.setText(info.getName());  
        viewHolder.infopingjia.setRating(info.getRating()); 
        viewHolder.infoImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				   System.out.println("点击洗车店:"+cur_info.getName());
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

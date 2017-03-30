package com.hbjr.washcheapp;


import com.baidu.lbsapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviPara;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.util.MyApplication;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class WxcRoutePlanActivity extends Activity implements BaiduMap.OnMapClickListener,
OnGetRoutePlanResultListener  {
	
	LinearLayout mLl_parent;
	
	private TextView  txt_cur_regin;
	private TextView  txt_dest_regin;
	private TextView  road_content;
	private Button  daohang_go_client;
	private Button  daohang_go_web;	
    
	 /** 
     * ���� 
     */  
    private double dest_latitude;  
    /** 
     * γ�� 
     */  
    private double dest_longitude;  
    
    private LatLng dest_latlng;
    private LatLng start_latlng;
    private String dest_info;	

	   //���·�߽ڵ����
    Button mBtnPre = null;//��һ���ڵ�
    Button mBtnNext = null;//��һ���ڵ�
    int nodeIndex = -1;//�ڵ�����,������ڵ�ʱʹ��
    RouteLine route = null;
    OverlayManager routeOverlay = null;
    boolean useDefaultIcon = false;
    private TextView popupText = null;//����view

    //��ͼ��أ�ʹ�ü̳�MapView��MyRouteMapViewĿ������дtouch�¼�ʵ�����ݴ���
    //���������touch�¼���������̳У�ֱ��ʹ��MapView����
    MapView mMapView = null;    // ��ͼView
    BaiduMap mBaidumap = null;
    //�������
    RoutePlanSearch mSearch = null;    // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
 		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());		
		setContentView(R.layout.activity_wxc_route_plan);
		MyApplication.getInstance().addActivity(this);
		addTop();
		initById();
		dest_latitude=getIntent().getDoubleExtra("dest_latitude",0); 		
		dest_longitude=getIntent().getDoubleExtra("dest_longitud",0);	
		dest_info=getIntent().getStringExtra("dest_info");
		txt_cur_regin.setText(ConstantS.cur_regin);
		txt_dest_regin.setText(dest_info);
		//��ʼ����ͼ
		mMapView=(MapView) findViewById(R.id.road_bmapView);
        mBaidumap = mMapView.getMap();
        mBtnPre = (Button) findViewById(R.id.pre);
        mBtnNext = (Button) findViewById(R.id.next);
        mBtnPre.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.INVISIBLE);
        //��ͼ����¼�����
        mBaidumap.setOnMapClickListener(this);
        // ��ʼ������ģ�飬ע���¼�����
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
       
        
        //��������ڵ��·������
        route = null;
        mBtnPre.setVisibility(View.INVISIBLE);
        mBtnNext.setVisibility(View.INVISIBLE);
        mBaidumap.clear();
        
       dest_latlng= new LatLng(dest_latitude, dest_longitude);
       start_latlng=ConstantS.cur_latlng;
       System.out.println("start latlng is:"+start_latlng.latitude+" "+start_latlng.longitude);
       System.out.println("dest latlng is:"+dest_latlng.latitude+" "+dest_latlng.longitude);       
       //�������յ���Ϣ������tranist search ��˵��������������
       PlanNode stNode = PlanNode.withLocation(start_latlng);
       PlanNode enNode = PlanNode.withLocation(dest_latlng);
       mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(enNode));
	}

	
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_rootplan_parent);
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
				intent.setClass(WxcRoutePlanActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	public void initById(){
		txt_cur_regin=(TextView) findViewById(R.id.txt_cur_regin);
		txt_dest_regin=(TextView) findViewById(R.id.txt_dest_regin);
		road_content=(TextView) findViewById(R.id.road_content);
		daohang_go_client=(Button) findViewById(R.id.daohang_go_client);
		daohang_go_web=(Button) findViewById(R.id.daohang_go_web);
		daohang_go_web.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
                 goByWeb();				
			}
		});
		daohang_go_client.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				goByClient();
				
				
			}
		});
		
	
	}
	
	
	public void goByClient(){
		// ���� ��������
		NaviPara para = new NaviPara();
		para.startPoint = start_latlng;
		para.startName = "�����￪ʼ";
		para.endPoint =dest_latlng;
		para.endName = "���������";

		try {

			BaiduMapNavigation.openBaiduMapNavi(para, WxcRoutePlanActivity.this);

		} catch (BaiduMapAppNotSupportNaviException e) {
			e.printStackTrace();
			AlertDialog.Builder builder = new AlertDialog.Builder(WxcRoutePlanActivity.this);
			builder.setMessage("����δ��װ�ٶȵ�ͼapp��app�汾���ͣ����ȷ�ϰ�װ��");
			builder.setTitle("��ʾ");
			builder.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					dialog.dismiss();
					BaiduMapNavigation.getLatestBaiduMapApp(WxcRoutePlanActivity.this);
				}
			});

			builder.setNegativeButton("ȡ��", new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});

			builder.create().show();
		}							
		
	}
	
	public void goByWeb(){
		// ���� ��������
		NaviPara para = new NaviPara();
		para.startPoint = start_latlng;
		para.endPoint = dest_latlng;
		BaiduMapNavigation.openWebBaiduMapNavi(para, this);
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_route_plan, menu);
		return true;
	}


	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		// TODO Auto-generated method stub
	      if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
	            Toast.makeText(WxcRoutePlanActivity.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
	        }
	        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
	            //���յ��;�����ַ����壬ͨ�����½ӿڻ�ȡ�����ѯ��Ϣ
	            //result.getSuggestAddrInfo()
	            return;
	        }
	        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
	            nodeIndex = -1;
	            mBtnPre.setVisibility(View.VISIBLE);
	            mBtnNext.setVisibility(View.VISIBLE);
	            route = result.getRouteLines().get(0);
	            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
	            routeOverlay = overlay;
	            mBaidumap.setOnMarkerClickListener(overlay);
	            overlay.setData(result.getRouteLines().get(0));
	            overlay.addToMap();
	            overlay.zoomToSpan();
	        }		
		
	}
	
	   //����RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }
	
	
	@Override
	public void onGetTransitRouteResult(TransitRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		  mBaidumap.hideInfoWindow();
		
	}


	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	  /**
     * �ڵ����ʾ��
     *
     * @param v
     */
    public void nodeClick(View v) {
        if (route == null ||
                route.getAllStep() == null) {
            return;
        }
        if (nodeIndex == -1 && v.getId() == R.id.pre) {
        	return;
        }
        //���ýڵ�����
        if (v.getId() == R.id.next) {
            if (nodeIndex < route.getAllStep().size() - 1) {
            	nodeIndex++;
            } else {
            	return;
            }
        } else if (v.getId() == R.id.pre) {
        	if (nodeIndex > 0) {
        		nodeIndex--;
        	} else {
            	return;
            }
        }
        //��ȡ�ڽ����Ϣ
        LatLng nodeLocation = null;
        String nodeTitle = null;
        Object step = route.getAllStep().get(nodeIndex);
        if (step instanceof DrivingRouteLine.DrivingStep) {
            nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrace().getLocation();
            nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
        } 
//        else if (step instanceof WalkingRouteLine.WalkingStep) {
//            nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrace().getLocation();
//            nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
//        } 
//        else if (step instanceof TransitRouteLine.TransitStep) {
//            nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrace().getLocation();
//            nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
//        }
        if (nodeLocation == null || nodeTitle == null) {
            return;
        }
        //�ƶ��ڵ�������
        mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
        // show popup
        popupText = new TextView(WxcRoutePlanActivity.this);
        popupText.setBackgroundResource(R.drawable.popup);
        popupText.setTextColor(0xFF000000);
        popupText.setText(nodeTitle);
        mBaidumap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));

    }
	
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSearch.destroy();
        mMapView.onDestroy();
        super.onDestroy();
    }	
	

}

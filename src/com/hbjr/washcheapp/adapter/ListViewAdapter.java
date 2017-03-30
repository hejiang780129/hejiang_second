package com.hbjr.washcheapp.adapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.WxcRoutePlanActivity;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.util.WxcPublicUtil;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewAdapter extends BaseAdapter {
    LayoutInflater mInflat;
	Context context;
    List<WxcBusInfo> list;
    WxcBusInfo info;
    File cache;
	
	public ListViewAdapter(Context context,List<WxcBusInfo>  list,File cache) {
		super();
		mInflat=LayoutInflater.from(context);
		this.context=context;
		this.list=list;
		this.cache=cache;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public final class ViewHolder{
		//{"img_wxc",    "name_xcd","   pingjia_xcd",    "dizhi_xcd",   "kanpinglun_xcd" ,  "img_jiage",        "jiage",    "img_daohang",               "juli",      "goumairenshu"};
		public ImageView img_wxc;
		public TextView name_xcd;
		public RatingBar pingjia_xcd;
		public TextView dizhi_xcd;
		public TextView jiage_down;
		public TextView img_jiage;
		public TextView jiage;
		public TextView jiage_old;
		public Button img_daohang;
		public TextView juli;
		public TextView goumairenshu;
	}
	
	
	
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if (convertView==null){
			viewHolder=new ViewHolder();
			convertView=mInflat.inflate(R.layout.listview_wxc, null);
			viewHolder.img_wxc=(ImageView) convertView.findViewById(R.id.img_wxc);
			viewHolder.name_xcd=(TextView) convertView.findViewById(R.id.name_xcd);
			viewHolder.pingjia_xcd=(RatingBar) convertView.findViewById(R.id.pingjia_xcd);
			viewHolder.dizhi_xcd=(TextView) convertView.findViewById(R.id.dizhi_xcd);
			viewHolder.jiage_down=(TextView) convertView.findViewById(R.id.jiage_down);
			viewHolder.jiage_old=(TextView) convertView.findViewById(R.id.jiage_old);
			viewHolder.img_jiage=(TextView) convertView.findViewById(R.id.img_jiage);
			viewHolder.jiage=(TextView) convertView.findViewById(R.id.jiage);
			viewHolder.img_daohang=(Button) convertView.findViewById(R.id.img_daohang);
			viewHolder.juli=(TextView) convertView.findViewById(R.id.juli);
			viewHolder.goumairenshu=(TextView) convertView.findViewById(R.id.goumairenshu);
			convertView.setTag(viewHolder);
		}else{
			 viewHolder=(ViewHolder) convertView.getTag();
		}
		info=(WxcBusInfo)list.get(position);
		List prices=info.getPrices();
		HashMap price=(HashMap) prices.get(0);
		
		System.out.println("取得的img id 是："+info.getImgId());
		System.out.println("取得的img url 是："+info.getImgUrl());		
		if (info.getImgId()>0){
		viewHolder.img_wxc.setImageResource(info.getImgId());
		}else{
		//从网络异步获取图片
	     System.out.println("异步获取图片：url："+info.getImgUrl());			
		 asyncImageLoad(viewHolder.img_wxc, info.getImgUrl());				
		}				
		viewHolder.name_xcd.setText(info.getName());
		viewHolder.pingjia_xcd.setRating(info.getRating());
		viewHolder.dizhi_xcd.setText(info.getAddress());
		viewHolder.jiage.setText((String)price.get("serviceprice"));
		viewHolder.jiage_old.setText((String)price.get("serviceprice_old"));
		viewHolder.jiage_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); 
		int cur_down=Integer.parseInt(info.getCutdown());
		if (cur_down>0){
		viewHolder.jiage_down.setText("立省"+info.getCutdown()+"元");
		}
		viewHolder.juli.setText(info.getDistance());
		viewHolder.goumairenshu.setText(info.getBuynum()+"人购买");
//		viewHolder.img_wxc.setImageResource(Integer.parseInt(list.get(position).get("img_wxc").toString()));
//		viewHolder.name_xcd.setText((String) list.get(position).get("name_xcd"));
//		viewHolder.pingjia_xcd.setImageResource(Integer.parseInt(list.get(position).get("pingjia_xcd").toString()));
//		viewHolder.dizhi_xcd.setText((String) list.get(position).get("dizhi_xcd"));
//		viewHolder.kanpinglun_xcd.setText((String) list.get(position).get("kanpinglun_xcd"));
//		viewHolder.img_jiage.setImageResource(Integer.parseInt(list.get(position).get("img_jiage").toString()));
//		viewHolder.jiage.setText((String) list.get(position).get("jiage"));
//		viewHolder.img_daohang.setImageResource(Integer.parseInt(list.get(position).get("img_daohang").toString()));
//		viewHolder.juli.setText((String) list.get(position).get("juli"));
//		viewHolder.goumairenshu.setText((String) list.get(position).get("goumairenshu"));
		
		viewHolder.img_daohang.setOnClickListener(new MyListener(position));
		
		return convertView;
	}
	
	
	private void asyncImageLoad(ImageView imageView, String path) {  
        WxcPublicUtil.AsyncImageTask asyncImageTask = new WxcPublicUtil.AsyncImageTask(imageView,cache);  
        asyncImageTask.execute(path);  
    } 
	
	
	private class MyListener implements OnClickListener{  
      int mPosition;  
      public MyListener(int inPosition){  
          mPosition= inPosition;  
      }  
      @Override  
      public void onClick(View v) {  
          // TODO Auto-generated method stub  
          System.out.println("触发导航去按钮，当前位置是："+mPosition);
          Intent intent =new Intent();
          intent.putExtra("dest_latitude", info.getLatitude());
          intent.putExtra("dest_longitud", info.getLongitude());
          intent.putExtra("dest_info", info.getName());
          intent.setClass(context, WxcRoutePlanActivity.class);
          context.startActivity(intent);          
      }  
        
  }  	
	
	
}

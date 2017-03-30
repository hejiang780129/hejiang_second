package com.hbjr.washcheapp.adapter;

import java.util.HashMap;
import java.util.List;

import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.adapter.ListViewAdapter.ViewHolder;
import com.hbjr.washcheapp.bean.WxcBusInfo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListViewPriceAdapter extends BaseAdapter {

	LayoutInflater mInflat;
	Context context;
    List<HashMap> list;
   	
	public ListViewPriceAdapter(Context context,List<HashMap>  list) {
		super();
		mInflat=LayoutInflater.from(context);
		this.context=context;
		this.list=list;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public final class ViewHolder{
		
		public TextView tv_price;
		public TextView tv_service;
		public Button bt_go;
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
			convertView=mInflat.inflate(R.layout.listview_wxc_prices, null);
			viewHolder.tv_price=(TextView) convertView.findViewById(R.id.lv_price);
			viewHolder.tv_service=(TextView) convertView.findViewById(R.id.lv_service);
			viewHolder.bt_go=(Button) convertView.findViewById(R.id.bt_go);
			convertView.setTag(viewHolder);
		}else{
			 viewHolder=(ViewHolder) convertView.getTag();
		}
		HashMap price=(HashMap) list.get(position);
		viewHolder.tv_price.setText((String)price.get("serviceprice"));
		viewHolder.tv_service.setText((String)price.get("servicename"));
		
		return convertView;
	}
	
}

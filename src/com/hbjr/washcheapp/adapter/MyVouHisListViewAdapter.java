package com.hbjr.washcheapp.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.bean.WxcUserVou;
import com.hbjr.washcheapp.util.WxcPublicUtil;

import android.content.ContentValues;
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

public class MyVouHisListViewAdapter extends BaseAdapter {
    LayoutInflater mInflat;
	Context context;
    List<WxcUserOrder> list;
    WxcUserOrder info;
    boolean isValid=true;
	
	public MyVouHisListViewAdapter(Context context,List<WxcUserOrder>  list) {
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

		public TextView order_list_his_date;
		public TextView order_list_his_xcd;
		public TextView order_list_his_service;
		public TextView order_list_his_status;
		public TextView order_list_his_vpay;
		public TextView order_list_his_opay;
		public TextView order_list_his_total_num;
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
			convertView=mInflat.inflate(R.layout.my_vou_history_list, null);
			viewHolder.order_list_his_date=(TextView) convertView.findViewById(R.id.order_list_his_date);
			viewHolder.order_list_his_xcd=(TextView) convertView.findViewById(R.id.order_list_his_xcd);
			viewHolder.order_list_his_service=(TextView) convertView.findViewById(R.id.order_list_his_service);
			viewHolder.order_list_his_status=(TextView) convertView.findViewById(R.id.order_list_his_status);
			viewHolder.order_list_his_vpay=(TextView) convertView.findViewById(R.id.order_list_his_vpay);
			viewHolder.order_list_his_opay=(TextView) convertView.findViewById(R.id.order_list_his_opay);
			viewHolder.order_list_his_total_num=(TextView) convertView.findViewById(R.id.order_list_his_total_num);
			convertView.setTag(viewHolder);
		}else{
			 viewHolder=(ViewHolder) convertView.getTag();
		}
		info=(WxcUserOrder) list.get(position);
        viewHolder.order_list_his_date.setText(info.getOrder_date());
        viewHolder.order_list_his_xcd.setText(info.getXcd_mc());
        viewHolder.order_list_his_service.setText(info.getOrder_service_name());
        viewHolder.order_list_his_status.setText(info.getOrder_state_dsc());
        viewHolder.order_list_his_vpay.setText(info.getPay_vou()+"Ԫ");
        viewHolder.order_list_his_opay.setText(info.getPay_online()+"Ԫ");
        viewHolder.order_list_his_total_num.setText(info.getPay()+"Ԫ");
		return convertView;
	}
	
}

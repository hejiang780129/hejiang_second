package com.hbjr.washcheapp.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.R;
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

public class MyVouListViewAdapter extends BaseAdapter {
    LayoutInflater mInflat;
	Context context;
    List<WxcUserVou> list;
    WxcUserVou info;
    boolean isValid=true;
	
	public MyVouListViewAdapter(Context context,List<WxcUserVou>  list) {
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
		//{"img_wxc",    "name_xcd","   pingjia_xcd",    "dizhi_xcd",   "kanpinglun_xcd" ,  "img_jiage",        "jiage",    "img_daohang",               "juli",      "goumairenshu"};
		public TextView my_vou_list_num;
		public TextView my_vou_list_item_total;
		public TextView my_vou_list_getby;
		public TextView my_vou_list_getdate;
		public TextView my_vou_list_limitdate;
		public TextView my_vou_list_status;
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
			convertView=mInflat.inflate(R.layout.my_vou_listview, null);
			viewHolder.my_vou_list_num=(TextView) convertView.findViewById(R.id.my_vou_list_num);
			viewHolder.my_vou_list_getby=(TextView) convertView.findViewById(R.id.my_vou_list_getby);
			viewHolder.my_vou_list_getdate=(TextView) convertView.findViewById(R.id.my_vou_list_getdate);
			viewHolder.my_vou_list_item_total=(TextView) convertView.findViewById(R.id.my_vou_list_item_total);
			viewHolder.my_vou_list_limitdate=(TextView) convertView.findViewById(R.id.my_vou_list_limitdate);
			viewHolder.my_vou_list_status=(TextView) convertView.findViewById(R.id.my_vou_list_status);
			convertView.setTag(viewHolder);
		}else{
			 viewHolder=(ViewHolder) convertView.getTag();
		}
		info=(WxcUserVou) list.get(position);
		viewHolder.my_vou_list_num.setText("´ú½ðÈ¯"+(position+1));
		viewHolder.my_vou_list_getby.setText(info.getGet_type_dsc());
		viewHolder.my_vou_list_getdate.setText(info.getGet_date());
		viewHolder.my_vou_list_item_total.setText(info.getTotal_num()+"Ôª");
		viewHolder.my_vou_list_limitdate.setText(info.getLimit_date());
		viewHolder.my_vou_list_status.setText(info.getCur_status_desc());	
		return convertView;
	}
	
}

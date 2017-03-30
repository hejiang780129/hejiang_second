package com.hbjr.washcheapp.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.WxcWyxcList;
import com.hbjr.washcheapp.bean.WxcMessage;
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

public class MyMessageListViewAdapter extends BaseAdapter {
    LayoutInflater mInflat;
	Context context;
    List<WxcMessage> list;
    WxcMessage info;
    boolean isValid=true;
	
	public MyMessageListViewAdapter(Context context,List<WxcMessage>  list) {
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
		public TextView my_message_title;
		public TextView my_message_type;
		public TextView my_message_content;
		public TextView my_message_date;

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
			convertView=mInflat.inflate(R.layout.my_message_listview, null);
			viewHolder.my_message_title=(TextView) convertView.findViewById(R.id.my_message_title);
			viewHolder.my_message_type=(TextView) convertView.findViewById(R.id.my_message_type);
			viewHolder.my_message_content=(TextView) convertView.findViewById(R.id.my_message_content);
			viewHolder.my_message_date=(TextView) convertView.findViewById(R.id.my_message_date);
			convertView.setTag(viewHolder);
		}else{
			 viewHolder=(ViewHolder) convertView.getTag();
		}
		info=(WxcMessage) list.get(position);
		viewHolder.my_message_title.setText(info.getMessage__title());	
		viewHolder.my_message_type.setText(info.getMessaage_type_dsc());
		viewHolder.my_message_content.setText(info.getMessage_content());
		viewHolder.my_message_date.setText(info.getMessage_date());		
		return convertView;
	}
	
}

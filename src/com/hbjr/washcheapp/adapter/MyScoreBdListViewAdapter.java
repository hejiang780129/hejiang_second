package com.hbjr.washcheapp.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.bean.WxcUserOrder;
import com.hbjr.washcheapp.bean.WxcUserScore;
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

public class MyScoreBdListViewAdapter extends BaseAdapter {
    LayoutInflater mInflat;
	Context context;
    List<WxcUserScore> list;
    WxcUserScore info;
    String score_type="";
	
	public MyScoreBdListViewAdapter(Context context,List<WxcUserScore>  list) {
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

		public TextView my_score_bd_list_num;
		public TextView my_score_bd_list_sum;
		public TextView my_score_bd_list_getby;
		public TextView my_score_bd_list_intro;
		public TextView my_score_bd_list_getdate;
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
			convertView=mInflat.inflate(R.layout.my_score_bd_listview, null);	
			viewHolder.my_score_bd_list_num=(TextView) convertView.findViewById(R.id.my_score_bd_list_num);
			viewHolder.my_score_bd_list_sum=(TextView) convertView.findViewById(R.id.my_score_bd_list_sum);
			viewHolder.my_score_bd_list_getby=(TextView) convertView.findViewById(R.id.my_score_bd_list_getby);
			viewHolder.my_score_bd_list_intro=(TextView) convertView.findViewById(R.id.my_score_bd_list_intro);
			viewHolder.my_score_bd_list_getdate=(TextView) convertView.findViewById(R.id.my_score_bd_list_getdate);
			convertView.setTag(viewHolder);
		}else{
			 viewHolder=(ViewHolder) convertView.getTag();
		}
		viewHolder.my_score_bd_list_num.setText("积分记录"+(position+1));
		info=(WxcUserScore) list.get(position);
		viewHolder.my_score_bd_list_sum.setText(info.getScore()+"分");
		score_type=info.getGet_type();
		if (score_type.equalsIgnoreCase("0")){
			viewHolder.my_score_bd_list_getby.setText("获取积分");
			
		}else{
			viewHolder.my_score_bd_list_getby.setText("兑换积分");
		}
		viewHolder.my_score_bd_list_intro.setText(info.getGet_type_dsc());   
		viewHolder.my_score_bd_list_getdate.setText(info.getGet_date());
		return convertView;
	}
	
}

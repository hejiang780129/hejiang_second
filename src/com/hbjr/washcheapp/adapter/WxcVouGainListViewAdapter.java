package com.hbjr.washcheapp.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.bean.WxcVouExercise;
import com.hbjr.washcheapp.util.WxcPublicUtil;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WxcVouGainListViewAdapter extends BaseAdapter {
    LayoutInflater mInflat;
	Context context;
    List<WxcVouExercise> list;
    WxcVouExercise info;
    boolean isValid=true;
	
	public WxcVouGainListViewAdapter(Context context,List<WxcVouExercise>  list) {
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

		public TextView my_vou_exercise_name;
		public TextView my_vou_exercise_edate;
		public TextView my_vou_exercise_num;
		public ImageView my_vou_more_go;
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
			
			convertView=mInflat.inflate(R.layout.my_vou_more_listview, null);
			viewHolder.my_vou_exercise_name=(TextView) convertView.findViewById(R.id.my_vou_exercise_name);
			viewHolder.my_vou_exercise_edate=(TextView) convertView.findViewById(R.id.my_vou_exercise_edate);
			viewHolder.my_vou_exercise_num=(TextView) convertView.findViewById(R.id.my_vou_exercise_num);
			viewHolder.my_vou_more_go=(ImageView) convertView.findViewById(R.id.my_vou_more_go);
			convertView.setTag(viewHolder);
		}else{
			 viewHolder=(ViewHolder) convertView.getTag();
		}
		info=(WxcVouExercise) list.get(position);
        viewHolder.my_vou_exercise_name.setText(info.getName());
        if(info.getEdate().equalsIgnoreCase("1")){
            viewHolder.my_vou_exercise_edate.setText("长期有效");
        }else{
            viewHolder.my_vou_exercise_edate.setText(info.getEdate()+"截止");        	
        }

        viewHolder.my_vou_exercise_num.setText(info.getVou_num()+"元");
        
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, 70);
        convertView.setLayoutParams(lp);
		return convertView;
	}
	
}

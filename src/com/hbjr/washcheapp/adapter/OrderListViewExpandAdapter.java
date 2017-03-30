package com.hbjr.washcheapp.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.WxcFinishOrderActivity;
import com.hbjr.washcheapp.WxcFinishOrderCommActivity;
import com.hbjr.washcheapp.adapter.ListViewAdapter.ViewHolder;
import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.bean.WxcUserOrder;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class OrderListViewExpandAdapter extends BaseExpandableListAdapter {
    
	private List<WxcUserOrder> orderList=null;
	private List<WxcUserOrder> orderList_all=null;
    LayoutInflater mInflat;
	Context context;
	WxcUserOrder orderinfo;
	WxcUserOrder orderinfo_sub;
	String tmp;
	
	 public OrderListViewExpandAdapter(Context context,List orderList,List orderListAll) {
         this.context = context;
         this.orderList=orderList;
         this.orderList_all=orderListAll;
         this.mInflat=LayoutInflater.from(context);
 }
	
	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return orderList.get(arg0);
	}

	@Override
	public long getChildId(int arg0, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if (convertView==null){
			viewHolder=new ViewHolder();
			convertView=mInflat.inflate(R.layout.my_order_listview_sub, null);
			viewHolder.order_list_sub_num=(TextView) convertView.findViewById(R.id.order_list_sub_num);
			viewHolder.order_list_sub_edate=(TextView) convertView.findViewById(R.id.order_list_sub_edate);
			viewHolder.order_list_sub_status=(TextView) convertView.findViewById(R.id.order_list_sub_status);
			viewHolder.order_list_sub_opay=(TextView) convertView.findViewById(R.id.order_list_sub_opay);
			viewHolder.order_list_sub_vpay=(TextView) convertView.findViewById(R.id.order_list_sub_vpay);			
			viewHolder.order_list_sub_comment=(TextView) convertView.findViewById(R.id.order_list_sub_comment);			
			viewHolder.order_list_sub_button=(Button) convertView.findViewById(R.id.order_list_sub_button);
			viewHolder.order_list_sub_button_add=(Button) convertView.findViewById(R.id.order_list_sub_button_add);
			viewHolder.order_list_sub_img_pingjia=(ImageView)convertView.findViewById(R.id.order_list_sub_img_pingjia);
			viewHolder.order_list_sub_rating=(RatingBar)convertView.findViewById(R.id.order_list_sub_rating);			
			convertView.setTag(viewHolder);
		}else{
			 viewHolder=(ViewHolder) convertView.getTag();
		}		
		orderinfo_sub=(WxcUserOrder) orderList.get(groupPosition);
		System.out.println("子列表item时order是："+orderinfo_sub.getId()+"groupPosition 是"+groupPosition);
		viewHolder.order_list_sub_num.setText(orderinfo_sub.getOrder_no());
		viewHolder.order_list_sub_edate.setText(orderinfo_sub.getOrder_enddate());
		viewHolder.order_list_sub_status.setText(orderinfo_sub.getOrder_state_dsc());
		viewHolder.order_list_sub_opay.setText(orderinfo_sub.getPay_online()+"元");
		viewHolder.order_list_sub_vpay.setText(orderinfo_sub.getPay_vou()+"元");

		List list=orderinfo_sub.getDiscuss();
		String str_com="";
		if (list!=null&&list.size()!=0){
		HashMap hm=(HashMap)list.get(list.size()-1);
		  if (hm!=null){
		  str_com=hm.get("comment")+"";
		  }
		}
		viewHolder.order_list_sub_comment.setText(str_com);
		//如果该订单如果没有评价信息，则可以评价
		if (orderinfo_sub.getOrder_evaluate()==-1){
			viewHolder.order_list_sub_button.setVisibility(View.VISIBLE);
		}else{
			viewHolder.order_list_sub_button.setVisibility(View.GONE);	
		}
		if (orderinfo_sub.getOrder_evaluate()==0){
			viewHolder.order_list_sub_img_pingjia.setBackgroundResource(R.drawable.com_good);
		}else if (orderinfo_sub.getOrder_evaluate()==1){
			viewHolder.order_list_sub_img_pingjia.setBackgroundResource(R.drawable.com_mid);			
		}else if (orderinfo_sub.getOrder_evaluate()==2){
			viewHolder.order_list_sub_img_pingjia.setBackgroundResource(R.drawable.com_bad);
		}else{
			viewHolder.order_list_sub_img_pingjia.setBackgroundResource(R.drawable.com_no);
		}
		viewHolder.order_list_sub_rating.setRating(Float.parseFloat(orderinfo_sub.getOrder_rating()));
		viewHolder.order_list_sub_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("触发完成订单按钮事件========================");		
			    Intent intent=new Intent();
			    intent.setClass(context, WxcFinishOrderActivity.class);
			    intent.putExtra("order",orderinfo_sub);
			    context.startActivity(intent);
			}
		});
		
		
//		viewHolder.order_list_sub_button_add.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub	
//			    Intent intent=new Intent();
//			    intent.setClass(context, WxcFinishOrderCommActivity.class);
//				System.out.println("追加评论时当前的order是："+orderinfo_sub.getId());
//			    intent.putExtra("order",orderinfo_sub);
//			    context.startActivity(intent);
//			}
//		});
		
		
		
		
		return convertView;
		
	}
	
	

	

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return orderList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return orderList.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	
	public final class ViewHolder{
	    public TextView order_list_date;
	    public TextView order_list_xcd;
	    public TextView order_list_price;
	    public TextView order_list_status;
	    public TextView order_list_service;
	    public Button order_list_button;
	    
	    public TextView order_list_sub_num;
	    public Button order_list_sub_button;
	    public TextView order_list_sub_edate;
	    public TextView order_list_sub_status;
	    public TextView order_list_sub_vpay;	
	    public TextView order_list_sub_opay;	    
	    public TextView order_list_sub_comment;
	    public Button order_list_sub_button_add;
	    
	    public ImageView order_list_sub_img_pingjia;
	    public RatingBar order_list_sub_rating;
	}
	
	
	
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder viewHolder=null;
		if (convertView==null){
			viewHolder=new ViewHolder();
			convertView=mInflat.inflate(R.layout.my_order_listview, null);
			viewHolder.order_list_date=(TextView) convertView.findViewById(R.id.order_list_date);
			viewHolder.order_list_xcd=(TextView) convertView.findViewById(R.id.order_list_xcd);
			viewHolder.order_list_price=(TextView) convertView.findViewById(R.id.order_list_price);
			viewHolder.order_list_status=(TextView) convertView.findViewById(R.id.order_list_status);
			viewHolder.order_list_button=(Button) convertView.findViewById(R.id.order_list_button);
			viewHolder.order_list_service=(TextView) convertView.findViewById(R.id.order_list_service);
			convertView.setTag(viewHolder);
		}else{
			 viewHolder=(ViewHolder) convertView.getTag();
		}
		orderinfo=(WxcUserOrder) orderList.get(groupPosition);
		System.out.println("父列表item时order是："+orderinfo.getId()+"groupPosition 是"+groupPosition);
		viewHolder.order_list_date.setText(orderinfo.getOrder_date());
		viewHolder.order_list_xcd.setText(orderinfo.getXcd_mc());
		viewHolder.order_list_price.setText(orderinfo.getPay()+"元");
		viewHolder.order_list_status.setText(orderinfo.getOrder_state_dsc());
		viewHolder.order_list_service.setText(orderinfo.getOrder_service_name());
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, 120);
        convertView.setLayoutParams(lp);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}

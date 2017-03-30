package com.hbjr.washcheapp.adapter;

import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;



public class ListViewCommentAdapter  extends BaseAdapter {

	private Context context;
	/**
	 * 显示数据的集合
	 */
	private List<Map<String, Object>> strings;
	/**
	 * 每层楼的背景色，此数组的大小必须与显示数据集合的大小相同
	 */
	private int[] color = null;
	/**
	 * 每层楼之间的间距
	 */
	private int pad = 2;

	public ListViewCommentAdapter(Context context, List<Map<String, Object>> strings,
			int[] color) {
		this.context = context;
		this.strings = strings;
		this.color = color;
	}

	@Override
	public int getCount() {
		return strings != null ? strings.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return strings.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		System.out.println("调用1次getView,此时的position 是"+position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_comment_add, null);
			// 获得显示用户名、楼层数、用户评论内容的TextView
			holder.name = (TextView) convertView.findViewById(R.id.add_name);
			holder.page = (TextView) convertView.findViewById(R.id.add_page);
			holder.comment = (TextView) convertView.findViewById(R.id.add_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 显示最后一个用户的评论内容
		//holder.textView.setText(strings.get(position).get("lastComment").toString());
		// 获得用户名的字符串数组和用户评论内容的字符串数组
		String strs = (String) strings.get(position).get("name");
		String strs1 =(String) strings.get(position).get("comment");
		String strs2 = (String) strings.get(position).get("date");
		holder.name.setText(strs);
		holder.page.setText(strs2);
		holder.comment.setText(strs1);
		convertView.setPadding(5,5,5,5);
		return convertView;
	}

	/**
	 * 递归加载楼层的方法
	 * 
	 * @param context上下文的对像
	 * @param 递归的控制参数
	 *            ，同时也是取用户评论信息和背景色的下标，引参数的大小必须是从集合中获得的用户名数组或从集合中获得的评论内容数据的大小减一
	 * @param pad
	 *            楼层的间距
	 * @param strs
	 *            用户名的字符串数组
	 * @param strs1
	 *            用户相应评论内容的字符串数组
	 * @param color
	 *            背景色的数组
	 * @return 返回一个楼层的LinearLayout布局对象
	 */
	private LinearLayout add(Context context, int i, int pad, String[] strs,
			String[] strs1, String[] strs2,int[] color) {
		// 加载一个布局
		LinearLayout layout1 = (LinearLayout) LayoutInflater.from(context)
				.inflate(R.layout.listview_comment_add, null);
		// 获得显示用户名、楼层数、用户评论内容的TextView
		TextView name = (TextView) layout1.findViewById(R.id.add_name);
		TextView page = (TextView) layout1.findViewById(R.id.add_page);
		TextView comment = (TextView) layout1.findViewById(R.id.add_content);
		// 设置显示用户名、楼层数、用户评论内容TextView的内容
		name.setText(strs[i]);
		page.setText(strs2[i]);
		comment.setText(strs1[i]);
		// 动态生成一个LinearLayout来装载获得的布局
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(R.drawable.shape);
		layout.setPadding(pad, pad, pad, pad);
		// 当i的值为零时，递归结束
		if (i != 0) {
			layout.addView(add(context, --i, pad, strs, strs1,strs2,color));
		}
		layout.addView(layout1);
		return layout;
	}

	private class ViewHolder {
		public TextView name;
		public TextView page;
		public TextView comment;
	}
	
	
}

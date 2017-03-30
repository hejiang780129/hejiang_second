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
	 * ��ʾ���ݵļ���
	 */
	private List<Map<String, Object>> strings;
	/**
	 * ÿ��¥�ı���ɫ��������Ĵ�С��������ʾ���ݼ��ϵĴ�С��ͬ
	 */
	private int[] color = null;
	/**
	 * ÿ��¥֮��ļ��
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
		System.out.println("����1��getView,��ʱ��position ��"+position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_comment_add, null);
			// �����ʾ�û�����¥�������û��������ݵ�TextView
			holder.name = (TextView) convertView.findViewById(R.id.add_name);
			holder.page = (TextView) convertView.findViewById(R.id.add_page);
			holder.comment = (TextView) convertView.findViewById(R.id.add_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// ��ʾ���һ���û�����������
		//holder.textView.setText(strings.get(position).get("lastComment").toString());
		// ����û������ַ���������û��������ݵ��ַ�������
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
	 * �ݹ����¥��ķ���
	 * 
	 * @param context�����ĵĶ���
	 * @param �ݹ�Ŀ��Ʋ���
	 *            ��ͬʱҲ��ȡ�û�������Ϣ�ͱ���ɫ���±꣬�������Ĵ�С�����ǴӼ����л�õ��û��������Ӽ����л�õ������������ݵĴ�С��һ
	 * @param pad
	 *            ¥��ļ��
	 * @param strs
	 *            �û������ַ�������
	 * @param strs1
	 *            �û���Ӧ�������ݵ��ַ�������
	 * @param color
	 *            ����ɫ������
	 * @return ����һ��¥���LinearLayout���ֶ���
	 */
	private LinearLayout add(Context context, int i, int pad, String[] strs,
			String[] strs1, String[] strs2,int[] color) {
		// ����һ������
		LinearLayout layout1 = (LinearLayout) LayoutInflater.from(context)
				.inflate(R.layout.listview_comment_add, null);
		// �����ʾ�û�����¥�������û��������ݵ�TextView
		TextView name = (TextView) layout1.findViewById(R.id.add_name);
		TextView page = (TextView) layout1.findViewById(R.id.add_page);
		TextView comment = (TextView) layout1.findViewById(R.id.add_content);
		// ������ʾ�û�����¥�������û���������TextView������
		name.setText(strs[i]);
		page.setText(strs2[i]);
		comment.setText(strs1[i]);
		// ��̬����һ��LinearLayout��װ�ػ�õĲ���
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(R.drawable.shape);
		layout.setPadding(pad, pad, pad, pad);
		// ��i��ֵΪ��ʱ���ݹ����
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

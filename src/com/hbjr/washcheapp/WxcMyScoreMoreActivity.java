package com.hbjr.washcheapp;

import java.util.List;

import com.hbjr.washcheapp.adapter.WxcScoreListViewAdapter;
import com.hbjr.washcheapp.bean.WxcScore;
import com.hbjr.washcheapp.util.MyApplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class WxcMyScoreMoreActivity extends Activity {

	private ListView my_score_more_list;
	private List score_list;
	private List score_list_2;
	
	private LinearLayout mLl_parent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_my_score_more);
		MyApplication.getInstance().addActivity(this);
		my_score_more_list=(ListView) findViewById(R.id.my_score_more_list);
		//score_list=WxcScore.info;
		//WxcScore.initScoreList();
		score_list_2=WxcScore.info_2;
		my_score_more_list.setAdapter(new WxcScoreListViewAdapter(this, score_list_2));
		my_score_more_list.setOnItemClickListener(new MyOnItemClickListener(score_list_2));
		addTop();
	}
	
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_score_more_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("赚更多积分");
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		ib_handle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(WxcMyScoreMoreActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
private class MyOnItemClickListener implements OnItemClickListener{
        
		List  list;
		MyOnItemClickListener(List list){
			this.list=list;
		}
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
		   WxcScore score=(WxcScore) list.get(position);
		   System.out.println("点击活动名:"+score.getName());
		   Intent intent = new Intent(WxcMyScoreMoreActivity.this,WxcBeneifActivity.class);
		   intent.putExtra("beneif", score);
		   startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_my_score_more, menu);
		return true;
	}

}

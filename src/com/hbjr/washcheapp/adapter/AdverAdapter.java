package com.hbjr.washcheapp.adapter;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbjr.washcheapp.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AdverAdapter extends PagerAdapter {
	
	private List views=null;
	
    
    public AdverAdapter(List views){
		this.views=views;	
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		 ((ViewPager) container).removeView((View)views.get(position));  
		//System.out.println("remove一次图片......."+position);
	}


	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView((View) views.get(position),0);
		//System.out.println("调用一次加载imageview......."+position);
		return views.get(position);
	}


	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}
	
//	private class BannerHolder {
//		private View view;
//		private ImageView imageview;
//
//		private BannerHolder(View v) {
//			view = v;
//		}
//
//		ImageView getImageView() {
//			if (imageview == null) {
//				imageview = (ImageView) view.findViewById(R.id.banner);
//			}
//			return imageview;
//		}
//	}
	

}

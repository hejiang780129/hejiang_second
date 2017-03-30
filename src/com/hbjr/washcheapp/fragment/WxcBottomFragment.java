package com.hbjr.washcheapp.fragment;

import com.baidu.navisdk.comapi.mapcontrol.MapParams.Const;
import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.util.WxcPublicUtil;
import com.hbjr.washcheapp.widget.BadgeView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class WxcBottomFragment extends LinearLayout implements OnClickListener {

	private static boolean is_already_show_my=false; 
	private static boolean is_already_show_set=false;	
	
	private static int bottom_default_index=0;
	private static int bottom_cur_index;
	
	private static View[] bottom_views;
	
	private static final String TAG_ICON_0 = "icon_tag_0";
	private static final String TAG_ICON_1 = "icon_tag_1";
	private static final String TAG_ICON_2 = "icon_tag_2";
		
	private static final String TAG_TEXT_0 = "text_tag_0";
	private static final String TAG_TEXT_1 = "text_tag_1";
	private static final String TAG_TEXT_2 = "text_tag_2";
		
	private static final int COLOR_UNSELECT = Color.GRAY;
	private static int COLOR_SELECT=Color.RED;
	
	private OnIndicateListener onbottomlistener;
	
	public BadgeView bgv_fir;
	public BadgeView bgv_my;	
	public BadgeView bgv_set;
	
	Context context;
	
	public WxcBottomFragment(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	public WxcBottomFragment(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		// TODO Auto-generated constructor stub
		bottom_cur_index=bottom_default_index;
		System.out.println("bottom 中 bottom_cur_index 是"+bottom_cur_index);
		setOrientation(LinearLayout.HORIZONTAL);
		System.out.println("加载底部按钮栏。。。。。。。");
		init();
	}
	
	public void init(){
		bottom_views=new View[3];
		bottom_views[0]=createBottom(R.drawable.bt_home_focus,R.string.bottom_first,COLOR_SELECT,TAG_ICON_0,TAG_TEXT_0,"first");
		bottom_views[0].setTag(Integer.valueOf(0));
		bottom_views[0].setOnClickListener(this);
		addView(bottom_views[0]);
		System.out.println("创建底部第一个按钮。。。。。。。");
		
		bottom_views[1]=createBottom(R.drawable.bt_my,R.string.bottom_my,COLOR_UNSELECT,TAG_ICON_1,TAG_TEXT_1,"my");
		bottom_views[1].setTag(Integer.valueOf(1));
		bottom_views[1].setOnClickListener(this);
		addView(bottom_views[1]);
		System.out.println("创建底部第二个按钮。。。。。。。");
		bottom_views[2]=createBottom(R.drawable.bt_set,R.string.bottom_set,COLOR_UNSELECT,TAG_ICON_2,TAG_TEXT_2,"set");
		bottom_views[2].setTag(Integer.valueOf(2));
		bottom_views[2].setOnClickListener(this);
		addView(bottom_views[2]);
		
		if (ConstantS.version_isnew!=0){
			bgv_set.setText("");
			bgv_set.setTextColor(Color.RED);
			bgv_set.show();					
		}
		if (ConstantS.user_score_benfit_isnew!=0||ConstantS.user_vou_benfit_isnew!=0||ConstantS.information_isnew!=0||ConstantS.user_score_isnew!=0||ConstantS.user_vou_isnew!=0){
			bgv_my.setText("");
			bgv_my.setTextColor(Color.RED);
			bgv_my.show();					
		}			
	}
	
	
	private View createBottom(int iconID, int stringID, int stringColor, 
			String iconTag, String textTag,String tag_name) {
		LinearLayout view = new LinearLayout(getContext());
		view.setOrientation(LinearLayout.VERTICAL);
		view.setLayoutParams(new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));
		
		view.setGravity(Gravity.CENTER);
		ImageView iconView = new ImageView(getContext());
		iconView.setTag(iconTag);
		LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 0);
        llp.topMargin=15;		
		iconView.setLayoutParams(llp);
		iconView.setImageResource(iconID);
				

		TextView textView = new TextView(getContext());
		textView.setTag(textTag);

		llp=new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 0);
		textView.setLayoutParams(llp);
		textView.setTextColor(stringColor);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		textView.setText(stringID);
							
		view.addView(iconView);		
		view.addView(textView);
//		if (tag_name.equalsIgnoreCase("first")){
//		bgv_fir=WxcPublicUtil.addRedPoint(context,iconView,"", 12, BadgeView.POSITION_TOP_RIGHT);
//		}
		if (tag_name.equalsIgnoreCase("my")){
		bgv_my=WxcPublicUtil.addRedPoint(context, iconView, "", 5, BadgeView.POSITION_TOP_RIGHT);
		}
		if (tag_name.equalsIgnoreCase("set")){
		bgv_set=WxcPublicUtil.addRedPoint(context, iconView, "", 5, BadgeView.POSITION_TOP_RIGHT);
		}		
        		
		return view;
	}

	public interface OnIndicateListener {
		public void onIndicate(View v, int which);
	}
	public void setOnIndicateListener(OnIndicateListener listener) {
		onbottomlistener = listener;
	}
	
		
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (onbottomlistener != null) {
			int tag = (Integer) arg0.getTag();
			switch (tag) {
			case 0:
				if (bottom_cur_index != 0) {
					onbottomlistener.onIndicate(arg0, 0);
					setIndicator(0);
									}
				break;
			case 1:
				if (bottom_cur_index != 1) {
					onbottomlistener.onIndicate(arg0, 1);
					setIndicator(1);
					if (ConstantS.user_score_isnew!=0||ConstantS.user_score_benfit_isnew!=0||ConstantS.user_vou_isnew!=0||ConstantS.user_vou_benfit_isnew!=0||ConstantS.information_isnew!=0){
						bgv_my.show();
					}else{
						bgv_my.hide();	
					}					
			}
				break;
			case 2:
				if (bottom_cur_index != 2) {
					onbottomlistener.onIndicate(arg0, 2);
					setIndicator(2);		
					if (ConstantS.version_isnew!=0){
						bgv_set.show();
					}else{	
						bgv_set.hide();						
					}					
			   }
				break;
			default:
				break;
			}
		}
		
		
	}



	public void setIndicator(int which) {
		// clear previous status.
		bottom_views[bottom_cur_index].setBackgroundColor(Color.alpha(0));
		switch(bottom_cur_index) {
		case 0:
			setBottomView(bottom_cur_index, TAG_ICON_0, TAG_TEXT_0, R.drawable.bt_home, COLOR_UNSELECT);
			break;
		case 1:
			setBottomView(bottom_cur_index, TAG_ICON_1, TAG_TEXT_1,R.drawable.bt_my, COLOR_UNSELECT);			
			break;
		case 2:
			setBottomView(bottom_cur_index, TAG_ICON_2, TAG_TEXT_2,R.drawable.bt_set, COLOR_UNSELECT);
			break;
		}
		

		switch(which) {
		case 0:
			setBottomView(which, TAG_ICON_0, TAG_TEXT_0, R.drawable.bt_home_focus, COLOR_SELECT);			
			break;
		case 1:
			setBottomView(which, TAG_ICON_1, TAG_TEXT_1, R.drawable.bt_my_focus, COLOR_SELECT);			
			break;
		case 2:
			setBottomView(which, TAG_ICON_2, TAG_TEXT_2, R.drawable.bt_set_focus, COLOR_SELECT);
			break;
		}
		
		System.out.println("点击bottom，之前的索引为："+bottom_cur_index);
		bottom_cur_index = which;
		System.out.println("点击bottom，点击索引为："+which);
	}
	
	
	
	public void setBottomView(int viewindex,String icon_tag,String text_tag,int icon_img,int text_color){
		ImageView ivIcon;
		TextView tvText;
		ivIcon =(ImageView) bottom_views[viewindex].findViewWithTag(icon_tag);
		ivIcon.setImageResource(icon_img);
		tvText = (TextView) bottom_views[viewindex].findViewWithTag(text_tag);
		tvText.setTextColor(text_color);	
	}
	
	
    
}

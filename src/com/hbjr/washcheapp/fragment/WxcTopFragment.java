package com.hbjr.washcheapp.fragment;

import com.hbjr.washcheapp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;


public class WxcTopFragment extends FrameLayout implements OnClickListener {

	public OnBackButtonClickListener mOnBackButtonClickListener;
	public OnHandleButtonClickListener  mHandleButtonClickListener;
	private TextView mTitle;
	private ImageButton mBackButton;
	private ImageButton ib_handle;
	
	
	public WxcTopFragment(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public WxcTopFragment(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	public WxcTopFragment(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		
		LayoutInflater inflate=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflate.inflate(R.layout.title_view,this,true);
		mBackButton = (ImageButton) findViewById(R.id.ib_back);
		mBackButton.setOnClickListener(this);
		mTitle = (TextView) findViewById(R.id.tv_title);
		mTitle.setVisibility(View.INVISIBLE);
		ib_handle = (ImageButton) findViewById(R.id.ib_handle);
		ib_handle.setOnClickListener(this);
	}

	
	
	public interface OnBackButtonClickListener {
		public void onClick(View button);
	}

	public interface OnHandleButtonClickListener {
		public void onClick(View button);
	}

	
	
	/**
	 * 标题返回按钮
	 * 
	 * @param listener
	 */
	public void setBackButton(OnBackButtonClickListener listener) {
		mOnBackButtonClickListener = listener;
	}
	
	public void showBackBtn(){
		mBackButton.setVisibility(View.VISIBLE);
	}
	
	public void hiddenBackBtn(){
		mBackButton.setVisibility(View.GONE);
	}
	
	/**
	 * 标题操作按钮
	 * 
	 * @param listener
	 */
	public void setHandleButton(OnHandleButtonClickListener listener) {
		mHandleButtonClickListener = listener;
	}
	
	public void hiddenHandleBtn(){
		ib_handle.setVisibility(View.GONE);
	}
	
	public void showHandleBtn(int iamgeBtnID){
		ib_handle.setImageResource(iamgeBtnID);
		ib_handle.setVisibility(View.VISIBLE);
	}
//设置标题
	public void setTitle(String text) {
		mTitle.setVisibility(View.VISIBLE);
		mTitle.setText(text);
	}

	public void setTitle(int stringID) {
		mTitle.setVisibility(View.VISIBLE);
		mTitle.setText(stringID);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_back:
			if (mOnBackButtonClickListener != null) {
				mOnBackButtonClickListener.onClick(v);
			}
			break;
		case R.id.ib_handle:
			if (mHandleButtonClickListener != null) {
				mHandleButtonClickListener.onClick(v);
			}
			break;
		default:
			break;
		}
	}

	
	
}

package com.hbjr.washcheapp.widget;

import com.hbjr.washcheapp.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;


public class LoadingDialog extends AlertDialog {

    private TextView tips_loading_msg;

    private String message = null;
    
    public final static int TYPE_GET=0;//取信息列表，提示加载中
    
    public final static int TYPE_POST=1;//取信息列表，提示提交中

    
    //flag:
    public LoadingDialog(Context context,int flag) {
        super(context);
        if (flag==TYPE_GET){
        message = getContext().getResources().getString(R.string.msg_load_ing);
        }else{
        message = getContext().getResources().getString(R.string.msg_post_ing);        	
        }
    }

    public LoadingDialog(Context context, String message) {
        super(context);
        this.message = message;
        this.setCancelable(false);
    }

    public LoadingDialog(Context context, int theme, String message) {
        super(context, theme);
        this.message = message;
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_tips_loading);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.message);
    }

    public void setText(String message) {
        this.message = message;
        tips_loading_msg.setText(this.message);
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }

}

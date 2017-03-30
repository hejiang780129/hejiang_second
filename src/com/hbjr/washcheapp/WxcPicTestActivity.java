package com.hbjr.washcheapp;

import java.util.HashMap;  
  
import android.app.Activity;  
import android.content.Context;  
import android.content.res.TypedArray;  
import android.os.Bundle;  
import android.view.View;  
import android.view.ViewGroup;  
import android.view.animation.AnimationUtils;  
import android.widget.AdapterView;  
import android.widget.AdapterView.OnItemClickListener;  
import android.widget.BaseAdapter;  
import android.widget.Gallery;  
import android.widget.ImageSwitcher;  
import android.widget.ImageView;  
import android.widget.AdapterView.OnItemSelectedListener;  
import android.widget.Gallery.LayoutParams;  
import android.widget.ViewSwitcher.ViewFactory;  
  
public class WxcPicTestActivity extends Activity implements OnItemSelectedListener, ViewFactory, OnItemClickListener  
{  
    private Gallery gallery;  
    private ImageSwitcher imageSwitcher;  
    private ImageAdapter imageAdapter;  
    private int mCurrentPos = -1;// ��ǰ��item  
    private HashMap<Integer, ImageView> mViewMap;  
  
    private int[] resIds = new int[]  
        {R.drawable.dian1 ,R.drawable.dian2 ,R.drawable.dian3 ,R.drawable.dian4 ,R.drawable.dian5 ,R.drawable.dian6 ,R.drawable.dian7 ,R.drawable.dian8 ,R.drawable.dian9 ,R.drawable.dian10 ,R.drawable.dian11 ,R.drawable.dian12 ,R.drawable.dian14 };  
  
    @Override  
    public void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_wxc_pic_test);  
        gallery = (Gallery) findViewById(R.id.gallery);  
        imageAdapter = new ImageAdapter(this, resIds.length);  
        gallery.setAdapter(imageAdapter);  
        gallery.setOnItemSelectedListener(this);  
        gallery.setSelection(1);// ����һ����Activity����ʾ��ͼƬΪ�ڶ���  
  
        gallery.setOnItemClickListener(this);  
  
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageswitcher);  
        imageSwitcher.setFactory(this);  
  
        // ���ö���Ч�� ���뵭��  
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));  
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));  
    }  
  
    public class ImageAdapter extends BaseAdapter  
    {  
        int mGalleryItemBackground;  
        private Context mContext;  
        private int mCount;// һ�����ٸ�item  
  
        public ImageAdapter(Context context, int count)  
        {  
            mContext = context;  
            mCount = count;  
            mViewMap = new HashMap<Integer, ImageView>(count);  
            TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);  
            // ���ñ߿����ʽ  
            mGalleryItemBackground = typedArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);  
        }  
  
        public int getCount()  
        {  
            return Integer.MAX_VALUE;  
        }  
  
        public Object getItem(int position)  
        {  
            return position;  
        }  
  
        public long getItemId(int position)  
        {  
            return position;  
        }  
  
        public View getView(int position, View convertView, ViewGroup parent)  
        {  
            ImageView imageview = mViewMap.get(position % mCount);  
            if (imageview == null)  
            {  
                imageview = new ImageView(mContext);  
                imageview.setImageResource(resIds[position % resIds.length]);  
                imageview.setScaleType(ImageView.ScaleType.FIT_XY);  
                imageview.setLayoutParams(new Gallery.LayoutParams(136, 88));  
                imageview.setBackgroundResource(mGalleryItemBackground);  
            }  
            return imageview;  
        }  
    }  
  
    // ����Gallery��ʱ��ImageView������ʾ��ǰ��item  
    @Override  
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)  
    {  
        // imageSwitcher.setImageResource(resIds[position % resIds.length]);  
    }  
  
    // ���õ��Gallery��ʱ����л�����ͼƬ  
    @Override  
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)  
    {  
        if (mCurrentPos == position)  
        {  
            // �������ʾ��ǰͼƬ���ٵ�����Ͳ��ټ��ء�  
            return;  
        }  
        mCurrentPos = position;  
        imageSwitcher.setImageResource(resIds[position % resIds.length]);  
    }  
  
    @Override  
    public void onNothingSelected(AdapterView<?> parent)  
    {  
    }  
  
    @Override  
    public View makeView()  
    {  
        ImageView imageView = new ImageView(this);  
        imageView.setBackgroundColor(0xFF000000);  
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);  
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
  
        return imageView;  
    }  
  
}  
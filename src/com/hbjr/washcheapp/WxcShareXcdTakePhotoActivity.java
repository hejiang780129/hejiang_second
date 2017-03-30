package com.hbjr.washcheapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;

public class WxcShareXcdTakePhotoActivity extends Activity implements OnItemSelectedListener, ViewFactory, OnItemClickListener {

	private Gallery share_gallery;
	private ImageView share_camera;
	private ImageView share_delete;	
	private ImageView share_fold;
	private ImageSwitcher imageSwitcher;
	private Button share_photo_upload;
	
    private ImageAdapter imageAdapter;
    private int mCurrentPos = -1;// 当前的item  
    private HashMap<Integer, ImageView> mViewMap;  
      
    private List<Bitmap> photos;
    private Bitmap cur_photo_bitmap;

	
    /*用来标识请求照相功能的activity*/  
    private static final int CAMERA_WITH_DATA = 3023;  
  
    /*用来标识请求gallery的activity*/  
    private static final int PHOTO_PICKED_WITH_DATA = 3021;  
  
    /*拍照的照片存储位置*/  
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");  
  
    
    private LinearLayout  mLl_parent;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wxc_share_xcd_take_photo);
		MyApplication.getInstance().addActivity(this);
		addTop();
		initById();
		photos=new ArrayList<Bitmap>();
		photos.add(BitmapFactory.decodeResource(getResources(), R.drawable.dian_example2));
		photos.add(BitmapFactory.decodeResource(getResources(), R.drawable.dian_example3));
		photos.add(BitmapFactory.decodeResource(getResources(), R.drawable.dian_example4));		
		
        imageAdapter = new ImageAdapter(this, photos.size());  
        share_gallery.setAdapter(imageAdapter);  
        share_gallery.setOnItemSelectedListener(this);  
        share_gallery.setSelection(1);// 设置一加载Activity就显示的图片为第二张  
        share_gallery.setOnItemClickListener(this);  
        
        imageSwitcher = (ImageSwitcher) findViewById(R.id.share_imageswitcher);  
        imageSwitcher.setFactory(this);  
        imageSwitcher.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	 
			}
		});
        // 设置动画效果 淡入淡出  
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));  
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out)); 
	}

	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_share_second_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,ConstantS.top_long);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("分享洗车店  拍照");
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
				intent.setClass(WxcShareXcdTakePhotoActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	
	
	public void initById(){
		share_gallery=(Gallery) findViewById(R.id.share_gallery);
		share_camera=(ImageView) findViewById(R.id.share_camera);
		share_fold=(ImageView) findViewById(R.id.share_fold);
		share_delete=(ImageView) findViewById(R.id.share_delete);
		share_photo_upload=(Button) findViewById(R.id.share_photo_upload);
		share_camera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  	
				intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);  
                startActivityForResult(intent, CAMERA_WITH_DATA); 
			}
		});
		share_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 //AlertDialog dialog；
					new AlertDialog.Builder(WxcShareXcdTakePhotoActivity.this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("删除图片提示").setMessage("您真的要删除该张图片么？").
					setPositiveButton("确定",new MyDialogOnclick())
					.setNegativeButton("取消", null)
					.show();
		}
	});
		
		share_fold.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 try {  
			            // Launch picker to choose photo for selected contact  
			            final Intent intent = getPhotoPickIntent();  
			            startActivityForResult(intent,PHOTO_PICKED_WITH_DATA);  
			        } catch (ActivityNotFoundException e) {  
                         e.printStackTrace();
                         System.out.println("没有打开图片浏览器");
			        }  
			}
		});
		
		share_photo_upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
               if (photos.size()<=3){
            	   WxcPublicUtil.showShortToast(WxcShareXcdTakePhotoActivity.this, "请拍照或选择照片");
            	   return;
               }
            
            //讲bitmap文件列表放入内存中
             Bitmap[]  bitMaps=new Bitmap[photos.size()-3];
             for (int i=0;i<bitMaps.length;i++){
            	 bitMaps[i]=(Bitmap) photos.get(i+3);
             }
             ConstantS.cur_obj.put("photolist", bitMaps);  
               
            /**
             * 如果需要进行图片上传，则直接将bitMap中的bitmap字节进行上传
             * 
            Bitmap bmpCompressed = Bitmap.createScaledBitmap(bitmap, 640, 480, true);
   	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
   	        // CompressFormat set up to JPG, you can change to PNG or whatever you want;
   	        bmpCompressed.compress(CompressFormat.JPEG, 100, bos);
   	        byte[] data = bos.toByteArray();
             *    
             */
				Intent intent=new Intent();
				intent.setClass(WxcShareXcdTakePhotoActivity.this,WxcShareXcdUploadActivity.class);
				startActivity(intent);					
			}
		});
		
	}
	
	
	class MyDialogOnclick implements android.content.DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			System.out.println("对话框按钮事件监听");
			System.out.println("当前的索引是："+mCurrentPos);
			if(mCurrentPos>=0&&mCurrentPos<=2){
				System.out.println("不能删除示例文件");
				Toast.makeText(WxcShareXcdTakePhotoActivity.this,"不能删除示例文件", Toast.LENGTH_SHORT);
				return;
			}
			System.out.println("开始删除");
			String fileName = ConstantS.PHOTO_PATH+ConstantS.PHOTO_NAME_PRE+"_"+(mCurrentPos);
			System.out.println("要删除的当前文件名为:"+fileName);
			File file=new File(fileName); 
		      if(file.exists()){ 
		       file.delete();
		       System.out.println("删除文件："+fileName+" 成功");
		       delRefreshPhoto();
		      }
		      
		}
		
	}
	
	

	
	/**
	 * 增加刷新图片列表
	 */
	public void addRefreshPhoto(){
		photos.add(cur_photo_bitmap);
		share_gallery.setSelection(photos.size()-1);
        Drawable drawable = new BitmapDrawable((Bitmap)photos.get(photos.size()-1));
	    imageSwitcher.setImageDrawable(drawable) ;
		imageAdapter.notifyDataSetChanged();
	}
	
	
	/**
	 * 增加图片选择后刷新列表
	 */
	public void addFromfoldRefreshPhoto(){
		photos.add(cur_photo_bitmap);
		share_gallery.setSelection(photos.size()-1);
        Drawable drawable = new BitmapDrawable((Bitmap)photos.get(photos.size()-1));
	    imageSwitcher.setImageDrawable(drawable) ;
		imageAdapter.notifyDataSetChanged();
	}
	
	
	/**
	 * 删除刷新图片列表
	 */
	public void delRefreshPhoto(){
		photos.remove(cur_photo_bitmap);
		share_gallery.setSelection(photos.size()-1);
        Drawable drawable = new BitmapDrawable((Bitmap)photos.get(photos.size()-1));
	    imageSwitcher.setImageDrawable(drawable) ;
		imageAdapter.notifyDataSetChanged();
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_share_xcd_take_photo, menu);
		return true;
	}
	
	
	 public class ImageAdapter extends BaseAdapter  
	    {  
	        int mGalleryItemBackground;  
	        private Context mContext;  
	        private int mCount;// 一共多少个item  
	  
	        public ImageAdapter(Context context, int count)  
	        {  
	            mContext = context;  
	            mCount = count;  
	            mViewMap = new HashMap<Integer, ImageView>(count);  
	            TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);  
	            // 设置边框的样式  
	            mGalleryItemBackground = typedArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);  
	        }  
	  
	        public int getCount()  
	        {  
	            return photos.size();  
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
	                //imageview.setImageBitmap((Bitmap) photos.get(position%photos.size()));
	                imageview.setImageBitmap((Bitmap) photos.get(position));
	                imageview.setScaleType(ImageView.ScaleType.FIT_XY);  
	                imageview.setLayoutParams(new Gallery.LayoutParams(136, 88));  
	                imageview.setBackgroundResource(mGalleryItemBackground);  
	            }  
	            return imageview;  
	        }  
	    }  
		
		

		@Override
		public void onItemClick(AdapterView<?> parent, View arg1, int position, long id) {
			// TODO Auto-generated method stub
		       if (mCurrentPos == position)  
		        {  
		            // 如果在显示当前图片，再点击，就不再加载。  
		            return;  
		        }  
		        mCurrentPos = position;  		                
		        Drawable drawable = new BitmapDrawable((Bitmap)photos.get(position));
		        imageSwitcher.setImageDrawable(drawable) ;
		}

		@Override
		public View makeView() {
			// TODO Auto-generated method stub
		      ImageView imageView = new ImageView(this);  
		      //imageView.setBackgroundColor(0xFF000000);  
		      imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);  
		      imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
	          return imageView;  
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			if (resultCode == Activity.RESULT_OK) {  
				
			   switch (requestCode) {
				
			   case CAMERA_WITH_DATA:{
			   
	            String sdStatus = Environment.getExternalStorageState();  
	            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { //检测sd是否可用  
	               System.out.println("SD card is not avaiable/writeable right now.");  
	                return;  
	            }  
	            String name = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";     
	            Toast.makeText(this, name, Toast.LENGTH_LONG).show();  
	            Bundle bundle = data.getExtras();  
	            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式            
	            FileOutputStream b = null;  
	           //???????????????????????????????为什么不能直接保存在系统相册位置呢？？？？？？？？？？？？  
	            File file = new File(ConstantS.PHOTO_PATH);  
	            file.mkdirs();// 创建文件夹  
	            String fileName = ConstantS.PHOTO_PATH+ConstantS.PHOTO_NAME_PRE+"_"+photos.size();  
	            try {  
	                b = new FileOutputStream(fileName);  
	                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件  
	                cur_photo_bitmap=bitmap;
	                addRefreshPhoto();
	                
	            } catch (FileNotFoundException e) {  
	                e.printStackTrace();  
	            } finally {  
	                try {  
	                    b.flush();  
	                    b.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }
	            break;
			   }
			   
			   case PHOTO_PICKED_WITH_DATA:{
				   cur_photo_bitmap= data.getParcelableExtra("data");
				   addFromfoldRefreshPhoto();
				   break;
			   }
	            
			   }//switch	            
	        }
		}
	
	

		 public static Intent getPhotoPickIntent() {  
		        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);  
		        intent.setType("image/*");  
		        intent.putExtra("crop", "true");  
		        intent.putExtra("aspectX", 1);  
		        intent.putExtra("aspectY", 1);  
		        intent.putExtra("outputX", 80);  
		        intent.putExtra("outputY", 80);  
		        intent.putExtra("return-data", true);  
		        return intent;  
		    }  
		
		
}

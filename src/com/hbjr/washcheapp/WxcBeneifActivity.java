package com.hbjr.washcheapp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.hbjr.washcheapp.bean.WxcBusInfo;
import com.hbjr.washcheapp.bean.WxcScore;
import com.hbjr.washcheapp.bean.WxcUserAccount;
import com.hbjr.washcheapp.bean.WxcUserScore;
import com.hbjr.washcheapp.bean.WxcUserVou;
import com.hbjr.washcheapp.bean.WxcVouExercise;
import com.hbjr.washcheapp.config.ConstConnectCode;
import com.hbjr.washcheapp.config.ConstantS;
import com.hbjr.washcheapp.net.JSONPostTools;
import com.hbjr.washcheapp.net.JSONTools;
import com.hbjr.washcheapp.net.URLProtocol;
import com.hbjr.washcheapp.util.MyApplication;
import com.hbjr.washcheapp.util.WxcPublicUtil;
import com.hbjr.washcheapp.widget.LoadingDialog;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class WxcBeneifActivity extends Activity {

	/**
	 * �ý����������ֻ�����ֶһ�������ȯ��ȡ�Ȼ
	 */
	
	private TextView beneif_title;
	private TextView benfit_validity;
	private ImageView benfit_img;
	private TextView benfit_content;
	private Button beneif_go;
	private EditText beneif_phone;
	private TextView beneif_show_info;
	
	private String cur_change_rule;
	private int cur_user_score,cur_user_exchange_vou,cur_score_remainder;
	private int rule_score,rule_vou;
	private WxcUserAccount user_account;
	private WxcScore score;
	private WxcVouExercise vou;
	private int beneif_type;//0:���֣�1������ȯ
	
	LinearLayout mLl_parent;
	
	
	WxcUserScore user_score;
	WxcUserVou  user_vou;
	Intent intent=new Intent();
	
	String phone_num="";
    LoadingDialog loadingdialog;
	String islogin="";
	/**
	 * ����file
	 */
	File cache; // �����ļ�
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.activity_wxc_beneif);
		MyApplication.getInstance().addActivity(this);
		Object obj=getIntent().getSerializableExtra("beneif");
		if (ConstantS.user_isLogin){
			islogin="1";
		}else{
			islogin="2";
		}
		if (obj instanceof WxcScore) {
            score= (WxcScore) obj;		
            beneif_type=0;
		}else{
			if (obj instanceof WxcVouExercise){
             vou=(WxcVouExercise) obj;
             beneif_type=1;             
			}
		}
		user_account=WxcUserAccount.useraccount;
		
		cache = new File(Environment.getExternalStorageDirectory(), "cache_bene"); // ʵ���������ļ�
		if (!cache.exists())  
            cache.mkdirs(); // ����ļ������ڣ�����
		
		initById();
		addTop();
	}
	
	private void addTop(){
		mLl_parent=(LinearLayout)findViewById(R.id.ll_beneif_parent);
		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,80);
		LayoutInflater inflate=LayoutInflater.from(this);
		View view=inflate.inflate(R.layout.title_view, null);
		TextView tv=(TextView) view.findViewById(R.id.tv_title);
		ImageButton ib_back=(ImageButton) view.findViewById(R.id.ib_back);
		ImageButton ib_handle=(ImageButton) view.findViewById(R.id.ib_handle);
		tv.setText("�鿴�");
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
				intent.setClass(WxcBeneifActivity.this, WxcMainActivity.class);
				startActivity(intent);
			}
		});
		
		view.setLayoutParams(lp);
		mLl_parent.addView(view);
		
	}
	

	public void initById(){
		beneif_title=(TextView) findViewById(R.id.beneif_title);
		benfit_validity=(TextView) findViewById(R.id.benfit_validity);
		benfit_img=(ImageView) findViewById(R.id.benfit_img);
		benfit_content=(TextView) findViewById(R.id.benfit_content);
		beneif_show_info=(TextView) findViewById(R.id.beneif_show_info);
		beneif_phone=(EditText) findViewById(R.id.beneif_phone);
		beneif_go=(Button) findViewById(R.id.beneif_go);		
		//����ǻ���
		if (beneif_type==0){			
			beneif_title.setText(score.getName()+"   "+score.getScore_desc());
			if (score.getEdate().equalsIgnoreCase("1")){
				benfit_validity.setText("������Ч");
			}else{
				benfit_validity.setText(score.getEdate());				
			}
	
			//benfit_img.setImageResource(score.getShow_img());
			
    	    System.out.println("�첽��ȡͼƬ��url��"+score.getImg_url());			
			asyncImageLoad(benfit_img, score.getImg_url());
			
			benfit_content.setText(score.getContent());		
			
			//����ǻ�ȡ����
			if (score.getType().equalsIgnoreCase("0")){			
			    if (ConstantS.user_name==null||ConstantS.user_name.equalsIgnoreCase("")){			
					beneif_phone.setVisibility(View.VISIBLE);
				 }				
			}
			
            //����ǻ��ֶһ�������Ҫ���е�¼
			if (score.getType().equalsIgnoreCase("1")){
				//����ѵ�¼
				if (ConstantS.user_isLogin){
					int tmp;
					//��ǰ�û�����
					cur_user_score=Integer.parseInt(user_account.getScore_sum());
					//��ǰ�һ�����
					cur_change_rule=score.getExchange_rule();
					String[] arr1=cur_change_rule.split(",");
	        		rule_score=Integer.parseInt(arr1[0]);
	        		rule_vou=Integer.parseInt(arr1[1]);	        		
	        		tmp=cur_user_score/rule_score;
	        		//�һ���Ĵ���ȯ���
	        		cur_user_exchange_vou=tmp*rule_vou;
	        		//�һ����ʣ�����
	        		cur_score_remainder=cur_user_score-(tmp*rule_score);				    
	        		//��ʽ�����Ļ���  200��\n�ɶһ�      100Ԫ\nʣ��          20��
					beneif_show_info.setText("���Ļ��� "+cur_user_score+"��\n�ɶһ�      "+cur_user_exchange_vou+"Ԫ\nʣ��          "+cur_score_remainder+"��");
				}
				
			}else{
				
				if (ConstantS.user_isLogin){
					beneif_show_info.setText("���Ļ��� "+user_account.getScore_sum()+"��");										
				}				
			}
			
		}
		
		//����Ǵ���ȯ				
		if (beneif_type==1){
			beneif_title.setText(vou.getName()+"   "+vou.getVou_num()+"Ԫ");
			if (vou.getEdate().equalsIgnoreCase("1")){
			benfit_validity.setText("������Ч");
			}else{
			benfit_validity.setText(vou.getEdate());				
			}
			//benfit_img.setImageResource(vou.getShow_img());
			
    	    System.out.println("�첽��ȡͼƬ��url��"+vou.getImg_url());			
			asyncImageLoad(benfit_img, vou.getImg_url());
			
			benfit_content.setText(vou.getContent());
		    //����ǻ�ȡ����
			if (vou.getType().equalsIgnoreCase("0")){			
			    if (ConstantS.user_name==null||ConstantS.user_name.equalsIgnoreCase("")){			
					beneif_phone.setVisibility(View.VISIBLE);
				 }
				
			}
			
			if (ConstantS.user_isLogin){
				beneif_show_info.setText("���Ĵ���ȯ  "+user_account.getOrder_num()+"Ԫ");										
			}				
			
			
		}
		
		
		
		
		beneif_go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				phone_num=beneif_phone.getText()+"";				
				if (beneif_type==0){					
					//����ǻ��ֻ����ֶһ�
                    if (!score.getType().equalsIgnoreCase("2")){					
					   //����ǻ��ֻ
					   if (score.getType().equalsIgnoreCase("0")){						   
						   if (ConstantS.user_name==null||ConstantS.user_name.equalsIgnoreCase("")){
				               if (phone_num==null||phone_num.equalsIgnoreCase("")){
				                  	 WxcPublicUtil.showShortToast(WxcBeneifActivity.this, "�������ֻ�����");
				                   	 return;
				                }
							}   
						   
						//==�첽�ϴ�=================================================================================   
			           //����һ��������ȡ��¼
						   user_score=new WxcUserScore("0",score.getName()+" "+score.getScore_desc(),score.getId(),score.getScore(),WxcPublicUtil.getCurDate());						   
                           invokeServer("1");                         
   						//===========================================================================================                           
                                                      
					   }
					   //����ǻ��ֶһ�
					   else{	
						   if (!ConstantS.user_isLogin){
			                  	 WxcPublicUtil.showShortToast(WxcBeneifActivity.this, "���ֶһ���Ҫ��¼ϵͳ����������ҳ�桿���е�¼");
			                   	 return;							   
						   }				
						   
						   if (cur_user_exchange_vou<=0){
			                  	 WxcPublicUtil.showShortToast(WxcBeneifActivity.this, "���Ļ��ֲ����Խ��б��ζһ������ȡ�������");
			                   	 return;							   							   
						   }
						   
							//==�첽�ϴ�=================================================================================						   
					        //����һ��������ȡ��¼
						   user_score=new WxcUserScore("1",score.getName()+" "+score.getScore_desc(),score.getId(),String.valueOf((cur_user_score-cur_score_remainder)),WxcPublicUtil.getCurDate());
					        //����һ������ȯ��¼
						   user_vou=new WxcUserVou("",String.valueOf(cur_user_exchange_vou), "0", "ͨ�����ֶһ���ȡ���һ��Ϊ��"+score.getName(),WxcPublicUtil.getCurDate(),WxcUserVou.getIvalid("1"),WxcUserVou.getStatusDsc(WxcUserVou.getIvalid("1")),"1", "");
						   
                           invokeServer("2");                           
      						//===========================================================================================                                                      
					   }					   					   
                    }else{                    	
                    	switch (Integer.parseInt(score.getType_sub_id())) {
						case 901:	
							intent.setClass(WxcBeneifActivity.this, WxcRegisterActivity.class);
							startActivity(intent);
							break;
						case 902:
							intent.setClass(WxcBeneifActivity.this, WxcShareXcdActivity.class);
							startActivity(intent);							
							break;
						case 903:						
						case 904:
						case 905:
							intent.setClass(WxcBeneifActivity.this, WxcMainActivity.class);
							startActivity(intent);							
							break;
						case 906:
						case 907:
							Intent intentsend=new Intent(Intent.ACTION_SEND);   
							intentsend.setType("image/*");   
							intentsend.putExtra(Intent.EXTRA_SUBJECT, "����");   
							intentsend.putExtra(Intent.EXTRA_TEXT, "����һ�����Եķ����ַ���");    
							intentsend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
					        startActivity(Intent.createChooser(intentsend,"51ϴ��"));   						
							break;																					
						}
                    	                    	                    	                    	                    	
                    }									
					
				}
				
				
				//����Ǵ���ȯ�
				if (beneif_type==1){					
	                if (!vou.getType().equalsIgnoreCase("2")){					
						   //����Ǵ���ȯ�
						   if (vou.getType().equalsIgnoreCase("0")){						   
							   if (ConstantS.user_name==null||ConstantS.user_name.equalsIgnoreCase("")){
					               if (phone_num==null||phone_num.equalsIgnoreCase("")){
					                  	 WxcPublicUtil.showShortToast(WxcBeneifActivity.this, "�������ֻ�����");
					                   	 return;
					                }
								}
								//==�첽�ϴ�=================================================================================     
							   user_vou=new WxcUserVou(vou.getId(),vou.getVou_num(), vou.getType(), vou.getName(),WxcPublicUtil.getCurDate(),WxcUserVou.getIvalid(vou.getEdate_vou()),WxcUserVou.getStatusDsc(WxcUserVou.getIvalid(vou.getEdate_vou())),vou.getEdate_vou(), "");     
	                           invokeServer("3");	                           
      						//===========================================================================================	                           
						   }	
						   						   						   
	                    }else{                    	
	                    	switch (Integer.parseInt(vou.getType_sub_id())) {
							case 601:	
								intent.setClass(WxcBeneifActivity.this, WxcRegisterActivity.class);
								startActivity(intent);
								break;
							case 602:
								intent.setClass(WxcBeneifActivity.this, WxcShareXcdActivity.class);
								startActivity(intent);							
								break;
							case 603:						
							case 604:
							case 605:
								intent.setClass(WxcBeneifActivity.this, WxcMainActivity.class);
								startActivity(intent);							
								break;
							case 606:
							case 607:
								Intent intentsend=new Intent(Intent.ACTION_SEND);   
								intentsend.setType("image/*");   
								intentsend.putExtra(Intent.EXTRA_SUBJECT, "����");   
								intentsend.putExtra(Intent.EXTRA_TEXT, "����һ�����Եķ����ַ���");    
								intentsend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
						        startActivity(Intent.createChooser(intentsend,"51ϴ��"));   						
								break;																					
							}
	                    	                    	                    	                    	                    	
	                    }														
															
					
				}                				
			}
		});
		
	}
	
	public void createrecod1(String sdate){		
		   user_score.setGet_date(sdate);
           WxcUserScore.info.add(user_score);
           WxcUserAccount.useraccount.setScore_get(  String.valueOf(Integer.parseInt(WxcUserAccount.useraccount.getScore_get())
        		                                                  + Integer.parseInt(score.getScore())));
           WxcUserAccount.useraccount.setScore_sum(  String.valueOf(Integer.parseInt(WxcUserAccount.useraccount.getScore_sum())
                   + Integer.parseInt(score.getScore())));		
	}

	public void createrecod2(String sdate){
		user_score.setGet_date(sdate);
		user_vou.setGet_date(sdate);
		
        WxcUserScore.info.add(user_score);
        WxcUserAccount.useraccount.setScore_exchange(  String.valueOf(Integer.parseInt(WxcUserAccount.useraccount.getScore_exchange())
                + Integer.parseInt(user_score.getScore())));
        WxcUserAccount.useraccount.setScore_sum(  String.valueOf(Integer.parseInt(WxcUserAccount.useraccount.getScore_sum())
     		   	- Integer.parseInt(user_score.getScore())));                                                      
                                   
       
        WxcUserVou.info.add(user_vou);						                           
        WxcUserAccount.useraccount.setVou_total( String.valueOf(Integer.parseInt(WxcUserAccount.useraccount.getVou_total())
                + Integer.parseInt(user_vou.getTotal_num())));
        
	}
	
	public void createrecod3(String sdate){		
           user_vou.setGet_date(sdate);
           WxcUserVou.info.add(user_vou);
           WxcUserAccount.useraccount.setVou_total( String.valueOf(Integer.parseInt(WxcUserAccount.useraccount.getVou_total())
                   + Integer.parseInt(user_vou.getTotal_num())));				
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wxc_beneif, menu);
		return true;
	}
	
	
	private void asyncImageLoad(ImageView imageView, String path) {  
        WxcPublicUtil.AsyncImageTask asyncImageTask = new WxcPublicUtil.AsyncImageTask(imageView,cache);  
        asyncImageTask.execute(path);  
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (cache!=null){
			File[] files=cache.listFiles();
			if (files!=null){
			    for (File file :files) {  
			    	   System.out.println("benefit������ɾ�����ļ����ǣ�"+file.getPath()+file.getName());	    	
			            file.delete();  
			        }  
			        cache.delete();
			}		        
		}
		super.onDestroy();
	} 
	
	
	
	
	public boolean invokeServer(String type){
         final String fl_type=type;
		//type:1:�ǻ��ֻ,2:���ֶһ�,3:����ȯ�				
        loadingdialog=new LoadingDialog(WxcBeneifActivity.this, LoadingDialog.TYPE_POST);   	    			    
		loadingdialog.show();		
		HashMap hmp_return=new HashMap();
		final Handler handler_post = new Handler() {
	        public void handleMessage(Message msg) {
				String rtmsg,rcode,scid,vid,bdate;		        	
                switch (msg.what) {
				case 0:						
	                HashMap  hmp_get= (HashMap) msg.obj;	
	                rcode=(String) hmp_get.get("rcode");			    
	                rtmsg=(String) hmp_get.get("rtmsg");		                                
	                //������ʧ��
	                if (!rcode.equalsIgnoreCase("0")){
						WxcPublicUtil.showShortToast(WxcBeneifActivity.this,rtmsg);
		        		loadingdialog.dismiss();						
		        		return;
	                }
	                
	                //bdate:����ʱ��
	                bdate=(String) hmp_get.get("bdate");			    
                    if (fl_type.equalsIgnoreCase("1")) createrecod1(bdate);	                	                
                    if (fl_type.equalsIgnoreCase("2")) createrecod2(bdate);
                    if (fl_type.equalsIgnoreCase("3")) createrecod3(bdate);            
                    loadingdialog.dismiss();
        		   WxcPublicUtil.showShortToast(WxcBeneifActivity.this, "������ȡ�ɹ�,�´ε�½��ɽ��롾�ҵġ�����ȷ��"); 
        		   
              	   Intent intent=new Intent();
					if (rtmsg!=null&&!rtmsg.equalsIgnoreCase("")){
						  //WxcPublicUtil.invokeAlertDlg(WxcShareXcdUploadActivity.this, "��̨��ʾ", rtmsg);            							            							
					   intent.putExtra("benfit", rtmsg);      
					}            						  
             	   intent.putExtra("title", "�ϴ��ɹ�");     
             	   intent.putExtra("content", "������ȡ�ɹ�,�´ε�½��ɽ��롾�ҵġ�����ȷ��");
             	   intent.setClass(WxcBeneifActivity.this, WxcPrompActivity.class);
             	   startActivity(intent);	    
        		           		   
					break;
				case 1:		
					WxcPublicUtil.showShortToast(WxcBeneifActivity.this,(String) msg.obj);
	        		loadingdialog.dismiss();						
					break;
				}		        	                    		        	
	        }
	  };	
    new Thread(new Runnable() {
        public void run() {
            try {
            	System.out.println("��ʼ�첽�ύ����===================");
                String phone="";
        		Map map_info = new HashMap();
        		map_info.put("fid", ConstConnectCode.FID_PUT_BENEFIT);
        		map_info.put("islogin", islogin);
        		if (islogin.equalsIgnoreCase("0"))  phone=phone_num;
            	if (islogin.equalsIgnoreCase("1"))  phone=ConstantS.user_name;      			
        		map_info.put("phone", phone);
        		map_info.put("type", fl_type);        		
                map_info.put("usrscore", JSONPostTools.createJsonString("usrscore", user_score));
                if (user_vou!=null)
                map_info.put("usrvou", JSONPostTools.createJsonString("usrvou", user_vou));                
        		            	        		
        		//��ȡ����������ĵ�ַ
        		String json = URLProtocol.postJSonString(map_info);
        		JSONTools.getPutBenefit(ConstConnectCode.FID_PUT_BENEFIT, json, handler_post);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
		
		
		
		return true;
	}
	
	

}

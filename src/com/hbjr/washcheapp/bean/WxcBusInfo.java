package com.hbjr.washcheapp.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.baidu.mapapi.map.InfoWindow;
import com.baidu.navisdk.model.MainMapModel;
import com.hbjr.washcheapp.R;
import com.hbjr.washcheapp.util.WxcPublicUtil;

public class WxcBusInfo implements Serializable {

	  private static final long serialVersionUID = -758459502806858414L;  
	   
	   private String regin_id;
	   private String regin_name;
	  
	   private String xcd_id;
	   /** 
	     * ά��
	     */  
	    private double latitude;  
	    /** 
	     * ���� 
	     */  
	    private double longitude;  
	    /** 
	     * ͼƬID����ʵ��Ŀ�п�����ͼƬ·�� 
	     */  
	    private int imgId;
	    
	    /** 
	     * ͼƬurl 
	     */  
	    private String imgUrl;
	    
	    /**
	     * ����ͼƬid
	     */
	    private int img_detail_id;
	    
	    /**
	     * ͼƬ�б�ֻ������Ҫ��ʱ��������������ȡ
	     */
	    
	    private List<byte[]> list_img;

	    
	    
	    /** 
	     * �̼����� 
	     */  
	    private String name;  
	    /** 
	     * ���� 
	     */  
	    private String distance;  
   
	    /** 
	     * ��ַ
	     */  
	    private String address;  
	    /** 
	     * �۸�
	     */   
	    private List<HashMap<String, String>> prices;
	    /** 
	     * ��������
	     */  
	    private String  buynum ;  
	    
	    /** 
	     * Ӫҵʱ��
	     */  
	    private String  bh ;  
	    /** 
	     * ��ϵ��ʽ
	     */  
	    private String  contactinfo ;
	    /** 
	     * ��ʡ����
	     */  
	    private String  cutdown ;
	    /**
	     * �û�����
	     */
	    private List lst_comment;//Ϊֱ�ӶԸõ�������б���չʾϴ������ϸ��Ϣʱ���û������������ڶ�ϴ����������붩��������֮��
	    	   
	    private List lst_order_comment;
	    
        private String comm_num;//�����������������������붩�����ۣ�	    
	    

		private String introduce;
	    
	    private int good_num;//����������
	    private int mid_num;//����������
	    private int bad_num;//����������
	    private float rating;//����ƽ����
	    
	    public static List<WxcBusInfo> infos_lbs_tmp = new ArrayList<WxcBusInfo>(); 
	    
	    public static List<WxcBusInfo> cur_select_infos=new ArrayList<WxcBusInfo>();
	    
	    public static List<HashMap> tmp_order_comm_list=new ArrayList<HashMap>();
    	public static int tmp_good_num,tmp_mid_num,tmp_bad_num;
    	public static float tmp_rating;
	
	    static  
	    {  
	    	//116.335486,40.071995
//            setPingjia(WxcUserOrder.orderinfos, "001");    	           
//	        infos.add(new WxcBusInfo("001",40.071995,116.335486, R.drawable.dian1, "����ϴ����1", 
//	        		"����2km", 1456,tmp_rating,"�����в�ƽ�������۵�ַ1","35Ԫ","20�˹���","13802392817","���ߵ�����˵�",setPrices("1#5�����£���ͨˮ����ϴ#35|45;2#5�����ϣ���ͨˮ����ϴ#30|40;3#��ϴ���������#60|80"),"10",setComment(0),tmp_good_num,tmp_mid_num,tmp_bad_num,tmp_order_comm_list));
//            setPingjia(WxcUserOrder.orderinfos, "002");	
//            //116.334269,40.070255
//	        infos.add(new WxcBusInfo("002",40.070255,116.334269,  R.drawable.dian2, "����ϴ����2",  
//	                "����3km", 456,tmp_rating,"�����в�ƽ�������۵�ַ2","45Ԫ","30�˹���","13802392817","���ߵ�����˵�",setPrices("1#5�����£���ͨˮ����ϴ#25|45;2#5�����ϣ���ͨˮ����ϴ#40|50;3#��ϴ���������#80|80"),"20",setComment(1),tmp_good_num,tmp_mid_num,tmp_bad_num,tmp_order_comm_list));
//            setPingjia(WxcUserOrder.orderinfos, "003");	        
//	        infos.add(new WxcBusInfo("003",40.085340, 116.350703, R.drawable.dian3, "����ϴ����3",  
//	                "����2.3km", 1456,tmp_rating,"�����в�ƽ�������۵�ַ3","25Ԫ","13�˹���","13802392817","���ߵ�����˵�",setPrices("1#��ͨˮ����ϴ#35|55"),"20",setComment(2),tmp_good_num,tmp_mid_num,tmp_bad_num,tmp_order_comm_list));
//            setPingjia(WxcUserOrder.orderinfos, "004");	        
//	        infos.add(new WxcBusInfo( "004",40.067370,  116.359605,R.drawable.dian4, "����ϴ����4",  
//	                "����3.5km", 1456,tmp_rating,"�����в�ƽ�������۵�ַ4","30Ԫ","24�˹���","13802392817","���ߵ�����˵�",setPrices("1#��ͨˮ����ϴ#35|48;2#��ϴ���������#70|80"),"13",setComment(0),tmp_good_num,tmp_mid_num,tmp_bad_num,tmp_order_comm_list));
//            setPingjia(WxcUserOrder.orderinfos, "005");	        
//	        infos.add(new WxcBusInfo("005",40.045949,116.335268, R.drawable.dian5, "����ϴ����5",  
//	                "����4.2km", 1456,tmp_rating,"�����в�ƽ�������۵�ַ5","35Ԫ","43�˹���","13802392817","���ߵ�����˵�",setPrices("1#��ͨˮ����ϴ#25|40;2#��ϴ���������#70|80"),"15",setComment(0),tmp_good_num,tmp_mid_num,tmp_bad_num,tmp_order_comm_list));
//            setPingjia(WxcUserOrder.orderinfos, "006");	        
//	        infos.add(new WxcBusInfo("006",40.035858,116.362106,  R.drawable.dian6, "����ϴ����6",  
//	                "����2.8km", 456,tmp_rating,"�����в�ƽ�������۵�ַ6","45Ԫ","56�˹���","13802392817","���ߵ�����˵�",setPrices("1#��ͨˮ����ϴ#35|45;2#��ϴ���������#70|80"),"10",setComment(1),tmp_good_num,tmp_mid_num,tmp_bad_num,tmp_order_comm_list));
//            setPingjia(WxcUserOrder.orderinfos, "007");	        
//	        infos.add(new WxcBusInfo("007",40.065340, 116.360703, R.drawable.dian7, "����ϴ����7",  
//	                "����1km", 1456,tmp_rating,"�����в�ƽ�������۵�ַ7","25Ԫ","66�˹���","13802392817","���ߵ�����˵�",setPrices("1#��ͨˮ����ϴ#25|35"),"10",setComment(1),tmp_good_num,tmp_mid_num,tmp_bad_num,tmp_order_comm_list));
//            setPingjia(WxcUserOrder.orderinfos, "008");	        
//	        infos.add(new WxcBusInfo( "008",40.057370,  116.369605,R.drawable.dian8, "����ϴ����8",  
//	                "����3km", 1456,tmp_rating,"�����в�ƽ�������۵�ַ8","30Ԫ","25�˹���","13802392817","���ߵ�����˵�",setPrices("1#��ͨˮ����ϴ#30|45"),"15",setComment(1),tmp_good_num,tmp_mid_num,tmp_bad_num,tmp_order_comm_list));
//            setPingjia(WxcUserOrder.orderinfos, "009");	        
//	        infos.add(new WxcBusInfo( "009",40.037370,  116.339605,R.drawable.dian9, "����ϴ����9",  
//	                "����5km", 1456,tmp_rating,"�����в�ƽ�������۵�ַ9","30Ԫ","44�˹���","13802392817","���ߵ�����˵�",setPrices("1#��ͨˮ����ϴ#20|20"),"0",setComment(1),tmp_good_num,tmp_mid_num,tmp_bad_num,tmp_order_comm_list));
//            setPingjia(WxcUserOrder.orderinfos, "010");	        
//	        infos.add(new WxcBusInfo( "010",40.057370,  116.369305,R.drawable.dian10, "����ϴ����10",  
//	                "����4km", 1456,tmp_rating,"�����в�ƽ�������۵�ַ10","30Ԫ","65�˹���","13802392817","���ߵ�����˵�",setPrices("1#��ͨˮ����ϴ#30|40"),"10",setComment(2),tmp_good_num,tmp_mid_num,tmp_bad_num,tmp_order_comm_list));
//            setPingjia(WxcUserOrder.orderinfos, "011");	        
//	        infos.add(new WxcBusInfo( "011",40.063570,  116.379305,R.drawable.dian11, "����ϴ����11",  
//	                "����1.2km", 1456,tmp_rating,"�����в�ƽ�������۵�ַ11","30Ԫ","3�˹���5","13802392817","���ߵ�����˵�",setPrices("1#��ͨˮ����ϴ#20|40"),"20",setComment(2),tmp_good_num,tmp_mid_num,tmp_bad_num,tmp_order_comm_list));	        
	    }  
	    
	    public WxcBusInfo(){
	    	
	    	
	    }


	    
	    public static void addCacheInfoList(WxcBusInfo info){
	        WxcBusInfo tmp_info;
	        boolean add_flag=false;
		    for (int i=0;i<WxcBusInfo.cur_select_infos.size();i++){
		        tmp_info=(WxcBusInfo) WxcBusInfo.cur_select_infos.get(i);
		        if (tmp_info.getXcd_id().equalsIgnoreCase(info.getXcd_id())){
		        	WxcBusInfo.cur_select_infos.set(i, info);
		        	add_flag=true;
		        	return;
		        }
		    }
		    if (!add_flag){
		    	WxcBusInfo.cur_select_infos.add(info);
		    }	    	
	    }
	    
	    
		public static void replaceInfoInList(WxcBusInfo info){
	        WxcBusInfo tmp_info;
	    	for (int i=0;i<WxcBusInfo.cur_select_infos.size();i++){
	    	    tmp_info=(WxcBusInfo) WxcBusInfo.cur_select_infos.get(i);
	    	    if (tmp_info.getXcd_id().equalsIgnoreCase(info.getXcd_id())){
	    	    	WxcBusInfo.cur_select_infos.set(i, info);
	    	    	return;
	    	    }
	    	}
	    		    	
	    }	    
	    
		public static WxcBusInfo isHaveInfo(String xcd_id){
	    	WxcBusInfo tmp_info;
			for (int i=0;i<WxcBusInfo.cur_select_infos.size();i++){
	    	    tmp_info=(WxcBusInfo) WxcBusInfo.cur_select_infos.get(i);
	    	    if (tmp_info.getXcd_id().equalsIgnoreCase(xcd_id)){
	    	    	return tmp_info;
	    	    }
	    	}
			return null;			
		}
		
		public static boolean isHaveInfoComment(String xcd_id){
	    	WxcBusInfo tmp_info;
			for (int i=0;i<WxcBusInfo.cur_select_infos.size();i++){
	    	    tmp_info=(WxcBusInfo) WxcBusInfo.cur_select_infos.get(i);
	    	    if (tmp_info.getXcd_id().equalsIgnoreCase(xcd_id)){
	    	    	if (tmp_info.getLst_comment()==null){
		    	    	return false;	
	    	    	}else{
	    	    		return true;
	    	    	}
	    	    }
	    	}
			return false;			
		}		
		
		
	    
        public static List setPrices(String prices){
        	//��ʽΪ����������,����۸�;�������ƣ�����۸�
        	List prices_tmp=new ArrayList();
        	String[] arr_prices=prices.split(";");
        	String str_tmp;
        	for (int i=0;i<arr_prices.length;i++){
        		HashMap hm_price=new HashMap();
        		str_tmp=arr_prices[i];        		
        		//System.out.println("arr_prices="+str_tmp);
        		
        		String[] arr1=str_tmp.split("#");
        		hm_price.put("servicename", arr1[1]);
        		hm_price.put("serviceid", arr1[0]);
        		//System.out.println("serviceid="+arr1[0]);        		
        		//System.out.println("servicename="+arr1[1]);
        		
        		str_tmp=arr1[2];
        		//System.out.println("���м۸���Ϣ="+str_tmp);
        		String[] arr2=str_tmp.split("\\|");
        		
        		hm_price.put("serviceprice", arr2[0]);
        		//System.out.println("serviceprice="+arr2[0]);
        		
        		hm_price.put("serviceprice_old", arr2[1]);
        		//System.out.println("serviceprice_old="+arr2[1]);
        		
        		prices_tmp.add(hm_price);
        		//System.out.println("=========================================");
        	}
        	return prices_tmp;
        	
        }
        
        
        public static void setPingjia(List<WxcUserOrder> orders,String xcd_id){
        	WxcUserOrder order_tmp;
        	tmp_good_num=0;
        	tmp_mid_num=0;
        	tmp_bad_num=0;
        	tmp_rating=0;
        	
        	float tmp=0;
        	int xcd_order_num=0;
  
        	for(int i=0;i<orders.size();i++){
        		order_tmp=(WxcUserOrder) orders.get(i);
        		if (order_tmp.getXcd_id().equalsIgnoreCase(xcd_id)){
        			//��������
        			xcd_order_num=xcd_order_num+1;
        			//��������
        			tmp=tmp+Float.valueOf(order_tmp.getOrder_rating());
        			//����
        			if (order_tmp.getOrder_evaluate()==0){
        			   tmp_good_num=tmp_good_num+1;	
        			}
        			//����
        			if (order_tmp.getOrder_evaluate()==1){
        				tmp_mid_num=tmp_mid_num+1;
        			}
        			//����
        			if (order_tmp.getOrder_evaluate()==2){
        				tmp_bad_num=tmp_bad_num+1;        				
        			} 
        		}
        	}
        	if (xcd_order_num==0) {
        	tmp_rating=0;	
        	}else{
        	tmp_rating=tmp/xcd_order_num;
        	}
        	tmp_rating=(float)(Math.round(tmp_rating*10))/10;
        	System.out.println("�������rating�ǣ�"+tmp_rating);       	
        }
        
              
        public static float getAverageRating(String xcd_id,float rating){
        	  //rating:Ϊ�������ֵ�ֵ
        	  //�˴����÷���������ȡĳϴ�����ƽ������ֵ
              return rating;
        }
        
        
        
        public static List setComment(int i){
           //���Է�����i=0��û���κ����ۣ�i=1:��1������,i=2����3����������
        	List str = new ArrayList<Map<String, Object>>();
        	if (i==0){
        		
        	}else if(i==1){
        		Map<String, Object> map = new HashMap<String, Object>();
        		map.put("name",  "����1");
        		map.put("comment","����겻��ϴ�ĺܸɾ�");
        		map.put("date","[2014-11-2]");
        		str.add(map);
        		
        	}else if(i==2){
        		Map<String, Object> map = new HashMap<String, Object>();
        		map.put("name",  "����2");
        		map.put("comment","����겻��ϴ�ĺܸɾ�");
        		map.put("date","[2014-11-2]");
        		str.add(map);
        		
        		map = new HashMap<String, Object>();
        		map.put("name",  "����3");
        		map.put("comment","�����ķ��񻹿���");
        		map.put("date","[2014-11-15]");
        		str.add(map);
        		
        		map = new HashMap<String, Object>();
        		map.put("name",  "����4");
        		map.put("comment","����Ĳ���ô����ϴ�Ĳ��ɾ�");
        		map.put("date","[2014-11-3]");
        		str.add(map);
        		
        		map = new HashMap<String, Object>();
        		map.put("name",  "����5");
        		map.put("comment","�۸��е��");
        		map.put("date","[2014-11-8]");
        		str.add(map);
        	}
        	
        	return str;
        }
	    
	    
        public static WxcBusInfo getInfoById(String xcd_id){
        	WxcBusInfo tmp;
        	for (int i=0;i<WxcBusInfo.cur_select_infos.size();i++){
        		tmp=(WxcBusInfo) WxcBusInfo.cur_select_infos.get(i);
        		if (tmp.getXcd_id().equalsIgnoreCase(xcd_id)){
        			return tmp;
        		}
        	}        	
        	return null;
        }
        
        
	    public WxcBusInfo(String xcd_id,double latitude, double longitude, int imgId, String name,  
	            String distance, int zan,float rating,String address,String price,String buynum,String contactinfo,String bh,List<HashMap<String, String>> prices,String cutdown,List comment,int good_num,int mid_num,int bad_num,List order_list_comm)  
	    {  
	        super();  
	        this.xcd_id=xcd_id;
	        this.imgId = imgId;  
	        this.name = name;
	        this.rating=rating;
	        this.address=address;
	        this.latitude = latitude;  
	        this.longitude = longitude;  
	        this.distance = distance;    
	        this.buynum=buynum;
	        this.bh=bh;
	        this.contactinfo=contactinfo;
	        this.prices=prices;
	        this.cutdown=cutdown;
	        this.lst_comment=comment;
	        this.good_num=good_num;
	        this.mid_num=mid_num;
	        this.bad_num=bad_num;
	    }  
	  
	    
	    
	    
	    
	    public String getRegin_id() {
			return regin_id;
		}

		public void setRegin_id(String regin_id) {
			this.regin_id = regin_id;
		}

		public String getRegin_name() {
			return regin_name;
		}

		public void setRegin_name(String regin_name) {
			this.regin_name = regin_name;
		}

		public String getXcd_id() {
			return xcd_id;
		}

		public void setXcd_id(String xcd_id) {
			this.xcd_id = xcd_id;
		}

		public double getLatitude()  
	    {  
	        return latitude;  
	    }  
	  
	    public void setLatitude(double latitude)  
	    {  
	        this.latitude = latitude;  
	    }  
	  
	    public double getLongitude()  
	    {  
	        return longitude;  
	    }  
	  
	    public void setLongitude(double longitude)  
	    {  
	        this.longitude = longitude;  
	    }  
	  
	    public String getName()  
	    {  
	        return name;  
	    }  
	  
	    public int getImgId()  
	    {  
	        return imgId;  
	    }  
	  
	    public void setImgId(int imgPingjiaId)  
	    {  
	        this.imgId = imgId;  
	    }  
	  
	    public float getRating()  
	    {  
	        return rating;  
	    }  
	  
	    public void setRating(float rating)  
	    {  
	        this.rating = rating;  
	    }  
	    
	    public void setName(String name)  
	    {  
	        this.name = name;  
	    }  
	  
	    public String getDistance()  
	    {  
	        return distance;  
	    }  
	  
	    public void setDistance(String distance)  
	    {  
	        this.distance = distance;  
	    }  
	    
	
	    public String getAddress()  
	    {  
	        return address;  
	    }  
	  
	    public void setAddress(String address)  
	    {  
	        this.address = address;  
	    }  	    
	  
	    public String getBuynum()  
	    {  
	        return buynum;  
	    }  
	  
	    public void setBuynum(String buynum)  
	    {  
	        this.buynum = buynum;  
	    }  


	    public String getBh()  
	    {  
	        return bh;  
	    }  
	  
	    public void setBh(String bh)  
	    {  
	        this.bh = bh;  
	    }  
	    public String getContactinfo()  
	    {  
	        return contactinfo;  
	    }  
	  
	    public void setContactinfo(String contactinfo)  
	    {  
	        this.contactinfo = contactinfo;  
	    }  
	
	    public void setPrices(List<HashMap<String, String>> prices){
	    	this.prices=prices;
	    }
	    public List getPrices(){
	    	return prices;
	    }
	
	    public String getCutdown()  
	    {  
	        return cutdown;  
	    }  
	  
	    public void setCutdown(String cutdown)  
	    {  
	        this.cutdown = cutdown;  
	    }

		public List getLst_comment() {
			return lst_comment;
		}

		public void setLst_comment(List lst_comment) {
			this.lst_comment = lst_comment;
		}

		public String getIntroduce() {
			return introduce;
		}

		public void setIntroduce(String introduce) {
			this.introduce = introduce;
		}

		public int getImg_detail_id() {
			return img_detail_id;
		}

		public void setImg_detail_id(int img_detail_id) {
			this.img_detail_id = img_detail_id;
		}

       
	    
	    public List<byte[]> getList_img() {
			return list_img;
		}

		public void setList_img(List<byte[]> list_img) {
			this.list_img = list_img;
		}
		
		
		
		
		
	    public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

		public Bitmap getImgBitmp() {
	    	byte[] bytetmp;
		    Bitmap tmpBitmp;
		    if (this.list_img.size()>=0){
		    	bytetmp=(byte[]) list_img.get(0);
		    	tmpBitmp=WxcPublicUtil.Bytes2Bitmap(bytetmp);
		    	return tmpBitmp;
		    	
		    }else{
		    	return null;
		    }
		}

	    public List getLst_order_comment() {
			return lst_order_comment;
		}

		public void setLst_order_comment(List lst_order_comment) {
			this.lst_order_comment = lst_order_comment;
		}

		public int getGood_num() {
			return good_num;
		}

		public void setGood_num(int good_num) {
			this.good_num = good_num;
		}

		public int getMid_num() {
			return mid_num;
		}

		public void setMid_num(int mid_num) {
			this.mid_num = mid_num;
		}

		public int getBad_num() {
			return bad_num;
		}

		public void setBad_num(int bad_num) {
			this.bad_num = bad_num;
		}

	    
		
		
		
	    
//	    public static void main(String[] args) {
//			
//	    	List tmp=WxcBusInfo.setPrices("5�����£���ͨˮ����ϴ#35Ԫ|45Ԫ;5�����ϣ���ͨˮ����ϴ#30Ԫ|40Ԫ;��ϴ���������#60Ԫ|80Ԫ");
//	    	for (int i=0;i<tmp.size();i++){
//	    		HashMap mp=(HashMap)tmp.get(i);
//	    		System.out.println(mp.get("servicename"));
//	    		System.out.println(mp.get("serviceprice"));
//	    		System.out.println(mp.get("serviceprice_old"));
//	    		System.out.println(mp.get("=========================="));
//	    	}
//}
	    
	    
	    public String getComm_num() {
			return comm_num;
		}



		public void setComm_num(String comm_num) {
			this.comm_num = comm_num;
		}



		public static List<Bitmap> getImgList(Context context,String xcd_id){
	    	List tmp=new ArrayList();
	    	InputStream is = context.getResources().openRawResource(R.drawable.guanggao3);  
	        Bitmap mBitmap = BitmapFactory.decodeStream(is);
	        tmp.add(mBitmap);
	    	is = context.getResources().openRawResource(R.drawable.guanggao4);  
	        mBitmap = BitmapFactory.decodeStream(is);
	        tmp.add(mBitmap);
	    	is = context.getResources().openRawResource(R.drawable.guanggao5);  
	        mBitmap = BitmapFactory.decodeStream(is);
	        tmp.add(mBitmap);
	    	is = context.getResources().openRawResource(R.drawable.guanggao6);  
	        mBitmap = BitmapFactory.decodeStream(is);
	        tmp.add(mBitmap);
	        return tmp;
	    }    
	    
	    public static void getAllInfo(WxcBusInfo info){
	    	//�ӷ�����ȡ�ø�ϴ�����������ϸ��Ϣ
	    }
	    
	    
}

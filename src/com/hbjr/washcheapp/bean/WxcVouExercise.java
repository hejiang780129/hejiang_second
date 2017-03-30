package com.hbjr.washcheapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hbjr.washcheapp.R;

public class WxcVouExercise implements Serializable {

    private static final long serialVersionUID = -758459502806858414L;
    
    private String type;//0:代金券临时活动;2：获取代金券方式
	 
   	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType_sub_id() {
		return type_sub_id;
	}

	public void setType_sub_id(String type_sub_id) {
		this.type_sub_id = type_sub_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
   	 * 活动类型子代码:该类子代码，需在活动领取时，由系统自动跳转到对应的功能界面
   	 * 0:积分临时活动;
   	 * 2：获取代金券方式：
 	 *   601:注册一次
	 *   602:分享洗车店
	 *   603:洗车1次
	 *   604:评价1次
	 *   605:追加评论1次	 * 
	 * 	 606:分享朋友圈
	 *   607:推荐其它用户
	 *   608:分享朋友圈

   	 */
   	 private String type_sub_id;	 
   	 
   	 private String id;//活动id
    
	
	private String name;//活动名称
	
	private String content;//活动内容
	
	private String vou_num;//活动涉及的额度
	
	private String edate;//活动截止日期：1：长期有效
	
	private String edate_vou;//积分有效期；1：长期有效
	
	public String getEdate_vou() {
		return edate_vou;
	}

	public void setEdate_vou(String edate_vou) {
		this.edate_vou = edate_vou;
	}

	private int show_img;//展示的图片
	
	private String img_url;
			

    public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public int getShow_img() {
		return show_img;
	}

	public void setShow_img(int show_img) {
		this.show_img = show_img;
	}

public static List<WxcVouExercise> info = new ArrayList<WxcVouExercise>();		
	
    static  
    {
//    	info.add(new WxcVouExercise("001","0","","限时抢购活动","2小时之内，领取即获取500元现金券，直接打入账户","500","2014-11-28",R.drawable.daijinjuan,"1"));
//    	info.add(new WxcVouExercise("002","0","","年底大放送","为了感谢所有车主对51的喜爱，特在年底推出10000元代金券抢购活动","500","2014-11-30",R.drawable.daijinjuan1,"1"));
//    	info.add(new WxcVouExercise("003","0","","分享送代金券","这是测试代金券活动","500","2014-11-29",R.drawable.daijinjuan2,"1"));    	
//    	info.add(new WxcVouExercise("004","0","","购买保险赠送代金券","这是测试代金券活动","500","1",R.drawable.daijinjuan1,"1"));
//    	info.add(new WxcVouExercise("005","2","601","注册一次","测试注册一次","500","2014-11-28",R.drawable.daijinjuan,"1"));
//    	info.add(new WxcVouExercise("006","2","602","分享洗车店","测试分享洗车店","500","2014-11-30",R.drawable.daijinjuan1,"1"));
//    	info.add(new WxcVouExercise("007","2","603","洗车1次","测试洗车1次","500","2014-11-29",R.drawable.daijinjuan2,"1"));    	
//    	info.add(new WxcVouExercise("008","2","604","评价1次","测试评价1次","500","1",R.drawable.daijinjuan1,"1"));    	
    }
	
    public WxcVouExercise(){
    	
    }
	
	public WxcVouExercise(String id,String type,String type_sub,String name, String content, String vou_num,
			String edate,int show_img_id,String edate_vou) {
		super();
		this.id=id;
		this.type=type;
		this.type_sub_id=type_sub;
		this.name = name;
		this.content = content;
		this.vou_num = vou_num;
		this.edate = edate;
		this.show_img=show_img_id;
		this.edate_vou=edate_vou;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getVou_num() {
		return vou_num;
	}

	public void setVou_num(String vou_num) {
		this.vou_num = vou_num;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}
	
	
	
	
}

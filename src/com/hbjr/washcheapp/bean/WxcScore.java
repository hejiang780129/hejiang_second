package com.hbjr.washcheapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hbjr.washcheapp.R;

/**
 * 积分活动表
 * @author a
 *
 */

public class WxcScore implements Serializable {

	private static final long serialVersionUID = -758459502806858414L;
	 
	 private String type;//0:积分临时活动;1：积分兑换活动;2：获取积分方式
	 	 
	/**
	 * 活动类型子代码:该类子代码，需在活动领取时，由系统自动跳转到对应的功能界面
	 * 0:积分临时活动;
	 * 1：积分兑换活动;
	 * 2：获取积分方式
	 *   901:注册一次
	 *   902:分享洗车店
	 *   903:洗车1次
	 *   904:评价1次
	 *   905:追加评论1次	 * 
	 * 	 906:分享朋友圈送积分
	 *   907:推荐其它用户送积分
	 */
	 private String type_sub_id;	 
	 
	 public String getType_sub_id() {
		return type_sub_id;
	}



	public void setType_sub_id(String type_sub_id) {
		this.type_sub_id = type_sub_id;
	}

	private String id;//活动id
	 	 
	 private String name;//活动名称
		
	 private String content;//活动内容
		
	 private String score_desc;//积分说明
		
	 private String edate;//活动截止日期：1：长期有效
	 
	 private int show_img;//活动展示图片
	 
	 private String img_url;
	 
	 private String score;//涉及到的积分额度
	 
	 private String exchange_rule;//积分兑换规则，至针对type为1时有值，规则格式：100,20  逗号分隔，前面是积分数量，后面是代金券数量
	 
	 
	 
	 public String getExchange_rule() {
		return exchange_rule;
	}



	public void setExchange_rule(String exchange_rule) {
		this.exchange_rule = exchange_rule;
	}



	public String getScore() {
		return score;
	}



	public void setScore(String score) {
		this.score = score;
	}



	public int getShow_img() {
		return show_img;
	}



	public void setShow_img(int show_img) {
		this.show_img = show_img;
	}
	
	
	

	public String getImg_url() {
		return img_url;
	}



	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public static List<WxcScore> info = new ArrayList<WxcScore>();
	 public static List<WxcScore> info_0 = new ArrayList<WxcScore>();
	 public static List<WxcScore> info_1 = new ArrayList<WxcScore>();
	 public static List<WxcScore> info_2 = new ArrayList<WxcScore>();
		
	    static  
	    {
//	    	info.add(new WxcScore("000","2","分享朋友圈送积分","测试分享朋友圈送积分","20分","2014-11-30",R.drawable.daijinjuan,"21","20",""));
//	    	info.add(new WxcScore("001","0","推荐其它用户送积分","测试推荐其它用户送积分","20分","2014-12-30",R.drawable.daijinjuan1,"22","20",""));
//	    	info.add(new WxcScore("002","0","分享有礼送积分","测试分享有礼送积分","20分","2014-11-30",R.drawable.daijinjuan2,"23","20",""));
//	    	info.add(new WxcScore("003","2","分享朋友圈送积分","测试分享朋友圈送积分","20分","2014-11-20",R.drawable.daijinjuan,"","20",""));
//	    	info.add(new WxcScore("004","1","积分兑换活动1","测试积分兑换活动1","100元 换 20分","2014-11-30",R.drawable.jifenduihuan,"","","100,20"));
//	    	info.add(new WxcScore("005","1","积分兑换活动2","测试积分兑换活动2","100元 换 40分","2014-11-30",R.drawable.jifenduihuan1,"","","100,40"));
//	    	info.add(new WxcScore("006","1","积分兑换活动3","测试积分兑换活动3","100元 换 50分","2014-11-30",R.drawable.jifenduihuan2,"","","100,50"));
//	    	info.add(new WxcScore("901","2","注册一次","测试注册一次","10分","1",R.drawable.daijinjuan1,"901","10",""));
//	    	info.add(new WxcScore("902","2","分享洗车店","测试分享洗车店","50分","1",R.drawable.daijinjuan2,"902","50",""));
//	    	info.add(new WxcScore("903","2","洗车1次","测试洗车1次","10分","1",R.drawable.daijinjuan,"903","10",""));
//	    	info.add(new WxcScore("904","2","评价1次","测试评价1次","10分","1",R.drawable.daijinjuan1,"904","10",""));
//	    	info.add(new WxcScore("905","2","追加评论1次","测试追加评论1次","5分","1",R.drawable.daijinjuan2,"905","5",""));	    	
	    }
	 

	    public static void initScoreList(){
			WxcScore tmp;
			String tmp_status;
			info_0.clear();
			info_1.clear();
			info_2.clear();
			for (int i=0;i<info.size();i++){
			    tmp=(WxcScore) info.get(i);
			    tmp_status=tmp.getType();
			    if (tmp_status.equalsIgnoreCase("0")){
			    	info_0.add(tmp);
			    }else if (tmp_status.equalsIgnoreCase("1")){
			    	info_1.add(tmp);
			    }else{
			    	info_2.add(tmp);
			    }
			}
		}    
	    
	    
    public WxcScore(){
    	
    }
	    
	public WxcScore(String id,String type, String name, String content, String score_desc, String edate,int show_img_id,String sub_type,String score,String exchange_rule) {
		super();
		this.id=id;
		this.type = type;
		this.name = name;
		this.content = content;
		this.score_desc = score_desc;
		this.edate = edate;
		this.show_img=show_img_id;
		this.type_sub_id=sub_type;
		this.score=score;
		this.exchange_rule=exchange_rule;
	}
	
	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	
	public String getScore_desc() {
		return score_desc;
	}

	public void setScore_desc(String score_desc) {
		this.score_desc = score_desc;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
	 
	
	 
	 
	 
}

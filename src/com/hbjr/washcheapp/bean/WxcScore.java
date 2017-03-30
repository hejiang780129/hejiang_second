package com.hbjr.washcheapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hbjr.washcheapp.R;

/**
 * ���ֻ��
 * @author a
 *
 */

public class WxcScore implements Serializable {

	private static final long serialVersionUID = -758459502806858414L;
	 
	 private String type;//0:������ʱ�;1�����ֶһ��;2����ȡ���ַ�ʽ
	 	 
	/**
	 * ������Ӵ���:�����Ӵ��룬���ڻ��ȡʱ����ϵͳ�Զ���ת����Ӧ�Ĺ��ܽ���
	 * 0:������ʱ�;
	 * 1�����ֶһ��;
	 * 2����ȡ���ַ�ʽ
	 *   901:ע��һ��
	 *   902:����ϴ����
	 *   903:ϴ��1��
	 *   904:����1��
	 *   905:׷������1��	 * 
	 * 	 906:��������Ȧ�ͻ���
	 *   907:�Ƽ������û��ͻ���
	 */
	 private String type_sub_id;	 
	 
	 public String getType_sub_id() {
		return type_sub_id;
	}



	public void setType_sub_id(String type_sub_id) {
		this.type_sub_id = type_sub_id;
	}

	private String id;//�id
	 	 
	 private String name;//�����
		
	 private String content;//�����
		
	 private String score_desc;//����˵��
		
	 private String edate;//���ֹ���ڣ�1��������Ч
	 
	 private int show_img;//�չʾͼƬ
	 
	 private String img_url;
	 
	 private String score;//�漰���Ļ��ֶ��
	 
	 private String exchange_rule;//���ֶһ����������typeΪ1ʱ��ֵ�������ʽ��100,20  ���ŷָ���ǰ���ǻ��������������Ǵ���ȯ����
	 
	 
	 
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
//	    	info.add(new WxcScore("000","2","��������Ȧ�ͻ���","���Է�������Ȧ�ͻ���","20��","2014-11-30",R.drawable.daijinjuan,"21","20",""));
//	    	info.add(new WxcScore("001","0","�Ƽ������û��ͻ���","�����Ƽ������û��ͻ���","20��","2014-12-30",R.drawable.daijinjuan1,"22","20",""));
//	    	info.add(new WxcScore("002","0","���������ͻ���","���Է��������ͻ���","20��","2014-11-30",R.drawable.daijinjuan2,"23","20",""));
//	    	info.add(new WxcScore("003","2","��������Ȧ�ͻ���","���Է�������Ȧ�ͻ���","20��","2014-11-20",R.drawable.daijinjuan,"","20",""));
//	    	info.add(new WxcScore("004","1","���ֶһ��1","���Ի��ֶһ��1","100Ԫ �� 20��","2014-11-30",R.drawable.jifenduihuan,"","","100,20"));
//	    	info.add(new WxcScore("005","1","���ֶһ��2","���Ի��ֶһ��2","100Ԫ �� 40��","2014-11-30",R.drawable.jifenduihuan1,"","","100,40"));
//	    	info.add(new WxcScore("006","1","���ֶһ��3","���Ի��ֶһ��3","100Ԫ �� 50��","2014-11-30",R.drawable.jifenduihuan2,"","","100,50"));
//	    	info.add(new WxcScore("901","2","ע��һ��","����ע��һ��","10��","1",R.drawable.daijinjuan1,"901","10",""));
//	    	info.add(new WxcScore("902","2","����ϴ����","���Է���ϴ����","50��","1",R.drawable.daijinjuan2,"902","50",""));
//	    	info.add(new WxcScore("903","2","ϴ��1��","����ϴ��1��","10��","1",R.drawable.daijinjuan,"903","10",""));
//	    	info.add(new WxcScore("904","2","����1��","��������1��","10��","1",R.drawable.daijinjuan1,"904","10",""));
//	    	info.add(new WxcScore("905","2","׷������1��","����׷������1��","5��","1",R.drawable.daijinjuan2,"905","5",""));	    	
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

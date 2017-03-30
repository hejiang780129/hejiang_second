package com.hbjr.washcheapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hbjr.washcheapp.R;

public class WxcVouExercise implements Serializable {

    private static final long serialVersionUID = -758459502806858414L;
    
    private String type;//0:����ȯ��ʱ�;2����ȡ����ȯ��ʽ
	 
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
   	 * ������Ӵ���:�����Ӵ��룬���ڻ��ȡʱ����ϵͳ�Զ���ת����Ӧ�Ĺ��ܽ���
   	 * 0:������ʱ�;
   	 * 2����ȡ����ȯ��ʽ��
 	 *   601:ע��һ��
	 *   602:����ϴ����
	 *   603:ϴ��1��
	 *   604:����1��
	 *   605:׷������1��	 * 
	 * 	 606:��������Ȧ
	 *   607:�Ƽ������û�
	 *   608:��������Ȧ

   	 */
   	 private String type_sub_id;	 
   	 
   	 private String id;//�id
    
	
	private String name;//�����
	
	private String content;//�����
	
	private String vou_num;//��漰�Ķ��
	
	private String edate;//���ֹ���ڣ�1��������Ч
	
	private String edate_vou;//������Ч�ڣ�1��������Ч
	
	public String getEdate_vou() {
		return edate_vou;
	}

	public void setEdate_vou(String edate_vou) {
		this.edate_vou = edate_vou;
	}

	private int show_img;//չʾ��ͼƬ
	
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
//    	info.add(new WxcVouExercise("001","0","","��ʱ�����","2Сʱ֮�ڣ���ȡ����ȡ500Ԫ�ֽ�ȯ��ֱ�Ӵ����˻�","500","2014-11-28",R.drawable.daijinjuan,"1"));
//    	info.add(new WxcVouExercise("002","0","","��״����","Ϊ�˸�л���г�����51��ϲ������������Ƴ�10000Ԫ����ȯ�����","500","2014-11-30",R.drawable.daijinjuan1,"1"));
//    	info.add(new WxcVouExercise("003","0","","�����ʹ���ȯ","���ǲ��Դ���ȯ�","500","2014-11-29",R.drawable.daijinjuan2,"1"));    	
//    	info.add(new WxcVouExercise("004","0","","���������ʹ���ȯ","���ǲ��Դ���ȯ�","500","1",R.drawable.daijinjuan1,"1"));
//    	info.add(new WxcVouExercise("005","2","601","ע��һ��","����ע��һ��","500","2014-11-28",R.drawable.daijinjuan,"1"));
//    	info.add(new WxcVouExercise("006","2","602","����ϴ����","���Է���ϴ����","500","2014-11-30",R.drawable.daijinjuan1,"1"));
//    	info.add(new WxcVouExercise("007","2","603","ϴ��1��","����ϴ��1��","500","2014-11-29",R.drawable.daijinjuan2,"1"));    	
//    	info.add(new WxcVouExercise("008","2","604","����1��","��������1��","500","1",R.drawable.daijinjuan1,"1"));    	
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

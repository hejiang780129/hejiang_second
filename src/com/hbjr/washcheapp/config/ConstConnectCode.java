package com.hbjr.washcheapp.config;

/***
 * �����������Ľӿ���
 */

public class ConstConnectCode {
	
	
	 public static final String RETURN_INFO_ERROR="ȡ��Ϣʧ�ܣ������������";
	 public static final String RETURN_SYS_ERROR="ȡ��Ϣ�쳣�������������";
	
	
	   /**
	    * 
	    * fid:����id�������еĲ���   
	    * 	 
	    */	    
	 
	 /**
		 * ��ȡϵͳ��Ϣ�������汾��
		 * �ϴ���
		 * ���أ�key:code(�ɹ�/ʧ��),msgnum��curver(�汾��)
		 */
		 
		public static final String FID_GET_SYSINFO="sysinfo";	 
	 

	 /**
	 * ע�᣺fid="regist"
	 * �ϴ��� key:uname(�ֻ���),pwd,rpwd,code(��֤��))
	 * ���أ�key:code(�ɹ�/ʧ��),account(WxcUserAccount)��msgnum(��Ϣ����)��version
	 */
	 public static final String FID_REG = "regist";	
				
		
		/**
		 * ��ȡ�ֻ���֤�룺fid="getcode"
		 * �ϴ��� key:uname(�ֻ���)
		 * ���أ�key:code(�ɹ�/ʧ��)
		 */
		public static final String FID_GETCODE = "getcode";		
				
		/**
		 * ��¼��fid="login"
		 * �ϴ��� key:uname(�ֻ���),pwd
		 * ���أ�key:code(�ɹ�/ʧ��),account(WxcUserAccount)��msgnum(��Ϣ����)��version
		 */		
		public static final String FID_LOGIN = "login";
		
		
		/**
		 * ��ȡϴ�����б�������Ϣ��fid="infolist"
		 * �ϴ�����ǰ���Ⱥ�ά�ȣ�poisition_lat,poisition_lang,cursize:��ǰ�б����������ݸ������������µ�3����Ϣ,condition:0��û�и�ֵ:����������1������������2������������3����������������
		 * ���أ�code(�ɹ�/ʧ��),infolist(WxcBusInfo list)��ֻ����ϴ����id��ϴ�������ơ���ַ�����ꡢ���۵÷֡����롢��һ������۸���Ϣ����ʡ��ȡ�ͼƬ�����롢��������
		 */
		public static final String FID_GET_XCDLIST="infolist";		
		
		/**
		 * ��ȡϴ������ϸ��Ϣ:fid="info"
		 * �ϴ���xcd_id
		 * ���أ�code(�ɹ�/ʧ��),info(WxcBusInfo)��������Ϣ�����У��̼����ۣ�����3����
		 */
		public static final String FID_GET_XCD_INFO="info";
		
		
	    /**--tbd---------
		 * ��ȡϴ����������ϸ��ϢϢ:fid="info_comment"(������ϴ���������Լ�ϴ�������ж���������)
		 * �ϴ���xcd_id
		 * ���أ�code(�ɹ�/ʧ��),info_comment;
		 */
		public static final String FID_GET_XCD_COMMENT="infocomment";
		
		
		/**
		 * ��ȡϴ������ͼƬ�б�:fid="info_imgs"
		 * �ϴ���xcd_id
		 * ���أ�code(�ɹ�/ʧ��),info_imgs;
		 */
		public static final String FID_GET_XCD_IMGS="infoimgs";
		
		/**
		 * ��ȡ�����˺���Ϣ:fid="user_account"
		 * �ϴ���uname(�ֻ���)
		 * ���أ�key:code(�ɹ�/ʧ��),account(WxcUserAccount)
		 */
		public static final String FID_GET_USER_ACCOUNT="usraccount";
		
		
		/**
		 * ��ȡ���������б�:fid="user_orders"
		 * �ϴ���uname(�ֻ���)
		 * ���أ�key:code(�ɹ�/ʧ��),orderlist(WxcUserOrder)
		 */
		public static final String FID_GET_USER_ORDER_LIST="usrordlist";
		
		
	    /**--tbd---------
		 * ��ȡ����������ϸ��Ϣ:fid="order_comment"
		 * �ϴ���xcd_id
		 * ���أ�code(�ɹ�/ʧ��),order_comment;
		 */
		public static final String FID_GET_ORDER_COMMENT="ordercomment";
		
		/**
		 * ��ȡ��������ȯ�б�
		 * �ϴ���uname(�ֻ���)
		 * ���أ�key:code(�ɹ�/ʧ��),voulist(WxcUserVou)
		 */
		public static final String FID_GET_USER_VOU_LIST="usrvlist";
		
		/**
		 * ��ȡ51����ȯ��б�
		 * �ϴ���
		 * ���أ�key:code(�ɹ�/ʧ��),vlist(WxcVouExercise)
		 */
		public static final String FID_GET_VOU_LIST="vlist";		
		
		
		/**
		 * ��ȡ���������б�
		 * �ϴ���uname(�ֻ���)
		 * ���أ�key:code(�ɹ�/ʧ��),usrslist(WxcUserScore)
		 */
		public static final String FID_GET_USER_SCORE_LIST="usrslist";		
		
		/**
		 * ��ȡ51���ֻ�б�
		 * �ϴ���
		 * ���أ�key:code(�ɹ�/ʧ��),slist(WxcScore)
		 */
		public static final String FID_GET_SCORE_LIST="slist";
		
				
		/**
		 * ��ȡ��Ϣ�б������û��ĺ�ϵͳ��
		 * �ϴ���uname
		 * ���أ�key:code(�ɹ�/ʧ��),msglist(WxcMessage)
		 */
		 
		public static final String FID_GET_MESSAGE_LIST="msglist";
		
		
		//====================================================================						
		
		 /**
		 * �ύ��ϴ��������		
		 * fid="xcdcom"
		 * �ϴ��� uname(�ֻ���),xcd_id,content(����)
		 * ���أ�key:code(�ɹ�/ʧ��),rcode:0���ɹ���1��ʧ�ܣ�rmsg:ʧ��ԭ��
		 */
		 public static final String FID_PUT_XCD_COMM= "xcdcomm";
		 
		 /**
		 * �ύϴ���궩��
		 * fid="creatorder"
		 * �ϴ��� uname(�ֻ���),WxcUserOrder
		 * ���أ�key:code(�ɹ�/ʧ��),rcode:0���ɹ���1��ʧ�ܣ�rmsg:ʧ��ԭ��,ordid:�������,ordate:����ʱ��
		 */
		 public static final String FID_PUT_ORDER= "creatorder";
		 
		 
		 /**
		 * ��������
		 * fid="ordereva"
		 * �ϴ��� uname(�ֻ���),ordid,eva:���ۣ�0��������1��������2������,rat:����,dis:����
		 * ���أ�key:code(�ɹ�/ʧ��),rcode:0���ɹ���1��ʧ�ܣ�evadate:����ʱ��,rat:ƽ����
		 */
		 public static final String FID_PUT_ORDER_EVA= "ordereva";
		 
		 
		 /**
		 * ����ϴ������Ϣ
		 * fid="sharexcd"
		 * �ϴ��� uname(�ֻ���),info:WxcBusInfo,imglist:list{String}
		 * ���أ�key:code(�ɹ�/ʧ��),rcode:0���ɹ���1��ʧ�ܣ�
		 */
		 public static final String FID_PUT_SHARE_XCD= "sharexcd";
		 
		
		 /**
		 * ����ִ���ύ
		 * fid="benefit"
		 * �ϴ��� islogin:0:δ��½��1��½;phone(�ֻ���);type:1:�ǻ��ֻ,2:���ֶһ�,3:����ȯ�;usrscore:���ּ�¼;usrvou:����ȯ��¼;	
		 * ���أ�key:code(�ɹ�/ʧ��),rcode:0���ɹ���1��ʧ�ܣ�bdate:����ʱ��
		 */
		 public static final String FID_PUT_BENEFIT= "benefit";
		
		 
		 //============================================������ϴ��������================================================
		 
		 /**
			*��ᶩ�� 
			*fid="finishorder"
			* �ϴ��� xcd_id,ord_id;	
			* ���أ�key:code(�ɹ�/ʧ��),rcode:0���ɹ���1��ʧ�ܣ�bdate:���ʱ��
		*/
		 public static final String FID_PUT_XCD_FINORD= "finishorder";
		 
		 
		 
	
}

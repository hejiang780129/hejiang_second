package com.hbjr.washcheapp.config;

/***
 * 服务器交互的接口类
 */

public class ConstConnectCode {
	
	
	 public static final String RETURN_INFO_ERROR="取信息失败，请检查网络情况";
	 public static final String RETURN_SYS_ERROR="取信息异常，请检查网络情况";
	
	
	   /**
	    * 
	    * fid:功能id，必须有的参数   
	    * 	 
	    */	    
	 
	 /**
		 * 获取系统消息数量、版本号
		 * 上传：
		 * 返回：key:code(成功/失败),msgnum，curver(版本号)
		 */
		 
		public static final String FID_GET_SYSINFO="sysinfo";	 
	 

	 /**
	 * 注册：fid="regist"
	 * 上传： key:uname(手机号),pwd,rpwd,code(验证码))
	 * 返回：key:code(成功/失败),account(WxcUserAccount)、msgnum(信息条数)、version
	 */
	 public static final String FID_REG = "regist";	
				
		
		/**
		 * 获取手机验证码：fid="getcode"
		 * 上传： key:uname(手机号)
		 * 返回：key:code(成功/失败)
		 */
		public static final String FID_GETCODE = "getcode";		
				
		/**
		 * 登录：fid="login"
		 * 上传： key:uname(手机号),pwd
		 * 返回：key:code(成功/失败),account(WxcUserAccount)、msgnum(信息条数)、version
		 */		
		public static final String FID_LOGIN = "login";
		
		
		/**
		 * 获取洗车店列表（主体信息）fid="infolist"
		 * 上传：当前精度和维度：poisition_lat,poisition_lang,cursize:当前列表条数，根据该条数，返回新的3条信息,condition:0或没有该值:按距离排序、1：按距离排序、2：按评分排序，3：按购买人数排序
		 * 返回：code(成功/失败),infolist(WxcBusInfo list)：只包括洗车店id、洗车店名称、地址、坐标、评价得分、距离、第一条服务价格信息，节省额度、图片、距离、购买人数
		 */
		public static final String FID_GET_XCDLIST="infolist";		
		
		/**
		 * 获取洗车店详细信息:fid="info"
		 * 上传：xcd_id
		 * 返回：code(成功/失败),info(WxcBusInfo)：所有信息，其中，商家评论（最新3条）
		 */
		public static final String FID_GET_XCD_INFO="info";
		
		
	    /**--tbd---------
		 * 获取洗车店评论详细信息息:fid="info_comment"(包括对洗车店评论以及洗车店所有订单的评论)
		 * 上传：xcd_id
		 * 返回：code(成功/失败),info_comment;
		 */
		public static final String FID_GET_XCD_COMMENT="infocomment";
		
		
		/**
		 * 获取洗车店评图片列表:fid="info_imgs"
		 * 上传：xcd_id
		 * 返回：code(成功/失败),info_imgs;
		 */
		public static final String FID_GET_XCD_IMGS="infoimgs";
		
		/**
		 * 获取车主账号信息:fid="user_account"
		 * 上传：uname(手机号)
		 * 返回：key:code(成功/失败),account(WxcUserAccount)
		 */
		public static final String FID_GET_USER_ACCOUNT="usraccount";
		
		
		/**
		 * 获取车主订单列表:fid="user_orders"
		 * 上传：uname(手机号)
		 * 返回：key:code(成功/失败),orderlist(WxcUserOrder)
		 */
		public static final String FID_GET_USER_ORDER_LIST="usrordlist";
		
		
	    /**--tbd---------
		 * 获取订单评论详细信息:fid="order_comment"
		 * 上传：xcd_id
		 * 返回：code(成功/失败),order_comment;
		 */
		public static final String FID_GET_ORDER_COMMENT="ordercomment";
		
		/**
		 * 获取车主代金券列表
		 * 上传：uname(手机号)
		 * 返回：key:code(成功/失败),voulist(WxcUserVou)
		 */
		public static final String FID_GET_USER_VOU_LIST="usrvlist";
		
		/**
		 * 获取51代金券活动列表
		 * 上传：
		 * 返回：key:code(成功/失败),vlist(WxcVouExercise)
		 */
		public static final String FID_GET_VOU_LIST="vlist";		
		
		
		/**
		 * 获取车主积分列表
		 * 上传：uname(手机号)
		 * 返回：key:code(成功/失败),usrslist(WxcUserScore)
		 */
		public static final String FID_GET_USER_SCORE_LIST="usrslist";		
		
		/**
		 * 获取51积分活动列表
		 * 上传：
		 * 返回：key:code(成功/失败),slist(WxcScore)
		 */
		public static final String FID_GET_SCORE_LIST="slist";
		
				
		/**
		 * 获取信息列表，包括用户的和系统的
		 * 上传：uname
		 * 返回：key:code(成功/失败),msglist(WxcMessage)
		 */
		 
		public static final String FID_GET_MESSAGE_LIST="msglist";
		
		
		//====================================================================						
		
		 /**
		 * 提交对洗车店评论		
		 * fid="xcdcom"
		 * 上传： uname(手机号),xcd_id,content(内容)
		 * 返回：key:code(成功/失败),rcode:0：成功，1：失败，rmsg:失败原因
		 */
		 public static final String FID_PUT_XCD_COMM= "xcdcomm";
		 
		 /**
		 * 提交洗车店订单
		 * fid="creatorder"
		 * 上传： uname(手机号),WxcUserOrder
		 * 返回：key:code(成功/失败),rcode:0：成功，1：失败，rmsg:失败原因,ordid:订单编号,ordate:订单时间
		 */
		 public static final String FID_PUT_ORDER= "creatorder";
		 
		 
		 /**
		 * 订单评价
		 * fid="ordereva"
		 * 上传： uname(手机号),ordid,eva:评价，0：好评，1：中评，2：差评,rat:评分,dis:评论
		 * 返回：key:code(成功/失败),rcode:0：成功，1：失败，evadate:评价时间,rat:平均分
		 */
		 public static final String FID_PUT_ORDER_EVA= "ordereva";
		 
		 
		 /**
		 * 分享洗车店信息
		 * fid="sharexcd"
		 * 上传： uname(手机号),info:WxcBusInfo,imglist:list{String}
		 * 返回：key:code(成功/失败),rcode:0：成功，1：失败，
		 */
		 public static final String FID_PUT_SHARE_XCD= "sharexcd";
		 
		
		 /**
		 * 各类活动执行提交
		 * fid="benefit"
		 * 上传： islogin:0:未登陆，1登陆;phone(手机号);type:1:是积分活动,2:积分兑换,3:代金券活动;usrscore:积分记录;usrvou:代金券记录;	
		 * 返回：key:code(成功/失败),rcode:0：成功，1：失败，bdate:生成时间
		 */
		 public static final String FID_PUT_BENEFIT= "benefit";
		
		 
		 //============================================以下是洗车店请求================================================
		 
		 /**
			*完结订单 
			*fid="finishorder"
			* 上传： xcd_id,ord_id;	
			* 返回：key:code(成功/失败),rcode:0：成功，1：失败，bdate:完结时间
		*/
		 public static final String FID_PUT_XCD_FINORD= "finishorder";
		 
		 
		 
	
}

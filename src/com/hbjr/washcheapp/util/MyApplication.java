package com.hbjr.washcheapp.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/***
 * 负责关闭Activity的类
 * @author Administrator
 *
 */
public class MyApplication extends Application {
	 private List<Activity> activityList = new LinkedList<Activity>(); 
	    private static MyApplication instance; 
	    private MyApplication() { 
	    } 
	    /**
	     *  单例模式中获取唯一的MyApplication实例
	     */
	    public static MyApplication getInstance() { 
	        if (null == instance) { 
	            instance = new MyApplication(); 
	        } 
	        return instance; 
	    } 
	    /**
	     * 添加Activity到容器中
	     */
	    public void addActivity(Activity activity) { 
	        activityList.add(activity); 
	    } 
	    /**
	     * 遍历所有Activity并finish
	     */
	    public void exit() { 
	    	System.out.println("关闭的application 数量 is :"+activityList.size());
	        for (Activity activity : activityList) { 
		    	System.out.println("关闭的application is :"+activity.getTitle());	        	
	            activity.finish(); 
	        } 
	        System.exit(0); 
	    } 
}

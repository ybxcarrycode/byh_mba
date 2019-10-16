package com.xszj.mba.utils;


import com.xszj.mba.BuildConfig;

/**
 * Created by QQ on 2016/3/9.
 */
public class ServiceUtils {


    public static String SERVICE_ADDR;
    public static String SERVICE_HOME_ADDR;
    public static String SERVICE_KAO_QUAN;
    public static String SERVICE_JPUSH_CHANNID;


    public static String SERVICE_ABOUT_SCHOOL;
    public static String SERVICE_ABOUT_HOME;


    static {
        if (BuildConfig.DEBUG) {

            //学校相关
            SERVICE_ABOUT_SCHOOL = "http://mobile.byhmba.com";
            //首页相关
            SERVICE_ABOUT_HOME = "http://mobile.byhmba.com";
            //首页相关
            SERVICE_KAO_QUAN = "http://api.51kkww.com/";

        } else {

            //学校相关
            SERVICE_ABOUT_SCHOOL = "http://mobile.byhmba.com";
            //首页相关
            SERVICE_ABOUT_HOME = "http://mobile.byhmba.com";
            //首页相关
            SERVICE_KAO_QUAN = "http://api.51kkww.com/";
        }
    }

}




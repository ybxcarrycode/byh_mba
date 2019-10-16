package com.xszj.mba.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.xszj.mba.activity.LoginNewActivity;

/**
 * Created by Ybx on 2016/11/28.
 */
public class LoginUtils {


    /**
     * 没有登录时  跳转到登录界面
     */
    public static void gotoLginActivity(Context context, int type) {
        Activity activity = (Activity) context;
        if (null == context || null == activity || activity.isFinishing()) {
            return;
        }

        Intent intent = new Intent(context, LoginNewActivity.class);
        //type =1 :登陆成功后  返回首页   type = 0 :登陆成功后  返回之前的界面
        if (type == 0) {
            intent.putExtra("currentItem", 31);
        } else {
            intent.putExtra("currentItem", 32);
            ((Activity) context).finish();
        }
        context.startActivity(intent);
    }


}

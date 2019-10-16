package com.xszj.mba.activity.moudel;


import com.xszj.mba.activity.listener.LoginListener;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface LoginMoudel {

    void login(String phone, String pwd, LoginListener loginListener);

}

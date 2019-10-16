package com.xszj.mba.activity.presenter;


import com.xszj.mba.activity.listener.LoginListener;
import com.xszj.mba.activity.moudel.LoginMoudelImpl;
import com.xszj.mba.activity.view.LoginView;

/**
 * Created by Administrator on 2016/11/11.
 */
public class LoginPresenterImpl implements LoginPresenter,LoginListener {

    private LoginMoudelImpl loginMoudel;
    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginMoudel = new LoginMoudelImpl();
    }

    @Override
    public void onSuccess(String content) {
        loginView.showSuccess(content);
    }

    @Override
    public void onError() {
        loginView.showError();
    }

    @Override
    public void login(String phone, String pwd) {
        loginMoudel.login(phone,pwd,this);
    }
}

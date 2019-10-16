package com.xszj.mba.activity.view;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface LoginView {

    void showLoading();

    void hideLoading();

    void showError();

    void showSuccess(String content);
}

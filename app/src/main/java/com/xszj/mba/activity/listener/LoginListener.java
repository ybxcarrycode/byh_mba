package com.xszj.mba.activity.listener;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface LoginListener {

    /**
     * 成功时回调
     *
     * @param content
     */
    void onSuccess(String content);
    /**
     * 失败时回调，简单处理，没做什么
     */
    void onError();
}

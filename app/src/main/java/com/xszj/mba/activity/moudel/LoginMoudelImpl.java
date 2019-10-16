package com.xszj.mba.activity.moudel;


import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xszj.mba.activity.listener.LoginListener;
import com.xszj.mba.utils.AESOperator;
import com.xszj.mba.utils.MD5Util;
import com.xszj.mba.utils.ServiceUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/11.
 */
public class LoginMoudelImpl implements LoginMoudel{


    private String respose;
    @Override
    public void login(String phone, String pwd, final LoginListener loginListener) {
/**
 * 传递登录所需参数
 */

        HashMap<String, String> map = new HashMap<>();
        map.put("userName", phone);
        map.put("pwd", pwd);

        String ensc = AESOperator.getInstance().enc(map);
        String sign = MD5Util.MD5Encode(ensc + "yunshuxie.com/1029", "UTF-8");
        final String url = ServiceUtils.SERVICE_ADDR + "v1/app/login.json?params=" + ensc + "&sign=" + sign;

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                respose = responseInfo.result;
                loginListener.onSuccess(respose);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                loginListener.onError();
            }
        });
    }
}

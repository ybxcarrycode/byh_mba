package com.xszj.mba.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.net.nim.demo.DemoCache;
import com.net.nim.demo.config.preference.Preferences;
import com.net.nim.demo.config.preference.UserPreferences;
import com.netease.nim.uikit.cache.DataCacheManager;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.common.util.string.MD5;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.xszj.mba.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/25.
 */
public class NmLoginUtils {


    public static void nmLogin(final Context context,final String account,String pwd){

        AbortableFuture<LoginInfo> loginRequest;
        final String token = tokenFromPassword(context,pwd);
        // 登录
        loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token));
        Log.e("dssdADA", "accout=="+account+"pwd="+token);
        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                LogUtil.e("ddddd", "login success");
                DemoCache.setAccount(account);
                saveLoginInfo(account, token);
                saveName(context);

                // 初始化消息提醒
                NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

                // 初始化免打扰
                if (UserPreferences.getStatusConfig() == null) {
                    UserPreferences.setStatusConfig(DemoCache.getNotificationConfig());
                }
                NIMClient.updateStatusBarNotificationConfig(UserPreferences.getStatusConfig());

                // 构建缓存
                DataCacheManager.buildDataCacheAsync();

            }

            @Override
            public void onFailed(int code) {
                LogUtil.e("ddddd", "login success=="+code);
                if (code == 302 || code == 404) {
                    Toast.makeText(context, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
                LogUtil.e("ddddd", "login success=="+exception);
                Toast.makeText(context, R.string.login_exception, Toast.LENGTH_LONG).show();
            }
        });
    }

    private static void saveName(Context context) {
        Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);
        fields.put(UserInfoFieldEnum.Name, SharedPreferencesUtils.getProperty(context,"nickName"));
        fields.put(UserInfoFieldEnum.AVATAR, SharedPreferencesUtils.getProperty(context,"headPic"));
        NIMClient.getService(UserService.class).updateUserInfo(fields)
                .setCallback(new RequestCallbackWrapper<Void>() {
                    @Override
                    public void onResult(int i, Void aVoid, Throwable throwable) {

                    }
                });
    }

    //DEMO中使用 username 作为 NIM 的account ，md5(password) 作为 token
    //开发者需要根据自己的实际情况配置自身用户系统和 NIM 用户系统的关系
    private static String tokenFromPassword(Context context,String password) {
        String appKey = readAppKey(context);
        boolean isDemo = "45c6af3c98409b18a84451215d0bdd6e".equals(appKey)
                || "fe416640c8e8a72734219e1847ad2547".equals(appKey);

        return isDemo ? MD5.getStringMD5(password) : password;
    }

    private static String readAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }
}

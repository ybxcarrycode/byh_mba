package com.xszj.mba.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.net.nim.demo.NimApplication;
import com.net.nim.demo.config.preference.Preferences;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.utils.CommonUtils;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.drawingBoard.DrawingTestActivity;
import com.xszj.mba.utils.SharedPreferencesUtils;
import com.xszj.mba.view.GlobalTitleView;

/**
 * 设置
 * Created by Administrator on 2017/2/15.
 */

public class SetingActivity extends BaseActivity {

    private GlobalTitleView titleView;
    private LinearLayout ll_feedback, ll_about;
    private Button btn_exit;


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        ll_feedback = (LinearLayout) findViewById(R.id.ll_feedback);
        ll_about = (LinearLayout) findViewById(R.id.ll_about);
        btn_exit = (Button) findViewById(R.id.btn_exit);
    }

    @Override
    protected void initViews() {
        titleView.setTitle("设置");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);

    }

    @Override
    protected void initListeners() {
        ll_feedback.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_feedback:
                startActivity(new Intent(context, FeedbackActivty.class));
                break;

            case R.id.ll_about:
                startActivity(new Intent(context,AboutOurActivity.class));
//                startActivity(new Intent(context, DrawingTestActivity.class));
                break;
            case R.id.btn_exit:
                logout();
                break;
        }
    }


    /**
     * 注销
     */
    private void logout() {
        removeLoginState();
        startActivity(new Intent(context, MainActivity.class));
        NimApplication.user = null;
        SharedPreferencesUtils.setProperty(context, "nickName", "");
        SharedPreferencesUtils.setProperty(context, "headPic", "");
        SharedPreferencesUtils.setProperty(context, "memberID", "");
        finish();
        NIMClient.getService(AuthService.class).logout();
    }


    /**
     * 清除登陆状态
     */
    private void removeLoginState() {
        Preferences.saveUserToken("");
        if (CommonUtils.isLogin(context)) {
            CommunitySDK sdk = CommunityFactory.getCommSDK(this);
            sdk.logout(context, new LoginListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onComplete(int i, CommUser commUser) {
                }
            });
        }
    }
}

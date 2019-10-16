package com.xszj.mba.activity;

import android.os.Bundle;
import android.view.Window;

import com.net.nim.demo.main.fragment.SessionListFragment;
import com.netease.nim.uikit.common.activity.UI;
import com.xszj.mba.R;
import com.xszj.mba.view.GlobalTitleView;

/**
 * Created by Administrator on 2016/12/1.
 */
public class MessageActivity extends UI {

    private GlobalTitleView titleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        initView();
        onInit();
    }

    private void initView() {

        titleView = (GlobalTitleView)findViewById(R.id.globalTitleView);
        titleView.setTitle("消息中心");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);
    }

    private void onInit() {
        getSupportFragmentManager().beginTransaction().replace(R.id.welcome_container, new SessionListFragment()).commitAllowingStateLoss();
    }
}

package com.umeng.comm.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.umeng.comm.core.utils.ResFinder;

/**
 * Created by wangfei on 15/11/26.
 */
public class TopicActivity extends FragmentActivity{
    public static String uid;
    private View mBackBtn;
    private View mSettingBtn;
    private TextView mTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getIntent()!=null){
            uid = getIntent().getStringExtra("uid");
        }
        setContentView(ResFinder.getLayout( "umeng_commm_topic_dialog"));

        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }


        mBackBtn = (View)findViewById(ResFinder.getId(
                "umeng_comm_title_back_btn"));
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleTv = (TextView) findViewById(ResFinder.getId("umeng_comm_title_tv"));
        mTitleTv.setText(ResFinder.getString("umeng_comm_topic_list"));
        mSettingBtn = findViewById(ResFinder.getId("umeng_comm_title_setting_btn"));
        mSettingBtn.setVisibility(View.GONE);
    }
}

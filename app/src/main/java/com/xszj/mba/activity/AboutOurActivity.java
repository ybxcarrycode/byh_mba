package com.xszj.mba.activity;

import android.widget.ImageView;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.utils.FilesUtils;
import com.xszj.mba.view.GlobalTitleView;

/**
 * 关于我们
 * Created by Administrator on 2017/2/16.
 */

public class AboutOurActivity extends BaseActivity {
    private GlobalTitleView titleView;
    private ImageView iv_about_us;
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_about_our;
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        iv_about_us = (ImageView)findViewById(R.id.iv_about_us);

    }

    @Override
    protected void initViews() {
        titleView.setTitle("关于我们");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);

        iv_about_us.setImageBitmap(FilesUtils.readBitMap(context,R.mipmap.icon_about_us));
    }

    @Override
    protected void initListeners() {

    }

}

package com.umeng.comm.ui.activities;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.umeng.comm.ui.fragments.RecommendTopicFragment;
import com.umeng.comm.ui.fragments.RecommendUserFragment;
import com.umeng.common.ui.activities.GuideBaseActivity;


/**
 * Created by wangfei on 16/1/25.
 */
public class GuideActivity extends GuideBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
    }

    @Override
    protected void showTopicFragment() {
                RecommendTopicFragment topicRecommendDialog =RecommendTopicFragment.newRecommendTopicFragment();
       topicRecommendDialog.setShowActionbar(true);
        topicRecommendDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                showRecommendUserFragment();
            }

        });
        addFragment(mContainer, topicRecommendDialog);
    }

    @Override
    protected void showRecommendUserFragment() {
        setFragmentContainerId(mContainer);
        RecommendUserFragment recommendUserFragment = new RecommendUserFragment();
        recommendUserFragment.setShowActionbar(true);
        replaceFragment(mContainer, recommendUserFragment);
    }
}

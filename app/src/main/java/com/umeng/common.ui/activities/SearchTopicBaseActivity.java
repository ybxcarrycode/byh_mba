package com.umeng.common.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.umeng.comm.core.utils.ResFinder;
import com.umeng.common.ui.fragments.BaseFragment;


/**
 * Created by wangfei on 16/1/19.
 */
public abstract class SearchTopicBaseActivity<T extends BaseFragment> extends BaseFragmentActivity {
    protected T mSearchFragment;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(ResFinder.getLayout("umeng_comm_search_activity"));
        mSearchFragment = createSearchTopicFragment();
        int container = ResFinder.getId("umeng_comm_main_container");

        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }


        setFragmentContainerId(container);
        showFragmentInContainer(container, mSearchFragment);
    }
    protected abstract T createSearchTopicFragment();
}

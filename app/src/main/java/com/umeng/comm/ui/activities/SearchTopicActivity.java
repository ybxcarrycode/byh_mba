package com.umeng.comm.ui.activities;


import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.umeng.comm.ui.fragments.SearchTopicFragment;
import com.umeng.common.ui.activities.SearchTopicBaseActivity;


/**
 * Created by wangfei on 15/12/8.
 */
public class SearchTopicActivity extends SearchTopicBaseActivity<SearchTopicFragment> {

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
    protected SearchTopicFragment createSearchTopicFragment() {
        return new SearchTopicFragment();
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {

            ((SearchTopicFragment)mSearchFragment).executeSearch();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}

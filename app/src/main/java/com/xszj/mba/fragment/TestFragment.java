package com.xszj.mba.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xszj.mba.R;
import com.xszj.mba.base.BaseFragment;

/**
 * Created by Ybx on 2016/12/12.
 */

public class TestFragment extends BaseFragment implements Loadable{
    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragemnt_test_layout, container, false);
    }

    @Override
    protected void bindViews(View view) {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public <T> void onLoad(T t, int type) {

    }
}

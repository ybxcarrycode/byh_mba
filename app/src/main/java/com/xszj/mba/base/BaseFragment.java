package com.xszj.mba.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xszj.mba.R;
import com.xszj.mba.utils.DialogProgressHelper;
import com.xszj.mba.view.NormalEmptyView;
import com.xszj.mba.view.PullToRefreshView;

/**
 * Created by Administrator on 2016/11/30.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {


    protected Handler refreshUi = new Handler();

    protected DialogProgressHelper dialogProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateCustomerView(inflater, container, savedInstanceState);
    }

    protected abstract View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getIntentDate();
        initTitle(view);
        bindViews(view);
        initViews();
        initListeners();
        getDateForService();
    }


    /**
     * 初始化 view
     */
    protected abstract void bindViews(View view);


    /**
     * 设置 view (比如：设置背景、大小等)
     */
    protected abstract void initViews();


    /**
     * 设置 view 的listener (添加以及实现)
     */
    protected abstract void initListeners();


    /**
     * 获取从其他界面传来的参数
     */
    protected void getIntentDate() {
    }


    /**
     * 设置Title
     */
    protected void initTitle(View view) {
    }


    /**
     * 初始加载  获取数据
     */
    protected void getDateForService() {
    }

    protected void dismissProgressDialog() {
        ((BaseActivity) getActivity()).dismissProgressDialog();
    }

    protected void showProgressDialog() {
        ((BaseActivity) getActivity()).showProgressDialog();
    }

    /**
     * 显示提示
     *
     * @param msg 要显示的提示文字
     */
    protected void showToast(final String msg) {
        if (null != refreshUi)
            refreshUi.post(new Runnable() {

                @Override
                public void run() {
                    if (null != getActivity())
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            });
    }


    protected void hideList(final PullToRefreshView ptr) {
        refreshUi.post(new Runnable() {

            @Override
            public void run() {
                try {
                    ptr.onHeaderRefreshComplete();
                } catch (Exception e) {

                }
                try {
                    ptr.onFooterRefreshComplete();
                } catch (Exception e) {

                }
            }
        });
    }

    public void setError(com.xszj.mba.view.NormalEmptyView mEmptyView, View contentLayout) {
        mEmptyView.tv_empty_text.setText(R.string.nonet);
        mEmptyView.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        mEmptyView.setEmptyType(NormalEmptyView.EMPTY_TYPE_ERROR);
    }

    public void setHasData(com.xszj.mba.view.NormalEmptyView mEmptyView, View contentLayout) {
        mEmptyView.setVisibility(View.GONE);
        contentLayout.setVisibility(View.VISIBLE);
    }
}

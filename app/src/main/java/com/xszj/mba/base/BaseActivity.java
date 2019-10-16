package com.xszj.mba.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.xszj.mba.R;
import com.xszj.mba.utils.DialogProgressHelper;
import com.xszj.mba.view.PullToRefreshView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ybx on 2016/11/28.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    protected Context context;
    protected DialogProgressHelper dialogProgress;

    protected boolean isTouchHideKeyBoard = false;

    protected Handler refreshUi = new Handler();

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        context = BaseActivity.this;

        initProgressDialog();
        beforeContentView();
        setContentView(getContentViewResId());

        mUnbinder = ButterKnife.bind(this);

        getIntentDate();
        initTitle();
        bindViews();
        initViews();
        initListeners();
        getDateForService();

        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }

    }


    /**
     * 设置activity view ID
     */
    protected abstract int getContentViewResId();


    /**
     * 初始化 view
     */
    protected abstract void bindViews();


    /**
     * 设置 view (比如：设置背景、大小、是否显示等)
     */
    protected abstract void initViews();


    /**
     * 设置 view 的listener (添加以及实现)
     */
    protected abstract void initListeners();


    /**
     * 添加
     */
    protected void initProgressDialog() {
        dialogProgress = DialogProgressHelper.show(BaseActivity.this, null, true, false, true, null);
    }

    /**
     * 添加view前对activity设置的方法  （比如 设置Window.FEATURE_NO_TITLE等）
     */
    protected void beforeContentView() {
    }


    /**
     * 获取从其他界面传来的参数
     */
    protected void getIntentDate() {
    }


    /**
     * 设置Title
     */
    protected void initTitle() {
    }

    /**
     * 初始加载  获取数据
     */
    protected void getDateForService() {
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

    /**
     * 隐藏软键盘
     *
     * @param token
     */
    protected void hideSoftInput() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null && getCurrentFocus() != null
                && getCurrentFocus().getWindowToken() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftKeyBoard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(this.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
        }
    }


    public void showProgressDialog() {
        try {
            if (dialogProgress != null && !dialogProgress.isShowing()) {
                dialogProgress.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgressDialog() {
        if (dialogProgress != null && dialogProgress.isShowing()) {
            dialogProgress.dismiss();
        }

    }


    /**
     * 显示提示  (Toast)
     *
     * @param msg 要显示的提示文字
     */
    protected void showToast(final String msg) {
        if (null != refreshUi)
            refreshUi.post(new Runnable() {

                @Override
                public void run() {
                    if (null != context)
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return super.onKeyDown(keyCode, event);
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 添加了onTouchEvent 事件 点击屏幕隐藏软键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (isTouchHideKeyBoard) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (getCurrentFocus() != null
                        && getCurrentFocus().getWindowToken() != null) {
                    manager.hideSoftInputFromWindow(getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }


}

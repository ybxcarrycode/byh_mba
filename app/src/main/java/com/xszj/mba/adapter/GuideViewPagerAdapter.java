package com.xszj.mba.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.xszj.mba.R;
import com.xszj.mba.activity.LoginNewActivity;
import com.xszj.mba.activity.MainActivity;
import com.xszj.mba.activity.SignInActivity;
import com.xszj.mba.utils.SharedPreferencesUtils;

import java.util.List;

/**
 * *************************************************************************
 * <p/>
 * <pre></pre>
 *
 * @文件名称: ViewPagerAdapter.java
 * @类描述: ViewPager适配器
 * @版本: V1.5
 * @创建人： wangzhan
 * @创建时间：2015-8-19 下午3:13:25
 * <p/>
 * *************************************************************************
 */
public class GuideViewPagerAdapter extends PagerAdapter {

    // 界面列表
    private List<View> views;
    private Activity activity;
    Button log_zhuche;
    public GuideViewPagerAdapter(List<View> views, Activity activity) {
        this.views = views;
        this.activity = activity;
    }

    // 销毁arg1位置的界面
    @Override
    public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
        arg0.removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    // 获得当前界面数
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    // 初始化arg1位置的界面
    @Override
    public Object instantiateItem(ViewGroup arg0, int arg1) {
        arg0.addView(views.get(arg1), 0);
        if (arg1 == views.size() - 1) {

            Button btnLogin = (Button) arg0.findViewById(R.id._guide_btn_login);
            btnLogin.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    setGuided();
                    goLogin();

                }
            });

        }
        log_zhuche = (Button) arg0.findViewById(R.id.log_zhuche);
        log_zhuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGuided();
                Intent intent = new Intent(activity, SignInActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        Button log_login = (Button) arg0.findViewById(R.id.log_login);
        log_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGuided();
                Intent intent = new Intent(activity, LoginNewActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });

//        }
        return views.get(arg1);
    }

    private void goLogin() {
        // 跳转
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * method desc：设置已经引导过了，下次启动不用再次引导
     */
    private void setGuided() {
        SharedPreferencesUtils.setIschecked(activity,"isFirstIn",true);
    }

    // 判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

}
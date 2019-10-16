package com.xszj.mba.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.net.nim.demo.main.fragment.SessionListFragment;
import com.netease.nim.uikit.common.activity.UI;
import com.xszj.mba.R;
import com.xszj.mba.adapter.SelcectPagerAdapter;
import com.xszj.mba.fragment.SysMessageFragment;
import com.xszj.mba.utils.SharedPreferencesUtils;
import com.xszj.mba.view.GlobalTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29.
 */

public class SysPushMessageActivity extends UI {
    private Context mContext;
    private GlobalTitleView titleView;
    private RadioGroup radiogroup;
    private ViewPager viewpager;
    private List<Fragment> framentList;
    private int unreadNum = 0;
    private RadioButton rb2;

    protected SysMessageFragment sysMessageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = SysPushMessageActivity.this;
        setContentView(R.layout.activity_sys_push_message);

        bindViews();
        initViews();
        initData();
        initListeners();
    }

    private void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        viewpager = (ViewPager) findViewById(R.id.message_tab_pager);
        rb2 = (RadioButton) findViewById(R.id.rb2);
    }

    private void initViews() {
        titleView.setTitle("消息中心");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);

        titleView.setMoreIconImage(R.mipmap.icon_delete_nomal);
        titleView.setMoreBtnVisible(true);

        framentList = new ArrayList<>();
        sysMessageFragment = new SysMessageFragment();
        framentList.add(sysMessageFragment);
        framentList.add(new SessionListFragment());

        SelcectPagerAdapter fragmentAdapter = new SelcectPagerAdapter(getSupportFragmentManager(),
                framentList);
        viewpager.setAdapter(fragmentAdapter);
        viewpager.setOffscreenPageLimit(2);

    }

    private void initData() {
        unreadNum = SharedPreferencesUtils.getIntValue(mContext,"unreadNum");
        if (unreadNum!=0){
            Spanned text = Html.fromHtml("咨询提问："+"<font color=#ff0000><b>" +unreadNum+"</b></font>");
            rb2.setText(text);
        }

    }

    private void initListeners() {

        titleView.setRightMoreBtnOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sysMessageFragment.delAllMsg();
            }
        });

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                RadioButton rb = (RadioButton) radiogroup.getChildAt(arg0);
                rb.setChecked(true);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb1:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.rb2:
                        viewpager.setCurrentItem(1);
                        rb2.setText("咨询提问");
                        SharedPreferencesUtils.setIntValue(mContext,"unreadNum",0);
                        break;
                    default:
                        break;
                }
            }
        });

    }
}

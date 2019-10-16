package com.xszj.mba.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.net.nim.demo.NimApplication;
import com.xszj.mba.R;
import com.xszj.mba.adapter.SelcectPagerAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.fragment.EvaluateTeacherHotFragment;
import com.xszj.mba.fragment.EvaluateTeacherNewFragment;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.view.GlobalTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 * 对老师评价的详情页
 */

public class EvaluateDetailActivity extends BaseActivity {

    private Context mContext;
    private GlobalTitleView titleView;
    private RadioGroup radiogroup;
    private ViewPager viewpager;
    private List<Fragment> framentList;
    protected List<String> list = new ArrayList<>();
    private String expertUuId;

    @Override
    protected int getContentViewResId() {
        mContext = EvaluateDetailActivity.this;
        return R.layout.activity_evaluate_detail;
    }


    @Override
    protected void getIntentDate() {
        super.getIntentDate();

        expertUuId = getIntent().getStringExtra("expertUuId");

        getIntent().putExtra("expertUuId", expertUuId);
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);

        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        viewpager = (ViewPager) findViewById(R.id.message_tab_pager);

    }

    @Override
    protected void initViews() {

        titleView.setTitle("评价详情");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);

        titleView.setMoreIconImage(R.mipmap.icon_evluate_teacher);
        titleView.setMoreBtnVisible(true);

    }

    @Override
    protected void getDateForService() {
        super.getDateForService();

        framentList = new ArrayList<>();

        framentList.add(new EvaluateTeacherHotFragment());
        framentList.add(new EvaluateTeacherNewFragment());

        SelcectPagerAdapter fragmentAdapter = new SelcectPagerAdapter(getSupportFragmentManager(),
                framentList);
        viewpager.setAdapter(fragmentAdapter);
        viewpager.setOffscreenPageLimit(2);

    }

    @Override
    protected void initListeners() {
        titleView.setRightMoreBtnOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    startActivity(new Intent(mContext,EvaluteTeacherActivity.class).putExtra("expertUuId",expertUuId));
                }
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
                        break;
                    default:
                        break;
                }
            }
        });

    }


}

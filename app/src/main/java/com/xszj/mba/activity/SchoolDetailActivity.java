package com.xszj.mba.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xszj.mba.R;
import com.xszj.mba.adapter.SelcectPagerAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.fragment.AboutSchTeachFragment;
import com.xszj.mba.fragment.AboutSchoolFragment;
import com.xszj.mba.fragment.AboutSchoolNewsFragment;
import com.xszj.mba.utils.TypeUtils;
import com.xszj.mba.view.GlobalTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ybx on 2017/1/6.
 */

public class SchoolDetailActivity extends BaseActivity {
    protected GlobalTitleView titleView;

    private RadioGroup radiogroup;
    private ViewPager viewpager;
    private List<Fragment> framentList;
    protected String title = "";
    protected String academyId = "";


    @Override
    protected int getContentViewResId() {
        return R.layout.activiy_school_detail;
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        title = getIntent().getStringExtra("titile");
        academyId = getIntent().getStringExtra("academyId");
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        titleView = (GlobalTitleView) findViewById(R.id.titleView);
    }

    @Override
    protected void bindViews() {
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    protected void initViews() {
        titleView.setTitle(title);
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);

        framentList = new ArrayList<>();

        AboutSchoolFragment fragment = new AboutSchoolFragment();
        Bundle bundle = new Bundle();
        bundle.putString("academyId", academyId);
        fragment.setArguments(bundle);
        framentList.add(fragment);

        AboutSchoolNewsFragment fragment0 = new AboutSchoolNewsFragment();
        Bundle bundle0 = new Bundle();
        bundle0.putInt("findtype", TypeUtils.SCHOOL_D_NEWS);
        bundle0.putString("academyId", academyId);
        fragment0.setArguments(bundle0);
        framentList.add(fragment0);

        AboutSchTeachFragment fragment1 = new AboutSchTeachFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("findtype", TypeUtils.SCHOOL_D_TEACHER);
        bundle1.putString("academyId", academyId);
        fragment1.setArguments(bundle1);
        framentList.add(fragment1);

        AboutSchTeachFragment fragment2 = new AboutSchTeachFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("findtype", TypeUtils.SCHOOL_D_UPPERCLASSMAN);
        bundle2.putString("academyId", academyId);
        fragment2.setArguments(bundle2);
        framentList.add(fragment2);


        SelcectPagerAdapter fragmentAdapter = new SelcectPagerAdapter(getSupportFragmentManager(),
                framentList);
        viewpager.setAdapter(fragmentAdapter);
        viewpager.setOffscreenPageLimit(3);
    }

    @Override
    protected void initListeners() {
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                RadioButton radioBtn = (RadioButton) radiogroup.getChildAt(arg0 * 2 + 1);
                radioBtn.setChecked(true);
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
                    case R.id.xinwen_rb1:
                        viewpager.setCurrentItem(0);
                        break;
                    case R.id.xinwen_rb2:
                        viewpager.setCurrentItem(1);
                        break;
                    case R.id.xinwen_rb3:
                        viewpager.setCurrentItem(2);
                        break;
                    case R.id.xinwen_rb4:
                        viewpager.setCurrentItem(3);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case 1:
                break;
            default:
                break;
        }
    }
}

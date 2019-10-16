package com.xszj.mba.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xszj.mba.R;
import com.xszj.mba.adapter.SelcectPagerAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.utils.TypeUtils;
import com.xszj.mba.view.GlobalTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ybx on 2016/12/9.
 */

public class FindFragment extends BaseFragment {

    protected GlobalTitleView titleView;

    private RadioGroup radiogroup;
    private ViewPager viewpager;
    private List<Fragment> framentList;

    private NearTermFragment fragment0;
    private NearTermFragment fragment1;
    private NearTermFragment fragment2;
    private NearTermFragment fragment3;

    //广播
    private BroadcastReceiver connectionReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("findCollectNews")) {
                    int type = intent.getIntExtra("findtype", 0);
                    int position = intent.getIntExtra("newsPosition", 0);

                    switch (type) {
                        case TypeUtils.FIND_NEAR_TERM:
                            fragment0.changeCollectType(position);
                            break;
                        case TypeUtils.FIND_APPLICATION_MATERIAL:
                            fragment1.changeCollectType(position);
                            break;
                        case TypeUtils.FIND_INTERVIEW:
                            fragment2.changeCollectType(position);
                            break;
                        case TypeUtils.FIND_WRITTEN_EXAMINATION:
                            fragment3.changeCollectType(position);
                            break;
                        default:
                            break;
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("findCollectNews");
        getActivity().registerReceiver(connectionReceiver, intentFilter);
    }

    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, null);
    }

    @Override
    protected void initTitle(View view) {
        super.initTitle(view);
        titleView = (GlobalTitleView) view.findViewById(R.id.titleView);
    }

    @Override
    protected void bindViews(View view) {
        radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
    }

    @Override
    protected void initViews() {
        titleView.setTitle("资讯");


        framentList = new ArrayList<>();

        fragment0 = new NearTermFragment();
        Bundle bundle0 = new Bundle();
        bundle0.putInt("findtype", TypeUtils.FIND_NEAR_TERM);
        fragment0.setArguments(bundle0);
        framentList.add(fragment0);


        fragment1 = new NearTermFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("findtype", TypeUtils.FIND_APPLICATION_MATERIAL);
        fragment1.setArguments(bundle1);
        framentList.add(fragment1);


        fragment2 = new NearTermFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("findtype", TypeUtils.FIND_INTERVIEW);
        fragment2.setArguments(bundle2);
        framentList.add(fragment2);


        fragment3 = new NearTermFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("findtype", TypeUtils.FIND_WRITTEN_EXAMINATION);
        fragment3.setArguments(bundle3);
        framentList.add(fragment3);

        //fragment adapter
        SelcectPagerAdapter fragmentAdapter = new SelcectPagerAdapter(getChildFragmentManager(),
                framentList);
        viewpager.setAdapter(fragmentAdapter);
        viewpager.setOffscreenPageLimit(3);

    }


    @Override
    public void onResume() {
        super.onResume();
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
//                //选中的RadioButton播放动画
//                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                sAnim.setDuration(500);
//                sAnim.setFillAfter(true);
//                //遍历所有的RadioButton
//                for (int i = 0; i < group.getChildCount(); i++) {
//                    RadioButton radioBtn = (RadioButton) group.getChildAt(i);
//                    if (radioBtn.isChecked()) {
//                        radioBtn.startAnimation(sAnim);
//                    } else {
//                        radioBtn.clearAnimation();
//                    }
//                }
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

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != connectionReceiver && null != getActivity()) {
            getActivity().unregisterReceiver(connectionReceiver);
        }
    }
}

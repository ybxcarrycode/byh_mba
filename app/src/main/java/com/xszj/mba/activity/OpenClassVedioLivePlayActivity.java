package com.xszj.mba.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.net.nim.demo.NimApplication;
import com.net.nim.demo.session.SessionHelper;
import com.xszj.mba.R;
import com.xszj.mba.adapter.ZhiboDMAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.fragment.Loadable;
import com.xszj.mba.fragment.TeacherDetailFragment;
import com.xszj.mba.fragment.TestFragment;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.ShareUtils;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.SimpleViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2016/12/13.
 * swh
 */

public class OpenClassVedioLivePlayActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private int currentPosition = 0;
    private SectionsPagerAdapter mPagerAdapter;
    private String[] mTitles = new String[]{"授课老师", "互动"};
    private SimpleViewPagerIndicator mIndicator;
    private GlobalTitleView titleView;
    private EditText editview;
    private Button btn_send;
    private RelativeLayout rel_hudong_msg;
    private ListView listview_msg;
    private ZhiboDMAdapter msgAdapter;
    private LinearLayout ll_class_bottom_follow_teacher;
    private List<String> listMsg = new ArrayList<>();
    private Context mContext;
    private TextView tv_following;
    private Button btn_talk;
    private String liveVideoId;
    @Override
    protected int getContentViewResId() {
        mContext = OpenClassVedioLivePlayActivity.this;
        return R.layout.activity_openclass_live_layout;
    }


    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        liveVideoId = getIntent().getStringExtra("liveVideoId");
        Log.e("ddddd",liveVideoId+"ddd");
        getIntent().putExtra("liveVideoId", liveVideoId);
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView)findViewById(R.id.globalTitleView);

        rel_hudong_msg = (RelativeLayout) findViewById(R.id.rel_hudong_msg);
        ll_class_bottom_follow_teacher = (LinearLayout) findViewById(R.id.ll_class_bottom_follow_teacher);
        editview = (EditText) findViewById(R.id.editview);
        btn_send = (Button) findViewById(R.id.btn_send);
        listview_msg = (ListView) findViewById(R.id.listview_msg);

        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        mIndicator.setViewpager(mViewPager);

        tv_following = (TextView) findViewById(R.id.tv_following);
        btn_talk = (Button) findViewById(R.id.btn_talk);

    }

    private void changeToLandscape() {
        titleView.setVisibility(View.GONE);
    }

    private void changeToPortrait() {
        titleView.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initViews() {
        titleView.setTitle("直播课程");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);


        titleView.setMoreIconImage(R.drawable.btn_share);
        titleView.setMoreBtnVisible(true);
        titleView.setRightMoreBtnOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    ShareUtils.share(mContext,"","","");
                }
            }
        });

        msgAdapter = new ZhiboDMAdapter(mContext, listMsg);
        listview_msg.setAdapter(msgAdapter);

        mPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(currentPosition);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void getDateForService() {
        super.getDateForService();
        mIndicator.setTitles(mTitles);
    }

    @Override
    protected void initListeners() {
        btn_send.setOnClickListener(this);
        tv_following.setOnClickListener(this);
        btn_talk.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            //发送弹幕信息
            case R.id.btn_send:

                break;

            case R.id.tv_following:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    //请求网络
                }
                break;

            case R.id.btn_talk:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    SessionHelper.startP2PSession(mContext, "ysx1480408356164test0003");
                }
                break;

            default:
                break;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        public Fragment getItem(int position) {
            Loadable fpage = null;
            Bundle bundle = new Bundle();
            bundle.putString("liveVideoId", liveVideoId);
            switch (position) {
                case 0:
                    fpage = (Loadable) Fragment.instantiate(context, TeacherDetailFragment.class.getName(), bundle);
                    break;
                case 1:
                    fpage = (Loadable) Fragment.instantiate(context, TestFragment.class.getName(), bundle);
                    break;
            }
            return (Fragment) fpage;
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    public void refreshPageData() {
        int position = mViewPager.getCurrentItem();
        Loadable apf = (Loadable) mPagerAdapter.instantiateItem(mViewPager, position);
        apf.onLoad(null, 2);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        mIndicator.scroll(arg0, arg1);
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rel_hudong_msg.setVisibility(View.GONE);
                ll_class_bottom_follow_teacher.setVisibility(View.VISIBLE);
                refreshPageData();
                break;
            case 1:
                rel_hudong_msg.setVisibility(View.VISIBLE);
                ll_class_bottom_follow_teacher.setVisibility(View.GONE);
                refreshPageData();
                break;
            default:
                break;
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

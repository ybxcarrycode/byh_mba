package com.xszj.mba.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.easefun.polyvsdk.demo.MediaController;
import com.easefun.polyvsdk.demo.VideoViewContainer;
import com.easefun.polyvsdk.ijk.IjkVideoView;
import com.easefun.polyvsdk.util.ScreenTool;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.net.nim.demo.session.SessionHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.bean.CourseDetailBean;
import com.xszj.mba.fragment.CourseDetailFragment;
import com.xszj.mba.fragment.Loadable;
import com.xszj.mba.fragment.TeacherDetailFragment;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.ShareUtils;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.SimpleViewPagerIndicator;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2016/12/13.
 */

public class RecommendVedioPlayActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private int currentPosition = 0;
    private SectionsPagerAdapter mPagerAdapter;
    private String[] mTitles = new String[]{"课程", "授课老师"};
    private SimpleViewPagerIndicator mIndicator;
    private GlobalTitleView titleView;
    private TextView tv_following;
    private ImageView iv_course;
    private Button btn_talk;
    private Context mContext;
    int w = 0, h = 0, adjusted_h = 0;
    private IjkVideoView videoView = null;
    private MediaController mediaController = null;
    ProgressBar progressBar;
    private String courseId;
    // videoview的容器
    private VideoViewContainer rl = null;
    public static CourseDetailBean courseDetailBean;
    private CourseDetailBean.DataBean.TeacherInfoBean teacherInfoBean;
    private BroadcastReceiver broadcastReceiver;
    private boolean isFollow = true;
    private String chapterId = "0";
    private String chapterSectionId = "0";
    private String text = "名师大咖视频直播，精品课程免费学习，博雅汇APP，您身边的备考专家！";
    private String title = "";

    @Override
    protected int getContentViewResId() {
        mContext = RecommendVedioPlayActivity.this;
        int[] wh = ScreenTool.getNormalWH(this);
        w = wh[0];
        h = wh[1];
        // 小窗口的比例
        float ratio = (float) 16 / 9;
        adjusted_h = (int) Math.ceil((float) w / ratio);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                rl.setVisibility(View.VISIBLE);
                iv_course.setVisibility(View.GONE);
                String vid = intent.getStringExtra("vedioVid");
                String vedioTitle = intent.getStringExtra("vedioTitle");
                Log.e("dddddd",vid+"/"+vedioTitle);
                setMyMediaController(vid,vedioTitle);
                videoView.setVid(vid,1);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("playVid");
        context.registerReceiver(broadcastReceiver, intentFilter);
        return R.layout.activity_recommend_void_layout;
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        courseId = intent.getStringExtra("courseId");
        chapterId = intent.getStringExtra("chapterId");
        chapterSectionId = intent.getStringExtra("chapterSectionId");
        if (chapterId==null || chapterSectionId==null ||chapterId.equals("") || chapterSectionId.equals("")){
            chapterId="0"; chapterSectionId="0";
        }
        intent.putExtra("courseId",courseId);
        intent.putExtra("chapterId",chapterId);
        intent.putExtra("chapterSectionId",chapterSectionId);
    }

    @Override
    protected void bindViews() {
        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        mIndicator.setViewpager(mViewPager);
        tv_following = (TextView) findViewById(R.id.tv_following);
        btn_talk = (Button) findViewById(R.id.btn_talk);

        rl = (VideoViewContainer) findViewById(R.id.rl);
        rl.setVisibility(View.GONE);
        rl.setLayoutParams(new LinearLayout.LayoutParams(w, adjusted_h));
        videoView = (IjkVideoView) findViewById(R.id.videoview);
        progressBar = (ProgressBar) findViewById(R.id.loadingprogress);

        iv_course =(ImageView)findViewById(R.id.iv_course);

    }

    @Override
    protected void initViews() {
        titleView = (GlobalTitleView)findViewById(R.id.globalTitleView);
        titleView.setTitle("课程详情");
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
                    ShareUtils.share(mContext,"http://mobile.byhmba.com/v1/news/goto/share.htm",title,text);
                }
            }
        });

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(currentPosition);
        mViewPager.setOnPageChangeListener(this);

        // 在缓冲时出现的loading
        videoView.setMediaBufferingIndicator(progressBar);
        videoView.setOpenTeaser(true);
        videoView.setOpenAd(true);
        videoView.setOpenQuestion(true);
        videoView.setOpenSRT(true);
        videoView.setNeedGestureDetector(true);
        videoView.setVideoLayout(IjkVideoView.VIDEO_LAYOUT_SCALE);
        videoView.setAutoContinue(true);

    }

    private void setMyMediaController(String vid,String vedioTitle) {
        videoView.setClick(new IjkVideoView.Click() {

            @Override
            public void callback(boolean start, boolean end) {
                mediaController.toggleVisiblity();
            }
        });

        mediaController = new MediaController(this, false,vid,vedioTitle,true);
        mediaController.setIjkVideoView(videoView);
        mediaController.setAnchorView(videoView);
        mediaController.setInstantSeeking(false);
        videoView.setMediaController(mediaController);


        // 设置切屏事件
        mediaController.setOnBoardChangeListener(new MediaController.OnBoardChangeListener() {

            @Override
            public void onPortrait() {
                changeToLandscape();
            }

            @Override
            public void onLandscape() {
                changeToPortrait();
            }
        });
    }


    private void changeToLandscape() {
        titleView.setVisibility(View.GONE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 由于切换到横屏获取到的宽高可能和竖屏的不一样，所以需要重新获取宽高
        int[] wh = ScreenTool.getNormalWH(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(wh[0], wh[1]);
        rl.setLayoutParams(p);
    }

    private void changeToPortrait() {
        titleView.setVisibility(View.VISIBLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, adjusted_h);
        rl.setLayoutParams(p);
    }



    @Override
    protected void getDateForService() {
        super.getDateForService();
        mIndicator.setTitles(mTitles);
        getDataFromServer();
    }

    private void getDataFromServer() {
        String url =  ServiceUtils.SERVICE_ABOUT_HOME+"/v1/course/courseSection.json?userId="+ NimApplication.user+"&courseId="+courseId;
        Log.e("ddddd",url);
        showProgressDialog();
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result  = responseInfo.result;
                dismissProgressDialog();
                if ("".equals(result)) {
                    return;
                }

                courseDetailBean= JsonUtil.parseJsonToBean(result, CourseDetailBean.class);

                if (null==courseDetailBean){
                    return;
                }

                if (courseDetailBean.getReturnCode().equals("0")){

                    mPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                    mViewPager.setAdapter(mPagerAdapter);

                    teacherInfoBean = courseDetailBean.getData().getTeacherInfo();
                    if (courseDetailBean.getData().getIsfocus().equals("1")){
                        tv_following.setText("已关注");
                        isFollow=true;
                        Drawable drawable = getResources().getDrawable(R.mipmap.follow_yes);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tv_following.setCompoundDrawables(null, null, drawable, null);//icon_tongji_teacher
                        tv_following.setCompoundDrawablePadding(5); //设置图片和text之间的间距
                    }else{
                        tv_following.setText("关注");
                        isFollow=false;
                        Drawable drawable = getResources().getDrawable(R.mipmap.follow_no);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tv_following.setCompoundDrawables(null, null, drawable, null);//icon_tongji_teacher
                        tv_following.setCompoundDrawablePadding(5); //设置图片和text之间的间距
                        tv_following.setOnClickListener(RecommendVedioPlayActivity.this);
                    }

                    ImageLoader.getInstance().displayImage(courseDetailBean.getData().getCourseCover(),iv_course,NimApplication.imageOptions);
                    //videoView.setVid(teacherInfoBean.getLiveVideoId(),1);
                    //videoView.setVid("eaceb0a5ea6f2737f35e2ec80d89c8c7_e",1);

                }else {
                    Toast.makeText(mContext, courseDetailBean.getReturnMsg() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("ddddd",s);
            }
        });

    }

    @Override
    protected void initListeners() {
        tv_following.setOnClickListener(this);
        btn_talk.setOnClickListener(this);
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
            switch (position) {
                case 0:
                    fpage = (Loadable) Fragment.instantiate(context, CourseDetailFragment.class.getName(), bundle);
                    break;
                case 1:
                    fpage = (Loadable) Fragment.instantiate(context, TeacherDetailFragment.class.getName(), bundle);
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
            case 1:
                refreshPageData();
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_following:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    if (null!=teacherInfoBean){
                        //请求网络
                        likeToServer(isFollow);
                    }
                }
                break;

            case R.id.btn_talk:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    if (null!=teacherInfoBean){
                        SessionHelper.startP2PSession(mContext,teacherInfoBean.getImUser());
                    }
                }
                break;

            default:
                break;
        }
    }

    // 配置文件设置congfigchange 切屏调用一次该方法，hide()之后再次show才会出现在正确位置
    @Override
    public void onConfigurationChanged(Configuration arg0) {
        super.onConfigurationChanged(arg0);
        videoView.setVideoLayout(IjkVideoView.VIDEO_LAYOUT_SCALE);
        mediaController.hide();
        // 隐藏或显示状态栏
        ScreenTool.reSetStatusBar(this);

    }


    // 请求网络 关注
    public void likeToServer(final boolean type) {
        String url;
        if (type){
            url= ServiceUtils.SERVICE_ABOUT_HOME + "/v1/consult/cancleFocusExpert.json?";
        }else{
            url= ServiceUtils.SERVICE_ABOUT_HOME + "/v1/consult/focusExpert.json?";
        }

        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("expertId", teacherInfoBean.getUserId());

        new HttpUtils().send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("dddddd", responseInfo.result);

                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    String returnCode = object.optString("returnCode", null);
                    Drawable drawable ;
                    if (returnCode.equals("0")) {
                        if (type){
                            isFollow=false;
                            tv_following.setText("关注");
                            drawable = getResources().getDrawable(R.mipmap.follow_no);
                        }else{
                            isFollow=true;
                            tv_following.setText("已关注");
                            drawable = getResources().getDrawable(R.mipmap.follow_yes);
                        }
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tv_following.setCompoundDrawables(null, null, drawable, null);//icon_tongji_teacher
                        tv_following.setCompoundDrawablePadding(5); //设置图片和text之间的间距
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (videoView != null && videoView.isPlayState()) {
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.destroy();
        }
        if (broadcastReceiver!=null){
            unregisterReceiver(broadcastReceiver);
        }
    }

}

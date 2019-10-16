package com.xszj.mba.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.admaster.sdk.api.AdmasterSdk;
import com.easefun.polyvsdk.live.PolyvLiveSDKUtil;
import com.easefun.polyvsdk.live.fragment.PolyvChatFragment;
import com.easefun.polyvsdk.live.fragment.PolyvDanmuFragment;
import com.easefun.polyvsdk.live.player.PolyvPlayerAuxiliaryView;
import com.easefun.polyvsdk.live.player.PolyvPlayerLightView;
import com.easefun.polyvsdk.live.player.PolyvPlayerMediaController;
import com.easefun.polyvsdk.live.player.PolyvPlayerVolumeView;
import com.easefun.polyvsdk.live.util.AdmasterSdkUtils;
import com.easefun.polyvsdk.live.util.PolyvScreenUtils;
import com.easefun.polyvsdk.live.video.PolyvLivePlayErrorReason;
import com.easefun.polyvsdk.live.video.PolyvLiveVideoView;
import com.easefun.polyvsdk.live.video.PolyvLiveVideoViewListener;
import com.easefun.polyvsdk.live.video.auxiliary.PolyvLiveAuxiliaryVideoView;
import com.easefun.polyvsdk.live.vo.PolyvLiveChannelVO;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.net.nim.demo.session.SessionHelper;
import com.xszj.mba.R;
import com.xszj.mba.bean.TeacherDetailFeagmentBean;
import com.xszj.mba.fragment.TeacherDetailLivingFragment;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.ShareUtils;
import com.xszj.mba.view.GlobalTitleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 播放器 activity，视频显示主类，{@link PolyvLiveVideoView} 演示主类
 */
public class OpenClassLivingActivity extends FragmentActivity implements View.OnClickListener{
    private static final String TAG = OpenClassLivingActivity.class.getSimpleName();
    private PolyvChatFragment polyvChatFragment;
    private PolyvDanmuFragment danmuFragment;
    private String uid, cid;
    private RelativeLayout viewLayout = null;
    /** 播放器主类 */
    private PolyvLiveVideoView videoView = null;
    /** 播放器控制栏 */
    private PolyvPlayerMediaController mediaController = null;
    /** 辅助播放器类，用于播放视频片头广告 */
    private PolyvLiveAuxiliaryVideoView auxiliaryVideoView = null;
    /** 辅助显示类，用于显示图片广告 */
    private PolyvPlayerAuxiliaryView auxiliaryView = null;
    /** 手势亮度指示标志 */
    private PolyvPlayerLightView lightView = null;
    /** 手势音量指示标志 */
    private PolyvPlayerVolumeView volumeView = null;
    /** 用于显示广告倒计时 */
    private TextView advertCountDown = null;
    private boolean isPlay = false;
    private GlobalTitleView titleView;
    private Button btn_teacher,btn_talk,btn_talk_to;
    private TextView tv_following;
    private String liveVideoId;
    private LinearLayout ll_bottom;
    public static TeacherDetailFeagmentBean teacherDetailFeagmentBean;
    private TeacherDetailFeagmentBean.DataBean.LiveVideoDetailBean liveVideoDetailBean;
    private String memberId;
    private boolean isFollow = true;
    private String text = "名师大咖视频直播，精品课程免费学习，博雅汇APP，您身边的备考专家！";
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openclass_living_player);

        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }

        uid = getIntent().getStringExtra("uid");
        cid = getIntent().getStringExtra("cid");

        liveVideoId = getIntent().getStringExtra("liveVideoId");
        title = getIntent().getStringExtra("title");
        Log.e("ddddd",liveVideoId+"ddd"+cid);
        getIntent().putExtra("liveVideoId", liveVideoId);

        // 初始化广告监测器
        AdmasterSdk.init(this, "");

        if(NimApplication.user==null || NimApplication.user.equals("")){
            memberId="-1";
        }else {
            memberId = NimApplication.user;
        }

        addFragment();
        findIdAndNew();
        initView();
        getDataFromService();
    }

    private void getDataFromService() {

        String url =  ServiceUtils.SERVICE_ABOUT_HOME+"/v1/course/liveCourseDetail.json?userId="+ memberId+"&liveVideoId="+liveVideoId;
        Log.e("ddddd",url);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result  = responseInfo.result;
                Log.e("ddddd",result);
                if ("".equals(result)) {
                    return;
                }

                teacherDetailFeagmentBean= JsonUtil.parseJsonToBean(result, TeacherDetailFeagmentBean.class);

                if (null==teacherDetailFeagmentBean){
                    return;
                }

                if (teacherDetailFeagmentBean.getReturnCode().equals("0")){

                    liveVideoDetailBean = teacherDetailFeagmentBean.getData().getLiveVideoDetail();
                    if (teacherDetailFeagmentBean.getData().getIsfocus().equals("1")){
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
                    }
                    //videoView.setVid(liveVideoDetailBean.getLiveVideoId(),1);


                }else {
                    Toast.makeText(OpenClassLivingActivity.this, teacherDetailFeagmentBean.getReturnMsg() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("ddddd",s);
            }
        });

    }

    private void addFragment() {
        polyvChatFragment = new PolyvChatFragment();
        // 初始化聊天室的配置
        polyvChatFragment.initChatConfig(uid, cid, "游客");
        danmuFragment = new PolyvDanmuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_chat, polyvChatFragment, "chatFragment")
                .add(R.id.fl_danmu, danmuFragment, "danmuFragment").commit();
    }

    private void findIdAndNew() {
        //xin加
        titleView = (GlobalTitleView)findViewById(R.id.globalTitleView);
        btn_teacher = (Button) findViewById(R.id.btn_teacher);
        btn_talk = (Button) findViewById(R.id.btn_talk);
        btn_teacher.setOnClickListener(this);
        btn_talk.setOnClickListener(this);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tv_following = (TextView) findViewById(R.id.tv_following);
        btn_talk_to = (Button) findViewById(R.id.btn_talk_to);
        tv_following.setOnClickListener(this);
        btn_talk_to.setOnClickListener(this);

        viewLayout = (RelativeLayout) findViewById(R.id.view_layout);
        videoView = (PolyvLiveVideoView) findViewById(R.id.polyv_live_video_view);
        mediaController = (PolyvPlayerMediaController) findViewById(R.id.polyv_player_media_controller);
        ProgressBar loadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
        ImageView noStream = (ImageView) findViewById(R.id.no_stream);
        auxiliaryVideoView = (PolyvLiveAuxiliaryVideoView) findViewById(R.id.polyv_live_auxiliary_video_view);
        ProgressBar auxiliaryLoadingProgress = (ProgressBar) findViewById(R.id.auxiliary_loading_progress);
        auxiliaryView = (PolyvPlayerAuxiliaryView) findViewById(R.id.polyv_player_auxiliary_view);
        lightView = (PolyvPlayerLightView) findViewById(R.id.polyv_player_light_view);
        volumeView = (PolyvPlayerVolumeView) findViewById(R.id.polyv_player_volume_view);
        advertCountDown = (TextView) findViewById(R.id.count_down);

        mediaController.initConfig(viewLayout);
        mediaController.setDanmuFragment(danmuFragment);
        videoView.setMediaController(mediaController);
        videoView.setAuxiliaryVideoView(auxiliaryVideoView);
        videoView.setPlayerBufferingIndicator(loadingProgress);
        videoView.setNoStreamIndicator(noStream);
        auxiliaryVideoView.setPlayerBufferingIndicator(auxiliaryLoadingProgress);
        auxiliaryView.setPolyvLiveVideoView(videoView);
    }

    private void initView() {
        titleView.setTitle("直播课程");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);

        titleView.setMoreIconImage(R.drawable.btn_share);
        titleView.setMoreBtnVisible(true);
        titleView.setRightMoreBtnOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(OpenClassLivingActivity.this);
                } else {
                    ShareUtils.share(OpenClassLivingActivity.this,"http://mobile.byhmba.com/v1/news/goto/share.htm",title,text);
                }
            }
        });
        videoView.setOpenAd(true);
        videoView.setOpenPreload(true, 2);
        videoView.setNeedGestureDetector(true);

        videoView.setOnPreparedListener(new PolyvLiveVideoViewListener.OnPreparedListener() {
            @Override
            public void onPrepared() {

            }
        });

        videoView.setOnInfoListener(new PolyvLiveVideoViewListener.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {

            }
        });

        videoView.setOnVideoPlayErrorListener(new PolyvLiveVideoViewListener.OnVideoPlayErrorListener() {
            @Override
            public void onVideoPlayError(@NonNull PolyvLivePlayErrorReason errorReason) {
                switch (errorReason.getType()) {
                    case NETWORK_DENIED:
                        Toast.makeText(OpenClassLivingActivity.this, "无法连接网络，请连接网络后再试", Toast.LENGTH_SHORT).show();
                        break;

                    case START_ERROR:
                        Toast.makeText(OpenClassLivingActivity.this, "视频开始播放错误，请重新播放", Toast.LENGTH_SHORT).show();
                        break;

                    case CHANNEL_NULL:
                        Toast.makeText(OpenClassLivingActivity.this, "频道信息为空", Toast.LENGTH_SHORT).show();
                        break;

                    case LIVE_UID_NOT_EQUAL:
                        Toast.makeText(OpenClassLivingActivity.this, "userId与服务器userId不匹配，请重新设置", Toast.LENGTH_SHORT).show();
                        break;

                    case LIVE_CID_NOT_EQUAL:
                        Toast.makeText(OpenClassLivingActivity.this, "channelId与服务器channelId不匹配，请重新设置", Toast.LENGTH_SHORT).show();
                        break;

                    case LIVE_PLAY_ERROR:
                        Toast.makeText(OpenClassLivingActivity.this, "播放错误，请稍后重试", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        videoView.setOnAdvertisementOutListener(new PolyvLiveVideoViewListener.OnAdvertisementOutListener() {
            @Override
            public void onOut(@NonNull PolyvLiveChannelVO.ADMatter adMatter) {
                auxiliaryView.show(adMatter);
            }

            @Override
            public void onClick(@NonNull PolyvLiveChannelVO.ADMatter adMatter) {
                // 发送广告点击监测
                AdmasterSdkUtils.sendAdvertMonitor(adMatter, AdmasterSdkUtils.MONITOR_CLICK);
                if (!TextUtils.isEmpty(adMatter.getAddrUrl())) {
                    try {
                        new URL(adMatter.getAddrUrl());
                    } catch (MalformedURLException e1) {
                        Log.e(TAG, PolyvLiveSDKUtil.getExceptionFullMessage(e1, -1));
                        return;
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(adMatter.getAddrUrl()));
                    startActivity(intent);
                }
            }
        });

        videoView.setOnAdvertisementCountDownListener(new PolyvLiveVideoViewListener.OnAdvertisementCountDownListener() {
            @Override
            public void onCountDown(int num) {
                advertCountDown.setText(String.format("广告也精彩：%d秒", num));
                advertCountDown.setVisibility(View.VISIBLE);
            }

            @Override
            public void onEnd(@NonNull PolyvLiveChannelVO.ADMatter adMatter) {
                advertCountDown.setVisibility(View.GONE);
                auxiliaryView.hide();
                // 发送广告曝光监测
                AdmasterSdkUtils.sendAdvertMonitor(adMatter, AdmasterSdkUtils.MONITOR_SHOW);
            }
        });

        videoView.setOnGestureLeftUpListener(new PolyvLiveVideoViewListener.OnGestureLeftUpListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("LeftUp %b %b brightness %d", start, end, videoView.getBrightness()));
                int brightness = videoView.getBrightness() + 5;
                if (brightness > 100) {
                    brightness = 100;
                }

                videoView.setBrightness(brightness);
                lightView.setViewLightValue(brightness, end);
            }
        });

        videoView.setOnGestureLeftDownListener(new PolyvLiveVideoViewListener.OnGestureLeftDownListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("LeftDown %b %b brightness %d", start, end, videoView.getBrightness()));
                int brightness = videoView.getBrightness() - 5;
                if (brightness < 0) {
                    brightness = 0;
                }

                videoView.setBrightness(brightness);
                lightView.setViewLightValue(brightness, end);
            }
        });

        videoView.setOnGestureRightUpListener(new PolyvLiveVideoViewListener.OnGestureRightUpListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("RightUp %b %b volume %d", start, end, videoView.getVolume()));
                // 加减单位最小为10，否则无效果
                int volume = videoView.getVolume() + 10;
                if (volume > 100) {
                    volume = 100;
                }

                videoView.setVolume(volume);
                volumeView.setViewVolumeValue(volume, end);
            }
        });

        videoView.setOnGestureRightDownListener(new PolyvLiveVideoViewListener.OnGestureRightDownListener() {

            @Override
            public void callback(boolean start, boolean end) {
                Log.d(TAG, String.format("RightDown %b %b volume %d", start, end, videoView.getVolume()));
                // 加减单位最小为10，否则无效果
                int volume = videoView.getVolume() - 10;
                if (volume < 0) {
                    volume = 0;
                }

                videoView.setVolume(volume);
                volumeView.setViewVolumeValue(volume, end);
            }
        });

        videoView.setLivePlay(uid, cid, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //回来后继续播放
        if (isPlay) {
            videoView.onActivityResume();
            if (auxiliaryView.isPauseAdvert()) {
                auxiliaryView.hide();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (PolyvScreenUtils.isLandscape(this)) {
                mediaController.changeToPortrait();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //弹出去暂停
        isPlay = videoView.onActivityStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.destroy();
        auxiliaryView.hide();
        // 关闭广告监测器
        AdmasterSdk.terminateSDK();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_teacher:
                ll_bottom.setVisibility(View.VISIBLE);
                btn_teacher.setTextColor(0xff22CBDB);
                btn_talk.setTextColor(0xffA2A2A2);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_chat, new TeacherDetailLivingFragment(), "teacherFragment").commit();
                break;

            case R.id.btn_talk:
                ll_bottom.setVisibility(View.GONE);
                btn_teacher.setTextColor(0xffA2A2A2);
                btn_talk.setTextColor(0xff22CBDB);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_chat, polyvChatFragment, "chatFragment").commit();
                break;

            case R.id.tv_following:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(OpenClassLivingActivity.this);
                } else {
                    if (null!=liveVideoDetailBean){
                        //请求网络
                        likeToServer(isFollow);
                    }
                }
                break;

            case R.id.btn_talk_to:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(OpenClassLivingActivity.this);
                } else {
                    if (null!=liveVideoDetailBean){
                        SessionHelper.startP2PSession(OpenClassLivingActivity.this,liveVideoDetailBean.getImUser());
                    }
                }
                break;
        }
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
        params.addBodyParameter("expertId", liveVideoDetailBean.getUserId());

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
}

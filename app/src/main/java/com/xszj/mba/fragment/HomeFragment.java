package com.xszj.mba.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.net.nim.demo.main.helper.SystemMessageUnreadManager;
import com.net.nim.demo.main.reminder.ReminderItem;
import com.net.nim.demo.main.reminder.ReminderManager;
import com.net.nim.demo.session.SessionHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.activity.CourseListActivity;
import com.xszj.mba.activity.HomeWebViewActivity;
import com.xszj.mba.activity.MessageActivity;
import com.xszj.mba.activity.OpenClassLivingActivity;
import com.xszj.mba.activity.SchoolDetailActivity;
import com.xszj.mba.activity.SchoolListActivity;
import com.xszj.mba.activity.SysPushMessageActivity;
import com.xszj.mba.adapter.HomeInterviewAdapter;
import com.xszj.mba.adapter.HomeRecommendAdapter;
import com.xszj.mba.adapter.HomeSchoolAdapter;
import com.xszj.mba.adapter.OpenClassAdapter;
import com.xszj.mba.adapter.ViewPagerAdpter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.bean.HomeDataBean;
import com.xszj.mba.utils.ACache;
import com.xszj.mba.utils.AbDialogUtil;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.SharedPreferencesUtils;
import com.xszj.mba.utils.TypeUtilsString;
import com.xszj.mba.utils.XRGridPaceItemDecoration;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.NoScrollGridView;
import com.xszj.mba.view.NoScrollListView;
import com.xszj.mba.view.NormalEmptyView;
import com.xszj.mba.view.PrototypeImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */
public class HomeFragment extends BaseFragment implements ReminderManager.UnreadNumChangedCallback {
    // 动态
    private GlobalTitleView titleView;
    private NormalEmptyView empty = null;
    // 动态

    private ViewPager viewPager;
    private Context mContext;
    private int width;
    private LinearLayout headView;
    private XRecyclerView xrecyclerview;
    protected HomeRecommendAdapter adapter;
    protected List<HomeDataBean.DataBean.WrittenExaminationListBean> list = new ArrayList<>();
    protected List<HomeDataBean.DataBean.LiveListBean> liveList = new ArrayList<>();
    protected List<HomeDataBean.DataBean.LiveListBean> liveListless = new ArrayList<>();
    protected List<HomeDataBean.DataBean.LiveListBean> liveListmore = new ArrayList<>();
    protected List<HomeDataBean.DataBean.InterviewListBean> interviewList = new ArrayList<>();
    protected List<HomeDataBean.DataBean.AcademyListBean> academyList = new ArrayList<>();
    private NoScrollListView headListView;
    private NoScrollGridView headGridView, headschoolGridView;
    private TextView headTvSchoolMore,headSpecialIncrease;
    private ImageView headImage;
    private boolean isFirst = false;
    private List<HomeDataBean.DataBean.BannerListBean> models = new ArrayList<>();
    private ViewPagerAdpter viewPagerAdpter;
    private List<View> views = new ArrayList<View>();
    private RadioGroup mPointIndicator;
    private LayoutInflater minflater;
    private HomeDataBean homeDataBean;
    private String memberId;
    private ACache mCache = null;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                int nextIndex = (viewPager.getCurrentItem() + 1) % models.size();
                viewPager.setCurrentItem(nextIndex);
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(1, 3000);//2秒自动滑动
            }
        }
    };

    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mCache = ACache.get(mContext);
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        minflater = getActivity().getLayoutInflater();
        return inflater.inflate(R.layout.home_fragment_layout, null);
    }

    @Override
    protected void bindViews(View view) {
        empty = (NormalEmptyView) view.findViewById(R.id.fmobile_empty_view);
        titleView = (GlobalTitleView) view.findViewById(R.id.globalTitleView);
        xrecyclerview = (XRecyclerView) view.findViewById(R.id.home_fragment_xrecyclerview);

        //加载头部布局
        headView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment_head_view, null);
        viewPager = (ViewPager) headView.findViewById(R.id.viewpagerId);
        headListView = (NoScrollListView) headView.findViewById(R.id.home_fragment_head_listview);
        headTvSchoolMore = (TextView) headView.findViewById(R.id.tv_school_more);
        headschoolGridView = (NoScrollGridView) headView.findViewById(R.id.home_fragment_head_school_gridview);
        headGridView = (NoScrollGridView) headView.findViewById(R.id.home_fragment_head_gridview);
        headImage = (ImageView) headView.findViewById(R.id.home_openclass_open);
        headSpecialIncrease = (TextView) headView.findViewById(R.id.tv_special_increase);
        mPointIndicator = (RadioGroup) headView.findViewById(R.id.radio_group_indicator);

    }


    @Override
    protected void initViews() {

        titleView.setTitle("博雅汇MBA");
        titleView.setLeftDiyBtnIcon(R.drawable.icon_mail);
        titleView.setLeftDiyBtnVisible(true);

        titleView.setMoreIconImage(R.drawable.message_icon);
        titleView.setMoreBtnVisible(true);


        //设置mViewPager的宽度
        FrameLayout.LayoutParams iParams = (FrameLayout.LayoutParams) viewPager.getLayoutParams();
        iParams.height = (int) (width * 0.55);
        viewPager.setLayoutParams(iParams);

        //笔试课程
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(gridLayoutManager);
        xrecyclerview.setPullRefreshEnabled(false);
        xrecyclerview.setLoadingMoreEnabled(false);
        xrecyclerview.addItemDecoration(new XRGridPaceItemDecoration(2, 20, 20, true, true, 40));
        xrecyclerview.addHeaderView(headView);
        adapter = new HomeRecommendAdapter(list, getActivity());
        xrecyclerview.setAdapter(adapter);
    }

    @Override
    protected void initListeners() {
        headImage.setOnClickListener(this);
        headTvSchoolMore.setOnClickListener(this);
        titleView.setLeftDiyBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(getActivity());
                } else {
                    startActivity(new Intent(getActivity(), SysPushMessageActivity.class));
                }
            }
        });

        titleView.setRightMoreBtnOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null == NimApplication.user || NimApplication.user.equals("") || NIMClient.getStatus()!= StatusCode.LOGINED) {
                    CommonUtil.showLoginddl(getActivity());
                } else {
                    //startActivity(new Intent(getActivity(), MessageActivity.class));
                    SessionHelper.startP2PSession(mContext, "ysx1487845920440test0046");
                }
            }
        });

        //公开课程的点击事件
        headListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String liveVideoId;
                String liveCid;
                String title;
                if (isFirst) {
                    liveVideoId = liveListmore.get(position).getLiveVideoId();
                    liveCid = liveListmore.get(position).getMobileUrl();
                    title = liveListmore.get(position).getLiveName();
                } else {
                    liveVideoId = liveListless.get(position).getLiveVideoId();
                    liveCid = liveListless.get(position).getMobileUrl();
                    title = liveListless.get(position).getLiveName();
                }
                Intent intent = new Intent(mContext, OpenClassLivingActivity.class);
                intent.putExtra("liveVideoId", liveVideoId);
                intent.putExtra("uid", "a53584648b");
                intent.putExtra("title",title);
                intent.putExtra("cid",liveCid);
                mContext.startActivity(intent);
            }
        });
        //院校点击事件
        headschoolGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, SchoolDetailActivity.class);
                intent.putExtra("titile", academyList.get(position).getAcademyName());
                intent.putExtra("academyId", academyList.get(position).getAcademyId());
                mContext.startActivity(intent);
            }
        });

        //提前面试
        headGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, CourseListActivity.class);
                intent.putExtra("category", interviewList.get(position).getDictionaryId());
                mContext.startActivity(intent);
            }
        });

        //笔试课程的item点击事件
        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, CourseListActivity.class);
                intent.putExtra("category", list.get(position).getDictionaryId());
                mContext.startActivity(intent);
            }
        });

        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                xrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                xrecyclerview.loadMoreComplete();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    protected void getDateForService() {
        super.getDateForService();
        if (CommonUtil.isNetworkConnected(mContext)) {
            getDateService();
        } else {
            String result = mCache.getAsString(TypeUtilsString.CACHE_HOME_DATE_KEY);
            setDate(result);
        }
    }

    private void getDateService() {
        if (null == NimApplication.user || NimApplication.user.equals("")) {
            memberId = "";
        } else {
            memberId = NimApplication.user;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("memberId", memberId);

        Log.e("dssdADA", memberId + "==");

        String url1 = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/home/appHome.json?userId=" + memberId;
        Log.e("dssdADA", url1);

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, url1, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                AbDialogUtil.closeProcessDialog(dialogProgress);
                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }
                mCache.put(TypeUtilsString.CACHE_HOME_DATE_KEY, result, 3 * ACache.TIME_HOUR);
                setDate(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                AbDialogUtil.closeProcessDialog(dialogProgress);
            }
        });
    }

    private void setDate(String result) {
        homeDataBean = JsonUtil.parseJsonToBean(result, HomeDataBean.class);
        if (null != homeDataBean) {
            if ("0".equals(homeDataBean.getReturnCode())) {
                parse(homeDataBean.getData());
            } else {
                empty.setVisibility(View.VISIBLE);
                Toast.makeText(mContext, homeDataBean.getReturnMsg() + "", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }


    private void parse(HomeDataBean.DataBean data) {
        if (data != null) {
            //轮播图
            if (data.getBannerList() != null && data.getBannerList().size() > 0) {
                models = data.getBannerList();
                initViewPager(models);
            }
            //公开课直播
            if (data.getLiveList() != null && data.getLiveList().size() >= 2) {
                headImage.setVisibility(View.VISIBLE);
                headSpecialIncrease.setVisibility(View.VISIBLE);
                liveListless.clear();
                liveListmore.clear();
                liveList = data.getLiveList();
                liveListless.add(liveList.get(0));
                liveListless.add(liveList.get(1));
                OpenClassAdapter openClassAdapter = new OpenClassAdapter(mContext, liveListless);
                headListView.setAdapter(openClassAdapter);
            }else if (data.getLiveList() != null && data.getLiveList().size() == 1) {
                headSpecialIncrease.setVisibility(View.VISIBLE);
                headImage.setVisibility(View.GONE);
                liveListless.clear();
                liveListmore.clear();
                liveList = data.getLiveList();
                liveListless.add(liveList.get(0));
                OpenClassAdapter openClassAdapter = new OpenClassAdapter(mContext, liveListless);
                headListView.setAdapter(openClassAdapter);
            }else {
                headImage.setVisibility(View.GONE);
                headSpecialIncrease.setVisibility(View.GONE);
            }
            //学校
            if (data.getAcademyList() != null && data.getAcademyList().size() > 0) {
                academyList = data.getAcademyList();
                headschoolGridView.setAdapter(new HomeSchoolAdapter(mContext, academyList));
            }
            //提前面试
            if (data.getInterviewList() != null && data.getInterviewList().size() > 0) {
                interviewList = data.getInterviewList();
                HomeInterviewAdapter homeInterviewAdapter = new HomeInterviewAdapter(mContext, interviewList);
                headGridView.setAdapter(homeInterviewAdapter);
            }
            //笔试课程
            if (data.getWrittenExaminationList() != null && data.getWrittenExaminationList().size() > 0) {
                list.clear();
                adapter.addItemLast(data.getWrittenExaminationList());
                adapter.notifyDataSetChanged();
            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_openclass_open:
                if (liveList != null && liveList.size() > 0) {
                    if (isFirst) {
                        isFirst = false;
                        liveListless.clear();
                        liveListmore.clear();
                        liveListless.add(liveList.get(0));
                        liveListless.add(liveList.get(1));
                        OpenClassAdapter openClassAdapter = new OpenClassAdapter(mContext, liveListless);
                        headListView.setAdapter(openClassAdapter);
                        openClassAdapter.notifyDataSetChanged();
                        headImage.setImageResource(R.drawable.home_open_down);
                    } else {
                        isFirst = true;
                        liveListless.clear();
                        liveListmore.clear();
                        liveListmore.addAll(liveList);
                        OpenClassAdapter openClassAdapter = new OpenClassAdapter(mContext, liveListmore);

                        headListView.setAdapter(openClassAdapter);
                        openClassAdapter.notifyDataSetChanged();
                        headImage.setImageResource(R.drawable.home_open_up);
                    }
                } else {
                    showToast("暂无数据");
                }

                break;

            case R.id.tv_school_more:
                mContext.startActivity(new Intent(mContext, SchoolListActivity.class));
                break;

            default:
                break;
        }

    }

    //banner中viewpager设置
    private void initViewPager(final List<HomeDataBean.DataBean.BannerListBean> models) {  //初始化ViewPager
        if (models != null && models.size() > 0) {
            for (int i = 0; i < models.size(); i++) {
                if (getActivity() != null) {
                    View view = View.inflate(getActivity(), R.layout.layout_viewpager, null);
                    ImageView imageView = (ImageView) view.findViewById(R.id.viewpager_image);
                    TextView tv_vp_message = (TextView) view.findViewById(R.id.tv_vp_message);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    ImageLoader.getInstance().displayImage(models.get(i).getBannerCover(), imageView, NimApplication.imageOptions);
                    imageView.setLayoutParams(params);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mContext.startActivity(new Intent(mContext, HomeWebViewActivity.class)
                                    .putExtra("url", models.get(finalI).getLinkUrl())
                                    .putExtra("title","博雅汇MBA"));
                        }
                    });
                    views.add(view);
                }
            }
        }
        viewPagerAdpter = new ViewPagerAdpter(views, getActivity());
        viewPager.setAdapter(viewPagerAdpter);
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessage(1);
        }

        if (models != null) {
            setPagerIndicator(viewPager, models, mPointIndicator, minflater);
        }

    }


    //banner中点的设值
    private void setPagerIndicator(final ViewPager pager, final List<HomeDataBean.DataBean.BannerListBean> models, final RadioGroup group, final LayoutInflater minflater) {

        if (group.getChildCount() != 0) {
            group.removeAllViews();
        }

        for (int i = 0; i < models.size(); i++) {
            if (minflater == null) {
                return;
            }

            RadioButton btn = (RadioButton) minflater.inflate(R.layout.radio_btn_indicator, null);
            int dimenSize = 20;
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(dimenSize, dimenSize);
            btn.setId(i);
            btn.setChecked(false);
            params.setMargins(dimenSize / 2, dimenSize, dimenSize, dimenSize);
            group.addView(btn, params);
            if (btn.getId() == 0) {
                btn.setChecked(true);
            }

        }
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                group.check(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        registerMsgUnreadInfoObserver(true);
        registerSystemMessageObservers(true);
        requestSystemMessageUnreadCount();
    }


    /**
     * 注册未读消息数量观察者
     */
    private void registerMsgUnreadInfoObserver(boolean register) {
        if (register) {
            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
        } else {
            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
        }
    }

    /**
     * 未读消息数量观察者实现
     */
    @Override
    public void onUnreadNumChanged(ReminderItem item) {
        final int unreadNum = NIMClient.getService(MsgService.class).getTotalUnreadCount();
        Log.e("dddddd",unreadNum+"");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("dddddd",unreadNum+"");
                if (unreadNum > 0) {
                    titleView.setUnreadLabelVisible(true);
                    titleView.setUnreadLabelCount(unreadNum + "");
                    SharedPreferencesUtils.setIntValue(mContext,"unreadNum",unreadNum);
                } else {
                    titleView.setUnreadLabelVisible(false);
                    SharedPreferencesUtils.setIntValue(mContext,"unreadNum",0);
                }
            }
        });

    }

    /**
     * 注册/注销系统消息未读数变化
     *
     * @param register
     */
    private void registerSystemMessageObservers(boolean register) {
        NIMClient.getService(SystemMessageObserver.class).observeUnreadCountChange(sysMsgUnreadCountChangedObserver,
                register);
    }

    private Observer<Integer> sysMsgUnreadCountChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer unreadCount) {
            SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unreadCount);
            ReminderManager.getInstance().updateContactUnreadNum(unreadCount);
        }
    };

    /**
     * 查询系统消息未读数
     */
    private void requestSystemMessageUnreadCount() {
        int unread = NIMClient.getService(SystemMessageService.class).querySystemMessageUnreadCountBlock();
        SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unread);
        ReminderManager.getInstance().updateContactUnreadNum(unread);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}

package com.xszj.mba.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.xszj.mba.R;
import com.xszj.mba.activity.SttDeatilActivity;
import com.xszj.mba.adapter.ExperAadpter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.bean.AboutTechUpperBean;
import com.xszj.mba.bean.DlgDataPicker;
import com.xszj.mba.bean.ExperNewBean;
import com.xszj.mba.bean.SettingValue;
import com.xszj.mba.popup.PopupWindowManager;
import com.xszj.mba.utils.ACache;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.TypeUtilsString;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.NormalEmptyView;
import com.xszj.mba.view.PullToRefreshView;
import com.xszj.mba.view.PullToRefreshView.OnFooterRefreshListener;
import com.xszj.mba.view.PullToRefreshView.OnHeaderRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */
public class MentersFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    // 上拉更新
    private PullToRefreshView ptr = null;
    // 动态
    private ListView mExpertLv = null;
    private NormalEmptyView empty = null;

    // 省份
    private TextView mProvinceTv = null;
    private ImageView mProvinceIv = null;
    private LinearLayout mProvinceLl = null;
    private String provinceId = "";


    //导师分类
    private TextView menterTypeTv;
    private ImageView menterTypeIv;
    private LinearLayout menterLayout;
    private String menterTypeId = "";

    private int page = 1;
    private int pageCount;
    private ExperAadpter mAdapter;

    private ArrayList<DlgDataPicker> expertProvince;
    private ArrayList<DlgDataPicker> menterTypes;
    private GlobalTitleView titleView;
    private String respose;
    private String memberId;
    private ACache mCache = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_expertlist, container, false);
        mExpertLv = (ListView) view.findViewById(R.id.expertLv);
        mExpertLv.setOnItemClickListener(this);
        ptr = (PullToRefreshView) view.findViewById(R.id.ptr);
        empty = (NormalEmptyView) view.findViewById(R.id.fmobile_empty_view);
        titleView = (GlobalTitleView) view.findViewById(R.id.globalTitleView);
        titleView.setTitle("询师");

        mProvinceTv = (TextView) view.findViewById(R.id.provinceTv);
        mProvinceIv = (ImageView) view.findViewById(R.id.provinceIv);
        mProvinceLl = (LinearLayout) view.findViewById(R.id.provinceLl);

        menterLayout = (LinearLayout) view.findViewById(R.id.menter_type_layout);
        menterTypeIv = (ImageView) view.findViewById(R.id.menter_type_iv);
        menterTypeTv = (TextView) view.findViewById(R.id.menter_type_tv);

        empty.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ptr.headerRefreshing();
            }
        });
        empty.setVisibility(View.VISIBLE);
        empty.setEmptyType(NormalEmptyView.EMPTY_TYPE_LOADING);

        mProvinceLl.setOnClickListener(this);
        menterLayout.setOnClickListener(this);

        ptr.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                page = 1;
                getExperList();
            }
        });
        ptr.setOnFooterRefreshListener(new OnFooterRefreshListener() {

            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                if (CommonUtil.isNetworkConnected(getActivity()) && page <= pageCount) {
                    getExperList();

                } else {
                    showToast("我也是有底线的");
                    hideList(ptr);
                }

            }
        });

        mCache = ACache.get(getActivity());

        initCustomData();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    protected void bindViews(View view) {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }

    protected void initCustomData() {
        menterTypes = new ArrayList<DlgDataPicker>();
        DlgDataPicker dp1 = new DlgDataPicker();
        dp1.isSelected = false;
        dp1.menuStr = "学长学姐";
        dp1.sid = 13 + "";
        menterTypes.add(dp1);
        DlgDataPicker dp2 = new DlgDataPicker();
        dp2.isSelected = false;
        dp2.menuStr = "招办老师";
        dp2.sid = 14 + "";
        menterTypes.add(dp2);
        DlgDataPicker dp3 = new DlgDataPicker();
        dp3.isSelected = false;
        dp3.menuStr = "备考专家";
        dp3.sid = 15 + "";
        menterTypes.add(dp3);

        mAdapter = new ExperAadpter(getActivity());
        mExpertLv.setAdapter(mAdapter);

        if (CommonUtil.isNetworkConnected(getActivity())) {
            getExperList();
        } else {
            String reslt = mCache.getAsString(TypeUtilsString.CACHE_MENTORS_DATE_KEY);
            requestServer(reslt);
        }
    }

    private void getExperList() {

        //String url = "http://api.51kkww.com/getExpertsByType?pageCount=20&uId=-1&page=1&channel=channel";

        if (NimApplication.user == null || NimApplication.user.equals("")) {
            memberId = "-1";
        } else {
            memberId = NimApplication.user;
        }

        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/consult/queryExpertList.json?focusUserId=" + memberId + "&expertType=" + menterTypeId + "&admitStudentSchoolId=" + provinceId + "&page=" + page + "&pageSize=10";
        Log.e("dddddd", url);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                respose = responseInfo.result;
                if (page == 1) {
                    mCache.put(TypeUtilsString.CACHE_MENTORS_DATE_KEY, respose, 3 * ACache.TIME_HOUR);
                }
                requestServer(respose);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                showToast(s);
                hideList(ptr);
                if (mAdapter == null || mAdapter.getList() == null) {
                    setError(empty, ptr);
                }
            }
        });
    }

    public void requestServer(String content) {
        hideList(ptr);
        if (content.equals("")) {
            empty.setVisibility(View.VISIBLE);
            return;
        }
        ExperNewBean experBean = JsonUtil.parseJsonToBean(content, ExperNewBean.class);
        if (experBean == null) {
            empty.setVisibility(View.VISIBLE);
        } else {
            if (experBean.getReturnCode().equals("0")) {
                pageCount = Integer.parseInt(experBean.getData().getPageCount());
                List<AboutTechUpperBean> temp1 = experBean.getData().getExpertList();
                if (null != temp1 && temp1.size() > 0) {
                    if (page == 1) {
                        mAdapter.cleanData();
                        mAdapter.addMoreData(temp1);
                    } else {
                        mAdapter.addMoreData(temp1);
                    }
                    setHasData(empty, ptr);
                    page++;
                } else {
                    if (mAdapter == null || mAdapter.getList() == null || mAdapter.getList().size() == 0) {
                        empty.setVisibility(View.VISIBLE);
                        empty.setEmptyType(NormalEmptyView.EMPTY_TYPE_NOCONTENT);
                    }
                }
            } else {
                showToast("没有更多了");
                if (mAdapter == null || mAdapter.getList() == null || mAdapter.getList().size() == 0) {
                    empty.setVisibility(View.VISIBLE);
                    empty.setEmptyType(NormalEmptyView.EMPTY_TYPE_ERROR);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.provinceLl:
                expertProvince = SettingValue.expertSchool;
                // 省份
                PopupWindowManager.showLocationPopupWindow(getActivity(), mProvinceLl, mProvinceIv, provinceId, new PopupWindowManager.LocationSelectListenr() {

                    @Override
                    public void onSelected(String id, String menStr) {
                        if (TextUtils.isEmpty(id)) {
                            mProvinceTv.setText("不限");
                        } else {
                            mProvinceTv.setText(menStr);
                        }
                        provinceId = id;
                        mAdapter.cleanData();
                        ptr.headerRefreshing();

                    }
                }, expertProvince);
                break;
            case R.id.menter_type_layout:
                if (menterTypes != null && menterTypes.size() > 0) {
                    PopupWindowManager.showCityPopupWindow(getActivity(), menterLayout, menterTypeIv, menterTypeId, new PopupWindowManager.CitySelectListenr() {

                        @Override
                        public void onSelected(String id, String menStr) {
                            if (TextUtils.isEmpty(id)) {
                                menterTypeTv.setText("不限");
                            } else {
                                menterTypeTv.setText(menStr);
                            }
                            menterTypeId = id;
                            mAdapter.cleanData();
                            ptr.headerRefreshing();
                        }
                    }, menterTypes);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        List<AboutTechUpperBean> temp = mAdapter.getList();
        if (temp != null && temp.size() > 0) {
            AboutTechUpperBean experBean = temp.get(arg2);
            Intent intent = new Intent(getActivity(), SttDeatilActivity.class);
            intent.putExtra("expertId", experBean.getUserId());
            startActivity(intent);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        page = 1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

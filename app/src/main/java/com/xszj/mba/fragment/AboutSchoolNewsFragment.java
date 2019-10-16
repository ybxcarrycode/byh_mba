package com.xszj.mba.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.xszj.mba.R;
import com.xszj.mba.activity.NewsWebViewActivity;
import com.xszj.mba.adapter.AboutSchoolNewsFAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.bean.AboutSchoolDetNewsBean;
import com.xszj.mba.bean.FindBean;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.TypeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ybx on 2016/12/22.
 */

public class AboutSchoolNewsFragment extends BaseFragment {

    protected Context context;
    protected XRecyclerView xrecyclerview;
    protected List<AboutSchoolDetNewsBean.DataBean.ListBean> list = new ArrayList<>();

    protected AboutSchoolNewsFAdapter adapter;

    protected String academyId;

    protected int pager = 1;
    protected int allPage;
    protected int type;
    private String memberId;
    //广播
    private BroadcastReceiver connectionReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("schoolCollectNews")) {
                    int position = intent.getIntExtra("newsPosition", 0);
                    changeCollectType(position);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("schoolCollectNews");
        getActivity().registerReceiver(connectionReceiver, intentFilter);

    }

    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        return inflater.inflate(R.layout.fragment_school, container, false);
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        Bundle bundle = getArguments();
        type = bundle.getInt("findtype", -1);
        academyId = bundle.getString("academyId");
    }


    @Override
    protected void bindViews(View view) {
        xrecyclerview = (XRecyclerView) view.findViewById(R.id.xrecyclerview);
    }

    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setPullRefreshEnabled(false);
        list.clear();
        adapter = new AboutSchoolNewsFAdapter(list, getActivity());
        xrecyclerview.setAdapter(adapter);
        showProgressDialog();

        if (NimApplication.user == null || NimApplication.user.equals("")) {
            memberId = "-1";
        } else {
            memberId = NimApplication.user;
        }
        loadDate();

    }

    @Override
    protected void initListeners() {
        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                AboutSchoolDetNewsBean.DataBean.ListBean date = list.get(position);
                Intent intent = new Intent(context, NewsWebViewActivity.class);
                if (date.getNewsType().equals("1")){
                    String url = "http://mobile.byhmba.com/v1/news/query_news_detail.htm?newsId="+date.getNewsId();
                    intent.putExtra("url", url);
                }else{
                    intent.putExtra("url", date.getLinkUrl());
                }
                intent.putExtra("isCollect", date.getIsCollect());
                intent.putExtra("newsId", date.getNewsId());
                intent.putExtra("title", date.getNewsTitle());
                intent.putExtra("selPosition", position);
                intent.putExtra("findType", TypeUtils.SCHOOL_D_NEWS);
                startActivity(intent);
            }
        });


        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                xrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                if (pager < allPage) {
                    pager++;
                    loadDate();
                } else {
                    xrecyclerview.loadMoreComplete();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    //  收藏/取消收藏 后的改变 list中的数据
    private void changeCollectType(int position) {
        if (list.size() > position) {
            AboutSchoolDetNewsBean.DataBean.ListBean date = list.get(position);
            if ("1".equals(date.getIsCollect())) {
                list.get(position).setIsCollect("0");
            } else {
                list.get(position).setIsCollect("1");
            }
        }
    }


    private void loadDate() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("academyId", academyId);
        params.addBodyParameter("page", pager + "");
        params.addBodyParameter("pageSize", "10");
        params.addBodyParameter("userId", memberId);

        String url1 = ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/mba/school/query/academy_news.json?";

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url1, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                xrecyclerview.refreshComplete();
                xrecyclerview.loadMoreComplete();
                dismissProgressDialog();

                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }
                AboutSchoolDetNewsBean resDate = JsonUtil.parseJsonToBean(result, AboutSchoolDetNewsBean.class);
                if (null != resDate) {
                    if ("0".equals(resDate.getReturnCode())) {
                        allPage = Integer.parseInt(resDate.getData().getPageCount());
                        List<AboutSchoolDetNewsBean.DataBean.ListBean> listI = resDate.getData().getList();
                        if (listI != null && listI.size() > 0) {
                            adapter.addItemLast(listI);
                            adapter.notifyDataSetChanged();
                        } else if (list.size() > 0) {
                            showToast("没有更多数据");
                        }
                    } else {
                        showToast(resDate.getReturnMsg());
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dismissProgressDialog();
                xrecyclerview.refreshComplete();
                xrecyclerview.loadMoreComplete();

                showToast("网络不给力，请重试..");
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != connectionReceiver && null != getActivity()) {
            getActivity().unregisterReceiver(connectionReceiver);
        }
    }


}

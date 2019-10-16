package com.xszj.mba.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

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
import com.xszj.mba.adapter.CollectFAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.bean.AboutSchoolDetNewsBean;
import com.xszj.mba.bean.FindBean;
import com.xszj.mba.bean.MyCollectBean;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.TypeUtils;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.NormalEmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 * 我的收藏
 */

public class CollectActity extends BaseActivity {

    private GlobalTitleView titleView;
    private Context mContext;
    protected XRecyclerView xrecyclerview;
    private NormalEmptyView empty = null;
    protected List<MyCollectBean.DataBean.NewsListBean> list = new ArrayList<>();
    private CollectFAdapter adapter;

    private int pager = 1;
    private int allPager = 1;

    //广播
    private BroadcastReceiver connectionReceiver;

    @Override
    protected int getContentViewResId() {
        mContext = CollectActity.this;
        connectionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("collectCollectNews")) {
                    int position = intent.getIntExtra("newsPosition", 0);
                    changeCollectType(position);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("collectCollectNews");
        registerReceiver(connectionReceiver, intentFilter);

        return R.layout.activity_my_collect;
    }

    @Override
    protected void bindViews() {

        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        xrecyclerview = (XRecyclerView) findViewById(R.id.xrecyclerview);
        empty = (NormalEmptyView) findViewById(R.id.fmobile_empty_view);
    }

    @Override
    protected void initViews() {

        titleView.setTitle("我的收藏");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setPullRefreshEnabled(false);
        list.clear();

        adapter = new CollectFAdapter(list, mContext);
        xrecyclerview.setAdapter(adapter);
        showProgressDialog();

    }


    @Override
    protected void getDateForService() {
        super.getDateForService();
        loadDate();

    }


    private void loadDate() {
        final String url1 = ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/news/myNewsCollectList.json?";
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", "" + NimApplication.user);
        params.addBodyParameter("page", "" + pager);
        params.addBodyParameter("pageSize", "10");

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url1, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                dismissProgressDialog();
                xrecyclerview.loadMoreComplete();
                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }
                MyCollectBean resBean = JsonUtil.parseJsonToBean(result, MyCollectBean.class);
                if (resBean != null) {
                    if ("0".equals(resBean.getReturnCode())) {
                        allPager = Integer.parseInt(resBean.getData().getPageCount());
                        List<MyCollectBean.DataBean.NewsListBean> list1 = resBean.getData().getNewsList();
                        if (list1 != null && list1.size() > 0) {
                            adapter.addItemLast(list1);
                            adapter.notifyDataSetChanged();
                        } else {
                            showToast("没有更多数据..");
                        }
                    } else {
                        showToast(resBean.getReturnMsg());
                    }
                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                dismissProgressDialog();
                xrecyclerview.loadMoreComplete();
                showToast("网络不给力，请重试..");
            }
        });
    }


    @Override
    protected void initListeners() {

        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                if (list != null && list.size() > 0) {
                    MyCollectBean.DataBean.NewsListBean date = list.get(position);
                    Log.e("dddddd",date.getNewsContent()+"11111");
                    Intent intent = null;
                    if (date.getNewsType().equals("1")) {
                        intent = new Intent(context, NewsTextActivity.class);
                        intent.putExtra("url", date.getNewsContent());
                    } else {
                        intent = new Intent(context, NewsWebViewActivity.class);
                        intent.putExtra("url", date.getLinkUrl());
                    }
                    intent.putExtra("isCollect", "1");
                    intent.putExtra("newsId", date.getNewsId());
                    intent.putExtra("title", date.getNewsTitle());
                    intent.putExtra("selPosition", position);
                    intent.putExtra("findType", TypeUtils.COLLECT_ACTIVITY);
                    startActivity(intent);

                }

            }
        });


        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (pager < allPager) {
                    pager++;
                    loadDate();
                } else {
                    xrecyclerview.loadMoreComplete();
                    showToast("没有更多数据");
                }
            }
        });
    }


    // 取消收藏 后的改变 list中的数据  删除取消收藏的那一条
    private void changeCollectType(int position) {
        if (list.size() > position) {
            list.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connectionReceiver!=null){
            unregisterReceiver(connectionReceiver);
        }
    }
}

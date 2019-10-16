package com.xszj.mba.activity;

import android.content.Context;
import android.content.Intent;
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
import com.net.nim.demo.session.SessionHelper;
import com.xszj.mba.R;
import com.xszj.mba.adapter.FollowTeacherFAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.bean.AboutTechUpperBean;
import com.xszj.mba.bean.MyAttentionResBean;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.NormalEmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 * 关注名师
 */

public class FollowTeacherActivity extends BaseActivity {

    private GlobalTitleView titleView;
    private Context mContext;
    protected XRecyclerView xrecyclerview;
    private NormalEmptyView empty = null;
    protected List<AboutTechUpperBean> list = new ArrayList<>();
    private FollowTeacherFAdapter adapter;

    private int pager = 1;
    private int allPager = 1;

    @Override
    protected int getContentViewResId() {
        mContext = FollowTeacherActivity.this;
        return R.layout.activity_follow_teacher;
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        xrecyclerview = (XRecyclerView) findViewById(R.id.xrecyclerview);
        empty = (NormalEmptyView) findViewById(R.id.fmobile_empty_view);
    }

    @Override
    protected void initViews() {
        titleView.setTitle("关注名师");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setPullRefreshEnabled(false);
        list.clear();

        adapter = new FollowTeacherFAdapter(list, mContext);
        xrecyclerview.setAdapter(adapter);
        showProgressDialog();
    }

    @Override
    protected void getDateForService() {
        super.getDateForService();
        loadDate();

    }


    private void loadDate() {
        final String url1 = ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/user_info/expertFollowList.json?";
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
                MyAttentionResBean resBean = JsonUtil.parseJsonToBean(result, MyAttentionResBean.class);
                if (resBean != null) {
                    if ("0".equals(resBean.getReturnCode())) {
                        allPager = Integer.parseInt(resBean.getData().getPageCount());
                        List<AboutTechUpperBean> list1 = resBean.getData().getExpertList();
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
                    /*Intent intent = new Intent(context, SttDeatilActivity.class);
                    intent.putExtra("expertId", list.get(position).getUserId());
                    startActivity(intent);*/
                    SessionHelper.startP2PSession(mContext, list.get(position).getImUser());
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
}

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
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.xszj.mba.R;
import com.xszj.mba.adapter.CourseListAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.bean.CourseListResBean;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.view.GlobalTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ybx on 2016/12/30.
 *
 * 课程中心列表
 */

public class CourseListActivity extends BaseActivity {

    protected GlobalTitleView titleView;
    protected XRecyclerView xrecyclerview;
    protected List<CourseListResBean.DataBean> list = new ArrayList<>();
    protected CourseListAdapter adapter;
    private Context mContext;

    protected String category;

    @Override
    protected int getContentViewResId() {
        mContext = CourseListActivity.this;
        return R.layout.activity_course_list;
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        category = getIntent().getStringExtra("category");
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        titleView = (GlobalTitleView) findViewById(R.id.titleView);
        titleView.setTitle("课程中心");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);
    }

    @Override
    protected void bindViews() {
        xrecyclerview = (XRecyclerView) findViewById(R.id.xrecyclerview);

    }

    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setPullRefreshEnabled(false);
        list.clear();
        adapter = new CourseListAdapter(list, context);
        xrecyclerview.setAdapter(adapter);
        loadDate();


    }

    @Override
    protected void initListeners() {
        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                if (NimApplication.user == null || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(context);
                } else {
                    startActivity(new Intent(mContext, RecommendVedioPlayActivity.class).putExtra("courseId",list.get(position).getCourseId()).putExtra("title",list.get(position).getCourseName()));
                }

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
                showToast("没有更多数据");
            }
        });

    }


    private void loadDate() {
        String url1 = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/course/courseList.json?category=" + category;
        Log.e("dssdADA", url1);
        showProgressDialog();
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.GET, url1, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                xrecyclerview.loadMoreComplete();
                dismissProgressDialog();

                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }
                CourseListResBean resBean = JsonUtil.parseJsonToBean(result, CourseListResBean.class);
                if (null != resBean) {
                    if ("0".equals(resBean.getReturnCode())) {
                        List<CourseListResBean.DataBean> list1 = resBean.getData();
                        if (null != list1 && list1.size() > 0) {
                            adapter.addItemLast(list1);
                            adapter.notifyDataSetChanged();
                        } else if (list.size() > 0) {
                            showToast("没有更多数据");
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


}

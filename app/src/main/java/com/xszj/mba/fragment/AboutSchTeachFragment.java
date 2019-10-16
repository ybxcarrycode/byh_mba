package com.xszj.mba.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.xszj.mba.activity.SttDeatilActivity;
import com.xszj.mba.adapter.FollowTeacherFAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.bean.AboutSchoolDetTeachBean;
import com.xszj.mba.bean.AboutTechUpperBean;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.TypeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ybx on 2017/1/9.
 */

public class AboutSchTeachFragment extends BaseFragment {


    protected Context context;
    protected XRecyclerView xrecyclerview;
    protected List<AboutTechUpperBean> list = new ArrayList<>();

    protected FollowTeacherFAdapter adapter;

    protected String academyId;

    protected int pager = 1;
    protected int allPage;

    protected RequestParams params;
    protected String url = null;
    protected int type;
    private String memberId="";


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

        setUrlFromType();

    }

    private void setUrlFromType() {
        switch (type) {
            case TypeUtils.SCHOOL_D_TEACHER:
                url = ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/mba/school//query/academy_teachers.json?";
                break;
            case TypeUtils.SCHOOL_D_UPPERCLASSMAN:
                url = ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/mba/school//query/academy_seniors.json?";
                break;
        }
    }

    private void setParamsFromType() {

        if(NimApplication.user==null || NimApplication.user.equals("")){
            memberId="-1";
        }else {
            memberId = NimApplication.user;
        }

        switch (type) {
            case TypeUtils.SCHOOL_D_TEACHER:
            case TypeUtils.SCHOOL_D_UPPERCLASSMAN:

                params = new RequestParams();
                params.addBodyParameter("userId", memberId);
                params.addBodyParameter("academyId", academyId);
                params.addBodyParameter("page", pager + "");
                params.addBodyParameter("pageSize", "10");
                break;
        }

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
        adapter = new FollowTeacherFAdapter(list, getActivity());
        xrecyclerview.setAdapter(adapter);
        showProgressDialog();
        loadDate();

    }

    @Override
    protected void initListeners() {
        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                AboutTechUpperBean resDate = list.get(position);
                Intent intent = new Intent(context, SttDeatilActivity.class);
                intent.putExtra("expertId", resDate.getUserId());
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


    private void loadDate() {
        setParamsFromType();
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                xrecyclerview.refreshComplete();
                xrecyclerview.loadMoreComplete();

                dismissProgressDialog();
                String result = responseInfo.result.toString();
                Log.e("dddddd school",result);
                if ("".equals(result)) {
                    return;
                }
                AboutSchoolDetTeachBean resDate = JsonUtil.parseJsonToBean(result, AboutSchoolDetTeachBean.class);
                if (null != resDate) {
                    if ("0".equals(resDate.getReturnCode())) {
                        allPage = Integer.parseInt(resDate.getData().getPageCount());
                        List<AboutTechUpperBean> listI = resDate.getData().getList();
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

}

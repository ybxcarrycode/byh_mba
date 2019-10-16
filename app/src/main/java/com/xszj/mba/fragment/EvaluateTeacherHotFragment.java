package com.xszj.mba.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.xszj.mba.R;
import com.xszj.mba.adapter.EvaluteTeacherFAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.bean.EvaluateTeacherListBean;
import com.xszj.mba.bean.EvaluateTeacherListBean.DataBean;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 * 最热
 */

public class EvaluateTeacherHotFragment extends BaseFragment {

    private Context mContext;
    protected XRecyclerView xrecyclerview;
    protected List<DataBean> list = new ArrayList<>();
    private EvaluteTeacherFAdapter adapter;
    private String expertUuId;
    private RequestParams params;
    private int startNum = 0;
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadDate(true);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("evaluteSuccess");
        mContext.registerReceiver(broadcastReceiver, intentFilter);
        return inflater.inflate(R.layout.fragment_sys_message, container, false);
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        expertUuId = getActivity().getIntent().getStringExtra("expertUuId");
    }

    @Override
    protected void bindViews(View view) {
        xrecyclerview = (XRecyclerView) view.findViewById(R.id.xrecyclerview);
    }

    @Override
    protected void initViews() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        adapter = new EvaluteTeacherFAdapter(list, mContext);
        xrecyclerview.setAdapter(adapter);

    }

    @Override
    protected void getDateForService() {
        super.getDateForService();
        showProgressDialog();
        loadDate(true);
    }

    @Override
    protected void initListeners() {
        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                loadDate(true);
            }

            @Override
            public void onLoadMore() {
                loadDate(false);
            }
        });
    }


    private void loadDate(final boolean type) {

       showProgressDialog();

        String url= ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/consult/commentlist.json?";
        Log.e("dssdADA", url);
        if (type){
            startNum=0;
        }
        params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("teacherId", expertUuId);
        params.addBodyParameter("orderType", 1+"");
        params.addBodyParameter("startNum",startNum+"");
        params.addBodyParameter("pageSize", "6");
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                xrecyclerview.refreshComplete();
                xrecyclerview.loadMoreComplete();

                dismissProgressDialog();
                String result = responseInfo.result.toString();

                Log.e("ddd",result);
                if ( "".equals(result)) {
                    return;
                }
                EvaluateTeacherListBean evaluateTeacherListBean = JsonUtil.parseJsonToBean(result, EvaluateTeacherListBean.class);
                if (null != evaluateTeacherListBean) {
                    if ("0".equals(evaluateTeacherListBean.getReturnCode())) {
                        List<DataBean> listTemp = evaluateTeacherListBean.getData();
                        if (listTemp !=null && listTemp.size()>0){
                            if (type){
                                list.clear();
                            }
                            adapter.addItemLast(listTemp);
                            startNum = list.size()+1;
                            adapter.notifyDataSetChanged();
                        }else {
                            showToast("没有更多数据");
                        }

                    } else {
                        showToast(evaluateTeacherListBean.getReturnMsg());
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
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver!=null){
            mContext.unregisterReceiver(broadcastReceiver);
        }
    }
}

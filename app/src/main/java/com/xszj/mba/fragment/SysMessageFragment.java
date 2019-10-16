package com.xszj.mba.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.xszj.mba.adapter.SysMessageFAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.bean.MyMsgBean;
import com.xszj.mba.bean.MyMsgBeanResult;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.DialogDoubleHelper;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */

public class SysMessageFragment extends BaseFragment {

    private Context mContext;
    protected XRecyclerView xrecyclerview;
    protected List<MyMsgBean> list = new ArrayList<>();
    protected SysMessageFAdapter adapter;

    protected int pager = 1;
    protected int allPager = 1;

    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_sys_message, container, false);
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
        xrecyclerview.setPullRefreshEnabled(false);
        adapter = new SysMessageFAdapter(list, mContext);
        xrecyclerview.setAdapter(adapter);

    }

    @Override
    protected void getDateForService() {
        super.getDateForService();
        loadDate();
    }

    @Override
    protected void initListeners() {
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
                }
            }
        });
    }

    public void delAllMsg() {
        if (list.size() > 0) {
            if (null == NimApplication.user || NimApplication.user.equals("")) {
                CommonUtil.showLoginddl(mContext);
                return;
            }
            DialogDoubleHelper dialogDoubleHelpernew = new DialogDoubleHelper(mContext);
            dialogDoubleHelpernew.setMsgTxt("确定要删除所有消息?");
            dialogDoubleHelpernew.setLeftTxt("取消");
            dialogDoubleHelpernew.setRightTxt("确定");
            dialogDoubleHelpernew.setDialogOnclickListener(new DialogDoubleHelper.DialogDoubleHelperOnclickListener() {
                @Override
                public void setLeftOnClickListener(Dialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void setRightOnClickListener(Dialog dialog) {
                    delAllMsgService();
                    dialog.dismiss();
                }
            });
            dialogDoubleHelpernew.show();
        }
    }

    private void loadDate() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("page", pager + "");
        params.addBodyParameter("pageSize", "8");

        final String url1 = ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/message_center/my_message_list.json?";

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url1, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                xrecyclerview.loadMoreComplete();

                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }
                MyMsgBeanResult resDate = JsonUtil.parseJsonToBean(result, MyMsgBeanResult.class);
                if (null != resDate) {
                    if ("0".equals(resDate.getReturnCode())) {
                        allPager = Integer.parseInt(resDate.getData().getPageCount());
                        List<MyMsgBean> listI = resDate.getData().getList();
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
                xrecyclerview.loadMoreComplete();
                showToast("网络不给力，请重试..");
            }
        });

    }

    private void delAllMsgService() {
        final String url1 = ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/message_center/delete_my_message_batch.json?userId=" + NimApplication.user;
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.GET, url1, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }
                String code;
                try {
                    JSONObject object = new JSONObject(result);
                    code = object.getString("returnCode");
                    if (null != code && "0".equals(code)) {
                        list.clear();
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "删除失败，请重试...", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mContext, "删除失败，请重试...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}

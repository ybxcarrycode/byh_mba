package com.xszj.mba.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.xszj.mba.R;
import com.xszj.mba.adapter.SchoolListAdapter;
import com.xszj.mba.adapter.SchoolListFAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.bean.SchoolListBean;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.NoScrollListView;
import com.xszj.mba.view.NormalEmptyView;
import com.xszj.mba.bean.SchoolListBean.DataBean.HotListBean;
import com.xszj.mba.bean.SchoolListBean.DataBean.FamousListBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */

public class SchoolListActivity extends BaseActivity {
    private GlobalTitleView titleView;
    private Context mContext;
    protected XRecyclerView xrecyclerview;
    private LinearLayout headView;
    private NoScrollListView headListView;
    private NormalEmptyView empty = null;
    private List<FamousListBean> listHead=new ArrayList<>();
    protected List<SchoolListBean.DataBean.HotListBean> list = new ArrayList<>();
    private SchoolListFAdapter adapter;

    protected int pager = 1;
    protected int allPage;

    protected RequestParams params;

    @Override
    protected int getContentViewResId() {
        mContext = SchoolListActivity.this;
        return R.layout.activity_school_list;
    }


    @Override
    protected void bindViews() {

        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        xrecyclerview = (XRecyclerView)findViewById(R.id.xrecyclerview);
        empty = (NormalEmptyView) findViewById(R.id.fmobile_empty_view);
        //加载头部布局
        headView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.school_list_head_view, null);
        headListView = (NoScrollListView) headView.findViewById(R.id.home_fragment_head_listview);
    }

    @Override
    protected void initViews() {

        titleView.setTitle("院校列表");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setPullRefreshEnabled(false);
        list.clear();
        xrecyclerview.addHeaderView(headView);
        adapter = new SchoolListFAdapter(list, mContext);
        xrecyclerview.setAdapter(adapter);
        showProgressDialog();

    }


    @Override
    protected void getDateForService() {
        super.getDateForService();

        loadDate();

    }


    private void loadDate() {

        String url= ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/mba/school/query/more_famous_school.json?";
        Log.e("dssdADA", url);

        params = new RequestParams();
        params.addBodyParameter("page", pager + "");
        params.addBodyParameter("pageSize", "30");
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                xrecyclerview.refreshComplete();
                xrecyclerview.loadMoreComplete();

                dismissProgressDialog();
                String result = responseInfo.result.toString();
                if ( "".equals(result)) {
                    return;
                }
                SchoolListBean resDate = JsonUtil.parseJsonToBean(result, SchoolListBean.class);
                if (null != resDate) {
                    if ("0".equals(resDate.getReturnCode())) {
                        allPage = 1;
                        listHead = resDate.getData().getFamousList();
                        if (listHead!=null && listHead.size()>0 && pager==1){
                            SchoolListAdapter schoolListAdapter =new SchoolListAdapter(mContext,listHead);
                            headListView.setAdapter(schoolListAdapter);
                        }

                        List<HotListBean> listI = resDate.getData().getHotList();

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
    protected void initListeners() {

        headListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FamousListBean famousListBean = listHead.get(position);
                Intent intent = new Intent(mContext,SchoolDetailActivity.class);
                intent.putExtra("titile",famousListBean.getAcademyName());
                intent.putExtra("academyId",famousListBean.getAcademyId());
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                HotListBean hotListBean = list.get(position);
                Intent intent = new Intent(mContext,SchoolDetailActivity.class);
                intent.putExtra("titile",hotListBean.getAcademyName());
                intent.putExtra("academyId",hotListBean.getAcademyId());
                startActivity(intent);
            }
        });


        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                pager++;
                if (pager <= allPage) {
                    loadDate();
                }else{
                    xrecyclerview.loadMoreComplete();
                }
            }
        });
    }
}

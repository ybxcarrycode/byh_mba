package com.xszj.mba.activity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.xszj.mba.R;
import com.xszj.mba.adapter.TopicListAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.bean.KaoquanBean;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.NormalEmptyView;
import com.xszj.mba.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/26.
 */

public class FoucsBbsActivity extends BaseActivity {

    @BindView(R.id.globalTitleView)
    GlobalTitleView globalTitleView;
    @BindView(R.id.fmobile_empty_view)
    NormalEmptyView fmobileEmptyView;
    @BindView(R.id.all_topic_listview)
    ListView mTopicListView;
    @BindView(R.id.all_topic_layout)
    PullToRefreshView mRefreshLayout;
    private TopicListAdapter adapter;
    private String respose;
    private String focusId;
    private List<KaoquanBean.DataBean> mListBbs = new ArrayList<KaoquanBean.DataBean>();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_me_foucsbbs;
    }

    @Override
    protected void bindViews() {
        globalTitleView.setTitle("考圈关注");
        globalTitleView.setBackVisible(true);
        globalTitleView.setBackIconImage(R.drawable.icon_back_arrow_normal);
        fmobileEmptyView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mRefreshLayout.headerRefreshing();
            }
        });
        initRefreshView();
    }

    @Override
    protected void initViews() {

    }

    /**
     * 初始化下拉刷新试图, listview
     */
    protected void initRefreshView() {
        // 下拉刷新, 上拉加载的布局
        mRefreshLayout.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getBbsList(true);
            }
        });
        mRefreshLayout.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                getBbsList(false);
            }
        });

        adapter = new TopicListAdapter(FoucsBbsActivity.this);
        mTopicListView.setAdapter(adapter);

        // 关闭动画缓存
        mTopicListView.setAnimationCacheEnabled(false);
        // 开启smooth scrool bar
        mTopicListView.setSmoothScrollbarEnabled(true);
        mTopicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (null == view.findViewById(R.id.like_tv).getTag()) {
                    return;
                }
                KaoquanBean.DataBean obj = (KaoquanBean.DataBean) view.findViewById(R.id.like_tv).getTag();
                if (null != obj) {
                    BbsDetailActivity.lauchSelf(context, obj.getSubjectId(),position);
                }
            }
        });
        mRefreshLayout.headerRefreshing();
    }

    private void getBbsList(final boolean refresh) {
        String uid = "-1";
        if (null != NimApplication.user) {
            uid = NimApplication.user;
        }
        if (refresh) {
            focusId="-1";
        }
        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/subject/subject_focus_list.json?";
        RequestParams params = new RequestParams();

        params.addBodyParameter("userId",uid);
        params.addBodyParameter("focusId",focusId);
        params.addBodyParameter("pageSize", "15");

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url,params,new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                hideList(mRefreshLayout);
                //mRefreshLayout.onFooterRefreshComplete();
                respose = responseInfo.result;
                Log.e("ddddd",respose);
                KaoquanBean bean = JsonUtil.parseJsonToBean(respose, KaoquanBean.class);
                if (bean==null){
                    return;
                }
                List<KaoquanBean.DataBean> temp =bean.getData();
                if (bean.getReturnCode().equals("0")) {
                    if (refresh) {
                        if (null != temp && 0 < temp.size()) {
                            mListBbs = temp;
                            adapter.setList(mListBbs);
                            fmobileEmptyView.setVisibility(View.GONE);
                            mRefreshLayout.setVisibility(View.GONE);
                            focusId = mListBbs.get(mListBbs.size()-1).getSubjectId();
                        } else {
                            //setEmpty(empty, mRefreshLayout);
                            fmobileEmptyView.setVisibility(View.VISIBLE);
                            fmobileEmptyView.setEmptyType(NormalEmptyView.EMPTY_TYPE_NOCONTENT);

                        }
                    } else {
                        if (null != temp && 0 < temp.size()) {
                            mListBbs.addAll(temp);
                            adapter.addMoreData(temp);
                            focusId = mListBbs.get(mListBbs.size()-1).getSubjectId();
                        } else {
                            Toast.makeText(context, "已经没有更多了", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    if (null == mListBbs || 0 == mListBbs.size()) {
                        if (refresh) {
                            fmobileEmptyView.setVisibility(View.VISIBLE);
                            mRefreshLayout.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                showToast(s);
                hideList(mRefreshLayout);
            }
        });
    }



    @Override
    protected void initListeners() {

    }

}

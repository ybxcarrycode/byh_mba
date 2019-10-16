package com.xszj.mba.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.nets.responses.FeedsResponse;
import com.umeng.comm.core.nets.uitls.NetworkUtils;
import com.umeng.comm.core.utils.ToastMsg;
import com.umeng.comm.ui.activities.FeedDetailActivity;
import com.xszj.mba.R;
import com.xszj.mba.adapter.QuestionFAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.NormalEmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 * 我的提问
 */

public class MyQuestionActivity extends BaseActivity {

    private GlobalTitleView titleView;
    private NormalEmptyView empty = null;
    protected XRecyclerView xrecyclerview;
    protected List<FeedItem> list = new ArrayList<>();
    private QuestionFAdapter adapter;
    private CommunitySDK mCommunitySDK = null;
    private String umengId = null;
    private String mNextPageUrl = null;
    private CommUser commUser;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_my_question;
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        mCommunitySDK = CommunityFactory.getCommSDK(this);
        commUser = CommConfig.getConfig().loginedUser;
        if (commUser==null){
            return;
        }

    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        xrecyclerview = (XRecyclerView) findViewById(R.id.xrecyclerview);
        empty = (NormalEmptyView) findViewById(R.id.fmobile_empty_view);
    }

    @Override
    protected void initViews() {
        titleView.setTitle("我的提问");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setPullRefreshEnabled(false);
        list.clear();

        adapter = new QuestionFAdapter(list, context);
        xrecyclerview.setAdapter(adapter);
        showProgressDialog();

    }


    @Override
    protected void getDateForService() {
        super.getDateForService();
        loadDate();
    }


    private void loadDate() {
        mCommunitySDK.fetchUserTimeLine(umengId, new Listeners.FetchListener<FeedsResponse>() {
            @Override
            public void onStart() {
                showProgressDialog();
            }

            @Override
            public void onComplete(FeedsResponse response) {
                dismissProgressDialog();

                if (NetworkUtils.handleResponseAll(response)) {
                    // 如果返回的数据是空，则需要置下一页地址为空
                    if (response.errCode == ErrorCode.NO_ERROR) {
                        mNextPageUrl = "";
                    }
                    return;
                }
                mNextPageUrl = response.nextPageUrl;

                List<FeedItem> newFeedItems = response.result;
                if (newFeedItems!=null&&newFeedItems.size()>0) {
                    adapter.addItemLast(newFeedItems);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void loadDateMore() {
        mCommunitySDK.fetchNextPageData(mNextPageUrl,
                FeedsResponse.class, new Listeners.SimpleFetchListener<FeedsResponse>() {

                    @Override
                    public void onComplete(FeedsResponse response) {
                        xrecyclerview.loadMoreComplete();

                        if (NetworkUtils.handleResponseAll(response)) {
                            // 如果返回的数据是空，则需要置下一页地址为空
                            if (response.errCode == ErrorCode.NO_ERROR) {
                                mNextPageUrl = "";
                            }
                            return;
                        }
                        mNextPageUrl = response.nextPageUrl;
                        // 去掉重复的feed
                        final List<FeedItem> feedItems = response.result;
                        if (feedItems != null && feedItems.size() > 0) {
                            adapter.addItemLast(feedItems);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected void initListeners() {
        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                final int realPosition = position;
                final FeedItem feedItem = list.get(realPosition < 0 ? 0
                        : realPosition);
                if (feedItem != null && feedItem.status >= FeedItem.STATUS_SPAM
                        && feedItem.category == FeedItem.CATEGORY.FAVORITES && feedItem.status != FeedItem.STATUS_LOCK) {
                    ToastMsg.showShortMsgByResName("umeng_comm_feed_spam_deleted");
                    return;
                }
                Intent intent = new Intent(context, FeedDetailActivity.class);
                intent.putExtra(Constants.TAG_FEED, feedItem);
                startActivity(intent);
            }
        });

        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (TextUtils.isEmpty(mNextPageUrl)) {
                    return;
                }
                loadDateMore();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

        }
    }
}

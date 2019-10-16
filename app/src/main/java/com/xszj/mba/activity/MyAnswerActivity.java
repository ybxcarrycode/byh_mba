package com.xszj.mba.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.umeng.comm.core.beans.FeedItem;
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
 * 我的回答
 */

public class MyAnswerActivity extends BaseActivity {

    private GlobalTitleView titleView;
    private NormalEmptyView empty = null;
    protected XRecyclerView xrecyclerview;
    private Context mContext;
    private EditText ed_text;
    protected List<FeedItem> list = new ArrayList<>();
    private QuestionFAdapter adapter;
    @Override
    protected int getContentViewResId() {
        mContext = MyAnswerActivity.this;
        return R.layout.activity_my_question;
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        xrecyclerview = (XRecyclerView)findViewById(R.id.xrecyclerview);
        empty = (NormalEmptyView) findViewById(R.id.fmobile_empty_view);
        ed_text = (EditText) findViewById(R.id.ed_text);
    }

    @Override
    protected void initViews() {
        titleView.setTitle("我的回复");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setPullRefreshEnabled(false);
        list.clear();

        adapter = new QuestionFAdapter(list, mContext);
        xrecyclerview.setAdapter(adapter);
        //showProgressDialog();

    }


    @Override
    protected void getDateForService() {
        super.getDateForService();
        //loadDate(1);

    }


    private void loadDate(final int p) {

    }

    @Override
    protected void initListeners() {
        ed_text.setOnClickListener(this);


        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                showToast("item" + position);
            }
        });


        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                int size = list.size();
                Log.e("dssdADA", size + "");
                if (size > 0) {

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ed_text:

                break;
        }
    }
}

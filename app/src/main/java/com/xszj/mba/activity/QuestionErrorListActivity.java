package com.xszj.mba.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xszj.mba.R;
import com.xszj.mba.adapter.QuestionErrorListAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.view.GlobalTitleView;

import java.util.ArrayList;
import java.util.List;

import activeandroid.query.From;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：mba
 * 类描述：
 * 创建人：ybx
 * 创建时间：2018/1/24 11:10
 * 备注：
 */

public class QuestionErrorListActivity extends BaseActivity {
    @BindView(R.id.globalTitleView)
    GlobalTitleView globalTitleView;
    @BindView(R.id.xrecyclerview)
    XRecyclerView xrecyclerview;

    private QuestionErrorListAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_question_error_list_layout;
    }

    @Override
    protected void bindViews() {
        globalTitleView.setTitle("错题本");
        globalTitleView.setTitleColor(Color.BLACK);
        globalTitleView.setBackVisible(true);
        globalTitleView.setBackIconImage(R.mipmap.btn_back_normal);
        globalTitleView.setTitleViewBackground(Color.WHITE);
    }

    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setPullRefreshEnabled(false);
        xrecyclerview.setLoadingMoreEnabled(true);
        adapter = new QuestionErrorListAdapter(null, context);
        xrecyclerview.setAdapter(adapter);

        getDataFromService();
    }

    @Override
    protected void initListeners() {
        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, OnlyQuestionErrorListActivity.class);
                intent.putExtra("title", "2016联考逻辑终极测试");
                startActivity(intent);
            }
        });

        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                xrecyclerview.loadMoreComplete();
                /*for (int i = 0; i < 7; i++) {
                    list.add("1" + i);
                }*/
                list.add("1");
                adapter.setData(list);
            }
        });
    }


    private void getDataFromService() {
        /*for (int i = 0; i < 10; i++) {
            list.add("1" + i);
        }*/
        list.add("1");
        adapter.setData(list);
    }
}

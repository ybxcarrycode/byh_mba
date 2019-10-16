package com.xszj.mba.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xszj.mba.R;
import com.xszj.mba.adapter.NewsAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.base.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
public class NewsFragment extends BaseFragment {

    protected XRecyclerView xrecyclerview;
    protected NewsAdapter adapter;
    protected List<String> list = new ArrayList<>();

    protected int pager = 1;

    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    protected void getDateForService() {
        super.getDateForService();

    }

    @Override
    protected void bindViews(View view) {
        xrecyclerview = (XRecyclerView) view.findViewById(R.id.xrecyclerview);
    }

    @Override
    protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(layoutManager);
        adapter = new NewsAdapter(list, getActivity());
        xrecyclerview.setAdapter(adapter);
        loadDate(1);

    }

    @Override
    protected void initListeners() {
        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                showToast("item" + position);
            }
        });

        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pager = 1;
                loadDate(1);
            }

            @Override
            public void onLoadMore() {
                pager++;
                loadDate(pager);
            }
        });


    }

    @Override
    public void onClick(View v) {

    }


    private void loadDate(final int p) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                xrecyclerview.refreshComplete();
                xrecyclerview.loadMoreComplete();

                List<String> listString = new ArrayList<String>();
                if (p > 4) {
                    showToast("没有更多数据");
                    return;
                }
                for (int i = 0; i < 10; i++) {
                    listString.add("");
                }
                if (p == 1) {
                    list.clear();
                }
                adapter.addItemLast(listString);
                adapter.notifyDataSetChanged();
            }
        }, 3000);


    }


}

package com.xszj.mba.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xszj.mba.R;
import com.xszj.mba.adapter.PerfectDetailFAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.bean.PerfectSchoolAndTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class PerfectDetailActivity extends BaseActivity {

    protected XRecyclerView xrecyclerview;
    protected PerfectDetailFAdapter adapter;
    protected List<PerfectSchoolAndTypeBean> list = new ArrayList<>();

    @Override
    protected int getContentViewResId() {

        return R.layout.activity_perfect_detail;
    }


    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        Object[] cobjs = (Object[]) getIntent().getSerializableExtra("list");

        if (cobjs != null) {
            for (int i = 0; i < cobjs.length; i++) {
                PerfectSchoolAndTypeBean perfectSchoolAndTypeBean  = (PerfectSchoolAndTypeBean)cobjs[i];
                Log.e("ddddd", ((PerfectSchoolAndTypeBean)cobjs[i]).getDictionaryName());
                list.add(perfectSchoolAndTypeBean);
            }
        }
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
        xrecyclerview.setLoadingMoreEnabled(false);
        adapter = new PerfectDetailFAdapter(list, context);
        xrecyclerview.setAdapter(adapter);
    }


    @Override
    protected void initListeners() {
//笔试课程的item点击事件
        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                setResult(RESULT_OK, new Intent().putExtra("position", position));
                finish();
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}

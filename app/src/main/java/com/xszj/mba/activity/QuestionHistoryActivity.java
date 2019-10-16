package com.xszj.mba.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xszj.mba.R;
import com.xszj.mba.adapter.QuestionHisAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.view.GlobalTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/24.
 * 刷题历史界面
 */

public class QuestionHistoryActivity extends BaseActivity {
    @BindView(R.id.tv_current_count)
    TextView tvCurrentCount;
    @BindView(R.id.tv_knowledge_count)
    TextView tvKnowledgeCount;
    @BindView(R.id.tv_allday_count)
    TextView tvAlldayCount;
    @BindView(R.id.tv_allquestion_count)
    TextView tvAllquestionCount;
    @BindView(R.id.recy)
    RecyclerView recy;

    private GlobalTitleView titleView;
    private List<String> list = new ArrayList<>();
    private QuestionHisAdapter questionHisAdapter;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_questions_history;
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);

    }

    @Override
    protected void initViews() {
        titleView.setTitle("刷题历史");
        titleView.setTitleColor(Color.BLACK);
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.mipmap.btn_back_normal);
        titleView.setTitleViewBackground(Color.WHITE);
        titleView.setTitleRightTvShow(true);
        titleView.setTitleRightTv("查看错题");
        titleView.setTitleRightTvColor(Color.parseColor("#646A6D"));
        titleView.setTitleViewListener(new GlobalTitleView.TitleViewListener() {
            @Override
            public void setOnBackBtn() {
                finish();
            }

            @Override
            public void setDoMore() {
                startActivity(new Intent(context, QuestionErrorListActivity.class));
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recy.setLayoutManager(linearLayoutManager);

        list.add("");

        questionHisAdapter = new QuestionHisAdapter(list);
        recy.setAdapter(questionHisAdapter);

        questionHisAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(context, QuestionDetailActivity.class));
            }
        });

    }

    @Override
    protected void initListeners() {

    }

}

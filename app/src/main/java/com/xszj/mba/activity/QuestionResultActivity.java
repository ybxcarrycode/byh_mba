package com.xszj.mba.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.adapter.QuestionResultAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.recySpacingItem.GridSpacingItemDecoration;
import com.xszj.mba.view.GlobalTitleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/15.
 */
public class QuestionResultActivity extends BaseActivity {

    @BindView(R.id.recy1)
    RecyclerView recy1;
    @BindView(R.id.tv_current_count)
    TextView tvCurrentCount;
    @BindView(R.id.tv_question_acount)
    TextView tvQuestionAcount;
    @BindView(R.id.tv_current_precent)
    TextView tvCurrentPrecent;
    @BindView(R.id.tv_beat_percent)
    TextView tvBeatPercent;
    private GlobalTitleView titleView;
    private GridLayoutManager layoutManager;
    private List<String> list = new ArrayList<>();
    private QuestionResultAdapter questionResultAdapter;
    private HashMap<Integer, Integer> answerHashMap;

    @Override
    protected void getIntentDate() {
        super.getIntentDate();

        answerHashMap = new HashMap<Integer, Integer>();
        answerHashMap = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("userSel");

        Iterator iter = answerHashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            Log.e("ddddddHashmap", key + "///" + value);
        }

    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_questions_result;
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
    }

    @Override
    protected void initViews() {
        titleView.setTitle("刷题报告");
        titleView.setTitleColor(Color.BLACK);
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.mipmap.btn_back_normal);
        titleView.setTitleViewBackground(Color.WHITE);

        // 如果我们想要一个GridView形式的RecyclerView，那么在LayoutManager上我们就要使用GridLayoutManager
        // 实例化一个GridLayoutManager，列数为6
        layoutManager = new GridLayoutManager(this, 6);
        recy1.addItemDecoration(new GridSpacingItemDecoration(6, 40, true));
        recy1.setLayoutManager(layoutManager);
    }

    @Override
    protected void getDateForService() {
        super.getDateForService();

        for (int i = 1; i < 25; i++) {

            list.add(i + "");
        }

        questionResultAdapter = new QuestionResultAdapter(list);
        questionResultAdapter.setMapData(answerHashMap);
        recy1.setAdapter(questionResultAdapter);

    }

    @Override
    protected void initListeners() {

    }


    @OnClick(R.id.tv_completion_answer)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_completion_answer:
                startActivity(new Intent(context, QuestionBankActivity.class));
                finish();
                break;

            default:
                break;
        }
    }
}

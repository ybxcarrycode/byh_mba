package com.xszj.mba.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xszj.mba.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 * 刷题历史adapter
 */

public class QuestionHisAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public QuestionHisAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_question_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_type, item+"逻辑")
                .setText(R.id.tv_title, item+"2016联考逻辑终极测试")
                .setText(R.id.tv_time, item+"2018.03.08");
    }
}

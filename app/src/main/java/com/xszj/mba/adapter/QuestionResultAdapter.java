package com.xszj.mba.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xszj.mba.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/1/15.
 */

public class QuestionResultAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    private HashMap<Integer,Integer> answerMap = new HashMap<>();

    public QuestionResultAdapter( @Nullable List<String> data) {
        super(R.layout.adapter_question_result, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setBackgroundRes(R.id.tv_ti,
                null == answerMap.get(helper.getAdapterPosition()+1)
                        ? R.drawable.bg_btn_question_select_no
                        : R.drawable.bg_btn_question_select_yes)

                .setText(R.id.tv_ti, item);
    }

    public void setMapData(HashMap<Integer, Integer> message) {
        answerMap = message;
        if (answerMap==null){
            return;
        }else {
            notifyDataSetChanged();
        }
    }
}

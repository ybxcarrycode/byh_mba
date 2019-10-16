package com.xszj.mba.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xszj.mba.R;
import com.xszj.mba.bean.AnswerBean;

import java.util.List;


/**
 * Created by swh on 2017/12/18.
 * 答案adapter
 */

public class AnswerAdapter extends BaseQuickAdapter<AnswerBean, BaseViewHolder> {

    private int selectPosition = -1;
    private boolean isCurrent = false;

    public AnswerAdapter(@Nullable List<AnswerBean> data) {
        super(R.layout.adapter_answer, data);
    }


    public void setSelectPosition(int currentPosition, boolean b) {
        this.selectPosition = currentPosition;
        isCurrent = b;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, AnswerBean item) {
        if (selectPosition == helper.getAdapterPosition()) {
            if (isCurrent) {
                helper.setTextColor(R.id.tv_answer, Color.parseColor("#0091EA"))
                        .setBackgroundRes(R.id.tv_answer_sel, R.drawable.btn_black_stroke_shape_select);
            } else {
                helper.setTextColor(R.id.tv_answer, Color.parseColor("#FF7676"))
                        .setBackgroundRes(R.id.tv_answer_sel, R.drawable.btn_black_stroke_shape_error);
            }
            helper.setTextColor(R.id.tv_answer_sel, Color.parseColor("#ffffff"));
        } else {
            helper.setTextColor(R.id.tv_answer, Color.parseColor("#646A6D"))
                    .setBackgroundRes(R.id.tv_answer_sel, R.drawable.btn_black_stroke_shape);
            helper.setTextColor(R.id.tv_answer_sel, Color.parseColor("#646A6D"));
        }
        String answer = "";
        String answernum = "";
        Log.e("dddddddddd",helper.getAdapterPosition() + "//");
        if (helper.getAdapterPosition() == 1) {
            answer = "中国关于鼓励高校毕业生创业的政策较少，创业环境不佳。";
            answernum = "A";
        } else if (helper.getAdapterPosition() == 2) {
            answer = "中国高校毕业生的绝对数量比西方发达国家多。";
            answernum = "B";
        } else if (helper.getAdapterPosition() == 3) {
            answer = "西方发达国家大学生创业成功率不比中国大学生高。";
            answernum = "C";
        } else if (helper.getAdapterPosition() == 4) {
            answer = "西方发达国家的大学生更喜欢挑战自我。";
            answernum = "D";
        } else if (helper.getAdapterPosition() == 5) {
            answer = "西方发达国家富裕的人多，大学生有更多的创业资金。";
            answernum = "E";
        }

        helper.setText(R.id.tv_answer, item.getAnswer() + answer)
                .setText(R.id.tv_answer_sel, answernum);
    }
}

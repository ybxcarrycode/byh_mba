package com.xszj.mba.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xszj.mba.R;
import com.xszj.mba.activity.QuestionDetailActivity;
import com.xszj.mba.bean.AnswerBean;
import com.xszj.mba.bean.QuestionBean;

import java.util.List;


/**
 * Created by swh on 2017/12/18.
 * 题adapter
 */

public class QuestionAdapter extends BaseQuickAdapter<QuestionBean, BaseViewHolder> {

    private ImageView iv_answer;
    private TextView tv_question;
    private TextView tv_question_type;
    private TextView tv_question_position;
    private TextView tv_offer_answer;
    private TextView tv_current_answer;
    private TextView tv_user_answer;

    public QuestionAdapter(@Nullable List<QuestionBean> data) {
        super(R.layout.adapter_question, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final QuestionBean item) {

        // 设置答案的Recy模式
        RecyclerView recyclerView = helper.getView(R.id.recy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //获取这题的所有选项
        List<AnswerBean> answerS = ((QuestionDetailActivity) mContext).getAnswerS(item.getAId());
        //查询这题学生答案，
        final int stuanswerId = ((QuestionDetailActivity) mContext).quaryAnswer(item.getAId());
        Log.e("dddddddd222", stuanswerId + "//");
        // 设置答案的Recy的adapter
        final AnswerAdapter answerAdapter = new AnswerAdapter(answerS);
        recyclerView.setAdapter(answerAdapter);

        //头部布局
        RelativeLayout answerAdaHeadView = (RelativeLayout) View.inflate(mContext, R.layout.adapter_answer_header, null);
        answerAdapter.addHeaderView(answerAdaHeadView);

        iv_answer = (ImageView) answerAdaHeadView.findViewById(R.id.iv_answer);
        tv_question = (TextView) answerAdaHeadView.findViewById(R.id.tv_question);
        tv_question_type = (TextView) answerAdaHeadView.findViewById(R.id.tv_question_type);
        tv_question_position = (TextView) answerAdaHeadView.findViewById(R.id.tv_question_position);
        tv_question.setText(item.getQuerstion());
        tv_question_position.setText(helper.getAdapterPosition() + 1 + "/24");
        tv_question_type.setText("逻辑思维");

        Log.e("dddddddd111", item.getACurId() + "///" + item.getAStuId() + "//" + item.getAId() + "///" + item.getOfferAnswer());

        //底部布局
        LinearLayout answerAdaBottomView = (LinearLayout) View.inflate(mContext, R.layout.adapter_answer_current, null);
        tv_current_answer = (TextView) answerAdaBottomView.findViewById(R.id.tv_current_answer);
        tv_user_answer = (TextView) answerAdaBottomView.findViewById(R.id.tv_user_answer);
        tv_offer_answer = (TextView) answerAdaBottomView.findViewById(R.id.tv_offer_answer);

        //tv_current_answer.setText(item.getACurId() + "");
        tv_current_answer.setText("C");
        tv_offer_answer.setText(item.getOfferAnswer());
        //判断学生答案对错，这里是求余
        if (stuanswerId != -1) {
            answerAdapter.addFooterView(answerAdaBottomView);
            if (stuanswerId == item.getACurId()) {
                iv_answer.setVisibility(View.VISIBLE);
                //已选答案，答案正确设置选择的item
                tv_user_answer.setText(Html.fromHtml("你的答案是: " + "<font color=#0091EA>" + "C" + "</font>"));
                answerAdapter.setSelectPosition(stuanswerId + 1, true);
            } else {
                //已选答案，答案错误设置选择的item
                iv_answer.setVisibility(View.GONE);
                tv_user_answer.setText(Html.fromHtml("你的答案是: " + "<font color=#FF7676>" + "B" + "</font>"));
                answerAdapter.setSelectPosition(stuanswerId + 1, false);
            }
        } else {
            iv_answer.setVisibility(View.GONE);
        }

        answerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (stuanswerId == -1) {
                    //保存学生答案
                    ((QuestionDetailActivity) mContext).updataAnswer(item.getAId(), position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "答案已选", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}

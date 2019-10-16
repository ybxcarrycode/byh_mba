package com.xszj.mba.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.activity.EvaluateDetailActivity;
import com.xszj.mba.activity.OpenClassLivingActivity;
import com.xszj.mba.adapter.TeacherDetailCommentAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.bean.MbaCommentListBean;
import com.xszj.mba.bean.TeacherDetailFeagmentBean;
import com.xszj.mba.bean.TeacherDetailFeagmentBean.DataBean.LiveVideoDetailBean;
import com.xszj.mba.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */

public class TeacherDetailLivingFragment extends BaseFragment implements Loadable {
    private Context mContext;
    private TextView tv_about_teach, tv_more_comment;
    private ImageView home_openclass_open, iv_back;
    private boolean isFirst = true;
    private NoScrollListView teacher_detail_listview;
    private String liveVideoId;
    private ImageView img_head;
    private TextView tv_tea_name, tv_tea_type;
    private List<String> commentList = new ArrayList<>();
    private TeacherDetailCommentAdapter teacherDetailCommentAdapter;
    private LiveVideoDetailBean liveVideoDetailBean;
    private TeacherDetailFeagmentBean teacherDetailFeagmentBean;

    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_teacher_detail, container, false);

    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();

        teacherDetailFeagmentBean = OpenClassLivingActivity.teacherDetailFeagmentBean;
    }

    @Override
    protected void bindViews(View view) {
        tv_about_teach = (TextView) view.findViewById(R.id.tv_about_teach);
        home_openclass_open = (ImageView) view.findViewById(R.id.home_openclass_open);
        teacher_detail_listview = (NoScrollListView) view.findViewById(R.id.teacher_detail_listview);
        teacher_detail_listview.setFocusable(false);
        tv_more_comment = (TextView) view.findViewById(R.id.tv_more_comment);

        img_head = (ImageView) view.findViewById(R.id.img_head);
        tv_tea_name = (TextView) view.findViewById(R.id.tv_tea_name);
        tv_tea_type = (TextView) view.findViewById(R.id.tv_tea_type);
    }

    @Override
    protected void initViews() {


    }

    @Override
    protected void initListeners() {
        home_openclass_open.setOnClickListener(this);
        tv_more_comment.setOnClickListener(this);
    }


    @Override
    protected void getDateForService() {
        super.getDateForService();

        if (null == teacherDetailFeagmentBean) {
            return;
        }

        if (teacherDetailFeagmentBean.getReturnCode().equals("0")) {
            liveVideoDetailBean = teacherDetailFeagmentBean.getData().getLiveVideoDetail();
            ImageLoader.getInstance().displayImage(liveVideoDetailBean.getTeacherPersonalCover(), img_head, NimApplication.imageOptions);
            tv_tea_name.setText(liveVideoDetailBean.getLiveName());
            tv_tea_type.setText(liveVideoDetailBean.getTeacherTitle());
            tv_about_teach.setText(liveVideoDetailBean.getTeacherPersonalIntroduce());

            List<MbaCommentListBean> mbaCommentListBeanList = teacherDetailFeagmentBean.getData().getMbaCommentList();
            if (null != mbaCommentListBeanList && mbaCommentListBeanList.size() > 0) {
                teacherDetailCommentAdapter = new TeacherDetailCommentAdapter(mContext, mbaCommentListBeanList);
                teacher_detail_listview.setAdapter(teacherDetailCommentAdapter);
            }

        }

    }

    @Override
    public <T> void onLoad(T t, int type) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more_comment:
                mContext.startActivity(new Intent(mContext, EvaluateDetailActivity.class).putExtra("expertUuId", liveVideoDetailBean.getUserId()));
                break;

            case R.id.home_openclass_open:
                Log.e("dddddd", "ssssss");
                if (isFirst) {
                    isFirst = false;
                    tv_about_teach.setEllipsize(null);
                    tv_about_teach.setSingleLine(isFirst);
                    home_openclass_open.setImageResource(R.drawable.home_open_up);
                } else {
                    isFirst = true;
                    tv_about_teach.setEllipsize(TextUtils.TruncateAt.END);
                    tv_about_teach.setLines(4);
                    home_openclass_open.setImageResource(R.drawable.home_open_down);
                }
                break;

            default:
                break;
        }
    }
}

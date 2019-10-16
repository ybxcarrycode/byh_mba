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
import com.xszj.mba.activity.RecommendVedioPlayActivity;
import com.xszj.mba.adapter.TeacherDetailCommentAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.bean.CourseDetailBean;
import com.xszj.mba.bean.MbaCommentListBean;
import com.xszj.mba.view.NoScrollListView;


import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */

public class TeacherDetailFragment extends BaseFragment implements Loadable {
    private Context mContext;
    private TextView tv_about_teach, tv_more_comment;
    private ImageView home_openclass_open;
    private boolean isFirst = true;
    private NoScrollListView teacher_detail_listview;
    private ImageView img_head;
    private TextView tv_tea_name, tv_tea_type;
    private TeacherDetailCommentAdapter teacherDetailCommentAdapter;
    private CourseDetailBean.DataBean.TeacherInfoBean teacherInfoBean;
    private CourseDetailBean courseDetailBean;

    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_teacher_detail, container, false);

    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();

        courseDetailBean = RecommendVedioPlayActivity.courseDetailBean;
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

        if (null == courseDetailBean) {
            return;
        }

        if (courseDetailBean.getReturnCode().equals("0")) {
            teacherInfoBean = courseDetailBean.getData().getTeacherInfo();
            ImageLoader.getInstance().displayImage(teacherInfoBean.getTeacherPersonalCover(), img_head, NimApplication.imageOptions);
            tv_tea_name.setText(teacherInfoBean.getNickName());
            tv_tea_type.setText(teacherInfoBean.getTeacherTitle());
            tv_about_teach.setText(teacherInfoBean.getTeacherPersonalIntroduce());

            List<MbaCommentListBean> mbaCommentListBeanList = courseDetailBean.getData().getMbaCommentList();
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
                mContext.startActivity(new Intent(mContext, EvaluateDetailActivity.class).putExtra("expertUuId", teacherInfoBean.getUserId()));
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

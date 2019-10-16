package com.xszj.mba.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.bean.AboutSchoolDetBean;
import com.xszj.mba.utils.CircleImageView;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;

/**
 * Created by Ybx on 2017/1/6.
 */

public class AboutSchoolFragment extends BaseFragment {

    //    protected XRecyclerView xrecyclerview;
//    protected LinearLayout headView;
//    protected List<String> list = new ArrayList<>();
//    protected AboutSchTimeTableAdapter adapter;

    protected Context context;
    protected CircleImageView img_school;
    protected TextView tv_school_name;
    protected TextView tv_study_costs;
    protected TextView tv_people_num;
    protected TextView tv_source_of_students;
    protected TextView tv_major_field;
    protected TextView tv_item_classification;
    protected TextView tv_have_interview;
    protected ImageView img_timetable;

    protected String academyId = null;

    protected AboutSchoolDetBean detBean;


    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        return inflater.inflate(R.layout.header_fragment_about_school, container, false);
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        Bundle bundle = getArguments();
        academyId = bundle.getString("academyId");
    }

    @Override
    protected void bindViews(View view) {
//        xrecyclerview = (XRecyclerView) view.findViewById(R.id.xrecyclerview);
//        headView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.header_fragment_about_school, null);

        img_school = (CircleImageView) view.findViewById(R.id.img_school);
        tv_school_name = (TextView) view.findViewById(R.id.tv_school_name);
        tv_people_num = (TextView) view.findViewById(R.id.tv_people_num);
        tv_source_of_students = (TextView) view.findViewById(R.id.tv_source_of_students);
        tv_study_costs = (TextView) view.findViewById(R.id.tv_study_costs);
        tv_major_field = (TextView) view.findViewById(R.id.tv_major_field);
        tv_item_classification = (TextView) view.findViewById(R.id.tv_item_classification);
        tv_have_interview = (TextView) view.findViewById(R.id.tv_have_interview);
        img_timetable = (ImageView) view.findViewById(R.id.img_timetable);
    }

    @Override
    protected void initViews() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        xrecyclerview.setLayoutManager(linearLayoutManager);
//        xrecyclerview.setPullRefreshEnabled(false);
//        xrecyclerview.addHeaderView(headView);
//        list.clear();
//        adapter = new AboutSchTimeTableAdapter(list, getActivity());
//        xrecyclerview.setAdapter(adapter);
        showProgressDialog();

        loadDate();
    }


    protected void setDate() {
        AboutSchoolDetBean.DataBean date = detBean.getData();
        ImageLoader.getInstance().displayImage("" + date.getAcademyLogo(), img_school, NimApplication.imageOptions);
        ImageLoader.getInstance().displayImage("" + date.getSignupTimesImg(), img_timetable, NimApplication.imageOptions);
        tv_school_name.setText(date.getAcademyName());
        tv_people_num.setText("招生人数: " + date.getRecruitStudentsNumber());
        tv_study_costs.setText("学习费用: " + date.getLearnCost());
        tv_source_of_students.setText("生源分类: " + date.getPupilsClassify());
        tv_major_field.setText("专业方向: " + date.getMajorDirection());
        tv_item_classification.setText("项目类别: " + date.getProjectCategory());
        if ("1".equals(date.getIsAdvanceInterview())) {
            tv_have_interview.setText("是否开设提前面试: " + "是");
        } else {
            tv_have_interview.setText("是否开设提前面试: " + "否");
        }
    }

    @Override
    protected void initListeners() {
//        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
//            @Override
//            public void onItemClick(View view, int position) {
//                showToast("item" + position);
//            }
//        });
//
//        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//            }
//
//            @Override
//            public void onLoadMore() {
//                int size = list.size();
//                Log.e("dssdADA", size + "");
//                if (size > 0) {
//                    loadDate("" + list.get(size - 1).getNewsId());
//                }else{
//                xrecyclerview.loadMoreComplete();
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {

    }


    private void loadDate() {
        final String url1 = ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/mba/school/query/academy_introduction.json?academyId=" + academyId;
        Log.e("dssdADA", url1);

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, url1, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                dismissProgressDialog();
                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }
                detBean = JsonUtil.parseJsonToBean(result, AboutSchoolDetBean.class);

                if (null == detBean || !"0".equals(detBean.getReturnCode())) {
                    return;
                }

                setDate();

            }

            @Override
            public void onFailure(HttpException e, String s) {
                dismissProgressDialog();
                showToast("网络不给力，请重试..");
            }
        });


    }

}

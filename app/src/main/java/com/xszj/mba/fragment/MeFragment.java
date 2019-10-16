package com.xszj.mba.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easefun.polyvsdk.demo.download.PolyvDownloadListActivity;
import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.activity.CollectActity;
import com.xszj.mba.activity.FollowTeacherActivity;
import com.xszj.mba.activity.FoucsBbsActivity;
import com.xszj.mba.activity.LearnRateActivity;
import com.xszj.mba.activity.LoginNewActivity;
import com.xszj.mba.activity.MyAnswerActivity;
import com.xszj.mba.activity.MyQuestionActivity;
import com.xszj.mba.activity.PerfectUserInfoActivity;
import com.xszj.mba.activity.QuestionBankActivity;
import com.xszj.mba.activity.SetingActivity;
import com.xszj.mba.activity.SignInActivity;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.utils.SharedPreferencesUtils;
import com.xszj.mba.view.GlobalTitleView;


/**
 * Created by pc on 2016/1/7.
 * 我
 */
public class MeFragment extends BaseFragment {

    private LinearLayout ll_learn_rate, ll_follow_teacher, ll_my_collect, ll_my_question, ll_my_answer
            ,ll_my_downlist, ll_my_center,ll_my_kaoquan;
    private Context mContext;
    private RelativeLayout rl_no_login;
    private LinearLayout yes_login;
    private TextView tv_perfect_info;
    private GlobalTitleView titleView;
    private Button btn_login, btn_regist;
    private ImageView image_head,iv_type;
    private TextView tv_me_name,tv_vip_type,tv_me_school;
    private String nickName,headPic,infoType,schoolName;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    protected void bindViews(View view) {

        titleView = (GlobalTitleView) view.findViewById(R.id.globalTitleView);

        rl_no_login = (RelativeLayout) view.findViewById(R.id.rl_no_login);
        yes_login = (LinearLayout) view.findViewById(R.id.yes_login);

        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_regist = (Button) view.findViewById(R.id.btn_regist);
        tv_perfect_info = (TextView) view.findViewById(R.id.tv_perfect_info);
        ll_learn_rate = (LinearLayout) view.findViewById(R.id.ll_learn_rate);

        image_head = (ImageView) view.findViewById(R.id.image_head);
        tv_me_name = (TextView) view.findViewById(R.id.tv_me_name);
        iv_type = (ImageView) view.findViewById(R.id.iv_type);
        tv_vip_type = (TextView) view.findViewById(R.id.tv_vip_type);
        tv_me_school = (TextView)view.findViewById(R.id.tv_me_school);

        ll_follow_teacher = (LinearLayout) view.findViewById(R.id.ll_follow_teacher);
        ll_my_collect = (LinearLayout) view.findViewById(R.id.ll_my_collect);
        ll_my_question = (LinearLayout) view.findViewById(R.id.ll_my_question);
        ll_my_answer = (LinearLayout) view.findViewById(R.id.ll_my_answer);
        ll_my_center = (LinearLayout) view.findViewById(R.id.ll_my_center);
        ll_my_downlist = (LinearLayout)view.findViewById(R.id.ll_my_downlist);
        ll_my_kaoquan = (LinearLayout) view.findViewById(R.id.ll_my_kaoquan);

    }

    @Override
    protected void initViews() {
        titleView.setTitle("我的");

    }

    @Override
    protected void initListeners() {
        btn_login.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
        tv_perfect_info.setOnClickListener(this);
        ll_learn_rate.setOnClickListener(this);
        ll_follow_teacher.setOnClickListener(this);
        ll_my_collect.setOnClickListener(this);
        ll_my_question.setOnClickListener(this);
        ll_my_answer.setOnClickListener(this);
        ll_my_center.setOnClickListener(this);
        ll_my_downlist.setOnClickListener(this);
        ll_my_kaoquan.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (null != NimApplication.user && !NimApplication.user.equals("")) {
            yes_login.setVisibility(View.VISIBLE);
            rl_no_login.setVisibility(View.GONE);
        } else {
            rl_no_login.setVisibility(View.VISIBLE);
            yes_login.setVisibility(View.GONE);
        }
        headPic = SharedPreferencesUtils.getProperty(mContext,"headPic");
        nickName = SharedPreferencesUtils.getProperty(mContext,"nickName");
        infoType = SharedPreferencesUtils.getProperty(mContext,"infoType");
        schoolName = SharedPreferencesUtils.getProperty(mContext,"schoolName");
        ImageLoader.getInstance().displayImage(headPic,image_head,NimApplication.imageOptions);
        tv_me_name.setText(nickName);
        tv_me_school.setVisibility(View.GONE);
        if (schoolName==null || schoolName.equals("")){
            tv_me_school.setText("还没有选择院校");
        }else{
            tv_me_school.setText(schoolName);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(mContext, LoginNewActivity.class));
                //startActivity(new Intent(mContext, QuestionBankActivity.class));
                break;
            case R.id.btn_regist:

                startActivity(new Intent(mContext, SignInActivity.class));
                break;

            case R.id.tv_perfect_info:
                startActivity(new Intent(mContext, PerfectUserInfoActivity.class));
                break;

            //学习进度
            case R.id.ll_learn_rate:
                if (null == NimApplication.user ||  NimApplication.user.equals("")) {
                    showToast("请先登陆或注册");
                } else {
                    startActivity(new Intent(mContext, LearnRateActivity.class));
                }
                break;

            //关注名师
            case R.id.ll_follow_teacher:
                if (null == NimApplication.user ||  NimApplication.user.equals("")) {
                    showToast("请先登陆或注册");
                } else {
                    startActivity(new Intent(mContext, FollowTeacherActivity.class));
                }
                break;
            //我的收藏
            case R.id.ll_my_collect:
                if (null == NimApplication.user ||  NimApplication.user.equals("")) {
                    showToast("请先登陆或注册");
                } else {
                    startActivity(new Intent(mContext, CollectActity.class));
                }
                break;
            //我的提问
            case R.id.ll_my_question:
                if (null == NimApplication.user ||  NimApplication.user.equals("")) {
                    showToast("请先登陆或注册");
                } else {
                    startActivity(new Intent(mContext, MyQuestionActivity.class));
                }
                break;
            //我的回答
            case R.id.ll_my_answer:
                if (null == NimApplication.user ||  NimApplication.user.equals("")) {
                    showToast("请先登陆或注册");
                } else {
                    startActivity(new Intent(mContext, MyAnswerActivity.class));
                }
                break;

            //关注考圈
            case R.id.ll_my_kaoquan:
                if (null == NimApplication.user ||  NimApplication.user.equals("")) {
                    showToast("请先登陆或注册");
                } else {
                    startActivity(new Intent(mContext, FoucsBbsActivity.class));
                }
                break;

            //我的下载
            case R.id.ll_my_downlist:
                if (null == NimApplication.user ||  NimApplication.user.equals("")) {
                    showToast("请先登陆或注册");
                } else {
                    startActivity(new Intent(mContext, PolyvDownloadListActivity.class));
                }
                break;

            //设置
            case R.id.ll_my_center:
                if (null == NimApplication.user ||  NimApplication.user.equals("")) {
                    showToast("请先登陆或注册");
                } else {
                    Intent intent10 = new Intent(mContext,
                            SetingActivity.class);
                    startActivity(intent10);
               /* Intent intent10 = new Intent(mContext,
                        OpenClassLivingActivity.class);
                intent10.putExtra("uid","edvbtqp7fj");
                intent10.putExtra("cid","101384");
                startActivity(intent10);*/
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}

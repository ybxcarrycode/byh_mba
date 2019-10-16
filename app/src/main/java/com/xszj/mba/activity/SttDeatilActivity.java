package com.xszj.mba.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.net.nim.demo.session.SessionHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.adapter.TeacherDetailCommentAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.bean.ExperDetailBean;
import com.xszj.mba.bean.ExperDetailBean.DataBean.ExpertDetailBean;
import com.xszj.mba.bean.MbaCommentListBean;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.view.NoScrollListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Ybx on 2016/12/14.
 */

public class SttDeatilActivity extends BaseActivity {

    private Context mContext;
    private TextView tv_following;
    private Button btn_talk;
    private TextView tv_about_teach, tv_more_comment, tv_tea_type, tv_tea_name;
    private ImageView home_openclass_open, iv_back, img_head;
    private boolean isFirst = true;
    private NoScrollListView teacher_detail_listview;
    private TeacherDetailCommentAdapter teacherDetailCommentAdapter;
    private String expertId = "";
    private String respose;
    private String memberId;
    private ExpertDetailBean expertDetailBean;
    private boolean isFollow= true;

    @Override
    protected int getContentViewResId() {
        mContext = SttDeatilActivity.this;

        if (NimApplication.user == null || NimApplication.user.equals("")) {
            memberId = "-1";
        } else {
            memberId = NimApplication.user;
        }

        return R.layout.activity_stt_detail;
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        expertId = getIntent().getStringExtra("expertId");

    }

    @Override
    protected void bindViews() {
        tv_following = (TextView) findViewById(R.id.tv_following);
        btn_talk = (Button) findViewById(R.id.btn_talk);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        img_head = (ImageView) findViewById(R.id.img_head);
        tv_tea_name = (TextView) findViewById(R.id.tv_tea_name);
        tv_tea_type = (TextView) findViewById(R.id.tv_tea_type);
        tv_about_teach = (TextView) findViewById(R.id.tv_about_teach);
        home_openclass_open = (ImageView) findViewById(R.id.home_openclass_open);
        teacher_detail_listview = (NoScrollListView) findViewById(R.id.teacher_detail_listview);
        teacher_detail_listview.setFocusable(false);
        tv_more_comment = (TextView) findViewById(R.id.tv_more_comment);

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void getDateForService() {
        super.getDateForService();

        showProgressDialog();
        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/consult/expertDetail.json?";
        Log.e("dddddd", url);
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", memberId);
        params.addBodyParameter("expertId", expertId);

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);

        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                dismissProgressDialog();
                respose = responseInfo.result;

                Log.e("dddddd", respose);
                requestServer(respose);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dismissProgressDialog();
            }
        });

    }

    private void requestServer(String content) {
        if (null == content || content.equals("")) {
            return;
        }

        ExperDetailBean experDetailBean = JsonUtil.parseJsonToBean(respose, ExperDetailBean.class);
        if (null == experDetailBean) {
            return;
        }
        if (experDetailBean.getReturnCode().equals("0")) {
            expertDetailBean = experDetailBean.getData().getExpertDetail();
            ImageLoader.getInstance().displayImage(expertDetailBean.getTeacherPersonalCover(), img_head, NimApplication.imageOptions);
            tv_tea_name.setText(expertDetailBean.getNickName());
            tv_tea_type.setText(expertDetailBean.getTeacherTitle());
            tv_about_teach.setText(expertDetailBean.getTeacherPersonalIntroduce());
            if (experDetailBean.getData().getIsFollow().equals("1")) {
                tv_following.setText("已关注");
                isFollow=true;
                Drawable drawable = getResources().getDrawable(R.mipmap.follow_yes);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_following.setCompoundDrawables(drawable, null, null, null);//icon_tongji_teacher
                tv_following.setCompoundDrawablePadding(5); //设置图片和text之间的间距

            } else {
                tv_following.setText("关注");
                isFollow=false;
                Drawable drawable = getResources().getDrawable(R.mipmap.follow_no);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tv_following.setCompoundDrawables(drawable, null, null, null);//icon_tongji_teacher
                tv_following.setCompoundDrawablePadding(5); //设置图片和text之间的间距

            }
            List<MbaCommentListBean> commentListBeanList = experDetailBean.getData().getCommentList();
            if (null != commentListBeanList || commentListBeanList.size() > 0) {
                tv_more_comment.setVisibility(View.VISIBLE);
                tv_more_comment.setText("查看更多评价");
                teacherDetailCommentAdapter = new TeacherDetailCommentAdapter(mContext, commentListBeanList);
                teacher_detail_listview.setAdapter(teacherDetailCommentAdapter);
            } else {
                tv_more_comment.setText("暂无评价");
                tv_more_comment.setVisibility(View.VISIBLE);
            }
        } else {
            tv_more_comment.setVisibility(View.GONE);
            showToast(experDetailBean.getReturnMsg());
        }
    }

    @Override
    protected void initListeners() {
        tv_following.setOnClickListener(this);
        btn_talk.setOnClickListener(this);
        home_openclass_open.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_more_comment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_following:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    //请求网络
                    likeToServer(isFollow);
                }
                break;

            case R.id.btn_talk:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    SessionHelper.startP2PSession(mContext, expertDetailBean.getImUser());
                }
                break;

            case R.id.tv_more_comment:
                mContext.startActivity(new Intent(mContext, EvaluateDetailActivity.class).putExtra("expertUuId",expertId));
                break;

            case R.id.iv_back:
                finish();
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

    // 请求网络 关注
    public void likeToServer(final boolean type) {
        String url;
        if (type){
            url= ServiceUtils.SERVICE_ABOUT_HOME + "/v1/consult/cancleFocusExpert.json?";
        }else{
            url= ServiceUtils.SERVICE_ABOUT_HOME + "/v1/consult/focusExpert.json?";
        }



        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("expertId", expertId);

        new HttpUtils().send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("dddddd", responseInfo.result);

                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    String returnCode = object.optString("returnCode", null);
                    Drawable drawable ;
                    if (returnCode.equals("0")) {
                        if (type){
                            isFollow=false;
                            tv_following.setText("关注");
                            drawable = getResources().getDrawable(R.mipmap.follow_no);
                        }else{
                            isFollow=true;
                            tv_following.setText("已关注");
                            drawable = getResources().getDrawable(R.mipmap.follow_yes);
                        }
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        tv_following.setCompoundDrawables(drawable, null, null, null);//icon_tongji_teacher
                        tv_following.setCompoundDrawablePadding(5); //设置图片和text之间的间距
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}

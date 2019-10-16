package com.xszj.mba.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.utils.ServiceUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/1/6.
 * 写评价的界面
 */

public class EvaluteTeacherActivity extends BaseActivity{

    private ImageButton main_top_left;
    private TextView main_top_right;
    private EditText et_user_freeback_content;
    private String content;
    private String teacherId;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_evaluate_teacher;
    }


    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        teacherId = getIntent().getStringExtra("expertUuId");

        Log.e("ddddd",teacherId+"");
    }

    @Override
    protected void bindViews() {
        main_top_left = (ImageButton) findViewById(R.id.main_top_left);
        main_top_right = (TextView) findViewById(R.id.main_top_right);
        et_user_freeback_content = (EditText) findViewById(R.id.et_user_freeback_content);
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initListeners() {
        main_top_left.setOnClickListener(this);
        main_top_right.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.main_top_left:
                hideSoftInput();
                finish();
                break;

            case R.id.main_top_right:
                content = et_user_freeback_content.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    showToast("请输入你想说的吧");
                }else{
                    commitEvalute(content);
                }
                break;
        }
    }

    private void commitEvalute(String content) {
        showProgressDialog();
        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/consult/commentTeacher.json?";

        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("teacherId", teacherId);
        params.addBodyParameter("commentContent", content);

        new HttpUtils().send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("dddddd", responseInfo.result);
                dismissProgressDialog();
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    String returnCode = object.optString("returnCode", null);
                    if (returnCode.equals("0")) {
                        et_user_freeback_content.setText("");
                        Intent intent = new Intent();
                        intent.setAction("evaluteSuccess");
                        sendBroadcast(intent);
                        finish();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dismissProgressDialog();
            }
        });

    }
}

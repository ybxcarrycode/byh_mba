package com.xszj.mba.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.login.LoginListener;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.bean.ResponseRegBean;
import com.xszj.mba.utils.FormatCheckUtils;
import com.xszj.mba.utils.IntentTagUtil;
import com.xszj.mba.utils.NmLoginUtils;
import com.xszj.mba.utils.OTPCountDown;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.SharedPreferencesUtils;
import com.xszj.mba.utils.StringUtils;
import com.xszj.mba.view.GlobalTitleView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Ybx on 2016/11/28.
 * <p>
 * 注册页面
 */
public class ForgetPasswordActivity extends BaseActivity {

    protected ResponseRegBean responseRegBean;

    protected OTPCountDown otpCountDown;
    protected long OTP_WAIT_INTERVAL = 1000;
    protected long OTP_WAIT_TIME = 60000;


    protected GlobalTitleView titleView;
    protected Button btn_getsms;
    protected Button btn_queren;

    protected EditText edt_login_num;
    protected EditText edt_login_sms;
    protected EditText edt_login_pass;
    protected EditText edt_login_pass_again;


    protected String phoneQ;
    protected String smsQ;
    protected String nameQ;
    protected String passQ;
    protected String passQ_again;


    private void showSmsError() {
        edt_login_sms.setError(getResources().getString(
                R.string.format_error_otp));
        edt_login_sms.requestFocus();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册短信回调

    }

    @Override
    protected void beforeContentView() {
        super.beforeContentView();
        isTouchHideKeyBoard = true;
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_forget_password;
    }


    @Override
    protected void initTitle() {
        super.initTitle();
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        titleView.setTitle("找回密码");
        titleView.setBackVisible(true);
        titleView.setTitleLeftTvShow(false);

        titleView.setBackIconImage(R.mipmap.icon_back_blue);
        titleView.setTitleLeftTvColor(Color.parseColor("#bdbdbd"));
        titleView.setTitleLeftTvSize(12);

        titleView.setLeftDiyBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput();
                finish();
            }
        });


    }

    @Override
    protected void bindViews() {
        btn_getsms = (Button) findViewById(R.id.btn_getsms);
        btn_queren = (Button) findViewById(R.id.btn_queren);

        edt_login_num = (EditText) findViewById(R.id.edt_login_num);
        edt_login_sms = (EditText) findViewById(R.id.edt_login_sms);
        edt_login_pass = (EditText) findViewById(R.id.edt_login_pass);
        edt_login_pass_again = (EditText) findViewById(R.id.edt_login_pass_again);
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initListeners() {
        btn_getsms.setOnClickListener(this);
        btn_queren.setOnClickListener(this);

        edt_login_num.addTextChangedListener(new SignEditViewListener());
        edt_login_sms.addTextChangedListener(new SignEditViewListener());
        edt_login_pass.addTextChangedListener(new SignEditViewListener());
        edt_login_pass_again.addTextChangedListener(new SignEditViewListener());
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_getsms:
                String phone = edt_login_num.getText().toString();
                /**校验手机号
                 */
                if (FormatCheckUtils.isMobileNum(phone)) {
                    btn_getsms.setEnabled(false);
                    getSmsForService(phone);
                } else {
                    edt_login_num.setError("请输入正确手机号");
                    edt_login_num.requestFocus();
                }

                break;
            case R.id.btn_queren:
                phoneQ = edt_login_num.getText().toString();
                smsQ = edt_login_sms.getText().toString();
                passQ = edt_login_pass.getText().toString();
                passQ_again = edt_login_pass_again.getText().toString();
                /**校验手机号
                 */
                if (!FormatCheckUtils.isMobileNum(phoneQ)) {
                    edt_login_num.setError("请输入正确手机号");
                    edt_login_num.requestFocus();
                    return;
                }

                /**校验密码
                 */
                if (!FormatCheckUtils.isPassword(passQ)) {
                    edt_login_pass.setError(getResources().getString(
                            R.string.format_error_password));
                    edt_login_pass.requestFocus();
                    return;
                }

                /**校验 两次密码是否一样
                 */
                if (!passQ_again.equals(passQ)) {
                    edt_login_pass_again.setError(getResources().getString(
                            R.string.format_error_password_again));
                    edt_login_pass_again.requestFocus();
                    return;
                }

                /**OTP校验
                 */
                if (!FormatCheckUtils.isStuNumber(smsQ) && smsQ.length() != 7) {
                    showSmsError();
                    return;
                }
                getSignService();
                showProgressDialog();

                break;
            default:
                break;
        }
    }


    protected void getSignService() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("phone", "" + phoneQ);
        params.addBodyParameter("password", "" + passQ);
        params.addBodyParameter("code", "" + smsQ);
        Log.e("qwer", phoneQ+"/"+passQ+"/"+smsQ);
        final String url1 = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/user_info/forget_pass.json?";

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url1, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                dismissProgressDialog();

                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }
                try {
                    String returnCode;
                    String returnMsg;
                    JSONObject object = new JSONObject(result);

                    returnCode = object.getString("returnCode");
                    returnMsg = object.getString("returnMsg");

                    if ("0".equals(returnCode)) {
                        showToast("操作成功");
                        finish();
                    } else {
                        showToast("" + returnMsg);
                    }

                } catch (JSONException e) {
                    showToast("网络不给力,请重试...");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                showToast("网络不给力,请重试...");
                dismissProgressDialog();
            }
        });

    }

    protected void getSmsForService(String phone) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("phone", "" + phone);
        params.addBodyParameter("type", "2");

        final String url1 = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/user_info/get_id_code.json?";
        Log.e("qwer", url1);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url1,params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }
                Log.e("qwer", result);

                String code;
                String msg;
                try {
                    JSONObject obj = new JSONObject(result);
                    code = obj.getString("returnCode");
                    msg = obj.getString("returnMsg");
                    if ("0".equals(code)) {
                        otpCountDown = new OTPCountDown(btn_getsms, context,
                                OTP_WAIT_TIME,
                                OTP_WAIT_INTERVAL);
                        otpCountDown.start();
                        btn_getsms.setEnabled(false);
                    }
                    showToast(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                showToast("网络不给力,请重试...");
            }
        });

    }


    protected class SignEditViewListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.e("qwer", "asda");
            if (!StringUtils.isEmpty(edt_login_num.getText().toString())
                    && !StringUtils.isEmpty(edt_login_sms.getText().toString())
                    && !StringUtils.isEmpty(edt_login_pass.getText().toString())
                    && !StringUtils.isEmpty(edt_login_pass_again.getText().toString())) {
                btn_queren.setEnabled(true);
            } else {
                btn_queren.setEnabled(false);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}

package com.xszj.mba.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.system.ErrnoException;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
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
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.NmLoginUtils;
import com.xszj.mba.utils.OTPCountDown;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.SharedPreferencesUtils;
import com.xszj.mba.utils.StringUtils;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * Created by Ybx on 2016/11/28.
 * <p>
 * 注册页面
 */
public class SignInActivity extends BaseActivity {

    protected ResponseRegBean responseRegBean;

    protected OTPCountDown otpCountDown;
    protected long OTP_WAIT_INTERVAL = 1000;
    protected long OTP_WAIT_TIME = 60000;


    protected Button btn_getsms;
    protected Button btn_queren;
    protected TextView tv_user_protocol;

    protected EditText edt_login_num;
    protected EditText edt_login_sms;
    protected EditText edt_login_name;
    protected EditText edt_login_pass;
    private EditText edt_login_req;

    protected String phoneQ;
    protected String smsQ;
    private String reqQ;
    protected String smsForService;
    protected String nameQ;
    protected String passQ;

    private TextView tv_back;


    private void showSmsError() {
        edt_login_sms.setError(getResources().getString(
                R.string.format_error_otp));
        edt_login_sms.requestFocus();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void beforeContentView() {
        super.beforeContentView();
        isTouchHideKeyBoard = true;
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_signin;
    }


    @Override
    protected void initTitle() {
        super.initTitle();

    }

    @Override
    protected void bindViews() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        btn_getsms = (Button) findViewById(R.id.btn_getsms);
        btn_queren = (Button) findViewById(R.id.btn_queren);
        tv_user_protocol = (TextView) findViewById(R.id.tv_user_protocol);

        edt_login_num = (EditText) findViewById(R.id.edt_login_num);
        edt_login_sms = (EditText) findViewById(R.id.edt_login_sms);
        edt_login_req = (EditText)findViewById(R.id.edt_login_req);
        edt_login_name = (EditText) findViewById(R.id.edt_login_name);
        edt_login_pass = (EditText) findViewById(R.id.edt_login_pass);
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initListeners() {
        btn_getsms.setOnClickListener(this);
        btn_queren.setOnClickListener(this);
        tv_user_protocol.setOnClickListener(this);

        edt_login_num.addTextChangedListener(new SignEditViewListener());
        edt_login_sms.addTextChangedListener(new SignEditViewListener());
        edt_login_name.addTextChangedListener(new SignEditViewListener());
        edt_login_pass.addTextChangedListener(new SignEditViewListener());
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_back:
                hideSoftInput();
                finish();
                break;
            case R.id.tv_user_protocol:
                showToast("用户协议");
                break;
            case R.id.btn_getsms:
                String phone = edt_login_num.getText().toString();
                /**校验手机号
                 */
                if (TextUtils.isEmpty(phone)) {
                    edt_login_num.setError("手机号不能为空");
                    edt_login_num.requestFocus();
                    return;
                }
                if (FormatCheckUtils.isMobileNum(phone)) {
                    getSmsForService(phone);

                } else {
                    edt_login_num.setError("请输入正确手机号");
                    edt_login_num.requestFocus();
                }

                break;
            case R.id.btn_queren:
                phoneQ = edt_login_num.getText().toString();
                smsQ = edt_login_sms.getText().toString();
                reqQ = edt_login_req.getText().toString();
                nameQ = edt_login_name.getText().toString();
                passQ = edt_login_pass.getText().toString();
                /**校验手机号
                 */
                if (!FormatCheckUtils.isMobileNum(phoneQ)) {
                    edt_login_num.setError("请输入正确手机号");
                    edt_login_num.requestFocus();
                    return;
                }
                /**校验昵称
                 */
                if (!FormatCheckUtils.isName(nameQ)) {
                    edt_login_name.setError(getResources().getString(
                            R.string.format_error_name));
                    edt_login_name.requestFocus();
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

                /**OTP校验
                 */
                if (!FormatCheckUtils.isStuNumber(smsQ) && smsQ.length() != 7) {
                    showSmsError();
                    return;
                }
                showProgressDialog();
                getSignService();

                break;
            default:
                break;
        }
    }


    protected void getSmsForService(String phone) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("phone", "" + phone);
        params.addBodyParameter("type", "1");

        final String url1 = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/user_info/get_id_code.json?";
        Log.e("qwer", url1);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url1, params,new RequestCallBack<Object>() {
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
                        /**倒计时
                         */
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

    protected void getSignService() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("phone", "" + phoneQ);
        params.addBodyParameter("nickName", "" + nameQ);
        params.addBodyParameter("password", "" + passQ);
        params.addBodyParameter("code", "" + smsQ);
        params.addBodyParameter("inviteCode",reqQ);

        final String url1 = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/user_info/register_user.json?";

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url1, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }

                Log.e("qwer", result);
                responseRegBean = JsonUtil.parseJsonToBean(result, ResponseRegBean.class);

                if (null != responseRegBean) {
                    if ("0".equals(responseRegBean.getReturnCode())) {
                        NimApplication.user = responseRegBean.getData().getUserId();
                        String imUser = responseRegBean.getData().getImUserId();
                        String imPwd = responseRegBean.getData().getImUserPwd();
                        SharedPreferencesUtils.setProperty(SignInActivity.this, "memberID", responseRegBean.getData().getUserId());
                        NmLoginUtils.nmLogin(SignInActivity.this, imUser, imPwd);

//                        String name = responseRegBean.getData().getNickName();
//                        String memberID = responseRegBean.getData().getUserId();
//                        loginUmeng(name, memberID, "");
                        Intent intent = new Intent(context, PerfectUserInfoActivity.class);
                        intent.putExtra(IntentTagUtil.PERFECT_USER_START_FROM_SIGN, 1);
                        startActivity(intent);
                        finish();
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                                SharedPreferencesUtils.setProperty(context, "nickName", responseRegBean.getData().getNickName());
                                SharedPreferencesUtils.setProperty(context, "infoType", responseRegBean.getData().getUserType());
                            }
                        }.start();
                    } else {
                        showToast(responseRegBean.getReturnMsg() + "");
                        return;
                    }
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                showToast("网络不给力,请重试...");
                dismissProgressDialog();
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
                    && !StringUtils.isEmpty(edt_login_name.getText().toString())
                    && !StringUtils.isEmpty(edt_login_pass.getText().toString())) {
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


    protected void loginUmeng(String name, String id, String imgUrl) {
        //创建CommUser前必须先初始化CommunitySDK
        CommunitySDK sdk = CommunityFactory.getCommSDK(this);
        CommUser user = new CommUser();
        user.name = "" + name;
        user.id = "" + id;
        user.iconUrl = "" + imgUrl;
        sdk.loginToUmengServerBySelfAccount(this, user, new LoginListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(int stCode, CommUser commUser) {
                Log.e("tag", "login result is" + stCode);          //获取登录结果状态码
                if (ErrorCode.NO_ERROR == stCode) {
                    dismissProgressDialog();

                } else {
                    dismissProgressDialog();
                    showToast("网络不给力，请重试...");
                }
            }
        });


    }
}

package com.xszj.mba.activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Process;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.mob.tools.utils.UIHandler;
import com.net.nim.demo.NimApplication;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.nets.responses.PortraitUploadResponse;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.bean.LoginResBean;
import com.xszj.mba.utils.FormatCheckUtils;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.NmLoginUtils;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.SharedPreferencesUtils;
import com.xszj.mba.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;


/**
 * Created by Ybx on 2016/11/28.
 */
public class LoginNewActivity extends BaseActivity implements Handler.Callback, PlatformActionListener {

    private int count = 2;

    protected LoginResBean resBean;

    protected String phoneQ;
    protected String passQ;


    protected Button btn_queren;
    protected TextView tv_back;
    protected TextView tv_forget_pass;
    protected TextView tv_go_sign;

    protected EditText edt_login_num;
    protected EditText edt_login_pass;

    protected ImageView enterpassword_imageview;
    protected ImageView img_login_qq;
    protected ImageView img_login_weixin;

    private String useName, userid, useHeadPic, share, unionid;

    private static final int MSG_ACTION_CCALLBACK = 2;

    @Override
    protected void beforeContentView() {
        super.beforeContentView();
        isTouchHideKeyBoard = true;
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_login_new;
    }


    @Override
    protected void bindViews() {
        btn_queren = (Button) findViewById(R.id.btn_queren);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_forget_pass = (TextView) findViewById(R.id.tv_forget_pass);
        tv_go_sign = (TextView) findViewById(R.id.tv_go_sign);

        edt_login_num = (EditText) findViewById(R.id.edt_login_num);
        edt_login_pass = (EditText) findViewById(R.id.edt_login_pass);

        enterpassword_imageview = (ImageView) findViewById(R.id.enterpassword_imageview);
        img_login_qq = (ImageView) findViewById(R.id.img_login_qq);
        img_login_weixin = (ImageView) findViewById(R.id.img_login_weixin);

    }


    @Override
    protected void initViews() {
    }

    @Override
    protected void initListeners() {
        btn_queren.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_forget_pass.setOnClickListener(this);
        tv_go_sign.setOnClickListener(this);
        img_login_qq.setOnClickListener(this);
        img_login_weixin.setOnClickListener(this);

        edt_login_num.addTextChangedListener(new LoginEditViewListener());
        edt_login_pass.addTextChangedListener(new LoginEditViewListener());

        enterpassword_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏显示密码
                if (count % 2 == 0) {
                    // 显示密码
                    edt_login_pass.setInputType(1);
                    enterpassword_imageview.setImageResource(R.mipmap.icon_register_password_see);
                } else {
                    // 隐藏密码
                    edt_login_pass.setInputType(129);
                    enterpassword_imageview.setImageResource(R.mipmap.icon_register_password);
                }
                count++;
            }
        });

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_back:
                hideSoftInput();
                finish();
                /*startActivity(new Intent(context, PayDemoActivity.class));
                wxpay();*/
                break;
            case R.id.img_login_qq:
                Platform qqfd = ShareSDK.getPlatform(QQ.NAME);
                qqfd.setPlatformActionListener(this);
                if (qqfd.isValid ()){
                    qqfd.removeAccount();
                }
                qqfd.showUser(null);
                break;
            case R.id.img_login_weixin:
                Platform weixinfd = ShareSDK.getPlatform(Wechat.NAME);
                weixinfd.setPlatformActionListener(this);
                if (weixinfd.isValid ()){
                    weixinfd.removeAccount();
                }
                weixinfd.showUser(null);
                break;
            case R.id.btn_queren:
                phoneQ = edt_login_num.getText().toString();
                passQ = edt_login_pass.getText().toString();
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
                getLoginService();

                break;
            case R.id.tv_forget_pass:
                Intent intentFP = new Intent(context, ForgetPasswordActivity.class);
                startActivity(intentFP);
                break;
            case R.id.tv_go_sign:
                Intent intentGS = new Intent(context, SignInActivity.class);
                startActivity(intentGS);
                finish();
                break;
            default:
                break;
        }

    }

    private void wxpay() {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        msgApi.registerApp("wxac6d7dce2014301c");

        PayReq request = new PayReq();
        request.appId = "wxac6d7dce2014301c";
        request.partnerId = "1900000109";
        request.prepayId= "1101000000140415649af9fc314aa427";
        request.packageValue = "Sign=WXPay";
        request.nonceStr= "1101000000140429eb40476f8896f4c9";
        request.timeStamp= "1398746574";
        request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
        msgApi.sendReq(request);

    }

    // 回调
    @Override
    public void onCancel(Platform platform, int action) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onComplete(Platform platform, int action,
                           HashMap<String, Object> res) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;

        UIHandler.sendMessage(msg, this);
        System.out.println(res);
        //获取资料
        useName=platform.getDb().getUserName();//获取用户名字
        useHeadPic=platform.getDb().getUserIcon(); //获取用户头像
        userid =platform.getDb().getUserId();
        if (platform.getName().toString().equals("QQ")) {
            share = "0";
        }
        if (platform.getName().toString().equals("Wechat")) {
            share = "1";
        }
        /*String token = platform.getDb().getToken();
        String refresh_token111 = platform.getDb().get("refresh_token");
        String unionid = (String) res.get("unionid");*/

    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        t.printStackTrace();

        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);

        // 分享失败的统计
        ShareSDK.logDemoEvent(4, platform);
    }

    // 回调handleMessage
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                // 成功
                Toast.makeText(LoginNewActivity.this, "成功", Toast.LENGTH_SHORT).show();
                /**
                 * 传递检查第三方登陆所需参数
                 */

                String url =ServiceUtils.SERVICE_ABOUT_HOME+"/v1/tp/tpCheckLogin.json?";
                RequestParams params = new RequestParams();
                params.addBodyParameter("tpId",userid);
                params.addBodyParameter("tpNickName",useName);
                params.addBodyParameter("tpHeadPic",useHeadPic);
                params.addBodyParameter("tpType",share);
                Log.e("ddddd",userid+"=="+useName+"=="+useHeadPic+"==="+share);
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.send(HttpRequest.HttpMethod.POST, url, params,new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        try {
                            JSONObject object = new JSONObject(result);
                            String returnCode = object.optString("returnCode", null);
                            String returnMsg = object.optString("returnMsg", null);
                            //首次使用第三方登陆，没有绑定账号
                            if (returnCode.equals("0")){
                                resBean = JsonUtil.parseJsonToBean(result, LoginResBean.class);
                                pareData(resBean);
                            }else{
                                showToast(returnMsg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
                    }
                });
            }
            break;
            case 2: {
                // 失败
                Toast.makeText(LoginNewActivity.this, "失败", Toast.LENGTH_SHORT).show();

                String expName = msg.obj.getClass().getSimpleName();
                if ("WechatClientNotExistException".equals(expName)
                        || "WechatTimelineNotSupportedException".equals(expName)
                        || "WechatFavoriteNotSupportedException".equals(expName)) {
                    Toast.makeText(LoginNewActivity.this, "请安装微信客户端", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case 3: {
                // 取消
                Toast.makeText(LoginNewActivity.this, "取消····", Toast.LENGTH_SHORT)
                        .show();
            }
            break;
        }

        return false;
    }

    /**
     * 登陆  网络请求
     */
    protected void getLoginService() {
        showProgressDialog();
        RequestParams params = new RequestParams();
        params.addBodyParameter("phone", "" + phoneQ);
        params.addBodyParameter("password", "" + passQ);
        final String url1 = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/user_info/login_user.json?";

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url1, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }
                Log.e("dfgfg",result);

                resBean = JsonUtil.parseJsonToBean(result, LoginResBean.class);
                if (null != resBean) {
                    if ("0".equals(resBean.getReturnCode())) {

                       pareData(resBean);
                    } else {
                        dismissProgressDialog();
                        showToast(resBean.getReturnMsg() + "");
                        return;
                    }
                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                dismissProgressDialog();
                showToast("网络不给力，请重试...");
            }
        });

    }

    //解析请求下面的数据及保存
    private void pareData(final LoginResBean resBean) {
        String name = resBean.getData().getNickName();
        String memberID = resBean.getData().getUserId();
        String imgUrl = resBean.getData().getHeadPic();
        String imUser = resBean.getData().getImUserId();
        String imPwd = resBean.getData().getImUserPwd();
        NimApplication.user = memberID;
        SharedPreferencesUtils.setProperty(context, "nickName", resBean.getData().getNickName());
        SharedPreferencesUtils.setProperty(LoginNewActivity.this, "memberID", memberID);
        SharedPreferencesUtils.setProperty(context, "imUser", imUser);
        SharedPreferencesUtils.setProperty(context, "imPwd", imPwd);
        Log.e("dssdADA", "accout==" + imUser + "pwd=" + imPwd);
        NmLoginUtils.nmLogin(LoginNewActivity.this, imUser, imPwd);

        //loginUmeng(name, memberID, imgUrl);

        new Thread() {
            @Override
            public void run() {
                super.run();
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

                SharedPreferencesUtils.setProperty(context, "schoolName", resBean.getData().getWillSchool());
                SharedPreferencesUtils.setProperty(context, "headPic", resBean.getData().getHeadPic());
                SharedPreferencesUtils.setProperty(context, "infoType", resBean.getData().getUserType());
            }
        }.start();

        finish();
    }


    protected class LoginEditViewListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!StringUtils.isEmpty(edt_login_num.getText().toString())
                    && !StringUtils.isEmpty(edt_login_pass.getText().toString())) {
                btn_queren.setEnabled(true);

            } else {
                btn_queren.setEnabled(false);
            }
        }
    }


    protected void loginUmeng(String name, String id, final String imgUrl) {
        //创建CommUser前必须先初始化CommunitySDK
        final CommunitySDK sdk = CommunityFactory.getCommSDK(this);
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
                    sdk.updateUserProtrait(imgUrl, new Listeners.SimpleFetchListener<PortraitUploadResponse>() {
                        @Override
                        public void onComplete(PortraitUploadResponse portraitUploadResponse) {
                        }
                    });
                    dismissProgressDialog();
                    showToast("登录成功");
                    finish();
                } else {
                    dismissProgressDialog();
                    showToast("网络不给力，请重试...");
                }
            }
        });

    }


}

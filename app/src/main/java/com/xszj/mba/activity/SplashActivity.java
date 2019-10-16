package com.xszj.mba.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.xszj.mba.R;

import android.util.Log;

import com.xszj.mba.utils.FilesUtils;
import com.xszj.mba.consts.Mbaconsts;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.SharedPreferencesUtils;
import com.xszj.mba.utils.StreamUtils;
import com.xszj.mba.view.XsDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * *************************************************************************
 * <p/>
 * <pre></pre>
 *
 * @文件名称: WelcomeActivity.java
 * @包 路 径： com.yunshuxie.main
 * @类描述: 首页
 * @版本: V1.5
 * @创建人： wangzhan
 * @创建时间：2015-8-12 上午12:47:25
 * <p/>
 * *************************************************************************
 */
public class SplashActivity extends Activity {

    boolean isFirstIn = false;
    protected static final int MSG_UPDATE_DIALOG = 1;
    protected static final int MSG_UPDATE_FORCE_DIALOG = 2;
    protected static final int MSG_SERVER_ERROR = 3;
    protected static final int MSG_URL_ERROR = 4;
    protected static final int MSG_IO_ERROR = 5;
    protected static final int MSG_JSON_ERROR = 6;
    private String downloadUrl; // 下载地址
    private String isForce; //0:不升级 1提示升级 2强制升级
    private TextView tv_splash_plan;
    private String adviceMsg = "";
    private String isUp;//是否升级
    private Context context;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_FORCE_DIALOG:
                    showDialog("1", adviceMsg);
                    break;
                case MSG_UPDATE_DIALOG:
                    showDialog("2", adviceMsg);
                    break;
                case MSG_SERVER_ERROR:
                    init();
                    break;
                case MSG_URL_ERROR:
                    init();
                    break;
                case MSG_IO_ERROR:
                    init();
                    break;
                case MSG_JSON_ERROR:
                    init();
                    break;
                case Mbaconsts.HandlerConsts.GO_LOGIN:
                    goLogin();
                    break;
                case Mbaconsts.HandlerConsts.GO_GUIDE:
                    goGuide();
                    break;
                default:
                    break;

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        context = SplashActivity.this;

        update();  // 版本更新
        // init();
        tv_splash_plan = (TextView) findViewById(R.id.tv_splash_plan);
        ImageView iv_black = (ImageView) findViewById(R.id.iv_black);

        iv_black.setImageBitmap(FilesUtils.readBitMap(context, R.mipmap.img_welcome_show));

    }


    private void init() {

        isFirstIn = SharedPreferencesUtils.getIschecked(context, "isFirstIn");

        if (isFirstIn && null != mHandler) {
            mHandler.sendEmptyMessageDelayed(Mbaconsts.HandlerConsts.GO_LOGIN,
                    Mbaconsts.HandlerConsts.SPLASH_DELAY_TIME);
        } else {
            if (null != mHandler) {
                mHandler.sendEmptyMessageDelayed(Mbaconsts.HandlerConsts.GO_GUIDE,
                        Mbaconsts.HandlerConsts.SPLASH_DELAY_TIME);
            }
        }

    }

    private void goLogin() {

        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goGuide() {
        Intent intent = new Intent(context, GuideActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 更新版本对话框
     */

    private void showDialog(String type, String content) {
        XsDialog noticeDialog;
        if (type.equals("1")) {
            noticeDialog = new XsDialog(context, "系统提示:", content, true, false, true);
        } else {
            noticeDialog = new XsDialog(context, "系统提示:", content, true, true, true);
        }

        noticeDialog.setBtnOklistener(new XsDialog.BtnOKListener() {
            @Override
            public void clickOk() {
                download();
            }
        });
        noticeDialog.setBtnCancelListener(new XsDialog.BtnCancelListener() {
            @Override
            public void clickCancel() {
                init();
            }
        });
        noticeDialog.show();
    }

    /**
     * 下载最新版本
     */
    protected void download() {
        tv_splash_plan.setVisibility(View.VISIBLE);
        HttpUtils httpUtils = new HttpUtils(2000);
        //参数1：新版本的下载地址
        //参数2：新版本的保存地址
        //参数3：回调监听
        httpUtils.download(downloadUrl, Environment.getExternalStorageDirectory().getAbsolutePath() + "/yunshuxie.apk", new RequestCallBack<File>() {

            //下载成功的时候调用
            @Override
            public void onSuccess(ResponseInfo<File> arg0) {
                Toast.makeText(getApplicationContext(), "下载成功", Toast.LENGTH_SHORT).show();
                installApk();
            }

            //下载失败的时候调用
            @Override
            public void onFailure(HttpException arg0, String arg1) {
                init();
                Toast.makeText(getApplicationContext(), "下载失败了", Toast.LENGTH_SHORT).show();
                Log.e("arg0", arg1);
            }

            //下载中去调用的
            //total : 下载总进度
            //current : 当前下载进度
            //isUploading ： 是否装载
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                // TODO Auto-generated method stub
                super.onLoading(total, current, isUploading);
                tv_splash_plan.setVisibility(View.VISIBLE);//显示textview
                tv_splash_plan.setText(current / 1024 + " KB " + "/" + total / 1024 + " KB " + " 正在下载新版本");
            }

        });
    }

    /**
     * 安装apk
     */
    protected void installApk() {
        try {

            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/yunshuxie.apk")), "application/vnd.android.package-archive");
            //当当前的activity退出的时候，会调用以前的activity的OnActivityResult方法
            startActivityForResult(intent, 0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        init();
    }

    /**
     * 更新版本
     */
    private void update() {
        //连接服务器，耗时操作，4.0以后就不允许在主线程中执行耗时操作了
        new Thread() {

            public void run() {
                Message message = Message.obtain();//从消息池中拿消息，减少资源浪费
                long startTime = System.currentTimeMillis();
                try {
                    //1.获取连接地址
                    URL url = new URL(ServiceUtils.SERVICE_ABOUT_HOME + "/v1/user_info/app_up.htm?appType=android&version=" + getVersionName());
                    //2.连接操作
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();//http协议,httpClient
                    //3.设置连接超时时间
                    conn.setConnectTimeout(5000);//如果连接5秒钟还没有连接成功，就不去连接
//					//设置读取超时时间
//					conn.setReadTimeout(5000);//如果读取5秒钟还没有读取完，就不在读取了
                    //4.设置请求方式
                    conn.setRequestMethod("GET");//post
                    //5.获取服务器返回状态码
                    int responseCode = conn.getResponseCode();
                    Log.e("dadw", responseCode + "");
                    if (responseCode == 200) {
                        //连接成功了，获取服务器返回的数据
                        //需要知道，服务器采用哪种形式封装数据，xml   json
                        InputStream in = conn.getInputStream();//获取服务器返回的流信息
                        //将获取流信息转换成字符串信息
                        String json = StreamUtils.parserStream(in);
                        //解析json数据
                        JSONObject jsonObject = new JSONObject(json);
                        JSONObject object = jsonObject.getJSONObject("data");
                        downloadUrl = object.getString("downloadUrl");
                        isForce = object.getString("isForce");
                        isUp = object.getString("isUp");
                        adviceMsg = object.getString("adviceMsg");
                        //判断是否有最新版本
                        if (isUp.equals("1")) {
                            //大于，升级，弹出更新版本对话框，不能再子线程中更新主线程的ui
                            if (isForce.equals("2")) {
                                message.what = MSG_UPDATE_FORCE_DIALOG;
                            } else if (isForce.equals("1")) {
                                message.what = MSG_UPDATE_DIALOG;
                            } else {
                                init();
                            }
                        } else {
                            //一致，不升级
                            init();
                        }
                    } else {
                        message.what = MSG_SERVER_ERROR;
                    }
                } catch (MalformedURLException e) {
                    //url错误异常
                    e.printStackTrace();
                    message.what = MSG_URL_ERROR;
                } catch (IOException e) {
                    //io异常
                    e.printStackTrace();
                    message.what = MSG_IO_ERROR;
                } catch (JSONException e) {
                    //json异常
                    e.printStackTrace();
                    message.what = MSG_JSON_ERROR;
                } finally {
                    long endTime = System.currentTimeMillis();
                    long dTime = endTime - startTime;
                    if (dTime < 2000) {
                        SystemClock.sleep(2000 - dTime);//保证睡两秒钟的时间
                    }
                    if (null != mHandler) {
                        mHandler.sendMessage(message);
                    }
                }
            }
        }.start();
    }

    /**
     * 获取应用程序的版本号
     */
    public String getVersionName() {
        //包的管理者，获取应用程序清单文件中的所有信息
        PackageManager packageManager = getPackageManager();//获取包的管理者
        //获取包的所有信息
        //packageName : 应用程序包名
        //flags ： 指定的信息
        try {
            //getPackageName() : 获取应用程序的包名
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;//获取应用程序的版本号
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //找不到包名的异常
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}

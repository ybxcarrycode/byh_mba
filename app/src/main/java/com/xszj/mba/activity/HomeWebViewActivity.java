package com.xszj.mba.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.xszj.mba.utils.AbDialogUtil;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.DialogProgressHelper;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.ShareUtils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by QQ on 2015/12/1.
 */
public class HomeWebViewActivity extends BaseActivity {

    private String url = null;
    private String title = null;
    private String newsId = "";
    private WebView webView;
    private DialogProgressHelper dialogProgressHelper;
    private Context mContext;
    private ImageButton main_top_left;
    private TextView main_top_title,tv_top_right;
    private ImageView iv_right;
    private String isCollect="0";
    private String text  = "重点商学院互动直播，名校MBA校友在线答疑，快来和我一起备考名校MBA吧!";

    @Override
    protected int getContentViewResId() {
        mContext = HomeWebViewActivity.this;
        return R.layout.activity_webview;
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
    }

    @Override
    protected void bindViews() {
        main_top_left = (ImageButton) findViewById(R.id.main_top_left);
        main_top_title = (TextView) findViewById(R.id.main_top_title);
        tv_top_right=(TextView) findViewById(R.id.tv_top_right);
        iv_right = (ImageView)findViewById(R.id.iv_right);
        webView = (WebView) findViewById(R.id.web_view);
    }

    @Override
    protected void initViews() {

        tv_top_right.setVisibility(View.GONE);
        main_top_title.setText(title);
    }

    private void putDataToService(final String type) {
        dialogProgressHelper = AbDialogUtil.showProcessDialog(mContext, null);
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("newsId",newsId);
        params.addBodyParameter("collectStatus",type);
        Log.e("dddddd",type);
        String url1 = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/news/newsCollect.json?";

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url1, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                dialogProgressHelper.dismiss();
                String result = responseInfo.result.toString();
                Log.e("dddddd",result);
                try {
                    JSONObject object = new JSONObject(result);
                    String returnCode = object.optString("returnCode", null);
                    if (returnCode.equals("0")) {
                        showToast("操作成功");
                        if (type.equals("1")){
                            tv_top_right.setText("已收藏");
                        }else{
                            tv_top_right.setText("收藏");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dialogProgressHelper.dismiss();
                showToast("网络不给力，请重试..");
            }
        });
    }

    @Override
    protected void initListeners() {
        main_top_left.setOnClickListener(this);
        tv_top_right.setOnClickListener(this);
        iv_right.setOnClickListener(this);
    }


    @Override
    protected void getDateForService() {
        super.getDateForService();

        dialogProgressHelper = AbDialogUtil.showProcessDialog(mContext, null);

        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        wSet.setSupportZoom(true);//支持缩放
        wSet.setUseWideViewPort(true);
        wSet.setLoadWithOverviewMode(true);
        wSet.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容从新布局
        //wSet.setBuiltInZoomControls(true);//支持缩放
//        webView.loadUrl("http://services.yunshuxie.com/wap/mkhxsqbj/sqcourse.html");  YSXConsts.ServiceConsts.SERVICE_SERVICES_ADDR + "wap/cjbj/AddClass.jsp"

        webView.loadUrl(url);
        webView.addJavascriptInterface(new JavascriptInterface(HomeWebViewActivity.this), "imagelistner");
        webView.setWebViewClient(new WebViewClient() {
            @SuppressLint("JavascriptInterface")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub

                webView.addJavascriptInterface(HomeWebViewActivity.this, "androidRollBack()");
                return true;
            }
        });
        webView.setWebViewClient(new MyWebViewClient());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_top_left:
                finish();
                break;
            case R.id.tv_top_right:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    if (isCollect.equals("1")){
                        isCollect="0";
                    }else{
                        isCollect="1";
                    }
                    putDataToService(isCollect);
                }
                break;

            case R.id.iv_right:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    ShareUtils.share(mContext,url,title,text);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.setVisibility(View.VISIBLE);
        webView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.pauseTimers();
        if (isFinishing()) {
            webView.loadUrl("about:blank");
            //setContentView(new FrameLayout(this));
        }
    }


    // js通信接口
    public static class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        public void opennymber(String num) {

            Intent intent = new Intent();
            intent.putExtra("num", num);
            intent.setClass(context, HomeWebViewActivity.class);
            context.startActivity(intent);

        }
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            AbDialogUtil.closeProcessDialog(dialogProgressHelper);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);


        }

    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}

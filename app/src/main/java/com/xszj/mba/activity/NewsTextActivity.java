package com.xszj.mba.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
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
import com.xszj.mba.utils.MyAsyncTask;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.ShareUtils;
import com.xszj.mba.utils.TypeUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.XMLReader;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by Administrator on 2017/4/13.
 */

public class NewsTextActivity extends BaseActivity {

    private String url = null;
    private String title = null;
    private String newsId = "";
    private String isCollect = "0";
    private int findType;
    private int selPosition;
    private Spanned sp=null;
    private ImageButton main_top_left;
    private TextView main_top_title, tv_top_right,textView;
    private ImageView iv_right;
    private String html=null;
    private DialogProgressHelper dialogProgressHelper;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_new_text;
    }


    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        html = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        newsId = getIntent().getStringExtra("newsId");
        isCollect = getIntent().getStringExtra("isCollect");
        findType = getIntent().getIntExtra("findType", 0);
        selPosition = getIntent().getIntExtra("selPosition", 0);
    }

    @Override
    protected void bindViews() {
        main_top_left = (ImageButton) findViewById(R.id.main_top_left);
        main_top_title = (TextView) findViewById(R.id.main_top_title);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        textView = (TextView)findViewById(R.id.textView);
    }

    @Override
    protected void initViews() {
        if (isCollect.equals("1")) {
            tv_top_right.setText("已收藏");
        } else {
            tv_top_right.setText("收藏");
        }
        main_top_title.setText(title);
    }

    @Override
    protected void initListeners() {
        main_top_left.setOnClickListener(this);
        tv_top_right.setOnClickListener(this);
        iv_right.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_top_left:
                finish();
                break;
            case R.id.tv_top_right:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(context);
                } else {
                    if (isCollect.equals("1")) {
                        isCollect = "0";
                    } else {
                        isCollect = "1";
                    }
                    putDataToService(isCollect);
                }
                break;

            case R.id.iv_right:
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(context);
                } else {
                    showToast("url");
                    //ShareUtils.share(context, url,title);
                }
                break;
        }
    }

    @Override
    protected void getDateForService() {
        super.getDateForService();

        new MyAsyncTask(){
            @Override
            public void preTask() {

            }

            @Override
            public void doInBack() {
                sp= Html.fromHtml(html, new MyImageGetter(),new MyTagHandler());
            }

            @Override
            public void postTask() {
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                textView.setText(sp);
            }
        }.execute();
    }


    private void sendBroadcastDate() {
        Intent intent;
        if (findType == TypeUtils.SCHOOL_D_NEWS) {
            intent = new Intent("schoolCollectNews");
        } else if (findType == TypeUtils.COLLECT_ACTIVITY) {
            intent = new Intent("collectCollectNews");
        } else {
            intent = new Intent("findCollectNews");
            intent.putExtra("findtype", findType);
        }
        intent.putExtra("newsPosition", selPosition);
        context.sendBroadcast(intent);
    }


    private void putDataToService(final String type) {
        dialogProgressHelper = AbDialogUtil.showProcessDialog(context, null);
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("newsId", newsId);
        params.addBodyParameter("collectStatus", type);
        Log.e("dddddd", type);
        String url1 = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/news/newsCollect.json?";

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url1, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                dialogProgressHelper.dismiss();
                String result = responseInfo.result.toString();
                Log.e("dddddd", result);
                try {
                    JSONObject object = new JSONObject(result);
                    String returnCode = object.optString("returnCode", null);
                    if (returnCode.equals("0")) {
                        showToast("操作成功");
                        if (type.equals("1")) {
                            tv_top_right.setText("已收藏");
                        } else {
                            tv_top_right.setText("收藏");
                        }
                        sendBroadcastDate();
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


    public class MyImageGetter implements Html.ImageGetter {

        @Override
        public Drawable getDrawable(String source) {
            Drawable d = null;
            try {
                InputStream is = new DefaultHttpClient().execute(new HttpGet(source)).getEntity().getContent();
                Bitmap bm = BitmapFactory.decodeStream(is);
                d = new BitmapDrawable(bm);
                int width = d.getIntrinsicWidth();
                int hight = d.getIntrinsicHeight();
                if (width==hight){
                    d.setBounds(0, 0, 400, 400);
                }else{
                    d.setBounds(0, 0, 600, 400);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return d;
        }
    }

    public class MyTagHandler implements Html.TagHandler {

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            // 处理标签<img>
            if (tag.toLowerCase(Locale.getDefault()).equals("img")) {
                // 获取长度
                int len = output.length();
                // 获取图片地址
                ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
                String imgURL = images[0].getSource();
                // 使图片可点击并监听点击事件
                output.setSpan(new ClickableImage(imgURL), len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        private class ClickableImage extends ClickableSpan {
            private String url;

            public ClickableImage(String url) {
                this.url = url;
            }
            @Override
            public void onClick(View widget) {
                // 进行图片点击之后的处理
                //showToast("我被点击了");
            }
        }
    }
}

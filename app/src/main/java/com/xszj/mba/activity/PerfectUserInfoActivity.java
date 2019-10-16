package com.xszj.mba.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.constants.ErrorCode;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.nets.Response;
import com.umeng.comm.core.nets.responses.PortraitUploadResponse;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.bean.PerfectSchoolAndTypeBean;
import com.xszj.mba.bean.PerfectUserInfoBean;
import com.xszj.mba.bean.PrefectUserInfoBean;
import com.xszj.mba.bean.UserInfoBean;
import com.xszj.mba.personImage.CropImageActivity;
import com.xszj.mba.utils.ActionSheetDialog;
import com.xszj.mba.utils.CircleImageView;
import com.xszj.mba.utils.IntentTagUtil;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.PermissionUtils;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.SharedPreferencesUtils;
import com.xszj.mba.utils.TypeUtils;
import com.xszj.mba.view.GlobalTitleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 * 修改用户资料
 */

public class PerfectUserInfoActivity extends BaseActivity {

    private CommunitySDK sdk;
    private String umengName = "";
    private String umengImg = "";
    protected int fromType;
    private GlobalTitleView titleView;
    private Context mContext;
    private LinearLayout ll_header;
    private CircleImageView image_head;
    private TextView tv_true_name, tv_craft_name, tv_want_school, tv_graduate_school, tv_company_type, tv_sign_ok;
    private LinearLayout ll_true_name, ll_want_school, ll_graduate_school, ll_company_type;
    private RelativeLayout rl_craft_name;
    private RequestParams params;

    public static final int FLAG_MODIFY_FINISH_WANT_SCHOOL = 11;
    public static final int FLAG_MODIFY_FINISH_GRADUATE_SCHOOL = 12;
    public static final int FLAG_MODIFY_FINISH_COMPANY_TYPE = 13;
    public static final int FLAG_MODIFY_FINISH_TRUE_NAME = 14;
    public static final int FLAG_MODIFY_FINISH_CRAFT_NAME = 15;
    private String nickName = "";
    private String personalSign = "";
    private String willSchoolId = "";
    private String willSchoolName = "";
    private String graduationShoolId = "";
    private String enterpriseType = "";

    private int changeType;

    private List<PerfectSchoolAndTypeBean> enterpriseDataList;
    private List<PerfectSchoolAndTypeBean> graduationShoolDataList;
    private List<PerfectSchoolAndTypeBean> willSchoolDataList;

    // 以下是和拍照有关的数据
    /**
     * 本页面打开相册的请求码。
     */
    public static final int FLAG_CHOOSE_IMG = 1;

    /**
     * 本页面打开Camera APP的请求码。
     */
    public static final int FLAG_CHOOSE_PHONE = 2;

    /**
     * 打开CropImageActivity页面的请求码。
     */
    public static final int FLAG_MODIFY_FINISH = 3;

    // 相机拍照
    public static final String IMAGE_PATH = "yunshuxie";
    public static final File FILE_SDCARD = Environment
            .getExternalStorageDirectory();
    public static final File FILE_LOCAL = new File(FILE_SDCARD, IMAGE_PATH);
    public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL,
            "images/screenshots");
    DisplayImageOptions options;
    private String file_url;
    /**
     * 拍摄的照片名称。
     */
    private String localTempImageFileName = "";
    public Bitmap bitmap = null;

    // 异步处理
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3:
                    putDataToService("1");
                    image_head.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        fromType = getIntent().getIntExtra(IntentTagUtil.PERFECT_USER_START_FROM_SIGN, 0);
    }

    @Override
    protected int getContentViewResId() {
        mContext = PerfectUserInfoActivity.this;
        sdk = CommunityFactory.getCommSDK(mContext);
        return R.layout.activity_perfect_userinfo;
    }

    @Override
    protected void initTitle() {
        super.initTitle();
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        titleView.setTitle("编辑资料");
        titleView.setBackVisible(true);
        titleView.setTitleLeftTvShow(true);

        titleView.setBackIconImage(R.mipmap.icon_back_blue);
        titleView.setTitleLeftTv("先逛逛");
        titleView.setTitleLeftTvColor(Color.parseColor("#bdbdbd"));
        titleView.setTitleLeftTvSize(12);

        titleView.setLeftDiyBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput();
                finish();
            }
        });
        titleView.setLeftTvOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput();
                finish();
            }
        });

    }

    @Override
    protected void bindViews() {

        //注册完成按钮
        tv_sign_ok = (TextView) findViewById(R.id.tv_sign_ok);

        image_head = (CircleImageView) findViewById(R.id.image_head);
        ll_header = (LinearLayout) findViewById(R.id.ll_header);
        //昵称
        ll_true_name = (LinearLayout) findViewById(R.id.ll_true_name);
        tv_true_name = (TextView) findViewById(R.id.tv_true_name);
        ll_true_name.setOnClickListener(this);

        //个性签名
        tv_craft_name = (TextView) findViewById(R.id.tv_craft_name);
        rl_craft_name = (RelativeLayout) findViewById(R.id.rl_craft_name);
        rl_craft_name.setOnClickListener(this);
        //意向院校
        tv_want_school = (TextView) findViewById(R.id.tv_want_school);
        ll_want_school = (LinearLayout) findViewById(R.id.ll_want_school);
        ll_want_school.setOnClickListener(this);
        //毕业院校
        tv_graduate_school = (TextView) findViewById(R.id.tv_graduate_school);
        ll_graduate_school = (LinearLayout) findViewById(R.id.ll_graduate_school);
        ll_graduate_school.setOnClickListener(this);
        //工作企业类别
        tv_company_type = (TextView) findViewById(R.id.tv_company_type);
        ll_company_type = (LinearLayout) findViewById(R.id.ll_company_type);
        ll_company_type.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        if (fromType == 1) {
            tv_sign_ok.setVisibility(View.VISIBLE);
            titleView.setTitle("个人资料");
        } else {
            titleView.setTitleLeftTvShow(false);
        }

    }

    @Override
    protected void initListeners() {

        ll_header.setOnClickListener(this);
        tv_sign_ok.setOnClickListener(this);
    }

    @Override
    protected void getDateForService() {
        super.getDateForService();

        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/user_info/getUserInfo.json?userId=" + NimApplication.user;

        new HttpUtils().configCurrentHttpCacheExpiry(200).send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.e("ddddd", result);

                if (null == result || result.equals("")) {
                    return;
                }

                PrefectUserInfoBean prefectUserInfoBean = JSON.parseObject(result, PrefectUserInfoBean.class);

                if (null == prefectUserInfoBean) return;

                if (prefectUserInfoBean.getReturnCode().equals("0")) {
                    UserInfoBean userInfoBean = prefectUserInfoBean.getData().getUserInfo();
                    SharedPreferencesUtils.setProperty(mContext, "headPic", userInfoBean.getHeadPic());
                    ImageLoader.getInstance().displayImage(userInfoBean.getHeadPic(), image_head, NimApplication.imageOptions);
                    umengImg = userInfoBean.getHeadPic();
                    umengName = userInfoBean.getNickName();
                    tv_true_name.setText(userInfoBean.getNickName());
                    tv_craft_name.setText(userInfoBean.getPersonalSign());
                    tv_want_school.setText(userInfoBean.getWillSchoolId());
                    tv_graduate_school.setText(userInfoBean.getGraduationShoolId());
                    tv_company_type.setText(userInfoBean.getEnterpriseType());

                    enterpriseDataList = prefectUserInfoBean.getData().getBaseData().getEnterpriseData();
                    graduationShoolDataList = prefectUserInfoBean.getData().getBaseData().getGraduationShoolData();
                    willSchoolDataList = prefectUserInfoBean.getData().getBaseData().getWillSchoolData();

                } else {
                    showToast(prefectUserInfoBean.getReturnMsg());
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_header:
                changeType = TypeUtils.PERFECT_USER_INFO_HEADPIC;
                if (PermissionUtils.isCameraPermission(PerfectUserInfoActivity.this, 0x007)) {
                    showPop();
                }

                break;

            case R.id.ll_want_school:
                changeType = TypeUtils.PERFECT_USER_INFO_SCHOOL_INTENTION;
                startActivityForResult(new Intent(context, PerfectDetailActivity.class).putExtra("list", willSchoolDataList.toArray()), FLAG_MODIFY_FINISH_WANT_SCHOOL);

                break;
            case R.id.ll_graduate_school:
                changeType = TypeUtils.PERFECT_USER_INFO_SCHOOL_GRADUATE;
                startActivityForResult(new Intent(context, PerfectDetailActivity.class).putExtra("list", graduationShoolDataList.toArray()), FLAG_MODIFY_FINISH_GRADUATE_SCHOOL);

                break;
            case R.id.ll_company_type:
                changeType = TypeUtils.PERFECT_USER_INFO_JOB_TYPE;
                startActivityForResult(new Intent(context, PerfectDetailActivity.class).putExtra("list", enterpriseDataList.toArray()), FLAG_MODIFY_FINISH_COMPANY_TYPE);

                break;

            case R.id.ll_true_name:
                changeType = TypeUtils.PERFECT_USER_INFO_NAME;
                startActivityForResult(new Intent(context, PerfectNameSignActivity.class).putExtra("type", "1"), FLAG_MODIFY_FINISH_TRUE_NAME);
                break;

            case R.id.rl_craft_name:
                changeType = TypeUtils.PERFECT_USER_INFO_SIGNATURE;
                startActivityForResult(new Intent(context, PerfectNameSignActivity.class).putExtra("type", "2"), FLAG_MODIFY_FINISH_CRAFT_NAME);
                break;
            case R.id.tv_sign_ok:
                finish();
                break;
            default:
                break;
        }
    }

    private void showPop() {
        new ActionSheetDialog(context)
                .builder()
                .setTitle("选择头像来源")
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                camera();
                            }
                        })
                .addSheetItem("从相册选择",
                        ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                gallery();
                            }
                        }).show();
    }


    //请求权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0x007:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showPop();
                } else {
                    Toast.makeText(this, "拍照权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public String str_path = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            // result is not correct
            return;
        } else {
            switch (requestCode) {
                case FLAG_CHOOSE_IMG:
                    // 表示从相册APP返回到当前页面了。
                    if (data != null) {
                        Uri uri = data.getData();
                        if (!TextUtils.isEmpty(uri.getAuthority())) {
                            Cursor cursor = context.getContentResolver().query(uri,
                                    new String[]{MediaStore.Images.Media.DATA},
                                    null, null, null);
                            if (null == cursor) {
                                // Toast.makeText("", "图片没找到", 0).show();
                                return;
                            }
                            cursor.moveToFirst();
                            String path = cursor.getString(cursor
                                    .getColumnIndex(MediaStore.Images.Media.DATA));
                            cursor.close();
                            Intent intent = new Intent(context, CropImageActivity.class);
                            intent.putExtra("path", path);
                            startActivityForResult(intent, FLAG_MODIFY_FINISH);
                        } else {
                            Intent intent = new Intent(context, CropImageActivity.class);
                            intent.putExtra("path", uri.getPath());
                            startActivityForResult(intent, FLAG_MODIFY_FINISH);
                        }
                    }
                    break;

                case FLAG_CHOOSE_PHONE:
                    File f = new File(FILE_PIC_SCREENSHOT, localTempImageFileName);
                    Intent intent = new Intent(context, CropImageActivity.class);
                    intent.putExtra("path", f.getAbsolutePath());

                    file_url = f.getPath();
                    Log.i("MSG", file_url + "d");
                    startActivityForResult(intent, FLAG_MODIFY_FINISH);
                    break;
                case FLAG_MODIFY_FINISH:
                    if (data != null) {
                        final String path = data.getStringExtra("path");
                        str_path = path + "";
                        Log.i("MSG", "xxxx" + path);
                        bitmap = BitmapFactory.decodeFile(path);
                        if (null != mHandler) {
                            mHandler.obtainMessage(3).sendToTarget();
                        }
                    }
                    break;

                case FLAG_MODIFY_FINISH_WANT_SCHOOL:
                    graduationShoolId = "";
                    enterpriseType = "";
                    willSchoolId = willSchoolDataList.get(data.getIntExtra("position", 0)).getDictionaryId();
                    Log.e("ddddd", willSchoolId);
                    willSchoolName = willSchoolDataList.get(data.getIntExtra("position", 0)).getDictionaryName();
                    tv_want_school.setText(willSchoolName);

                    putDataToService("0");
                    break;

                case FLAG_MODIFY_FINISH_GRADUATE_SCHOOL:
                    willSchoolId = "";
                    enterpriseType = "";
                    graduationShoolId = graduationShoolDataList.get(data.getIntExtra("position", 0)).getDictionaryId();
                    Log.e("ddddd", graduationShoolId);
                    tv_graduate_school.setText(graduationShoolDataList.get(data.getIntExtra("position", 0)).getDictionaryName());
                    putDataToService("0");
                    break;

                case FLAG_MODIFY_FINISH_COMPANY_TYPE:
                    willSchoolId = "";
                    graduationShoolId = "";
                    enterpriseType = enterpriseDataList.get(data.getIntExtra("position", 0)).getDictionaryId();
                    Log.e("ddddd", enterpriseType);
                    tv_company_type.setText(enterpriseDataList.get(data.getIntExtra("position", 0)).getDictionaryName());
                    putDataToService("0");
                    break;

                case FLAG_MODIFY_FINISH_TRUE_NAME:
                    willSchoolId = "";
                    graduationShoolId = "";
                    enterpriseType = "";
                    nickName = data.getStringExtra("content");
                    SharedPreferencesUtils.setProperty(mContext, "nickName", nickName);
                    personalSign = "";
                    tv_true_name.setText(nickName);
                    umengName = nickName;
                    putDataToService("0");
                    break;

                case FLAG_MODIFY_FINISH_CRAFT_NAME:
                    willSchoolId = "";
                    graduationShoolId = "";
                    enterpriseType = "";
                    nickName = "";
                    personalSign = data.getStringExtra("content");
                    tv_craft_name.setText(personalSign);
                    putDataToService("0");
                    break;
                default:
                    break;
            }
        }

    }

    private void putDataToService(String type) {

        dismissProgressDialog();

        String url = ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/user_info/updateUserInfo.json?";
        //String url= "http://192.168.100.56/wacc-mba-web/v1/user_info/updateUserInfo.json?";
        Log.e("ddddd", url);

        params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("nickName", nickName);
        params.addBodyParameter("personalSign", personalSign);
        params.addBodyParameter("willSchoolId", willSchoolId);
        params.addBodyParameter("graduationShoolId", graduationShoolId);
        params.addBodyParameter("enterpriseType", enterpriseType);

        if (type.equals("1")) {
            params.addBodyParameter("file", new File(str_path));
        }

        Log.e("ddddd", NimApplication.user + "===" + nickName + "==" + personalSign + "==" + willSchoolId + "==" + graduationShoolId + "===" + enterpriseType + "==" + new File(str_path));
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                dismissProgressDialog();
                String result = responseInfo.result.toString();
                if (result == null || "".equals(result)) {
                    return;
                }

                PerfectUserInfoBean bean = JsonUtil.parseJsonToBean(result, PerfectUserInfoBean.class);
                if (null == bean) {
                    return;
                }
                if ("0".equals(bean.getReturnCode())) {
                    showToast(bean.getReturnMsg());
                    switch (changeType) {
                        case TypeUtils.PERFECT_USER_INFO_HEADPIC:
                            umengImg = bean.getData().getUserBean().getHeadPic();
                            SharedPreferencesUtils.setProperty(context, "headPic", bean.getData().getUserBean().getHeadPic());
                            updateUmengUserImg();
                            break;
                        case TypeUtils.PERFECT_USER_INFO_SCHOOL_INTENTION:
                            SharedPreferencesUtils.setProperty(context, "schoolName", willSchoolName);
                            break;
                        case TypeUtils.PERFECT_USER_INFO_NAME:
                            updateUmengUserName();
                            break;
                        default:
                            break;
                    }

                } else {
                    showToast(bean.getReturnMsg());
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                dismissProgressDialog();
                Log.e("ddddd", e.toString() + "===" + s);
                showToast("网络不给力，请重试..");
            }
        });
    }

    private void updateUmengUserName() {
        CommUser user = new CommUser();
        user.name = "" + umengName;
        user.id = "" + NimApplication.user;
        user.iconUrl = "" + umengImg;
        sdk.updateUserProfile(user, new Listeners.CommListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(Response response) {
            }
        });
    }
    private void updateUmengUserImg() {
        sdk.updateUserProtrait(bitmap, new Listeners.SimpleFetchListener<PortraitUploadResponse>() {
            @Override
            public void onComplete(PortraitUploadResponse portraitUploadResponse) {
            }
        });
    }


    /**
     * 打开相册APP。
     */
    public void gallery() {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, FLAG_CHOOSE_IMG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开拍照APP。
     */
    public void camera() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            try {
                localTempImageFileName = String.valueOf((new Date()).getTime())
                        + ".png";
                File filePath = FILE_PIC_SCREENSHOT;
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                Intent intent2 = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(filePath, localTempImageFileName);
                // localTempImgDir和localTempImageFileName是自己定义的名字
                Uri u = Uri.fromFile(f);
                intent2.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, u);
                startActivityForResult(intent2, FLAG_CHOOSE_PHONE);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    protected void loginUmeng(String name, String id, String imgUrl) {
        //创建CommUser前必须先初始化CommunitySDK
        CommunitySDK sdk = CommunityFactory.getCommSDK(this);
        CommUser user = new CommUser();
        user.name = "" + SharedPreferencesUtils.getProperty(context, "nickName");
        user.id = "" + NimApplication.user;
        user.iconUrl = "" + imgUrl;
        sdk.loginToUmengServerBySelfAccount(this, user, new LoginListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(int stCode, CommUser commUser) {
                Log.e("tag", "login result is" + stCode);          //获取登录结果状态码
                if (ErrorCode.NO_ERROR == stCode) {
                    finish();
                } else {
                    showToast("网络不给力，请重试...");
                }
            }
        });

    }

}

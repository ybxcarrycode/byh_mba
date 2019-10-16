package com.xszj.mba.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.adapter.GroupListAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.bean.DlgDataPicker;
import com.xszj.mba.utils.ActionSheetDialog;
import com.xszj.mba.utils.FilesUtils;
import com.xszj.mba.utils.PermissionUtils;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.StringUtil;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.LodingDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2017/4/28.
 */

public class AskActivity extends BaseActivity implements AdapterView.OnItemClickListener{


    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    private GlobalTitleView titleView;
    private ImageView select_iv;
    private TextView spinner;
    private View groupWindow;
    /***** popwindow下拉显示的视图 *******/
    private PopupWindow pw;
    private ArrayList<DlgDataPicker> mGroupData = null;
    private String groupId = "";
    private TextView askTv;
    private EditText contentEt = null;
    private ImageView imageIv = null;
    /** 加载时的对话框 */
    private LodingDialog loadingDialog;
    private FunctionConfig functionConfig;
    private GridView pic_list;
    private List<PhotoInfo> mPicList = new ArrayList<>();
    private ImageAdapter adapter;
    private int max;
    private List<String>  mList = new ArrayList<>();

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_ask;
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        select_iv = (ImageView) findViewById(R.id.select_iv);
        spinner = (TextView) findViewById(R.id.spinnerb_sp);
        if (null == groupWindow) {
            groupWindow = (View) getLayoutInflater().inflate(R.layout.group_select_listview, null);
        }
        askTv = (TextView) findViewById(R.id.ask_tv);

        imageIv = (ImageView)findViewById(R.id.imageIv);
        imageIv.setOnClickListener(this);
        contentEt = (EditText) findViewById(R.id.contentEt);
        pic_list = (GridView) findViewById(R.id.pic_list);

        initData();
    }

    private void initData() {
        mGroupData = new ArrayList<DlgDataPicker>();
        mGroupData.add(new DlgDataPicker("院校信息","103"));
        mGroupData.add(new DlgDataPicker("面试攻略","106"));
        mGroupData.add(new DlgDataPicker("联考答疑","101"));
        mGroupData.add(new DlgDataPicker("经验分享","102"));
        mGroupData.add(new DlgDataPicker("申请材料","104"));
        mGroupData.add(new DlgDataPicker("其他","105"));

    }


    @Override
    protected void initViews() {
        titleView.setTitle("我要提问");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.mipmap.icon_evalute_quit);
    }

    @Override
    protected void initListeners() {
        select_iv.setOnClickListener(this);
        spinner.setOnClickListener(this);
        askTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.select_iv:
                showSelectNumberDialog();
                break;

            case R.id.spinnerb_sp:
                showSelectNumberDialog();
                break;

            case R.id.imageIv:

                if (mPicList.size() >= 3) {
                    Toast.makeText(this, "添加图片最多3张,不能再添加了!", Toast.LENGTH_LONG).show();
                    break;
                }

                if(mPicList!=null&& mPicList.size()>=0){
                    max=3-mPicList.size();
                }

                functionConfig = new FunctionConfig.Builder()
                        .setMutiSelectMaxSize(max)
                        .setEnableCamera(false)
                        .setEnableEdit(false)
                        .setEnableCrop(true)
                        .setEnableRotate(true)
                        .build();

                new ActionSheetDialog(AskActivity.this)
                        .builder()
                        .setTitle("选择头像来源")
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {

                                        if (PermissionUtils.isCameraPermission(AskActivity.this, 0x007)) {
                                            GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
                                        }
                                    }
                                })
                        .addSheetItem("从相册选择",
                                ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                                    }
                                }).show();
                break;
            case R.id.ask_tv:

                sendPost();

                break;
            default:
                break;
        }
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {

                mPicList.addAll(resultList);
                adapter = new ImageAdapter(context);
                pic_list.setAdapter(adapter);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };


    private class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context context) {
            this.context = context;
        }

        //可以return images.lenght(),在这里返回Integer.MAX_VALUE
        //是为了使图片循环显示
        public int getCount() {
            return mPicList.size();
        }

        public Object getItem(int position) {
            return mPicList.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = new ViewHolder();

            View view = View.inflate(context, R.layout.item_ask_gridview, null);

            viewHolder.image_view = (ImageView) view.findViewById(R.id.imageView);
            viewHolder.delete_but = (ImageView) view.findViewById(R.id.delete_but);
            viewHolder.delete_but.setTag(position);

            viewHolder.delete_but.setOnClickListener(new DeleteListener(position));

            ImageLoader.getInstance().displayImage("file://" + mPicList.get(position).getPhotoPath(),viewHolder.image_view);

            return view;

        }
    }

    private static class ViewHolder {
        ImageView image_view,delete_but;
    }

    /**
     * 弹出选择号码对话框
     */
    @SuppressLint("NewApi")
    private void showSelectNumberDialog() {
        // PopupWindow浮动下拉框布局
        ListView listView = (ListView) groupWindow.findViewById(R.id.list);
        ListView mListView = initListView(listView);
        if (null == mListView) {
            return;
        }
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mListView.setDivider(null);
        //		mListView.setSelector(R.drawable.transparent_img);
        //		mListView.setBackgroundResource(R.drawable.qz_bg_container_cell_bottom_normal);
        pw.showAsDropDown(spinner, 0, 0);
    }

    /**
     * 初始化ListView对象
     */

    @SuppressWarnings("deprecation")
    private ListView initListView(ListView lv) {
        lv.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray_square_bottom_corner_bg));
        lv.setOnItemClickListener(this);
        if (null == pw) {
            pw = new PopupWindow(groupWindow, spinner.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
            pw.setOutsideTouchable(true);
            pw.setBackgroundDrawable(new BitmapDrawable());
            pw.setFocusable(true);
        }
        lv.setAdapter(new GroupListAdapter(mGroupData, context, pw));
        return lv;
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String number = mGroupData.get(arg2).menuStr;
        spinner.setText(number);
        groupId = mGroupData.get(arg2).sid;
        pw.dismiss();
    }

    private String content;
    /**
     *
     * 功能:发送问题
     * @author yinxuejian
     */
    private void sendPost() {

        content = contentEt.getEditableText().toString();
        if (TextUtils.isEmpty(content.trim())) {
            showToast("请输入文字");
            return;
        }
        if (!StringUtil.notEmpty(groupId)) {
            showToast("请选择话题");
            return;
        }

        askTv.setClickable(false);
        loadingDialog = LodingDialog.createDialog(context);
        loadingDialog.setMessage("正在提交...");
        loadingDialog.show();

        putDataToService();
    }

    private void putDataToService() {

        String url= ServiceUtils.SERVICE_ABOUT_HOME + "/v1/subject/publish_subject.json?";

        if (mPicList!= null && mPicList.size()>0){
            for (int i = 0 ;i<mPicList.size();i++){
                mList.add(FilesUtils.saveBitmap(FilesUtils.compressBySize(mPicList.get(i).getPhotoPath(),900,1200),i));
            }
        }

        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("subjectContent", content);
        params.addBodyParameter("classify", groupId);
        for (int i = 0; i < mPicList.size(); i++) {
            Log.e("dddd", mList.get(i));
            params.addBodyParameter("files" + i, new File(mList.get(i)), "multipart/form-data");
        }
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                loadingDialog.dismiss();
                askTv.setClickable(true);
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    String returnCode = object.optString("returnCode", null);
                    if (returnCode.equals("0")) {
                        Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                        if (mList!=null && mList.size()>0){
                            FilesUtils.deleFile(mList);
                        }
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                askTv.setClickable(true);
                loadingDialog.dismiss();
                Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class DeleteListener implements View.OnClickListener {
        private int mPosition;
        public DeleteListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mPicList.remove(mPosition);
            adapter.notifyDataSetChanged();
        }
    }
}

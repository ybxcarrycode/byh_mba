package com.xszj.mba.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.adapter.BbsGridviewAdapter;
import com.xszj.mba.adapter.CommentsAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.bean.BbsDetailBean;
import com.xszj.mba.bean.BbsDetailCommentListBean;
import com.xszj.mba.bean.CommentsModel;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.StringUtil;
import com.xszj.mba.utils.TypeUtils;
import com.xszj.mba.view.ExpandableTextView;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.LodingDialog;
import com.xszj.mba.view.NormalEmptyView;
import com.xszj.mba.view.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/4/27.
 */

public class BbsDetailActivity extends BaseActivity {

    public static void lauchSelf(Context context, String id,int position) {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("position",position);
        intent.setClass(context, BbsDetailActivity.class);
        context.startActivity(intent);
    }

    private List<BbsDetailCommentListBean.DataBean> mListNews = new ArrayList<BbsDetailCommentListBean.DataBean>();// 评论列表数据源
    @BindView(R.id.globalTitleView)
    GlobalTitleView titleView;
    private String postId = "";
    private int position;
    private EditText etContent = null;
    private CommentsModel cmPublish = null;
    private BbsDetailBean.DataBean mBbsBean = null;
    private TextView tvNoComments = null;
    private View headView = null;
    /**
     * 加载时的对话框
     */
    private LodingDialog loadingDialog;
    private NormalEmptyView allEmpty;
    private TextView commentCountTv;
    private TextView submitIv;
    private PullToRefreshView ptrNews = null;
    private ListView listNews = null;
    private CommentsAdapter adapterNews;
    private String replyId;
    private int commentCount = 0;
    private String curUid;

    @Override
    protected int getContentViewResId() {
        return R.layout.act_bbs_detail;
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        postId = getIntent().getStringExtra("id");
        position = getIntent().getIntExtra("position",0);
        Log.e("dddddd",postId+"//");
        if (TextUtils.isEmpty(postId)) {
            showToast("数据错误");
            finish();
            return;
        }
        curUid = NimApplication.user;
    }

    @Override
    protected void bindViews() {

        tvNoComments = (TextView) findViewById(R.id.tv_nocomments);
        etContent = (EditText) findViewById(R.id.et_pcontent);
        etContent.setFocusable(true);
        allEmpty = (NormalEmptyView) findViewById(R.id.empty_view);
        submitIv = (TextView) findViewById(R.id.iv_comment_now);

        ptrNews = (PullToRefreshView) findViewById(R.id.ptr_comments);
        ptrNews.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getCommentList(true);
            }
        });
        ptrNews.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                getCommentList(false);
            }
        });
        listNews = (ListView) findViewById(R.id.lv_comments);
        headView = View.inflate(context, R.layout.bbs_details_header, null);
        listNews.addHeaderView(headView);
        adapterNews = new CommentsAdapter(BbsDetailActivity.this, mListNews, BbsDetailActivity.this);
        listNews.setAdapter(adapterNews);

        initDetailData();
        ptrNews.headerRefreshing();
    }

    @Override
    protected void initViews() {
       // titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        titleView.setTitle("问答详情");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);
    }

    @Override
    protected void initListeners() {
        submitIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(context);
                    return;
                }

                if (TextUtils.isEmpty(etContent.getText().toString().trim())) {
                    showToast("亲请输入评论内容");
                    return;
                }
                loadingDialog = LodingDialog.createDialog(BbsDetailActivity.this);
                loadingDialog.setMessage("正在提交...");
                loadingDialog.show();
                submitIv.setClickable(false);
                pubishComment();
            }
        });

        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == 0) {
                    return;
                }
                BbsDetailCommentListBean.DataBean commentsModel = (BbsDetailCommentListBean.DataBean) adapterNews.getItem(arg2 - 1);
                String userType = commentsModel.getUserType();
                if (!StringUtil.isNullOrEmpty(commentsModel.getReplyUserId())) {
                    if (userType.equals("13")|| userType.equals("14") || userType.equals("15")){
                        Intent intent = new Intent(BbsDetailActivity.this, SttDeatilActivity.class);
                        intent.putExtra("expertId", commentsModel.getReplyUserId());
                        startActivity(intent);
                    }
                }
            }
        });
    }


    /**
     * 功能:加载
     *
     * @author yinxuejian
     */
    public void initDetailData() {

        String uid = "-1";
        if (null != NimApplication.user) {
            uid = NimApplication.user;
        }

        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/subject/subject_detail.json?";
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", uid);
        params.addBodyParameter("subjectId", postId);

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String response = responseInfo.result;
                Log.e("ddddd",response);
                BbsDetailBean bean = JsonUtil.parseJsonToBean(response, BbsDetailBean.class);
                if (bean == null) {
                    return;
                }

                if (bean.getReturnCode().equals("0")) {
                    mBbsBean = bean.getData();
                    if (mBbsBean != null) {
                        if("10155".equals(curUid)) {
                            titleView.setRightDiyBtnVisible(true);
                            titleView.setRightDiyBtnText("删除");
                            titleView.setRightDiyBtnTextColor(getResources().getColor(R.color.white));
                            titleView.setRightDiyBtnOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {

                                    //deleteState();
                                }
                            });
                        } else {
                            titleView.setRightDiyBtnVisible(false);
                        }
                        ptrNews.setVisibility(View.VISIBLE);
                        refreshHeadView();
                    }
                } else {
                    showToast(bean.getReturnMsg());
                    if (adapterNews == null || adapterNews.getList() == null || adapterNews.getList().size() == 0) {

                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                ptrNews.setVisibility(View.GONE);
                allEmpty.setVisibility(View.VISIBLE);
                allEmpty.setEmptyType(NormalEmptyView.EMPTY_TYPE_DELETE);
            }
        });

    }

    public void pubishComment() {
        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/subject/reply_subject.json?";
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("subjectId", postId);
        params.addBodyParameter("replyContent", etContent.getText().toString().trim());

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                loadingDialog.dismiss();
                submitIv.setClickable(true);
                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    String returnCode = object.optString("returnCode");
                    String returnMsg = object.optString("returnMsg", null);
                    if (returnCode.equals("0")) {
                        hideSoftInput();
                        etContent.setText("");
                        getCommentList(true);
                        commentCount++;
                        commentCountTv.setText(commentCount + "人回答");

                        sendBroadcast("1",commentCount);

                    } else {
                        showToast(returnMsg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                loadingDialog.dismiss();
                submitIv.setClickable(true);
                showToast(s);
            }
        });

    }

    public void refreshHeadView() {
        TextView nickNameTv = (TextView) headView.findViewById(R.id.nickNameTv);
        ExpandableTextView postDetaiTv = (ExpandableTextView) headView.findViewById(R.id.expand_text_view);
        TextView userDescTv = (TextView) headView.findViewById(R.id.userDescTv);
        TextView pushTimeTv = (TextView) headView.findViewById(R.id.pushTimeTv);
        TextView groupNameTv = (TextView) headView.findViewById(R.id.groupNameTv);
        ImageView userImgIv = (ImageView) headView.findViewById(R.id.userImgIv);
        GridView grid_pic_list = (GridView) headView.findViewById(R.id.grid_pic_list);
        commentCountTv = (TextView) headView.findViewById(R.id.commentCountTv);
        final TextView likeTv = (TextView) headView.findViewById(R.id.like_tv);
        likeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(context);
                } else {
                    if (null != mBbsBean && mBbsBean.getIsFocus().equals("1")) {
                        likeToServer(likeTv,mBbsBean, "2");
                    } else {
                        likeToServer(likeTv,mBbsBean, "1");
                    }

                }
            }
        });
        userImgIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //UserDetailsActvity5.lauchSelf(mContext, mBbsBean.uId, true);
            }
        });

        if (mBbsBean.getIsFocus().equals("1")) {
            likeTv.setTextColor(getResources().getColor(R.color.c9c9c9));
            likeTv.setText("已关注");
        } else {
            likeTv.setTextColor(getResources().getColor(R.color.c1c1c1));
            likeTv.setText("+关注");
        }
        if (StringUtil.notEmpty(mBbsBean.getReplyNum())) {
            commentCount = Integer.parseInt(mBbsBean.getReplyNum());
        } else {
            commentCount = 0;
        }

        commentCountTv.setText(mBbsBean.getReplyNum() + "人回答");
        if (StringUtil.notEmpty(mBbsBean.getPersonalSign())) {
            userDescTv.setText(mBbsBean.getPersonalSign());
        } else {
            userDescTv.setText("");

        }

        postDetaiTv.setText(mBbsBean.getSubjectContent());
        if (StringUtil.notEmpty(mBbsBean.getNickName())) {
            nickNameTv.setText(mBbsBean.getNickName());
        }
        if (StringUtil.notEmpty(mBbsBean.getClassify())) {
            groupNameTv.setText(mBbsBean.getClassify());
        }

        if (StringUtil.notEmpty(mBbsBean.getCreateDate())) {
            pushTimeTv.setText(mBbsBean.getCreateDate());
        }

        ImageLoader.getInstance().displayImage(mBbsBean.getHeadPic(), userImgIv,NimApplication.imageOptions);

        if (mBbsBean.getImageUrls()!=null && mBbsBean.getImageUrls().size()>0 && !mBbsBean.getImageUrls().get(0).equals("")){
            grid_pic_list.setVisibility(View.VISIBLE);
            BbsGridviewAdapter adapter = new BbsGridviewAdapter(context,mBbsBean.getImageUrls());
            grid_pic_list.setAdapter(adapter);
        }else {
            grid_pic_list.setVisibility(View.GONE);
        }
        grid_pic_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(context, ImageShowActivity.class);
                intent.putExtra("imageUrlList", mBbsBean.getImageUrls().toArray());
                intent.putExtra("currentItem", position);
               startActivity(intent);
            }
        });
    }


    private void getCommentList(final boolean refresh) {
        if (refresh) {
            replyId = "";
        }

        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/subject/reply_list.json?subjectId=" + postId + "&replyId=" + replyId + "&pageSize=" + 20;
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("subjectId", postId);
//        params.addBodyParameter("replyId ", 22 + "");
//        params.addBodyParameter("pageSize", "2");
//        Log.e("ddddd", "==/" + url);
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String response = responseInfo.result;

//                Log.e("ddddd", postId + "==" + response);
                hideList(ptrNews);

                BbsDetailCommentListBean bean = JsonUtil.parseJsonToBean(response, BbsDetailCommentListBean.class);
                if (bean == null) {
                    return;
                }

                List<BbsDetailCommentListBean.DataBean> temp = bean.getData();
                if (bean.getReturnCode().equals("0")) {
                    if (refresh) {
                        if (null != temp && 0 < temp.size()) {
                            mListNews = temp;
                            adapterNews.setNews(mListNews);
                            tvNoComments.setVisibility(View.GONE);
                            replyId = temp.get(temp.size() - 1).getReplyId();
                        } else {
                            tvNoComments.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (null != temp && 0 < temp.size()) {
                            mListNews.addAll(temp);
                            adapterNews.notifyDataSetChanged();
                            replyId = temp.get(temp.size() - 1).getReplyId();
                        } else {
                            showToast("已经没有更多了");
                        }

                    }
                } else {
                    if (null == mListNews || 0 == mListNews.size()) {
                        if (refresh) {
                            tvNoComments.setVisibility(View.VISIBLE);
                            showToast("没有数据");
                        }
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                hideList(ptrNews);
                showToast(s);
            }
        });

    }

    public void likeToServer(final TextView likeTv, final BbsDetailBean.DataBean bean, final String type) {

        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/subject/focus_subject.json?";

        final RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("subjectId", postId);
        params.addBodyParameter("focusState", type);

        new HttpUtils().send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("dddddd", responseInfo.result);

                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    String returnCode = object.optString("returnCode", null);
                    if (returnCode.equals("0")) {
                        if (type.equals("2")) {
                            likeTv.setTextColor(getResources().getColor(R.color.c1c1c1));
                            likeTv.setText("+关注");
                            bean.setIsFocus("0");
                        } else {
                            likeTv.setTextColor(getResources().getColor(R.color.c9c9c9));
                            likeTv.setText("已关注");
                            bean.setIsFocus("1");
                        }

                        sendBroadcast("0",0);

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

    private void sendBroadcast(String fromTypeKey,int allPosition){
        Intent intent = new Intent();
        intent.setAction(TypeUtils.BBS_PRAISE_BROADCAST_ACTION);
        intent.putExtra(TypeUtils.BBS_TYPE_KEY, fromTypeKey);
        intent.putExtra(TypeUtils.BBS_FEEDBACK_POSITION,position);
        intent.putExtra(TypeUtils.BBS_FUSION_FEEDBACK_COMMENT_SIZE, allPosition);
        sendBroadcast(intent);
    }


    public void setCommentsModel(CommentsModel model) {
        cmPublish = model;
    }
}

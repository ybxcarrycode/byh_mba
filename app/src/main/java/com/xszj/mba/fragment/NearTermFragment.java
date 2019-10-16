package com.xszj.mba.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.xszj.mba.R;
import com.xszj.mba.activity.NewsTextActivity;
import com.xszj.mba.activity.NewsWebViewActivity;
import com.xszj.mba.adapter.NearTermFAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.bean.FindBean;
import com.xszj.mba.bean.FindResList;
import com.xszj.mba.utils.ACache;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.TypeUtils;
import com.xszj.mba.utils.TypeUtilsString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ybx on 2016/12/22.
 */

public class NearTermFragment extends BaseFragment {

    protected Context context;
    protected XRecyclerView xrecyclerview;
    private RadioGroup radiogroup;
    private RadioButton rb_type1, rb_type2, rb_type3, rb_type4;
    protected List<FindBean> list = new ArrayList<>();

    protected NearTermFAdapter adapter;

    protected int pager = 1;

    protected String url = null;
    protected int type;
    protected int newsClassify;
    private String userId;

    private ACache mCache = null;
    private String mCacheType = null;

    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        mCache = ACache.get(context);
        if (NimApplication.user == null || NimApplication.user.equals("")) {
            userId = "-1";
        } else {
            userId = NimApplication.user;
        }
        return inflater.inflate(R.layout.fragment_school, container, false);
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        Bundle bundle = getArguments();
        type = bundle.getInt("findtype", -1);

    }

    private void setUrlFromType() {
        switch (type) {
            case TypeUtils.FIND_NEAR_TERM:
                newsClassify = 35;
                mCacheType = TypeUtilsString.CACHE_FIND_NEAR_TERM_STR1;
                setRbText("院校动态", "专题解析", "公开课", "备考心得");
                break;
            case TypeUtils.FIND_APPLICATION_MATERIAL:
                newsClassify = 39;
                mCacheType = TypeUtilsString.CACHE_FIND_APPLICATION_MATERIAL_STR1;
                setRbText("申请书", "推荐信", "组织结构图", "个人简历");
                break;
            case TypeUtils.FIND_INTERVIEW:
                newsClassify = 43;
                mCacheType = TypeUtilsString.CACHE_FIND_INTERVIEW_STR1;
                setRbText("验证面试", "压力面试", "小组面试", "自我介绍");
                break;
            case TypeUtils.FIND_WRITTEN_EXAMINATION:
                newsClassify = 47;
                mCacheType = TypeUtilsString.CACHE_FIND_WRITTEN_EXAMINATION_STR1;
                setRbText("数学", "英语", "逻辑", "写作");
                break;
            default:
                break;
        }
    }

    //设置二级选项的内容
    private void setRbText(String type1, String type2, String type3, String type4) {
        rb_type1.setText(type1);
        rb_type2.setText(type2);
        rb_type3.setText(type3);
        rb_type4.setText(type4);
    }

    //设置二级选项缓存key
    private void setCacheTypeKey(int rbType) {
        if (list != null && list.size() > 0) {
            list.clear();
            adapter.notifyDataSetChanged();
        }
        //近期活动
        if (type == TypeUtils.FIND_NEAR_TERM) {
            switch (rbType) {
                case 1:
                    newsClassify = 35;
                    mCacheType = TypeUtilsString.CACHE_FIND_NEAR_TERM_STR1;
                    break;
                case 2:
                    newsClassify = 36;
                    mCacheType = TypeUtilsString.CACHE_FIND_NEAR_TERM_STR2;
                    break;
                case 3:
                    newsClassify = 37;
                    mCacheType = TypeUtilsString.CACHE_FIND_NEAR_TERM_STR3;
                    break;
                case 4:
                    newsClassify = 38;
                    mCacheType = TypeUtilsString.CACHE_FIND_NEAR_TERM_STR4;
                    break;
                default:
                    newsClassify = 35;
                    mCacheType = TypeUtilsString.CACHE_FIND_NEAR_TERM_STR1;
                    break;
            }
        }
        //申请材料
        else if (type == TypeUtils.FIND_APPLICATION_MATERIAL) {
            switch (rbType) {
                case 1:
                    newsClassify = 39;
                    mCacheType = TypeUtilsString.CACHE_FIND_APPLICATION_MATERIAL_STR1;
                    break;
                case 2:
                    newsClassify = 40;
                    mCacheType = TypeUtilsString.CACHE_FIND_APPLICATION_MATERIAL_STR2;
                    break;
                case 3:
                    newsClassify = 41;
                    mCacheType = TypeUtilsString.CACHE_FIND_APPLICATION_MATERIAL_STR3;
                    break;
                case 4:
                    newsClassify = 42;
                    mCacheType = TypeUtilsString.CACHE_FIND_APPLICATION_MATERIAL_STR4;
                    break;
                default:
                    newsClassify = 39;
                    mCacheType = TypeUtilsString.CACHE_FIND_APPLICATION_MATERIAL_STR1;
                    break;
            }
        }
        //面试攻略
        else if (type == TypeUtils.FIND_INTERVIEW) {
            switch (rbType) {
                case 1:
                    newsClassify = 43;
                    mCacheType = TypeUtilsString.CACHE_FIND_INTERVIEW_STR1;
                    break;
                case 2:
                    newsClassify = 44;
                    mCacheType = TypeUtilsString.CACHE_FIND_INTERVIEW_STR2;
                    break;
                case 3:
                    newsClassify = 45;
                    mCacheType = TypeUtilsString.CACHE_FIND_INTERVIEW_STR3;
                    break;
                case 4:
                    newsClassify = 46;
                    mCacheType = TypeUtilsString.CACHE_FIND_INTERVIEW_STR4;
                    break;
                default:
                    newsClassify = 43;
                    mCacheType = TypeUtilsString.CACHE_FIND_INTERVIEW_STR1;
                    break;
            }
        }
        //笔试攻略
        else if (type == TypeUtils.FIND_WRITTEN_EXAMINATION) {
            switch (rbType) {
                case 1:
                    newsClassify = 47;
                    mCacheType = TypeUtilsString.CACHE_FIND_WRITTEN_EXAMINATION_STR1;
                    break;
                case 2:
                    newsClassify = 48;
                    mCacheType = TypeUtilsString.CACHE_FIND_WRITTEN_EXAMINATION_STR2;
                    break;
                case 3:
                    newsClassify = 49;
                    mCacheType = TypeUtilsString.CACHE_FIND_WRITTEN_EXAMINATION_STR3;
                    break;
                case 4:
                    newsClassify = 50;
                    mCacheType = TypeUtilsString.CACHE_FIND_WRITTEN_EXAMINATION_STR4;
                    break;
                default:
                    newsClassify = 47;
                    mCacheType = TypeUtilsString.CACHE_FIND_WRITTEN_EXAMINATION_STR1;
                    break;
            }
        }

        loadDateAll();
    }


    @Override
    protected void bindViews(View view) {
        radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        radiogroup.setVisibility(View.VISIBLE);
        rb_type1 = (RadioButton) view.findViewById(R.id.rb_type1);
        rb_type2 = (RadioButton) view.findViewById(R.id.rb_type2);
        rb_type3 = (RadioButton) view.findViewById(R.id.rb_type3);
        rb_type4 = (RadioButton) view.findViewById(R.id.rb_type4);

        xrecyclerview = (XRecyclerView) view.findViewById(R.id.xrecyclerview);
        setUrlFromType();
    }

    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview.setLayoutManager(linearLayoutManager);
        xrecyclerview.setPullRefreshEnabled(false);
        list.clear();
        adapter = new NearTermFAdapter(list, getActivity());
        xrecyclerview.setAdapter(adapter);
    }

    @Override
    protected void getDateForService() {
        super.getDateForService();
        loadDateAll();
    }

    private void loadDateAll() {
        if (!CommonUtil.isNetworkConnected(context)) {
            String result = mCache.getAsString(mCacheType);
            setListDate(result);
        } else {
            showProgressDialog();
            loadDate("-1");
        }
    }

    @Override
    protected void initListeners() {
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_type1:
                        setCacheTypeKey(1);
                        break;
                    case R.id.rb_type2:
                        setCacheTypeKey(2);
                        break;
                    case R.id.rb_type3:
                        setCacheTypeKey(3);
                        break;
                    case R.id.rb_type4:
                        setCacheTypeKey(4);
                        break;
                    default:
                        setCacheTypeKey(1);
                        break;
                }
            }
        });

        adapter.setOnItemClickListenerRecyclerView(new BaseRecyclerAdapter.OnItemClickListenerRecyclerView() {
            @Override
            public void onItemClick(View view, int position) {
                if (list != null && list.size() > 0) {
                    FindBean date = list.get(position);
                    Log.e("dddddd",date.getNewsContent()+"11111");
                    Intent intent = new Intent(context, NewsWebViewActivity.class);
                    if (date.getNewsType().equals("1")) {
                        String url = "http://mobile.byhmba.com/v1/news/query_news_detail.htm?newsId="+date.getNewsId();
                        intent.putExtra("url", url);
                    } else {
                        intent.putExtra("url", date.getLinkUrl());
                    }
                    intent.putExtra("isCollect", date.getIsCollect());
                    intent.putExtra("newsId", date.getNewsId());
                    intent.putExtra("title", date.getNewsTitle());
                    intent.putExtra("selPosition", position);
                    intent.putExtra("findType", type);
                    startActivity(intent);
                }
            }
        });


        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                int size = list.size();
                Log.e("dssdADA", size + "");
                if (CommonUtil.isNetworkConnected(context) && size > 0) {
                    loadDate("" + list.get(size - 1).getNewsId());
                } else {
                    xrecyclerview.loadMoreComplete();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
    }


    //  收藏/取消收藏 后的改变 list中的数据
    public void changeCollectType(int position) {
        if (list.size() > position) {
            FindBean date = list.get(position);
            if ("1".equals(date.getIsCollect())) {
                list.get(position).setIsCollect("0");
            } else {
                list.get(position).setIsCollect("1");
            }
        }
    }


    private void loadDate(final String newsId) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("newsClassify", "" + newsClassify);
        params.addBodyParameter("newsId", "" + newsId);
        params.addBodyParameter("userId", userId);
        params.addBodyParameter("pageSize", "10");

        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/news/newslist.json?";
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                xrecyclerview.refreshComplete();
                xrecyclerview.loadMoreComplete();

                dismissProgressDialog();
                String result = responseInfo.result.toString();
                Log.e("dddddd news", result);
                if ("".equals(result)) {
                    return;
                }
                if ("-1".equals(newsId)) {
                    mCache.put(mCacheType, result, 3 * ACache.TIME_HOUR);
                }
                setListDate(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                dismissProgressDialog();
                xrecyclerview.refreshComplete();
                xrecyclerview.loadMoreComplete();

                showToast("网络不给力，请重试..");
            }
        });


    }

    private void setListDate(String result) {
        FindResList resDate = JsonUtil.parseJsonToBean(result, FindResList.class);
        if (null != resDate) {
            if ("0".equals(resDate.getReturnCode())) {
                List<FindBean> listI = resDate.getData();
                if (listI != null && listI.size() > 0) {
                    adapter.addItemLast(listI);
                    adapter.notifyDataSetChanged();
                } else if (list.size() > 0) {
                    showToast("没有更多数据");
                }
            } else {
                showToast(resDate.getReturnMsg());
            }
        }
    }


}
/**
* @author yinxuejian
* @version 创建时间：2015-11-4 下午3:24:38
* 
*/

package com.xszj.mba.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.xszj.mba.R;
import com.xszj.mba.activity.AskActivity;
import com.xszj.mba.activity.BbsDetailActivity;
import com.xszj.mba.activity.HobiessSelectActivity;
import com.xszj.mba.adapter.TopicListAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.bean.KaoquanBean;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.TypeUtils;
import com.xszj.mba.view.GlobalTitleView;
import com.xszj.mba.view.NormalEmptyView;
import com.xszj.mba.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;


public class AllTopicFragment extends BaseFragment {
	/**
	 * 下拉刷新, 上拉加载的布局, 包裹了Feeds ListView
	 */
	protected static PullToRefreshView mRefreshLayout;
	private NormalEmptyView empty = null;
	/**
	 * feeds ListView
	 */
	protected ListView mTopicListView;
	private TopicListAdapter adapter;
	private List<KaoquanBean.DataBean> mListBbs = new ArrayList<KaoquanBean.DataBean>();
	private String respose = "";
	private RequestParams params;
	private GlobalTitleView titleView;
	private String subjectId="-1";
	private BroadcastReceiver broadcastReceiver;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//注册刷新数据广播
		broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				String feedbackType = intent.getStringExtra(TypeUtils.BBS_TYPE_KEY);
				int mPosition = intent.getIntExtra(TypeUtils.BBS_FEEDBACK_POSITION, 0);
				int cOrPsize = intent.getIntExtra(TypeUtils.BBS_FUSION_FEEDBACK_COMMENT_SIZE,0);

				if (mPosition >= mListBbs.size() || mListBbs.size() == 0) {
					return;
				}
				if ("0".equals(feedbackType)) {
					updatePraiseNum(mPosition, cOrPsize);
				} else if ("1".equals(feedbackType)) {
					updateCommentNum(mPosition, cOrPsize);
				}
				adapter.notifyDataSetChanged();
			}
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(TypeUtils.BBS_PRAISE_BROADCAST_ACTION);
		getActivity().registerReceiver(broadcastReceiver, intentFilter);
	}

	//更新点赞数和状态
	private void updatePraiseNum(int mPosition, int cOrPsize) {
		int praiseNum;
		if(mListBbs.get(mPosition)==null) return;
		if (mListBbs.get(mPosition).getIsFocus().equals("1")){
			mListBbs.get(mPosition).setIsFocus("0");
			praiseNum = Integer.parseInt(mListBbs.get(mPosition).getFocusNum())-1;
		}else {
			mListBbs.get(mPosition).setIsFocus("1");
			praiseNum = Integer.parseInt(mListBbs.get(mPosition).getFocusNum())+1;
		}
		mListBbs.get(mPosition).setFocusNum(praiseNum+"");
	}
	//更新评论数
	private void updateCommentNum(int mPosition, int cOrPsize) {
		if(mListBbs.get(mPosition)==null) return;
		if (mListBbs.get(mPosition).getReplyNum()!=null){
			mListBbs.get(mPosition).setReplyNum(cOrPsize+"");
		}
	}

	@Override
	protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.all_topic_frag_layout, null);
	}

	@Override
	protected void bindViews(View view) {
		titleView = (GlobalTitleView) view.findViewById(R.id.globalTitleView);
		titleView.setTitle("考圈");

		titleView.setLeftDiyBtnIcon(R.mipmap.topic_sort_btn);
		titleView.setLeftDiyBtnVisible(true);

		titleView.setMoreIconImage(R.mipmap.icon_evluate_teacher);
		titleView.setMoreBtnVisible(true);
		empty = (NormalEmptyView) view.findViewById(R.id.fmobile_empty_view);
		empty.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mRefreshLayout.headerRefreshing();
			}
		});
		initRefreshView(view);
	}

	/**
	 * 初始化下拉刷新试图, listview
	 */
	protected void initRefreshView(View view) {
		// 下拉刷新, 上拉加载的布局
		mRefreshLayout = (PullToRefreshView) view.findViewById(R.id.all_topic_layout);
		mRefreshLayout.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				getBbsList(true);
			}
		});
		mRefreshLayout.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				getBbsList(false);
			}
		});
		mTopicListView = (ListView) mRefreshLayout.findViewById(R.id.all_topic_listview);

		adapter = new TopicListAdapter(getActivity());
		mTopicListView.setAdapter(adapter);

		// 关闭动画缓存
		mTopicListView.setAnimationCacheEnabled(false);
		// 开启smooth scrool bar
		mTopicListView.setSmoothScrollbarEnabled(true);
		mTopicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (null == view.findViewById(R.id.like_tv).getTag()) {
					return;
				}
				KaoquanBean.DataBean obj = (KaoquanBean.DataBean) view.findViewById(R.id.like_tv).getTag();
				if (null != obj) {

					if (null == NimApplication.user) {
						CommonUtil.showLoginddl(getActivity());
					} else {
						BbsDetailActivity.lauchSelf(getActivity(), obj.getSubjectId(),position);
					}

				}
			}
		});
		mRefreshLayout.headerRefreshing();
	}

	@Override
	protected void initViews() {


	}

	@Override
	protected void initListeners() {
		titleView.setRightMoreBtnOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null == NimApplication.user || NimApplication.user.equals("")) {
					CommonUtil.showLoginddl(getActivity());
				} else {
					startActivityForResult(new Intent(getActivity(),AskActivity.class),1);
				}
			}
		});

		titleView.setLeftDiyBtnOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(getActivity(),HobiessSelectActivity.class),1);
			}
		});
	}


	public static void refresh() {
		mRefreshLayout.headerRefreshing();
	}

	private void getBbsList(final boolean refresh) {
		String uid = "-1";
		if (null != NimApplication.user) {
			uid = NimApplication.user;
		}
		if (refresh) {
			subjectId="-1";
		}
		String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/subject/all_subject.json?";
		params = new RequestParams();
		if (!TextUtils.isEmpty(KaoQuanFragment.groupId)) {
			params.addBodyParameter("classify", KaoQuanFragment.groupId);
		}
		params.addBodyParameter("userId",uid);
		params.addBodyParameter("subjectId",subjectId);
		params.addBodyParameter("pageSize", "20");
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(200);
		http.send(HttpRequest.HttpMethod.POST, url,params,new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				hideList(mRefreshLayout);
				//mRefreshLayout.onFooterRefreshComplete();
				respose = responseInfo.result;
				Log.e("ddddd",respose);
				KaoquanBean bean = JsonUtil.parseJsonToBean(respose, KaoquanBean.class);
				if (bean==null){
					return;
				}
				List<KaoquanBean.DataBean> temp =bean.getData();
				if (bean.getReturnCode().equals("0")) {
					if (refresh) {
						if (null != temp && 0 < temp.size()) {
							mListBbs = temp;
							adapter.setList(mListBbs);
							setHasData(empty, mRefreshLayout);
							subjectId = mListBbs.get(mListBbs.size()-1).getSubjectId();
						} else {
							//setEmpty(empty, mRefreshLayout);
							empty.setVisibility(View.VISIBLE);
							empty.setEmptyType(NormalEmptyView.EMPTY_TYPE_NOCONTENT);

						}
					} else {
						if (null != temp && 0 < temp.size()) {
							mListBbs.addAll(temp);
							adapter.addMoreData(temp);
							subjectId = mListBbs.get(mListBbs.size()-1).getSubjectId();
						} else {
							Toast.makeText(getActivity(), "已经没有更多了", Toast.LENGTH_SHORT).show();
						}

					}
				} else {
					if (null == mListBbs || 0 == mListBbs.size()) {
						if (refresh) {
							setError(empty, mRefreshLayout);
						}
					}
				}
			}

			@Override
			public void onFailure(HttpException e, String s) {
				showToast(s);
				hideList(mRefreshLayout);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case 1:
					AllTopicFragment.refresh();
					break;

				default:
					break;
			}
		}
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (broadcastReceiver!=null){
			getActivity().unregisterReceiver(broadcastReceiver);
		}
	}
}

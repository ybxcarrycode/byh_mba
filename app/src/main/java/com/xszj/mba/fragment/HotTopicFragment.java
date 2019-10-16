/**
* @author yinxuejian
* @version 创建时间：2015-11-4 下午3:24:51
* 
*/

package com.xszj.mba.fragment;


import android.os.Bundle;
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
import com.xszj.mba.activity.BbsDetailActivity;
import com.xszj.mba.adapter.TopicListAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.bean.KaoquanBean;
import com.xszj.mba.bean.PublishBbsModel;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.JsonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.view.NormalEmptyView;
import com.xszj.mba.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

public class HotTopicFragment extends BaseFragment {

	private List<KaoquanBean.DataBean> mListBbs = new ArrayList<KaoquanBean.DataBean>();// 论坛数据源
	private TopicListAdapter adapter = null;
	private static PullToRefreshView ptrBbs = null;
	private ListView listBbss = null;
	private int page = 1;
	private NormalEmptyView empty = null;
	private PublishBbsModel pm = null;
	private String respose;
	private RequestParams params;
	@Override
	protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.hot_topic_frag_layout, null);
	}

	@Override
	protected void bindViews(View view) {
		empty = (NormalEmptyView) view.findViewById(R.id.fmobile_empty_view);
		empty.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ptrBbs.headerRefreshing();
			}
		});

		ptrBbs = (PullToRefreshView) view.findViewById(R.id.ptr_bbs);
		ptrBbs.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				getBbsList(true);
			}
		});
		ptrBbs.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {

			@Override
			public void onFooterRefresh(PullToRefreshView view) {
				getBbsList(false);
			}
		});
		listBbss = (ListView) view.findViewById(R.id.lv_bbs);
		adapter = new TopicListAdapter(getActivity());
		listBbss.setAdapter(adapter);
		listBbss.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				KaoquanBean.DataBean obj = mListBbs.get(pos);
				if (null == NimApplication.user) {
					CommonUtil.showLoginddl(getActivity());
				} else {
					BbsDetailActivity.lauchSelf(getActivity(),obj.getSubjectId(),pos);
				}
			}

		});
		ptrBbs.headerRefreshing();
	}

	@Override
	protected void initViews() {

	}


	private void getBbsList(final boolean refresh) {
		String uid = "-1";
		if (null != NimApplication.user) {
			uid = NimApplication.user;
		}
		if (refresh) {
			page = 1;
		}

		String url = ServiceUtils.SERVICE_KAO_QUAN + "mainLatestPosts?";
		params = new RequestParams();
		if (!TextUtils.isEmpty(KaoQuanFragment.groupId)) {
			params.addBodyParameter("topicId", KaoQuanFragment.groupId);
		}
		params.addBodyParameter("uId",uid);
		params.addBodyParameter("type", 2+"");
		params.addBodyParameter("page",page+"");
		params.addBodyParameter("pageCount", "20");
		Log.e("dddddd", url);
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(200);
		http.send(HttpRequest.HttpMethod.POST, url,params,new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				hideList(ptrBbs);
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
							setHasData(empty, ptrBbs);
							page++;
						} else {
							//setEmpty(empty, mRefreshLayout);
						}
					} else {
						if (null != temp && 0 < temp.size()) {
							mListBbs.addAll(temp);
							adapter.addMoreData(temp);
							page++;
						} else {
							Toast.makeText(getActivity(), "已经没有更多了", Toast.LENGTH_SHORT).show();
						}

					}
				} else {
					if (null == mListBbs || 0 == mListBbs.size()) {
						if (refresh) {
							setError(empty, ptrBbs);
						}
					}
				}
			}

			@Override
			public void onFailure(HttpException e, String s) {
				showToast(s);
				hideList(ptrBbs);
			}
		});

	}

	public static void refresh() {
		if (ptrBbs != null) {
			ptrBbs.headerRefreshing();
		}

	}


	@Override
	public void onResume() {
		if (NimApplication.isRefresh) {
			NimApplication.isRefresh = false;
			getBbsList(true);
		}
		super.onResume();
	}


	@Override
	protected void initListeners() {

	}

	@Override
	public void onClick(View v) {

	}
}

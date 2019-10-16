package com.easefun.polyvsdk.demo.download;

import java.util.LinkedList;
import android.widget.ListView;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.view.GlobalTitleView;

public class PolyvDownloadListActivity extends BaseActivity {
	private static final String TAG = "PolyvDownloadListActivity";
	private ListView list;
	private LinkedList<PolyvDownloadInfo> infos;
	private PolyvDBservice service;
	private PolyvDownloadListAdapter adapter;
	private TextView emptyView;
	private GlobalTitleView titleView;

	@Override
	protected int getContentViewResId() {
		return R.layout.activity_downloadlist;
	}

	@Override
	protected void bindViews() {

		titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);

		list = (ListView) findViewById(R.id.list);
		emptyView = (TextView) findViewById(R.id.emptyView);
		list.setEmptyView(emptyView);

		initData();
	}

	@Override
	protected void initViews() {
		titleView.setTitle("下载列表");
		titleView.setBackVisible(true);
		titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);
	}

	@Override
	protected void initListeners() {

	}

	private void initData() {
		service = new PolyvDBservice(this);
		infos = service.getDownloadFiles();
		adapter = new PolyvDownloadListAdapter(this, infos, list);
		list.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(adapter.getSerConn());
	}
}

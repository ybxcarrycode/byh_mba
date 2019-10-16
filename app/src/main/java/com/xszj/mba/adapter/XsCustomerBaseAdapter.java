package com.xszj.mba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public abstract class XsCustomerBaseAdapter<E> extends BaseAdapter {

	protected List<E> mList;
	protected Context mContext;
	protected ListView mListView;
	protected GridView mGridView;

	public XsCustomerBaseAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList == null ? null : mList.get(position);

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	abstract public View getView(int position, View convertView,
			ViewGroup parent);

	public void setList(List<E> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	public List<E> getList() {
		return mList;
	}

	public void cleanData() {
		if (null != mList)
			mList.clear();
		notifyDataSetChanged();
	}

	public void addMoreData(List<E> list) {
		if (mList == null) {
			this.mList = new ArrayList<E>();
			mList.addAll(list);
			notifyDataSetChanged();
		} else {
			mList.addAll(list);
			notifyDataSetChanged();
		}
	}

	public void setList(E[] list) {
		List<E> List = new ArrayList<E>(list.length);
		for (E t : list) {
			List.add(t);
		}
		setList(List);
	}

	public ListView getListView() {
		return mListView;
	}

	public void setListView(ListView listView) {
		mListView = listView;
	}

	public GridView getmGridView() {
		return mGridView;
	}

	public void setmGridView(GridView mGridView) {
		this.mGridView = mGridView;
	}

}

package com.xszj.mba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.xszj.mba.R;
import com.xszj.mba.bean.DlgDataPicker;

import java.util.List;

public class GroupListAdapter extends BaseAdapter {

	private List<DlgDataPicker> list;
	private Context context;
	private PopupWindow pw;

	public GroupListAdapter(List<DlgDataPicker> list, Context context, PopupWindow pw) {
		this.list = list;
		this.context = context;
		this.pw = pw;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.group_select_item, null);
			holder = new ViewHolder();
			holder.tv_listview_item_number = (TextView) convertView.findViewById(R.id.tv_listview_item_number);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_listview_item_number.setText(list.get(position).menuStr);
		return convertView;
	}

	class ViewHolder {
		ImageView iv_usericon;
		TextView tv_listview_item_number;
		ImageView ib_listview_item_delete;
	}
}

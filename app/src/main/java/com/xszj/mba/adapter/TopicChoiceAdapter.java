/**
* @author yinxuejian
* @version 创建时间：2015-11-9 上午11:03:12
* 
*/

package com.xszj.mba.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.xszj.mba.R;
import com.xszj.mba.bean.DlgDataPicker;


public class TopicChoiceAdapter extends XsCustomerBaseAdapter<DlgDataPicker> {
	public TopicChoiceAdapter(Activity context) {
		super(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.item_group, null);
			holder.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		DlgDataPicker bean = mList.get(position);
		if (bean.isSelected) {
			holder.nameTv.setBackgroundResource(R.drawable.fillet_attention_press_btn);
			holder.nameTv.setTextColor(mContext.getResources().getColor(R.color.blue_bg));
		} else {
			holder.nameTv.setBackgroundResource(R.drawable.fillet_attention_btn);
			holder.nameTv.setTextColor(mContext.getResources().getColor(R.color.black));
		}
		holder.nameTv.setText(bean.menuStr);
		return convertView;
	}

	class ViewHolder {
		TextView nameTv;
	}
}
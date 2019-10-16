package com.xszj.mba.popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.bean.DlgDataPicker;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * 专业筛选适配器
 * */
public class PopLocationAdapter extends BaseAdapter {

	private int colorPress = 0;
	private int colorNormal = 0;
	private int colorBgInsideNormal = 0;
	private int colorBgInsidePress = 0;

	class ItemViewHolder {
		public TextView tvName = null;
		public LinearLayout llBgInside = null;
	}

	private LayoutInflater vInflater = null;
	private ArrayList<DlgDataPicker> majors = null;
	private Context mContext = null;
	private String selectId = "";

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (null == majors || 0 == majors.size())
			return convertView;

		ItemViewHolder holder = null;
		if (null == convertView) {
			holder = new ItemViewHolder();
			convertView = vInflater.inflate(R.layout.item_submajor, null);
			//
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_zy_submajor);
			holder.llBgInside = (LinearLayout) convertView.findViewById(R.id.ll_submajor_bg_1);
			convertView.setTag(holder);
		} else {
			holder = (ItemViewHolder) convertView.getTag();
		}
		holder.tvName.setText("");
		//holder.llBgInside.setBackgroundResource(colorBgInsideNormal);
		holder.tvName.setTextColor(colorNormal);
		holder.tvName.setTag(null);
		DlgDataPicker model = majors.get(position);
		holder.tvName.setText(model.menuStr);
		holder.tvName.setTag(model);
		//		holder.tvName.setOnClickListener(new OnClickListener() {
		//			
		//			@Override
		//			public void onClick(View v) {
		//				Object obj = v.getTag();
		//				if(null != obj){
		//					DlgDataPicker sm= (DlgDataPicker) obj ;
		//					sm.isSelected = !sm.isSelected;
		//					notifyDataSetChanged();
		//				}
		//			}
		//		});
		if (selectId.equals(model.sid)) {
			holder.tvName.setTextColor(colorPress);
			//holder.llBgInside.setBackgroundResource(colorBgInsidePress);
		}

		return convertView;
	}

	public PopLocationAdapter(Context context, ArrayList<DlgDataPicker> models, String selectId) {
		mContext = context;
		vInflater = LayoutInflater.from(context);
		this.majors = models;
		this.colorNormal = mContext.getResources().getColor(R.color.sc_zysx_txt_normal);
		this.colorPress = mContext.getResources().getColor(R.color.blue_bg);
		this.colorBgInsideNormal = R.drawable.fillet_attention_btn;
		this.colorBgInsidePress = R.drawable.fillet_attention_press_btn;
		this.selectId = selectId;
	}

	public void setModels(ArrayList<DlgDataPicker> models) {
		this.majors = models;
		notifyDataSetChanged();
	}

	public void addModels(List<DlgDataPicker> models) {
		this.majors.addAll(models);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (null == majors)
			return 0;
		return majors.size();
	}

	@Override
	public Object getItem(int position) {
		if (null == majors)
			return null;
		return majors.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}

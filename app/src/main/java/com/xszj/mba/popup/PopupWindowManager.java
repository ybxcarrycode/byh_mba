package com.xszj.mba.popup;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.xszj.mba.R;
import com.xszj.mba.bean.DlgDataPicker;

import java.util.ArrayList;


public class PopupWindowManager {
	private static PopLocationAdapter adpaterLocation = null;
	private static PopupWindow popLocation = null;

	public interface LocationSelectListenr {
		/**
		 * @param id
		 *            选择的地区id 不限时为空字符串
		 * @param menStr
		 *            选择的地区名称,如果id为空字符串请自行更换文字
		 * */
		void onSelected(String id, String menStr);
	}

	public static void showLocationPopupWindow(Activity context, View p, final ImageView arrow, String selectId, final LocationSelectListenr ls, ArrayList<DlgDataPicker> datas) {
		if (datas == null) {
			return;
		}
		View view = context.getLayoutInflater().inflate(R.layout.pop_location_two, null);
		ListView lvTeachers = (ListView) view.findViewById(R.id.gv_pop_yxsd);
		View view_null = view.findViewById(R.id.view_null);
		ArrayList<DlgDataPicker> temp = (ArrayList<DlgDataPicker>) datas.clone();
		DlgDataPicker ddp = new DlgDataPicker();
		ddp.sid = "";
		ddp.menuStr = "不限";
		temp.add(0, ddp);

		for (DlgDataPicker d : temp) {
			if (d.sid.equals(selectId)) {
				d.isSelected = true;
				break;
			}
		}

		adpaterLocation = new PopLocationAdapter(context, temp, selectId);
		lvTeachers.setAdapter(adpaterLocation);
		lvTeachers.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				DlgDataPicker sm = (DlgDataPicker) adpaterLocation.getItem(position);
				if (null != ls) {
					ls.onSelected(sm.sid, sm.menuStr);
				}
				popLocation.dismiss();
			}
		});

		view_null.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popLocation.dismiss();
			}
		});

		popLocation = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		// 使其聚焦
		popLocation.setFocusable(true);
		// 设置允许在外点击消失
		popLocation.setOutsideTouchable(true);
		// 刷新状态
		popLocation.update();
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		popLocation.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_));
		popLocation.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				if (null != arrow)
					arrow.setImageResource(R.drawable.arrawdown);
			}
		});
		popLocation.showAsDropDown(p);
		if (null != arrow)
			arrow.setImageResource(R.drawable.arrawup);
	}

	public interface CitySelectListenr {
		/**
		 * @param id
		 *            选择的地区id 不限时为空字符串
		 * @param menStr
		 *            选择的地区名称,如果id为空字符串请自行更换文字
		 * */
		void onSelected(String id, String menStr);
	}

	private static PopLocationAdapter adpaterCity = null;
	private static PopupWindow popCity = null;

	/**
	 * 地区选择框
	 * 
	 * @param context
	 *            必须为Activity对象
	 * @param p
	 *            父控件,popupwindows显示在其下方
	 * @param arrow
	 *            下拉上拉箭头对象,这里会控制其变化逻辑,外部不用管
	 * @param selectId
	 *            当前选中的项的id,默认不选传空字符串则选择不限
	 * @param datas
	 *            要选择的数据集
	 * @param ls
	 *            选项选中后触发该监听器返回
	 * */
	public static void showCityPopupWindow(Activity context, View p, final ImageView arrow, String selectId, final CitySelectListenr ls, ArrayList<DlgDataPicker> citys) {
		if (citys == null)
			return;

		View view = context.getLayoutInflater().inflate(R.layout.pop_location, null);
		ListView lvTeachers = (ListView) view.findViewById(R.id.gv_pop_yxsd);
		View view_null = view.findViewById(R.id.view_null);
		ArrayList<DlgDataPicker> temp = (ArrayList<DlgDataPicker>) citys.clone();

		DlgDataPicker ddp = new DlgDataPicker();
		ddp.sid = "";
		ddp.menuStr = "不限";
		temp.add(0, ddp);

		for (DlgDataPicker d : temp) {
			if (d.sid.equals(selectId)) {
				d.isSelected = true;
				break;
			}
		}

		adpaterCity = new PopLocationAdapter(context, temp, selectId);
		lvTeachers.setAdapter(adpaterCity);
		lvTeachers.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				DlgDataPicker sm = (DlgDataPicker) adpaterCity.getItem(position);
				if (null != ls) {
					ls.onSelected(sm.sid, sm.menuStr);
				}
				popCity.dismiss();
			}
		});

		view_null.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popCity.dismiss();
			}
		});
		popCity = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		// 使其聚焦
		popCity.setFocusable(true);
		// 设置允许在外点击消失
		popCity.setOutsideTouchable(true);
		// 刷新状态
		popCity.update();
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		popCity.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_));
		popCity.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				if (null != arrow)
					arrow.setImageResource(R.drawable.arrawdown);
			}
		});
		popCity.showAsDropDown(p);
		if (null != arrow)
			arrow.setImageResource(R.drawable.arrawup);
	}


}

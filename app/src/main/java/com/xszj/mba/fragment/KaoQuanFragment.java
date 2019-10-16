/**
* @author yinxuejian
* @version 创建时间：2015-11-3 下午4:51:47
* 
*/

package com.xszj.mba.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.xszj.mba.R;
import com.xszj.mba.adapter.SelcectPagerAdapter;
import com.xszj.mba.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class KaoQuanFragment extends BaseFragment {

	/** 选项卡 */
	private RadioGroup radiogroup;
	private ViewPager viewpager;
	private List<Fragment> framentList;
	public static String groupId = "-1";
	@Override
	protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.kao_quan_fragment_layout, null);

	}

	@Override
	protected void bindViews(View view) {
		radiogroup = (RadioGroup) view.findViewById(R.id.radioGroup);
		viewpager = (ViewPager) view.findViewById(R.id.message_tab_pager);
	}

	/**
	 * 
	 * 初始化控件
	 * 
	 * @update 2014-5-21 下午1:54:08
	 */
	protected void initViews() {
		framentList = new ArrayList<>();

		framentList.add(new AllTopicFragment());
		framentList.add(new HotTopicFragment());

		SelcectPagerAdapter fragmentAdapter = new SelcectPagerAdapter(getChildFragmentManager(),
				framentList);
		viewpager.setAdapter(fragmentAdapter);
		viewpager.setOffscreenPageLimit(2);
	}

	@Override
	protected void initListeners() {
		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				RadioButton rb = (RadioButton) radiogroup.getChildAt(arg0);
				rb.setChecked(true);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});


		radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
					case R.id.rb1:
						viewpager.setCurrentItem(0);
						break;
					case R.id.rb2:
						viewpager.setCurrentItem(1);
						break;
					default:
						break;
				}
			}
		});

	}

	@Override
	public void onClick(View v) {

	}
}

package com.xszj.mba.activity;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.adapter.TopicChoiceAdapter;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.bean.DlgDataPicker;
import com.xszj.mba.fragment.KaoQuanFragment;
import com.xszj.mba.view.GlobalTitleView;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/4/28.
 */

public class HobiessSelectActivity extends BaseActivity {

    private GlobalTitleView titleView;
    private TopicChoiceAdapter mAdapter;
    private TextView confirmTv;
    private ArrayList<DlgDataPicker> mGroupData = null;
    private GridView gridView;
    @Override
    protected int getContentViewResId() {
        return R.layout.activity_hobiess_select;
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        confirmTv = (TextView) findViewById(R.id.confirm_tv);
        confirmTv.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.topic_gv);

        initData();
    }

    private void initData() {

        mGroupData = new ArrayList<DlgDataPicker>();
        DlgDataPicker dBean = new DlgDataPicker();
        dBean.isSelected = false;
        dBean.menuStr = "不限";
        dBean.sid = "";
        mGroupData.add(dBean);

        mGroupData.add(new DlgDataPicker("院校信息","103"));
        mGroupData.add(new DlgDataPicker("面试攻略","106"));
        mGroupData.add(new DlgDataPicker("联考答疑","101"));
        mGroupData.add(new DlgDataPicker("经验分享","102"));
        mGroupData.add(new DlgDataPicker("申请材料","104"));
        mGroupData.add(new DlgDataPicker("其他","105"));


        mAdapter = new TopicChoiceAdapter(HobiessSelectActivity.this);
        mGroupData.get(0).isSelected = true;
        mAdapter.setList(mGroupData);
        gridView.setAdapter(mAdapter);
        KaoQuanFragment.groupId = mGroupData.get(0).sid;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                for (DlgDataPicker bean : mGroupData) {
                    bean.isSelected = false;
                }
                DlgDataPicker data = mGroupData.get(arg2);
                data.isSelected = true;
                KaoQuanFragment.groupId = data.sid;
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initViews() {
        titleView.setTitle("话题分类");
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);
    }

    @Override
    protected void initListeners() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_tv:
                setResult(Activity.RESULT_OK);
                finish();
                break;

            default:
                break;
        }
    }

}

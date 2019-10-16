package com.xszj.mba.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.xszj.mba.R;
import com.xszj.mba.activity.RecommendVedioPlayActivity;
import com.xszj.mba.adapter.CourseDetailAdapter;
import com.xszj.mba.base.BaseFragment;
import com.xszj.mba.bean.CourseDetailBean;
import com.xszj.mba.bean.CourseDetailBean.DataBean.CourseSectionListBean;
import com.xszj.mba.utils.ServiceUtils;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */

public class CourseDetailFragment extends BaseFragment implements Loadable{
    private Context mContext;
    private ExpandableListView expandable_list = null;
    private List<CourseSectionListBean> listGroup = new ArrayList<>();
    private CourseDetailBean courseDetailBean;
    private String courseId;
    private int openItem = 0;
    private CourseDetailAdapter courseDetailAdapter;
    private String chapterId = "0";
    private String chapterSectionId = "0";
    private int chapterNum = 0;
    private int chapterSectionNum = 0;
    @Override
    protected View onCreateCustomerView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return inflater.inflate(R.layout.fragment_class_detail, container, false);

    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        Intent intent = getActivity().getIntent();
        courseId = intent.getStringExtra("courseId");
        chapterId = intent.getStringExtra("chapterId");
        chapterSectionId = intent.getStringExtra("chapterSectionId");

        Log.e("ddddd",chapterId+"==="+chapterSectionId);
    }

    @Override
    protected void bindViews(View view) {
        expandable_list = (ExpandableListView) view.findViewById(R.id.expandable_list);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

        expandable_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (openItem >= 0 && openItem != groupPosition) {
                    expandable_list.collapseGroup(openItem);
                }
                openItem = groupPosition;
                if (expandable_list.isGroupExpanded(groupPosition)) {
                    expandable_list.collapseGroup(groupPosition);
                } else {
                    expandable_list.expandGroup(groupPosition);
                }
                expandable_list.setSelection(groupPosition);

                return true;
            }
        });

        expandable_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                courseDetailAdapter.setSelectedItem(groupPosition,childPosition);
                String vedioVid = listGroup.get(groupPosition).getChapterSectionList().get(childPosition).getThirdPartyId();
                String vedioTitle = listGroup.get(groupPosition).getChapterSectionList().get(childPosition).getChapterSectionName();
                String chapterId = listGroup.get(groupPosition).getChapterId();
                String chapterSectionId = listGroup.get(groupPosition).getChapterSectionList().get(childPosition).getChapterSectionId();
                Intent intent = new Intent();
                intent.putExtra("vedioVid",vedioVid);
                intent.putExtra("vedioTitle",vedioTitle);
                intent.setAction("playVid");
                mContext.sendBroadcast(intent);
                //请求
                putDataToService(chapterId,chapterSectionId);
                return true;
            }
        });
    }

    private void putDataToService(String chapterId,String chapterSectionId) {
        String url = ServiceUtils.SERVICE_ABOUT_HOME+"/v1/course/recordCourse.json?";

        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("courseId",courseId);
        params.addBodyParameter("chapterId",chapterId);
        params.addBodyParameter("chapterSectionId",chapterSectionId);
        params.addBodyParameter("courseType","1");

        new HttpUtils().send(HttpRequest.HttpMethod.POST, url, params,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("dddddd", responseInfo.result);

                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    String returnCode = object.optString("returnCode", null);
                    if (returnCode.equals("0")) {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void getDateForService() {
        super.getDateForService();

        courseDetailBean = RecommendVedioPlayActivity.courseDetailBean;

        if (null!=courseDetailBean ){
            listGroup = courseDetailBean.getData().getCourseSectionList();
            if (listGroup!=null && listGroup.size()>0){
                courseDetailAdapter = new CourseDetailAdapter(mContext,listGroup);
                expandable_list.setAdapter(courseDetailAdapter);
                //判断是第几个章节，第几个小节
                if (chapterId.equals("0") && chapterSectionId.equals("0")){
                    chapterNum = 0; chapterSectionNum = 0;
                }else {
                    getChapterSectionId(chapterId,chapterSectionId,listGroup);
                }

                courseDetailAdapter.setSelectedItem(chapterNum,chapterSectionNum);
                expandable_list.expandGroup(chapterNum);
            }

        }

    }


    private void getChapterSectionId(String chapterId1, String chapterSectionId1, List<CourseSectionListBean> listGroup) {

        for (int i=0 ;i<listGroup.size();i++){
            if (listGroup.get(i).getChapterId().equals(chapterId1)){
                chapterNum = i;
                List<CourseSectionListBean.ChapterSectionListBean> listBeanList = listGroup.get(i).getChapterSectionList();
                for (int j= 0; j<listBeanList.size();j++){
                    if (listBeanList.get(j).getChapterSectionId().equals(chapterSectionId1)){
                        chapterSectionNum = j;
                        break;
                    }
                }
            }
        }

    }

    @Override
    public <T> void onLoad(T t, int type) {

    }
}

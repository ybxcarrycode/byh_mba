package com.xszj.mba.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.xszj.mba.R;
import com.xszj.mba.bean.CourseDetailBean.DataBean.CourseSectionListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QQ on 2016/6/13.
 */
public class CourseDetailAdapter extends BaseExpandableListAdapter {

    private Context context;
    //用来装载某个item是否被选中
    SparseBooleanArray selected;
    int old = -1;
    int parentPosition = -1;

    private List<CourseSectionListBean> listGroup = new ArrayList<>();

    public CourseDetailAdapter(Context context, List<CourseSectionListBean> list) {
        this.context = context;
        this.listGroup = list;
        selected = new SparseBooleanArray();
    }


    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = listGroup.get(groupPosition).getChapterSectionList().size();
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return (listGroup.get(groupPosition).getChapterSectionList().get(childPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.class_detail_group_item, null);

            holder.tv_right = (TextView) convertView.findViewById(R.id.tv_right);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_right.setText(listGroup.get(groupPosition).getChapterName());
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.class_detail_child_item, null);

            holder.tvc_left = (TextView) convertView.findViewById(R.id.tvc_left);
            holder.tvc_time = (TextView) convertView.findViewById(R.id.tvc_time);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CourseSectionListBean.ChapterSectionListBean date = listGroup.get(groupPosition).getChapterSectionList().get(childPosition);
        if (selected.get(childPosition)&&this.parentPosition==groupPosition) {
            holder.tvc_left.setTextColor(context.getResources().getColor(
                    R.color.text_color_007aff));
            holder.tvc_time.setTextColor(context.getResources().getColor(
                    R.color.tv_find_rb_lg));
        } else {
            // convertView.setBackgroundResource(R.color.white);
            holder.tvc_left.setTextColor(context.getResources().getColor(
                    R.color.black));
            holder.tvc_time.setTextColor(context.getResources().getColor(
                    R.color.c999999));
        }
        holder.tvc_left.setText(date.getChapterSectionName());
        holder.tvc_time.setText("时长:" + date.getChapterSectionDuration());

        return convertView;
    }
    public void setSelectedItem(int groupPosition,int selected) {
        this.parentPosition = groupPosition;
        if (old != -1) {
            this.selected.put(old, false);
        }
        this.selected.put(selected, true);
        old = selected;
        notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    static class ViewHolder {
        //一级控件
        TextView tv_right;

        //二级控件

        TextView tvc_left;
        TextView tvc_time;
    }

}

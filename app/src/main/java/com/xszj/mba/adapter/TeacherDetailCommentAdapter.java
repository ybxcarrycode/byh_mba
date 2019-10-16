package com.xszj.mba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.bean.MbaCommentListBean;


import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */

public class TeacherDetailCommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<MbaCommentListBean> list;

    public TeacherDetailCommentAdapter(Context context, List<MbaCommentListBean> list ) {
        this.mContext = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.teacher_comment_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.tvc_name = (TextView) convertView.findViewById(R.id.tvc_name);
            holder.tvc_time = (TextView) convertView.findViewById(R.id.tvc_time);
            holder.tvc_type = (TextView) convertView.findViewById(R.id.tvc_type);
            holder.tvc_comment = (TextView) convertView.findViewById(R.id.tvc_comment);
            holder.tvc_left = (TextView) convertView.findViewById(R.id.tvc_left);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MbaCommentListBean byhRatedListBean = list.get(position);

        ImageLoader.getInstance().displayImage(byhRatedListBean.getHeadPic(),holder.imageView, NimApplication.imageOptions);
        holder.tvc_name.setText(byhRatedListBean.getNickName());
        holder.tvc_comment.setText(byhRatedListBean.getContent());
        holder.tvc_time.setText(byhRatedListBean.getCreateDate());
        holder.tvc_type.setText(byhRatedListBean.getPersonalSign());
        return convertView;
    }

    private class ViewHolder {
        // 个人简介
        private ImageView imageView;
        private TextView tvc_name;
        private TextView tvc_time;
        private TextView tvc_type;
        private TextView tvc_comment;
        private TextView tvc_left;
    }
}

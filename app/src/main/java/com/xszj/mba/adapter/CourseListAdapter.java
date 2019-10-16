package com.xszj.mba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.base.BaseRecyclerViewHolder;
import com.xszj.mba.bean.CourseListResBean;

import java.util.List;

/**
 * Created by Ybx on 2016/12/30.
 */

public class CourseListAdapter extends BaseRecyclerAdapter<CourseListResBean.DataBean> {

    public CourseListAdapter(List<CourseListResBean.DataBean> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        CourseListResBean.DataBean dataBean = list.get(position);
        holder.tv_title.setText("" + dataBean.getCourseName());
        holder.tv_course_time.setText("" + dataBean.getDuration());
        holder.tv_course_class.setText(dataBean.getCourseChapterNum() + "节");
        ImageLoader.getInstance().displayImage("" + dataBean.getCover(), holder.imageView, NimApplication.imageOptions);

        holder.itemView.setTag(position);
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder;
        final View itemView = inflater.inflate(R.layout.item_course_list_layout, parent, false);
        holder = new ViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListenerRecyclerView) {
                    onItemClickListenerRecyclerView.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
        return holder;
    }


    static class ViewHolder extends BaseRecyclerViewHolder {
        private ImageView imageView;
        private TextView tv_title;
        private TextView tv_course_time;
        private TextView tv_course_class;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_course_time = (TextView) itemView.findViewById(R.id.tv_course_time);
            tv_course_class = (TextView) itemView.findViewById(R.id.tv_course_class);

        }
    }

}

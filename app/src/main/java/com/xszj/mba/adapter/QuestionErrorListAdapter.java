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

public class QuestionErrorListAdapter extends BaseRecyclerAdapter<String> {

    public QuestionErrorListAdapter(List<String> list, Context context) {
        super(list, context);
    }

    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;

        holder.tv_type.setText("逻辑");
        holder.tv_title.setText("2016联考逻辑终极测试");
        holder.tv_time.setText("2018.03.08");

        holder.itemView.setTag(position);
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder;
        final View itemView = inflater.inflate(R.layout.item_question_error_list_layout, parent, false);
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
        private TextView tv_type;
        private TextView tv_title;
        private TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);

        }
    }

}

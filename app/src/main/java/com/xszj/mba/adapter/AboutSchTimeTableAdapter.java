package com.xszj.mba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by Ybx on 2017/1/6.
 */

public class AboutSchTimeTableAdapter extends BaseRecyclerAdapter<String> {

    public AboutSchTimeTableAdapter(List<String> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        if (position % 2 == 0) {
            holder.linear.setBackgroundResource(R.color.bg_about_school_list_item);
        } else {
            holder.linear.setBackgroundResource(R.color.white);
        }

        if (position == 0) {
            holder.tv_rank_label.setText("1");
            holder.tv_rank_label.setBackgroundResource(R.drawable.bg_dot_aboutsch_time_table_item_1);
        } else if (position == 1) {
            holder.tv_rank_label.setText("2");
            holder.tv_rank_label.setBackgroundResource(R.drawable.bg_dot_aboutsch_time_table_item_2);
        } else if (position == 2) {
            holder.tv_rank_label.setText("3");
            holder.tv_rank_label.setBackgroundResource(R.drawable.bg_dot_aboutsch_time_table_item_3);
        } else {
            holder.tv_rank_label.setText("");
            holder.tv_rank_label.setBackgroundResource(R.color.transparent);
        }

        holder.tv_rank.setText("第" + (position + 1) + "批");
        holder.tv_deadline1.setText("2017.1.2");
        holder.tv_deadline2.setText("12:10");
        holder.tv_interview_time1.setText("2017.1.2");
        holder.tv_interview_time2.setText("12:10");
        holder.tv_result_time1.setText("2017.1.2");
        holder.tv_result_time2.setText("12:10");
        holder.tv_site.setText("北京");

        holder.itemView.setTag(position);

    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder;
        final View itemView = inflater.inflate(R.layout.item_aboutsch_time_table_layout, parent, false);
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
        private LinearLayout linear;
        private TextView tv_rank_label;
        private TextView tv_rank;
        private TextView tv_deadline1;
        private TextView tv_deadline2;
        private TextView tv_interview_time1;
        private TextView tv_interview_time2;
        private TextView tv_result_time1;
        private TextView tv_result_time2;
        private TextView tv_site;

        public ViewHolder(View itemView) {
            super(itemView);
            linear = (LinearLayout) itemView.findViewById(R.id.linear);
            tv_rank_label = (TextView) itemView.findViewById(R.id.tv_rank_label);
            tv_rank = (TextView) itemView.findViewById(R.id.tv_rank);
            tv_deadline1 = (TextView) itemView.findViewById(R.id.tv_deadline1);
            tv_deadline2 = (TextView) itemView.findViewById(R.id.tv_deadline2);
            tv_interview_time1 = (TextView) itemView.findViewById(R.id.tv_interview_time1);
            tv_interview_time2 = (TextView) itemView.findViewById(R.id.tv_interview_time2);
            tv_result_time1 = (TextView) itemView.findViewById(R.id.tv_result_time1);
            tv_result_time2 = (TextView) itemView.findViewById(R.id.tv_result_time2);
            tv_site = (TextView) itemView.findViewById(R.id.tv_site);

        }
    }
}

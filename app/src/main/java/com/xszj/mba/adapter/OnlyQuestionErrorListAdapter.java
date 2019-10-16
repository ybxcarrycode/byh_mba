package com.xszj.mba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by Ybx on 2016/12/30.
 */

public class OnlyQuestionErrorListAdapter extends BaseRecyclerAdapter<String> {

    public OnlyQuestionErrorListAdapter(List<String> list, Context context) {
        super(list, context);
    }

    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;

        holder.tv_title.setText(list.get(position));

        if (position==0){
            holder.tv_time.setText("1/24");
        }else if (position==1){
            holder.tv_time.setText("5/24");
        }else if (position==2){
            holder.tv_time.setText("8/24");
        }else if (position==3){
            holder.tv_time.setText("17/24");
        }else if (position==4){
            holder.tv_time.setText("19/24");
        }else if (position==5){
            holder.tv_time.setText("24/24");
        }


        holder.itemView.setTag(position);
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder;
        final View itemView = inflater.inflate(R.layout.item_only_question_error_list_layout, parent, false);
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
        private TextView tv_title;
        private TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);

        }
    }

}

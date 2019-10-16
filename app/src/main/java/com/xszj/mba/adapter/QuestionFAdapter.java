package com.xszj.mba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.utils.SharePrefUtils;
import com.umeng.comm.core.utils.TimeUtils;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.base.BaseRecyclerViewHolder;
import com.xszj.mba.utils.ShareUtils;
import com.xszj.mba.utils.SharedPreferencesUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by swh on 2016/12/22.
 * 我的提问adapter
 */

public class QuestionFAdapter extends BaseRecyclerAdapter<FeedItem> {

    public QuestionFAdapter(List<FeedItem> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;

        FeedItem date = list.get(position);

        holder.imageView.setVisibility(View.GONE);
        holder.tv_title.setText(date.text);
        holder.tv_name_time.setText(SharedPreferencesUtils.getProperty(context,"nickName"));
        Date dateTime = new Date(Long.parseLong(date.publishTime));
        holder.tv_time.setText(TimeUtils.format(dateTime));

        holder.itemView.setTag(position);
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        View itemView = inflater.inflate(R.layout.item_myquestion_activity_layout, parent, false);
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
        private TextView tv_name_time;
        private TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_show);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_name_time = (TextView) itemView.findViewById(R.id.tv_name_time);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);

        }
    }
}

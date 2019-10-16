package com.xszj.mba.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by Ybx on 2016/12/9.
 */

public class NewsAdapter extends BaseRecyclerAdapter<String> {


    public NewsAdapter(List<String> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;
//        ImageLoader.getInstance().displayImage(bean.newsImageUrl, holder.questionIv, KaowenAppLication.options);
        holder.titleTv.setText("title");
        holder.contentTv.setText("content");
        holder.timeTv.setText("time");
        holder.itemView.setTag(position);

    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, final int viewType) {
        final ViewHolder holder;
        final View itemView = inflater.inflate(R.layout.item_news_layout, parent, false);
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

        ImageView questionIv;
        TextView titleTv, contentTv, timeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            questionIv = (ImageView) itemView.findViewById(R.id.questionIv);
            titleTv = (TextView) itemView.findViewById(R.id.title_tv);
            timeTv = (TextView) itemView.findViewById(R.id.time_tv);
            contentTv = (TextView) itemView.findViewById(R.id.content_tv);

        }
    }

}

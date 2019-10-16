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
import com.xszj.mba.bean.FindBean;

import java.util.List;

/**
 * Created by Ybx on 2016/12/22.
 */

public class NearTermFAdapter extends BaseRecyclerAdapter<FindBean> {

    public NearTermFAdapter(List<FindBean> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        FindBean date = list.get(position);
        holder.tv_title.setText("" + date.getNewsTitle());
        holder.tv_name_time.setText("" + date.getNewsAuthor());
        if (date.getCreateDate()!=null){
            holder.tv_time.setText("" + date.getCreateDate());
        }
        ImageLoader.getInstance().displayImage(date.getNewsCover(), holder.imageView, NimApplication.imageOptions);

        holder.itemView.setTag(position);


    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder;
        final View itemView = inflater.inflate(R.layout.item_nearterm_fragment_layout, parent, false);
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
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_name_time = (TextView) itemView.findViewById(R.id.tv_name_time);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);

        }
    }
}

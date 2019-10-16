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
import com.xszj.mba.bean.MyCollectBean;

import java.util.List;

/**
 * Created by swh on 2016/12/22.
 * 收藏的adapter
 */

public class CollectFAdapter extends BaseRecyclerAdapter<MyCollectBean.DataBean.NewsListBean> {

    public CollectFAdapter(List<MyCollectBean.DataBean.NewsListBean> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        MyCollectBean.DataBean.NewsListBean date = list.get(position);
        holder.iv_follow_is.setImageResource(R.mipmap.icon_my_collect);
        holder.tv_title.setText("" + date.getNewsTitle());
        holder.tv_name_time.setText("" + date.getCreateDate());

        ImageLoader.getInstance().displayImage("" + date.getNewsCover(), holder.imageView, NimApplication.imageOptions);

        holder.itemView.setTag(position);
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        View itemView = inflater.inflate(R.layout.item_nearterm_fragment_layout, parent, false);
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
        private ImageView iv_follow_is;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_name_time = (TextView) itemView.findViewById(R.id.tv_name_time);
            iv_follow_is = (ImageView) itemView.findViewById(R.id.iv_follow_is);

        }
    }
}

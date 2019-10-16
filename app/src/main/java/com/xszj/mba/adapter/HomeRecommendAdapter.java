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
import com.xszj.mba.bean.HomeDataBean;
import java.util.List;

/**
 * Created by swh on 2016/12/9.
 */

public class HomeRecommendAdapter extends BaseRecyclerAdapter<HomeDataBean.DataBean.WrittenExaminationListBean> {


    public HomeRecommendAdapter(List<HomeDataBean.DataBean.WrittenExaminationListBean> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        ImageLoader.getInstance().displayImage(list.get(position).getDictionaryRemark(), holder.iv_recommend, NimApplication.imageOptions);
        holder.titleTv.setText(list.get(position).getDictionaryName());
        holder.titleTv.setVisibility(View.VISIBLE);
        holder.tv_hour_peo.setText(list.get(position).getDictionaryName());
        holder.itemView.setTag(position);
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, final int viewType) {
        final ViewHolder holder;
        final View itemView = inflater.inflate(R.layout.home_recommed_item, parent, false);
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

        ImageView iv_recommend;
        TextView titleTv, tv_hour_peo;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_recommend = (ImageView) itemView.findViewById(R.id.iv_recommend);
            titleTv = (TextView) itemView.findViewById(R.id.tv_title);
            tv_hour_peo = (TextView) itemView.findViewById(R.id.tv_hour_peo);
        }
    }

}

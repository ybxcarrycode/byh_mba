package com.xszj.mba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.base.BaseRecyclerViewHolder;
import com.xszj.mba.bean.PerfectSchoolAndTypeBean;

import java.util.List;

/**
 * Created by swh on 2016/12/22.
 * 我的提问adapter
 */

public class PerfectDetailFAdapter extends BaseRecyclerAdapter<PerfectSchoolAndTypeBean> {


    public PerfectDetailFAdapter(List<PerfectSchoolAndTypeBean> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tv_zy_submajor.setText(list.get(position).getDictionaryName());

        holder.itemView.setTag(position);
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        View itemView = inflater.inflate(R.layout.perfect_detail_item, parent, false);
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

        private TextView tv_zy_submajor;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_zy_submajor = (TextView) itemView.findViewById(R.id.tv_zy_submajor);
        }
    }
}

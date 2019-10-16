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
import com.xszj.mba.bean.SchoolListBean.DataBean.HotListBean;
import java.util.List;

/**
 * Created by swh on 2016/12/22.
 * 学习进度的adapter
 */

public class SchoolListFAdapter extends BaseRecyclerAdapter<HotListBean> {

    public SchoolListFAdapter(List<HotListBean> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;

        HotListBean hotListBean = list.get(position);
        ImageLoader.getInstance().displayImage(hotListBean.getAcademyLogo(), holder.img_school, NimApplication.imageOptions);
        holder.tv_school_name.setText(hotListBean.getAcademyName());
        holder.tv_people_num.setText( "招生人数:"+hotListBean.getRecruitStudentsNumber());
        holder.tv_money.setText("学费:"+hotListBean.getLearnCost()+"");

        holder.itemView.setTag(position);
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        View itemView = inflater.inflate(R.layout.school_list_head_list_item, parent, false);
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
        private ImageView img_school;
        private TextView tv_school_name;
        private TextView tv_people_num;
        private TextView tv_money;

        public ViewHolder(View itemView) {
            super(itemView);
            img_school = (ImageView) itemView.findViewById(R.id.img_school);
            tv_school_name = (TextView) itemView.findViewById(R.id.tv_school_name);
            tv_people_num = (TextView) itemView.findViewById(R.id.tv_people_num);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
        }
    }
}

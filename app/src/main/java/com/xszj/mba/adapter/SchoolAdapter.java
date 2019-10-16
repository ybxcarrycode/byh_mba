package com.xszj.mba.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xszj.mba.R;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.base.BaseRecyclerViewHolder;
import com.xszj.mba.bean.SchoolAnalysisModel;
import com.xszj.mba.view.widget.Tag;
import com.xszj.mba.view.widget.TagListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ybx on 2016/12/12.
 */

public class SchoolAdapter extends BaseRecyclerAdapter<String> {

    private List<Tag> mTags = new ArrayList<Tag>();
    private ArrayList<SchoolAnalysisModel> majors = null;


    public SchoolAdapter(List<String> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;

//        SchoolAnalysisModel model = majors.get(position);
//
//        if (majors != null && majors.get(position).features != null) {
//
//            if (majors.get(position).features.size() > 0) {
//                mTags = new ArrayList<Tag>();
//                setUpData(majors.get(position).features);
//                holder.mTagview.setTags(mTags);
//            }
//
//        }
//
//
//        if (!TextUtils.isEmpty("")) {
//            ImageLoader.getInstance().displayImage("", holder.mIv_sc_logo);
//        }
//
//        holder.mTv_sc_middle_aqx.setText("");
//        holder.mTv_sc_middle_mkx.setText(model.aveScoreLi + "");
//        if (Integer.parseInt(model.rank) == 1) {
//            setTopThree(holder.mTv_paming, R.mipmap.first_crown_icon);
//        } else if (Integer.parseInt(model.rank) == 2) {
//            setTopThree(holder.mTv_paming, R.mipmap.second_crown_icon);
//        } else if (Integer.parseInt(model.rank) == 3) {
//            setTopThree(holder.mTv_paming, R.mipmap.third_crown_icon);
//        } else {
//            holder.mTv_paming.setCompoundDrawables(null, null, null, null);
//            holder.mTv_paming.setBackgroundResource(R.drawable.round_school_rank_bg);
//            holder.mTv_paming.setText(model.rank);
//
//        }
//        holder.mTv_sc_name.setText(model.name);

        holder.itemView.setTag(position);
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder;
        final View itemView = inflater.inflate(R.layout.item_school_layout, parent, false);
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


    /**
     * 功能:设置特色
     *
     * @param titles
     * @author yinxuejian
     */
    private void setUpData(ArrayList<String> titles) {
        for (int i = 0; i < titles.size(); i++) {
            String tc = titles.get(i);
            Tag tag = new Tag();
            tag.setId(i);
            tag.setChecked(true);
            tag.setTitle(tc);
            mTags.add(tag);

        }
    }


    /**
     * 功能:设置前三名排名
     *
     * @author yinxuejian
     */
    @SuppressLint("NewApi")
    private void setTopThree(TextView view, int rId) {
        view.setBackground(null);
        Drawable nav_up = context.getResources().getDrawable(rId);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        view.setCompoundDrawables(null, null, nav_up, null);
    }


    static class ViewHolder extends BaseRecyclerViewHolder {
        private ImageView mIv_sc_logo;
        private LinearLayout mLl_sc_right;
        private LinearLayout mLl_sc_middle_top;
        private TextView mTv_paming;
        private TextView mTv_sc_name;
        private LinearLayout mLl_sc_middle_mid;
        private TextView mTv_sc_middle_mkx;
        private TextView mTv_sc_middle_aqx;
        private TagListView mTagview;

        public ViewHolder(View itemView) {
            super(itemView);
            mIv_sc_logo = (ImageView) itemView.findViewById(R.id.iv_sc_logo);
            mLl_sc_right = (LinearLayout) itemView.findViewById(R.id.ll_sc_right);
            mLl_sc_middle_top = (LinearLayout) itemView.findViewById(R.id.ll_sc_middle_top);
            mTv_paming = (TextView) itemView.findViewById(R.id.tv_paming);
            mTv_sc_name = (TextView) itemView.findViewById(R.id.tv_sc_name);
            mLl_sc_middle_mid = (LinearLayout) itemView.findViewById(R.id.ll_sc_middle_mid);
            mTv_sc_middle_mkx = (TextView) itemView.findViewById(R.id.tv_sc_middle_mkx);
            mTv_sc_middle_aqx = (TextView) itemView.findViewById(R.id.tv_sc_middle_aqx);
            mTagview = (TagListView) itemView.findViewById(R.id.tagview);
            mTagview.setTagBgs(true);
            mTagview.setTagViewTextColorRes(R.color.text_color_007aff);
        }
    }

}

package com.xszj.mba.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.base.BaseRecyclerViewHolder;
import com.xszj.mba.bean.AboutTechUpperBean;
import com.xszj.mba.utils.StringUtil;

import java.util.List;

/**
 * Created by swh on 2016/12/22.
 * 关注老师的adapter
 */

public class FollowTeacherFAdapter extends BaseRecyclerAdapter<AboutTechUpperBean> {

    public FollowTeacherFAdapter(List<AboutTechUpperBean> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        AboutTechUpperBean dataBean = list.get(position);
        holder.nameTv.setText("" + dataBean.getNickName());
        holder.titleTv.setText("" + dataBean.getTeacherTitle());
        holder.signatureTv.setText("" + dataBean.getPersonalSign());
        holder.mentorTypeTv.setText(StringUtil.getUserTypeName(dataBean.getUserType()));
        holder.roleTag1Tv.setText("" + dataBean.getAcademyName());
        holder.likeCountTv.setText(""+ dataBean.getFocusNum());
        ImageLoader.getInstance().displayImage(dataBean.getHeadPic(), holder.headIv, NimApplication.imageOptions);

        if ("0".equals(dataBean.getIsFocus())) {
            holder.iv_follow.setImageResource(R.mipmap.follow_no);
        } else {
            holder.iv_follow.setImageResource(R.mipmap.follow_yes);
        }
        holder.mentorTypeTv.setTextColor(0xffffc43b);
        if ("14".equals(dataBean.getUserType())) {
            holder.left_tv.setImageResource(R.mipmap.icon_school_detail_teacher);
            holder.mentorTypeTv.setTextColor(0xffffc43b);
        } else if ("15".equals(dataBean.getUserType())) {
            holder.left_tv.setImageResource(R.mipmap.icon_school_detail_expert);
            holder.mentorTypeTv.setTextColor(0xffffc43b);
        } else if ("13".equals(dataBean.getUserType())) {
            holder.left_tv.setImageResource(R.mipmap.icon_school_detail_upperclassman);
            holder.mentorTypeTv.setTextColor(0xff915743);
        }

        holder.itemView.setTag(position);
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        View itemView = inflater.inflate(R.layout.expert_item_layout, parent, false);
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
        ImageView headIv;
        ImageView iv_follow;
        ImageView left_tv;
        TextView likeTv, likeCountTv;
        TextView nameTv;
        TextView titleTv;
        TextView signatureTv;
        TextView chatTv;
        TextView roleTag1Tv;
        TextView mentorTypeTv;
        LinearLayout ll_follow;

        public ViewHolder(View itemView) {
            super(itemView);

            headIv = (ImageView) itemView.findViewById(R.id.headIv);
            left_tv = (ImageView) itemView.findViewById(R.id.left_tv);
            likeTv = (TextView) itemView.findViewById(R.id.likeTv);
            likeCountTv = (TextView) itemView.findViewById(R.id.like_count_tv);
            iv_follow = (ImageView) itemView.findViewById(R.id.iv_follow_is);
            chatTv = (TextView) itemView.findViewById(R.id.chatTv);
            nameTv = (TextView) itemView.findViewById(R.id.nameTv);
            titleTv = (TextView) itemView.findViewById(R.id.titleTv);
            signatureTv = (TextView) itemView.findViewById(R.id.signatureTv);
            roleTag1Tv = (TextView) itemView.findViewById(R.id.roletag1_tv);
            mentorTypeTv = (TextView) itemView.findViewById(R.id.mentor_vip_type_tv);
            ll_follow = (LinearLayout) itemView.findViewById(R.id.ll_follow);

        }
    }
}

package com.xszj.mba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.bean.HomeDataBean;

import java.util.List;


/**
 * Created by QQ on 2015/9/23.
 * 我的书籍课程Adapter
 */
public class HomeInterviewAdapter extends BaseAdapter {
    private Context context;
    private List<HomeDataBean.DataBean.InterviewListBean> lists;

    public HomeInterviewAdapter(Context context, List<HomeDataBean.DataBean.InterviewListBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_recommed_item, null);
            holder = new ViewHolder();
            holder.iv_recommend = (ImageView) convertView.findViewById(R.id.iv_recommend);
            holder.titleTv = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(lists.get(position).getDictionaryRemark(),holder.iv_recommend, NimApplication.imageOptions);
        holder.titleTv.setText(lists.get(position).getDictionaryName());

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_recommend;
        TextView titleTv;
    }
}

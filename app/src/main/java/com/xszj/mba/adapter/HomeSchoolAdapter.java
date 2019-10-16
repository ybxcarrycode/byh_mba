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
public class HomeSchoolAdapter extends BaseAdapter {
    private Context context;
    private List<HomeDataBean.DataBean.AcademyListBean> lists;

    public HomeSchoolAdapter(Context context, List<HomeDataBean.DataBean.AcademyListBean> lists) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.home_school_grid_item, null);
            holder = new ViewHolder();
            holder.school_pic = (ImageView) convertView.findViewById(R.id.school_pic);
            holder.school_name = (TextView) convertView.findViewById(R.id.school_name);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(lists.get(position).getAcademyLogo(),holder.school_pic, NimApplication.imageOptions);
        holder.school_name.setText(lists.get(position).getAcademyName());

        return convertView;
    }

    static class ViewHolder {
        ImageView school_pic;
        TextView school_name;
    }
}

package com.xszj.mba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xszj.mba.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by QQ on 2015/9/23.
 * 直播精华和直播互动adapter
 */
public class ZhiboDMAdapter extends BaseAdapter {
    private Context context;
    private List<String> list = new ArrayList<String>();

    public ZhiboDMAdapter(Context context, List<String> lists) {
        this.context = context;
        this.list = lists;


    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.zhibo_danmu_list_item, null);
            holder = new ViewHolder();
            holder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);//书名
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        //加载书的图片
//        ImageLoader.getInstance().displayImage(, holder.tv_time_hour, options);

        holder.tv_msg.setText(list.get(position) + "");

        return convertView;
    }


    static class ViewHolder {
        TextView tv_msg;
    }

}

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
import com.xszj.mba.bean.SchoolListBean.DataBean.FamousListBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/1/11.
 */

public class SchoolListAdapter extends BaseAdapter {

    private Context context;
    private List<FamousListBean> list = new ArrayList<>();

    public SchoolListAdapter(Context context, List<FamousListBean> lists) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.school_list_head_list_item, null);
            holder = new ViewHolder();
            holder.img_school = (ImageView) convertView.findViewById(R.id.img_school);//书名
            holder.tv_school_name = (TextView) convertView.findViewById(R.id.tv_school_name);//书名
            holder.tv_people_num = (TextView) convertView.findViewById(R.id.tv_people_num);//书名
            holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);//书名
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FamousListBean famousListBean = list.get(position);

        //加载书的图片
        ImageLoader.getInstance().displayImage(famousListBean.getAcademyLogo(), holder.img_school, NimApplication.imageOptions);

        holder.tv_school_name.setText(famousListBean.getAcademyName());
        holder.tv_people_num.setText("招生人数:"+famousListBean.getRecruitStudentsNumber());
        holder.tv_money.setText("学费:"+famousListBean.getLearnCost());
        return convertView;
    }


    static class ViewHolder {
        TextView tv_school_name,tv_people_num,tv_money;
        ImageView img_school;
    }
}

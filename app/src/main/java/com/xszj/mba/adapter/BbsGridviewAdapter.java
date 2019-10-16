package com.xszj.mba.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */

public class BbsGridviewAdapter extends BaseAdapter {
    private Context context;
    private List<String> mList = new ArrayList<>();

    public BbsGridviewAdapter(Context context,List<String> list) {
        this.context = context;
        this.mList = list;
    }


    //可以return images.lenght(),在这里返回Integer.MAX_VALUE
    //是为了使图片循环显示
    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();

        View view = View.inflate(context, R.layout.item_bbs_gridview, null);

        viewHolder.image_view = (ImageView) view.findViewById(R.id.imageView);

        ImageLoader.getInstance().displayImage(mList.get(position)+"?x-oss-process=image/resize,m_mfit,h_500,w_500", viewHolder.image_view, NimApplication.imageOptions);

        return view;

    }


    static class ViewHolder {
        ImageView image_view;
    }

}

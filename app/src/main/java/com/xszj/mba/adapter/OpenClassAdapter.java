package com.xszj.mba.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.bean.HomeDataBean;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.ServiceUtils;


import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */

public class OpenClassAdapter extends BaseAdapter {

    private Context mContext;
    private List<HomeDataBean.DataBean.LiveListBean> list;

    public OpenClassAdapter(Context context, List<HomeDataBean.DataBean.LiveListBean> list) {
        this.mContext = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.home_head_openclass_item, null);
            holder.image_head = (ImageView) convertView.findViewById(R.id.image_head);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.rl_type1 = (RelativeLayout) convertView.findViewById(R.id.rl_type1);
            holder.rl_type2 = (RelativeLayout) convertView.findViewById(R.id.rl_type2);
            holder.tv_time_pic = (TextView) convertView.findViewById(R.id.tv_time_pic);
            holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomeDataBean.DataBean.LiveListBean liveListBean = list.get(position);

        holder.tv_title.setText(liveListBean.getLiveName());
        holder.tv_time.setText(liveListBean.getStartTime());
        ImageLoader.getInstance().displayImage(liveListBean.getCover(),holder.image_head);

        //0 正在直播 1 是即将 2 已预约 -1 已过期
        if (liveListBean.getShowtype().equals("0")) {
            holder.rl_type1.setVisibility(View.GONE);
            holder.rl_type2.setVisibility(View.VISIBLE);
        } else if(liveListBean.getShowtype().equals("2")){
            holder.rl_type1.setVisibility(View.VISIBLE);
            holder.rl_type2.setVisibility(View.GONE);
            holder.tv_time_pic.setText("已预约");
            holder.iv_pic.setImageResource(R.drawable.home_icon_appointmented);
            holder.rl_type1.setOnClickListener(new SubClickListener(liveListBean, 1));
        }else{
            holder.rl_type1.setVisibility(View.VISIBLE);
            holder.rl_type2.setVisibility(View.GONE);
            holder.tv_time_pic.setText("预约");
            holder.iv_pic.setImageResource(R.drawable.home_icon_appointment);
            holder.rl_type1.setOnClickListener(new SubClickListener(liveListBean, 0));
        }
        holder.rl_type1.setTag(position);

        return convertView;
    }


    private class SubClickListener implements View.OnClickListener {

        private HomeDataBean.DataBean.LiveListBean  liveListBean;
        private int num;

        private SubClickListener(HomeDataBean.DataBean.LiveListBean liveListBean, int num) {
            this.liveListBean = liveListBean;
            this.num = num;
        }

        @Override
        public void onClick(View v) {
            // 先判断是否登陆,登陆才能发
            if (null == NimApplication.user || NimApplication.user.equals("")) {
                CommonUtil.showLoginddl(mContext);
            } else {
                if (num==0){
                    subtoserver(liveListBean);
                }else{
                    Log.e("type", "dddddd");
                }
            }
        }
    }

    private void subtoserver(final HomeDataBean.DataBean.LiveListBean liveListBean) {

        String url =  ServiceUtils.SERVICE_ABOUT_HOME+"/v1/home/subscribeLive.json?userId="+NimApplication.user+"&liveVideoId="+liveListBean.getLiveVideoId();
        Log.e("ddddd",url);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("ddddd",responseInfo.result);
                liveListBean.setShowtype("2");
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("ddddd",s);
            }
        });
    }

    private class ViewHolder {
        // 个人简介
        private RelativeLayout rl_type1, rl_type2;
        private TextView tv_title, tv_time;
        private TextView tv_time_pic;
        private ImageView image_head, iv_pic;

        
    }
}

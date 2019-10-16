package com.xszj.mba.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.base.BaseRecyclerViewHolder;
import com.xszj.mba.bean.EvaluateTeacherListBean.DataBean;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.ServiceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by swh on 2016/12/22.
 */

public class EvaluteTeacherFAdapter extends BaseRecyclerAdapter<DataBean> {

    public EvaluteTeacherFAdapter(List<DataBean> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;

        DataBean dataBean = list.get(position);

        ImageLoader.getInstance().displayImage(dataBean.getHeadPic(),holder.imageView, NimApplication.imageOptions);
        holder.tvc_name.setText(dataBean.getNickName());
        holder.tvc_time.setText(dataBean.getCreateDate());
        holder.tvc_type.setText(dataBean.getPersonalSign());
        holder.tvc_comment.setText(dataBean.getContent());
        holder.tvc_praise_count.setText(dataBean.getPraiseCount()+"有用");
        if ("0".equals(dataBean.getIsPraise())){
            holder.tvc_praise_count.setBackgroundResource(R.drawable.tv_ispraise_backfround_nomal);
        }else{
            holder.tvc_praise_count.setBackgroundResource(R.drawable.tv_ispraise_backfround_press);
        }


        holder.tvc_praise_count.setTag(dataBean);
        holder.tvc_praise_count.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(context);
                    return;
                }
                DataBean experBean = (DataBean) arg0.getTag();
                if ("1".equals(experBean.getIsPraise())) {
                    likeToServer(experBean, "0");
                } else {
                    likeToServer(experBean, "1");
                }
            }
        });

        holder.itemView.setTag(position);


    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder;
        final View itemView = inflater.inflate(R.layout.evalute_teacher_list_item, parent, false);
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


    // 请求网络 关注
    public void likeToServer(final DataBean bean, final String type) {

        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/consult/praiseTeacherComment.json?";

        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("commentId", bean.getCommentId());
        params.addBodyParameter("praiseStatus",type);

        new HttpUtils().send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("dddddd", responseInfo.result);

                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    String returnCode = object.optString("returnCode", null);
                    if (returnCode.equals("0")) {
                        if (type.equals("0")) {
                            bean.setIsPraise("0");
                            bean.setPraiseCount((Integer.parseInt(bean.getPraiseCount()) - 1) + "");
                        } else {
                            bean.setIsPraise("1");
                            bean.setPraiseCount((Integer.parseInt(bean.getPraiseCount()) + 1) + "");
                        }

                        notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


    static class ViewHolder extends BaseRecyclerViewHolder {
        private ImageView imageView;
        private TextView tvc_name;
        private TextView tvc_time;
        private TextView tvc_type;
        private TextView tvc_comment;
        private TextView tvc_left;
        private TextView tvc_praise_count;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tvc_name = (TextView) itemView.findViewById(R.id.tvc_name);
            tvc_time = (TextView) itemView.findViewById(R.id.tvc_time);
            tvc_type = (TextView) itemView.findViewById(R.id.tvc_type);
            tvc_comment = (TextView) itemView.findViewById(R.id.tvc_comment);
            tvc_left = (TextView) itemView.findViewById(R.id.tvc_left);
            tvc_praise_count = (TextView) itemView.findViewById(R.id.tvc_praise_count);
        }
    }
}

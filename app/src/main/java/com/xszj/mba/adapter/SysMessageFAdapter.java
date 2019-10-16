package com.xszj.mba.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseRecyclerAdapter;
import com.xszj.mba.base.BaseRecyclerViewHolder;
import com.xszj.mba.bean.MyMsgBean;
import com.xszj.mba.utils.DialogDoubleHelper;
import com.xszj.mba.utils.ServiceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by swh on 2016/12/22.
 */

public class SysMessageFAdapter extends BaseRecyclerAdapter<MyMsgBean> {

    public SysMessageFAdapter(List<MyMsgBean> list, Context context) {
        super(list, context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder viewHolder, final int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        MyMsgBean date = list.get(position);

//        holder.tv_message_read_type.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        holder.imt_delete.setOnClickListener(new DeleteListener(position));

        ImageLoader.getInstance().displayImage(date.getMessageUrl(), holder.imageView, NimApplication.imageOptions);
        holder.tvc_message_title.setText(date.getMessageContent());
        holder.tvc_time.setText(date.getMessageReadDate());

//        //设置是否已读
//        if ("0".equals(date.getMessageStatus())) {
//            holder.tv_message_read_type.setTextColor(Color.parseColor("#979797"));
//            Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_message_no);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            holder.tv_message_read_type.setCompoundDrawables(drawable, null, null, null);
//        } else if ("1".equals(date.getMessageStatus())) {
//            holder.tv_message_read_type.setTextColor(Color.parseColor("#ffc77a"));
//            Drawable drawable = context.getResources().getDrawable(R.mipmap.icon_message_yes);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            holder.tv_message_read_type.setCompoundDrawables(drawable, null, null, null);
//        }

        //设置标签
        if ("1".equals(date.getMessageType())) {
            holder.tvc_message_type.setText("直播课程");
            holder.tvc_message_type.setBackgroundResource(R.drawable.sysmessage_fragment_item_bg_red);
        } else if ("2".equals(date.getMessageType())) {
            holder.tvc_message_type.setText("线下活动");
            holder.tvc_message_type.setBackgroundResource(R.drawable.sysmessage_fragment_item_bg_blue);
        } else if ("3".equals(date.getMessageType())) {
            holder.tvc_message_type.setText("院校资讯");
            holder.tvc_message_type.setBackgroundResource(R.drawable.sysmessage_fragment_item_bg_black);
        }

        holder.itemView.setTag(position);


    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        final ViewHolder holder;
        final View itemView = inflater.inflate(R.layout.system_message_item, parent, false);
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
        private ImageView imageView;
        private TextView tvc_message_title;
        private TextView tvc_message_type;
        private TextView tvc_time;
        private TextView tv_message_read_type;
        private ImageButton imt_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tvc_message_title = (TextView) itemView.findViewById(R.id.tvc_message_title);
            tvc_message_type = (TextView) itemView.findViewById(R.id.tvc_message_type);
            tvc_time = (TextView) itemView.findViewById(R.id.tvc_time);
//            tv_message_read_type = (TextView) itemView.findViewById(R.id.tv_message_read_type);
            imt_delete = (ImageButton) itemView.findViewById(R.id.imt_delete);
        }
    }


    class DeleteListener implements View.OnClickListener {

        private int position;

        public DeleteListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            DialogDoubleHelper dialogDoubleHelpernew = new DialogDoubleHelper(context);
            dialogDoubleHelpernew.setMsgTxt("确定要删除此消息?");
            dialogDoubleHelpernew.setLeftTxt("取消");
            dialogDoubleHelpernew.setRightTxt("确定");
            dialogDoubleHelpernew.setDialogOnclickListener(new DialogDoubleHelper.DialogDoubleHelperOnclickListener() {
                @Override
                public void setLeftOnClickListener(Dialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void setRightOnClickListener(Dialog dialog) {
                    delOneMsg( position);
                    dialog.dismiss();
                }
            });
            dialogDoubleHelpernew.show();

        }
    }


    protected void delOneMsg(int pos) {
        String messageId = list.get(pos).getMessageId();
        final String url1 = ServiceUtils.SERVICE_ABOUT_SCHOOL + "/v1/message_center/delete_my_message.json?messageId=" + messageId;
        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(200);
        http.send(HttpRequest.HttpMethod.GET, url1, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {

                String result = responseInfo.result.toString();
                if ("".equals(result)) {
                    return;
                }
                String code;
                try {
                    JSONObject object = new JSONObject(result);
                    code = object.getString("returnCode");
                    if (null != code && "0".equals(code)) {
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "删除失败，请重试...", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(context, "删除失败，请重试...", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

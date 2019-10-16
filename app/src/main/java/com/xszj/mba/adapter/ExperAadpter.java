package com.xszj.mba.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.net.nim.demo.NimApplication;
import com.net.nim.demo.session.SessionHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.StatusCode;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.activity.EvaluateDetailActivity;
import com.xszj.mba.bean.AboutTechUpperBean;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class ExperAadpter extends XsCustomerBaseAdapter<AboutTechUpperBean> {

    public ExperAadpter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.expert_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.headIv = (ImageView) convertView.findViewById(R.id.headIv);
            viewHolder.likeTv = (TextView) convertView.findViewById(R.id.likeTv);
            viewHolder.likeCountTv = (TextView) convertView.findViewById(R.id.like_count_tv);
            viewHolder.iv_follow = (ImageView) convertView.findViewById(R.id.iv_follow_is);
            viewHolder.chatTv = (TextView) convertView.findViewById(R.id.chatTv);
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
            viewHolder.titleTv = (TextView) convertView.findViewById(R.id.titleTv);
            viewHolder.signatureTv = (TextView) convertView.findViewById(R.id.signatureTv);
            viewHolder.roleTag1Tv = (TextView) convertView.findViewById(R.id.roletag1_tv);
            viewHolder.mentorTypeTv = (TextView) convertView.findViewById(R.id.mentor_vip_type_tv);
            viewHolder.ll_follow = (LinearLayout) convertView.findViewById(R.id.ll_follow);
            viewHolder.left_tv = (ImageView) convertView.findViewById(R.id.left_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AboutTechUpperBean experBean = mList.get(position);
        String hasFollowed = experBean.getIsFollow();

        if ("1".equals(hasFollowed)) {
            viewHolder.iv_follow.setImageResource(R.mipmap.follow_yes);
        } else {
            viewHolder.iv_follow.setImageResource(R.mipmap.follow_no);
        }
        viewHolder.likeCountTv.setText(experBean.getFocusNum() + "关注");
        viewHolder.chatTv.setOnClickListener(new ConsultClickListener(experBean.getImUser()));
        viewHolder.likeTv.setOnClickListener(new EvaluateClickListener(experBean.getUserId()));
        viewHolder.mentorTypeTv.setText(StringUtil.getUserTypeName(experBean.getUserType()));

        if ("14".equals(experBean.getUserType())) {
            viewHolder.left_tv.setImageResource(R.mipmap.icon_school_detail_teacher);
            viewHolder.mentorTypeTv.setTextColor(0xffffc43b);
        } else if ("15".equals(experBean.getUserType())) {
            viewHolder.left_tv.setImageResource(R.mipmap.icon_school_detail_expert);
            viewHolder.mentorTypeTv.setTextColor(0xffffc43b);
        }else if ("13".equals(experBean.getUserType())){
            viewHolder.left_tv.setImageResource(R.mipmap.icon_school_detail_upperclassman);
            viewHolder.mentorTypeTv.setTextColor(0xff915743);
        }

        viewHolder.ll_follow.setTag(experBean);
        viewHolder.ll_follow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // 先判断是否登陆,登陆才能发
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    AboutTechUpperBean experBean = (AboutTechUpperBean) arg0.getTag();
                    if ("1".equals(experBean.getIsFollow())) {
                        likeToServer(experBean, "0");
                    } else {
                        likeToServer(experBean, "1");
                    }
                }
            }
        });

        viewHolder.nameTv.setText(experBean.getNickName());
        if (!TextUtils.isEmpty(experBean.getTeacherTitle())) {
            viewHolder.titleTv.setText(experBean.getTeacherTitle() + "");
            viewHolder.roleTag1Tv.setText(experBean.getAcademyName() + "");
        }

        if (StringUtil.notEmpty(experBean.getPersonalSign())) {
            viewHolder.signatureTv.setText(experBean.getPersonalSign());
        } else {
            viewHolder.signatureTv.setText("未填写");
        }

        ImageLoader.getInstance().displayImage(experBean.getHeadPic(), viewHolder.headIv,NimApplication.imageOptions);
        viewHolder.chatTv.setTag(experBean);
        viewHolder.likeTv.setTag(experBean);
        return convertView;
    }


    private class ConsultClickListener implements OnClickListener {

        private String huanxinToken;

        private ConsultClickListener(String huanxinToken) {

            this.huanxinToken = huanxinToken;
        }

        @Override
        public void onClick(View v) {

            // 先判断是否登陆,登陆才能发
            if (null == NimApplication.user || NimApplication.user.equals("")) {
                CommonUtil.showLoginddl(mContext);
            } else {
                Log.e("dddddd",NIMClient.getStatus()+"====");
                if (NIMClient.getStatus()== StatusCode.LOGINED){
                    SessionHelper.startP2PSession(mContext, huanxinToken);
                }else {
                    CommonUtil.showLoginddl(mContext);
                }
            }

        }
    }


    private class EvaluateClickListener implements OnClickListener {

        private String expertUuId;

        private EvaluateClickListener(String expertUuId) {
            this.expertUuId = expertUuId;
        }

        @Override
        public void onClick(View v) {
            if (!TextUtils.isEmpty(expertUuId)) {
                // 先判断是否登陆,登陆才能发
                if (null == NimApplication.user || NimApplication.user.equals("")) {
                    CommonUtil.showLoginddl(mContext);
                } else {
                    mContext.startActivity(new Intent(mContext, EvaluateDetailActivity.class).putExtra("expertUuId", expertUuId));
                }
            }
        }
    }

    // 请求网络 关注
    public void likeToServer(final AboutTechUpperBean bean, final String type) {

        String url;

        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("expertId", bean.getUserId());
        if (type.equals("0")) {
            url = ServiceUtils.SERVICE_ABOUT_HOME+"/v1/consult/cancleFocusExpert.json?";
        } else {
            url = ServiceUtils.SERVICE_ABOUT_HOME+"/v1/consult/focusExpert.json?";
        }

        new HttpUtils().send(HttpRequest.HttpMethod.POST, url, params,new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("dddddd", responseInfo.result);

                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    String returnCode = object.optString("returnCode", null);
                    if (returnCode.equals("0")) {
                        if (type.equals("0")) {
                            bean.setIsFollow("0");
                            bean.setFocusNum((Integer.parseInt(bean.getFocusNum()) - 1) + "");
                        } else {
                            bean.setIsFollow("1");
                            bean.setFocusNum((Integer.parseInt(bean.getFocusNum()) + 1) + "");
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


    class ViewHolder {
        ImageView headIv;
        ImageView iv_follow;
        ImageView left_tv;
        TextView likeTv, likeCountTv;
        TextView nameTv;
        TextView titleTv;
        TextView signatureTv;
        TextView fromTv;
        TextView chatTv;
        TextView roleTag1Tv;
        TextView mentorTypeTv;
        LinearLayout ll_follow;
    }
}

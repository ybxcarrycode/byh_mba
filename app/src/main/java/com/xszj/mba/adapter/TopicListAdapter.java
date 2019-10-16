/**
 * @author yinxuejian
 * @version 创建时间：2015-11-4 下午5:31:12
 */

package com.xszj.mba.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.umeng.comm.core.listener.Listeners;
import com.xszj.mba.R;
import com.xszj.mba.activity.ImageShowActivity;
import com.xszj.mba.bean.KaoquanBean;
import com.xszj.mba.utils.CircleImageView;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.ServiceUtils;
import com.xszj.mba.utils.StringUtil;
import com.xszj.mba.view.ExpandableTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TopicListAdapter extends XsCustomerBaseAdapter<KaoquanBean.DataBean> {


    private SparseBooleanArray mCollapsedStatus;
    private Context mContext;

    public TopicListAdapter(Activity context) {
        super(context);
        this.mContext = context;
        mCollapsedStatus = new SparseBooleanArray();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.topic_listview_item_layout, null);
            holder.nickNameTv = (TextView) convertView.findViewById(R.id.nickNameTv);
            holder.postDetaiTv = (ExpandableTextView) convertView.findViewById(R.id.expand_text_view);
            holder.commentCountTv = (TextView) convertView.findViewById(R.id.commentCountTv);
            holder.likeCountTv = (TextView) convertView.findViewById(R.id.likeCountTv);
            holder.userDescTv = (TextView) convertView.findViewById(R.id.userDescTv);
            holder.pushTimeTv = (TextView) convertView.findViewById(R.id.pushTimeTv);
            holder.groupNameTv = (TextView) convertView.findViewById(R.id.groupNameTv);
            holder.likeTv = (TextView) convertView.findViewById(R.id.like_tv);
            holder.userImgIv = (CircleImageView) convertView.findViewById(R.id.userImgIv);
            holder.grid_pic_list = (GridView) convertView.findViewById(R.id.grid_pic_list);
            holder.commentTv = (TextView) convertView.findViewById(R.id.comment_tv);
            holder.likeTv.setOnClickListener(listener);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final KaoquanBean.DataBean bean = mList.get(position);

        if (bean.getImageUrls() != null && bean.getImageUrls().size() > 0 && !bean.getImageUrls().get(0).equals("")) {
            holder.grid_pic_list.setVisibility(View.VISIBLE);
            BbsGridviewAdapter adapter = new BbsGridviewAdapter(mContext, bean.getImageUrls());
            holder.grid_pic_list.setAdapter(adapter);
        } else {
            holder.grid_pic_list.setVisibility(View.GONE);
        }

        holder.grid_pic_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gotoImgShow(position, bean.getImageUrls());
            }
        });

        if (StringUtil.notEmpty(bean.getPersonalSign())) {
            holder.userDescTv.setText(bean.getPersonalSign());
        } else {
            holder.userDescTv.setText("");

        }
        if (StringUtil.notEmpty(bean.getHeadPic())) {
            ImageLoader.getInstance().displayImage(bean.getHeadPic(), holder.userImgIv, NimApplication.imageOptions);
        } else {
            holder.userImgIv.setImageResource(R.drawable.tu_zhanweitu_houzi_gray);
        }

        if (bean.getIsFocus().equals("1")) {
            holder.likeTv.setTextColor(mContext.getResources().getColor(R.color.blue_bg));
            holder.likeTv.setBackgroundResource(R.drawable.fillet_attention_press_btn);
            holder.likeTv.setText("已关注");
        } else {
            holder.likeTv.setText("+关注");
            holder.likeTv.setBackgroundResource(R.drawable.fillet_attention_btn);
            holder.likeTv.setTextColor(mContext.getResources().getColor(R.color.c1c1c1));
        }
        holder.likeTv.setTag(bean);

        holder.commentCountTv.setText(bean.getReplyNum() + "");

        holder.likeCountTv.setText(bean.getFocusNum() + "");
        //		holder.postDetaiTv.setText(bean.postDetail);
        holder.postDetaiTv.setText(bean.getSubjectContent(), mCollapsedStatus, position);
        //		holder.postDetaiTv.setDesc(bean.postDetail, BufferType.NORMAL);

        if (StringUtil.notEmpty(bean.getNickName())) {
            holder.nickNameTv.setText(bean.getNickName());
        }

        if (StringUtil.notEmpty(bean.getCreateDate())) {
            holder.pushTimeTv.setText(bean.getCreateDate());
        }

        if (StringUtil.notEmpty(bean.getSubjectClassify())) {
            holder.groupNameTv.setText(StringUtil.selectHobbiesTypeName(bean.getSubjectClassify()));
        }
        return convertView;
    }

    private void gotoImgShow(int position, List<String> imgList) {
        Intent intent;
        intent = new Intent(mContext, ImageShowActivity.class);
        intent.putExtra("imageUrlList", imgList.toArray());
        intent.putExtra("currentItem", position);
        mContext.startActivity(intent);
    }


    public void likeToServer(final KaoquanBean.DataBean bean, final String type) {

        String url = ServiceUtils.SERVICE_ABOUT_HOME + "/v1/subject/focus_subject.json?";
        RequestParams params = new RequestParams();
        params.addBodyParameter("userId", NimApplication.user);
        params.addBodyParameter("subjectId", bean.getSubjectId());
        params.addBodyParameter("focusState", type);

        new HttpUtils().send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("dddddd", responseInfo.result);

                try {
                    JSONObject object = new JSONObject(responseInfo.result);
                    String returnCode = object.optString("returnCode", null);
                    if (returnCode.equals("0")) {
                        if (type.equals("1")) {
                            bean.setIsFocus("1");
                            bean.setFocusNum((Integer.parseInt(bean.getFocusNum()) + 1) + "");
                        } else {
                            bean.setIsFocus("0");
                            bean.setFocusNum((Integer.parseInt(bean.getFocusNum()) - 1) + "");
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


    View.OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.like_tv:
                    if (NimApplication.user == null || NimApplication.user.equals("")) {
                        CommonUtil.showLoginddl(mContext);
                    } else {
                        KaoquanBean.DataBean bean = (KaoquanBean.DataBean) v.getTag();
                        if (null != bean && bean.getIsFocus().equals("1")) {
                            likeToServer(bean, "2");
                        } else {
                            likeToServer(bean, "1");
                        }

                    }
                    break;


                default:
                    break;
            }
        }
    };

    class ViewHolder {
        TextView nickNameTv, commentTv, likeCountTv, userDescTv, userTypeTv, pushTimeTv, groupNameTv, likeTv, commentCountTv;
        GridView grid_pic_list;
        CircleImageView userImgIv;
        ExpandableTextView postDetaiTv;
    }

}

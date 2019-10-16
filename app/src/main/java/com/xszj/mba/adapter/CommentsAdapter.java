package com.xszj.mba.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xszj.mba.R;
import com.xszj.mba.activity.BbsDetailActivity;
import com.xszj.mba.bean.BbsDetailCommentListBean;
import com.xszj.mba.bean.CommentsModel;
import com.xszj.mba.utils.CircleImageView;
import com.xszj.mba.utils.CommonUtil;
import com.xszj.mba.utils.StringUtil;

import java.util.List;


/**
 * 
 * 新闻列表适配器
 * */
public class CommentsAdapter extends BaseAdapter {

	class NewsItemViewHolder {
		public CircleImageView ivImg = null;
		public TextView tvName = null;
		public TextView tvContent = null;
		public TextView tvCtime = null;
		public TextView tvDelete = null;
		TextView roleTag2Tv;
		LinearLayout replyLayout;
		//		TextView replyToTv;
	}

	private LayoutInflater lInflater = null;
	private List<BbsDetailCommentListBean.DataBean> mListNews = null;
	private Context context = null;
	private BbsDetailActivity activity;
	private String curUid;

	public List<BbsDetailCommentListBean.DataBean> getList() {
		return mListNews;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NewsItemViewHolder holder = null;
		if (null == convertView) {
			holder = new NewsItemViewHolder();
			convertView = lInflater.inflate(R.layout.item_comment_list, null);
			holder.ivImg = (CircleImageView) convertView.findViewById(R.id.iv_headiconcomment);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_comment_name);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tv_comment_content);
			holder.tvCtime = (TextView) convertView.findViewById(R.id.tv_comment_ctime);
			holder.tvDelete = (TextView)convertView.findViewById(R.id.tag_delete);
			holder.roleTag2Tv = (TextView) convertView.findViewById(R.id.tag_tv);
			holder.replyLayout = (LinearLayout) convertView.findViewById(R.id.reply_ll);

			//			holder.replyToTv = (TextView) convertView.findViewById(R.id.reply_to_tv);
			convertView.setTag(holder);
		} else {
			holder = (NewsItemViewHolder) convertView.getTag();
			holder.tvName.setText("");
			holder.tvContent.setText("");
			holder.tvCtime.setText("");
			holder.ivImg.setImageResource(R.drawable.tu_zhanweitu_houzi_gray);
		}

		BbsDetailCommentListBean.DataBean model = mListNews.get(position);
		holder.tvName.setText(model.getNickName());
		holder.tvCtime.setText(model.getCreateDate());
		holder.roleTag2Tv.setText(StringUtil.notEmpty(model.getPersonalSign()) ? model.getPersonalSign() : "");
		holder.replyLayout.setOnClickListener(listener);
		holder.tvDelete.setOnClickListener(listener);
		holder.replyLayout.setTag(model);
		holder.tvDelete.setTag(model);

		if (model.getReplyUserId().equals(curUid)){
			holder.tvDelete.setVisibility(View.VISIBLE);
		}else {
			holder.tvDelete.setVisibility(View.GONE);
		}

		holder.tvContent.setText(model.getReplyContent());

		if (!TextUtils.isEmpty(model.getHeadPic()))
			ImageLoader.getInstance().displayImage(model.getHeadPic(), holder.ivImg, NimApplication.imageOptions);

		return convertView;
	}

	View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.reply_ll:
				if (NimApplication.user == null) {
					CommonUtil.showLoginddl(context);
				} else {
					CommentsModel bean = (CommentsModel) v.getTag();
					activity.setCommentsModel(bean);
				}
				break;

				case R.id.tag_delete:
					Toast.makeText(context,"我被点了",Toast.LENGTH_SHORT).show();
					break;

			default:
				break;
			}
		}
	};


	public CommentsAdapter(Context context, List<BbsDetailCommentListBean.DataBean> news, BbsDetailActivity activity) {
		lInflater = LayoutInflater.from(context);
		this.context = context;
		this.mListNews = news;
		this.activity = activity;
		this.curUid = NimApplication.user;
	}

	public void setNews(List<BbsDetailCommentListBean.DataBean> news) {
		this.mListNews = news;
		notifyDataSetChanged();
	}

	public void addNews(List<BbsDetailCommentListBean.DataBean> news) {
		this.mListNews.addAll(news);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (null == mListNews)
			return 0;
		return mListNews.size();
	}

	@Override
	public Object getItem(int position) {
		if (null == mListNews || 0 == mListNews.size())
			return null;
		return mListNews.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}

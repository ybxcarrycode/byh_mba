/**
 * 
 */
package com.xszj.mba.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;


import com.xszj.mba.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kince
 * @category ģ������Ӧ�õײ�tagview
 * 
 */
public class TagListView extends FlowLayout implements OnClickListener {

	private boolean mIsDeleteMode;
	private OnTagCheckedChangedListener mOnTagCheckedChangedListener;
	private OnTagClickListener mOnTagClickListener;
	private int mTagViewBackgroundResId;
	private int mTagViewTextColorResId;
	private final List<Tag> mTags = new ArrayList<Tag>();
	private int[] tagBgs;
	private boolean isSchool;

	/**
	 * @param context
	 */
	public TagListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public void setTagBgs(boolean school) {
		isSchool = school;
		if (school) {
			tagBgs = new int[] { R.drawable.fillet_attention_press_btn };
		} else {
			tagBgs = new int[] { R.drawable.fillet_tag1_background, R.drawable.fillet_tag2_background, R.drawable.fillet_tag3_background, R.drawable.fillet_tag4_background, R.drawable.fillet_tag5_background };
		}
	}

	/**
	 * @param context
	 * @param attributeSet
	 */
	public TagListView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * @param context
	 * @param attributeSet
	 * @param defStyle
	 */
	public TagListView(Context context, AttributeSet attributeSet, int defStyle) {
		super(context, attributeSet, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public void onClick(View v) {
		if ((v instanceof TagView)) {
			Tag localTag = (Tag) v.getTag();
			if (this.mOnTagClickListener != null) {
				this.mOnTagClickListener.onTagClick((TagView) v, localTag);
			}
		}
	}

	private void init() {

	}

	private void inflateTagView(final Tag t, boolean b) {

		TagView localTagView = (TagView) View.inflate(getContext(), R.layout.tag, null);
		if (isSchool) {
			localTagView.setBackgroundResource(tagBgs[0]);
		} else {
			localTagView.setBackgroundResource(tagBgs[t.getId() % 5]);
		}
		localTagView.setText(t.getTitle());
		localTagView.setTag(t);
		localTagView.setTextSize(10.0f);

		if (mTagViewTextColorResId <= 0) {
			int c = getResources().getColor(R.color.text_color_007aff);
			localTagView.setTextColor(c);
		}

		//		if (mTagViewBackgroundResId <= 0) {
		//			mTagViewBackgroundResId = R.drawable.tag_icon;
		//			localTagView.setBackgroundResource(mTagViewBackgroundResId);
		//		}

		localTagView.setChecked(t.isChecked());
		localTagView.setCheckEnable(b);
		if (mIsDeleteMode) {
			int k = (int) TypedValue.applyDimension(1, 5.0F, getContext().getResources().getDisplayMetrics());
			localTagView.setPadding(localTagView.getPaddingLeft(), localTagView.getPaddingTop(), k, localTagView.getPaddingBottom());
			localTagView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.forum_tag_close, 0);
		}
		if (t.getBackgroundResId() > 0) {
			localTagView.setBackgroundResource(t.getBackgroundResId());
		}
		if ((t.getLeftDrawableResId() > 0) || (t.getRightDrawableResId() > 0)) {
			localTagView.setCompoundDrawablesWithIntrinsicBounds(t.getLeftDrawableResId(), 0, t.getRightDrawableResId(), 0);
		}
		localTagView.setOnClickListener(this);
		localTagView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean) {
				t.setChecked(paramAnonymousBoolean);
				if (TagListView.this.mOnTagCheckedChangedListener != null) {
					TagListView.this.mOnTagCheckedChangedListener.onTagCheckedChanged((TagView) paramAnonymousCompoundButton, t);
				}
			}
		});
		addView(localTagView);
	}

	public void addTag(int i, String s) {
		addTag(i, s, false);
	}

	public void addTag(int i, String s, boolean b) {
		addTag(new Tag(i, s), b);
	}

	public void addTag(Tag tag) {
		addTag(tag, false);
	}

	public void addTag(Tag tag, boolean b) {
		mTags.add(tag);
		inflateTagView(tag, b);
	}

	public void addTags(List<Tag> lists) {
		addTags(lists, false);
	}

	public void addTags(List<Tag> lists, boolean b) {
		for (int i = 0; i < lists.size(); i++) {
			addTag((Tag) lists.get(i), b);
		}
	}

	public List<Tag> getTags() {
		return mTags;
	}

	public View getViewByTag(Tag tag) {
		return findViewWithTag(tag);
	}

	public void removeTag(Tag tag) {
		mTags.remove(tag);
		removeView(getViewByTag(tag));
	}

	public void setDeleteMode(boolean b) {
		mIsDeleteMode = b;
	}

	public void setOnTagCheckedChangedListener(OnTagCheckedChangedListener onTagCheckedChangedListener) {
		mOnTagCheckedChangedListener = onTagCheckedChangedListener;
	}

	public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
		mOnTagClickListener = onTagClickListener;
	}

	public void setTagViewBackgroundRes(int res) {
		mTagViewBackgroundResId = res;
	}

	public void setTagViewTextColorRes(int res) {
		mTagViewTextColorResId = res;
	}

	public void setTags(List<? extends Tag> lists) {
		setTags(lists, false);
	}

	public void setTags(List<? extends Tag> lists, boolean b) {
		removeAllViews();
		mTags.clear();
		for (int i = 0; i < lists.size(); i++) {
			addTag((Tag) lists.get(i), b);
		}
	}

	public static abstract interface OnTagCheckedChangedListener {
		public abstract void onTagCheckedChanged(TagView tagView, Tag tag);
	}

	public static abstract interface OnTagClickListener {
		public abstract void onTagClick(TagView tagView, Tag tag);
	}

}

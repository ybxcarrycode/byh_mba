/**
 * @ProjectName:ShowPlatform
 * @Title: ShowHistoryService.java
 * @Package com.xszj.base.service
 * @Description: (第一视频秀场)
 * @author liuqi liuqimother@163.com 18612251467
 * @date 2014-11-13 下午1:50:24
 * @version V1.0
 * Copyright (c) 2014第一视频-版权所有
 */
package com.xszj.mba.view;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xszj.mba.R;

import java.util.ArrayList;

/**
 * @author liuqi liuqimother@163.com 18612251467
 * @ClassName: GlobalTitleView
 * @Description: (全局的标题)
 * @date 2014-11-13 下午3:29:48
 */
public class GlobalTitleView extends RelativeLayout implements android.view.View.OnClickListener {

    private Context mContext;

    private RelativeLayout global_title;

    private TextView mTitle;

    public TextView getmTitle() {
        return mTitle;
    }

    public void setmTitle(TextView mTitle) {
        this.mTitle = mTitle;
    }

    private ImageButton mBackBtn;

    private Button mLeftCustomBtn;

    private Button mRightCustomBtn;

    private ImageButton mMoreBtn;

    private RadioGroup mRadioGroup;

    private TextView mDependenceText;

    private TextView unreadLabel;

    private TextView titleLeftTv;

    private TextView title_right;

    private TitleViewListener titleViewListener = null;

    private View mLineView;

    private String category;

    public GlobalTitleView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public GlobalTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.global_title, this);

        global_title = (RelativeLayout) findViewById(R.id.global_title);

        unreadLabel = (TextView) findViewById(R.id.together_number);

        mTitle = (TextView) findViewById(R.id.global_title_textview_title);
        titleLeftTv = (TextView) findViewById(R.id.global_title_left_textview);
        title_right = (TextView) findViewById(R.id.title_right);
        mLeftCustomBtn = (Button) findViewById(R.id.global_title_left_custom_btn);
        mRightCustomBtn = (Button) findViewById(R.id.global_title_right_custom_btn);
        mBackBtn = (ImageButton) findViewById(R.id.global_title_back_btn);

        mMoreBtn = (ImageButton) findViewById(R.id.global_title_more_btn);
        mRadioGroup = (RadioGroup) findViewById(R.id.global_title_radiogroup);
        mDependenceText = (TextView) findViewById(R.id.global_title_dependence_text);
        mLineView = findViewById(R.id.global_title_bottom_line);

        mBackBtn.setImageResource(R.drawable.sel_system_back);

        mBackBtn.setOnClickListener(this);
        mLeftCustomBtn.setOnClickListener(this);
        mRightCustomBtn.setOnClickListener(this);
        mMoreBtn.setOnClickListener(this);
        title_right.setOnClickListener(this);

    }

    public void showLine(boolean flag) {
        mLineView.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置标题布局的背景色
     *
     * @param title
     */
    public void setTitleViewBackground(int resId) {
        global_title.setBackgroundColor(resId);
    }

    /**
     * 设置标题文本
     *
     * @param title
     */
    public void setTitle(int resId) {
        mTitle.setText(resId);
    }

    /**
     * 设置 左侧 标题文本
     *
     * @param title
     */
    public void setTitleLeftTv(int resId) {
        titleLeftTv.setText(resId);
    }

    /**
     * 设置 右侧 标题文本
     *
     * @param title
     */
    public void setTitleRightTv(String resId) {
        title_right.setText(resId);
    }

    /**
     * @Title: setTitleRightDrawable
     * @Description: (设置title标题右边的图片)
     * @param: @param resId    资源id
     * @return: void    返回类型
     * @date: 2014-11-22 上午10:20:26
     */
    public void setTitleRightDrawable(int resId) {
        Drawable mDrawable = getResources().getDrawable(resId);
        mDrawable.setBounds(0, 5, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        mTitle.setCompoundDrawablePadding(15);
        mTitle.setCompoundDrawables(null, null, mDrawable, null);
    }

    /**
     * @Title: getCategory
     * @Description: 设置跳转类别
     * @param: @return    设定文件
     * @return: String    返回类型
     * @date: 2014-10-16 下午5:16:37
     */
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 设置标题文本
     *
     * @param title
     */
    public void setTitle(String text) {
        mTitle.setText(text);
    }

    /**
     * 设置 左侧 标题文本
     *
     * @param title
     */
    public void setTitleLeftTv(String text) {
        titleLeftTv.setText(text);
    }

    /**
     * 设置标题文本颜色
     *
     * @param color
     */
    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    /**
     * 设置 左侧 标题文本颜色
     *
     * @param color
     */
    public void setTitleLeftTvColor(int color) {
        titleLeftTv.setTextColor(color);
    }

    /**
     * 设置 右侧 标题文本颜色
     *
     * @param color
     */
    public void setTitleRightTvColor(int color) {
        title_right.setTextColor(color);
    }

    /**
     * 设置 左侧 标题文本 字号
     *
     * @param color
     */
    public void setTitleLeftTvSize(float size) {
        titleLeftTv.setTextSize(size);
    }

    /**
     * 设置 右侧 标题文本 字号
     *
     * @param color
     */
    public void setTitleRightTvSize(float size) {
        title_right.setTextSize(size);
    }

    /**
     * 设置标题显示
     *
     * @param isVisible
     */
    public void setTitleShow(boolean isVisible) {
        mTitle.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置 左侧 标题显示
     *
     * @param isVisible
     */
    public void setTitleLeftTvShow(boolean isVisible) {
        titleLeftTv.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置 右侧 标题显示
     *
     * @param isVisible
     */
    public void setTitleRightTvShow(boolean isVisible) {
        title_right.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置标题右侧view的文本
     *
     * @param title
     */
    public void setDependenceViewText(String title) {
        mDependenceText.setText(title);
    }

    /**
     * 设置标题右侧view的文本颜色
     *
     * @param color
     */
    public void setDependenceViewTextColor(int color) {
        mDependenceText.setTextColor(color);
    }

    /**
     * 设置标题右侧view的文本字体大小
     *
     * @param color
     */
    public void setDependenceViewTextSize(int size) {
        mDependenceText.setTextSize(size);
    }

    /**
     * 设置标题右侧view的背景
     *
     * @param color
     */
    public void setDependenceViewBgRes(int resid) {
        mDependenceText.setBackgroundResource(resid);
    }

    /**
     * 设置标题显示
     *
     * @param isVisible
     */
    public void setDependenceViewShow(boolean isVisible) {
        mDependenceText.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 是否启用返回键
     *
     * @param isVisible
     */
    public void setBackVisible(boolean isVisible) {
        mBackBtn.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置返回键图片样式
     *
     * @param resId
     */
    public void setBackImage(int resId) {
        mBackBtn.setBackgroundResource(resId);
    }

    public void setBackIconImage(int resId) {
        mBackBtn.setImageResource(resId);
    }

    public void setMoreIconImage(int resId) {
        mMoreBtn.setImageResource(resId);
    }

    /**
     * 是否启用left自定义按钮
     *
     * @param isVisible
     */
    public void setLeftDiyBtnVisible(boolean isVisible) {
        mLeftCustomBtn.setVisibility(
                isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置left自定义按钮图标
     *
     * @param resId
     */
    public void setLeftDiyBtnIcon(int resId) {
        mLeftCustomBtn.setBackgroundResource(resId);
    }

    /**
     * 设置left自定义按钮文本颜色
     *
     * @param resId
     */
    public void setLeftDiyBtnTextColor(int resId) {
        mLeftCustomBtn.setTextColor(resId);
    }

    /**
     * 设置left自定义按钮文本
     *
     * @param resId
     */
    public void setLeftDiyBtnText(int resId) {
        mLeftCustomBtn.setTextColor(resId);
    }

    // 设置left自定义按钮文本
    public void setLeftDiyBtnText(String text) {
        mLeftCustomBtn.setText(text);
    }


    /**
     * 设置left自定义按钮字体大小
     *
     * @param text
     */
    public void setLeftDiyBtnTextSize(float size) {
        mLeftCustomBtn.setTextSize(size);
    }

    /**
     * 设置left自定义按钮点击事件
     *
     * @param onClickListener
     */
    public void setLeftDiyBtnOnClickListener(OnClickListener onClickListener) {
        mLeftCustomBtn.setOnClickListener(onClickListener);
    }

    public void setLeftTvOnClickListener(OnClickListener onClickListener) {
        titleLeftTv.setOnClickListener(onClickListener);
    }

    public void setRightMoreBtnOnClickListener(OnClickListener onClickListener) {
        mMoreBtn.setOnClickListener(onClickListener);
    }

    /**
     * 设置left自定义按钮compound drawable
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setLeftDiyBtnCompoundDrawables(int left, int top, int right, int bottom) {
        mLeftCustomBtn.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * 是否启用更多按钮
     *
     * @param isVisible
     */
    public void setMoreBtnVisible(boolean isVisible) {
        mMoreBtn.setVisibility(
                isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 是否显示未读消息
     *
     * @param isVisible
     */
    public void setUnreadLabelVisible(boolean isVisible) {
        unreadLabel.setVisibility(
                isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置未读消息数量
     *
     * @param
     */
    public void setUnreadLabelCount(String num) {
        unreadLabel.setText(num);
    }

    /**
     * 是否启用radiogroup
     *
     * @param isVisible
     */
    public void setRadioGroupVisible(boolean isVisible) {
        mRadioGroup.setVisibility(
                isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 添加radiobutton
     *
     * @param stringIds   标题
     * @param drawableIds 图标 ,根据 left top right bottom顺序
     * @param bgIds       不同的背景
     */
    public void addRadioBtns(ArrayList<RadioPreferences> radioPreList) {

        for (int i = 0; i < radioPreList.size(); i++) {
            RadioPreferences radioPre = radioPreList.get(i);
            RadioButton radioBtn = new RadioButton(mContext);
            android.widget.RadioGroup.LayoutParams params = new android.widget.RadioGroup.LayoutParams(radioPre.getWidth(), radioPre.getHeight());
            params.setMargins(radioPre.getMargin()[0], radioPre.getMargin()[1],
                    radioPre.getMargin()[2], radioPre.getMargin()[3]);
            radioBtn.setLayoutParams(params);
            radioBtn.setButtonDrawable(color.transparent);
            radioBtn.setText(radioPre.getTextResId());
            radioBtn.setCompoundDrawablesWithIntrinsicBounds(radioPre.getDrawableLeftRedId(), radioPre.getDrawableTopRedId(),
                    radioPre.getDrawableRightRedId(), radioPre.getDrawableBottomRedId());
            radioBtn.setGravity(radioPre.getGravity());
            if (radioPre.getBgResid() != -1)
                radioBtn.setBackgroundResource(radioPre.getBgResid());
            mRadioGroup.addView(radioBtn);
            //默认选中
            if (mRadioGroup.getChildCount() == 1)
                mRadioGroup.check(radioBtn.getId());
        }

    }

    /**
     * 添加radiobutton
     *
     * @param RadioButton radio按钮
     */
    public void addRadioBtn(RadioButton radioBtn) {

        mRadioGroup.addView(radioBtn);
        //默认选中
        if (mRadioGroup.getChildCount() == 1)
            mRadioGroup.check(radioBtn.getId());
    }

    public void setOnRadioChangeListener(OnCheckedChangeListener checkedChangeListener) {
        mRadioGroup.setOnCheckedChangeListener(checkedChangeListener);
    }

    public void radioCheck(int resId) {
        mRadioGroup.check(resId);
    }

    /**
     * return the right custom button, use this button to create strategy menu
     *
     * @author guangwei
     */
    public Button getRightDiyBtn() {
        return mRightCustomBtn;
    }

    public Button getLeftDiyBtn() {
        return mLeftCustomBtn;
    }

    /**
     * 是否启动right自定义按钮
     *
     * @param isVisible
     */
    public void setRightDiyBtnVisible(boolean isVisible) {
        mRightCustomBtn.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置right自定义按钮图标
     *
     * @param resId
     */
    public void setRightDiyBtnIcon(int resId) {
        mRightCustomBtn.setBackgroundResource(resId);
        if (!mRightCustomBtn.isShown()) {
            mRightCustomBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置right自定义按钮文本颜色
     *
     * @param resId
     */
    public void setRightDiyBtnTextColor(int color) {
        mRightCustomBtn.setTextColor(color);
    }

    /**
     * 设置right自定义按钮文本
     *
     * @param resId
     */
    public void setRightDiyBtnText(int resId) {
        setRightDiyBtnText(mContext.getString(resId));
    }

    /**
     * 设置right自定义按钮文本
     *
     * @param text
     */
    public void setRightDiyBtnText(String text) {
        if (TextUtils.isEmpty(text)) {
            mRightCustomBtn.setVisibility(View.GONE);
        } else {
            mRightCustomBtn.setVisibility(View.VISIBLE);
            mRightCustomBtn.setText(text);
        }
    }

    /**
     * 设置right自定义按钮字体大小
     *
     * @param text
     */
    public void setRightDiyBtnTextSize(float size) {
        mRightCustomBtn.setTextSize(size);
    }

    public void setRightDiyBtnTextSize(int unit, float size) {
        mRightCustomBtn.setTextSize(unit, size);
    }

    /**
     * 设置right自定义按钮点击事件
     *
     * @param onClickListener
     */
    public void setRightDiyBtnOnClickListener(OnClickListener onClickListener) {
        mRightCustomBtn.setOnClickListener(onClickListener);
    }

    /**
     * 设置right自定义按钮compound drawable
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setRightDiyBtnCompoundDrawables(int left, int top, int right, int bottom) {
        mRightCustomBtn.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.global_title_back_btn:
                if (mBackBtn.getVisibility() != View.VISIBLE) {
                    break;
                }
                if (mContext instanceof Activity) {
                    if (titleViewListener != null) {
                        titleViewListener.setOnBackBtn();
                    } else {
                        ((Activity) mContext).onBackPressed();

                    }
                }
                break;
            case R.id.global_title_more_btn:
                break;
            case R.id.title_right:
                if (mContext instanceof Activity && titleViewListener != null) {
                    titleViewListener.setDoMore();
                }
                break;

        }
    }

    /**
     * @return the mBack
     */
    public View getBackView() {
        return mBackBtn;
    }

    public void setTitleViewListener(TitleViewListener titleViewListener) {
        this.titleViewListener = titleViewListener;
    }

    public String getRightTag() {
        return mRightCustomBtn.getTag().toString();
    }

    public void setRightTag(String rightTag) {
        mRightCustomBtn.setTag(rightTag);
    }

    public class RadioPreferences {
        private int width;
        private int height;
        private int gravity = Gravity.CENTER;
        private int textResId;
        private int bgResid = -1;
        private int drawableLeftRedId = 0;
        private int drawableRightRedId = 0;
        private int drawableTopRedId = 0;
        private int drawableBottomRedId = 0;
        private int[] padding = new int[4];
        private int[] margin = new int[4];
        private int drawablePadding = 0;

        /**
         * @return the width
         */
        public int getWidth() {
            return width;
        }

        /**
         * @param width the width to set
         */
        public void setWidth(int width) {
            this.width = width;
        }

        /**
         * @return the height
         */
        public int getHeight() {
            return height;
        }

        /**
         * @param height the height to set
         */
        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * @return the gravity
         */
        public int getGravity() {
            return gravity;
        }

        /**
         * @param gravity the gravity to set
         */
        public void setGravity(int gravity) {
            this.gravity = gravity;
        }

        /**
         * @return the stringResId
         */
        public int getTextResId() {
            return textResId;
        }

        /**
         * @param stringResId the stringResId to set
         */
        public void setTextResId(int stringResId) {
            this.textResId = stringResId;
        }

        /**
         * @return the bgResid
         */
        public int getBgResid() {
            return bgResid;
        }

        /**
         * @param bgResid the bgResid to set
         */
        public void setBgResid(int bgResid) {
            this.bgResid = bgResid;
        }

        /**
         * @return the drawableLeftRedId
         */
        public int getDrawableLeftRedId() {
            return drawableLeftRedId;
        }

        /**
         * @param drawableLeftRedId the drawableLeftRedId to set
         */
        public void setDrawableLeftRedId(int drawableLeftRedId) {
            this.drawableLeftRedId = drawableLeftRedId;
        }

        /**
         * @return the drawableRightRedId
         */
        public int getDrawableRightRedId() {
            return drawableRightRedId;
        }

        /**
         * @param drawableRightRedId the drawableRightRedId to set
         */
        public void setDrawableRightRedId(int drawableRightRedId) {
            this.drawableRightRedId = drawableRightRedId;
        }

        /**
         * @return the drawableTopRedId
         */
        public int getDrawableTopRedId() {
            return drawableTopRedId;
        }

        /**
         * @param drawableTopRedId the drawableTopRedId to set
         */
        public void setDrawableTopRedId(int drawableTopRedId) {
            this.drawableTopRedId = drawableTopRedId;
        }

        /**
         * @return the drawableBottomRedId
         */
        public int getDrawableBottomRedId() {
            return drawableBottomRedId;
        }

        /**
         * @param drawableBottomRedId the drawableBottomRedId to set
         */
        public void setDrawableBottomRedId(int drawableBottomRedId) {
            this.drawableBottomRedId = drawableBottomRedId;
        }

        /**
         * @return the padding
         */
        public int[] getPadding() {
            return padding;
        }

        /**
         * @param padding the padding to set
         */
        public void setPadding(int padding) {
            this.padding[0] = padding;
            this.padding[1] = padding;
            this.padding[2] = padding;
            this.padding[3] = padding;
        }

        /**
         * @param left
         * @param top
         * @param right
         * @param bottom
         */
        public void setPadding(int left, int top, int right, int bottom) {
            this.padding[0] = left;
            this.padding[1] = top;
            this.padding[2] = right;
            this.padding[3] = bottom;
        }

        /**
         * @return the drawablePadding
         */
        public int getDrawablePadding() {
            return drawablePadding;
        }

        /**
         * @param drawablePadding the drawablePadding to set
         */
        public void setDrawablePadding(int drawablePadding) {
            this.drawablePadding = drawablePadding;
        }

        /**
         * @return the margin
         */
        public int[] getMargin() {
            return margin;
        }

        /**
         * @param margin the margin to set
         */
        public void setMargin(int margin) {
            this.margin[0] = margin;
            this.margin[1] = margin;
            this.margin[2] = margin;
            this.margin[3] = margin;
        }

        /**
         * @param left
         * @param top
         * @param right
         * @param bottom
         */
        public void setMargin(int left, int top, int right, int bottom) {
            this.margin[0] = left;
            this.margin[1] = top;
            this.margin[2] = right;
            this.margin[3] = bottom;
        }
    }

    /**
     * title控件自定义事件监听器
     */
    public interface TitleViewListener {

        public void setOnBackBtn();

        public void setDoMore();
    }
}

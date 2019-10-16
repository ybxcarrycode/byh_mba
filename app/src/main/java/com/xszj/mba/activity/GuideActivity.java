package com.xszj.mba.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xszj.mba.R;
import com.xszj.mba.adapter.GuideViewPagerAdapter;
import com.xszj.mba.utils.FilesUtils;
import com.xszj.mba.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * *************************************************************************
 * <p/>
 * <pre></pre>
 *
 * @文件名称: GuideActivity.java
 * @包 路 径： com.yunshuxie.main
 * @类描述: 引导页, 初次使用APP时加载
 * @版本: V1.5
 * @创建人： swh
 * @创建时间：2015-8-19 下午1:55:35
 * <p/>
 * *************************************************************************
 */
public class GuideActivity extends Activity implements OnPageChangeListener {

    private ViewPager vp;
    private GuideViewPagerAdapter vpAdapter;
    private List<View> views;
    private GestureDetector gestureDetector; // 用户滑动
    /** 记录当前分页ID */
    private int flaggingWidth;// 互动翻页所需滚动的长度是当前屏幕宽度的1/3

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        findView();

        initDots();

        gestureDetector = new GestureDetector(new GuideViewTouch());
        // 获取分辨率
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 4;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressLint("InflateParams")
    private void findView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        //第一个引导页
        View view1 = inflater.inflate(R.layout.guide_layout_one, null);
        View view2= inflater.inflate(R.layout.guide_layout_twoo, null);
        View view3= inflater.inflate(R.layout.guide_layout_two, null);

        ImageView _guide_one_yuan1 = (ImageView) view1.findViewById(R.id._guide_one_yuan);
        //第二个引导页
        ImageView _guide_one_yuan2 = (ImageView) view2.findViewById(R.id._guide_one_yuan);
        //第三个引导页
        ImageView _guide_one_yuan3 = (ImageView) view3.findViewById(R.id._guide_one_yuan);

        _guide_one_yuan1.setImageBitmap(FilesUtils.readBitMap(GuideActivity.this, R.mipmap.linkpage_1));
        _guide_one_yuan2.setImageBitmap(FilesUtils.readBitMap(GuideActivity.this, R.mipmap.linkpage_2));
        _guide_one_yuan3.setImageBitmap(FilesUtils.readBitMap(GuideActivity.this, R.mipmap.linkpage_3));

        views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);

        vpAdapter = new GuideViewPagerAdapter(views, this);

        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        vp.setOnPageChangeListener(this);
    }

    private void initDots() {

        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);


        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);

    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int position) {
    }

    @Override
    public void onPageScrolled(int position, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentDot(position);
        currentIndex = position;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event))
        {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

    private class GuideViewTouch extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if (currentIndex == 2)
            {
                if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY() - e2.getY()) && (e1.getX() - e2.getX() <= (-flaggingWidth) || e1.getX() - e2.getX() >= flaggingWidth))
                {
                    if (e1.getX() - e2.getX() >= flaggingWidth)
                    {
                        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                        startActivity(intent);
                        SharedPreferencesUtils.setIschecked(GuideActivity.this,"isFirstIn",true);
                        finish();
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}

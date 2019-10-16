package com.xszj.mba.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.net.nim.demo.NimApplication;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.photoview.HackyViewPager;
import com.xszj.mba.photoview.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ybx on 2017/6/20.
 */

public class ImageShowActivity extends BaseActivity {

    @BindView(R.id.main_top_left)
    ImageButton mainTopLeft;
    @BindView(R.id.rel_title)
    RelativeLayout relTitle;
    @BindView(R.id.pb_stu_jobs)
    ProgressBar pbStuJobs;
    @BindView(R.id.viewPager)
    HackyViewPager viewPager;

    private List<String> list = new ArrayList<>();
    private Object[] cobjs;
    private int pos;


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_imageshow_rel;
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();
        cobjs = (Object[]) getIntent().getSerializableExtra("imageUrlList");
        pos = getIntent().getIntExtra("currentItem", 0);
    }

    @Override
    protected void bindViews() {
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initListeners() {
        mainTopLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void getDateForService() {
        super.getDateForService();

        if (cobjs != null) {
            for (int i = 0; i < cobjs.length; i++) {
                String imgUrl = (String) cobjs[i];
                list.add(imgUrl);
            }
            viewPager.setAdapter(new ImageScaleAdapter());
            viewPager.setCurrentItem(pos);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private class ImageScaleAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(getApplicationContext(), R.layout.item_imageshow_adapter_rel, null);
            TextView tv_count = (TextView) view.findViewById(R.id.tv_count);

            ImageView imageView = (ImageView) view.findViewById(R.id.image_view);

            final PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);

            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText((position + 1) + "/" + list.size());
            ImageLoader.getInstance().displayImage(list.get(position), imageView, NimApplication.imageOptions, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    if (pbStuJobs != null)
                        pbStuJobs.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view,
                                            FailReason failReason) {
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (pbStuJobs != null && attacher != null) {
                        pbStuJobs.setVisibility(View.INVISIBLE);
                        attacher.update();
                    }
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//		super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}

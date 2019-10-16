package com.xszj.mba.adapter;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewPagerAdpter extends PagerAdapter {

    private List<View> views = null;

    public ViewPagerAdpter(List<View> views, Activity activity) {
        // TODO Auto-generated constructor stub
        this.views = views;
    }

    @Override
    public void destroyItem(ViewGroup container, int arg1, Object arg2) {
        if (views.size() > arg1) {
            container.removeView(views.get(arg1));
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return views.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            View view = views.get(position);
            ViewGroup vg = (ViewGroup) view.getParent();
            if (vg != null) {//先移除即将添加的view本来所附的父控件.
                vg.removeAllViews();//或者vg.removeView();
            }
            if (views.size() > position) {
                container.addView(views.get(position));
                return views.get(position);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        // TODO Auto-generated method stub
        super.finishUpdate(container);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        // TODO Auto-generated method stub
        super.restoreState(state, loader);
    }

    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub
        return super.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        // TODO Auto-generated method stub
        super.startUpdate(container);
    }
}

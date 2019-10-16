package com.xszj.mba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by QQ on 2016/1/13.
 */
public class SelcectPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public SelcectPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }



    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return fragments.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragments.size();
    }



}

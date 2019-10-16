package com.xszj.mba.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2018/1/4.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向左滑动
    private boolean isSlidingToLeft = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

        int lastItemPosition1 = manager.findLastCompletelyVisibleItemPosition();
        // 当拖动滑时判断是否是最后一个
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            // 获取最后一个完全显示的itemPosition
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();

            // 判断是否滑动到了最后一个Item，并且是向左滑动
            if (lastItemPosition == (itemCount-1) && isSlidingToLeft) {
                // 加载更多
                onLoadMore();
            }
        }
        //滑动时返回当前position
        if (newState == RecyclerView.SCROLL_STATE_IDLE){
            currentPosition(lastItemPosition1);
        }
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // dx值大于0表示正在向左滑动，小于或等于0表示向右滑动或停止
        isSlidingToLeft = dx > 0;
        /*if (dx>0){
            onNext();
        }else if (dx<0){
            onPrew();
        }*/
    }


    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();

    /**
     * 左滑
     */
    public abstract void onPrew();

    /**
     * 右滑
     */
    public abstract void onNext();

    /**
     * 返回当前posiotion
     */
    public abstract void currentPosition( int position);
}

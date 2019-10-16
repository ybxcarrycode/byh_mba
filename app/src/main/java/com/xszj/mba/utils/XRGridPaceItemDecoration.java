package com.xszj.mba.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by Ybx on 2016/12/12.
 */

public class XRGridPaceItemDecoration extends RecyclerView.ItemDecoration {

    //间距 (item 左右边的间距)
    private int lrPace = 0;
    //垂直间距 (gridview)
    private int vPace = 0;
    //有间距时平均到每一个item上面的距离
    private int vPace2 = 0;
    //水平间距 (gridview)
    private int hPace = 0;
    //gridview 的列数
    private int gNum = 0;

    //左右边 的 余数
    private int lRemainder = 0;
    private int rRemainder = 0;

    //gridview 开始绘制的 position
    private int startPosition = 0;

    //是否有间距
    private boolean isHasPace = false;
    //是否有头布局
    private boolean isHasHeader = false;

    public XRGridPaceItemDecoration(int gNum, int vPace, int hPace) {
        this(gNum, vPace, hPace, false, false, 0);
    }

    public XRGridPaceItemDecoration(int gNum, int vPace, int hPace, boolean isHasHeader) {
        this(gNum, vPace, hPace, isHasHeader, false, 0);
    }

    public XRGridPaceItemDecoration(int gNum, int vPace, int hPace, boolean isHasHeader, boolean isHasPace, int lrPace) {
        this.vPace = (int) (vPace * 0.5);
        this.hPace = hPace;
        this.gNum = gNum;
        this.isHasHeader = isHasHeader;
        this.isHasPace = isHasPace;
        this.lrPace = lrPace;

        int lr = (gNum == 2) ? 0 : (gNum - 2);

        this.startPosition = isHasHeader ? 2 : 1;
        this.lRemainder = isHasHeader ? lr : 1;
        this.rRemainder = isHasHeader ? 1 : 0;


        Log.e("XRGridPaceItemDecoration", startPosition + "/" + lRemainder + "/" + rRemainder);


    }


    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int postion = parent.getChildAdapterPosition(view);
        if (postion >= startPosition) {
            outRect.left = vPace;
            outRect.bottom = hPace;
            outRect.right = vPace;
            if (gNum > 0 && postion % gNum == lRemainder) {
                outRect.left = isHasPace ? lrPace : 0;
            } else if (gNum > 0 && postion % gNum == rRemainder) {
                outRect.right = isHasPace ? lrPace : 0;
            }
        }

    }

}

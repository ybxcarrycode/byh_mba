package com.xszj.mba.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 自定义的一个锯齿边框效果的view
 * yangqiangyu on 5/16/16 11:15
 * 博客:http://blog.csdn.net/yissan
 */
public class SawtoothDisplayView extends LinearLayout {

    private Paint mPaint;
    /**
     * 锯齿三角边长
     */
    private float lengthSide = 30;
    private float lengthSideHalf = 15;
    /**
     * 锯齿三角 底边高
     */
    private float sawHight = 18;
    /**
     * 锯齿三角数量
     */
    private int circleNum;

    private float remain;


    public SawtoothDisplayView(Context context) {
        super(context);
    }

    public SawtoothDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.parseColor("#f6f6f6"));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain == 0) {
            //计算不整除的剩余部分
            remain = (int) w % lengthSide;
        }
        circleNum = (int) ((int) w / lengthSide);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circleNum; i++) {
            float x = remain / 2 + (lengthSide * i);
            int allHight = getHeight();

            Path path = new Path();
            path.moveTo(x, allHight);// 此点为多边形的起点
            path.lineTo(x + lengthSide, allHight);
            path.lineTo(x + lengthSideHalf, allHight - sawHight);
            path.close(); // 使这些点构成封闭的多边形
            canvas.drawPath(path, mPaint);
        }
    }
}

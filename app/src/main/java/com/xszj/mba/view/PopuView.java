package com.xszj.mba.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.xszj.mba.R;

/**
 * Created by Administrator on 2018/1/18.
 */

public class PopuView extends View {
    //圆角半径
    private float ridus;
    //背景颜色
    private int popu_bg;
    private Paint paint;
    //view的宽
    private int widthSize;
    //view的高
    private int heightSize;
    //字体的颜色
    private int textColor;
    //文字
    private String mText;
    //字的大小
    private float textSize;
    private Context mContext;

    public PopuView(Context context) {
        this(context, null);
    }

    public PopuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.PopuView, 0, 0);
        ridus = typedArray.getDimension(R.styleable.PopuView_ridus, 5);
        popu_bg = typedArray.getColor(R.styleable.PopuView_popu_bg, 0xFF4081);
        textColor = typedArray.getColor(R.styleable.PopuView_text_color, 0xFFFFFF);
        textSize = typedArray.getDimension(R.styleable.PopuView_text_size, 14);
        typedArray.recycle();

        //初始化画笔
        paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置填充
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置防抖动
        paint.setDither(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得当前view的宽高限制的类型
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获得view的默认尺寸
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //判断view的mode类型
        //这种是 warp_parent 类型 就是view的宽高不确定，所以我们要给view 赋值。实在 dimen.xml 里面写的
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            widthSize = R.dimen.dimen_100dp;
            heightSize = R.dimen.dimen_40dp;
        }
        //最后把宽，高设置到view中
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int center = getWidth() / 2;
        paint.setColor(popu_bg);
        //画矩形
        RectF rectF = new RectF(0, 0, widthSize, heightSize - 20);
        canvas.drawRoundRect(rectF, ridus, ridus, paint);
        //画三角形（这里是基于path路径的绘制）
        Path path = new Path();
        path.moveTo((widthSize / 2) - 20, heightSize - 20);
        path.lineTo(widthSize / 2, heightSize);
        path.lineTo((widthSize / 2) + 20, heightSize - 20);
        path.close();
        canvas.drawPath(path, paint);


        //画文字将坐标平移至圆心
        paint.setColor(textColor);
        if (TextUtils.isEmpty(mText)) {
            mText = "hello! 选择你感兴趣的科目来练习吧";
        }
        paint.setTextSize(textSize);
        //将文字画到中间
        float textWidth = paint.measureText(mText);
        canvas.drawText(mText, center - textWidth / 2, heightSize / 2, paint);

    }

    /**
     * 设置文本
     *
     * @param mText
     */
    public void setmText(String mText) {
        this.mText = mText;
        postInvalidate();
    }

}

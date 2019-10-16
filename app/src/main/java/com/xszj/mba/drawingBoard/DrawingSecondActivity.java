package com.xszj.mba.drawingBoard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.xszj.mba.R;

public class DrawingSecondActivity extends Activity {

    private Context context;
    private ImageView img;
    private Canvas canvas;
    private Paint paint;
//
//    Display display = getWindowManager().getDefaultDisplay();
//    float dw = display.getWidth();
//    float dh = display.getHeight();

    private List<String> listBitmap = new ArrayList<>();
    // 重置按钮
    private Button reset_btn;

    private Button btn_paint_style;

    private Button btn_paint_color;

    private Button btn_paint_back;
    
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==001){
                Bundle bundle=msg.getData();
                Bitmap bitmap = bundle.getParcelable("bitmap");
                saveBitmap(bitmap);
            }
        }
    };
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_second);
        context = DrawingSecondActivity.this;
        img = (ImageView) findViewById(R.id.img);

        reset_btn = (Button) findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                img.setImageBitmap(null);
                Bitmap mBitmap = Bitmap.createBitmap(720, 1280, Bitmap.Config.ARGB_8888);
                showImage(mBitmap);
            }
        });

        btn_paint_style = (Button) findViewById(R.id.btn_paint_style);
        btn_paint_style.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                paint.setStyle(Paint.Style.FILL);
            }
        });

        btn_paint_color = (Button) findViewById(R.id.btn_paint_color);
        btn_paint_color.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                paint.setColor(Color.RED);
            }
        });

        btn_paint_back = (Button) findViewById(R.id.btn_paint_back);
        btn_paint_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                
                if (listBitmap.size()>1){
                    int size = listBitmap.size()-1;
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(listBitmap.get(size-1));
                        listBitmap.remove(size);
                        Bitmap bitmap  = BitmapFactory.decodeStream(fis).copy(Bitmap.Config.ARGB_8888, true);
                        img.setImageBitmap(null);
                        showImage(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else {
                    Bitmap mBitmap = drawableToBitamp(getResources().getDrawable(R.mipmap.linkpage_1));
                    showImage(mBitmap);  
                }

            }
        });
        // 绘图
        Bitmap mBitmap = drawableToBitamp(getResources().getDrawable(R.mipmap.linkpage_1));
        showImage(mBitmap);

    }


    private void showImage(final Bitmap mBitmap) {
        // 创建一张空白图片

        // 创建一张画布
        canvas = new Canvas(mBitmap);
        // 画布背景为白色
//        canvas.drawColor(Color.WHITE);
        // 创建画笔
        paint = new Paint();
        // 画笔颜色为蓝色
        paint.setColor(Color.BLUE);
        // 宽度5个像素
        paint.setStrokeWidth(5);
        // 先将白色背景画上
        canvas.drawBitmap(mBitmap, new Matrix(), paint);
        img.setImageBitmap(mBitmap);

        img.setOnTouchListener(new OnTouchListener() {
            int startX;
            int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 获取手按下时的坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取手移动后的坐标
                        int endX = (int) event.getX();
                        int endY = (int) event.getY();
                        // 在开始和结束坐标间画一条线
                        canvas.drawLine(startX, startY, endX, endY, paint);
                        // 刷新开始坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        img.setImageBitmap(mBitmap);
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                Message msg = new Message();
                                msg.what = 001;
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("bitmap",mBitmap);
                                msg.setData(bundle);
                                if (null != handler) {
                                    handler.sendMessage(msg);
                                }
                            }
                        }.start();
                        break;
                }
                return true;
            }
        });

    }

    private void saveBitmap(Bitmap bitmap) {
        int position = listBitmap.size();
        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "语音评论需要sdcard支持", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory(),
                "yunshuxieMBA/drawingBoard");
        if (!file.exists()) {
            file.mkdirs();
        }
        String path = file + "/ceshi" + position + ".jpg";
        OutputStream stream;
        try {
            stream = new FileOutputStream(path);
            bitmap.compress(CompressFormat.JPEG, 100, stream);
            listBitmap.add(path);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Bitmap drawableToBitamp(Drawable drawable)
    {
        int w = drawable.getMinimumWidth();
        int h = drawable.getMinimumHeight();
//      int height = getWindow().getWindowManager().getDefaultDisplay().getHeight();  
        int wehit = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w,500,config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图  
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

}

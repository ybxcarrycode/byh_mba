package com.xszj.mba.personImage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xszj.mba.R;


/**
 * 裁剪界面
 */
public class CropImageActivity extends Activity implements OnClickListener {

    private CropImageView mImageView;
    private Bitmap mBitmap;

    private CropImage mCrop;

    private Button mSave;
    private Button mCancel, rotateLeft, rotateRight;

    /**
     * 表示 当前页面显示的（未裁剪）图片的绝对路径。
     */
    private String mPath = "CropImageActivity";

    private String TAG = "";
    public int screenWidth = 0;
    public int screenHeight = 0;

    private ProgressBar mProgressBar;

    public static final int SHOW_PROGRESS = 2000;

    public static final int REMOVE_PROGRESS = 2001;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SHOW_PROGRESS:
                    mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case REMOVE_PROGRESS:
                    mHandler.removeMessages(SHOW_PROGRESS);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gl_modify_avatar);

        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBitmap != null) {
            mBitmap = null;
        }
    }

    private void init() {
        getWindowWH();
        mPath = getIntent().getStringExtra("path");
        Log.e("TAG", "得到的图片的路径�? = " + mPath);
        mImageView = (CropImageView) findViewById(R.id.gl_modify_avatar_image);
        mSave = (Button) this.findViewById(R.id.gl_modify_avatar_save);
        mCancel = (Button) this.findViewById(R.id.gl_modify_avatar_cancel);
        rotateLeft = (Button) this
                .findViewById(R.id.gl_modify_avatar_rotate_left);
        rotateRight = (Button) this
                .findViewById(R.id.gl_modify_avatar_rotate_right);
        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        rotateLeft.setOnClickListener(this);
        rotateRight.setOnClickListener(this);
        try {
            mBitmap = createBitmap(mPath, screenWidth, screenHeight);
            if (mBitmap == null) {
                Toast.makeText(CropImageActivity.this, "没有找到图片", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                resetImageView(mBitmap);
            }
        } catch (Exception e) {
            Toast.makeText(CropImageActivity.this, "没有找到图片", Toast.LENGTH_SHORT).show();
            finish();
        }
        addProgressbar();
    }

    /**
     * 获取屏幕的高和宽
     */
    private void getWindowWH() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    private void resetImageView(Bitmap b) {
        mImageView.clear();
        mImageView.setImageBitmap(b);
        mImageView.setImageBitmapResetBase(b, true);
        mCrop = new CropImage(CropImageActivity.this, mImageView, mHandler);
        mCrop.crop(b);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gl_modify_avatar_cancel:
                // mCrop.cropCancel();
                finish();
                break;
            case R.id.gl_modify_avatar_save:
                // 当点击本页面的“保存”按钮的时候。
                // 裁剪后的图片的路径。
                String path = mCrop.saveToLocal(mCrop.cropAndSave());
                Log.e("TAG", "截取后图片的路径�? = " + path);
                Intent intent = new Intent();
                intent.putExtra("path", path);

                // 把当前未裁剪的图片的绝对路径返回给AddLogActivity页面。
                intent.putExtra("originalPath", mPath);

                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.gl_modify_avatar_rotate_left:
                mCrop.startRotate(270.f);
                break;
            case R.id.gl_modify_avatar_rotate_right:
                mCrop.startRotate(90.f);
                break;

        }
    }

    protected void addProgressbar() {
        mProgressBar = new ProgressBar(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        addContentView(mProgressBar, params);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    public Bitmap createBitmap(String path, int w, int h) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            // 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存�?
            BitmapFactory.decodeFile(path, opts);
            int srcWidth = opts.outWidth;// 获取图片的原始宽�?
            int srcHeight = opts.outHeight;// 获取图片原始高度
            int destWidth = 0;
            int destHeight = 0;
            // 缩放的比�?
            double ratio = 0.0;
            if (srcWidth < w || srcHeight < h) {
                ratio = 0.0;
                destWidth = srcWidth;
                destHeight = srcHeight;
            } else if (srcWidth > srcHeight) {// 按比例计算缩放后的图片大小，maxLength是长或宽允许的最大长�?
                ratio = (double) srcWidth / w;
                destWidth = w;
                destHeight = (int) (srcHeight / ratio);
            } else {
                ratio = (double) srcHeight / h;
                destHeight = h;
                destWidth = (int) (srcWidth / ratio);
            }
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能�?�过inSampleSize来进行缩放，其�?�表明缩放的倍数，SDK中建议其值是2的指数�??
            newOpts.inSampleSize = (int) ratio + 1;
            // newOpts.inSampleSize = 2;
            Log.i("MSG", ((int) ratio + 1) + "--------");
            // inJustDecodeBounds设为false表示把图片读进内存中
            newOpts.inJustDecodeBounds = false;
            // 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
            newOpts.outHeight = destHeight;
            newOpts.outWidth = destWidth;
            // 获取缩放后图�?
            return BitmapFactory.decodeFile(path, newOpts);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

}
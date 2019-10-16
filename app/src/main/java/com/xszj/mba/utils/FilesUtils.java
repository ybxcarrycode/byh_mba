package com.xszj.mba.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */
public class FilesUtils {
    private static final File mParentPath = Environment.getExternalStorageDirectory();
    private static String mStoragePath = "";
    private static final String DST_FOLDER_NAME = "byh";
    /**
     * @return
     */
    private static String initPath() {
        if (mStoragePath.equals("")) {
            mStoragePath = mParentPath.getAbsolutePath() + "/" + DST_FOLDER_NAME;
        }
        return mStoragePath;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }


    //图片压缩机保存
    //压缩图片尺寸
    public static Bitmap compressBySize(String path, int w, int h) {

        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            opts.inSampleSize = 16;

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
            //  newOpts.inSampleSize = (int) ratio;
            //  int minSampleSize =2;
            //newOpts.inSampleSize= minSampleSize >(int) ratio?minSampleSize :(int)ratio;

            double maxSize = 0.4 * 512 * 512;//最大一兆
            int bmpsize;
            bmpsize = srcWidth * srcHeight * 32;
            if (bmpsize > maxSize) {
                newOpts.inSampleSize = bmpsize / (int) maxSize;
            } else
                newOpts.inSampleSize = 1;

            newOpts.inJustDecodeBounds = false;        // inJustDecodeBounds设为false表示把图片读进内存中
            newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
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


    //保存图片
    public static String saveBitmap(Bitmap bm, int position) {
        String filePath = initPath();

        String path = filePath + "/comment" + position + ".jpg";
        try {

            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }

            FileOutputStream fos = new FileOutputStream(file+"/comment" + position + ".jpg");
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return path;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleFile(List<String> list){
        for (String str: list){
            File file = new File (str);
            if (file.exists()) {
                Log.e("ddddd","wwwww");

                file.delete();
                Log.e("ddddd","wwwww111");
            }
        }
    }
}

package com.xszj.mba.activity;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.easefun.polyvsdk.demo.MediaController;
import com.easefun.polyvsdk.demo.VideoViewContainer;
import com.easefun.polyvsdk.ijk.IjkVideoView;
import com.easefun.polyvsdk.util.ScreenTool;
import com.net.nim.demo.NimApplication;
import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.sardar.CompressListener;
import com.xszj.mba.sardar.Compressor;
import com.xszj.mba.sardar.InitListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ybx on 2017/2/17.
 */

public class TestActivity extends BaseActivity {
    private Button btn;
    private List<String> listPath = new ArrayList<>();
    private List<String> listduration = new ArrayList<>();
    private List<String> listsize = new ArrayList<>();
    private String cmd;
    private Compressor com;
    private IjkVideoView videoView = null;
    private MediaController mediaController = null;
    ProgressBar progressBar;
    private VideoViewContainer rl;
    int w = 0, h = 0, adjusted_h = 0;
    private String vid = "a53584648baefbe01433587f1bf42eca_a";
    private String vedioTitle = "ddsdd";

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_test_layout;
    }

    @Override
    protected void getIntentDate() {
        super.getIntentDate();

        getLoadMedia();
    }

    @Override
    protected void bindViews() {
        int[] wh = ScreenTool.getNormalWH(this);
        w = wh[0];
        h = wh[1];
        // 小窗口的比例
        float ratio = (float) 16 / 9;
        adjusted_h = (int) Math.ceil((float) w / ratio);

        btn = (Button) findViewById(R.id.btn);
        rl = (VideoViewContainer) findViewById(R.id.rl);
        videoView = (IjkVideoView) findViewById(R.id.videoview);
        progressBar = (ProgressBar) findViewById(R.id.loadingprogress);
    }

    @Override
    protected void initViews() {

        /*//这里我用LinearLayout布局为列，其他布局设置方法一样，只需改变布局名就行
        LinearLayout.LayoutParams layout=(LinearLayout.LayoutParams)rl.getLayoutParams();
        //获得rl控件的位置属性，需要注意的是，可以将button换成想变化位置的其它控件
        layout.setMargins(0,120,0,5);
        //设置rl的新位置属性,left，top，right，bottom
        rl.setLayoutParams(layout);
        //将新的位置加入rl控件中*/

        // 在缓冲时出现的loading
        videoView.setMediaBufferingIndicator(progressBar);
        videoView.setOpenTeaser(true);
        videoView.setOpenAd(true);
        videoView.setOpenQuestion(true);
        videoView.setOpenSRT(true);
        videoView.setNeedGestureDetector(true);
        videoView.setVideoLayout(IjkVideoView.VIDEO_LAYOUT_SCALE);
        videoView.setAutoContinue(true);


        videoView.setClick(new IjkVideoView.Click() {

            @Override
            public void callback(boolean start, boolean end) {
                mediaController.toggleVisiblity();
            }
        });

        mediaController = new MediaController(this, false,vid,vedioTitle,true);
        mediaController.setIjkVideoView(videoView);
        mediaController.setAnchorView(videoView);
        mediaController.setInstantSeeking(false);
        videoView.setMediaController(mediaController);


        // 设置切屏事件
        mediaController.setOnBoardChangeListener(new MediaController.OnBoardChangeListener() {

            @Override
            public void onPortrait() {
                changeToLandscape();
            }

            @Override
            public void onLandscape() {
                changeToPortrait();
            }
        });
    }


    private void changeToLandscape() {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 由于切换到横屏获取到的宽高可能和竖屏的不一样，所以需要重新获取宽高
        int[] wh = ScreenTool.getNormalWH(this);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(wh[0], wh[1]);
        rl.setLayoutParams(p);
    }

    private void changeToPortrait() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(400, 400);
        rl.setLayoutParams(p);
    }

    @Override
    protected void initListeners() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVid(vid,1);
                //compressVideo();
            }
        });
    }


    //获取视频
    private void getLoadMedia() {
        Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)); // id
                String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)); // 专辑
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)); // 艺术家
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 显示名称
                String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
                long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
                String resolution = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));

                listPath.add(path);
                listduration.add(duration+"");
                listsize.add(size+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        Log.e("MedVideo", listPath.get(0)+"/"+listduration.get(0)+"/"+listsize.get(0));

    }


    //压缩 处理
    private void compressVideo() {

//        // 设置视频缓存路径
//        File dcim = Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//        String cacheVideoPath = dcim + "/mabeijianxi/101.mp4";
//        if (dcim.exists()) {
//            File file = new File(cacheVideoPath);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//        }

        String cacheVideoPath = listPath.get(0);
        cacheVideoPath = cacheVideoPath.replace(".mp4", "");
        cacheVideoPath = cacheVideoPath + "_my606.mp4";


//        cmd = "-y -i " + listPath.get(0) + " -strict -2 -vcodec libx264 -preset ultrafast -crf 24 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 640x352 -aspect 16:9 " + cacheVideoPath;
        cmd = "-y -i " + listPath.get(0) + " -strict -2 -vcodec libx264 -preset ultrafast -crf 24 -acodec copy -ar 44100 -ac 2 -b:a 96k -s 480x272 -aspect 16:9 " + cacheVideoPath;
        com = new Compressor(this);

        com.loadBinary(new InitListener() {
            @Override
            public void onLoadSuccess() {
                com.execCommand(cmd, new CompressListener() {
                    @Override
                    public void onExecSuccess(String message) {
                        Log.e("MedVideoSuccess", message);
                    }

                    @Override
                    public void onExecFail(String reason) {
                        Log.e("MedVideoExecFail", reason);
                    }

                    @Override
                    public void onExecProgress(String message) {
                        Log.e("MedVideoProgress", message);
                    }
                });
            }

            @Override
            public void onLoadFail(String reason) {
                Log.e("MedVideoLoadFail", reason);
            }
        });
    }


}

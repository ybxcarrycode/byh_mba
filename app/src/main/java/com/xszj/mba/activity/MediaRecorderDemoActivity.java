package com.xszj.mba.activity;

import com.xszj.mba.R;
import com.xszj.mba.base.BaseActivity;
import com.xszj.mba.view.GlobalTitleView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Created by Administrator on 2017/3/22.
 */

public class MediaRecorderDemoActivity extends BaseActivity implements SurfaceHolder.Callback{

    private Button button_start;
    private Button button_stop;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private File storageDir;
    private File tempFile;
    private MediaRecorder mediaRecorder;
    private Camera camera;
    private Spinner spinner;
    private int width;
    private int height;
    private int hou;
    private int min;
    private int sec;
    private Handler handler;
    private TextView textView_time;
    private GlobalTitleView titleView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            storageDir = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "Demo" + File.separator);
            if (!storageDir.exists()) {
                storageDir.mkdir();
            }
            button_stop.setVisibility(View.GONE);
        } else {
            button_start.setVisibility(View.GONE);
            Toast.makeText(MediaRecorderDemoActivity.this, "SDcard is not exist", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_mediarecord;
    }

    @Override
    protected void bindViews() {
        titleView = (GlobalTitleView) findViewById(R.id.globalTitleView);
        button_start = (Button)this.findViewById(R.id.button_start);
        button_stop = (Button)this.findViewById(R.id.button_stop);
        surfaceView = (SurfaceView)this.findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(MediaRecorderDemoActivity.this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        spinner = (Spinner)this.findViewById(R.id.spinner_size);
        width = getWindowManager().getDefaultDisplay().getWidth();
        height =getWindowManager().getDefaultDisplay().getHeight();
        handler = new Handler();
        textView_time = (TextView)this.findViewById(R.id.textView_time);
    }

    @Override
    protected void initViews() {
        titleView.setBackVisible(true);
        titleView.setBackIconImage(R.drawable.icon_back_arrow_normal);
    }

    @Override
    protected void initListeners() {

        button_start.setOnClickListener(btnStartListener);
        button_stop.setOnClickListener(btnStopListener);
        spinner.setOnItemSelectedListener(spinnerListener);
    }

    private View.OnClickListener btnStartListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            button_start.setVisibility(View.GONE);
            button_stop.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);

            hou = 0;
            min = 0;
            sec = 0;
            handler.postDelayed(refreshTime, 1000);

            try {
                tempFile = File.createTempFile("Demo", ".3gp", storageDir);
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setCamera(camera);
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

                // Step 3: Set output format and encoding (for versions prior to API
                // Level 8)
                CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_QCIF);
                camcorderProfile.videoFrameWidth = 1024;
                camcorderProfile.videoFrameHeight = 768;
                camcorderProfile.videoFrameRate = 15;
                camcorderProfile.videoCodec = MediaRecorder.VideoEncoder.H264;
                // camcorderProfile.audioCodec = MediaRecorder.AudioEncoder.AAC;
                camcorderProfile.fileFormat = MediaRecorder.OutputFormat.MPEG_4;

                mediaRecorder.setProfile(camcorderProfile);

                mediaRecorder.setMaxDuration(120000);

                // Step 4: Set output file
                mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
                mediaRecorder.setOutputFile(tempFile.getAbsolutePath());

                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener btnStopListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            button_start.setVisibility(View.VISIBLE);
            button_stop.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);

            handler.removeCallbacks(refreshTime);

            if (mediaRecorder != null) {
                try {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    Log.e("Demo", tempFile.getAbsolutePath());
                } catch(RuntimeException e) {
                    Log.e("Demo", e.getMessage());
                }
            }
        }
    };

    private OnItemSelectedListener spinnerListener = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            // TODO Auto-generated method stub
            Spinner spinner = (Spinner)arg0;
            String wh[] = spinner.getSelectedItem().toString().split("\\*");
            width = Integer.parseInt(wh[0]);
            height = Integer.parseInt(wh[1]);
            try {
                camera.lock();
                camera.stopPreview();
                Camera.Parameters para = camera.getParameters();
                para.setPreviewSize(width, height);
                camera.setParameters(para);
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();

                Log.d("Demo", camera.getParameters().getPreviewSize().width + "*"
                        + camera.getParameters().getPreviewSize().height);

                camera.unlock();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e("Demo", e.getMessage());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    };

    private Runnable refreshTime = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            sec++;
            handler.postDelayed(refreshTime, 1000);
            if (sec >= 60) {
                sec = sec % 60;
                min++;
            }
            if (min >= 60) {
                min = min % 60;
                hou++;
            }
            textView_time.setText(timeFormat(hou) + ":" + timeFormat(min) + ":" + timeFormat(sec));
        }
    };

    private String timeFormat(int t) {
        if (t / 10 == 0) {
            return "0" + t;
        } else {
            return t + "";
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera = Camera.open();
        List<Size> supportedSize = camera.getParameters().getSupportedPreviewSizes();
        if (supportedSize != null) {
            List<String> list = new ArrayList<String>();
            for (Size s: supportedSize) {
                list.add(s.width + "*" + s.height);
            }
            list.remove(0); // 在使用G7的摄像头支持的最大分辨率进行拍摄的时候会出现stop failed错误，无法关闭MediaRecorder，原因不明！
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MediaRecorderDemoActivity.this,
                    android.R.layout.simple_spinner_dropdown_item, list);
            spinner.setAdapter(adapter);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera.release();
    }



}

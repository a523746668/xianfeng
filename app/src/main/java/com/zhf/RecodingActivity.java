package com.zhf;


import android.content.res.Configuration;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qingyii.hxtz.R;

import org.simple.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


//录制视频
public class RecodingActivity extends AppCompatActivity implements View.OnClickListener ,SurfaceHolder.Callback{

    private static final int REQUEST_PERMISSION_CAMERA_CODE =100 ;
    @BindView(R.id.surfaceview)
  SurfaceView mSurfaceview;

    @BindView(R.id.btnStartStop)
    Button mBtnStartStop;
     private Unbinder unbinder;
    private MediaRecorder mediaRecorder;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private MediaPlayer mediaPlayer;
    private String path;
    @BindView(R.id.text)
    TextView textView;
    private int text = 0;

    private boolean isRecoding=false;//  正在录制
    private android.os.Handler handler = new android.os.Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
           if(text>=20){
               stopRecord();
           }
            text++;
            textView.setText(text+"");
            handler.postDelayed(this,1000);
        }
    };


    private MediaRecorder.OnErrorListener onErrorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mediaRecorder, int what, int extra) {
            try {
                if (mediaRecorder != null) {
                    mediaRecorder.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_recoding);

             EventBus.getDefault().register(this);
            unbinder= ButterKnife.bind(this);
          //  Toasty.info(this,"正在录制视频，最多录制30秒，按下stop键停止录制",0).show();
          mBtnStartStop.setOnClickListener(this);
          init ();

    }


    private void init() {
        mSurfaceHolder=mSurfaceview.getHolder();
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.setKeepScreenOn(true);
        // 设置分辨率
        mSurfaceHolder.setFixedSize(320, 280);
        mSurfaceHolder.addCallback(this);

    }

    //开始录制
    private void startrecoding() {
        initCamera();
        mCamera.unlock();
            setConfigRecord();

        try {

            mediaRecorder.prepare();
            mediaRecorder.start();

        } catch (Exception e) {
           e.printStackTrace();
           // Toasty.error(this,"录制视频失败："+e.getMessage().toString(),1).show();
        }

    }

   //设置recoding属性
    private void setConfigRecord() {
        if(mediaRecorder==null){
            mediaRecorder = new MediaRecorder();
        }

        mediaRecorder.reset();
        mediaRecorder.setCamera(mCamera);
        mediaRecorder.setOnErrorListener(onErrorListener);

        //使用SurfaceView预览
        mediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

        //1.设置采集声音
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置采集图像
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //2.设置视频，音频的输出格式 mp4
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        //3.设置音频的编码格式
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        //设置图像的编码格式
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
        //设置立体声
//        mediaRecorder.setAudioChannels(2);
        //设置最大录像时间 单位：毫秒
       mediaRecorder.setMaxDuration(22 * 1000);
        //设置最大录制的大小 单位，字节
//        mediaRecorder.setMaxFileSize(1024 * 1024);
        //音频一秒钟包含多少数据位;
        mediaRecorder.setAudioEncodingBitRate(44100);
        CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);

        if (mProfile.videoBitRate > 2 * 1024 * 1024)
            mediaRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);
        else
            mediaRecorder.setVideoEncodingBitRate(1024 * 1024);
        mediaRecorder.setVideoFrameRate(mProfile.videoFrameRate);

        //设置选择角度，顺时针方向，因为默认是逆向90度的，这样图像就是正常显示了,这里设置的是观看保存后的视频的角度
        mediaRecorder.setOrientationHint(90);
        //设置录像的分辨率
        mediaRecorder.setVideoSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);

          path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+System.currentTimeMillis()+"xf.mp4";
        mediaRecorder.setOutputFile(path);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
          switch (v.getId()) {
              case R.id.btnStartStop:

                      if(isRecoding){
                          stopRecord();
                      }   else {
                          startrecoding();
                          handler.postDelayed(runnable,1000);
                          isRecoding=true;
                          mBtnStartStop.setText("保存上传");
                      }
                  break;
          }
    }


    public void stopRecord() {
        if (isRecoding && mediaRecorder != null) {
            // 设置后不会崩
            mediaRecorder.setOnErrorListener(null);
            mediaRecorder.setPreviewDisplay(null);
            //停止录制
                mediaRecorder.stop();
            //释放资源
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;

            isRecoding = false;

            EventBus.getDefault().post(path);
            handler.removeCallbacks(runnable);
            finish();

        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        initCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopCamera();
    }
  // 初始化摄像头
    private void initCamera() {
        if (mCamera != null) {
            stopCamera();
        }
        //默认启动后置摄像头
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        if (mCamera == null) {
            // Toast.makeText(this, "未能获取到相机！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            //配置CameraParams
            setCameraParams();
            //启动相机预览
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("TAG", "Error starting camera preview: " + e.getMessage());
        }

    }

  // 设置摄像头竖屏幕
    private void setCameraParams() {
        if (mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();
            //设置相机的很速屏幕
            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                params.set("orientation", "portrait");
                mCamera.setDisplayOrientation(90);
            } else {
                params.set("orientation", "landscape");
                mCamera.setDisplayOrientation(0);
            }
            //设置聚焦模式
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            //缩短Recording启动时间
            params.setRecordingHint(true);
            //是否支持影像稳定能力，支持则开启
            if (params.isVideoStabilizationSupported())
                params.setVideoStabilization(true);
            mCamera.setParameters(params);
        }
    }



   //释放摄像头资源
    private void stopCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
}

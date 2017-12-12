package com.example.laotong_ausu.mz_09_camera_video;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.VideoSource;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button bt_take_video;
    private SurfaceView sView;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_take_video = (Button) findViewById(R.id.bt_take_video);
        sView = (SurfaceView) findViewById(R.id.id_sv);

        sView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        sView.getHolder().addCallback(new MycallBack());

        bt_take_video.setOnClickListener(new OnClickListener() {
            private MediaRecorder recorder;

            @Override
            public void onClick(View v) {
                Button button = (Button) v;

                if ("开始录像".equals(button.getText())) {
                    button.setText("暂停录像");
                    camera.unlock(); // 1.解锁摄像头
                    recorder = new MediaRecorder(); // 2.创建一个多媒体记录器
                    recorder.setCamera(camera); // 3.给记录器设置摄像头
                    recorder.setAudioSource(AudioSource.MIC); // 4.设置音频源
                    recorder.setVideoSource(VideoSource.CAMERA); // 5.设置视频源
                    recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH)); // 6.设置输出格式和编码，2.2以上。
                    recorder.setOutputFile("/mnt/sdcard/" + System.currentTimeMillis() + ".mp4"); // 7.设置文件输出路径
                    recorder.setPreviewDisplay(sView.getHolder().getSurface()); // 8.设置预览


                    try {
                        recorder.prepare(); // 9.准备
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    recorder.start(); // 10.开始录像
                } else {
                    button.setText("开始录像");
                    if (recorder != null) {
                        recorder.stop(); // 停止多媒体记录器
                        recorder.release(); // 释放
                        recorder = null; // 销毁
                        camera.lock(); // 锁定摄像头
                    }
                }
            }
        });

    }

    // surfaceview的回调方法
    private class MycallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera = Camera.open();
                camera.setPreviewDisplay(sView.getHolder());
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        }
    }
}

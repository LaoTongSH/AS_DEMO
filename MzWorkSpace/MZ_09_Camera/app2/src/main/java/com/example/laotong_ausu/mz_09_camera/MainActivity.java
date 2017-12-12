package com.example.laotong_ausu.mz_09_camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button bt_take_pic;
    private FrameLayout fLayout;
    private Camera cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_take_pic = (Button) findViewById(R.id.bt_take_pic);
        fLayout = (FrameLayout) findViewById(R.id.fl_suf);

        cm = getCameraInstance(); // 创建camera的实例

        CameraPreview preview = new CameraPreview(this, cm);
        fLayout.addView(preview);

        boolean hasCamera = checkCameraHardware(this);
        if (hasCamera) {
            Toast.makeText(this, "检测到摄像头", 0).show();
        } else {
            Toast.makeText(this, "未检测到摄像头", 0).show();
        }
        bt_take_pic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                cm.autoFocus(new AutoFocusCallback() {

                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            cm.takePicture(new ShutterCallback() {

                                @Override
                                public void onShutter() {
                                    Toast.makeText(getApplicationContext(), "点击快门", 0).show();

                                }
                            }, null, new PictureCallback() {
                                @Override
                                public void onPictureTaken(byte[] data, Camera camera) {
                                    try {
                                        File file = new File(Environment.getExternalStorageDirectory(), SystemClock.uptimeMillis() + ".jpg");
                                        FileOutputStream fos = new FileOutputStream(file);
                                        fos.write(data);
                                        fos.close();
                                        Toast.makeText(getApplicationContext(), "拍照成功", 0).show();
                                        cm.startPreview();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                    }
                });

            }
        });

    }

    /** 创建一个摄像头的实例. */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // open()不传值或者传0，就是使用后置摄像头，如果传1，那就是使用前置摄像头。
            Parameters parameters = c.getParameters();
            parameters.setFlashMode(parameters.FLASH_MODE_AUTO);// 设置闪光灯强制打开 FLASH_MODE_TORCH
            parameters.setWhiteBalance(Parameters.WHITE_BALANCE_AUTO);// 设置白平衡，WHITE_BALANCE
            parameters.setColorEffect(parameters.EFFECT_SEPIA);//设置照片颜色特效，EFFECT

            parameters.setPictureSize(1280, 720);//设置拍摄照片的尺寸 (4288,3120 图片达5M)
            parameters.setPreviewSize(1280, 720);//设置照片的预览尺寸
            parameters.setJpegQuality(100);//设置照片的质量

            // 2.2以后
            c.setDisplayOrientation(90); //0 水平 90垂直方向
//            c.setParameters(parameters);
            System.out.println("parameters:" + parameters.flatten());

        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    /** 检测手机上是否有摄像头 */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        cm.stopPreview();
        cm.release();
        cm = null;

    }
}

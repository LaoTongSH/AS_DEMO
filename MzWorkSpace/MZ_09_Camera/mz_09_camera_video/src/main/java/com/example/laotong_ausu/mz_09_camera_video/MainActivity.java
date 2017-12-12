package com.example.laotong_ausu.mz_09_camera_video;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Button bt_take_video;
    private Button bt_watch_video;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_take_video = (Button) findViewById(R.id.bt_take_video);
        bt_watch_video = (Button) findViewById(R.id.bt_watch_video);

        bt_take_video.setOnClickListener(new OnClickListener() {// 按钮的点击事件

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);// 创建一个意图，设置动作为录像
                file = new File(Environment.getExternalStorageDirectory(), SystemClock.uptimeMillis() + ".mp4");
                // 创建一个文件存储file，并设置文件名
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));// 设置uri为存储录像的路径
                startActivityForResult(intent, 200);

            }
        });

        bt_watch_video.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);// 创建意图
                intent.setDataAndType(Uri.fromFile(file), "video/*");// 设置数据和类型
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 200:
                Toast.makeText(MainActivity.this, "录像成功", 0).show();
                break;

            default:
                break;
        }

    }

}

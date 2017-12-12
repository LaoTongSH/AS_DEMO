package com.example.laotong_ausu.mz_09_camera;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private Button bt_camera;
    private Button bt_show_img;
    private ImageView iv_img;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_img = (ImageView) findViewById(R.id.iv_img);
        bt_camera = (Button) findViewById(R.id.bt_camera);
        bt_show_img = (Button) findViewById(R.id.bt_show_img);
        bt_camera.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //创建一个意图，设置动作为拍照
                // fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                // intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");//创建一个uri，指向sd卡
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));   //设置uri为存储照片的路径

                startActivityForResult(intent, 100);//开启系统拍照的activity

            }
        });
        bt_show_img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            iv_img.setImageURI(Uri.fromFile(file));

        }
    }

}
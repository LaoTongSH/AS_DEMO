package com.laotong.mylockview;

/**
 * Created by LaoTong on 2016/1/11. 10:10
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("sp", Context.MODE_PRIVATE);
                String passwordStr = sp.getString("password", "");
                //判断是否已经设置过密码
                //没有设置过密码
                if(TextUtils.isEmpty(passwordStr)){
                    startActivity(new Intent(WelcomeActivity.this, SettingPasswordActivity.class));
                    finish();
                    //解锁
                }else {
                    getFragmentManager().beginTransaction().replace(android.R.id.content, PasswordFragment.newInstance(PasswordFragment.TYPE_CHECK)).commit();

                }
            }
        }, 1000);
    }
}

package com.laotong.mylockview;

/**
 * Created by LaoTong on 2016/1/11. 10:12
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SettingPasswordActivity extends  Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getFragmentManager().beginTransaction().replace(android.R.id.content, PasswordFragment.newInstance(PasswordFragment.TYPE_SETTING)).commit();
    }

}

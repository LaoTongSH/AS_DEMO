package com.laotong_ausu.lockview.util;

/**
 * Created by LaoTong_ausu on 2016/1/7.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
    public static String getGesturePassword(Context context) {
        SharedPreferences sp = context.getSharedPreferences("gesturePassword", Context.MODE_PRIVATE);
        String password = sp.getString("password", "");
        return password;
    }

    public static void setGesturePassword(Context context, String gesturePassword) {
        SharedPreferences sp = context.getSharedPreferences("gesturePassword", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password", gesturePassword);
        editor.commit();
    }
}


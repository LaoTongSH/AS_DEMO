package com.laotong.mylockview;

/**
 * Created by LaoTong on 2016/1/11. 10:12
 */

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laotong.mylockview.LockPatternView.OnPatterChangeListner;

/**
 * 密码碎片
 *
 * @author Administrator
 *
 */
public class PasswordFragment extends Fragment implements OnPatterChangeListner, OnClickListener {
    public static final String TYPE_SETTING = "setting";
    public static final String TYPE_CHECK = "check";
    private TextView mainLock;
    private LockPatternView lockPatternView;
    private LinearLayout btnLinearLayout;
    private static final String ARG_TYPE = "type";
    private String passString;

    public PasswordFragment() {
    }

    public static android.app.Fragment newInstance(String arg_type) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, arg_type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_password, container, false);
        mainLock = (TextView) v.findViewById(R.id.fragment_password_lock_hint);
        lockPatternView = (LockPatternView) v
                .findViewById(R.id.fragment_password_lock);
        btnLinearLayout = (LinearLayout) v
                .findViewById(R.id.fragment_password_btn_layout);
        // 设置密码
        if (getArguments() != null) {
            if (TYPE_SETTING.equals(getArguments().getString(ARG_TYPE))) {
                btnLinearLayout.setVisibility(View.VISIBLE);
            }
        }
        v.findViewById(R.id.fragment_password_btn_commit).setOnClickListener(this);
        v.findViewById(R.id.fragment_password_btn_forget).setOnClickListener(this);
        lockPatternView.setPatterChangeListner(this);
        return v;
    }

    @Override
    public void onPatterChange(String passString) {
        this.passString = passString;
        if (!TextUtils.isEmpty(passString)) {
            mainLock.setText(passString);
            //没有密码直接进入主页面
            // 检查密码
            if (getArguments() != null) {
                if (TYPE_CHECK.equals(getArguments().getString(ARG_TYPE))) {
                    SharedPreferences sp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
                    // 检查成功
                    if (passString.equals(sp.getString("password", ""))) {
                        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                        // 检查失败
                    } else {
                        mainLock.setText("密码错误");
                        lockPatternView.errorPoint();
                    }
                }
            }
        } else {
            mainLock.setText("请设置至少5位密码");
        }
    }

    @Override
    public void onPatterStart(boolean isStart) {
        if (isStart) {
            mainLock.setText("请绘制图案");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_password_btn_commit:
                SharedPreferences sp = getActivity().getSharedPreferences("sp",
                        Context.MODE_PRIVATE);
                // 设置密码
                sp.edit().putString("password", passString).commit();
                getActivity().finish();
                break;
            case R.id.fragment_password_btn_forget:
                sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
                // 设置密码
                sp.edit().putString("password", "").commit();
                startActivity(new Intent(getActivity(), SettingPasswordActivity.class));
                getActivity().finish();
                break;
        }
    }
}

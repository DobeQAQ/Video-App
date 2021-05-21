package com.example.group4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.group4.activity.BaseActivity;
import com.example.group4.activity.HomeActivity;
import com.example.group4.activity.LoginActivity;
import com.example.group4.activity.RegisterActivity;
import com.example.group4.util.StringUtils;

public class MainActivity extends BaseActivity {

    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        if (!StringUtils.isEmpty(findByKey("token"))) {
            navigateTo(HomeActivity.class);
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(LoginActivity.class);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(RegisterActivity.class);
            }
        });
    }
}
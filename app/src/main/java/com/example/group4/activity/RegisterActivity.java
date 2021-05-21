package com.example.group4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.group4.MainActivity;
import com.example.group4.R;
import com.example.group4.service.Impl.UserServiceImpl;
import com.example.group4.service.UserService;

public class RegisterActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private Button btnRegister;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnRegister = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etAccount.getText().toString().trim();
                String password = etPwd.getText().toString().trim();
                if(username.isEmpty()||password.isEmpty()){
                    showToast("用户名或密码为空");
                }else{
                    UserService userService=new UserServiceImpl(RegisterActivity.this);
                    //插入用户信息
                    if(userService.register(username,password)){
                        showToast("注册成功");
                        navigateTo(MainActivity.class);
                    }else{
                        showToast("注册失败，用户名已存在");
                    }
                }

            }
        });
    }

}
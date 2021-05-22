package com.example.group4.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.group4.R;
import com.example.group4.entity.User;
import com.example.group4.service.Impl.UserServiceImpl;
import com.example.group4.service.UserService;
import com.example.group4.util.StringUtils;

public class LoginActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private Button btnLogin;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etAccount.getText().toString().trim();
                String password = etPwd.getText().toString().trim();
                if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                    showToast("用户名或密码为空");
                } else {
                    UserService userService = new UserServiceImpl(LoginActivity.this);
                    //查询核对用户信息
                    if (userService.login(username, password)) {
                        Intent in = new Intent(mContext, HomeActivity.class);
                        User user = userService.getUserByName(username);
                        in.putExtra("user", user);
                        startActivity(in);
                        showToast("登录成功");
                    } else {
                        showToast("用户名或密码错误");
                    }
                }
            }
        });
    }


}
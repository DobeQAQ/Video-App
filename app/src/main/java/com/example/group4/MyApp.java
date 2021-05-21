package com.example.group4;

import android.app.Application;
import android.content.SharedPreferences;

import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinCompatManager.withoutActivity(this)
                .addInflater(new SkinAppCompatViewInflater())           // 基础控件换肤初始化
                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
//
//        //查找本地当前皮肤状态
//        SharedPreferences sp = getSharedPreferences("sp_group4", MODE_PRIVATE);
//        String skin = sp.getString("skin", "");
//        if (skin.equals("night")) {
//            SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
//        } else {
//            SkinCompatManager.getInstance().restoreDefaultTheme();
//        }

        SkinCompatManager.getInstance().restoreDefaultTheme();
    }
}

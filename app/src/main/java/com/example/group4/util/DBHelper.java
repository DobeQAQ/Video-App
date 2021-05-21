package com.example.group4.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "group4.db";
    public static final int DATABASE_VERSION = 1;

    //    单例模式,返回唯一的dbHelper实例
    public static DBHelper dbHelper;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //    单例模式,返回唯一的DBHelper实例
    public synchronized static DBHelper getInstance(Context context) {
        if (null == dbHelper) {
            dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        只有创建表格时调用
        //用于创建表字段   create
        String sql="create table user("
                +"user_id integer primary key autoincrement,"
                +"username text,"
                +"password text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //修改数据库表格字段
    }
}

package com.example.group4.service.Impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.group4.entity.User;
import com.example.group4.service.UserService;
import com.example.group4.util.DBHelper;

public class UserServiceImpl implements UserService {

    private DBHelper dbHelper;

    public UserServiceImpl(Context context){
        dbHelper=DBHelper.getInstance(context);
    }

    @Override
    public Boolean login(String username, String password) {
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select * from user where username=? and password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }

    @Override
    public Boolean register(String username, String password) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String sql="select * from user where username="+username;
        Cursor cursor=db.rawQuery(sql,null);
        if(cursor.moveToFirst()==true){
            cursor.close();
            return  false;
        }
        sql="insert into user(username,password) values(?,?)";
        db.execSQL(sql,new String[]{username,password});
        return true;
    }

    @Override
    public User getUserByName(String username) {
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select * from user where username=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username});
        User user=new User();
        while(cursor.moveToNext()){
            user.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
        }
        return user;
    }
}

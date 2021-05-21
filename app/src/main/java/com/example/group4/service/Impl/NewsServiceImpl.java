package com.example.group4.service.Impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.group4.entity.NewsEntity;
import com.example.group4.service.NewsService;
import com.example.group4.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class NewsServiceImpl implements NewsService {

    private DBHelper dbHelper;

    public NewsServiceImpl(Context context){
        dbHelper=DBHelper.getInstance(context);
    }

    @Override
    public List<NewsEntity> listNews() {
        List<NewsEntity> list=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="select n.news_id news_id,\n" +
                "\t\t\t n.news_title news_title,\n" +
                "\t\t\t n.author_name author_name,\n" +
                "\t\t\t n.header_url header_url,\n" +
                "\t\t\t n.release_date release_date,\n" +
                "\t\t\t n.news_content news_content\n" +
                "\t\t\t from news n\n" +
                "\t\t\t ORDER BY n.release_date DESC";
        Cursor cursor=db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            NewsEntity newsEntity=new NewsEntity();
            newsEntity.setNewsId(cursor.getInt(cursor.getColumnIndex("news_id")));
            newsEntity.setNewsTitle(cursor.getString(cursor.getColumnIndex("news_title")));
            newsEntity.setAuthorName(cursor.getString(cursor.getColumnIndex("author_name")));
            newsEntity.setHeaderUrl(cursor.getString(cursor.getColumnIndex("header_url")));
            newsEntity.setReleaseDate(cursor.getString(cursor.getColumnIndex("release_date")));
            newsEntity.setNewsContent(cursor.getString(cursor.getColumnIndex("news_content")));
            list.add(newsEntity);
        }
        return list;
    }
}

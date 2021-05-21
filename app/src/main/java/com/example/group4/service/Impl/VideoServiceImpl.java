package com.example.group4.service.Impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.group4.entity.VideoEntity;
import com.example.group4.service.VideoService;
import com.example.group4.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class VideoServiceImpl implements VideoService {

    private DBHelper dbHelper;

    public VideoServiceImpl(Context context){
        dbHelper=DBHelper.getInstance(context);
    }

    /**
     * 根据分类查询视频列表，根据用户id读取个人是否喜爱收藏
     * @param categoryId
     * @param user_id
     * @return
     */

    @Override
    public List<VideoEntity> listVideoByCategory(int categoryId, int user_id) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        List<VideoEntity> list = new ArrayList<>();
        String sql="select v.vid vid,\n" +
                "\t\t\t v.vtitle vtitle,\n" +
                "\t\t\t v.author author,\n" +
                "\t\t\t v.coverurl coverurl,\n" +
                "\t\t\t v.headurl headurl,\n" +
                "\t\t\t v.playerurl playerurl,\n" +
                "\t\t\t v.create_time create_time,\n" +
                "\t\t\t v.update_time update_time,\n" +
                "\t\t\t v.category_id category_id,\n" +
                "\t\t\t v.like_num like_num,\n" +
                "\t\t\t v.collect_num collect_num,\n" +
                "\t\t\t vs.flag_like flag_like,\n" +
                "\t\t\t vs.flag_collect flag_collect,\n" +
                "\t\t\t vs.user_id user_id\n" +
                "\t\t\t from video v,video_social vs\n" +
                "\t\t\t where vs.video_id = v.vid \n" +
                "\t\t\t and vs.user_id= ?\n" +
                "\t\t\t and v.category_id=?\n" +
                "\t\t\t ORDER BY v.update_time DESC";
        Cursor cursor=db.rawQuery(sql,new String[]{String.valueOf(user_id),String.valueOf(categoryId)});
        while(cursor.moveToNext()){
            VideoEntity videoEntity = new VideoEntity();
            videoEntity.setVid(cursor.getInt(cursor.getColumnIndex("vid")));
            videoEntity.setVtitle(cursor.getString(cursor.getColumnIndex("vtitle")));
            videoEntity.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            videoEntity.setCoverurl(cursor.getString(cursor.getColumnIndex("coverurl")));
            videoEntity.setHeadurl(cursor.getString(cursor.getColumnIndex("headurl")));
            videoEntity.setPlayurl(cursor.getString(cursor.getColumnIndex("playerurl")));
            videoEntity.setCreateTime(cursor.getString(cursor.getColumnIndex("create_time")));
            videoEntity.setUpdateTime(cursor.getString(cursor.getColumnIndex("update_time")));
            videoEntity.setCategoryId(cursor.getInt(cursor.getColumnIndex("category_id")));
            videoEntity.setLikenum(cursor.getInt(cursor.getColumnIndex("like_num")));
            videoEntity.setCollectnum(cursor.getInt(cursor.getColumnIndex("collect_num")));
            videoEntity.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
            if(cursor.getInt(cursor.getColumnIndex("flag_like"))>0){
                videoEntity.setFlagLike(true);
            }else{
                videoEntity.setFlagLike(false);
            }
            if(cursor.getInt(cursor.getColumnIndex("flag_collect"))>0){
                videoEntity.setFlagCollect(true);
            }else{
                videoEntity.setFlagCollect(false);
            }
            list.add(videoEntity);
        }
        return list;
    }

    /**
     * 获取所有收藏的数据
     * @param user_id
     * @return
     */
    @Override
    public List<VideoEntity> listVideoByMyCollect(int user_id) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        List<VideoEntity> list = new ArrayList<>();
        String sql="select v.vid vid,\n" +
                "\t\t\t v.vtitle vtitle,\n" +
                "\t\t\t v.author author,\n" +
                "\t\t\t v.coverurl coverurl,\n" +
                "\t\t\t v.headurl headurl,\n" +
                "\t\t\t v.playerurl playerurl,\n" +
                "\t\t\t v.update_time update_time,\n" +
                "\t\t\t vs.user_id user_id,\n" +
                "\t\t\t vs.flag_collect flag_collect\n" +
                "\t\t\t from video v,video_social vs\n" +
                "\t\t\t where v.vid=vs.video_id\n" +
                "\t\t\t and vs.flag_collect=1\n" +
                "\t\t\t and vs.user_id = ?\n" +
                "\t\t\t ORDER BY v.update_time DESC";
        Cursor cursor=db.rawQuery(sql,new String[]{String.valueOf(user_id)});
        while(cursor.moveToNext()){
            VideoEntity videoEntity = new VideoEntity();
            videoEntity.setVid(cursor.getInt(cursor.getColumnIndex("vid")));
            videoEntity.setVtitle(cursor.getString(cursor.getColumnIndex("vtitle")));
            videoEntity.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            videoEntity.setCoverurl(cursor.getString(cursor.getColumnIndex("coverurl")));
            videoEntity.setHeadurl(cursor.getString(cursor.getColumnIndex("headurl")));
            videoEntity.setPlayurl(cursor.getString(cursor.getColumnIndex("playerurl")));
            videoEntity.setUpdateTime(cursor.getString(cursor.getColumnIndex("update_time")));
            videoEntity.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
            videoEntity.setFlagCollect(true);
            list.add(videoEntity);
        }
        return list;
    }

    @Override
    public List<VideoEntity> listVideoByMyLike(int user_id) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        List<VideoEntity> list = new ArrayList<>();
        String sql="select v.vid vid,\n" +
                "\t\t\t v.vtitle vtitle,\n" +
                "\t\t\t v.author author,\n" +
                "\t\t\t v.coverurl coverurl,\n" +
                "\t\t\t v.headurl headurl,\n" +
                "\t\t\t v.playerurl playerurl,\n" +
                "\t\t\t v.update_time update_time,\n" +
                "\t\t\t vs.user_id user_id,\n" +
                "\t\t\t vs.flag_like flag_like\n" +
                "\t\t\t from video v,video_social vs\n" +
                "\t\t\t where v.vid=vs.video_id\n" +
                "\t\t\t and vs.flag_like=1\n" +
                "\t\t\t and vs.user_id = ?\n" +
                "\t\t\t ORDER BY v.update_time DESC";
        Cursor cursor=db.rawQuery(sql,new String[]{String.valueOf(user_id)});
        while(cursor.moveToNext()){
            VideoEntity videoEntity = new VideoEntity();
            videoEntity.setVid(cursor.getInt(cursor.getColumnIndex("vid")));
            videoEntity.setVtitle(cursor.getString(cursor.getColumnIndex("vtitle")));
            videoEntity.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            videoEntity.setCoverurl(cursor.getString(cursor.getColumnIndex("coverurl")));
            videoEntity.setHeadurl(cursor.getString(cursor.getColumnIndex("headurl")));
            videoEntity.setPlayurl(cursor.getString(cursor.getColumnIndex("playerurl")));
            videoEntity.setUpdateTime(cursor.getString(cursor.getColumnIndex("update_time")));
            videoEntity.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
            videoEntity.setFlagLike(true);
            list.add(videoEntity);
        }
        return list;
    }

    /**
     * 根据flag，修改喜爱值
     * @param videoEntity
     * @param flag
     */
    @Override
    public void updateLikeNum(VideoEntity videoEntity, int flag) {

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="update video SET like_num=like_num+? where vid=?";
        db.execSQL(sql, new Object[]{flag,videoEntity.getVid()});

        sql="UPDATE video_social SET flag_like=1-flag_like where video_id=? and user_id=?";
        db.execSQL(sql, new Object[]{videoEntity.getVid(),videoEntity.getUserId()});
    }

    /**
     * 根据flag修改收藏值
     * @param videoEntity
     * @param flag
     */
    @Override
    public void updateCollectNum(VideoEntity videoEntity, int flag) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="update video SET collect_num=collect_num+? where vid=?;";
        db.execSQL(sql, new Object[]{flag,videoEntity.getVid()});

        sql="UPDATE video_social SET flag_collect=1-flag_collect where video_id=? and user_id=?";
        db.execSQL(sql, new Object[]{videoEntity.getVid(),videoEntity.getUserId()});
    }
}

package com.example.group4.service;

import com.example.group4.entity.VideoEntity;

import java.util.List;

public interface VideoService {

    public List<VideoEntity> listVideoByCategory(int categoryId, int user_id);

    public List<VideoEntity> listVideoByMyCollect(int user_id);

    public List<VideoEntity> listVideoByMyLike(int user_id);

    public void updateLikeNum(VideoEntity videoEntity, int flag);//喜爱数变更

    public void updateCollectNum(VideoEntity videoEntity, int flag);//收藏数变更
}

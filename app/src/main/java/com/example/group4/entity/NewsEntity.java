package com.example.group4.entity;

import java.io.Serializable;

public class NewsEntity implements Serializable {

    /**
     * newsId : 1
     * newsTitle : 《忍者蛙》发售日公布 已上架Steam、支持简中
     * authorName : 3DMGAME
     * headerUrl : https://p9.pstatp.com/thumb/6eed00026c4eac713a44
     * releaseDate : 2020-07-31 22:23:00
     */

    private int newsId;
    private String newsTitle;
    private String authorName;
    private String headerUrl;
    private String releaseDate;
    private String newsContent;

    public NewsEntity() {
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}

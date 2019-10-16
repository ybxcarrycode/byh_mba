package com.xszj.mba.bean;

import java.io.Serializable;

/**
 * Created by Ybx on 2016/12/27.
 */

public class FindBean implements Serializable{
    /**
     * newsCover : http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg
     * linkUrl : www.baidu.com
     * newsId : 5
     * newsAuthor : 测试2
     * newsTitle : 咨询标题2
     */

    private String newsCover;
    private String linkUrl;
    private String newsId;
    private String newsAuthor;
    private String newsTitle;
    private String isCollect;
    private String newsContent;
    private String newsType;
    private String createDate;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getNewsCover() {
        return newsCover;
    }

    public void setNewsCover(String newsCover) {
        this.newsCover = newsCover;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
}

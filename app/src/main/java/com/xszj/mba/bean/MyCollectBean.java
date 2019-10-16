package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Ybx on 2017/2/13.
 */

public class MyCollectBean {


    /**
     * returnCode : 0
     * data : {"pageCount":"1","newsList":[{"newsCover":"http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg","createDate":"2017-02-09 00:00:00","newsTitle":"咨询标题2"}]}
     * returnMsg : 操作成功
     */

    private String returnCode;
    private DataBean data;
    private String returnMsg;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public static class DataBean {
        /**
         * pageCount : 1
         * newsList : [{"newsCover":"http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg","createDate":"2017-02-09 00:00:00","newsTitle":"咨询标题2"}]
         */

        private String pageCount;
        private List<NewsListBean> newsList;

        public String getPageCount() {
            return pageCount;
        }

        public void setPageCount(String pageCount) {
            this.pageCount = pageCount;
        }

        public List<NewsListBean> getNewsList() {
            return newsList;
        }

        public void setNewsList(List<NewsListBean> newsList) {
            this.newsList = newsList;
        }

        public static class NewsListBean {
            /**
             * newsCover : http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg
             * createDate : 2017-02-09 00:00:00
             * newsTitle : 咨询标题2
             */

            private String newsCover;
            private String createDate;
            private String newsTitle;
            private String linkUrl;
            private String newsId;
            private String newsContent;
            private String newsType;


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

            public String getNewsId() {
                return newsId;
            }

            public void setNewsId(String newsId) {
                this.newsId = newsId;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getNewsCover() {
                return newsCover;
            }

            public void setNewsCover(String newsCover) {
                this.newsCover = newsCover;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getNewsTitle() {
                return newsTitle;
            }

            public void setNewsTitle(String newsTitle) {
                this.newsTitle = newsTitle;
            }
        }
    }
}

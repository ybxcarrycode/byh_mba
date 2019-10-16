package com.xszj.mba.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ybx on 2017/2/7.
 */

public class AboutSchoolDetNewsBean implements Serializable {

    /**
     * returnCode : 0
     * data : {"pageCount":"1","list":[{"newsCover":"http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg","linkUrl":"www.vipshop.com","newsAuthor":"测试1","newsRemark":"咨询详情1","createDate":"2017-01-23 17:07:19","newsTitle":"咨询标题1"},{"newsCover":"http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg","linkUrl":"www.jd.com","newsAuthor":"测试2","newsRemark":"咨询详情2","createDate":"2017-01-23 17:07:19","newsTitle":"咨询标题2"}]}
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
         * list : [{"newsCover":"http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg","linkUrl":"www.vipshop.com","newsAuthor":"测试1","newsRemark":"咨询详情1","createDate":"2017-01-23 17:07:19","newsTitle":"咨询标题1"},{"newsCover":"http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg","linkUrl":"www.jd.com","newsAuthor":"测试2","newsRemark":"咨询详情2","createDate":"2017-01-23 17:07:19","newsTitle":"咨询标题2"}]
         */

        private String pageCount;
        private List<ListBean> list;

        public String getPageCount() {
            return pageCount;
        }

        public void setPageCount(String pageCount) {
            this.pageCount = pageCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * newsCover : http://oss-ysx-pic.yunshuxie.com/course/2016/11/07/15/1478504441372.jpg
             * linkUrl : www.vipshop.com
             * newsAuthor : 测试1
             * newsRemark : 咨询详情1
             * createDate : 2017-01-23 17:07:19
             * newsTitle : 咨询标题1
             */

            private String newsCover;
            private String linkUrl;
            private String newsAuthor;
            private String newsRemark;
            private String createDate;
            private String newsTitle;
            private String isCollect;
            private String newsId;
            private String newsType;

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

            public String getNewsId() {
                return newsId;
            }

            public void setNewsId(String newsId) {
                this.newsId = newsId;
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

            public String getNewsAuthor() {
                return newsAuthor;
            }

            public void setNewsAuthor(String newsAuthor) {
                this.newsAuthor = newsAuthor;
            }

            public String getNewsRemark() {
                return newsRemark;
            }

            public void setNewsRemark(String newsRemark) {
                this.newsRemark = newsRemark;
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

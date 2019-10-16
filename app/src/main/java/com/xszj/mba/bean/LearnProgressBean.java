package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Ybx on 2017/2/13.
 */

public class LearnProgressBean {

    /**
     * returnCode : 0
     * data : {"pageCount":"2","courseMemberList":[{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"2","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典111"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"2","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典111"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"2","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典111"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"}]}
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
         * pageCount : 2
         * courseMemberList : [{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"2","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典111"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"2","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典111"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"2","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典111"},{"courseCover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseId":"1","createDate":"2017-02-13 00:00:00","courseName":"mba面试宝典"}]
         */

        private String pageCount;
        private List<CourseMemberListBean> courseMemberList;

        public String getPageCount() {
            return pageCount;
        }

        public void setPageCount(String pageCount) {
            this.pageCount = pageCount;
        }

        public List<CourseMemberListBean> getCourseMemberList() {
            return courseMemberList;
        }

        public void setCourseMemberList(List<CourseMemberListBean> courseMemberList) {
            this.courseMemberList = courseMemberList;
        }

        public static class CourseMemberListBean {
            /**
             * courseCover : http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg
             * courseId : 1
             * createDate : 2017-02-13 00:00:00
             * courseName : mba面试宝典
             */

            private String courseCover;
            private String courseId;
            private String createDate;
            private String courseName;
            private String chapterId;
            private String chapterSectionId;

            public String getChapterId() {
                return chapterId;
            }

            public void setChapterId(String chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterSectionId() {
                return chapterSectionId;
            }

            public void setChapterSectionId(String chapterSectionId) {
                this.chapterSectionId = chapterSectionId;
            }

            public String getCourseCover() {
                return courseCover;
            }

            public void setCourseCover(String courseCover) {
                this.courseCover = courseCover;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }
        }
    }
}

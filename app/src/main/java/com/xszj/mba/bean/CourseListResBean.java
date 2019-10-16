package com.xszj.mba.bean;

import java.util.List;

/**
 * Created by Ybx on 2017/2/8.
 */

public class CourseListResBean {

    /**
     * returnCode : 0
     * data : [{"duration":"1小时2分钟","cover":"http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg","courseChapterNum":"3","courseId":"1","courseName":"mba面试宝典"}]
     * returnMsg : 操作成功
     */

    private String returnCode;
    private String returnMsg;
    private List<DataBean> data;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * duration : 1小时2分钟
         * cover : http://ysx-pic.oss-cn-beijing.aliyuncs.com/byhDemo/mba%E9%9D%A2%E8%AF%95%E5%AE%9D%E5%85%B8.jpg
         * courseChapterNum : 3
         * courseId : 1
         * courseName : mba面试宝典
         */

        private String duration;
        private String cover;
        private String courseChapterNum;
        private String courseId;
        private String courseName;

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCourseChapterNum() {
            return courseChapterNum;
        }

        public void setCourseChapterNum(String courseChapterNum) {
            this.courseChapterNum = courseChapterNum;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }
    }
}
